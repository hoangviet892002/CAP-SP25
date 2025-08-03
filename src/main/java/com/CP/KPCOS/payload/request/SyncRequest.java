package com.CP.KPCOS.payload.request;

import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.exceptions.BadRequestException;
import com.CP.KPCOS.services.sync.SyncTask;
import com.CP.KPCOS.services.sync.SyncTimeParams;
import com.CP.KPCOS.shared.AppConstants;
import com.CP.KPCOS.utils.CommonUtils;
import com.CP.KPCOS.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SyncRequest {

    public enum SyncBy {
        ADMIN, USER
    }
    private SyncBy syncBy;
    private UserEntity user;
    private String trelloId;
    private SyncTask syncTask;
    private String start;
    private String end;

    public void validateAndParseRequestOrThrow(){
        if (syncTask == null){
            throw new BadRequestException("Sync task must provide.");
        }
        if ((!CommonUtils.isEmptyString(start) && CommonUtils.isEmptyString(end))
        || (CommonUtils.isEmptyString(start) && !CommonUtils.isEmptyString(end))) {
            throw new BadRequestException("Both start and end dates must be provided together or not at all.");
        }
        if (!CommonUtils.isEmptyString(start) && !CommonUtils.isEmptyString(end)){
            Timestamp startTime = DateTimeUtils.parseStringToTimestamp(this.start, AppConstants.DATE_TIME_PATTERN);
            if (startTime == null) {
                throw new BadRequestException("Invalid start date format. Expected format: " + AppConstants.DATE_TIME_PATTERN);
            }
            Timestamp endTime = DateTimeUtils.parseStringToTimestamp(this.end, AppConstants.DATE_TIME_PATTERN);
            if (endTime == null) {
                throw new BadRequestException("Invalid end date format. Expected format: " + AppConstants.DATE_TIME_PATTERN);
            }
            if (startTime.after(endTime)) {
                throw new BadRequestException("Start date cannot be after end date.");
            }

            SyncTimeParams syncTimeParams = new SyncTimeParams(startTime.getTime() / 1000, endTime.getTime() / 1000);
            syncTask.setSyncTimeParams(syncTimeParams);
        }
        if (user == null){
            throw new BadRequestException("User must be provided for sync by user.");
        }
        Long userId = user.getId();
        switch (syncBy){
            case USER:
                if (userId == null) {
                    throw new BadRequestException("User ID must be provided for user sync.");
                }
                break;
            default:
                throw new BadRequestException("Invalid sync type provided.");
        }
    }
}
