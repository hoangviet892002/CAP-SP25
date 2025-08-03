package com.CP.KPCOS.entity.key;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TrelloCardKey {
    @Column(name = "trello_board_id")
    private String trelloBoardId;

    @Column(name = "trello_id")
    private String trelloId;

    @Column(name = "trello_list_id")
    private String trelloListId;

    @Column(name = "trello_card_id")
    private String trelloCardId;
}
