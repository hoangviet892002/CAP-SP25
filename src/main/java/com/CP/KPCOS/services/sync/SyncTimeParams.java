package com.CP.KPCOS.services.sync;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SyncTimeParams {
    //Unix timestamp
    private int from;
    private int to;

    public SyncTimeParams(long from, long to) {
        this.from = (int) from;
        this.to = (int) to;
    }

    public SyncTimeParams(SyncService.SyncLevel syncLevel) {
        long currentTime = System.currentTimeMillis() / 1000; // Convert to seconds
        switch (syncLevel) {
            case ALL -> {
                this.from = 0; // Sync from the beginning of time
                this.to = (int) currentTime; // Sync to now
            }
            case YESTERDAY -> {
                this.from = (int) (currentTime - 24 * 60 * 60); // 24 hours ago
                this.to = (int) currentTime; // Sync to now
            }
            case LAST_2_DAYS -> {
                this.from = (int) (currentTime - 2 * 24 * 60 * 60); // 2 days ago
                this.to = (int) currentTime; // Sync to now
            }
            case LAST_7_DAYS -> {
                this.from = (int) (currentTime - 7 * 24 * 60 * 60); // 7 days ago
                this.to = (int) currentTime; // Sync to now
            }
        }
    }
}
