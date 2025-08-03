package com.CP.KPCOS.repository.trello;

import com.CP.KPCOS.dtos.TrelloTokenWithBoardsDTO;
import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrelloTokenRepository extends JpaRepository<TrelloTokenEntity, Long> {

    @Query("SELECT t FROM TrelloTokenEntity t WHERE t.userId = ?1 AND t.trelloApiToken IS NOT NULL")
    List<TrelloTokenEntity> findActiveByUserId(Long userId);

    @Query("SELECT t FROM TrelloTokenEntity t WHERE t.trelloId = ?1")
    TrelloTokenEntity findByTrelloId(String trelloId);

    @Query("SELECT t FROM TrelloTokenEntity t WHERE t.userId = ?1")
    List<TrelloTokenEntity> findAllByUserId(Long userId);

    @Query("SELECT t FROM TrelloTokenEntity t WHERE t.trelloId = ?1 AND t.userId = ?2")
    TrelloTokenEntity findByTrelloIdAndUserId(String trelloId, Long userId);

    @Query("SELECT NEW com.CP.KPCOS.dtos.TrelloTokenWithBoardsDTO(" +
            "t, " +
            "(SELECT b.id.trelloBoardId FROM TrelloBoardEntity b " +
            "WHERE b.id.trelloId = t.trelloId AND b.id.trelloId IN :boardIds)) " +
            "FROM TrelloTokenEntity t " +
            "WHERE t.userId = :userId")
    List<TrelloTokenWithBoardsDTO> findTokensWithBoardIds(
            @Param("userId") Long userId,
            @Param("boardIds") List<String> boardIds
    );

    @Query(value = "SELECT t.id, t.trello_id, t.trello_api_token, " +
            "b.trello_board_id as board_id " +
            "FROM trello_token t " +
            "LEFT JOIN trello_board b ON b.trello_id = t.trello_id AND b.trello_board_id IN (:boardIds) " +
            "WHERE t.user_id = :userId", nativeQuery = true)
    List<Object[]> findTokensWithBoardIdsNative(
            @Param("userId") Long userId,
            @Param("boardIds") List<String> boardIds);

}
