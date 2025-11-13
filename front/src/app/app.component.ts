import {Component} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {distinctUntilChanged, filter, map, Observable} from 'rxjs';
import {SessionService} from './features/auth/services/session.service';
import {MatIconRegistry} from "@angular/material/icon";
import {DomSanitizer} from "@angular/platform-browser";
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  public pageUrl$: Observable<String>;

  constructor(
    private router: Router,
    private sessionService: SessionService,
    private matIconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer) {
    this.pageUrl$ = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map( (e)  => (e as NavigationEnd).url),
      distinctUntilChanged()
    );

    registerLocaleData(localeFr, 'fr');

    /*
    if (sessionStorage.getItem("lang") !== "fr") {
      sessionStorage.setItem("lang", "fr");
      window.location.reload();
    }

     */

    this.matIconRegistry.addSvgIcon(
      "mdd",
      this.domSanitizer.bypassSecurityTrustResourceUrl("../assets/logo_p6.svg")
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
