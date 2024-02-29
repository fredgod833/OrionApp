import { isDevMode } from '@angular/core';
import {
  ActionReducer,
  ActionReducerMap,
  createFeatureSelector,
  createSelector,
  MetaReducer,
} from '@ngrx/store';
import { userInfoReducer } from './user-info.reducer';

export interface State {}

export const reducers: ActionReducerMap<State> = {
  userInfo: userInfoReducer,
};

export const metaReducers: MetaReducer<State>[] = isDevMode() ? [] : [];
