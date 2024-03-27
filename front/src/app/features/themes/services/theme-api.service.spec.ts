import {ThemeApiService} from './theme-api.service';
import {HttpClientTestingModule, HttpTestingController, TestRequest} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {Theme} from '../interfaces/theme';

describe('ThemeApiService', () => {
  let service: ThemeApiService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ThemeApiService]
    });
    service = TestBed.inject(ThemeApiService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send a GET request to fetch ALL themes', () => {
    const dummyThemes: Theme[] = [
      {id: 1, name: 'Theme 1', description: 'description 1', createdAt: "26.03.2024", updatedAt: "26.02.2024"},
      {id: 2, name: 'Theme 2', description: 'description 2', createdAt: "26.03.2024", updatedAt: "26.02.2024"}
    ];

    service.all().subscribe((themes: Theme[]) => {
      expect(themes).toEqual(dummyThemes);
    });

    const req: TestRequest = httpTestingController.expectOne('/api/themes');
    expect(req.request.method).toBe('GET');
    req.flush(dummyThemes);
  });
});
