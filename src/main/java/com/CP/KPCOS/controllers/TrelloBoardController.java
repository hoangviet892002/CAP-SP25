package com.CP.KPCOS.controllers;


import com.CP.KPCOS.dtos.response.BaseList;
import com.CP.KPCOS.dtos.response.BaseResponse;
import com.CP.KPCOS.dtos.response.object.TripResponse;
import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.entity.trello.TrelloBoardEntity;
import com.CP.KPCOS.payload.request.filter.TrelloBoardQueryRequest;
import com.CP.KPCOS.services.trello.TrelloBoardService;
import com.CP.KPCOS.shared.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trip")
public class TrelloBoardController {

    @Autowired
    private TrelloBoardService trelloBoardService;

    @GetMapping("")
    public BaseResponse<BaseList<TrelloBoardEntity>> getAllBoards(TrelloBoardQueryRequest queryRequest, @CurrentUser UserEntity user) {
        queryRequest.setCurrentUser(user);
        BaseList<TrelloBoardEntity> boards = trelloBoardService.findAll(queryRequest);
        return new BaseResponse<>(boards);
    }

    @GetMapping("/{id}")
    public BaseResponse<TripResponse> getBoardById(@PathVariable("id") String id, @CurrentUser UserEntity user) {
        TripResponse tripResponse = trelloBoardService.getTripByTrelloBoardId(id);
        return new BaseResponse<>(tripResponse);
    }
}
