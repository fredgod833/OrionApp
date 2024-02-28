package com.mddcore.usecases.comment;

import com.mddcore.domain.models.Comment;
import com.mddcore.domain.repository.ICommentRepository;
import com.mddcore.usecases.UseCase;

import java.util.List;

public class GetAllCommentUseCase extends UseCase<GetAllCommentUseCase.InputValues, GetAllCommentUseCase.OutputValues> {
    private final ICommentRepository commentRepository;

    public GetAllCommentUseCase(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public OutputValues execute(InputValues inputValues) {
        return new OutputValues(commentRepository.findAll());
    }


    public record InputValues() implements UseCase.InputValues {}
    public record OutputValues(List<Comment> commentList) implements UseCase.OutputValues {}
}
