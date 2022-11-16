import { formatDate } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Commentt } from '../comments/comment.model';
import { CommentService } from '../comments/comment.service';
import { Report } from '../comments/report.model';
import { Upvote } from '../comments/upvote.model';
import { Student } from '../student/student.model';
import { University } from '../university/university.model';
import { UniversityService } from '../university/university.service';
import { Subject } from './subject.model';
import { SubjectService } from './subject.service';

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.css']
})
export class SubjectsComponent implements OnInit {
  student: Student =JSON.parse(localStorage.getItem('current-user') || '{}');

  public subID: string;
  subject: Subject = {} as Subject;
  uni: University = {} as University;
  public comSub: Commentt[];
  public tempComSub: Commentt[];
  public warningtext: String;
  public warningPost: String;
  upvoteReq: Upvote = {} as Upvote;
  public commID: number;
  report: Report = {} as Report;
  myComment: Commentt = {} as Commentt ;
  newComment: Commentt = {} as Commentt ;
  emptyComment: Commentt = {} as Commentt ;
  public sortByAaC: any;
  public sortByStC: any;
  public sortByDateC: any;
  public sortByUpC: any;
  constructor( private _Activatedroute: ActivatedRoute, 
    private _router: Router, 
    public sanitizer: DomSanitizer, 
    private subjectService: SubjectService,
    private universityService: UniversityService,
    private commentService: CommentService, ) {
      this.comSub = [];
      this.tempComSub = [];
      this.subID = "";
      this.warningPost = "";
      this.warningtext ="";
      this.commID = 0;
      this.sortByAaC = 0;
      this.sortByStC = 0;
      this.sortByDateC = 0;
      this.sortByUpC = 0;
     }

  ngOnInit(): void {
    this._Activatedroute.paramMap.subscribe(params => { 
      this.subID = params.get('id')!; 
      console.log( this.subID)
      this.getSubjectByID(Number(this.subID));
      this.getCommentsBySubjID(Number(this.subID));
      if(this.isUserLoggedIn()){
        this.getCommentsBySubjIDSt(Number(this.subID), this.student.studentID);
      }else{
        this.getCommentsBySubjID(Number(this.subID));
      }
 });
  }
  public sortAaC(): void {
    this.sortByUpC = 0;
    this.sortByStC = 0;
    this.sortByDateC = 0;
    if (this.sortByAaC !== 2) {
      ++this.sortByAaC;
    } else {
      this.sortByAaC = 0;
    }
    switch (this.sortByAaC) {
      case 2:
        this.comSub = this.tempComSub.sort((a, b) => b.description.localeCompare(a.description))
        break;
      default:
        this.comSub = this.tempComSub.sort((a, b) => a.description.localeCompare(b.description))
        break;
    }
  }

  public sortUpC(): void {
    this.sortByAaC = 0;
    this.sortByDateC = 0;
    this.sortByStC = 0;
    if (this.sortByUpC !== 2) {
      ++this.sortByUpC;
    } else {
      this.sortByUpC = 0;
    }
    switch (this.sortByUpC) {
      case 2:
        this.comSub = this.tempComSub.sort((a, b) => a.upvote - b.upvote)
        break;
      default:
        this.comSub = this.tempComSub.sort((a, b) => b.upvote - a.upvote)
        break;
    }
  }

  public sortStC(): void {
    this.sortByUpC = 0;
    this.sortByAaC = 0;
    this.sortByDateC = 0;
    if (this.sortByStC !== 2) {
      ++this.sortByStC;
    } else {
      this.sortByStC = 0;
    }
    switch (this.sortByStC) {
      case 2:
        this.comSub = this.tempComSub.sort((a, b) => a.rating - b.rating)
        break;
      default:
        this.comSub = this.tempComSub.sort((a, b) => b.rating - a.rating)
        break;
    }
  }
  public sortDateC(): void {
    this.sortByUpC = 0;
    this.sortByStC = 0;
    this.sortByAaC = 0;
    if (this.sortByDateC !== 2) {
      ++this.sortByDateC;
    } else {
      this.sortByDateC = 0;
    }
    switch (this.sortByDateC) {
      case 2:
        this.comSub = this.tempComSub.sort(function (a, b) {
          var dateA = new Date(a.dateEdited), dateB = new Date(b.dateEdited)
          return dateA.getTime() - dateB.getTime()
        });
        break;
      default:
        this.comSub = this.tempComSub.sort(function (a, b) {
          var dateA = new Date(a.dateEdited), dateB = new Date(b.dateEdited)
          return dateB.getTime() - dateA.getTime()
        });
        break;
    }
  }
  public getSubjectByID(subID : number): void{
    this.subjectService.getSubjectByID(subID).subscribe(
      (response: Subject) =>{
        this.subject = response;
        this.getUniByID(Number(this.subject.universityID));
      },
      (error: HttpErrorResponse)=>{
        console.log("err get sub")
      }
    )
  }

  public getUniByID(universityID : number): void{
    this.universityService.getUniByID(universityID).subscribe(
      (response: University) =>{
        this.uni = response;
      },
      (error: HttpErrorResponse)=>{
        console.log("err get un2")
      }
    )
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

  public getCommentsBySubjID(uniId : number): void{
    this.commentService.getCommentsBySubjID(uniId).subscribe(
      (response: Commentt[]) =>{
        this.comSub = response;
        this.tempComSub = response;
      },
      (error: HttpErrorResponse)=>{
        console.log("err get un2")
      }
    )
  }

  public getCommentsBySubjIDSt(uniId : number, stID:number): void{
    this.commentService.getCommentsBySubjIDSt(uniId,stID).subscribe(
      (response: Commentt[]) =>{
        this.comSub = response;
        this.tempComSub = response;
        this.excludeMyComment()
      },
      (error: HttpErrorResponse)=>{
        console.log("err get un2")
      }
    )
  }

  public routeUni(uniId: number){
    this._router.navigate(['/university-component', uniId]);
  }


  reportComm(): void {
    console.log(this.report.reason)
    this.report.studentID = this.student.studentID;
    this.report.commentID = this.commID; 
    this.commentService.reportComment(this.report).subscribe(
      (response: Report) =>{
        // this.getTopCommentStud(this.loggedUser.studentID)
        this.getCommentsBySubjIDSt(Number(this.subID), this.student.studentID);
        this.report.reason="";
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
  
  setCommId(id:number): void {
    this.commID=id; 
  }
  
  upvoteTopComm( comm : Commentt): void {
    if(comm.upvoted){
      comm.upvoted= false;
  
      this.upvoteReq.studentID = this.student.studentID;
      this.upvoteReq.commentID = comm.commentID;
        this.commentService.deleteUpvote(this.upvoteReq.commentID, this.student.studentID).subscribe(
          (response: void) =>{
            console.log("deleteUpvote")
            this.getCommentsBySubjIDSt(Number(this.subID), this.student.studentID);
          },
          (error: HttpErrorResponse)=>{
            console.log("err downvote")
          }
        )
  
        this.getCommentsBySubjIDSt(Number(this.subID), this.student.studentID);
    }else{
      comm.upvoted= true;
  
      this.upvoteReq.studentID = this.student.studentID;
      this.upvoteReq.commentID = comm.commentID;
        this.commentService.upvoteComment(this.upvoteReq).subscribe(
          (response: Upvote) =>{
            console.log(response)
            this.getCommentsBySubjIDSt(Number(this.subID), this.student.studentID);
          },
          (error: HttpErrorResponse)=>{
            console.log("err upvoteComment")
          }
        )
      // this.commDay.upvote = this.commDay.upvote+1;
    }
  }
  

  stars: number[] = [1, 2, 3, 4, 5];
  newCommRating: number = 0;
  countStar(star: number) {
    this.newCommRating = star;
    console.log('Value of star', star);
  }
  
  postComment (){
    if(this.newCommRating === 0){
      this.warningPost="Please enter University rating via number of stars";
    }
    // else if(this.newComment.description === "" || this.newComment.description === undefined){
    //   this.warningPost="Please enter University comment";
    // }
    else{
      if(this.newComment.description === "" || this.newComment.description === undefined){
          this.newComment.description=" ";
        }
      this.warningPost="";
      this.newComment.rating = this.newCommRating;
      this.newComment.studentID = this.student.studentID;
      this.newComment.subjectID = Number(this.subID);
      
      this.commentService.insertComment(this.newComment).subscribe(
        (response: Commentt) =>{
          console.log(response)
          this.getCommentsBySubjIDSt(Number(this.subID), this.student.studentID);
          this.getSubjectByID(Number(this.subID));
          this.getCommentsBySubjID(Number(this.subID));
          this.newComment.commentID = null!;
        },
        (error: HttpErrorResponse)=>{
          console.log("err postComment")
        }
      )
    }
  }
  
  edit (){
    this.newCommRating =this.myComment.rating;
    this.newComment.description = this.myComment.description;
    this.newComment.commentID = this.myComment.commentID;
    this.myComment = this.emptyComment;
    console.log("asd"+this.myComment.description)
  }
  deleteComment (){
  
      this.commentService.deleteComment(this.myComment.commentID).subscribe(
        (response: void) =>{
          this.newComment.description="";
          this.newCommRating=0;
          console.log(response)
          this.getCommentsBySubjIDSt(Number(this.subID), this.student.studentID);
          this.getSubjectByID(Number(this.subID));
          this.getCommentsBySubjID(Number(this.subID));
        },
        (error: HttpErrorResponse)=>{
          console.log("err upvoteComment")
        }
      )
      }
      public excludeMyComment(){

        this.myComment = this.comSub.find(s => s.studentID === this.student.studentID)!;
        console.log(this.myComment)
        if(this.myComment !== undefined){
             this.comSub = this.comSub.filter(item => item !== this.myComment);
             this.tempComSub = this.tempComSub.filter(item => item !== this.myComment);
        }
    
      }
    
}
