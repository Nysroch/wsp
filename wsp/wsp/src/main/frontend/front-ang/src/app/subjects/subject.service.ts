import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from '../app.constants';
import { register } from '../login/register';
import { Subject } from './subject.model';


@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  private usersUrl = `${SERVER_API_URL}/api/user`;

  constructor(private http: HttpClient) { }

  public getAllSubjects(): Observable<Subject[]>{
    return this.http.get<Subject[]>(`http://localhost:8080/rest/subject/getList`)
  }

  public getTopThreeSubjects(): Observable<Subject[]>{
    return this.http.get<Subject[]>(`http://localhost:8080/rest/subject/getTopSubjects`)
  }

  public getSubjectsByUniID(uniId : number): Observable<Subject[]>{
    return this.http.get<Subject[]>(`http://localhost:8080/rest/subject/getListByUC?universityID=${uniId}`)
  }

  public getSubjectByID(subjId : number): Observable<Subject>{
    return this.http.get<Subject>(`http://localhost:8080/rest/subject/get/${subjId}`)
  }

}