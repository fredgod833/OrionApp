import { Component } from "@angular/core";
import { MatCardModule } from "@angular/material/card";
import { MatFormFieldModule } from "@angular/material/form-field";

@Component({
    selector: 'user-info',
    templateUrl: './user.information.html',
    styleUrls: ['./user.information.scss'],
    standalone: true,
    imports: [MatFormFieldModule, MatCardModule]
})
export default class UserInformation{}