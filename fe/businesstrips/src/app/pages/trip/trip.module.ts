import { NgIconsModule } from '@ng-icons/core';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TripRoutingModule } from './trip-routing.module';
import { TripComponent } from './trip.component';
import { bootstrapPlus, bootstrapSearch } from '@ng-icons/bootstrap-icons';
import { CardModule } from '../../components/card/card.module';

@NgModule({
  declarations: [TripComponent],
  imports: [
    CommonModule,
    TripRoutingModule,
    NgIconsModule.withIcons({ bootstrapSearch, bootstrapPlus }),
    CardModule,
  ],
})
export class TripModule {}
