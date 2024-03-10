import {Component, OnDestroy, OnInit} from '@angular/core';
import {Observable, Subject, takeUntil} from "rxjs";
import {ThemeApiService} from "../../services/theme-api.service";
import {ThemesResponse} from "../../interfaces/api/themesResponse";
import {User} from "../../../../interfaces/user.interface";
import {UserService} from "../../../../services/user.service";
import {Theme} from "../../interfaces/theme";

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
  // changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ThemesComponent implements OnInit, OnDestroy {
  public themes$!: Observable<ThemesResponse>;
  public user!: User;

  private destroy$: Subject<void> = new Subject<void>();

  constructor(private themeApiService: ThemeApiService,
              private userService: UserService) {
  }

  public ngOnInit(): void {
    this.loadData();
  }

  private loadData(): void {
    this.themes$ = this.themeApiService.all();
    this.userService.getUser()
      .pipe(takeUntil(this.destroy$))
      .subscribe((user: User) => {
        this.user = user
      });
  }

  public isSubscribed(id: number): boolean {
    return this.user.subscriptions.some((theme: Theme) => theme.id === id);
  }

  public toggleSubscription(themeId: number): void {
    (this.isSubscribed(themeId) ? this.userService.unFollow(themeId) : this.userService.follow(themeId))
      .pipe(takeUntil(this.destroy$))
      .subscribe((user: User) => {
        this.user = user;
      });
  }

  public getButtonText(id: number): string {
    console.log("called with " + id);
    return this.isSubscribed(id) ? 'Se désabonner' : "S'abonner";
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
