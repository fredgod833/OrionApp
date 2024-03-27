import {ComponentFixture, TestBed} from '@angular/core/testing';
import {SidenavListComponent} from './sidenav-list.component';
import {NO_ERRORS_SCHEMA} from "@angular/core";

describe('SidenavListComponent', () => {
  let component: SidenavListComponent;
  let fixture: ComponentFixture<SidenavListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SidenavListComponent],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(SidenavListComponent);
    component = fixture.componentInstance;

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit sidenavClose event', () => {
    let emitted: boolean = false;
    component.sidenavClose.subscribe(() => {
      emitted = true;
    });

    component.onSidenavClose();

    expect(emitted).toBeTruthy();
  });
});
