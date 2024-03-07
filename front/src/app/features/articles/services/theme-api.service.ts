import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Theme} from "../interfaces/theme";
import {ThemesResponse} from "../interfaces/api/themesResponse";

@Injectable({
  providedIn: 'root'
})
export class ThemeApiService {

  private pathService = '/api/themes';

  constructor(private httpClient: HttpClient) {
  }

  public all(): Observable<ThemesResponse> {
    return this.httpClient.get<ThemesResponse>(this.pathService);
  }
}
