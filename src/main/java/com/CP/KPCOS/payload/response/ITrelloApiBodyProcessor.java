package com.CP.KPCOS.payload.response;

import org.springframework.util.MultiValueMap;

public interface ITrelloApiBodyProcessor {
    MultiValueMap<String, Object> processBody();
}
