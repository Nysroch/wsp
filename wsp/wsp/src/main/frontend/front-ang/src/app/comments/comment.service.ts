import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from '../app.constants';
import { register } from '../login/register';
import { Commentt } from './comment.model';
import { Upvote } from './upvote.model';
import { Report } from './report.model';


@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private usersUrl = `${SERVER_API_URL}/api/user`;

  constructor(private http: HttpClient) { }

  public getAllComments(): Observable<Commentt[]>{
    return this.http.get<Commentt[]>(`http://localhost:8080/rest/comment/getList`)
  }

  public getTopComment(): Observable<Commentt>{
    return this.http.get<Commentt>(`http://localhost:8080/rest/comment/getTopComment`)
  }

  public getTopCommentStud(registeredStudentID: number): Observable<Commentt>{
    return this.http.get<Commentt>(`http://localhost:8080/rest/comment/getTopComment?registeredStudentID=${registeredStudentID}`)
  }
  public getCommentsByUniID(uniId: number): Observable<Commentt[]>{
    return this.http.get<Commentt[]>(`http://localhost:8080/rest/comment/getListByUC?universityID=${uniId}`)
  }
  public getCommentsBySubjID(subID: number): Observable<Commentt[]>{
    return this.http.get<Commentt[]>(`http://localhost:8080/rest/comment/getListByUC?subjectID=${subID}`)
  }
  public getCommentsByStudID(studID: number): Observable<Commentt[]>{
    return this.http.get<Commentt[]>(`http://localhost:8080/rest/comment/getListByUC?studentID=${studID}`)
  }

  public getCommentsByUniIDSt(uniId: number, stID : number): Observable<Commentt[]>{
    return this.http.get<Commentt[]>(`http://localhost:8080/rest/comment/getListByUC?universityID=${uniId}&registeredStudentID=${stID}`)
  }
  public getCommentsBySubjIDSt(subID: number, stID: number): Observable<Commentt[]>{
    return this.http.get<Commentt[]>(`http://localhost:8080/rest/comment/getListByUC?subjectID=${subID}&registeredStudentID=${stID}`)
  }
  public upvoteComment(upvote: Upvote): Observable<Upvote> {
    return this.http.post<Upvote>(`${SERVER_API_URL}/rest/upvote/upvote`, upvote);
  }

  public deleteUpvote(commID: number, userId: number): Observable<void> {
    return this.http.delete<void>(`${SERVER_API_URL}/rest/upvote/delete?commentID=${commID}&studentID=${userId}`);
  }

  public reportComment(rep: Report): Observable<Report> {
    return this.http.post<Report>(`${SERVER_API_URL}/rest/report/insert`,rep);
  }

  public insertComment(comm: Commentt): Observable<Commentt> {
    return this.http.post<Commentt>(`${SERVER_API_URL}/rest/comment/insert`,comm);
  }

  public deleteComment(commID: number): Observable<void> {
    return this.http.delete<void>(`${SERVER_API_URL}/rest/comment/delete?commentID=${commID}`);
  }

}