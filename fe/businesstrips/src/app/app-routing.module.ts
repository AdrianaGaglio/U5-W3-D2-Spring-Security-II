import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./pages/dashboard/dashboard.module').then(
        (m) => m.DashboardModule
      ),
  },
  {
    path: 'employees',
    loadChildren: () =>
      import('./pages/employees/employees.module').then(
        (m) => m.EmployeesModule
      ),
  },
  {
    path: 'trips',
    loadChildren: () =>
      import('./pages/trips/trips.module').then((m) => m.TripsModule),
  },
  {
    path: 'reservations',
    loadChildren: () =>
      import('./pages/reservation/reservation.module').then(
        (m) => m.ReservationModule
      ),
  },
  {
    path: 'employee/:id',
    loadChildren: () =>
      import('./pages/employee/employee.module').then((m) => m.EmployeeModule),
  },
  {
    path: 'trip/:id',
    loadChildren: () =>
      import('./pages/trip/trip.module').then((m) => m.TripModule),
  },
  { path: 'auth', loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule) },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
