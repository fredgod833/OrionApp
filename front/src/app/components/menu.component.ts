import { Component } from "@angular/core";
import { Router, RouterModule } from "@angular/router";
import { SessionService } from "../services/session.service";
import Subject from "../pages/subject/subject.component";
import Post from "../pages/post/post.component";
import { NgIf } from "@angular/common";

@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls:['./menu.component.scss'],
    standalone:true,
    imports: [RouterModule, Subject, Post, NgIf],
  })

export default class menuBar{

  isPostLinkActive = false;
  isSubjectLinkActive = false;

  constructor(private sessionService: SessionService, private router: Router){

  }

  activePostLink(){
    this.sessionService.isLogged;
    this.isPostLinkActive = true;
    this.isSubjectLinkActive = false;

  }
  activeSubjectLink(){
    this.isSubjectLinkActive = true;
    this.isPostLinkActive = false;
  }
}