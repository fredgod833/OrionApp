package com.openclassrooms.mddapi.configuration.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Builder;
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Token {
    private String token;
}
