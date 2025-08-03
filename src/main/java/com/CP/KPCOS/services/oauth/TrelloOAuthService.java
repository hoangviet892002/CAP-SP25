package com.CP.KPCOS.services.oauth;

import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import com.CP.KPCOS.exceptions.ApiException;
import com.CP.KPCOS.exceptions.AppException;
import com.CP.KPCOS.payload.request.TrelloApiRequest;
import com.CP.KPCOS.payload.response.trello.TrelloAccountResponse;
import com.CP.KPCOS.repository.trello.TrelloTokenRepository;
import com.CP.KPCOS.services.sync.SyncService;
import com.CP.KPCOS.services.sync.SyncTask;
import com.CP.KPCOS.services.trello.TrelloApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrelloOAuthService {
    @Autowired
    private TrelloApiService trelloApiService;
    @Autowired
    private TrelloTokenRepository trelloTokenRepository;
    @Autowired
    private SyncService syncService;

    public String connectTrello() {
        TrelloApiRequest request = TrelloApiRequest.builder()
                .callUrl("/authorize")
                .build();
        request.addParameter("callback_method", "fragment");
        request.addParameter("return_url", "http://localhost:5173/trello/callback");
        request.addParameter("scope", "read,write");
        request.addParameter("expiration", "never");
        request.addParameter("response_type", "code");
        trelloApiService.buildRequest(request);
        return request.getCallUrl();
    }

    public String callbackTrello(String token, Long userId) {
        TrelloApiRequest request = TrelloApiRequest.builder()
                .callUrl("/members/me")
                .baseType(TrelloAccountResponse.class)
                .responseType(TrelloApiRequest.ResponseType.Single)
                .build();
        request.addParameter("token", token);
        TrelloAccountResponse response = trelloApiService.get(request);
        if (response == null || response.getId() == null) {
            throw new ApiException("Failed to retrieve Trello account information.");
        }
        TrelloTokenEntity existingToken = trelloTokenRepository.findByTrelloIdAndUserId(response.getId(), userId);

        if (existingToken != null) {
            existingToken.setName(response.getFullName());
            existingToken.setTrelloApiToken(token);
            existingToken.setUserId(userId);
        } else {
            TrelloTokenEntity newEntity = new TrelloTokenEntity();
            newEntity.setTrelloId(response.getId());
            newEntity.setName(response.getFullName());
            newEntity.setTrelloApiToken(token);
            newEntity.setUserId(userId);
            trelloTokenRepository.save(newEntity);
        }

        try {
            assert existingToken != null;
            trelloTokenRepository.save(existingToken);
            SyncTask syncTask = SyncTask.builder()
                    .syncBoard(true)
                    .trello(existingToken)
                    .build();
            syncService.syncByTrelloToken(syncTask);

        } catch (Exception e) {
            throw new ApiException("Failed to save Trello token: " + e.getMessage(), e);
        }
        return "Trello account connected successfully: " + response.getFullName();
    }
}
