import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ThemesComponent} from './themes.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {ThemeApiService} from "../../services/theme-api.service";
import {UserService} from "../../../../services/user.service";
import {of} from "rxjs";
import {Theme} from "../../interfaces/theme";
import {User} from "../../../../interfaces/user.interface";
import {NO_ERRORS_SCHEMA} from "@angular/core";

describe('ThemesComponent', () => {
  let component: ThemesComponent;
  let fixture: ComponentFixture<ThemesComponent>;
  let themeApiService: ThemeApiService;
  let userService: UserService;

  const dummyUser: User = {
    id: 1,
    username: 'Joe Doe',
    email: 'joe@example.com',
    createdAt: new Date(),
    updatedAt: new Date(),
    subscriptions: [{id: 1, name: 'Java', description: 'Java is cool', createdAt: '', updatedAt: ''}]
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ThemesComponent],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ThemesComponent);
    component = fixture.componentInstance;
    themeApiService = TestBed.inject(ThemeApiService);
    userService = TestBed.inject(UserService);
    component.user = dummyUser;

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch themes and user data on initialization', () => {
    const dummyThemes: Theme[] = [{id: 1, name: 'Java', description: 'Java is cool', createdAt: '', updatedAt: ''}];
    const themesApiServiceSpy: jest.SpyInstance = jest
      .spyOn(themeApiService, 'all')
      .mockReturnValue(of(dummyThemes));
    const userServiceSpy: jest.SpyInstance = jest
      .spyOn(userService, 'getUser')
      .mockReturnValue(of(dummyUser));

    component.ngOnInit();

    expect(themesApiServiceSpy).toHaveBeenCalled();
    expect(userServiceSpy).toHaveBeenCalled();
    expect(component.isLoading).toBeFalsy();
    expect(component.themes).toEqual(dummyThemes);
    expect(component.user.subscriptions).toEqual(dummyThemes);
  });

  it('should correctly determine subscription status', () => {
    expect(component.isSubscribed(1)).toBe(true);
    expect(component.isSubscribed(3)).toBe(false);
  });

  it('should provide correct button text based on subscription status', () => {

    expect(component.getButtonText(1)).toBe("Se désabonner");
    expect(component.getButtonText(2)).toBe("S'abonner");
  });
});
