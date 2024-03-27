import {UserService} from './user.service';
import {HttpClientTestingModule, HttpTestingController, TestRequest} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {FormGroup} from '@angular/forms';
import {MessageResponse} from '../interfaces/message-response';
import {User} from "../interfaces/user.interface";

describe('UserService', () => {
  let service: UserService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });
    service = TestBed.inject(UserService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send a GET request to fetch user data', () => {
    const dummyUser: User = {
      id: 1,
      username: 'Joe Doe',
      email: 'joe@example.com',
      createdAt: new Date(),
      updatedAt: new Date(),
      subscriptions: []
    };

    service.getUser().subscribe((user: User) => {
      expect(user).toEqual(dummyUser);
    });

    const req: TestRequest = httpTestingController.expectOne('/api/user');
    expect(req.request.method).toBe('GET');
    req.flush(dummyUser);
  });

  it('should send a PUT request to update user data', () => {
    const userId: number = 1;
    const dummyResponse: MessageResponse = {message: 'User updated successfully'};
    const form: FormGroup = {
      value: {username: 'Doe_Joe', email: 'joe@example.com'}
    } as FormGroup;

    service.update(userId, form).subscribe((response: MessageResponse) => {
      expect(response).toEqual(dummyResponse);
    });

    const req: TestRequest = httpTestingController.expectOne(`/api/user/update/${userId}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(form.value);
    req.flush(dummyResponse);
  });

  it('should send a POST request to subscribe user', () => {
    const themeId: number = 1;
    const dummyUser: User = {
      id: 1,
      username: 'Joe_Doe',
      email: 'joe@example.com',
      createdAt: new Date(),
      updatedAt: new Date(),
      subscriptions: []
    };

    service.subscribe(themeId).subscribe((user: User) => {
      expect(user).toEqual(dummyUser);
    });

    const req: TestRequest = httpTestingController.expectOne(`/api/user/subscribe/${themeId}`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toBe(themeId);
    req.flush(dummyUser);
  });

  it('should send a DELETE request to unsubscribe user', () => {
    const themeId: number = 1;
    const dummyUser: User = {
      id: 1,
      username: 'Joe_Doe',
      email: 'joe@example.com',
      createdAt: new Date(),
      updatedAt: new Date(),
      subscriptions: []
    };

    service.unSubscribe(themeId).subscribe((user: User) => {
      expect(user).toEqual(dummyUser);
    });

    const req: TestRequest = httpTestingController.expectOne(`/api/user/unsubscribe/${themeId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(dummyUser);
  });
});
