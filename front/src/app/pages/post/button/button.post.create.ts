import { NgIf } from "@angular/common";
import { Component } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import CreatePost from "../create/post.create";
import { RouterModule } from "@angular/router";

@Component({
    selector: 'button-create-post',
    templateUrl: './button.post.create.html',
    styleUrls: ['./button.post.create.scss'],
    standalone: true,
    imports: [NgIf, MatCardModule, MatButtonModule, CreatePost, RouterModule]
})
export default class ButtonCreation{
    isPostCreationActive = false;

    activePostCreation(){
        this.isPostCreationActive = true;
      }
}