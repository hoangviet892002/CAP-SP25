package com.CP.KPCOS.entity.trello;

import com.CP.KPCOS.entity.key.TrelloBoardKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "trello_board")
@Getter
@Setter
@NoArgsConstructor
public class TrelloBoardEntity implements Persistable<TrelloBoardKey> {
    @EmbeddedId
    private TrelloBoardKey id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Transient
    private boolean isNew = false;

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public String getTrelloBoardId() {
        return this.id != null ? this.id.getTrelloBoardId() : null;
    }

    public void setTrelloBoardId(String trelloBoardId) {
        if (this.id == null) {
            this.id = new TrelloBoardKey();
        }
        this.id.setTrelloBoardId(trelloBoardId);
    }

    public String getTrelloId() {
        return this.id != null ? this.id.getTrelloId() : null;
    }

    public void setTrelloId(String trelloId) {
        if (this.id == null) {
            this.id = new TrelloBoardKey();
        }
        this.id.setTrelloId(trelloId);
    }
}
