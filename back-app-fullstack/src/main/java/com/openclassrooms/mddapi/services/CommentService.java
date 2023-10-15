package com.openclassrooms.mddapi.services;

import java.util.Date;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.UserEntity;

public interface CommentService {

    CommentDto createCommentDto(Date date, String content, UserEntity user);

}
