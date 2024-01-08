import { Component, OnDestroy, OnInit } from "@angular/core";
import { MatCardModule } from "@angular/material/card";
import { MatFormFieldModule } from "@angular/material/form-field";
import AuthService from "../services/auth.component";
import User from "src/app/interfaces/user.interface";
import { SubjectDto } from "../model/subjectdto";
import { NgFor } from "@angular/common";
import { UserService } from "../services/user.service";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import menuBar from "src/app/components/menu.component";

@Component({
    selector: 'user-info',
    templateUrl: './user.information.html',
    styleUrls: ['./user.information.scss'],
    standalone: true,
    imports: [MatFormFieldModule, MatCardModule, NgFor, ReactiveFormsModule, menuBar]
})
export default class UserInformation implements OnInit, OnDestroy{
    public subjectList: SubjectDto[] = [];
    public form_profil!: FormGroup;
    
    //Properties to stock subscriptions
    public subscriptionOfMeForProfil!:Subscription;
    public subscriptionOfMeForUser!:Subscription;
    public subscriptionOfLogOut!:Subscription;
    
    constructor(private formBuilder:FormBuilder, private authService: AuthService, private userService: UserService, 
    private router: Router){}
    
    //Initialization
    ngOnInit(): void {
        this.initFormProfil();
        this.userProfil();
    }

    //Unsubscribe
    unsubscribe(idSubject:SubjectDto):void{
         this.userService.unsubscribe(idSubject);
    }

    //Push subscribed subjects for list of subjects
    public userProfil():Subscription{

        this.subscriptionOfMeForProfil = this.authService.me().subscribe({
            next:(value)=> {

                if(value.subscription != null && value.subscription.subjectList.length > 0){
                    console.log("List",this.subjectList)

                    value.subscription.subjectList.map(subscribed => {
                        this.subjectList.push(subscribed);
                    });
                }
            },
        })
        return this.subscriptionOfMeForProfil;
    }

    //Change username, email and return a message
    public changeUserUsernameAndEmail():Subscription{
        console.log(this.form_profil.value)
        this.authService.me().subscribe({next:(value)=> {

            const usernameAndEmail  = this.form_profil.value as User;
            value.username = usernameAndEmail.username;
            value.email = usernameAndEmail.email;

            this.subscriptionOfMeForUser = this.userService.changeUserUsernameAndEmail(value).subscribe({
                next(){
                    console.log("Username and Email changed: ", value);
                    return "Username and Email changed: ";
                }
            })
        },});
        return this.subscriptionOfMeForUser;
    }

    //Log user out
    public logOut():void{
        this.authService.logout().subscribe({
            next:()=> {
                console.log("Logout sucessfull");
                localStorage.clear();
                this.router.navigate(['/']);
            },
        });
    }

    //Unsubscribe subscriptions
    ngOnDestroy():void{
        if(this.subscriptionOfLogOut){
            this.subscriptionOfLogOut.unsubscribe();
        }
        if(this.subscriptionOfMeForProfil){
            this.subscriptionOfMeForProfil.unsubscribe();
        }
        if(this.subscriptionOfMeForUser){
            this.subscriptionOfMeForUser.unsubscribe();
        }
    }
    //Redirect menu for navigation
    navigateMenu():void{
        this.router.navigate(['menu'])
    }

    //Form required fields
    public initFormProfil():void{
        this.form_profil = this.formBuilder.group({
            username: ["", [Validators.required]],
            email: ["", [Validators.required]]
        })
    }
}