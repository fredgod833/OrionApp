import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "./auth/services/auth.service";
import {SessionService} from "./service/session.service";
import {User} from "./interfaces/user.interface";
import {Observable} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  public routerLinkHome = "/";
  public routerLinkAccount = "/user/account";
  public routerLinkTopic = "/topics";
  public routerLinkPost = "/posts";

  constructor(
    public router: Router,
    private sessionService: SessionService,
    private authService: AuthService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.autoLog()
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public showHeader(): boolean {
    return this.router.url == '/';
  }

  public autoLog(): void {
    this.authService.me().subscribe({
      next: (user: User) => {
        this.sessionService.logIn(user);
      },
      error: (_) => {
        this.router.navigate(['/'])
        this.sessionService.logOut();
      }
    })
  }
}
