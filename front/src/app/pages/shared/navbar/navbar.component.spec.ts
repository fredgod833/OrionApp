import {ComponentFixture, TestBed} from '@angular/core/testing';
import {NavbarComponent} from './navbar.component';
import {Observable, of} from 'rxjs';
import {SessionService} from "../../../services/session.service";
import {NO_ERRORS_SCHEMA} from "@angular/core";

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;
  let sessionService: any;

  beforeEach(async () => {
    sessionService = {
      $isLogged: jest.fn().mockReturnValue(of(true))
    };

    await TestBed.configureTestingModule({
      declarations: [NavbarComponent],
      providers: [{provide: SessionService, useValue: sessionService}],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit sidenavToggle event', () => {
    let emitted: boolean = false;
    component.sidenavToggle.subscribe(() => {
      emitted = true;
    });

    component.onToggleSidenav();

    expect(emitted).toBeTruthy();
  });

  it('should return observable of boolean from $isLogged()', () => {
    const result: Observable<boolean> = component.$isLogged();

    expect(result).toBeInstanceOf(Observable);

    result.subscribe((value: boolean) => {
      expect(typeof value).toBe('boolean');
    });
  });
});
