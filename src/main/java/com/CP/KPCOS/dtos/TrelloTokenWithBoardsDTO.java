package com.CP.KPCOS.dtos;

import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrelloTokenWithBoardsDTO {
    private TrelloTokenEntity trelloToken;
    private List<String> boardIds;
}
