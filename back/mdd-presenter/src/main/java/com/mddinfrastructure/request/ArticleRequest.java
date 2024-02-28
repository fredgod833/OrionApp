package com.mddinfrastructure.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record ArticleRequest(
        @NotNull Long subject_id,
        @NotNull Long user_id,
        @NotNull @Size(min = 3 , max = 80) String title,
        @NotNull @Size(min = 80) String content
) {}
