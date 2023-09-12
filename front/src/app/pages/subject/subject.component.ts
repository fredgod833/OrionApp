import { Component, OnInit } from "@angular/core";
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { SubjectService } from "../services/SubjectService";

@Component({
    selector: 'app-subject',
    templateUrl: './subject.component.html',
    styleUrls: ['./subject.component.scss'],
    standalone: true,
    imports: [MatCardModule, MatButtonModule],
})
export class Subject implements OnInit{

    longText!:any;

    constructor(private pathService: SubjectService){}
    ngOnInit(){
      this.getSubjectList()
    };

    getSubjectList(){
      this.pathService.getSubjectList().subscribe((response) => {
        response.map(val => this.longText = val.description)
      })
    }
      
    
}