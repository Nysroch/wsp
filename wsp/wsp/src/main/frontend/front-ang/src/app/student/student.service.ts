import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from './student.model';
import { SERVER_API_URL } from '../app.constants';
import { register } from '../login/register';


@Injectable({
  providedIn: 'root'
})
export class StudentService {

  currentUser: Student= {} as Student;

  private usersUrl = `${SERVER_API_URL}/api/user`;

  constructor(private http: HttpClient) { }

//   getCurrentUser(): Observable<Student> {
//     return this.http.get<Student>(`${this.usersUrl}/current-user`);
//     return localStorage
//   }

//   isRoleAdmin(): boolean {
//     if (this.currentUser) {
//       return this.currentUser.authorities.some((authority: string) => authority === Authority.ADMIN);
//     } else {
//       return false;
//     }
//   }

//   public addss(ss: string): Observable<string>{
//     return this.http.post<string>(`http://localhost:8080/ss`,ss)
//   }

  public save(student: register): Observable<Number> {
    return this.http.post<Number>(`http://localhost:8080/rest/student/register`, student);
  }
 

}
