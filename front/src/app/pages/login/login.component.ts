import {Component} from '@angular/core';
import {
  Validators,
  FormBuilder,
  ReactiveFormsModule,
} from '@angular/forms';
import {NgIf} from '@angular/common';
import { MatFormFieldModule} from '@angular/material/form-field';
import AuthService from '../services/auth.component';
import { MatButtonModule } from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import LoginRequest from 'src/app/security/interfaces/login.component';
import { Token } from 'src/app/security/interfaces/token.component';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';


@Component({
  selector: 'login-app',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [ MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatToolbarModule,
    MatInputModule, 
    NgIf,  
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule]
})
export class LoginComponent {

  public onError = false;

  // Login form fields required
  public form =  this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });

  constructor(public formBuilder: FormBuilder, private router:Router, 
    private authService: AuthService){}

// Users authentication 
public login():void{
    const loginRequest = this.form.value as LoginRequest;

    this.authService.login(loginRequest).subscribe({
       next:(response: Token) => {
    
          localStorage.setItem('token', response.token );

          this.authService.me().subscribe(
            ()=>{
              this.router.navigate(['/menu'])
            })
          this.router.navigate(['/menu']);

       },
       error: () => {this.onError = true; }
});
       
  }
}
