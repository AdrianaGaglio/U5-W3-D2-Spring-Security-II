import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { iEmployee } from '../interfaces/iemployee';
import { iPagedEmployees } from '../interfaces/ipagedemployees';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  constructor(private http: HttpClient) {}

  url: string = environment.employeeUrl;
  employees$ = new BehaviorSubject<iEmployee[]>([]);

  getEmployees(): Observable<iEmployee[]> {
    return this.http
      .get<iEmployee[]>(this.url)
      .pipe(tap((employees) => this.employees$.next(employees)));
  }

  getPagedEmployees(page: number, size: number): Observable<iPagedEmployees> {
    return this.http
      .get<iPagedEmployees>(
        `${this.url}/paged?page=${page}&size=${size}&sort=lastName`
      )
      .pipe(tap((result) => this.employees$.next(result.content)));
  }

  getById(id: number): Observable<iEmployee> {
    return this.http.get<iEmployee>(`${this.url}/${id}`);
  }

  create(employee: Partial<iEmployee>): Observable<iEmployee> {
    return this.http
      .post<iEmployee>(this.url, employee)
      .pipe(
        tap((res) => this.employees$.next([...this.employees$.getValue(), res]))
      );
  }

  delete(employee: iEmployee): Observable<string> {
    return this.http
      .delete<string>(`${this.url}/${employee.id}`, {
        responseType: 'text' as 'json',
      })
      .pipe(
        tap((res) =>
          this.getEmployees().subscribe((employees) =>
            this.employees$.next(
              this.employees$.getValue().filter((emp) => emp.id !== employee.id)
            )
          )
        )
      );
  }

  update(employee: Partial<iEmployee>): Observable<iEmployee> {
    return this.http
      .put<iEmployee>(`${this.url}/${employee.id}`, employee)
      .pipe(tap((result) => this.getEmployees().subscribe()));
  }

  search(name: string): Observable<iEmployee[]> {
    return this.http.get<iEmployee[]>(`${this.url}/search?name=${name}`);
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.url}/count`);
  }
}
