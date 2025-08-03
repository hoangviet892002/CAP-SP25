package com.CP.KPCOS.entity.trello;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trello_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrelloTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "trello_api_token", nullable = false)
    private String trelloApiToken;
    @Column(name = "trello_id", nullable = false)
    private String trelloId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "is_sync", nullable = false)
    private boolean isSync;
}
