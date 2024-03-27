import {PostApiService} from './post-api.service';
import {HttpClientTestingModule, HttpTestingController, TestRequest} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {FormGroup} from '@angular/forms';
import {Post} from '../interfaces/post';

describe('PostApiService', () => {
  let service: PostApiService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PostApiService]
    });
    service = TestBed.inject(PostApiService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send a GET request to fetch all posts', () => {
    const dummyPosts: Post[] = [
      {
        id: 1,
        title: 'Post 1',
        content: 'Content',
        author: 'Joe',
        theme: 'Java',
        createdAt: new Date(),
        updatedAt: new Date()
      },
      {
        id: 2,
        title: 'Post 2',
        content: 'Content',
        author: 'Mike',
        theme: 'Python',
        createdAt: new Date(),
        updatedAt: new Date()
      }
    ];

    service.getAll().subscribe((posts: Post[]) => {
      expect(posts).toEqual(dummyPosts);
    });

    const req: TestRequest = httpTestingController.expectOne('/api/posts');
    expect(req.request.method).toBe('GET');
    req.flush(dummyPosts);
  });

  it('should send a POST request to create a post', () => {
    const dummyPost: Post = {
      id: 1,
      title: 'Created post',
      content: 'Content',
      author: 'Joe',
      theme: 'Java',
      createdAt: new Date(),
      updatedAt: new Date()
    };
    const form: FormGroup = {
      value: {title: 'New Post 1', content: 'New Content 1'}
    } as FormGroup;

    service.create(form).subscribe((post: Post) => {
      expect(post).toEqual(dummyPost);
    });

    const req: TestRequest = httpTestingController.expectOne('/api/posts');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(form.value);
    req.flush(dummyPost);
  });

  it('should send a GET request to fetch a single post by ID', () => {
    const postId: number = 1;
    const dummyPost: Post = {
      id: 1,
      title: 'Post',
      content: 'Content',
      author: 'Joe',
      theme: 'Java',
      createdAt: new Date(),
      updatedAt: new Date()
    };

    service.getById(postId).subscribe((post: Post) => {
      expect(post).toEqual(dummyPost);
    });

    const req: TestRequest = httpTestingController.expectOne(`/api/posts/${postId}`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyPost);
  });
});
