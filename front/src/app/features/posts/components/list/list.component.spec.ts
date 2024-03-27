import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ListComponent} from './list.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {PostApiService} from "../../services/post-api.service";
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {Post} from "../../interfaces/post";
import {of} from "rxjs";

describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;
  let postApiService: PostApiService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ListComponent],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    postApiService = TestBed.inject(PostApiService);

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch posts on initialization', () => {
    const dummyPosts: Post[] = [
      {
        id: 1,
        title: 'Post 1',
        content: 'Content 1',
        author: 'Joe',
        theme: 'Java',
        createdAt: new Date(),
        updatedAt: new Date()
      },
      {
        id: 2,
        title: 'Post 2',
        content: 'Content 2',
        author: 'Mike',
        theme: 'Python',
        createdAt: new Date(),
        updatedAt: new Date()
      }
    ];
    const postApiServiceSpy: jest.SpyInstance = jest
      .spyOn(postApiService, 'getAll')
      .mockReturnValue(of(dummyPosts))

    component.ngOnInit();

    expect(postApiServiceSpy).toHaveBeenCalled();
    expect(component.isLoading).toBeFalsy();
    expect(component.posts).toEqual(dummyPosts);
  });

  it('should sort posts by date', () => {
    component.posts = [
      {
        id: 1,
        title: 'Post 1',
        content: 'Content 1',
        author: 'Joe',
        theme: 'Java',
        createdAt: new Date('2022-01-01'),
        updatedAt: new Date()
      },
      {
        id: 2,
        title: 'Post 2',
        content: 'Content 2',
        author: 'Mike',
        theme: 'Python',
        createdAt: new Date('2022-01-02'),
        updatedAt: new Date()
      }
    ];

    component.sortPosts();

    expect(component.posts[0].title).toBe('Post 2');
    expect(component.posts[1].title).toBe('Post 1');
    expect(component.arrowIcon).toBe('arrow_upward');
  });

  it('should display posts', () => {
    component.posts = [
      {
        id: 1,
        title: 'Post',
        content: 'Content',
        author: 'Joe',
        theme: 'Java',
        createdAt: new Date(),
        updatedAt: new Date()
      }
    ];
    fixture.detectChanges();

    const compiled = fixture.nativeElement;

    const postElements = compiled.querySelectorAll('.item');
    expect(postElements.length).toBe(1);
    expect(postElements[0].textContent).toContain('Post');
  });

  it('should display empty state if no posts', () => {
    component.posts = [];
    component.isLoading = false;
    fixture.detectChanges();

    const compiled = fixture.nativeElement;

    const emptyStateElement = compiled.querySelector('.empty-state');
    expect(emptyStateElement).toBeTruthy();

    const loadingSpinner = compiled.querySelector('#loading');
    expect(loadingSpinner).toBeFalsy();
  });

});
