import { Component, OnInit } from "@angular/core";
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { SubjectService } from "../services/subject.service";
import { SubjectDto } from "../model/subjectdto";
import { NgFor } from "@angular/common";

@Component({
    selector: 'app-subject',
    templateUrl: './subject.component.html',
    styleUrls: ['./subject.component.scss'],
    standalone: true,
    imports: [MatCardModule, MatButtonModule, NgFor],
})
export default class Subject implements OnInit{

    subject_list: SubjectDto[] = [];
    
    constructor(private pathService: SubjectService){}
    ngOnInit(){
      this.getSubjectList()
    };

    getSubjectList(){
      this.pathService.getSubjectList().subscribe(response => {
        for(let i=0; i < response.length; i++){
          console.log("BOUCLE: ", response[i]);

          this.subject_list.push(response[i]);
        }
      })

    }
}