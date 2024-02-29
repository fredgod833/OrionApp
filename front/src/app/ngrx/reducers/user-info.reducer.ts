import { createReducer, on } from '@ngrx/store';
import { setInfo } from '../actions/user-info.actions';

export const userInfoInitialState = {
  id: null,
  username: null,
  email: null,
};

export const userInfoReducer = createReducer(
  userInfoInitialState,
  on(setInfo, (state) => {
    return state;
  })
);
