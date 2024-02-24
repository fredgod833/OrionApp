import { Component, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { UserComment } from '@core/types/article.type';
import { CommentsComponent } from '@components/common/article/comments/comments.component';

@Component({
  selector: 'app-article',
  standalone: true,
  templateUrl: './article.component.html',
  styleUrl: './article.component.scss',
  imports: [RouterLink, CommentsComponent],
})
export class ArticleComponent {
  public activatedRoute = inject(ActivatedRoute);
  public id = Number(this.activatedRoute.snapshot.params['id']);

  public commentArray: UserComment[] = [
    { username: 'user1', comment: 'This is the first comment.' },
    { username: 'user2', comment: 'Great job on the post!' },
    { username: 'user3', comment: 'I have a question about this topic.' },
  ];
}
