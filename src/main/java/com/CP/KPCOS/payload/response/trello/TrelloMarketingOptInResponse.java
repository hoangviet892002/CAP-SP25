package com.CP.KPCOS.payload.response.trello;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrelloMarketingOptInResponse {
    @JsonProperty("optedIn")
    private boolean optedIn;

    @JsonProperty("date")
    private String date;
}
