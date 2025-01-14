import { iEmployee } from './iemployee';
import { iPreference } from './ipreference';
import { iTrip } from './itrip';

export interface IReservationresponse {
  id: number;
  requestDate: string;
  preferences: iPreference[];
  trip: iTrip;
  employee: iEmployee;
}
