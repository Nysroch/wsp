import { Component, OnInit } from '@angular/core';
import { MainService } from './main.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UniversityService } from '../university/university.service';
import { University } from '../university/university.model';
import { CommentService } from '../comments/comment.service';
import { SubjectService } from '../subjects/subject.service';
import { Subject } from '../subjects/subject.model';
import { Commentt } from '../comments/comment.model';
import {formatDate} from '@angular/common';
import { Router } from '@angular/router';
import { Student } from '../student/student.model';
import { Upvote } from '../comments/upvote.model';
import { Report } from '../comments/report.model';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent implements OnInit {
  mymodel = '';
  public uniAll: University[];
  public uniAllTemp: University[];
  public commAll: Commentt[];
  public subAll: Subject[];
  public top3Sub: Subject[];
  public test: String;
  public warningtext: String;
  public repText: String;
  public avgSubjRating: Number;
  public sortByAa: any;
  public sortBySt: any;
  commDay: Commentt = {} as Commentt;
  report: Report = {} as Report;
  upvoteReq: Upvote = {} as Upvote;
  loggedUser: Student =JSON.parse(localStorage.getItem('current-user') || '{}');

  constructor
  (
    private mainService: MainService,
    private universityService: UniversityService,
    private commentService: CommentService,
    private subjectService: SubjectService,
    private router: Router
    ) {
    this.test ="";
    this.repText ="";
    this.warningtext ="";
    this.uniAll = [];
    // this.uniAlls = [];
    this.uniAllTemp = [];
    this.commAll = [];
    this.subAll = [];
    this.top3Sub = [];
    this.avgSubjRating = 0;
    this.sortByAa = 0;
    this.sortBySt = 0;
   }

  ngOnInit(): void {
    this.getAllUni()
    this.getAllComments()
    this.getAllSubjects()
    this.getTopThreeSubjects()
    if(this.isUserLoggedIn()){
      this.getTopCommentStud(this.loggedUser.studentID)
    }else{
      this.getTopComment()
    }
    
  }

  public sortAa(): void {
    this.sortBySt=0;
    if(this.sortByAa !== 2){
      ++this.sortByAa;
    }else{
      this.sortByAa=0;
    }

    switch (this.sortByAa) {
      case 2:
          this.uniAll = this.uniAllTemp.sort((a,b) => b.name.localeCompare(a.name))
          break;
      default:
          this.uniAll = this.uniAllTemp.sort((a,b) => a.name.localeCompare(b.name)) 
          break;
  }
  }

  public sortSt(): void {
    this.sortByAa=0;
    if(this.sortBySt !== 2){
      ++this.sortBySt;
    }else{
      this.sortBySt=0;
    }
    switch (this.sortBySt) {
      case 2:
          this.uniAll = this.uniAllTemp.sort((a,b) => a.rating-b.rating)
          break;
      default:
          this.uniAll = this.uniAllTemp.sort((a,b) => b.rating-a.rating) 
          break;
  }
  }

  isUserLoggedIn(): boolean {
    if(localStorage.getItem('current-user') !== null){
      return true
    }else
    return false;
  }

  public datum(date: Date): string{
    return formatDate(date, 'dd/MM/yyyy', 'en-US');
  }

  // public numComm(subID:number): number{
  //   const count = this.commAll.filter((obj) => obj.subjectID === subID).length;
  //   return count;
  // }
  public ifBigComment (): boolean{
    if(this.commDay.description.length > 500){
      return true;
    }else
    return false;
  }
  public routeUni(uniId: number){
    this.router.navigate(['/university-component', uniId]);
  }
  public routeSubj(subjID: number){
    this.router.navigate(['/subject-component', subjID]);
  }

  public getTopCommentStud(registeredStudentID: number): void{
    this.commentService.getTopCommentStud(registeredStudentID).subscribe(
      (response: Commentt) =>{
        this.commDay = response;
      },
      (error: HttpErrorResponse)=>{
        console.log("err get commDaySt")
      }
    )
  }

  public getTopComment(): void{
    this.commentService.getTopComment().subscribe(
      (response: Commentt) =>{
        this.commDay = response;
      },
      (error: HttpErrorResponse)=>{
        console.log("err get commDay")
      }
    )
  }

  public getAllUni(): void{
    this.universityService.getAllUni().subscribe(
      (response: University[]) =>{
        this.uniAll = response;
        this.uniAllTemp = response;
      }, 
      (error: HttpErrorResponse)=>{
        console.log("err get uniAll")
      }
    )
  }

  public getAllComments(): void{
    this.commentService.getAllComments().subscribe(
      (response: Commentt[]) =>{
        this.commAll = response;
      },
      (error: HttpErrorResponse)=>{
        console.log("err get commAll")
      }
    )
  }

  public getAllSubjects(): void{
    this.subjectService.getAllSubjects().subscribe(
      (response: Subject[]) =>{
        this.subAll = response;
      },
      (error: HttpErrorResponse)=>{
        console.log("err get subAll")
      }
    )
  }

  public getTopThreeSubjects(): void{
    this.subjectService.getTopThreeSubjects().subscribe(
      (response: Subject[]) =>{
        this.top3Sub = response;
        console.log(response)
      },
      (error: HttpErrorResponse)=>{
        console.log("err get subAll")
      }
    )
  }

  valuechange(searchValue: any): void {  
    console.log(this.mymodel)
    this.mymodel = this.mymodel.toLowerCase();
    this.uniAll = this.uniAllTemp.filter(s => s.name.toLowerCase().includes(this.mymodel));
  }

  // pozvati backend da se upvota/makne upvote
  upvoteTopComm( commentId : number): void {
    if(this.commDay.upvoted){
      this.commDay.upvoted= false;

      this.upvoteReq.studentID = this.loggedUser.studentID;
      this.upvoteReq.commentID = commentId;
        this.commentService.deleteUpvote(this.upvoteReq.commentID, this.loggedUser.studentID).subscribe(
          (response: void) =>{
            console.log("deleteUpvote")
            this.getTopCommentStud(this.loggedUser.studentID)
            console.log(this.commDay.upvoted)
          },
          (error: HttpErrorResponse)=>{
            console.log("err downvote")
          }
        )

      this.getTopCommentStud(this.loggedUser.studentID)
    }else{
      this.commDay.upvoted= true;

      this.upvoteReq.studentID = this.loggedUser.studentID;
      this.upvoteReq.commentID = commentId;
        this.commentService.upvoteComment(this.upvoteReq).subscribe(
          (response: Upvote) =>{
            console.log(response)
            this.getTopCommentStud(this.loggedUser.studentID)
            console.log(this.commDay.upvoted)
          },
          (error: HttpErrorResponse)=>{
            console.log("err upvoteComment")
          }
        )
      // this.commDay.upvote = this.commDay.upvote+1;
    }
  }

  //pozvati metodu za report
  reportTopComm(): void {
    console.log(this.report.reason)
    this.report.studentID = this.loggedUser.studentID;
    this.report.commentID = this.commDay.commentID; 
    this.commentService.reportComment(this.report).subscribe(
      (response: Report) =>{
        this.getTopCommentStud(this.loggedUser.studentID)
        this.textareaclear();
      },
      (error: HttpErrorResponse)=>{
        console.log("err reportTopComm")
      }
    )
  }

  textarea(): void {
    this.warningtext="Reason must not be empty!"
  }
  textareaclear(): void {
    this.warningtext=""
  }
}
