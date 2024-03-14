package com.mddcore.usecases.ut.article;

import com.mddcore.domain.models.Article;
import com.mddcore.domain.models.Subject;
import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IArticleRepository;
import com.mddcore.usecases.article.CreateArticleUseCase;
import com.mddcore.usecases.subject.GetSubjectUseCase;
import com.mddcore.usecases.user.GetUserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateArticleUseCaseUnitTest {
    @InjectMocks
    private CreateArticleUseCase articleUseCase;
    @Mock
    private IArticleRepository articleRepository;
    @Mock
    private GetSubjectUseCase subjectUseCase;
    @Mock
    private GetUserUseCase userUseCase;

    private final CreateArticleUseCase.InputRequest inputRequest = new CreateArticleUseCase.InputRequest(1L, 1L, "Title test", "Content test");

    @Test
    public void CreateArticle_ShouldReturnTrue_WithValidInputRequest() {
        Subject subject = new Subject();
        User user = new User();

        Article article = new Article();
        article.setSubject(subject);
        article.setUser(user);
        article.setTitle(inputRequest.title());
        article.setContent(inputRequest.content());

        doReturn(new GetSubjectUseCase.OutputValues(subject)).when(subjectUseCase).execute(new GetSubjectUseCase.InputValues(inputRequest.subject_id()));
        doReturn(new GetUserUseCase.OutputValues(user)).when(userUseCase).execute(new GetUserUseCase.InputValues(inputRequest.user_id()));

        CreateArticleUseCase.InputValues inputValues = new CreateArticleUseCase.InputValues(inputRequest);
        CreateArticleUseCase.OutputValues outputValues = articleUseCase.execute(inputValues);

        assertTrue(outputValues.success());
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    public void CreateArticle_ShouldThrowError_WithInvalidUser() {
        doThrow(new IllegalArgumentException("Subject Cant Be Found")).when(subjectUseCase).execute(new GetSubjectUseCase.InputValues(inputRequest.subject_id()));

        CreateArticleUseCase.InputValues inputValues = new CreateArticleUseCase.InputValues(inputRequest);

        assertThatThrownBy(() -> articleUseCase.execute(inputValues)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Error while creating new Article, cant be save : ");
    }
}
