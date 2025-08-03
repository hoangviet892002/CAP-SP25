package com.CP.KPCOS.controllers;


import com.CP.KPCOS.dtos.response.BaseResponse;
import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.services.sync.SyncService;
import com.CP.KPCOS.shared.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sync")
@Slf4j
public class SyncController {
    @Autowired
    private SyncService syncService;

    @GetMapping("/board")
    public BaseResponse<String> syncBoard(@CurrentUser UserEntity user) {
        long startTime = System.currentTimeMillis();
        syncService.syncBoardByUser(user);
        long endTime = System.currentTimeMillis();
        log.info("API response time: {}ms", endTime - startTime);
        return new BaseResponse<>(null, "Board sync started successfully.");
    }
    
    @GetMapping("/list")
    public BaseResponse<String> syncList(@RequestParam List<String> boardIds, @CurrentUser UserEntity user) {
        long startTime = System.currentTimeMillis();
        syncService.syncListByUser(user, boardIds);
        long endTime = System.currentTimeMillis();
        log.info("API response time: {}ms", endTime - startTime);
        return new BaseResponse<>(null, "List sync started successfully for " + boardIds.size() + " boards.");
    }
}
