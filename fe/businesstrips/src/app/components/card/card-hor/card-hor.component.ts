import { Component, Input } from '@angular/core';
import { iEmployee } from '../../../interfaces/iemployee';
import { EmployeeService } from '../../../services/employee.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from '../../modal/modal.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-card-hor',
  templateUrl: './card-hor.component.html',
  styleUrl: './card-hor.component.scss',
})
export class CardHorComponent {
  constructor(
    private employeeSvc: EmployeeService,
    private modalService: NgbModal,
    private router: Router
  ) {}

  @Input() employee!: iEmployee;
  message!: string;
  isDetails: boolean = false;

  ngOnInit() {
    if (this.router.url.startsWith('/employee')) {
      this.isDetails = true;
    }
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
