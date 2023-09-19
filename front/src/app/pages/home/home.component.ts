import {Component, OnInit} from '@angular/core';
import { Router } from "@angular/router";
import {AuthService} from "../../auth/services/auth.service";
import {SessionService} from "../../service/session.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit{
  constructor(
    public router: Router,
    private sessionService: SessionService
  ){}

  ngOnInit() {
    if (this.sessionService.isLogged) {
      this.router.navigateByUrl('topics');
    }
  }
  loginUrl() {
    this.router.navigateByUrl('login');
  }

  registerUrl() {
    this.router.navigateByUrl('register');
  }
}
