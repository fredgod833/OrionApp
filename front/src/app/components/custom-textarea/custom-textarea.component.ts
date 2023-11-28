import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-custom-textarea',
  templateUrl: './custom-textarea.component.html',
  styleUrls: ['./custom-textarea.component.scss'],
})
export class CustomTextareaComponent {
  @Input() id: string;
  @Input() label: string;
  @Input() name: string;
  @Input() placeholder: string;
  @Input() value: string;
  @Input() control!: FormControl;

  constructor() {
    this.id = '';
    this.label = '';
    this.name = '';
    this.placeholder = '';
    this.value = '';
  }
}
