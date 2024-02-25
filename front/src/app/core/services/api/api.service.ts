import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  /**
   * The base URL for making HTTP requests to the API.
   * It is initialized to an empty string by default.
   */
  protected BASE_URL: string = environment.BASE_URL;

  protected http = inject(HttpClient);

  /**
   * Constructs the complete URL by combining the base URL and the provided URL segment.
   *
   * @private
   * @param {string} urlPathname - The URL segment to be appended to the base URL.
   * @returns {string} The constructed URL.
   */
  private constructUrl(urlPathname: string): string {
    const constructedUrl: URL = new URL(this.BASE_URL);
    constructedUrl.pathname = urlPathname;

    return constructedUrl.href;
  }

  /**
   * Performs an HTTP GET request.
   *
   * @param {string} urlPathname - The URL segment to be appended to the base URL. ex: `/api/auth/login`
   * @returns An Observable with the response data.
   */
  protected fetchGet<T>(urlPathname: string): Observable<T> {
    const constructedUrl: string = this.constructUrl(urlPathname);

    return this.http.get<T>(constructedUrl);
  }

  /**
   * Performs an HTTP POST request.
   *
   * @param {args} - An object containing urlPathname, body, and headers.
   * @returns An Observable with the response data.
   */
  protected fetchPost<T>(urlPathname: string, body: any): Observable<T> {
    const constructedUrl: string = this.constructUrl(urlPathname);

    return this.http.post<T>(constructedUrl, body);
  }

  /**
   * Performs an HTTP PUT request.
   *
   * @param {args} - An object containing urlPathname, body, and headers.
   * @returns An Observable with the response data.
   */
  protected fetchPut<T>(urlPathname: string, body: any): Observable<T> {
    const constructedUrl: string = this.constructUrl(urlPathname);

    return this.http.put<T>(constructedUrl, body);
  }

  /**
   * Performs an HTTP DELETE request.
   *
   * @param {args} - An object containing urlPathname and headers.
   * @returns An Observable with the response data.
   */
  protected fetchDelete<T>(urlPathname: string): Observable<T> {
    const constructedUrl: string = this.constructUrl(urlPathname);
    return this.http.delete<T>(constructedUrl);
  }
}
