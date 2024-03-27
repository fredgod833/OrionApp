import {ComponentFixture, TestBed} from '@angular/core/testing';
import {NotFoundComponent} from './not-found.component';
import {Observable, of} from 'rxjs';
import {SessionService} from "../../services/session.service";
import {NO_ERRORS_SCHEMA} from "@angular/core";

describe('NotFoundComponent', () => {
  let component: NotFoundComponent;
  let fixture: ComponentFixture<NotFoundComponent>;
  let sessionService: any;

  beforeEach(async () => {
    sessionService = {
      $isLogged: jest.fn().mockReturnValue(of(true))
    };

    await TestBed.configureTestingModule({
      declarations: [NotFoundComponent],
      providers: [{provide: SessionService, useValue: sessionService}],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(NotFoundComponent);
    component = fixture.componentInstance;

    fixture.detectChanges();
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return observable of boolean', () => {
    const result: Observable<boolean> = component.$isLogged();

    expect(result).toBeInstanceOf(Observable);

    result.subscribe((value: boolean) => {
      expect(typeof value).toBe('boolean');
    });
  });
});
