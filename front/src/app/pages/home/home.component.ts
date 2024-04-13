import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {SessionService} from "../../services/session.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  constructor(private router: Router, private session: SessionService) {}

  public login() {
    this.router.navigate(['login']).then(() => console.log("Navigated to Login page"));
  }

  public register() {
    this.router.navigate(['register']).then(() => console.log("Navigated to Register page"));
  }

  ngOnInit(): void {
    if (this.session.isLogged) {
      this.router.navigate(['/posts']).then(() => console.log("Navigated to Posts page"));
    }
  }
}
