import { NgModule } from '@angular/core';
import { HeaderComponent } from './header/header.component';
import { SharedModule } from 'src/app/core/components/shared.module';

@NgModule({
    imports: [
        SharedModule
    ],
    exports: [],
    declarations: [
        HeaderComponent
    ],
    providers: [],
})

export class HomeModule { }
