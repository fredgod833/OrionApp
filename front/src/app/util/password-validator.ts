import {AbstractControl, ValidationErrors} from "@angular/forms";

export function passwordValidator(control: AbstractControl): ValidationErrors | null {
  const password = control.value;

  const hasDigit = /\d/.test(password);
  const hasLowercase = /[a-z]/.test(password);
  const hasUppercase = /[A-Z]/.test(password);
  const hasSpecialChar = /\W/.test(password) && !/\s/.test(password);

  if (!hasDigit || !hasLowercase || !hasUppercase || !hasSpecialChar) {
    return { complexity: { requiredTypes: ['digit', 'lowercase', 'uppercase', 'special character'] } };
  }

  return null;
}
