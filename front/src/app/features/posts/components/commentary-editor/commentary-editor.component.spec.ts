import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentaryEditorComponent } from './commentary-editor.component';

describe('CommentaryEditorComponent', () => {
  let component: CommentaryEditorComponent;
  let fixture: ComponentFixture<CommentaryEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommentaryEditorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommentaryEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
