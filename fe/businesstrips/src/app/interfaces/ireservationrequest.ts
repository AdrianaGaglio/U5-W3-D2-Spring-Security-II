import { iPreference } from './ipreference';

export interface iReservationrequest {
  tripId: number;
  requestDate: string;
  employeeId: number;
  preferences: Partial<iPreference>[];
}
