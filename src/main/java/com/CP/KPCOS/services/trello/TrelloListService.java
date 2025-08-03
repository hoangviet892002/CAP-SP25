package com.CP.KPCOS.services.trello;

import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import com.CP.KPCOS.payload.request.TrelloApiRequest;
import com.CP.KPCOS.payload.response.trello.TrelloListResponse;
import com.CP.KPCOS.repository.trello.TrelloListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TrelloListService {

    @Autowired
    private TrelloListRepository trelloListRepository;
    @Autowired
    private TrelloApiService trelloApiService;
    @Autowired
    private TrelloCardService trelloCardService;

    public void syncTrelloLists(List<String> idBoards, TrelloTokenEntity trelloTokenEntity) {
        log.info("Start syncing Trello lists for boards: {}", idBoards);
        log.info("Async task running on thread: {}", Thread.currentThread().getName());

        for (String boardId : idBoards) {
            try {
                TrelloApiRequest request = TrelloApiRequest.builder()
                        .callUrl("/boards/{id}/lists")
                        .trelloToken(trelloTokenEntity)
                        .baseType(TrelloListResponse.class)
                        .responseType(TrelloApiRequest.ResponseType.Search)
                        .build();
                request.addPathVariable("id", boardId);

                List<TrelloListResponse> listResponses = trelloApiService.get(request);

                if (listResponses == null || listResponses.isEmpty()) {
                    log.warn("No Trello lists found for board: {}", boardId);
                    return;
                }

                for (TrelloListResponse listResponse : listResponses) {
                    trelloCardService.syncTrelloCardsByListId(listResponse.getId(), trelloTokenEntity);
                }
                trelloListRepository.saveAll(
                        listResponses.stream()
                                .map(TrelloListResponse::toEntity)
                                .peek(listEntity -> listEntity.setTrelloId(trelloTokenEntity.getTrelloId()))
                                .toList()
                );
            } catch (Exception e) {
                log.error("Error syncing Trello lists for board: {}. Error: {}", boardId, e.getMessage());
            }
        }
    }


}
