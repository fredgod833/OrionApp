import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '@core/services/auth/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  public authService = inject(AuthService);

  public test = {};

  ngOnInit() {
    const obs = this.authService.login({
      identifier: 'test',
      password: 'test',
    });

    // console.log({ obs });
  }

  onSubmit(event: Event) {
    event.preventDefault();

    this.authService
      .login({
        identifier: 'test@test.com',
        password: 'test!1234',
      })
      .subscribe(() => {
        console.log(this.authService.userInfo);
      });
  }
}
