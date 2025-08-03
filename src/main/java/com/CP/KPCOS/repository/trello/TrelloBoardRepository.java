package com.CP.KPCOS.repository.trello;

import com.CP.KPCOS.entity.key.TrelloBoardKey;
import com.CP.KPCOS.entity.trello.TrelloBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TrelloBoardRepository extends JpaRepository<TrelloBoardEntity, TrelloBoardKey> {
    Page<TrelloBoardEntity> findAll(Specification<TrelloBoardEntity> specification, Pageable pageable);

    @Query("SELECT t FROM TrelloBoardEntity t WHERE t.id.trelloBoardId IN :boardIds")
    List<TrelloBoardEntity> findByBoardIds(@Param("boardIds") List<String> boardIds);

    @Query("SELECT t FROM TrelloBoardEntity t WHERE t.id.trelloBoardId = :trelloBoardId")
    TrelloBoardEntity findByTrelloBoardId(@Param("trelloBoardId") String trelloBoardId);

    @Query(value = """
        SELECT 
            tb.trello_board_id as trelloBoardId,
            tb.name as boardName,
            tb.trello_id as trelloId,
            tl.trello_list_id as trelloListId,
            tl.name as listName,
            tc.trello_card_id as trelloCardId,
            tc.name as cardName,
            tc.image as cardImage
        FROM trello_board tb
        LEFT JOIN trello_list tl ON tb.trello_board_id = tl.trello_board_id AND tb.trello_id = tl.trello_id
        LEFT JOIN trello_card tc ON tl.trello_board_id = tc.trello_board_id 
            AND tl.trello_id = tc.trello_id 
            AND tl.trello_list_id = tc.trello_list_id
        WHERE tb.trello_board_id = :trelloBoardId
        ORDER BY tl.trello_list_id, tc.trello_card_id
        """, nativeQuery = true)
    List<Map<String, Object>> findTripWithPlacesAndMemoriesByBoardId(@Param("trelloBoardId") String trelloBoardId);
}
