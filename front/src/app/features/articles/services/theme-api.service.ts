import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ThemesResponse} from "../interfaces/api/themesResponse";

@Injectable({
  providedIn: 'root'
})
export class ThemeApiService {

  private pathService = '/api/themes';

  constructor(private http: HttpClient) {
  }

  public all(): Observable<ThemesResponse> {
    return this.http.get<ThemesResponse>(this.pathService);
  }
}
