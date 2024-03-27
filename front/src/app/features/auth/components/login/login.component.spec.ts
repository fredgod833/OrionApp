import {LoginComponent} from "./login.component";
import {ComponentFixture, TestBed} from "@angular/core/testing";
import {AuthService} from "../../services/auth.service";
import {UserService} from "../../../../services/user.service";
import {SessionService} from "../../../../services/session.service";
import {Router} from "@angular/router";
import {ReactiveFormsModule} from "@angular/forms";
import {User} from "../../../../interfaces/user.interface";
import {AuthSuccess} from "../../interfaces/authSuccess.interface";
import {of, throwError} from "rxjs";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {testBackNavigation, testFormControl} from "../../../../util/tests-utils";

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let userService: UserService;
  let sessionService: SessionService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [
        ReactiveFormsModule,
        HttpClientTestingModule,
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
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

  it('should navigate back on back()', () => {
    testBackNavigation(component);
  });

  it('should initialize form with validators', () => {
    testFormControl(component , 'email');
    testFormControl(component, 'password');
  });

  describe('Login', () => {
    let routerSpy: jest.SpyInstance;

    beforeEach(() => {
      routerSpy = jest.spyOn(router, 'navigate').mockImplementation();
    })

    it('should submit login request successfully', () => {
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
        .spyOn(authService, 'login')
        .mockReturnValue(of(dummyAuthSuccess));
      const userServiceSpy = jest
        .spyOn(userService, 'getUser')
        .mockReturnValue(of(dummyUser));
      const sessionServiceSpy = jest
        .spyOn(sessionService, 'logIn')
        .mockImplementation();

      component.submit();

      expect(authServiceSpy).toHaveBeenCalledWith(component.form.value);
      expect(userServiceSpy).toHaveBeenCalledTimes(1);
      expect(sessionServiceSpy).toHaveBeenCalledWith(dummyUser);
      expect(routerSpy).toHaveBeenCalledTimes(2);
    });

    it('should handle login error', () => {
      const authServiceSpy = jest
        .spyOn(authService, 'login')
        .mockReturnValue(throwError({error: {message: 'Login error'}}));

      component.submit();

      expect(authServiceSpy).toHaveBeenCalledWith(component.form.value);
      expect(component.error).toBe('Login error');
      expect(routerSpy).not.toHaveBeenCalled()
    });
  })

  it('should unsubscribe on destroy', () => {
    const unsubscribeSpy = jest.spyOn(component['destroy$'], 'complete');
    component.ngOnDestroy();
    expect(unsubscribeSpy).toHaveBeenCalled();
  });
});
