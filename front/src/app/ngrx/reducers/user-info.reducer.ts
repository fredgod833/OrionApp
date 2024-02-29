import { createReducer, on } from '@ngrx/store';
import { setInfo } from '../actions/user-info.actions';
import { UserInfo } from '@core/types/user.type';

export const userInfoInitialState: Omit<UserInfo, 'token'> = {
  id: 0,
  username: '',
  email: '',
};

export const userInfoReducer = createReducer(
  userInfoInitialState,
  on(setInfo, (state, info: Omit<UserInfo, 'token'>) => {
    const { id, email, username } = info;

    return { ...state, id, username, email };
  })
);
