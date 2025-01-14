import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from './sidebar.component';

import { NgIconsModule } from '@ng-icons/core';
import { bootstrapList } from '@ng-icons/bootstrap-icons';
import { RouterLink } from '@angular/router';

@NgModule({
  declarations: [SidebarComponent],
  imports: [
    CommonModule,
    NgIconsModule.withIcons({ bootstrapList }),
    RouterLink,
  ],
  exports: [SidebarComponent],
})
export class SidebarModule {}
