import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {RegisterRequest} from "../../interfaces/registerRequest.interface";
import {LoginRequest} from "../../interfaces/loginRequest.interface";

@Component({
  selector: 'app-register',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.scss']
})
export class RegisterComponent  {

  public onError = false;
  public form = this.fb.group ({ email: ['', [Validators.required, Validators.email]],
  username: ['', [Validators.required, Validators.min(3), Validators.maxLength(20)]],
  password: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(40)]],
});

  constructor(private authService: AuthService,
              private fb: FormBuilder,
              private router: Router) { }

// ngOnInit() {
//   this.form = this.fb.group({
//     email: ['', [Validators.required, Validators.email]],
//     username: ['', [Validators.required, Validators.min(3), Validators.maxLength(20)]],
//     password: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(40)]],
//   });
// }

  public submit() {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe({
        next: (response) => {
          const loginRequest: LoginRequest = {
            email: response.email,
            password: response.password
          }
          this.login(loginRequest)
        },
        error: _ => this.onError = true,
    }
    );
  }

  public login(loginRequest : LoginRequest) {
    this.authService.login(loginRequest).subscribe( {
      next:(_) => this.router.navigate(['/article']),
      error: _ => this.onError = true
    })
  }
}
