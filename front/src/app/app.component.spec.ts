import {ComponentFixture, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {AppComponent} from './app.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {Observable, of, throwError} from "rxjs";
import {UserService} from "./services/user.service";
import {SessionService} from "./services/session.service";
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {User} from "./interfaces/user.interface";

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let userService: UserService;
  let sessionService: SessionService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterTestingModule
      ],
      declarations: [AppComponent],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
    sessionService = TestBed.inject(SessionService);

    fixture.detectChanges();
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it('should autoLog user', () => {
    const dummyUser: User = {
      id: 1,
      username: 'testUser',
      email: 'test@example.com',
      createdAt: new Date(),
      updatedAt: new Date(),
      subscriptions: []
    };
    const userServiceSpy: jest.SpyInstance = jest
      .spyOn(userService, 'getUser')
      .mockReturnValue(of(dummyUser));
    const sessionServiceSpy: jest.SpyInstance = jest
      .spyOn(sessionService, 'logIn')
      .mockImplementation();

    component.autoLog();

    expect(userServiceSpy).toHaveBeenCalled();
    expect(sessionServiceSpy).toHaveBeenCalled();
  });

  it('should log out if autoLog encounters an error', () => {
    const userServiceSpy: jest.SpyInstance = jest
      .spyOn(userService, 'getUser')
      .mockReturnValue(throwError('Error'));
    const sessionServiceSpy: jest.SpyInstance = jest.spyOn(sessionService, 'logOut').mockImplementation();

    component.autoLog();

    expect(userServiceSpy).toHaveBeenCalled();
    expect(sessionServiceSpy).toHaveBeenCalled();
  });

  it('should return true if on home page', () => {
    const result: boolean = component.isHomePage();

    expect(result).toBe(true);
  });

  it('should return observable of boolean from $isLogged()', () => {
    const result: Observable<boolean> = component.$isLogged();

    expect(result).toBeInstanceOf(Observable);
    result.subscribe((value: boolean) => {
      expect(value).toBe(true);
    });
  });
});
