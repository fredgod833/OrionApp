import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Topic } from 'src/app/interfaces/topic.interface';

@Component({
  selector: 'app-custom-select',
  templateUrl: './custom-select.component.html',
  styleUrls: ['./custom-select.component.scss'],
})
export class CustomSelectComponent {
  @Input() id: string;
  @Input() label: string;
  @Input() placeholder: string;
  @Input() options: Topic[] | null;
  @Input() control!: FormControl;

  constructor() {
    this.id = '';
    this.label = '';
    this.placeholder = '';
    this.options = [];
  }
}
