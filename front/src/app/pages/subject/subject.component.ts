import { Component, OnInit } from "@angular/core";
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { SubjectService } from "../services/subject.service";
import { SubjectDto } from "../model/subjectdto";
import { NgFor, NgIf } from "@angular/common";
import { UserService } from "../services/user.service";
import { Subscription } from "rxjs";
import { Router } from "@angular/router";
import menuBar from "src/app/components/menu.component";

@Component({
    selector: 'app-subject',
    templateUrl: './subject.component.html',
    styleUrls: ['./subject.component.scss'],
    standalone: true,
    imports: [MatCardModule, MatButtonModule, NgFor, NgIf, menuBar],
})
export default class Subject implements OnInit{

    //Initalise list of subjects
    subject_list: SubjectDto[] = [];

    //Property to stock subscription
    subscriptionOfSubjectList!: Subscription;
    subscriptionOfUserService!:Subscription;

    //Control of subscription button
    isSubscribed =  false;

    constructor(private pathService: SubjectService, private userService: UserService, private router: Router){}
    
    ngOnInit():void{
      this.getSubjectList()
    };

    //Set values to new list of subjects
    getSubjectList():void{
      this.pathService.getSubjectList().subscribe(response => {
        for(let i=0; i < response.length; i++){
          //Add subject values to list
          this.subject_list.push(response[i]);
        }
      })
    }

    //User subscription 
    subscribe(idSubject: number):void{

      if(idSubject != 0){
       // Subscribe subject
       this.userService.subscribe(idSubject);
        
       //Loop to find the right subject
       this.subject_list.map(val => {

        //Set subscribed to true to the right subject
        if(idSubject == val.idSubject){
         val.isSubscribed = true;
        }
      })
      }
    }
    //Unsubscribe subscriptions
    ngOnDestroy():void{
      if(this.subscriptionOfSubjectList){
        this.subscriptionOfSubjectList.unsubscribe();
      }

      if(this.subscriptionOfUserService){
        this.subscriptionOfUserService.unsubscribe();
      }
    }
      //Redirect menu for navigation
      navigateMenu():void{
        this.router.navigate(['menu'])
    }
}