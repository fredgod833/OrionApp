import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Post } from 'src/app/interfaces/post.interface';
import { Topic } from 'src/app/interfaces/topic.interface';
import { PostsService } from 'src/app/services/post.service';
import { TopicsService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent {
  public hide = true;
  public onError = false;

  public form;
  public topics$;

  constructor(
    private postService: PostsService,
    private topicService: TopicsService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.form = this.fb.group({
      title: ['', [Validators.required, Validators.min(3), Validators.max(50)]],
      content: [
        '',
        [Validators.required, Validators.min(3), Validators.max(500)],
      ],
      topic: [0, [Validators.required]],
    });
    this.topics$ = this.topicService.getTopics();
  }

  public submit(): void {
    const postRequest: Partial<Post> = {
      title: this.form.value.title as string | undefined,
      content: this.form.value.content as string | undefined,
      topic: {
        id: this.form.value.topic as number,
      },
    };
    this.postService.createPost(postRequest).subscribe({
      next: (response: Post) => {
        this.router.navigate(['/posts']);
      },
      error: (_) => (this.onError = true),
    });
  }
  public back(): void {
    this.router.navigate(['/posts']);
  }
}
