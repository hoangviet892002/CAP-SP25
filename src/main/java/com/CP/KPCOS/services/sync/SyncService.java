package com.CP.KPCOS.services.sync;

import com.CP.KPCOS.dtos.TrelloTokenWithBoardsDTO;
import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.entity.trello.TrelloBoardEntity;
import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import com.CP.KPCOS.exceptions.BadRequestException;
import com.CP.KPCOS.exceptions.NotFoundException;
import com.CP.KPCOS.payload.request.SyncRequest;
import com.CP.KPCOS.services.app.AuthService.UserService;
import com.CP.KPCOS.services.trello.TrelloBoardService;
import com.CP.KPCOS.services.trello.TrelloListService;
import com.CP.KPCOS.services.trello.TrelloTokenService;
import com.CP.KPCOS.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SyncService {
    public enum SyncLevel {
        ALL, YESTERDAY, LAST_2_DAYS, LAST_7_DAYS
    }

    @Autowired
    private TrelloBoardService trelloBoardService;
    @Autowired
    TrelloListService trelloListService;
    @Autowired
    TrelloTokenService trelloTokenService;
    @Autowired
    private UserService userService;

    public void syncByUser(SyncRequest syncRequest) {
        Long userId = syncRequest.getUser().getId();
        UserEntity user = userService.findById(userId);

        List<TrelloTokenEntity> lsTrelloTokens = trelloTokenService.findAllByUserId(userId);

        if (CommonUtils.isEmptyCollection(lsTrelloTokens)) {
            throw new NotFoundException("User does not have any Trello tokens.");
        }

        for (TrelloTokenEntity token : lsTrelloTokens) {
            SyncTask syncTask = syncRequest.getSyncTask();
            syncTask.setTrello(token);
            sync(syncTask);
        }
    }

    @Async ("syncExecutor")
    public void syncByTrelloToken(SyncTask task) {
        if (task == null || task.getTrello() == null) {
            throw new BadRequestException("Sync task or Trello token cannot be null.");
        }
        sync(task);
    }

    public void sync(SyncTask syncTask) {
        TrelloTokenEntity trelloToken = syncTask.getTrello();
        syncTask.checkNeedSyncOrThrow();
        trelloTokenService.updateSyncStatus(trelloToken.getTrelloId(), true);
        log.info("Syncing Trello token: {}", trelloToken.getTrelloId());
        SyncTimeParams syncTimeParams = syncTask.getSyncTimeParams();

        try{
            if (syncTask.isSyncBoard()) {
                trelloBoardService.syncTrelloBoard(trelloToken, syncTimeParams);
            }
        }
        catch (Exception e) {
            log.error("Error during sync for Trello token {}: {}", trelloToken.getTrelloId(), e.getMessage(), e);
            throw new SyncDataExeption("Error during sync: " + e.getMessage());
        } finally {
            trelloTokenService.updateSyncStatus(trelloToken.getTrelloId(), false);
            log.info("Sync completed for Trello token: {}", trelloToken.getTrelloId());
        }
    }


    @Async("syncExecutor")
    public void syncBoardByUser(UserEntity user) {
        List<TrelloTokenEntity> trelloTokens = trelloTokenService.findAllByUserId(user.getId());
        if (CommonUtils.isEmptyCollection(trelloTokens)) {
            throw new NotFoundException("User does not have any Trello tokens.");
        }
        for (TrelloTokenEntity token : trelloTokens) {
            SyncTask syncTask = SyncTask.builder()
                    .trello(token)
                    .syncBoard(true)
                    .syncLevel(SyncLevel.ALL)
                    .build();
            sync(syncTask);
        }
    }

    @Async("syncExecutor")
    public void syncListByUser(UserEntity user, List<String> boardIds) {
        // find tokens by board IDs
        List<TrelloTokenWithBoardsDTO> trelloTokens = trelloTokenService.findTokensWithBoardIds(user.getId(), boardIds);
        if (CommonUtils.isEmptyCollection(trelloTokens)) {
            throw new NotFoundException("User does not have any Trello tokens for the specified boards.");
        }
        for (TrelloTokenWithBoardsDTO token : trelloTokens) {
           trelloListService.syncTrelloLists(token.getBoardIds(), token.getTrelloToken());
        }
    }
}
