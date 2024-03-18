package com.mdddetails.repository.comment;

import com.mddcore.domain.models.Comment;
import com.mddcore.domain.repository.ICommentRepository;
import com.mdddetails.mapper.CommentMapper;
import com.mdddetails.models.CommentEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages comment data operations.
 */
@Repository
public class CommentRepoImpl implements ICommentRepository {
    private final CommentJpaRepo jpaRepo;
    private final CommentMapper commentMapper;

    public CommentRepoImpl(CommentJpaRepo jpaRepo, CommentMapper commentMapper) {
        this.jpaRepo = jpaRepo;
        this.commentMapper = commentMapper;
    }


    /**
     * Gets all comments.
     * @return list of comments
     */
    @Override
    @Transactional
    public List<Comment> findAll() {
        return jpaRepo.findAll().stream().map(commentMapper::toDomain).collect(Collectors.toList());
    }

    /**
     * Saves a comment.
     * @param entity comment to save
     */
    @Override
    public void save(Comment entity) {
        CommentEntity comment = commentMapper.toEntity(entity);
        jpaRepo.save(comment);
    }
}
