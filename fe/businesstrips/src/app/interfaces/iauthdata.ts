import { iEmployee } from './iemployee';

export interface iAuthdata {
  token: string;
  user: iEmployee;
}
