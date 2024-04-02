import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, takeUntil} from "rxjs";
import {ThemeApiService} from "../../services/theme-api.service";
import {User} from "../../../../interfaces/user.interface";
import {UserService} from "../../../../services/user.service";
import {Theme} from "../../interfaces/theme";

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit, OnDestroy {
  private destroy$: Subject<void> = new Subject<void>();

  public isLoading: boolean = true;
  public themes!: Theme[];
  public user!: User;

  constructor(private themeApiService: ThemeApiService,
              private userService: UserService) {
  }

  public ngOnInit(): void {
    this.loadData();
  }

  private loadData(): void {
    this.themeApiService.all().subscribe((themes: Theme[]) => {
        this.isLoading = false;
        this.themes = themes;
      }
    );
    this.userService.getUser()
      .pipe(takeUntil(this.destroy$))
      .subscribe((user: User) => {
        this.user = user;
      });
  }

  public isSubscribed(id: number): boolean {
    return this.user?.subscriptions.some((theme: Theme) => theme.id === id);
  }

  public toggleSubscription(themeId: number): void {
    (this.isSubscribed(themeId) ? this.userService.unSubscribe(themeId) : this.userService.subscribe(themeId))
      .pipe(takeUntil(this.destroy$))
      .subscribe((user: User) => {
        this.user = user;
      });
  }

  public getButtonText(id: number): string {
    return this.isSubscribed(id) ? 'Se désabonner' : "S'abonner";
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
