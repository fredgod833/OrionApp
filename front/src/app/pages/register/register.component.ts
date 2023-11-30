import { NgIf } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatCardModule } from "@angular/material/card";
import User from "src/app/interfaces/user.interface";
import AuthService from "../services/auth.component";
import RegisterRequest from "src/app/security/interfaces/register.component";
import { MatFormFieldModule } from "@angular/material/form-field";

@Component({
    selector: 'register-page',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss'],
    standalone: true,
    imports:[ReactiveFormsModule, MatCardModule, NgIf,  MatFormFieldModule]
})
export default class Register implements OnInit{
    public onError = false;
    public form!: FormGroup;

    constructor(private formBuilder: FormBuilder, private authService: AuthService){}
    ngOnInit(){
        this.initForm();
    }

    public register(){
        const register = this.form.value as RegisterRequest;
        return this.authService.register(register).subscribe!({
            next(value) {
                console.log("User Registered!!!", value);
            },
        });
    }

    public initForm(){
        this.form = this.formBuilder.group({
            username: ["", [Validators.required]],
            email: ["", [Validators.required]],
            password: ["" ,[Validators.required]]
        })
    }
}