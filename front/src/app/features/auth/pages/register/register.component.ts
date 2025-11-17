import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import {SessionInformation} from "../../../../interfaces/sessionInformation.interface";
import {SessionService} from "../../services/session.service";

import {take} from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  public hide = true;

  public onError = false;

  public errorMessage? : string;

  public form = this.fb.group({
    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],
    login: [
      '',
      [
        Validators.required,
        Validators.min(3),
        Validators.max(20)
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.min(3),
        Validators.max(40)
      ]
    ]
  });

  constructor(private authService: AuthService,
              private sessionService: SessionService,
              private fb: FormBuilder,
              private router: Router) {
  }

  public submit(): void {

    const registerRequest:RegisterRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest)
      .pipe(take(1))
      .subscribe({
      next: (response: SessionInformation) => {
        this.onError = false;
        //this.sessionService.logIn(response);
        //this.router.navigate(["/me"]);
        this.sessionService.logOut();
        this.router.navigateByUrl("/");
      },

      error: error => {
        this.onError = true;
        if (error.error instanceof Object && error.error.hasOwnProperty("message")) {
          this.errorMessage = error.error.message;
        } else {
          this.errorMessage = "une erreur est survenue.";
        }
      },

    });

  }

}
