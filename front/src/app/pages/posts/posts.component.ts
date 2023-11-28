import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, map } from 'rxjs';
import { Post } from 'src/app/interfaces/post.interface';
import { PostsService } from 'src/app/services/post.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss'],
})
export class PostsComponent {
  public posts$: Observable<Post[]>;

  constructor(
    private postsService: PostsService,
    private router: Router
  ) {
    this.posts$ = this.postsService.getPosts();
  }

  public sort(): void {
    this.posts$ = this.posts$.pipe(
      map((posts) =>
        posts.sort((a, b) => {
          return a.createdAt < b.createdAt ? 1 : -1;
        })
      )
    );

  }

  public goToPostNew(): void {
    this.router.navigate(['/posts/new']);
  }
}
