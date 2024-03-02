import {
  Component,
  ElementRef,
  ViewChild,
  inject,
  signal,
} from '@angular/core';
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
import { ArticleCreationValues } from '@core/types/article.type';
import { Message } from '@core/types/message.type';
import { TopicOptions } from '@core/types/topic.type';
import { WebStorage } from '@lephenix47/webstorage-utility';
import { Subscription } from 'rxjs';

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

  public creationFormDefaultValues: ArticleCreationValues | null =
    WebStorage.getKey('article-creation');

  ngOnInit() {
    if (!this.creationFormDefaultValues) {
      this.resetLocalStorage();
    }
  }

  // Form for login
  public createArticleForm = this.formBuilder.group({
    themeId: [
      this.creationFormDefaultValues?.themeId || '1',
      [Validators.required],
    ],
    title: [this.creationFormDefaultValues?.title || '', Validators.required],
    description: [
      this.creationFormDefaultValues?.description || '',
      Validators.required,
    ],
  });

  public setDefaultValueToLocalStorage(event: Event): void {
    const element = event.target as HTMLElement;

    const articleCreationValues =
      WebStorage.getKey<ArticleCreationValues>('article-creation');

    switch (element.tagName.toLowerCase()) {
      case 'select': {
        if ((element as HTMLSelectElement).name === 'topics') {
          articleCreationValues.themeId = (element as HTMLSelectElement).value;
        }
        break;
      }
      case 'input': {
        if ((element as HTMLInputElement).name === 'title') {
          articleCreationValues.title = (element as HTMLInputElement).value;
        }
        break;
      }
      case 'textarea': {
        if ((element as HTMLTextAreaElement).name === 'description') {
          articleCreationValues.description = (
            element as HTMLTextAreaElement
          ).value;
        }
        break;
      }

      default:
        break;
    }

    WebStorage.setKey('article-creation', articleCreationValues);
  }

  public onSubmit(event: Event): void {
    event.preventDefault();

    const { themeId, title, description } =
      this.createArticleForm.getRawValue();

    const normalizedThemeId: number = Number(themeId as string);

    const normalizedBody = {
      title: title as string,
      description: description as string,
    };

    console.log('Form submission', normalizedThemeId, normalizedBody);

    const subscription: Subscription = this.articleService
      .postArticle(normalizedThemeId, normalizedBody)
      .subscribe((value: Message) => {
        this.hasSuccess.update(() => {
          return true;
        });

        this.resetForm();
        this.resetLocalStorage();

        subscription.unsubscribe();
      });
  }

  private resetForm() {
    this.createArticleForm.setValue({
      title: '',
      themeId: '1',
      description: '',
    });
  }

  private resetLocalStorage() {
    WebStorage.setKey('article-creation', {
      themeId: '1',
      title: '',
      description: '',
    });
  }
}
