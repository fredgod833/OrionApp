import {ComponentFixture, TestBed} from '@angular/core/testing';
import {RegisterComponent} from './register.component';
import {ReactiveFormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {of, throwError} from 'rxjs';
import {AuthService} from "../../services/auth.service";
import {UserService} from "../../../../services/user.service";
import {SessionService} from "../../../../services/session.service";
import {AuthSuccess} from "../../interfaces/authSuccess.interface";
import {User} from "../../../../interfaces/user.interface";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {testBackNavigation, testFormControl} from "../../../../util/tests-utils";

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService: AuthService;
  let userService: UserService;
  let sessionService: SessionService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule],
      providers: [],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    userService = TestBed.inject(UserService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with validators', () => {
    testFormControl(component, 'username');
    testFormControl(component, 'email');
    testFormControl(component, 'password');
  });

  describe('Registration', () => {
    let routerSpy: jest.SpyInstance;

    beforeEach(() => {
      routerSpy = jest.spyOn(router, 'navigate').mockImplementation();
    })

    it('should submit registration request successfully', () => {
      const dummyUser: User = {
        id: 1,
        username: 'testUser',
        email: 'test@example.com',
        createdAt: new Date(),
        updatedAt: new Date(),
        subscriptions: []
      };
      const dummyAuthSuccess: AuthSuccess = {
        token: 'dummyToken'
      };
      const authServiceSpy = jest
        .spyOn(authService, 'register')
        .mockReturnValue(of(dummyAuthSuccess));
      const userServiceSpy = jest
        .spyOn(userService, 'getUser')
        .mockReturnValue(of(dummyUser));
      const sessionServiceSpy = jest
        .spyOn(sessionService, 'logIn')
        .mockImplementation();

      component.submit();

      expect(authServiceSpy).toHaveBeenCalledWith(component.form.value);
      expect(userServiceSpy).toHaveBeenCalled();
      expect(sessionServiceSpy).toHaveBeenCalled();
      expect(routerSpy).toHaveBeenCalledTimes(1);
    });

    it('should handle registration error', () => {
      const authServiceSpy = jest
        .spyOn(authService, 'register')
        .mockReturnValue(throwError({error: {message: 'Registration error'}}));

      component.submit();

      expect(authServiceSpy).toHaveBeenCalledWith(component.form.value);
      expect(component.error).toEqual('Registration error');
      expect(routerSpy).not.toHaveBeenCalled();
    });
  })

  it('should navigate back on back()', () => {
    testBackNavigation(component);
  });

  it('should unsubscribe on destroy', () => {
    const unsubscribeSpy = jest.spyOn(component['destroy$'], 'complete');
    component.ngOnDestroy();
    expect(unsubscribeSpy).toHaveBeenCalled();
  });

  it('should return correct password error message', () => {
    expect(component.getPasswordError()).toBe('Password is required.');
  });

  it('should return correct email error message', () => {
    expect(component.getEmailError()).toBe('Email is required.');
  });

  it('should return correct username error message', () => {
    expect(component.getUsernameError()).toBe('Username is required.');
  });
});
