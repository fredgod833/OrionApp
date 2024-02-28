package com.mdddetails.repository.comment;

import com.mddcore.domain.models.Comment;
import com.mddcore.domain.repository.ICommentRepository;
import com.mdddetails.mapper.CommentMapper;
import com.mdddetails.models.CommentEntity;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CommentRepoImpl implements ICommentRepository {
    private final CommentJpaRepo jpaRepo;
    private final CommentMapper commentMapper;

    public CommentRepoImpl(CommentJpaRepo jpaRepo, CommentMapper commentMapper) {
        this.jpaRepo = jpaRepo;
        this.commentMapper = commentMapper;
    }

    @Override
    public List<Comment> findAll() {
        return jpaRepo.findAll().stream()
                .map(commentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Comment entity) {
        CommentEntity comment = commentMapper.toEntity(entity);
        jpaRepo.save(comment);
    }
}
