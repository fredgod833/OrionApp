import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {ThemeApiService} from "../../services/theme-api.service";
import {ThemesResponse} from "../../interfaces/api/themesResponse";

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit {

  public themes$: Observable<ThemesResponse> = this.themeApiService.all();

  constructor(private themeApiService: ThemeApiService) {
  }

  ngOnInit(): void {
  }

}
