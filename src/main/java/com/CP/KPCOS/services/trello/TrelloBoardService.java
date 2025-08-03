package com.CP.KPCOS.services.trello;

import com.CP.KPCOS.dtos.response.BaseList;
import com.CP.KPCOS.dtos.response.object.TripResponse;
import com.CP.KPCOS.entity.trello.TrelloBoardEntity;
import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import com.CP.KPCOS.exceptions.ApplicationExeption;
import com.CP.KPCOS.payload.request.TrelloApiRequest;
import com.CP.KPCOS.payload.request.filter.TrelloBoardQueryRequest;
import com.CP.KPCOS.payload.response.trello.TrelloBoardResponse;
import com.CP.KPCOS.repository.trello.TrelloBoardRepository;
import com.CP.KPCOS.services.sync.SyncTimeParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TrelloBoardService {
    @Autowired
    private TrelloBoardRepository trelloBoardRepository;
    @Autowired
    private TrelloApiService trelloApiService;

    public void syncTrelloBoard(TrelloTokenEntity trelloTokenEntity, SyncTimeParams syncTimeParams) {
        log.info("Start syncing Trello board for: {}", trelloTokenEntity.getName());
        log.info("Async task running on thread: {}", Thread.currentThread().getName());
        TrelloApiRequest trelloApiRequest = TrelloApiRequest.builder()
                .callUrl("/members/me/boards")
                .trelloToken(trelloTokenEntity)
                .baseType(TrelloBoardResponse.class)
                .responseType(TrelloApiRequest.ResponseType.Search)
                .build();
        trelloApiRequest.addParameter("fields", "name,url");
        List<TrelloBoardResponse> boardResponses = trelloApiService.get(trelloApiRequest);
        if (boardResponses == null || boardResponses.isEmpty()) {
            log.warn("No Trello boards found for token: {}", trelloTokenEntity.getTrelloId());
            return;
        }
        List<TrelloBoardEntity> boardEntities = boardResponses.stream()
                .map(TrelloBoardResponse::toEntity).collect(Collectors.toList());
        boardEntities.forEach(boardEntity -> {
            boardEntity.setTrelloId(trelloTokenEntity.getTrelloId());
        });
        try {
            trelloBoardRepository.saveAll(boardEntities);
            log.info("Successfully synced {} Trello boards for token: {}", boardEntities.size(), trelloTokenEntity.getTrelloId());
        } catch (Exception e) {
            log.error("Error saving Trello boards for token: {}. Error: {}", trelloTokenEntity.getTrelloId(), e.getMessage());
            throw new ApplicationExeption("Failed to save Trello boards: " + e.getMessage(), e);
        }
    }

    public BaseList findAll(TrelloBoardQueryRequest queryRequest) {
        try{
            log.info("Finding Trello boards with query: {}", queryRequest.getQuery());
            Specification<TrelloBoardEntity> specification = queryRequest.build();
            return new BaseList<>(trelloBoardRepository.findAll(specification, queryRequest.getPageable()));
        }
        catch (Exception e) {
            return new BaseList<>(Page.empty());
        }
    }

    /**
     * Get complete TripResponse with places and memories by trelloBoardId using single query
     */
    public TripResponse getTripByTrelloBoardId(String trelloBoardId) {
        try {
            log.info("Getting complete trip data for trelloBoardId: {}", trelloBoardId);

            List<Map<String, Object>> rawData = trelloBoardRepository.findTripWithPlacesAndMemoriesByBoardId(trelloBoardId);

            if (rawData.isEmpty()) {
                log.warn("No trip found for trelloBoardId: {}", trelloBoardId);
                return null;
            }

            return convertToTripResponse(rawData);

        } catch (Exception e) {
            log.error("Error getting trip for trelloBoardId: {}. Error: {}", trelloBoardId, e.getMessage());
            throw new ApplicationExeption("Failed to get trip: " + e.getMessage(), e);
        }
    }

    /**
     * Convert raw database results to single TripResponse object
     */
    private TripResponse convertToTripResponse(List<Map<String, Object>> rawData) {
        if (rawData.isEmpty()) {
            return null;
        }

        // Get trip info from first row
        Map<String, Object> firstRow = rawData.get(0);
        TripResponse trip = new TripResponse();
        trip.setTrelloBoardId((String) firstRow.get("trelloBoardId"));
        trip.setName((String) firstRow.get("boardName"));
        trip.setTrelloId((String) firstRow.get("trelloId"));

        // Group data by places (lists)
        Map<String, TripResponse.PlaceResponse> placesMap = new LinkedHashMap<>();

        for (Map<String, Object> row : rawData) {
            String trelloListId = (String) row.get("trelloListId");
            String listName = (String) row.get("listName");
            String trelloCardId = (String) row.get("trelloCardId");
            String cardName = (String) row.get("cardName");
            String cardImage = (String) row.get("cardImage");

            // Handle places (lists)
            if (trelloListId != null) {
                TripResponse.PlaceResponse place = placesMap.computeIfAbsent(trelloListId, k -> {
                    TripResponse.PlaceResponse newPlace = new TripResponse.PlaceResponse();
                    newPlace.setTrelloId(trip.getTrelloId());
                    newPlace.setTrelloBoardId(trip.getTrelloBoardId());
                    newPlace.setTrelloListId(trelloListId);
                    newPlace.setName(listName);
                    newPlace.setMemories(new ArrayList<>());
                    return newPlace;
                });

                // Handle memories (cards)
                if (trelloCardId != null) {
                    TripResponse.MemoryResponse memory = new TripResponse.MemoryResponse();
                    memory.setTrelloId(trip.getTrelloId());
                    memory.setTrelloBoardId(trip.getTrelloBoardId());
                    memory.setTrelloListId(trelloListId);
                    memory.setTrelloCardId(trelloCardId);
                    memory.setName(cardName);
                    memory.setImage(cardImage);
                    place.getMemories().add(memory);
                }
            }
        }

        trip.setPlaces(new ArrayList<>(placesMap.values()));

        log.info("Successfully converted trip: {} with {} places", trip.getName(), trip.getPlaces().size());
        return trip;
    }
}
