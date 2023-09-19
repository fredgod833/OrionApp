import {Component, OnInit} from '@angular/core';
import { Router } from "@angular/router";
import { FormBuilder, Validators } from "@angular/forms";
import { AuthService } from "../../services/auth.service";
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { AuthSuccess } from "../../interfaces/authSuccess.interface";
import {User} from "../../../interfaces/user.interface";
import {SessionService} from "../../../service/session.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{
  public hide = true;
  public onError = false;

  public form = this.formBuilder.group({
    email: [
      '', [ Validators.required, Validators.email ]
    ],
    password: [
      '', [ Validators.required, Validators.min(3) ]
    ]
  });

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router,
    private sessionService: SessionService
  ) { }

  ngOnInit() {
    if (this.sessionService.isLogged) {
      this.router.navigateByUrl('topics');
    }
  }
  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;

    this.authService.login(loginRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem('token', response.response);
        this.authService.me().subscribe((user: User) => {
          this.sessionService.logIn(user);
          this.router.navigate(['posts'])
        });
      },
      error: error => this.onError = true,
    });
  }
}
