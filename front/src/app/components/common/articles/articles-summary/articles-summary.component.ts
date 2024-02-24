import { Component, computed, input } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-articles-summary',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './articles-summary.component.html',
  styleUrl: './articles-summary.component.scss',
})
export class ArticlesSummaryComponent {
  // * Signal  inputs
  public id = input.required<number>();

  public title = input.required<string>();

  public description = input.required<string>();

  public author = input.required<string>();

  public date = input.required<string>();

  public publishDate = computed(() => {
    const date = new Date(this.date()).toLocaleDateString();

    return date;
  });
}
