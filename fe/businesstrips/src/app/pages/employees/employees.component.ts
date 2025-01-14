import { Component } from '@angular/core';
import { PagetitleService } from '../../services/pagetitle.service';
import { EmployeeService } from '../../services/employee.service';
import { iEmployee } from '../../interfaces/iemployee';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from '../../components/modal/modal.component';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrl: './employees.component.scss',
})
export class EmployeesComponent {
  constructor(
    private pageTitle: PagetitleService,
    private employeeSvc: EmployeeService,
    private modalService: NgbModal
  ) {}

  employees!: iEmployee[];
  page: number = 0;
  iterations: number[] = [];
  isGrid: boolean = true;
  text: string = '';

  ngOnInit() {
    this.pageTitle.title.next('Manage employees');
    this.employeeSvc.employees$.subscribe((res) => {
      this.employees = res;
    });
    this.employeeSvc.getPagedEmployees(this.page, 10).subscribe((result) => {
      this.iterations = Array(result.totalPages);
    });
  }

  grid() {
    this.employeeSvc.getPagedEmployees(this.page, 10).subscribe((result) => {
      this.employees = result.content;
    });
    this.isGrid = true;
  }

  list() {
    this.isGrid = false;
  }

  changePage(num: number) {
    this.page = num;
    this.employeeSvc.getPagedEmployees(this.page, 10).subscribe((result) => {
      this.employees = result.content;
    });
  }

  openModal(isEmployee: boolean) {
    const modalRef = this.modalService.open(ModalComponent, { centered: true });
    modalRef.componentInstance.isEmployee = isEmployee;
  }

  search(text: string) {
    this.employeeSvc.search(text).subscribe((res) => {
      this.employees = res;
      this.isGrid = false;
    });
  }
}
