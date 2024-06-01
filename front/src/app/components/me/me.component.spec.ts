import {ComponentFixture} from '@angular/core/testing';

import {MeComponent} from './me.component';
import {SessionInformation} from "../../interfaces/sessionInformation.interface";


describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;

  const sessionInfo : SessionInformation = {
    admin: false, email: "", id: 1, token: "", type: "", username: ""
  };


});
