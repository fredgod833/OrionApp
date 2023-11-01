import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PostService } from '../../services/post.service';
import { TopicService } from '../../services/topic.service';
import { Post } from '../../interfaces/post.interface';
import { Topic } from '../../interfaces/topic.interface';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {
  posts: Post[] = [];
  topics: Topic[] = [];
  users: any[] = []; // Ajout de la propriété users pour stocker tous les utilisateurs

  constructor(
    private router: Router,
    private postService: PostService,
    private topicService: TopicService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
    this.loadPosts();
    this.loadTopics();
  }

  loadUsers(): void {
    this.userService.getUsers().subscribe(
      (data: any[]) => this.users = data,
      error => console.error(error)
    );
  }

  loadPosts(): void {
    this.postService.getPosts().subscribe(
      (data: Post[]) => this.posts = data,
      error => console.error(error)
    );
  }

  loadTopics(): void {
    this.topicService.getTopics().subscribe(
      (data: Topic[]) => this.topics = data,
      error => console.error(error)
    );
  }

  getUsername(userId: number): string {
    const user = this.users.find(u => u.id === userId);
    return user ? user.username : 'Inconnu';
  }

  triggerCreatePostForm(): void {
    this.router.navigate(['/create-post']);
  }

  goToPostDetail(postId: number): void {
    this.router.navigate(['/posts', postId]);
  }
}
