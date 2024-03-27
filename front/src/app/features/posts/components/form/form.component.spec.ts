import {ComponentFixture, TestBed} from '@angular/core/testing';

import {FormComponent} from './form.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {FormBuilder} from "@angular/forms";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {PostApiService} from "../../services/post-api.service";
import {Router} from "@angular/router";
import {of, throwError} from "rxjs";
import {Post} from "../../interfaces/post";

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let postApiService: PostApiService;
  let matSnackBar: MatSnackBar;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, MatSnackBarModule],
      providers: [FormBuilder],
      declarations: [FormComponent],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    postApiService = TestBed.inject(PostApiService);
    matSnackBar = TestBed.inject(MatSnackBar);
    router = TestBed.inject(Router);

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should submit post', () => {
    let dummyPost: Post = {
      id: 1,
      title: 'Test title',
      content: 'Test content',
      author: 'Joe',
      theme: 'Java',
      createdAt: new Date(),
      updatedAt: new Date()
    }
    const postApiServiceSpy: jest.SpyInstance = jest.spyOn(postApiService, 'create').mockReturnValue(of(dummyPost));
    const matSnackBarSpy: jest.SpyInstance = jest.spyOn(matSnackBar, 'open');
    component.form.setValue({theme: 'Test theme', title: 'Test title', content: 'Test content'});

    component.submit();

    expect(postApiServiceSpy).toHaveBeenCalledWith(component.form);
    expect(component.form.pristine).toBeTruthy();
    expect(matSnackBarSpy).toHaveBeenCalledWith('Post created !', 'Close', {duration: 3000});
  });

  it('should handle submission error', () => {
    const postApiServiceSpy = jest.spyOn(postApiService, 'create')
      .mockReturnValue(throwError(() => new Error('Error creating post')));

    component.form.setValue({ theme: 'Test theme', title: 'Test title', content: 'Test content' });
    component.submit();

    expect(postApiServiceSpy).toHaveBeenCalled();
    expect(postApiServiceSpy).toHaveBeenCalledWith(component.form);
  });

  it('should navigate back', () => {
    const routerSpy: jest.SpyInstance = jest.spyOn(router, 'navigate').mockResolvedValue(true);

    component.back();

    expect(routerSpy).toHaveBeenCalledWith(['/posts']);
  });

  it('should confirm exiting with unsaved changes', () => {
    component.form.markAsDirty();
    window.confirm = jest.fn(() => true);

    expect(component.canExit()).toBeTruthy();
    expect(window.confirm).toHaveBeenCalled();
  });
});
