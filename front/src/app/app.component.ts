import {Component} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {distinctUntilChanged, filter, map, Observable} from 'rxjs';
import {SessionService} from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  public pageUrl$: Observable<String>;

  constructor(
    private router: Router,
    private sessionService: SessionService) {
    this.pageUrl$ = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map( (e)  => (e as NavigationEnd).url),
      distinctUntilChanged()
    );
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }




  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }
}
