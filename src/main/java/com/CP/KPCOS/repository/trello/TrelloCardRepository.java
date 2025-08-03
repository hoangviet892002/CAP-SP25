package com.CP.KPCOS.repository.trello;

import com.CP.KPCOS.entity.key.TrelloCardKey;
import com.CP.KPCOS.entity.trello.TrelloCardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrelloCardRepository extends JpaRepository<TrelloCardEntity, TrelloCardKey> {
    Page<TrelloCardEntity> findAll(Specification<TrelloCardEntity> specification, Pageable pageable);
}

