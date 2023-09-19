import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {RegisterRequest} from "../../interfaces/registerRequest.interface";
import {AuthSuccess} from "../../interfaces/authSuccess.interface";
import {SessionService} from "../../../service/session.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit{
  public onError = false;

  public form = this.fb.group({
    name: ['', [Validators.required, Validators.min(3)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.min(3)]]
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService
  ) { }

  ngOnInit() {
    if (this.sessionService.isLogged) {
      this.router.navigateByUrl('topics');
    }
  }
  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe(
      {
        next: (response: AuthSuccess) => {
          this.router.navigate(['/login']);
        },
        error: error => this.onError = true,
      }
    );
  }
}
