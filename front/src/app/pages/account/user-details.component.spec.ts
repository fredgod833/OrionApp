import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UserDetailsComponent} from './user-details.component';
import {UserService} from "../../services/user.service";
import {SessionService} from "../../services/session.service";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import {User} from "../../interfaces/user.interface";
import {of} from "rxjs";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {FormBuilder, FormGroup} from "@angular/forms";
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {MessageResponse} from "../../interfaces/message-response";
import {Router} from "@angular/router";

describe('UserDetailsComponent', () => {
  let component: UserDetailsComponent;
  let fixture: ComponentFixture<UserDetailsComponent>;
  let userService: UserService;
  let sessionService: SessionService;
  let matSnackBar: MatSnackBar;
  let router: Router;

  const dummyUser: User = {
    id: 1,
    username: 'Joe Doe',
    email: 'joe@example.com',
    createdAt: new Date(),
    updatedAt: new Date(),
    subscriptions: [
      {id: 1, name: 'Java', description: 'Java is cool', createdAt: '', updatedAt: ''},
      {id: 2, name: 'Python', description: 'Python is cool', createdAt: '', updatedAt: ''}
    ]
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, MatSnackBarModule],
      providers: [FormBuilder],
      declarations: [UserDetailsComponent],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(UserDetailsComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
    sessionService = TestBed.inject(SessionService);
    matSnackBar = TestBed.inject(MatSnackBar);
    component.user = dummyUser;
    router = TestBed.inject(Router);

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch user data on initialization', () => {
    const userServiceSpy: jest.SpyInstance = jest
      .spyOn(userService, 'getUser')
      .mockReturnValue(of(dummyUser));

    component.ngOnInit();

    expect(userServiceSpy).toHaveBeenCalled();
    expect(component.user).toEqual(dummyUser);
    expect(component.isLoading).toBeFalsy();
  });

  it('should update user details properly', () => {
    const messageResponse: MessageResponse = {message: "User updated successfully"};
    const userServiceSpy: jest.SpyInstance = jest
      .spyOn(userService, 'update')
      .mockImplementation(() => {
        return of(messageResponse);
      });
    const matSnackBarSpy: jest.SpyInstance = jest.spyOn(matSnackBar, 'open');

    component.form.setValue({username: 'Doe Joe', email: 'new@example.com'});
    component.submit();
    fixture.detectChanges();

    expect(userServiceSpy).toHaveBeenCalledWith(1, component.form);
    expect(matSnackBarSpy).toHaveBeenCalledWith('Changes saved', 'Close', {duration: 3000});
    expect(component.form.pristine).toBe(true);
  });

  it('should log out correctly', () => {
    const sessionServiceSpy: jest.SpyInstance = jest.spyOn(sessionService, 'logOut').mockImplementation();
    const routerSpy: jest.SpyInstance = jest.spyOn(router, 'navigate').mockResolvedValue(true);

    component.logout();

    expect(sessionServiceSpy).toHaveBeenCalled();
    expect(routerSpy).toHaveBeenCalledWith(['']);
  });

  it('should unsubscribe from themes properly', () => {
    const userServiceSpy: jest.SpyInstance = jest
      .spyOn(userService, 'unSubscribe')
      .mockReturnValue(of(dummyUser));

    component.unSubscribe(1);

    expect(userServiceSpy).toHaveBeenCalledWith(1);
    expect(component.themes[0].name).toEqual('Java');
  });

  it('should handle navigation away with unsaved changes', () => {
    component.form.markAsDirty();
    window.confirm = jest.fn(() => true);

    const result: boolean = component.canExit();

    expect(result).toBe(true);
    expect(window.confirm).toHaveBeenCalled();
  });
});
