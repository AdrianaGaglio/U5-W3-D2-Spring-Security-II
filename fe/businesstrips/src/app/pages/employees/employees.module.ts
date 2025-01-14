import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmployeesRoutingModule } from './employees-routing.module';
import { EmployeesComponent } from './employees.component';
import { CardModule } from '../../components/card/card.module';
import { NgIconsModule } from '@ng-icons/core';
import {
  bootstrapGrid,
  bootstrapList,
  bootstrapListNested,
  bootstrapPlus,
} from '@ng-icons/bootstrap-icons';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [EmployeesComponent],
  imports: [
    CommonModule,
    EmployeesRoutingModule,
    CardModule,
    FormsModule,
    NgIconsModule.withIcons({ bootstrapList, bootstrapGrid, bootstrapPlus }),
  ],
})
export class EmployeesModule {}
