package com.openclassrooms.mddapi.servicesImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Date;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.UserIService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserIService userService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createCommentDto(Date date, String content, UserEntity user) {
        UserEntity currentUser = userService.findById(user.getId());

        Comment comment = new Comment();
        comment.setDate(date);
        comment.setContent(content);
        comment.setUser(currentUser);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());

        Comment commentSaved = commentRepository.save(comment);
        CommentDto commentDto = modelMapper.map(commentSaved, CommentDto.class);
        return commentDto;
    }

}
