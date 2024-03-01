import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { AuthService } from '@core/services/auth/auth.service';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { UserBasicInfo, UserInfo } from '@core/types/user.type';

import { NgIconComponent, provideIcons } from '@ng-icons/core';

import {
  bootstrapEyeFill,
  bootstrapEyeSlashFill,
} from '@ng-icons/bootstrap-icons';

import { Store } from '@ngrx/store';
import { setInfo } from '@mdd-global-state-ngrx/actions/user-info.actions';

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

  private store = inject(Store);

  public timeoutId!: NodeJS.Timeout;

  // * Signals
  public showPassword = signal(false);

  public isLoading = toSignal<boolean>(this.authService.isLoading$);

  public hasError = toSignal<boolean>(this.authService.hasError$);

  public errorMessage = toSignal<string>(this.authService.errorMessage$);

  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  public registerForm = this.formBuilder.group({
    username: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
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
        const { token, id, email, username } = value;
        // Setting the cookies
        this.cookiesService.setJwt(token as string);

        // Dispatching an action
        this.store.dispatch(setInfo({ id, email, username }));

        this.timeoutId = setTimeout(() => {
          this.router.navigate(['/articles']);
        }, 3_000);
      });
  }
}
