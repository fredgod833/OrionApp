import { Component, OnInit } from "@angular/core";
import { FormGroup, FormsModule } from "@angular/forms";
import { MatFormFieldControl, MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { ActivatedRoute, RouterModule } from "@angular/router";
import PostInterface from "../../model/post";
import { UserService } from "../../services/user.service";

@Component({
    selector: 'app-post-selected',
    templateUrl:'./selected.component.html',
    styleUrls: ['./selected.component.scss'],
    standalone: true,
    imports: [FormsModule, MatFormFieldModule, MatInputModule, RouterModule, MatSelectModule],
})
export default class PostSelected implements OnInit{

    public post!:any;
    public comment_text = "";

    constructor(private router:ActivatedRoute, private userService: UserService){}

    ngOnInit(): void {
        this.router.params.subscribe(params => {
         
        this.post = params;
        
        })
    }

    comment(post: PostInterface){
        
    //    return this.userService.commentPost(post).subscribe({
    //         next(value) {
    //             console.log(value);
    //             return "Post commentered !!!";
    //         },
    //     })
    }
}