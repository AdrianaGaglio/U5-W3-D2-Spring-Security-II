import { AppModule } from './../../app.module';
import {
  Component,
  inject,
  signal,
  TemplateRef,
  WritableSignal,
} from '@angular/core';

import {
  NgbDatepickerModule,
  NgbOffcanvas,
  OffcanvasDismissReasons,
} from '@ng-bootstrap/ng-bootstrap';
import { PagetitleService } from '../../services/pagetitle.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent {
  constructor(
    private pageTitle: PagetitleService,
    private authSvc: AuthService
  ) {}

  title!: string;
  isLoggedIn: boolean = false;

  ngOnInit() {
    this.pageTitle.title.subscribe((title) => {
      this.title = title;
    });

    this.authSvc.isLoggedIn$.subscribe((res) => (this.isLoggedIn = res));
  }

  private offcanvasService = inject(NgbOffcanvas);
  closeResult: WritableSignal<string> = signal('');

  open(content: TemplateRef<any>) {
    this.offcanvasService
      .open(content, { ariaLabelledBy: 'offcanvas-basic-title' })
      .result.then(
        (result) => {
          this.closeResult.set(`Closed with: ${result}`);
        },
        (reason) => {
          this.closeResult.set(`Dismissed ${this.getDismissReason(reason)}`);
        }
      );
  }

  private getDismissReason(reason: any): string {
    switch (reason) {
      case OffcanvasDismissReasons.ESC:
        return 'by pressing ESC';
      case OffcanvasDismissReasons.BACKDROP_CLICK:
        return 'by clicking on the backdrop';
      default:
        return `with: ${reason}`;
    }
  }

  logout() {
    this.authSvc.logout();
  }
}
