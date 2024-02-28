package com.mddinfrastructure.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CommentRequest(
        @NotNull @Size(min = 25, max = 220) String content,
        @NotNull String userName,
        @NotNull Long article_id) {}
