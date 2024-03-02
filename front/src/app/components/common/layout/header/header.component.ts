import {
  Component,
  ElementRef,
  ViewChild,
  effect,
  inject,
} from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import {
  NavigationEnd,
  Router,
  RouterLink,
  RouterLinkActive,
} from '@angular/router';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { matchesCssMediaQuery } from '@utils/helpers/window.helpers';
import { Subscription, filter } from 'rxjs';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  passedAuthentication: boolean = false;
  // * Reference to the navigation bar element.
  @ViewChild('navigationBarRef') navigationBarRef!: ElementRef<HTMLElement>;

  // * Dependency injections
  private router = inject(Router);

  private cookiesService = inject(CookiesService);

  // * Observables
  private routerNavigationEventsSubscription: Subscription = new Subscription();

  ngOnInit() {
    // Subscribe to NavigationEnd events to update authentication status on route changes
    this.routerNavigationEventsSubscription = this.router.events
      .pipe(
        filter(
          (event): event is NavigationEnd => event instanceof NavigationEnd
        )
      )
      .subscribe(() => {
        this.updateAuthenticationStatus();
      });
  }

  ngOnDestroy() {
    this.routerNavigationEventsSubscription.unsubscribe();
  }

  /**
   * Updates the authentication status based on the current route.
   */
  private updateAuthenticationStatus(): void {
    this.passedAuthentication =
      this.router.url !== '/register' &&
      this.router.url !== '/login' &&
      this.cookiesService.getJwt() !== null;
  }

  /**
   * Toggles the visibility of the mobile burger menu sidebar.
   * @param isActive A boolean indicating whether the sidebar should be active.
   */
  toggleMobileBurgerMenuSidebar(isActive: boolean): void {
    const isNotMobile = matchesCssMediaQuery('width > 768px');
    if (isNotMobile || !this.passedAuthentication) {
      return;
    }

    const navigationBar: HTMLElement = this.navigationBarRef.nativeElement;

    navigationBar.classList.toggle('active', isActive);
  }
}
