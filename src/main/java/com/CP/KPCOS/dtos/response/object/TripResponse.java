package com.CP.KPCOS.dtos.response.object;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripResponse {

    private String trelloBoardId;

    private String name;

    private String trelloId;

    private List<PlaceResponse> places;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceResponse {

        private String trelloId;

        private String trelloBoardId;

        private String trelloListId;

        private String name;

        private List<MemoryResponse> memories;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemoryResponse {

        private String trelloId;

        private String trelloBoardId;

        private String trelloListId;

        private String trelloCardId;

        private String image;

        private String name;

    }
}
