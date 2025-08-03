package com.CP.KPCOS.payload.response.trello;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrelloLimitsResponse {
    @JsonProperty("boards")
    private LimitDetail boards;

    @JsonProperty("orgs")
    private LimitDetail orgs;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LimitDetail {
        @JsonProperty("totalPerMember")
        private LimitStatus totalPerMember;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LimitStatus {
        @JsonProperty("status")
        private String status;

        @JsonProperty("disableAt")
        private int disableAt;

        @JsonProperty("warnAt")
        private int warnAt;
    }
}
