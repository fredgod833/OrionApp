import { Injectable } from '@angular/core';
import { CookieService, CookieType } from '@lephenix47/cookies-utility';

@Injectable({
  providedIn: 'root',
})
export class CookiesService {
  private TOKEN_KEY_NAME: string = 'jwt';

  public getJwt(): CookieType | null {
    return CookieService.getCookieByName(this.TOKEN_KEY_NAME);
  }

  public setJwt(jwt: string): void {
    CookieService.setCookie(this.TOKEN_KEY_NAME, jwt);
  }

  public deleteJwt(): void {
    CookieService.deleteCookieByName(this.TOKEN_KEY_NAME);
  }
}
