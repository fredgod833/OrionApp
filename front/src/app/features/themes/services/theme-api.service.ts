import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Theme} from "../interfaces/theme";

@Injectable({
  providedIn: 'root'
})
export class ThemeApiService {

  private pathService: string = '/api/themes';

  constructor(private http: HttpClient) {
  }

  public all(): Observable<Theme[]> {
    return this.http.get<Theme[]>(this.pathService);
  }
}
