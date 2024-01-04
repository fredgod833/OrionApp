import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { SubjectDto } from "../model/subjectdto";
import { Observable } from "rxjs";
import Subject from "../model/subject";

@Injectable({
    providedIn: 'root'
})
export class SubjectService{
    
    private pathService = 'api/subject';

    constructor(private httpClient: HttpClient){};

    //Return list of subjectDto
    public getSubjectDtoList():Observable<SubjectDto[]>{

       return this.httpClient.get<SubjectDto[]>(`${this.pathService}/subjectDto_list`);   
    }

    //Return a list of subject
    public getSubjectList():Observable<Subject[]>{
        const result = this.httpClient.get<Subject[]>(`${this.pathService}/subject_list`)
        result.subscribe({next(value) {
            console.log("RESULT",value);

        },})
        return result;
    }

}