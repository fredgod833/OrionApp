import {ComponentFixture, TestBed} from '@angular/core/testing';
import {DetailComponent} from './detail.component';
import {ReactiveFormsModule} from '@angular/forms';
import {of, throwError} from 'rxjs';
import {CommentApiService} from "../../services/comment-api.service";
import {PostApiService} from "../../services/post-api.service";
import {Post} from "../../interfaces/post";
import {Comment} from "../../interfaces/comment";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let commentApiService: CommentApiService;
  let postApiService: PostApiService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DetailComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule],
      providers: [{
        provide: ActivatedRoute,
        useValue: {
          snapshot: {
            params: {id: 1}
          }
        }
      }],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    commentApiService = TestBed.inject(CommentApiService);
    postApiService = TestBed.inject(PostApiService);

    jest.spyOn(postApiService, 'getById').mockReturnValue(of({
      id: 1,
      title: 'Title',
      content: 'Content',
      author: 'Joe',
      theme: 'Java',
      createdAt: new Date(),
      updatedAt: new Date()
    }));

    fixture.detectChanges();
  })
  ;

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize post and comments', () => {
    const dummyPost: Post = {
      id: 1,
      title: 'Title',
      content: 'Content',
      author: 'Joe',
      theme: 'Java',
      createdAt: new Date(),
      updatedAt: new Date()
    };
    const dummyComments: Comment[] = [
      {content: 'Test Comment 1', author: 'Author 1'},
      {content: 'Test Comment 2', author: 'Author 2'}
    ];

    const postApiServiceSpy: jest.SpyInstance = jest
      .spyOn(postApiService, 'getById')
      .mockReturnValue(of(dummyPost));
    const commentApiServiceSpy: jest.SpyInstance = jest
      .spyOn(commentApiService, 'getComments')
      .mockReturnValue(of(dummyComments));

    component.ngOnInit();

    expect(postApiServiceSpy).toHaveBeenCalledWith(1);
    expect(commentApiServiceSpy).toHaveBeenCalledWith(1);
    expect(component.post).toEqual(dummyPost);
    expect(component.comments).toEqual(dummyComments);
  });

  it('should submit comment successfully', () => {
    const dummyComment: Comment = {content: 'New Comment', author: 'Joe'};
    const commentApiServiceSpy: jest.SpyInstance = jest
      .spyOn(commentApiService, 'saveComment')
      .mockReturnValue(of(dummyComment));

    component.form.setValue({content: 'New Comment'});
    component.submit();

    expect(commentApiServiceSpy).toHaveBeenCalledWith(1, component.form);
    expect(component.form.value).toEqual({content: "New Comment"});
  });

  it('should handle comment submission error', () => {
    const commentApiServiceSpy: jest.SpyInstance = jest
      .spyOn(commentApiService, 'saveComment')
      .mockReturnValue(throwError(() => new Error()));

    component.form.setValue({content: 'New Comment'});
    component.submit();

    expect(commentApiServiceSpy).toHaveBeenCalledWith(1, component.form);
    expect(component.onError).toBeTruthy();
  });
});
