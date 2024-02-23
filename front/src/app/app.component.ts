import { Component, inject } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/common/header/header.component';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  imports: [RouterOutlet, HeaderComponent],
})
export class AppComponent {
  title = 'mdd-app';

  showHeader = true;

  // * Dependency injections
  public router = inject(Router);

  // * Observables
  private navigationEndEvents$ = this.router.events.pipe(
    filter((event): event is NavigationEnd => event instanceof NavigationEnd)
  );

  ngOnInit() {
    this.navigationEndEvents$.subscribe(() => {
      this.showHeader = this.router.url !== '/home';
    });
  }
}
