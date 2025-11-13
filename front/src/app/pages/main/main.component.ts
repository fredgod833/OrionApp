import { Component } from '@angular/core';
import {AuthService} from "../../features/auth/services/auth.service";
import {Router} from "@angular/router";
import {SessionService} from "../../features/auth/services/session.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent {

  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService) {
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  }

}
