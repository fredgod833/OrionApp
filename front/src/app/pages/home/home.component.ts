import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent {
  constructor(private router: Router) {}

  public login() {
    this.router.navigate(['login']).then(() => console.log("Navigated to Login page"));
  }

  public register() {
    this.router.navigate(['register']).then(() => console.log("Navigated to Register page"));
  }
}
