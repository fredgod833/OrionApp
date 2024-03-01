import { Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { RouterLink } from '@angular/router';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { ArticleService } from '@core/services/article/article.service';
import { TopicOptions } from '@core/types/topic.type';

@Component({
  selector: 'app-create-article',
  standalone: true,
  imports: [RouterLink, SpinLoaderComponent],
  templateUrl: './create-article.component.html',
  styleUrl: './create-article.component.scss',
})
export class CreateArticleComponent {
  private articleService = inject(ArticleService);

  public isLoading = toSignal<boolean>(this.articleService.isLoading$);

  public hasError = toSignal<boolean>(this.articleService.hasError$);

  public errorMessage = toSignal<string>(this.articleService.errorMessage$);

  public themesArray: Array<TopicOptions> = [
    { id: 1, theme: 'Python' },
    { id: 2, theme: 'TypeScript' },
    { id: 3, theme: 'Java' },
    { id: 4, theme: 'SEO' },
    { id: 5, theme: 'SASS' },
    { id: 6, theme: 'C++' },
  ];
}
