import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TripsRoutingModule } from './trips-routing.module';
import { TripsComponent } from './trips.component';
import { NgIconsModule } from '@ng-icons/core';
import {
  bootstrapGrid,
  bootstrapList,
  bootstrapPlus,
  bootstrapSearch,
} from '@ng-icons/bootstrap-icons';
import { CardModule } from '../../components/card/card.module';

@NgModule({
  declarations: [TripsComponent],
  imports: [
    CommonModule,
    TripsRoutingModule,
    NgIconsModule.withIcons({
      bootstrapList,
      bootstrapGrid,
      bootstrapSearch,
      bootstrapPlus,
    }),
    CardModule,
  ],
  exports: [TripsComponent],
})
export class TripsModule {}
