import { Component, ElementRef, ViewChild } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { matchesCssMediaQuery } from '@utils/helpers/window.helpers';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
  host: {
    '(click)': 'onHostClick($event)',
  },
})
export class HeaderComponent {
  /** Reference to the navigation bar element. */
  @ViewChild('navigationBarRef') navigationBarRef!: ElementRef;

  /**
   * Handles click events on the host element using event delegation
   * @param e The PointerEvent object representing the click event.
   */
  onHostClick(e: PointerEvent) {
    const isNotMobile = matchesCssMediaQuery('width > 768px');
    if (isNotMobile) {
      return;
    }

    const clickedElement = e.target as HTMLElement;

    const elementClasses: string[] = Array.from(clickedElement.classList);
    const hasClickedOverlay: boolean = elementClasses
      .join(' ')
      .includes('overlay');

    if (hasClickedOverlay) {
      this.toggleMobileBurgerMenuSidebar(false);
    }
  }

  /**
   * Toggles the visibility of the mobile burger menu sidebar.
   * @param isActive A boolean indicating whether the sidebar should be active.
   */
  toggleMobileBurgerMenuSidebar(isActive: boolean) {
    this.navigationBarRef.nativeElement.classList.toggle('active', isActive);
  }
}
