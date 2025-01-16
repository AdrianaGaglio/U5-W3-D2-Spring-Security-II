import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalComponent } from './modal.component';
import { FormsModule, NgModel, ReactiveFormsModule } from '@angular/forms';
import { ResModalComponent } from './res-modal/res-modal.component';

@NgModule({
  declarations: [ModalComponent, ResModalComponent],
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  exports: [ModalComponent, ResModalComponent],
})
export class ModalModule {}
