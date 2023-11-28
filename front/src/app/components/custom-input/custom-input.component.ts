import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-custom-input',
  templateUrl: './custom-input.component.html',
  styleUrls: ['./custom-input.component.scss'],
})
export class CustomInputComponent {
  @Input() id: string;
  @Input() label: string;
  @Input() type: string;
  @Input() placeholder: string;
  @Input() value: string;
  @Input() name: string;
  @Input() control!: FormControl;

  constructor() {
    this.id = '';
    this.label = '';
    this.type = '';
    this.placeholder = '';
    this.value = '';
    this.name = '';
  }
}
