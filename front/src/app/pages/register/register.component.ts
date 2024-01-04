import { NgIf } from "@angular/common";
import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatCardModule } from "@angular/material/card";
import AuthService from "../services/auth.component";
import RegisterRequest from "src/app/security/interfaces/register.component";
import { MatFormFieldModule } from "@angular/material/form-field";
import { Subscription } from "rxjs";
import { Router } from "@angular/router";
import { HttpErrorResponse } from "@angular/common/http";

@Component({
    selector: 'register-page',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss'],
    standalone: true,
    imports:[ReactiveFormsModule, MatCardModule, NgIf,  MatFormFieldModule]
})
export default class Register implements OnInit, OnDestroy{
    public onError = false;
    public form!: FormGroup;
    public message!:string;
    public subscription!: Subscription;

    constructor(private formBuilder: FormBuilder, private authService: AuthService, private router:Router){}

    //Initialization
    ngOnInit():void{
        this.initForm();
    }

    //Register a user as authentified and return a message
    public register():Subscription{
        const register = this.form.value as RegisterRequest;

        this.subscription = this.authService.register(register).subscribe!({
            next:()=> {
                console.log("Apres next")
                this.message='User Registered !!!';

               setTimeout(()=>{
                console.log("Dans setTimeout")
                this.router.navigate(['/login']); 
               }, 2000)
            },
        
            //Error set true for template
            error:(err: HttpErrorResponse)=> {
                this.message = "User already exists";
                console.log("Dans error", err);
                }
        });

        return this.subscription;
    }
    
    //Unsubscribe subscription
    ngOnDestroy():void{
        if(this.subscription){
            this.subscription.unsubscribe();
        }
    }

     //Redirect arrow left
     arrowLeftDirection():void{
        this.router.navigate(['/']);
    }

    isValid(): Boolean{
     return this.form.valid;
    }

    //Request required field for register validation
    public initForm():void{
        //Stock form fields
        this.form = this.formBuilder.group({
            username: ["", [Validators.required, Validators.pattern(/^[a-zA-Z0-9_-]{3,16}$/)]],
            email: ["", [Validators.required, Validators.email]],
            password: ["" ,[Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,}$/)]]
        })
    }
}