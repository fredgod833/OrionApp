import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import PostInterface from "../model/post";
import { Token } from "@angular/compiler";

@Injectable({
    providedIn: 'root'
})
export default class postService{

    private pathService = 'api/post';
    private token!: Token;

    constructor(private httClient: HttpClient){};

    public getPostList():Observable<PostInterface[]>{

         return this.httClient.get<PostInterface[]>(`${this.pathService}/post_list`);
        }
}