package com.CP.KPCOS.services.sync;

import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import com.CP.KPCOS.exceptions.ApplicationExeption;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class SyncTask {
    private TrelloTokenEntity trello;
    @Builder.Default
    private boolean syncBoard = false;

    @Builder.Default
    private SyncTimeParams syncTimeParams = null;
    @Builder.Default
    private SyncService.SyncLevel syncLevel = SyncService.SyncLevel.ALL;

    public SyncTimeParams getSyncTimeParams() {
        if (syncTimeParams != null) {
            return this.syncTimeParams;
        }
        return SyncService.SyncLevel.ALL.equals(this.syncLevel) ? null : new SyncTimeParams(this.syncLevel);
    }

    public void checkNeedSyncOrThrow() {
        if (!this.trello.isSync()){
            return;
        }
        if (this.syncBoard) {
            throw new ApplicationExeption("Another sync is already in progress for this board: " + this.trello.getName());
        }
    }
}
