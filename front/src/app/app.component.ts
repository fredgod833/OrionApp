import { Component } from '@angular/core';
import AuthService from './pages/services/auth.component';
import { SessionService } from './services/session.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'front';

  // constructor(
  //   private authService: AuthService,
  //   private sessionService: SessionService
  // ){}

  // public $isLogged(): Observable<boolean>{
  //   return this.sessionService.$isLogged();
  // }
}
