package com.CP.KPCOS.payload.request.filter;

import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import com.CP.KPCOS.services.trello.TrelloTokenService;
import com.CP.KPCOS.utils.ApplicationContextHelper;
import com.CP.KPCOS.utils.CommonUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseQueryRequest {

    protected String query;
    protected String sortBy;
    protected String sortOrder;
    protected int page = 0;
    protected int size = 10;
    protected UserEntity currentUser;
    protected List<String> trelloTokensIds;

    protected int getPageSize() {
        return size > 0 ? size : 10;
    }

    public abstract Specification build();

    public Sort getSort() {
        if (CommonUtils.isEmptyString(sortOrder) || (!"asc".equalsIgnoreCase(sortOrder) && !"desc".equalsIgnoreCase(sortOrder))) {
            log.warn("Invalid sort order: {}, defaulting to 'asc'", sortOrder);
            return Sort.unsorted();
        }
        return "asc".equalsIgnoreCase(sortOrder) ?
                Sort.by(Sort.Direction.ASC, sortBy) :
                Sort.by(Sort.Direction.DESC, sortBy);
    }

    public Pageable getPageable() {
        return PageRequest.of(page, getPageSize(), getSort());
    }

    protected List<String> getUserTrelloTokenIds() {
        if (this.currentUser == null) {
            log.warn("Current user is null, returning empty token list");
            return Collections.emptyList();
        }

        try {
            if (!ApplicationContextHelper.isContextAvailable()) {
                log.error("ApplicationContext is not available");
                return Collections.emptyList();
            }

            TrelloTokenService trelloTokenService = ApplicationContextHelper.getBean(TrelloTokenService.class);
            return trelloTokenService.findAllByUser(currentUser)
                    .stream()
                    .map(TrelloTokenEntity::getTrelloId)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting user trello token IDs: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
