import {AuthService} from './auth.service';
import {HttpClientTestingModule, HttpTestingController, TestRequest} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {RegisterRequest} from "../interfaces/registerRequest.interface";
import {AuthSuccess} from "../interfaces/authSuccess.interface";
import {LoginRequest} from "../interfaces/loginRequest.interface";

describe('AuthService', () => {
  let service: AuthService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });
    service = TestBed.inject(AuthService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send a POST request to register user', () => {
    const registerRequest: RegisterRequest = {email: 'joe@example.com', password: 'Aa12345.', name: "Joe Doe"};
    const dummyAuthSuccess: AuthSuccess = {token: 'token'};

    service.register(registerRequest).subscribe((authSuccess: AuthSuccess) => {
      expect(authSuccess).toEqual(dummyAuthSuccess);
    });

    const req: TestRequest = httpTestingController.expectOne('api/auth/register');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(registerRequest);
    req.flush(dummyAuthSuccess);
  });

  it('should send a POST request to login user', () => {
    const loginRequest: LoginRequest = {email: 'joe@example.com', password: 'Aa12345.'};
    const dummyAuthSuccess: AuthSuccess = {token: 'token'};

    service.login(loginRequest).subscribe((authSuccess: AuthSuccess) => {
      expect(authSuccess).toEqual(dummyAuthSuccess);
    });

    const req: TestRequest = httpTestingController.expectOne('api/auth/login');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(loginRequest);
    req.flush(dummyAuthSuccess);
  });
});
