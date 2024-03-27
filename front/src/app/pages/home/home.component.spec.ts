import {ComponentFixture, TestBed} from '@angular/core/testing';

import {HomeComponent} from './home.component';
import {Router} from "@angular/router";
import {NO_ERRORS_SCHEMA} from "@angular/core";

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomeComponent],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to login page', () => {
    const routerSpy: jest.SpyInstance = jest.spyOn(router, 'navigate').mockResolvedValue(true);

    component.login();

    expect(routerSpy).toHaveBeenCalledWith(['login']);
  });

  it('should navigate to register page', () => {
    const routerSpy: jest.SpyInstance = jest.spyOn(router, 'navigate').mockResolvedValue(true);

    component.register();

    expect(routerSpy).toHaveBeenCalledWith(['register']);
  });
});
