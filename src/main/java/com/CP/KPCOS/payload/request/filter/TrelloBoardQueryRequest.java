package com.CP.KPCOS.payload.request.filter;

import com.CP.KPCOS.entity.trello.TrelloBoardEntity;
import com.CP.KPCOS.utils.CommonUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
@Component
@RequiredArgsConstructor
public class TrelloBoardQueryRequest extends BaseQueryRequest {

    @Override
    public Specification<TrelloBoardEntity> build() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            List<String> trelloTokenIds = getUserTrelloTokenIds();
            if (CommonUtils.isEmptyCollection(this.trelloTokensIds)) {
                predicates.add(root.get("id").get("trelloId").in(trelloTokenIds));
            } else {
                predicates.add(root.get("id").get("trelloId").in(this.trelloTokensIds));
            }
            // Global search query
            if (!CommonUtils.isEmptyString(this.query)) {
                String searchTerm = "%" + this.query.toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchTerm),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("url")), searchTerm)
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
