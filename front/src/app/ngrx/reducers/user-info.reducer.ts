import { createReducer, on } from '@ngrx/store';
import { setInfo } from '@mdd-global-state-ngrx/actions/user-info.actions';
import { UserBasicInfo, UserInfo } from '@core/types/user.type';

export const userInfoInitialState: UserBasicInfo = {
  id: null,
  username: null,
  email: null,
};

export const userInfoReducer = createReducer(
  userInfoInitialState,
  on(setInfo, (state, info: UserBasicInfo) => {
    const { id, email, username } = info;

    return { ...state, id, username, email };
  })
);
