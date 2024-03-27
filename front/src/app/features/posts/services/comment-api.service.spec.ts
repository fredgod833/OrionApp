import {CommentApiService} from './comment-api.service';
import {HttpClientTestingModule, HttpTestingController, TestRequest} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {FormGroup} from '@angular/forms';
import {Comment} from '../interfaces/comment';

describe('CommentApiService', () => {
  let service: CommentApiService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CommentApiService]
    });
    service = TestBed.inject(CommentApiService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send a GET request to fetch comments for a post', () => {
    const postId: number = 1;
    const dummyComments: Comment[] = [
      {content: 'Comment 1', author: 'Joe'},
      {content: 'Comment 2', author: 'Mike'}
    ];

    service.getComments(postId).subscribe((comments: Comment[]) => {
      expect(comments).toEqual(dummyComments);
    });

    const req: TestRequest = httpTestingController.expectOne(`/api/post/${postId}/comments`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyComments);
  });

  it('should send a POST request to save a comment for a post', () => {
    const postId: number = 1;
    const dummyComment: Comment = {content: 'New Comment', author: "Joe"};
    const form: FormGroup = {value: {text: 'New Comment'}} as FormGroup;

    service.saveComment(postId, form).subscribe((comment: Comment) => {
      expect(comment).toEqual(dummyComment);
    });

    const req: TestRequest = httpTestingController.expectOne(`/api/post/${postId}`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(form.value);
    req.flush(dummyComment);
  });
});
