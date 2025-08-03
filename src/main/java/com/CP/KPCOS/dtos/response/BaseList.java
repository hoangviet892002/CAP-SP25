package com.CP.KPCOS.dtos.response;

import com.CP.KPCOS.entity.trello.TrelloBoardEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@Builder
public class BaseList<T> {
    @JsonProperty("page")
    private int page;
    @JsonProperty("size")
    private int size;
    @JsonProperty("totalPage")
    private int totalPage;
    @JsonProperty("totalRecord")
    private int totalRecord;
    @JsonProperty("datas")
    private List<T> data;

    public BaseList() {
        this.page = 0;
        this.size = 10; // Default size
        this.totalPage = 0;
        this.totalRecord = 0;
        this.data = List.of(); // Empty list by default
    }

    public BaseList(Page<T> page) {
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalPage = page.getTotalPages();
        this.totalRecord = (int) page.getTotalElements();
        this.data = page.getContent();
    }
}
