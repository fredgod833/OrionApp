import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ArticleSummary } from '@core/types/article.type';
import { ArticlesSummaryComponent } from '@components/common/articles/articles-summary/articles-summary.component';
import { BehaviorSubject, Observable, Subject, Subscription } from 'rxjs';
import { Store } from '@ngrx/store';
import { toSignal } from '@angular/core/rxjs-interop';
import { UserBasicInfo, UserEntity, UserInfo } from '@core/types/user.type';
import { ArticleService } from '@core/services/article/article.service';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { UserService } from '@core/services/user/user.service';
import { setInfo } from '@mdd-global-state-ngrx/actions/user-info.actions';

@Component({
  selector: 'app-articles',
  standalone: true,
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss',
  imports: [RouterLink, ArticlesSummaryComponent, SpinLoaderComponent],
})
export class ArticlesComponent {
  private store = inject(Store);

  public userService = inject(UserService);

  public articleService = inject(ArticleService);

  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  public isAscending = signal<boolean>(false);

  public isLoading = toSignal<boolean>(this.articleService.isLoading$);

  public hasError = toSignal<boolean>(this.articleService.hasError$);

  public errorMessage = toSignal<string>(this.articleService.errorMessage$);

  // Array of articles
  public arrOfArticles = signal<{ articles: Array<ArticleSummary> }>({
    articles: [],
  });

  // Create a Subject of type Article array
  public test = new Subscription();

  changeArticlesOrder() {
    this.isAscending.update((previousValue) => {
      return !previousValue;
    });

    const sortedArray = this.arrOfArticles().articles.sort(
      (a: ArticleSummary, b: ArticleSummary) => {
        const difference =
          new Date(a.publicationDate).getTime() -
          new Date(b.publicationDate).getTime();

        return this.isAscending() ? -1 * difference : difference;
      }
    );

    this.arrOfArticles.update(() => {
      return {
        articles: sortedArray,
      };
    });
  }

  ngOnInit() {
    console.log(this.userInfo());

    const subscription: Subscription = this.articleService
      .getAllArticles()
      .subscribe((articlesArray) => {
        this.arrOfArticles.update(() => {
          return articlesArray;
        });

        subscription.unsubscribe();
      });
  }

  ngOnDestroy() {
    this.test.unsubscribe();
  }
}
