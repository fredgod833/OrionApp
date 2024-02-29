import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '@core/services/auth/auth.service';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { UserInfo } from '@core/types/user.type';
import { WebStorage } from '@lephenix47/webstorage-utility';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  imports: [RouterLink, ReactiveFormsModule, SpinLoaderComponent],
})
export class LoginComponent {
  public cookiesService = inject(CookiesService);

  public router = inject(Router);

  public authService = inject(AuthService);

  public formBuilder = inject(FormBuilder);

  public isLoading = toSignal(this.authService.isLoading$);

  public hasError = toSignal(this.authService.hasError$);

  public userInfo = toSignal(this.authService.userInfo$);

  // Form for login
  public loginForm = this.formBuilder.group({
    identifier: ['', [Validators.required]],
    password: ['', Validators.required],
  });

  onSubmit(event: Event) {
    event.preventDefault();

    this.authService
      .login({
        identifier: this.loginForm.value.identifier as string,
        password: this.loginForm.value.password as string,
      })
      .subscribe((value: UserInfo) => {
        const { username, id, email, token } = value;
        WebStorage.setKey('userInfo', { username, id, email });

        this.cookiesService.setJwt(token);

        setTimeout(() => {
          this.router.navigate(['/articles']);
        }, 3_000);
      });
  }
}
