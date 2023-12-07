import { AsyncPipe, NgFor, NgIf } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { MatFormFieldModule } from "@angular/material/form-field";
import {MatSelectModule} from '@angular/material/select';
import { SubjectService } from "../../services/subject.service";
import { PostService } from "../../services/post.service";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import PostCreate from "../../model/post.create";

@Component({
    selector: 'create-post',
    templateUrl: './post.create.html',
    styleUrls: ['./post.create.scss'],
    standalone: true,
    imports: [NgIf, MatFormFieldModule, MatSelectModule, NgFor, AsyncPipe, ReactiveFormsModule, FormsModule]
})
export default class CreatePost implements OnInit{

    public postGroup!: FormGroup;

    //Stock list of subject
    public subjectList$ = this.subjectService.getSubjectList();

    //Form initialization
    ngOnInit(): void {
        this.initForm();
    }
  
    constructor(private subjectService: SubjectService, private postService: PostService, private fb: FormBuilder){
    }

    //Create a post
    createPost(){
        //Loading values
        const postCreate: PostCreate = {
            title: this.postGroup.get('title')?.value,
            date: this.postGroup.get('date')?.value,
            author: this.postGroup.get('author')?.value,
            content: this.postGroup.get('content')?.value,
            comments: this.postGroup.get('comment')?.value
        }
    
        //Subject identity
        const id_subject = this.postGroup.get('idSubject')?.value;
        console.log("POSTCREATE: ", postCreate)
        
        if(postCreate == null){
            return "Body is not correct";
        }
        return this.postService.createPost(postCreate, id_subject).subscribe(
            {
                next() {
                    return "Post created !!!";
                },
            }
        )
        }
    
      //Form required fields for post creation
      initForm(){
        // Stock a group of fields
        this.postGroup = this.fb.group({
            idSubject: [
                [Validators.required]
            ],
            title:[
                '',
                [Validators.required]
            ],
        
            date: [
                 new Date().toISOString()
            ],
        
            author:[
                 ''
            ],
            content: [
                '',
                [Validators.required]
            ],
            comments: [
                '',
            ]
        });

      }
}