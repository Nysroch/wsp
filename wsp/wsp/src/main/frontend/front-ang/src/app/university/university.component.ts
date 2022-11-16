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
import { Subject } from '../subjects/subject.model';
import { SubjectService } from '../subjects/subject.service';
import { University } from './university.model';
import { UniversityService } from './university.service';
@Component({
  selector: 'app-university',
  templateUrl: './university.component.html',
  styleUrls: ['./university.component.css']
})
export class UniversityComponent implements OnInit {

  public uniId: string;
   student: Student =JSON.parse(localStorage.getItem('current-user') || '{}');
   university: University = {} as University;
   public subUni: Subject[];
   public comUni: Commentt[];
   public tempSubUni: Subject[];
   public tempComUni: Commentt[];
   public warningtext: String;
   public warningPost: String;
   upvoteReq: Upvote = {} as Upvote;
   public commID: number;
   public sortByAa: any;
   public sortBySt: any;
   public sortByAaC: any;
   public sortByStC: any;
   public sortByDateC: any;
   public sortByUpC: any;
   report: Report = {} as Report;
   myComment: Commentt = {} as Commentt ;
   newComment: Commentt = {} as Commentt ;
   emptyComment: Commentt = {} as Commentt ;
   
  constructor(
    private universityService: UniversityService, 
    private _Activatedroute: ActivatedRoute, 
    private _router: Router, 
    public sanitizer: DomSanitizer, 
    private subjectService: SubjectService,
    private commentService: CommentService,) 
     {
    this.warningPost = "";
    this.uniId = "";
    this.subUni = [];
    this.comUni = [];
    this.tempSubUni = [];
    this.tempComUni = [];
    this.warningtext ="";
    this.commID = 0;
    this.sortByAa = 0;
    this.sortBySt = 0;
    this.sortByAaC = 0;
    this.sortByStC = 0;
    this.sortByDateC = 0;
    this.sortByUpC = 0;
  } 

  ngOnInit(): void {
    this._Activatedroute.paramMap.subscribe(params => { 
      this.warningPost="";
      this.uniId = params.get('id')!; 
      console.log(this.uniId)
      this.getUniByID(Number(this.uniId));
      this.getSubjectsByUniID(Number(this.uniId));
      
      if(this.isUserLoggedIn()){
        this.getCommentsByUniIDSt(Number(this.uniId), this.student.studentID);
      }else{
        this.getCommentsByUniID(Number(this.uniId));
      }
 });

//  alert(this.uniId)
     
  }

  public sortAa(): void {
    this.sortBySt = 0;
    if (this.sortByAa !== 2) {
      ++this.sortByAa;
    } else {
      this.sortByAa = 0;
    }
    switch (this.sortByAa) {
      case 2:
        this.subUni = this.tempSubUni.sort((a, b) => b.name.localeCompare(a.name))
        break;
      default:
        this.subUni = this.tempSubUni.sort((a, b) => a.name.localeCompare(b.name))
        break;
    }
  }

  public sortSt(): void {
    this.sortByAa = 0;
    if (this.sortBySt !== 2) {
      ++this.sortBySt;
    } else {
      this.sortBySt = 0;
    }
    switch (this.sortBySt) {
      case 2:
        this.subUni = this.tempSubUni.sort((a, b) => a.rating - b.rating)
        break;
      default:
        this.subUni = this.tempSubUni.sort((a, b) => b.rating - a.rating)
        break;
    }
  }

  
  public sortAaC(): void {
    this.sortByStC = 0;
    this.sortByDateC = 0;
    this.sortByUpC = 0;
    if (this.sortByAaC !== 2) {
      ++this.sortByAaC;
    } else {
      this.sortByAaC = 0;
    }
    switch (this.sortByAaC) {
      case 2:
        this.comUni = this.tempComUni.sort((a, b) => b.description.localeCompare(a.description))
        break;
      default:
        this.comUni = this.tempComUni.sort((a, b) => a.description.localeCompare(b.description))
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
        this.comUni = this.tempComUni.sort((a, b) => a.upvote - b.upvote)
        break;
      default:
        this.comUni = this.tempComUni.sort((a, b) => b.upvote - a.upvote)
        break;
    }
  }

  public sortStC(): void {
    this.sortByAaC = 0;
    this.sortByUpC = 0;
    this.sortByDateC = 0;
    if (this.sortByStC !== 2) {
      ++this.sortByStC;
    } else {
      this.sortByStC = 0;
    }
    switch (this.sortByStC) {
      case 2:
        this.comUni = this.tempComUni.sort((a, b) => a.rating - b.rating)
        break;
      default:
        this.comUni = this.tempComUni.sort((a, b) => b.rating - a.rating)
        break;
    }
  }
  public sortDateC(): void {
    this.sortByStC = 0;
    this.sortByAaC = 0;
    this.sortByUpC = 0;
    if (this.sortByDateC !== 2) {
      ++this.sortByDateC;
    } else {
      this.sortByDateC = 0;
    }
    switch (this.sortByDateC) {
      case 2:
        this.comUni = this.tempComUni.sort(function (a, b) {
          var dateA = new Date(a.dateEdited), dateB = new Date(b.dateEdited)
          return dateA.getTime() - dateB.getTime()
        });
        break;
      default:
        this.comUni = this.tempComUni.sort(function (a, b) {
          var dateA = new Date(a.dateEdited), dateB = new Date(b.dateEdited)
          return dateB.getTime() - dateA.getTime()
        });
        break;
    }
  }

  public getUniByID(uniId : number): void{
    this.universityService.getUniByID(uniId).subscribe(
      (response: University) =>{
        this.university = response;
        // console.log(response)
      },
      (error: HttpErrorResponse)=>{
        console.log("err get getUniByID")
      }
    )
  }

  public getSubjectsByUniID(uniId : number): void{
    this.subjectService.getSubjectsByUniID(uniId).subscribe(
      (response: Subject[]) =>{
        // console.log(response)
        this.subUni = response;
        this.tempSubUni = response;
      },
      (error: HttpErrorResponse)=>{
        console.log("err get getSubjectsByUniID")
      }
    )
  }

  public getCommentsByUniIDSt(uniId : number, studId: number): void{
    this.commentService.getCommentsByUniIDSt(uniId, studId).subscribe(
      (response: Commentt[]) =>{
        this.comUni = response;
        this.tempComUni = response;
        console.log(response)
        this.excludeMyComment();
      },
      (error: HttpErrorResponse)=>{
        console.log("err get getCommentsByUniIDSt")
      }
    )
  }
  public excludeMyComment(){

    this.myComment = this.comUni.find(s => s.studentID === this.student.studentID)!;
    console.log(this.myComment)
    if(this.myComment !== undefined){
         this.comUni = this.comUni.filter(item => item !== this.myComment);
         this.tempComUni = this.tempComUni.filter(item => item !== this.myComment);
    }
  }


  public getCommentsByUniID(uniId : number): void{
    this.commentService.getCommentsByUniID(uniId).subscribe(
      (response: Commentt[]) =>{
        this.comUni = response;
        this.tempComUni = response;
        console.log(response)
      },
      (error: HttpErrorResponse)=>{
        console.log("err get getCommentsByUniID")
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
  public routeSubj(subjID: number){
    this._router.navigate(['/subject-component', subjID]);
  }
 //pozvati metodu za report
 reportComm(): void {
  this.report.studentID = this.student.studentID;
  this.report.commentID = this.commID; 
  console.log(this.report.reason+ this.report.commentID)
  this.commentService.reportComment(this.report).subscribe(
    (response: Report) =>{
      // this.getTopCommentStud(this.loggedUser.studentID)
      this.getCommentsByUniIDSt(Number(this.uniId), this.student.studentID);
      this.textareaclear();
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
          this.getCommentsByUniIDSt(Number(this.uniId), this.student.studentID);
        },
        (error: HttpErrorResponse)=>{
          console.log("err downvote")
        }
      )

      this.getCommentsByUniIDSt(Number(this.uniId), this.student.studentID);
  }else{
    comm.upvoted= true;

    this.upvoteReq.studentID = this.student.studentID;
    this.upvoteReq.commentID = comm.commentID;
      this.commentService.upvoteComment(this.upvoteReq).subscribe(
        (response: Upvote) =>{
          console.log(response)
          this.getCommentsByUniIDSt(Number(this.uniId), this.student.studentID);
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
  else {
    if (this.newComment.description === "" || this.newComment.description === undefined) {
      this.newComment.description = " ";
    }
    this.warningPost = "";
    this.newComment.rating = this.newCommRating;
    this.newComment.studentID = this.student.studentID;
    this.newComment.universityID = Number(this.uniId);
    
    this.commentService.insertComment(this.newComment).subscribe(
      (response: Commentt) =>{
        console.log(response)
        this.getCommentsByUniIDSt(Number(this.uniId), this.student.studentID);
        this.getUniByID(Number(this.uniId));
        this.getSubjectsByUniID(Number(this.uniId));
        this.newComment.commentID = null!;
      },
      (error: HttpErrorResponse)=>{
        console.log("err upvoteComment")
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
        // this.newComment = this.emptyComment;
        console.log(response)
        this.getCommentsByUniIDSt(Number(this.uniId), this.student.studentID);
        this.getUniByID(Number(this.uniId));
        this.getSubjectsByUniID(Number(this.uniId));
        
      },
      (error: HttpErrorResponse)=>{
        console.log("err upvoteComment")
      }
    )
    }

}
