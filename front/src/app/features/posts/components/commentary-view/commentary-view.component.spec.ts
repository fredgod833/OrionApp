import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentaryViewComponent } from './commentary-view.component';

describe('CommentViewComponent', () => {
  let component: CommentaryViewComponent;
  let fixture: ComponentFixture<CommentaryViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommentaryViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommentaryViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
