package com.CP.KPCOS.controllers;

import com.CP.KPCOS.dtos.response.BaseResponse;
import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.shared.CurrentUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.CP.KPCOS.services.oauth.TrelloOAuthService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/oauth/trello")
public class TrelloOauthController {
    @Autowired
    private TrelloOAuthService trelloOAuthService;

    @GetMapping("/connect")
    public BaseResponse<String> connectTrello() {
        String url = trelloOAuthService.connectTrello();
        return new BaseResponse<>(url);
    }

    @GetMapping("/callback")
    public BaseResponse<String> callbackTrello(@RequestParam("token") String token, @CurrentUser UserEntity user) {
        String url = trelloOAuthService.callbackTrello(token, user.getId());
        return new BaseResponse<>(url);
    }
}
