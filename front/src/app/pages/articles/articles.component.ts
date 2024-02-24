import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Article } from '@core/types/article.type';
import { ArticlesSummaryComponent } from '@components/common/articles/articles-summary/articles-summary.component';
import { BehaviorSubject, Subject } from 'rxjs';

@Component({
  selector: 'app-articles',
  standalone: true,
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss',
  imports: [RouterLink, ArticlesSummaryComponent],
})
export class ArticlesComponent {
  public isAscending: boolean = true;

  // Array of articles
  public arrOfArticles: Array<Article> = [
    {
      id: 1,
      title: 'Article 1',
      date: '2024-02-23',
      username: 'user1',
      description: 'Description of article 1',
    },
    {
      id: 2,
      title: 'Article 2',
      date: '2024-02-24',
      username: 'user2',
      description: 'Description of article 2',
    },
  ];

  // Create a Subject of type Article array
  public test = new BehaviorSubject<Article[]>(this.arrOfArticles)
    .asObservable()
    .subscribe();
}
