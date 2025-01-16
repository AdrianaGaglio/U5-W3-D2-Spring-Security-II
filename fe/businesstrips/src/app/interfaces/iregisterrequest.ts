import { iEmployeecreaterequest } from './iemployeecreaterequest';

export interface iRegisterrequest {
  username: string;
  email: string;
  password: string;
  employeeRequest: iEmployeecreaterequest;
}
