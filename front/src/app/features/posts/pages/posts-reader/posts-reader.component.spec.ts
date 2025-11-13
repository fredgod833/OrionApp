import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostsReaderComponent } from './posts-reader.component';

describe('PostsReaderComponent', () => {
  let component: PostsReaderComponent;
  let fixture: ComponentFixture<PostsReaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostsReaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostsReaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
