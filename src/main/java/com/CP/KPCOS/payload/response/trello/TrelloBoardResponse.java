package com.CP.KPCOS.payload.response.trello;

import com.CP.KPCOS.entity.trello.TrelloBoardEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrelloBoardResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

    public TrelloBoardEntity toEntity() {
        TrelloBoardEntity entity = new TrelloBoardEntity();
        entity.setTrelloBoardId(this.id);
        entity.setName(this.name);
        entity.setUrl(this.url);
        return entity;
    }
}
