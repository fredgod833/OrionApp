import {Component, OnDestroy} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {RegisterRequest} from "../../interfaces/registerRequest.interface";
import {passwordValidator} from "../../../../util/password-validator";
import {Subject, takeUntil} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {AuthSuccess} from "../../interfaces/authSuccess.interface";
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../interfaces/user.interface";
import {SessionService} from "../../../../services/session.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnDestroy {
  private destroy$: Subject<void> = new Subject<void>();

  public error: string = '';

  public form: FormGroup = this.fb.group({
    username: [
      '',
      [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_]{6,20}$'),
        Validators.minLength(6),
        Validators.maxLength(30)
      ]
    ],
    email: [
      '',
      [
        Validators.required,
        Validators.pattern('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$'),
        Validators.maxLength(60)
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
              private sessionService: SessionService,
              private fb: FormBuilder,
              private router: Router) {
  }

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response: AuthSuccess) => {
          localStorage.setItem('token', response.token);
          this.userService.getUser().subscribe((user: User) => {
            this.sessionService.logIn(user);
            this.router.navigate(['/posts']).then(() => console.log("Redirected to posts page"));
          })
        }, error: (err: HttpErrorResponse) => this.error = err.error.message
      })
  }

  public back(): void {
    window.history.back();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  /* Validation messages */
  public getPasswordError(): string {
    const control: FormControl = this.form.get('password') as FormControl;
    if (control.hasError('required')) {
      return 'Password is required.';
    } else if (control.hasError('minlength')) {
      return `Password must be at least 8 characters long.`;
    } else if (control.hasError('complexity')) {
      const requiredTypes = control.getError('complexity').requiredTypes;
      return `Password must contain: ${requiredTypes.map((type: string) => `- ${type}`).join(', ')}`;
    }

    return "";
  }

  public getEmailError(): string {
    const control: FormControl = this.form.get('email') as FormControl;
    if (control.hasError('required')) {
      return 'Email is required.';
    } else {
      return 'Invalid email format. Please enter a valid email address.';
    }
  }

  public getUsernameError(): string {
    const control: FormControl = this.form.get('username') as FormControl;
    if (control.hasError('required')) {
      return 'Username is required.';
    } else if (control.hasError('minlength')) {
      return `Username must be at least 6 characters long.`;
    } else if (control.hasError('maxlength')) {
      return `Username cannot exceed 20 characters.`;
    } else if (control.hasError('pattern')) {
      return `Username can only contain letters, numbers, and underscores.`;
    }

    return "";
  }
}
