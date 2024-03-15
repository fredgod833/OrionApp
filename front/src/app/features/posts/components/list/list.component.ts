import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, takeUntil} from "rxjs";
import {PostApiService} from "../../services/post-api.service";
import {Router} from "@angular/router";
import {Post} from "../../interfaces/post";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit, OnDestroy {
  private destroy$: Subject<void> = new Subject<void>();

  public posts!: Post[];
  public arrowIcon: string = 'arrow_downward';
  public isAscending: boolean = true;
  public isLoading: boolean = true;

  constructor(private postApiService: PostApiService, private router: Router) {
  }

  ngOnInit(): void {
    this.postApiService.getPosts()
      .pipe(takeUntil(this.destroy$))
      .subscribe((posts: Post[]) => {
        this.posts = posts;
        this.isLoading = false;
      });
  }

  public sortPosts(): void {
    this.isAscending = !this.isAscending;
    this.arrowIcon = this.isAscending ? 'arrow_downward' : 'arrow_upward';
    this.sortPostsByDate();
  }

  private sortPostsByDate(): void {
    this.posts.sort((a, b) => {
      const dateA = new Date(a.createdAt);
      const dateB = new Date(b.createdAt);

      if (this.isAscending) {
        return dateA.getTime() - dateB.getTime(); // Sort ascending
      } else {
        return dateB.getTime() - dateA.getTime(); // Sort descending
      }
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
