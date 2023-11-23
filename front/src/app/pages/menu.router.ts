import { Route, RouterModule } from "@angular/router";
import Subject from "./subject/subject.component";
import menuBar from "../components/menu.component";
import Post from "./post/post.component";
import { LoginComponent } from "./login/login.component";
import { NgModule } from "@angular/core";
import PostSelected from "./post/comments/selected.component";
import CreatePost from "./post/create/post.create";

const MENU_ROUTER:Route[] = [
    // {title: 'Subject', path: 'subject', component: Subject},
    // {path: 'menu', component: menuBar},
    // {title: 'Post', path:'post', component: Post},
    {title: 'comments', path: 'comments', component: PostSelected},
    // {path: 'create-post', component: CreatePost},

];

@NgModule({
    imports: [RouterModule.forChild(MENU_ROUTER)],
    exports: [RouterModule]
})
export class MenuRoutingModule {}