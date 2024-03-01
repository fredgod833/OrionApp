import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { RouterLink } from '@angular/router';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { ArticleService } from '@core/services/article/article.service';
import { Message } from '@core/types/message.type';
import { TopicOptions } from '@core/types/topic.type';

@Component({
  selector: 'app-create-article',
  standalone: true,
  imports: [RouterLink, SpinLoaderComponent, ReactiveFormsModule],
  templateUrl: './create-article.component.html',
  styleUrl: './create-article.component.scss',
})
export class CreateArticleComponent {
  private formBuilder = inject(FormBuilder);

  private articleService = inject(ArticleService);

  public isLoading = toSignal<boolean>(this.articleService.isLoading$);

  public hasError = toSignal<boolean>(this.articleService.hasError$);

  public errorMessage = toSignal<string>(this.articleService.errorMessage$);

  public hasSuccess = signal<boolean>(false);

  public themesArray: Array<TopicOptions> = [
    { id: 1, theme: 'Python' },
    { id: 2, theme: 'TypeScript' },
    { id: 3, theme: 'Java' },
    { id: 4, theme: 'SEO' },
    { id: 5, theme: 'SASS' },
    { id: 6, theme: 'C++' },
  ];

  // Form for login
  public createArticleForm = this.formBuilder.group({
    themeId: ['', [Validators.required]],
    title: ['', Validators.required],
    description: ['', Validators.required],
  });

  onSubmit(event: Event) {
    event.preventDefault();

    const { themeId, title, description } =
      this.createArticleForm.getRawValue();

    const normalizedThemeId: number = Number(themeId as string);

    const normalizedBody = {
      title: title as string,
      description: description as string,
    };

    console.log('Form submission', normalizedThemeId, normalizedBody);

    this.articleService
      .postArticle(normalizedThemeId, normalizedBody)
      // TODO: Unsubscribe from this observable
      .subscribe((value: Message) => {
        this.hasSuccess.update(() => {
          return true;
        });
      });
  }
}
