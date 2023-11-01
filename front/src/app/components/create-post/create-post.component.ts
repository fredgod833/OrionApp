import { Component, OnInit } from '@angular/core';
import { PostService } from '../../services/post.service';
import { TopicService } from '../../services/topic.service';
import { Post } from '../../interfaces/post.interface';
import { Topic } from '../../interfaces/topic.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent implements OnInit {
  topics: Topic[] = [];
  newPost: any = {
    userId: 1
  };


  constructor(
    private postService: PostService,
    private topicService: TopicService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.topicService.getTopics().subscribe(
      (data: Topic[]) => this.topics = data,
      error => console.error(error)
    );
  }

  createPost(): void {
    if (!this.newPost.topicId || !this.newPost.title || !this.newPost.content) {
      console.error('All fields are mandatory.');
      return;
    }

    this.postService.createPost(this.newPost).subscribe(
      () => {
        this.router.navigate(['/posts']);
      },
      error => console.error('There was an error while creating the post:', error)
    );
  }
}
