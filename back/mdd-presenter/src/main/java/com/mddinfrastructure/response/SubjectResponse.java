package com.mddinfrastructure.response;

import com.mddcore.domain.models.Subject;

import java.util.List;
import java.util.stream.Collectors;

public record SubjectResponse(
        Long id,
        String name,
        String description
) {

    public static SubjectResponse from(Subject subject) {
        return new SubjectResponse(
                subject.getId(),
                subject.getName(),
                subject.getDescription()
        );
    }

    public static List<SubjectResponse> from(List<Subject> subjectList) {
        return subjectList.stream()
                .map(SubjectResponse::from)
                .collect(Collectors.toList());
    }
}
