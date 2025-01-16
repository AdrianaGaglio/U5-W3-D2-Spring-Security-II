import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { GuestGuard } from './guards/guest.guard';
import { AdminGuard } from './guards/admin.guard';
import { SameuseroradminGuard } from './guards/sameuseroradmin.guard';

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./pages/dashboard/dashboard.module').then(
        (m) => m.DashboardModule
      ),
    canActivate: [AuthGuard, AdminGuard],
  },
  {
    path: 'employees',
    loadChildren: () =>
      import('./pages/employees/employees.module').then(
        (m) => m.EmployeesModule
      ),
    canActivate: [AuthGuard, AdminGuard],
  },
  {
    path: 'trips',
    loadChildren: () =>
      import('./pages/trips/trips.module').then((m) => m.TripsModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'reservations',
    loadChildren: () =>
      import('./pages/reservation/reservation.module').then(
        (m) => m.ReservationModule
      ),
    canActivate: [AuthGuard, AdminGuard],
  },
  {
    path: 'employee/:id',
    loadChildren: () =>
      import('./pages/employee/employee.module').then((m) => m.EmployeeModule),
    canActivate: [AuthGuard, SameuseroradminGuard],
  },
  {
    path: 'trip/:id',
    loadChildren: () =>
      import('./pages/trip/trip.module').then((m) => m.TripModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
