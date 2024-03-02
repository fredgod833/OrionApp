import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '@core/services/auth/auth.service';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { UserBasicInfo, UserInfo } from '@core/types/user.type';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import {
  bootstrapEyeFill,
  bootstrapEyeSlashFill,
} from '@ng-icons/bootstrap-icons';

import { Store } from '@ngrx/store';
import { setInfo } from '@mdd-global-state-ngrx/actions/user-info.actions';
import { Subscription } from 'rxjs';

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

  private store = inject(Store);

  public timeoutId!: NodeJS.Timeout;

  // * Signals

  public showPassword = signal(false);

  public isLoading = toSignal<boolean>(this.authService.isLoading$);

  public hasError = toSignal<boolean>(this.authService.hasError$);

  public errorMessage = toSignal<string>(this.authService.errorMessage$);

  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  public hasJwt: boolean = false;

  // Form for login
  public loginForm = this.formBuilder.group({
    identifier: ['', [Validators.required]],
    password: ['', Validators.required],
  });

  ngOnDestroy() {
    clearTimeout(this.timeoutId);

    this.authService.isLoading$.next(false);
    this.authService.hasError$.next(false);
  }

  togglePasswordVisibility() {
    this.showPassword.update((oldValue: boolean) => {
      return !oldValue;
    });
  }

  onSubmit(event: Event) {
    event.preventDefault();

    const { identifier, password } = this.loginForm.getRawValue();

    const subscription: Subscription = this.authService
      .login({
        identifier: identifier as string,
        password: password as string,
      })
      // TODO: Unsubscribe from this observable
      .subscribe((value: UserInfo) => {
        const { token, id, email, username } = value;
        // Setting the cookies
        this.cookiesService.setJwt(token as string);

        this.hasJwt = true;

        // Dispatching an action
        this.store.dispatch(setInfo({ id, email, username }));

        subscription.unsubscribe();

        this.timeoutId = setTimeout(() => {
          this.router.navigate(['/articles']);
        }, 3_000);
      });
  }
}
