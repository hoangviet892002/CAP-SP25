package com.CP.KPCOS.services.trello;

import com.CP.KPCOS.entity.trello.TrelloCardEntity;
import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import com.CP.KPCOS.payload.request.TrelloApiRequest;
import com.CP.KPCOS.payload.response.trello.TrelloCardResponse;
import com.CP.KPCOS.repository.trello.TrelloCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TrelloCardService {

    @Autowired
    private TrelloApiService trelloApiService;

    @Autowired
    private TrelloCardRepository trelloCardRepository;

    public void syncTrelloCardsByListId(String listId, TrelloTokenEntity trelloToken) {
        log.info("Start syncing Trello cards for list: {}", listId);
        log.info("Async task running on thread: {}", Thread.currentThread().getName());

        try {
            // Create the API request to fetch cards
            TrelloApiRequest request = TrelloApiRequest.builder()
                    .callUrl("/lists/{id}/cards")
                    .trelloToken(trelloToken)
                    .baseType(TrelloCardResponse.class)
                    .responseType(TrelloApiRequest.ResponseType.Search)
                    .build();
            request.addPathVariable("id", listId);

            // Fetch the cards from the API
            List<TrelloCardResponse> cardResponses = trelloApiService.get(request);

            for (TrelloCardResponse cardResponse : cardResponses) {
                this.syncTrelloCardById(cardResponse.getId(), trelloToken);
            }
        } catch (Exception e) {
            log.error("Error syncing Trello cards for list: {}. Error: {}", listId, e.getMessage());
        }
    }

    protected void syncTrelloCardById(String cardId, TrelloTokenEntity trelloToken) {
        log.info("Start syncing Trello card: {}", cardId);
        log.info("Async task running on thread: {}", Thread.currentThread().getName());

        try {
            // Create the API request to fetch a single card
            TrelloApiRequest request = TrelloApiRequest.builder()
                    .callUrl("/cards/{id}")
                    .trelloToken(trelloToken)
                    .baseType(TrelloCardResponse.class)
                    .responseType(TrelloApiRequest.ResponseType.Single)
                    .build();
            request.addPathVariable("id", cardId);

            // Fetch the card from the API
            TrelloCardResponse cardResponse = trelloApiService.get(request);

            // Save or update the card in the repository
            TrelloCardEntity trelloCardEntity = cardResponse.toEntity(trelloToken);
            trelloCardRepository.save(trelloCardEntity);
        } catch (Exception e) {
            log.error("Error syncing Trello card: {}. Error: {}", cardId, e.getMessage());
        }
    }
}
