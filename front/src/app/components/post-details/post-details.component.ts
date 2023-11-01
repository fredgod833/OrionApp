import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { PostService } from '../../services/post.service';
import { CommentService } from '../../services/comment.service';
import { TopicService } from '../../services/topic.service';
import { Post } from '../../interfaces/post.interface';
import { Comment } from '../../interfaces/comment.interface';
import { Topic } from 'src/app/interfaces/topic.interface';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.scss']
})
export class PostDetailsComponent implements OnInit {
  post?: Post;
  users: User[] = [];
  comments: Comment[] = [];
  newCommentContent: string = '';
  topics: Topic[] = [];

  constructor(
    private route: ActivatedRoute,
    private postsService: PostService,
    private commentService: CommentService,
    private topicService: TopicService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.topicService.getTopics().subscribe(topics => {
      this.topics = topics;
    });

    this.route.params.subscribe((params: Params) => {
      const id = +params['id'];
      this.postsService.getPostById(id).subscribe((post: Post) => {
        this.post = post;

        const topic = this.topics.find(t => t.id === post.topicId);
        if (topic) {
          this.post.topicName = topic.name;
        }

        if (post.userId) {
          this.loadUser(post.userId);
        }

        this.loadComments(id);
      }, error => {
        console.error('There was an error loading the post!', error);
      });
    });
  }

  loadUser(userId: number): void {
      this.userService.getUserById(userId).subscribe(user => {
        if (this.post && user) {
          this.post.username = user.username;
      }

      }, error => {
          console.error('There was an error loading the user!', error);
      });
  }


  loadComments(postId: number): void {
    this.commentService.getComments(postId).subscribe(comments => {
      this.comments = comments;
    }, error => {
      console.error('There was an error loading the comments!', error);
    });
  }

  postComment(): void {
    if (this.post && this.newCommentContent.trim().length > 0) {
      const comment: Comment = {
        content: this.newCommentContent.trim(),
        postId: this.post.id,
        userId: 1,
      };
      this.commentService.createComment(this.post.id, comment).subscribe(newComment => {
        this.comments.push(newComment);
        this.newCommentContent = '';
      }, error => {
        console.error('There was an error posting the comment!', error);
      });
    }
  }

}
