package com.CP.KPCOS.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
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
    @JsonProperty("data")
    private List<T> data;
}
