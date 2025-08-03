package com.CP.KPCOS.controllers;


import com.CP.KPCOS.dtos.response.BaseResponse;
import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import com.CP.KPCOS.services.trello.TrelloTokenService;
import com.CP.KPCOS.shared.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trello")
public class TrelloTokenController {

    @Autowired
    private TrelloTokenService trelloTokenService;

    @GetMapping("")
    public BaseResponse<List<TrelloTokenEntity>> getAllTokens(@CurrentUser UserEntity user) {
        List<TrelloTokenEntity> tokens = trelloTokenService.findAllByUser(user);
        return new BaseResponse<>(tokens);
    }

}
