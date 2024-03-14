import {Component} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {LoginRequest} from "../../interfaces/loginRequest.interface";
import {FormBuilder, Validators} from "@angular/forms";
import {SessionService} from "../../../../services/session.service";
import {AuthSuccess} from "../../interfaces/authSuccess.interface";
import {User} from "../../../../interfaces/user.interface";
import {UserService} from "../../../../services/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  public hide = true;
  public onError = false;

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.min(3)]]
  });

  constructor(private authService: AuthService,
              private userService: UserService,
              private fb: FormBuilder,
              private router: Router,
              private sessionService: SessionService) {
  }

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.userService.getUser().subscribe((user: User) => {
          this.sessionService.logIn(user);
          this.router.navigate(['/articles']).then(() => console.log("Redirected to themes page"));
        });
        this.router.navigate(['/articles']).then(() => console.log("Redirected to themes page"));
      },
      error: _ => this.onError = true,
    });
  }

  public back(): void {
    window.history.back();
  }
}
