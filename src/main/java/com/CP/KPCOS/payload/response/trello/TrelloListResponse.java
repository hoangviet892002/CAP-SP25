package com.CP.KPCOS.payload.response.trello;

import com.CP.KPCOS.entity.trello.TrelloListEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrelloListResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("closed")
    private boolean closed;

    @JsonProperty("color")
    private String color;

    @JsonProperty("idBoard")
    private String idBoard;

    @JsonProperty("pos")
    private Integer pos;

    @JsonProperty("subscribed")
    private boolean subscribed;

    @JsonProperty("softLimit")
    private Integer softLimit;

    @JsonProperty("type")
    private String type;

    @JsonProperty("datasource")
    private DataSource datasource;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DataSource {
        @JsonProperty("filter")
        private boolean filter;
    }

    /**
     * Convert TrelloListResponse to TrelloListEntity
     */
    public TrelloListEntity toEntity() {
        TrelloListEntity entity = new TrelloListEntity();
        entity.setTrelloListId(this.id);
        entity.setTrelloBoardId(this.idBoard);
        entity.setName(this.name);
        return entity;
    }
}
