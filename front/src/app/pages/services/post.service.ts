import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import PostInterface from "../model/post.component";

@Injectable({
    providedIn: 'root'
})
export default class postService{

    private pathService = 'api/post'

    constructor(private httClient: HttpClient){};

    public getPostList(){

        const response: Observable<PostInterface[]> = this.httClient.get<PostInterface[]>(`${this.pathService}/post_list`);
       
        console.log("Response", response);

        return response;
    }
}