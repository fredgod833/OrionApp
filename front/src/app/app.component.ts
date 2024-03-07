import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SessionService} from "./services/session.service";
import {Observable} from "rxjs";
import {AuthService} from "./features/auth/services/auth.service";
import {User} from "./interfaces/user.interface";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private sessionService: SessionService) {
  }

  public ngOnInit(): void {
    this.autoLog();
  }

  public autoLog(): void {
    this.authService.me().subscribe({
        next: (user: User) => {
          this.sessionService.logIn(user);
        },
        error: (_) => {
          this.sessionService.logOut();
        }
      }
    )
  }
}
