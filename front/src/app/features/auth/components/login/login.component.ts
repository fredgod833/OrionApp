import {Component, OnDestroy} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {LoginRequest} from "../../interfaces/loginRequest.interface";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SessionService} from "../../../../services/session.service";
import {AuthSuccess} from "../../interfaces/authSuccess.interface";
import {User} from "../../../../interfaces/user.interface";
import {UserService} from "../../../../services/user.service";
import {Subject, takeUntil} from "rxjs";
import {passwordValidator} from "../../../../util/password-validator";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnDestroy {
  private destroy$: Subject<void> = new Subject<void>();

  public hide: boolean = true;
  public error: string = '';

  public form: FormGroup = this.fb.group({
    email: [
      '',
      [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(60),
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(120),
        passwordValidator
      ]
    ]
  });

  constructor(private authService: AuthService,
              private userService: UserService,
              private fb: FormBuilder,
              private router: Router,
              private sessionService: SessionService) {
  }

  public submit(): void {
    const loginRequest: LoginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response: AuthSuccess) => {
          localStorage.setItem('token', response.token);
          this.userService.getUser()
            .pipe(takeUntil(this.destroy$))
            .subscribe((user: User) => {
              this.sessionService.logIn(user);
              this.router.navigate(['/posts']).then(() => console.log("Redirected to posts page"));
            });
          this.router.navigate(['/posts']).then(() => console.log("Redirected to posts page"));
        },
        error: (err: HttpErrorResponse) => this.error = err.error.message,
      });
  }

  public back(): void {
    window.history.back();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
