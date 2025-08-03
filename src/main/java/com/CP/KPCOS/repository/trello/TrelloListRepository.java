package com.CP.KPCOS.repository.trello;

import com.CP.KPCOS.entity.key.TrelloListKey;
import com.CP.KPCOS.entity.trello.TrelloListEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrelloListRepository extends JpaRepository<TrelloListEntity, TrelloListKey> {
    Page<TrelloListEntity> findAll(Specification<TrelloListEntity> specification, Pageable pageable);

    @Query("SELECT tl FROM TrelloListEntity tl WHERE tl.id.trelloId = :trelloId AND tl.id.trelloBoardId = :trelloBoardId")
    List<TrelloListEntity> findByTrelloIdAndBoardId(@Param("trelloId") String trelloId, @Param("trelloBoardId") String trelloBoardId);

    @Query("SELECT tl FROM TrelloListEntity tl WHERE tl.id.trelloId = :trelloId")
    List<TrelloListEntity> findByTrelloId(@Param("trelloId") String trelloId);
}
