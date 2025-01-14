import { iEmployee } from './iemployee';
import { iPreference } from './ipreference';
import { iTrip } from './itrip';

export interface iReservation {
  id: number;
  trip: iTrip;
  requestDate: string;
  employee: iEmployee;
  preferences: iPreference[];
}
