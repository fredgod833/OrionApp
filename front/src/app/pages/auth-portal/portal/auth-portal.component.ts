import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './auth-portal.component.html',
  styleUrls: ['./auth-portal.component.scss'],
})
export class AuthPortalComponent {

  constructor(
    auth: AuthService,
    router: Router
  ) {
    auth.tryAutoLogin().then(logged => {
      if(!logged){ return; }
      router.navigateByUrl("/home");
    });
  }
}
