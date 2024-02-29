import { UserInfo } from '@core/types/user.type';
import { createAction, props } from '@ngrx/store';

export const setInfo = createAction(
  'set-info',
  props<Omit<UserInfo, 'token'>>()
);
