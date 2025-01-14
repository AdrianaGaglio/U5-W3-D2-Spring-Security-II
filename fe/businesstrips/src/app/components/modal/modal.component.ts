import {
  Component,
  Input,
  TestabilityRegistry,
  ViewChild,
} from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { iEmployee } from '../../interfaces/iemployee';
import { UploadService } from '../../services/upload.service';
import { EmployeeService } from '../../services/employee.service';
import { iTrip } from '../../interfaces/itrip';
import { setThrowInvalidWriteToSignalError } from '@angular/core/primitives/signals';
import { environment } from '../../../environments/environment.development';
import { TripService } from '../../services/trip.service';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.scss',
})
export class ModalComponent {
  constructor(
    public activeModal: NgbActiveModal,
    private uploadSvc: UploadService,
    private employeeSvc: EmployeeService,
    private tripSvc: TripService
  ) {}

  @Input() employee: iEmployee | Partial<iEmployee> = {};
  @Input() trip: iTrip | Partial<iTrip> = {};
  @Input() isEmployee!: boolean;
  @Input() isTrip!: boolean;

  tripStatus: string[] = environment.tripStatus;

  selectedFile!: File;

  ngOnInit() {
    if ((this.trip && !this.employee) || this.trip.id) {
      this.isTrip = true;
    }
    if ((this.employee && !this.trip) || this.employee.id) {
      this.isEmployee = true;
    }
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  save() {
    if (!this.selectedFile) {
      this.employeeSvc.update(this.employee).subscribe((res) => {
        setTimeout(() => this.activeModal.close(), 1000);
      });
    } else {
      const formData = new FormData();
      formData.append('file', this.selectedFile);
      this.uploadSvc.upload(formData).subscribe((res) => {
        if (res) {
          this.employee.image = res;
          this.employeeSvc.update(this.employee).subscribe((res) => {
            setTimeout(() => this.activeModal.close(), 1000);
          });
          this.employeeSvc.getEmployees().subscribe();
        }
      });
    }
  }

  addEmployee() {
    if (!this.selectedFile) {
      this.employeeSvc.update(this.employee).subscribe((res) => {
        setTimeout(() => this.activeModal.close(), 1000);
      });
    } else {
      const formData = new FormData();
      formData.append('file', this.selectedFile);
      this.uploadSvc.upload(formData).subscribe((res) => {
        if (res) {
          this.employee.image = res;
          this.employeeSvc.create(this.employee).subscribe((res) => {
            setTimeout(() => this.activeModal.close(), 1000);
            this.employeeSvc.getEmployees().subscribe();
          });
        }
      });
    }
  }

  addTrip() {
    this.trip.date = this.trip.date?.toString().slice(0, 10);
    this.tripSvc.create(this.trip).subscribe((res) => {
      setTimeout(() => this.activeModal.close(), 1000);
    });
  }

  saveTrip() {
    this.tripSvc.update(this.trip).subscribe((res) => {
      setTimeout(() => this.activeModal.close(), 1000);
    });
  }

  close() {
    this.activeModal.close();
  }
}
