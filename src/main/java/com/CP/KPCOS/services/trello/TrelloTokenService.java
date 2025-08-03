package com.CP.KPCOS.services.trello;

import com.CP.KPCOS.dtos.TrelloTokenWithBoardsDTO;
import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import com.CP.KPCOS.exceptions.ApplicationExeption;
import com.CP.KPCOS.repository.trello.TrelloTokenRepository;
import com.CP.KPCOS.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrelloTokenService {
    @Autowired
    private TrelloTokenRepository trelloTokenRepository;

    public List<TrelloTokenEntity> findAllByUserId(Long userId) {
        return trelloTokenRepository.findActiveByUserId(userId);
    }

    public List<TrelloTokenEntity> findAllByUser(UserEntity user) {
        return trelloTokenRepository.findAllByUserId(user.getId());
    }

    public void updateSyncStatus(String trelloId, boolean isSync) {
        TrelloTokenEntity token = trelloTokenRepository.findByTrelloId(trelloId);
        if (token != null) {
            token.setSync(isSync);
            trelloTokenRepository.save(token);
        }
    }

    public List<TrelloTokenWithBoardsDTO> findTokensWithBoardIds(Long userId, List<String> boardIds) {
        if (userId == null || boardIds == null || boardIds.isEmpty()) {
            throw new ApplicationExeption("User ID and board IDs must not be null or empty.");
        }

        List<Object[]> results = trelloTokenRepository.findTokensWithBoardIdsNative(userId, boardIds);
        Map<TrelloTokenEntity, List<String>> tokenBoardsMap = new HashMap<>();

        for (Object[] row : results) {
            TrelloTokenEntity token = TrelloTokenEntity.builder()
                    .id((Long) row[0])
                    .trelloId((String) row[1])
                    .trelloApiToken((String) row[2])
                    .build();
            String boardId = (String) row[3];
            if (token == null) continue;
            tokenBoardsMap.computeIfAbsent(token, k -> new ArrayList<>());
            if (boardId != null) {
                tokenBoardsMap.get(token).add(boardId);
            }
        }

        return tokenBoardsMap.entrySet().stream()
                .map(entry -> new TrelloTokenWithBoardsDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
