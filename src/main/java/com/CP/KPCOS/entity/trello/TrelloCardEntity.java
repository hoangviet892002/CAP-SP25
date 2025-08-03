package com.CP.KPCOS.entity.trello;


import com.CP.KPCOS.entity.key.TrelloCardKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "trello_card")
@Getter
@Setter
@NoArgsConstructor
public class TrelloCardEntity implements Persistable<TrelloCardKey> {
    @EmbeddedId
    private TrelloCardKey id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

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
            this.id = new TrelloCardKey();
        }
        this.id.setTrelloBoardId(trelloBoardId);
    }

    public String getTrelloId() {
        return this.id != null ? this.id.getTrelloId() : null;
    }

    public void setTrelloId(String trelloId) {
        if (this.id == null) {
            this.id = new TrelloCardKey();
        }
        this.id.setTrelloId(trelloId);
    }

    public String getTrelloListId() {
        return this.id != null ? this.id.getTrelloListId() : null;
    }

    public void setTrelloListId(String trelloListId) {
        if (this.id == null) {
            this.id = new TrelloCardKey();
        }
        this.id.setTrelloListId(trelloListId);
    }

    public String getTrelloCardId() {
        return this.id != null ? this.id.getTrelloCardId() : null;
    }

    public void setTrelloCardId(String trelloCardId) {
        if (this.id == null) {
            this.id = new TrelloCardKey();
        }
        this.id.setTrelloCardId(trelloCardId);
    }
}
