import { Component, inject, Input, TemplateRef } from '@angular/core';
import { iEmployee } from '../../../interfaces/iemployee';
import { EmployeeService } from '../../../services/employee.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from '../../modal/modal.component';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss',
})
export class CardComponent {
  constructor(
    private employeeSvc: EmployeeService,
    private modalService: NgbModal,
    private authSvc: AuthService
  ) {}
  message!: string;
  isAdmin: boolean = false;

  @Input() employee!: iEmployee;

  ngOnInit() {
    this.authSvc.isAdmin$.subscribe((res) => (this.isAdmin = res));
    console.log(this.isAdmin);
  }

  delete(employee: iEmployee) {
    this.employeeSvc
      .delete(employee)
      .subscribe((res) => (this.message = 'Employee deleted successfully!'));
  }

  openModal(employee: iEmployee) {
    const modalRef = this.modalService.open(ModalComponent, { centered: true });
    modalRef.componentInstance.employee = employee;
  }
}
