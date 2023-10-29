import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PostService } from '../../services/post.service';
import { TopicService } from '../../services/topic.service'; // Correction du chemin d'accès.
import { Post } from '../../interfaces/post.interface';
import { Topic } from '../../interfaces/topic.interface'; // Vous utilisez 'Topic' au lieu de 'Theme'.

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {
  posts: Post[] = [];
  topics: Topic[] = []; // Renommé en 'topics' pour plus de clarté.
  newPost: any = {}; // Vous pouvez aussi créer une interface pour cela.
  showCreatePostForm: boolean = false;

  constructor(
    private router: Router,
    private postService: PostService,
    private topicService: TopicService // Corrigé pour utiliser TopicService.
  ) {}

  ngOnInit(): void {
    this.loadPosts();
    this.loadTopics(); // Cette fonction devrait charger les sujets, pas les thèmes.
  }

  loadPosts(): void {
    this.postService.getPosts().subscribe(
      (data: Post[]) => this.posts = data,
      error => console.error(error)
    );
  }

  loadTopics(): void { // Renommé en 'loadTopics'.
    this.topicService.getTopics().subscribe( // Cette méthode devrait exister dans votre TopicService.
      (data: Topic[]) => this.topics = data,
      error => console.error(error)
    );
  }

  triggerCreatePostForm(): void {
    this.showCreatePostForm = !this.showCreatePostForm; // Cela permet de basculer l'affichage du formulaire.
  }

  createPost(): void {
    // Vérification basique des données du formulaire.
    if (!this.newPost.topic || !this.newPost.title || !this.newPost.content) {
      console.error('All fields are mandatory.');
      return;
    }

    // Vous devrez peut-être adapter ceci en fonction de la méthode 'createPost' dans votre 'PostService'.
    this.postService.createPost(this.newPost).subscribe(
      (response) => {
        this.posts.push(response); // Ajouter le nouveau post à la liste.
        this.showCreatePostForm = false; // Cacher le formulaire.
        this.newPost = {}; // Réinitialiser les données du formulaire.
      },
      error => console.error('There was an error while creating the post:', error)
    );
  }

  goToPostDetail(postId: number): void {
    this.router.navigate(['/posts', postId]);
  }
}
