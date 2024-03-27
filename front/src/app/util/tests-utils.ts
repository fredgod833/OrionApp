export function testBackNavigation(component: any) {
  const historyBackSpy: jest.SpyInstance = jest.spyOn(window.history, 'back').mockImplementation();

  component.back();

  expect(historyBackSpy).toHaveBeenCalled();
}

export function testFormControl(component: any, controlName: string) {
  const formControl = component.form.get(controlName);
  expect(formControl).toBeTruthy();
  expect(formControl?.errors).toBeTruthy();
}
