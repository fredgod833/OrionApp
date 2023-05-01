import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthPortalComponent } from './auth-portal.component';

describe('HomeComponent', () => {
  let component: AuthPortalComponent;
  let fixture: ComponentFixture<AuthPortalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthPortalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthPortalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
