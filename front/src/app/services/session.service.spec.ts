import {SessionService} from './session.service';
import {User} from "../interfaces/user.interface";

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    service = new SessionService();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should set user and isLogged to true on login', () => {
    const dummyUser: User = {
      id: 1,
      username: 'testUser',
      email: 'test@example.com',
      createdAt: new Date(),
      updatedAt: new Date(),
      subscriptions: []
    };

    service.logIn(dummyUser);

    expect(service.user).toEqual(dummyUser);
    expect(service.isLogged).toBeTruthy();
    expect(service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBeTruthy();
    })).toBeInstanceOf(Object);
  });

  it('should clear user and set isLogged to false on logout', () => {
    service.logIn({} as User);

    service.logOut();

    expect(service.user).toBeUndefined();
    expect(service.isLogged).toBeFalsy();
    expect(service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBeFalsy();
    })).toBeInstanceOf(Object);
  });
});
