import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ArticleSummary } from '@core/types/article.type';
import { ArticlesSummaryComponent } from '@components/common/articles/articles-summary/articles-summary.component';
import { BehaviorSubject, Observable, Subject, Subscription } from 'rxjs';
import { Store } from '@ngrx/store';
import { toSignal } from '@angular/core/rxjs-interop';
import { UserBasicInfo, UserInfo } from '@core/types/user.type';
import { ArticleService } from '@core/services/article/article.service';

@Component({
  selector: 'app-articles',
  standalone: true,
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss',
  imports: [RouterLink, ArticlesSummaryComponent],
})
export class ArticlesComponent {
  private store = inject(Store);

  public articleService = inject(ArticleService);

  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  public isAscending: boolean = true;

  public isLoading = toSignal<boolean>(this.articleService.isLoading$);

  public hasError = toSignal<boolean>(this.articleService.hasError$);

  public errorMessage = toSignal<string>(this.articleService.errorMessage$);

  // Array of articles
  public arrOfArticles = signal<Array<ArticleSummary>>([]);

  // Create a Subject of type Article array
  public test = new Subscription();

  ngOnInit() {
    console.log(this.userInfo());

    this.test = this.articleService
      .getAllArticles()
      .subscribe((articlesArray) => {
        this.arrOfArticles.update(() => {
          return articlesArray;
        });
      });
  }
}
