package com.mddinfrastructure.config;

import com.mddcore.domain.repository.IArticleRepository;
import com.mddcore.domain.repository.ICommentRepository;
import com.mddcore.domain.repository.ISubjectRepository;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.article.CreateArticleUseCase;
import com.mddcore.usecases.article.GetAllArticleUseCase;
import com.mddcore.usecases.article.GetArticleUseCase;
import com.mddcore.usecases.auth.IJwtExecFinal;
import com.mddcore.usecases.auth.IPasswordEncodeFinal;
import com.mddcore.usecases.comment.CreateCommentUseCase;
import com.mddcore.usecases.comment.GetAllCommentUseCase;
import com.mddcore.usecases.subject.GetAllSubjectUseCase;
import com.mddcore.usecases.subject.GetSubjectUseCase;
import com.mddcore.usecases.user.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfigImpl {


    // USER
    @Bean
    public GetUserUseCase getUserUseCase(IUserRepository userRepository) {
        return new GetUserUseCase(userRepository);
    }
    @Bean
    public DeleteUserUseCase deleteUserUseCase(IUserRepository userRepository) {
        return new DeleteUserUseCase(userRepository);
    }
    @Bean
    public UpdateUserUseCase updateUserUseCase(IUserRepository userRepository, IPasswordEncodeFinal passwordEncodeFinal) {
        return new UpdateUserUseCase(userRepository, passwordEncodeFinal);
    }

    // AUTH
    @Bean
    public RegisterUseCase registerUseCase(IUserRepository userRepository, IPasswordEncodeFinal passwordEncodeFinal) {
        return new RegisterUseCase(userRepository, passwordEncodeFinal);
    }
    @Bean
    public LoginUseCase loginUseCase(IJwtExecFinal jwtExecFinal) {
        return new LoginUseCase(jwtExecFinal);
    }

    // COMMENT
    @Bean
    public CreateCommentUseCase createCommentUseCase(ICommentRepository commentRepository, GetArticleUseCase getArticleUseCase) {
        return new CreateCommentUseCase(commentRepository, getArticleUseCase);
    }
    @Bean
    public GetAllCommentUseCase getAllCommentUseCase(ICommentRepository commentRepository) {
        return new GetAllCommentUseCase(commentRepository);
    }

    // SUBJECT
    @Bean
    public GetAllSubjectUseCase getAllSubjectUseCase(ISubjectRepository subjectRepository) {
        return new GetAllSubjectUseCase(subjectRepository);
    }
    @Bean
    public GetSubjectUseCase getSubjectUseCase(ISubjectRepository subjectRepository) {
        return new GetSubjectUseCase(subjectRepository);
    }

    // ARTICLE
    @Bean
    public CreateArticleUseCase createArticleUseCase(IArticleRepository articleRepository, GetSubjectUseCase getSubjectUseCase,
                                                     GetUserUseCase getUserUseCase) {
        return new CreateArticleUseCase(articleRepository, getSubjectUseCase, getUserUseCase);
    }
    @Bean
    public GetAllArticleUseCase getAllArticleUseCase(IArticleRepository articleRepository) {
        return new GetAllArticleUseCase(articleRepository);
    }
    @Bean
    public GetArticleUseCase getArticleUseCase(IArticleRepository articleRepository) {
        return new GetArticleUseCase(articleRepository);
    }
}
