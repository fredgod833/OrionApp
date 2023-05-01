import { Component } from '@angular/core';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './auth-portal.component.html',
  styleUrls: ['./auth-portal.component.scss'],
})
export class AuthPortalComponent {

  constructor(
    private auth: AuthService
  ) {
    
  }
}
