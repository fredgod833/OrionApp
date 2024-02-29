import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, tap } from 'rxjs';
import { ApiService } from '../api/api.service';
import { UserBasicInfo } from '@core/types/user.type';

@Injectable({
  providedIn: 'root',
})
export class UserService extends ApiService {
  private API_PATHNAME: string = 'api/user';

  public isLoading$ = new BehaviorSubject<boolean>(false);

  public errorMessage$ = new BehaviorSubject<string>('');

  public hasError$ = new BehaviorSubject<boolean>(false);

  constructor() {
    super();

    this.updateLoadingState = this.updateLoadingState.bind(this);
    this.handleErrors = this.handleErrors.bind(this);
  }

  public updateUser(updatedUser: Omit<UserBasicInfo, 'id'>): Observable<any> {
    this.isLoading$.next(true);

    return this.fetchPut<any>(`${this.API_PATHNAME}/`, updatedUser).pipe(
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  }

  private updateLoadingState(value: any): void {
    this.isLoading$.next(false);
    this.hasError$.next(false);
  }

  private handleErrors(err: any, caught: any): never {
    this.isLoading$.next(false);
    this.hasError$.next(true);

    this.errorMessage$.next(err?.error.message);

    throw new Error(`An error occurred: ${err?.message}`);
  }
}
