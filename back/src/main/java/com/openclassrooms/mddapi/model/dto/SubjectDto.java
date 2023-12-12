package com.openclassrooms.mddapi.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
/**
 * Class dto set by subject class
 */
public class SubjectDto {
    private int idSubject;
    private String title;
    private String description;
    private Boolean isSubscribed;
}
