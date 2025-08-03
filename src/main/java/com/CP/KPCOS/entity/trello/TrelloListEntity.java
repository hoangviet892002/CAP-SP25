package com.CP.KPCOS.entity.trello;


import com.CP.KPCOS.entity.key.TrelloListKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "trello_list")
@Getter
@Setter
@NoArgsConstructor
public class TrelloListEntity implements Persistable<TrelloListKey> {
    @EmbeddedId
    private TrelloListKey id;
    @Column
    private String name;

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
            this.id = new TrelloListKey();
        }
        this.id.setTrelloBoardId(trelloBoardId);
    }

    public String getTrelloId() {
        return this.id != null ? this.id.getTrelloId() : null;
    }

    public void setTrelloId(String trelloId) {
        if (this.id == null) {
            this.id = new TrelloListKey();
        }
        this.id.setTrelloId(trelloId);
    }

    public String getTrelloListId() {
        return this.id != null ? this.id.getTrelloListId() : null;
    }

    public void setTrelloListId(String trelloListId) {
        if (this.id == null) {
            this.id = new TrelloListKey();
        }
        this.id.setTrelloListId(trelloListId);
    }
}
