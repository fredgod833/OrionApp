import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { AuthService } from '@core/services/auth/auth.service';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { UserInfo } from '@core/types/user.type';

import { NgIconComponent, provideIcons } from '@ng-icons/core';

import {
  bootstrapEyeFill,
  bootstrapEyeSlashFill,
} from '@ng-icons/bootstrap-icons';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    RouterLink,
    ReactiveFormsModule,
    SpinLoaderComponent,
    NgIconComponent,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
  viewProviders: [provideIcons({ bootstrapEyeFill, bootstrapEyeSlashFill })],
})
export class RegisterComponent {
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

  public registerForm = this.formBuilder.group({
    username: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
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

    console.log(this.registerForm);

    const { username, email, password } = this.registerForm.getRawValue();

    this.authService
      .register({
        username: username as string,
        email: email as string,
        password: password as string,
      })
      // TODO: Unsubscribe from this observable
      .subscribe((value: UserInfo) => {
        const { token } = value;
        this.cookiesService.setJwt(token);

        setTimeout(() => {
          this.router.navigate(['/articles']);
        }, 3_000);
      });
  }
}
