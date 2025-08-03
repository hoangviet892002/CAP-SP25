package com.CP.KPCOS.payload.response.trello;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrelloPrefsResponse {
    @JsonProperty("sendSummaries")
    private boolean sendSummaries;

    @JsonProperty("minutesBetweenSummaries")
    private int minutesBetweenSummaries;

    @JsonProperty("minutesBeforeDeadlineToNotify")
    private int minutesBeforeDeadlineToNotify;

    @JsonProperty("colorBlind")
    private boolean colorBlind;

    @JsonProperty("keyboardShortcutsEnabled")
    private boolean keyboardShortcutsEnabled;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("privacy")
    private Privacy privacy;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Privacy {
        @JsonProperty("fullName")
        private String fullName;

        @JsonProperty("avatar")
        private String avatar;
    }
}
