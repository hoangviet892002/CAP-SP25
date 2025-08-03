package com.CP.KPCOS.entity.key;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TrelloBoardKey implements Serializable {
    @Column(name = "trello_board_id")
    private String trelloBoardId;

    @Column(name = "trello_id")
    private String trelloId;
}
