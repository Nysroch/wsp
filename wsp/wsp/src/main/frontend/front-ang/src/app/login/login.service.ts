import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtToken } from './jwt-token.model';
import { Student } from '../student/student.model';
import { SERVER_API_URL } from '../app.constants';
import { UserCredentials } from './user-credentials.model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private http: HttpClient
  ) { }

  authenticate(userCredentials: UserCredentials): Observable<Student> {
    return this.http.post<Student>(`${SERVER_API_URL}/login`, userCredentials);
  }

  logout(): void {
    localStorage.removeItem('token');
  }
}
