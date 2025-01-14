import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReservationRoutingModule } from './reservation-routing.module';
import { ReservationComponent } from './reservation.component';
import { NgIconsModule } from '@ng-icons/core';
import {
  bootstrapGrid,
  bootstrapList,
  bootstrapPlus,
  bootstrapSearch,
} from '@ng-icons/bootstrap-icons';
import { CardModule } from '../../components/card/card.module';

@NgModule({
  declarations: [ReservationComponent],
  imports: [
    CommonModule,
    ReservationRoutingModule,
    NgIconsModule.withIcons({
      bootstrapGrid,
      bootstrapList,
      bootstrapPlus,
      bootstrapSearch,
    }),
    CardModule,
  ],
})
export class ReservationModule {}
