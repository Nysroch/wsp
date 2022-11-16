import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from '../app.constants';
import { register } from '../login/register';
import { University } from './university.model';


@Injectable({
  providedIn: 'root'
})
export class UniversityService {

  private usersUrl = `${SERVER_API_URL}/api/user`;

  constructor(private http: HttpClient) { }

  public getUniByID(universityID: Number): Observable<University> {
    return this.http.get<University>(`http://localhost:8080/rest/university/get/${universityID}`)
  }

  public getAllUni(): Observable<University[]>{
    return this.http.get<University[]>(`http://localhost:8080/rest/university/getList`)
  }

}
