import { Component, OnInit } from "@angular/core";
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { SubjectService } from "../services/subject.service";
import { SubjectDto } from "../model/subjectdto";
import { NgFor, NgIf } from "@angular/common";
import { UserService } from "../services/user.service";

@Component({
    selector: 'app-subject',
    templateUrl: './subject.component.html',
    styleUrls: ['./subject.component.scss'],
    standalone: true,
    imports: [MatCardModule, MatButtonModule, NgFor, NgIf],
})
export default class Subject implements OnInit{

    subject_list: SubjectDto[] = [];

    //Control of subscription button
    isSubscribed =  false;

    constructor(private pathService: SubjectService, private userService: UserService){}
    
    ngOnInit(){
      this.getSubjectList()
    };

    //Set values to new list of subjects
    getSubjectList(){
      this.pathService.getSubjectList().subscribe(response => {
        for(let i=0; i < response.length; i++){
          //Add subject values to list
          this.subject_list.push(response[i]);
        }
      })
    }

    //User subscription 
    subscribe(idSubject: number){

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
}