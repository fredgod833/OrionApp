import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '@core/services/auth/auth.service';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { UserInfo } from '@core/types/user.type';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import {
  bootstrapEyeFill,
  bootstrapEyeSlashFill,
} from '@ng-icons/bootstrap-icons';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  imports: [
    RouterLink,
    ReactiveFormsModule,
    SpinLoaderComponent,
    NgIconComponent,
  ],
  viewProviders: [provideIcons({ bootstrapEyeFill, bootstrapEyeSlashFill })],
})
export class LoginComponent {
  // * Services
  public cookiesService = inject(CookiesService);

  public router = inject(Router);

  public authService = inject(AuthService);

  public formBuilder = inject(FormBuilder);

  public timeoutId!: NodeJS.Timeout;

  // * Signals

  public showPassword = signal(false);

  public isLoading = toSignal(this.authService.isLoading$);

  public hasError = toSignal(this.authService.hasError$);

  public errorMessage = toSignal(this.authService.errorMessage$);

  public userInfo = toSignal(this.authService.userInfo$);

  // Form for login
  public loginForm = this.formBuilder.group({
    identifier: ['', [Validators.required]],
    password: ['', Validators.required],
  });

  ngOnDestroy() {
    clearTimeout(this.timeoutId);
  }

  togglePasswordVisibility() {
    this.showPassword.update((oldValue: boolean) => {
      return !oldValue;
    });
  }

  onSubmit(event: Event) {
    event.preventDefault();

    const { identifier, password } = this.loginForm.getRawValue();

    this.authService
      .login({
        identifier: identifier as string,
        password: password as string,
      })
      // TODO: Unsubscribe from this observable
      .subscribe((value: UserInfo) => {
        const { token } = value;
        this.cookiesService.setJwt(token);

        this.timeoutId = setTimeout(() => {
          this.router.navigate(['/articles']);
        }, 3_000);
      });
  }
}
