import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ArticleSummary } from '@core/types/article.type';
import { ArticlesSummaryComponent } from '@components/common/articles/articles-summary/articles-summary.component';
import { BehaviorSubject, Subject } from 'rxjs';
import { Store } from '@ngrx/store';
import { toSignal } from '@angular/core/rxjs-interop';
import { UserBasicInfo, UserInfo } from '@core/types/user.type';

@Component({
  selector: 'app-articles',
  standalone: true,
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss',
  imports: [RouterLink, ArticlesSummaryComponent],
})
export class ArticlesComponent {
  private store = inject(Store);

  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  public isAscending: boolean = true;

  // Array of articles
  public arrOfArticles: Array<ArticleSummary> = [
    {
      id: 1,
      title: 'Article 1',
      date: '2024-02-23',
      username: 'user1',
      description:
        'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
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
  public test = new BehaviorSubject<ArticleSummary[]>(this.arrOfArticles)
    .asObservable()
    .subscribe();

  ngOnInit() {
    console.log(this.userInfo());
  }
}
