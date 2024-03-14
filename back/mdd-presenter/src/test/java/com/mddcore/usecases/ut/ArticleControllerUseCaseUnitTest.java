package com.mddcore.usecases.ut;

import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.article.CreateArticleUseCase;
import com.mddcore.usecases.article.GetAllArticleUseCase;
import com.mddcore.usecases.article.GetArticleUseCase;
import com.mddinfrastructure.article.ArticleController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArticleControllerUseCaseUnitTest {
    @InjectMocks
    private ArticleController articleController;
    @Mock
    private UseCaseExecutor useCaseExecutor;
    @Mock
    private CreateArticleUseCase createArticleUseCase;
    @Mock
    private GetAllArticleUseCase getAllArticleUseCase;
    @Mock
    private GetArticleUseCase getArticleUseCase;
}
