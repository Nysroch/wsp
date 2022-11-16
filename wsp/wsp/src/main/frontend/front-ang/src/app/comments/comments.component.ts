import { formatDate } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Student } from '../student/student.model';
import { SubjectService } from '../subjects/subject.service';
import { University } from '../university/university.model';
import { UniversityService } from '../university/university.service';
import { Commentt } from './comment.model';
import { CommentService } from './comment.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {
  student: Student = JSON.parse(localStorage.getItem('current-user') || '{}');

  uni: University = {} as University;
  public com: Commentt[];
  public tempCom: Commentt[];
  public sortByStC: any;
  public sortByDateC: any;
  public sortByUpC: any;
  constructor(
    private _router: Router,
    public sanitizer: DomSanitizer,
    private subjectService: SubjectService,
    private universityService: UniversityService,
    private commentService: CommentService
  ) {
    this.com = [];
    this.tempCom = [];
    this.sortByStC = 0;
    this.sortByDateC = 0;
    this.sortByUpC = 0;
  }

  ngOnInit(): void {
    this.getUniByID(Number(this.student.universityID));
    this.getCommentsByStudID(Number(this.student.studentID));
  }

  public getUniByID(universityID: number): void {
    this.universityService.getUniByID(universityID).subscribe(
      (response: University) => {
        this.uni = response;
      },
      (error: HttpErrorResponse) => {
        console.log("err get un2")
      }
    )
  }
  public routeUni(uniId: number) {
    this._router.navigate(['/university-component', uniId]);
  }

  public routeComm(uniId: number, subjId: number) {
    console.log(uniId , subjId)
    if( uniId !== null){
      this._router.navigate(['/university-component', uniId]);
    }else{
      this._router.navigate(['/subject-component', subjId]);
    }
  }
  isUserLoggedIn(): boolean {
    if (localStorage.getItem('current-user') !== null) {
      return true
    } else
      return false;
  }
  public datum(date: Date): string {
    return formatDate(date, 'dd/MM/yyyy', 'en-US');
  }


  public getCommentsByStudID(studID: number): void {
    this.commentService.getCommentsByStudID(studID).subscribe(
      (response: Commentt[]) => {
        this.com = response;
        this.tempCom = response;
      },
      (error: HttpErrorResponse) => {
        console.log("err get getCommentsByStudID")
      }
    )
  }
  stars: number[] = [1, 2, 3, 4, 5];
  newCommRating: number = 0;
  countStar(star: number) {
    this.newCommRating = star;
    console.log('Value of star', star);
  }
  // getCommentsByStudID

  public sortUpC(): void {
    this.sortByDateC = 0;
    this.sortByStC = 0;
    if (this.sortByUpC !== 2) {
      ++this.sortByUpC;
    } else {
      this.sortByUpC = 0;
    }
    switch (this.sortByUpC) {
      case 2:
        this.com = this.com.sort((a, b) => a.upvote - b.upvote)
        break;
      default:
        this.com = this.com.sort((a, b) => b.upvote - a.upvote)
        break;
    }
  }

  public sortStC(): void {
    this.sortByUpC = 0;
    this.sortByDateC = 0;
    if (this.sortByStC !== 2) {
      ++this.sortByStC;
    } else {
      this.sortByStC = 0;
    }
    switch (this.sortByStC) {
      case 2:
        this.com = this.com.sort((a, b) => a.rating - b.rating)
        break;
      default:
        this.com = this.com.sort((a, b) => b.rating - a.rating)
        break;
    }
  }
  public sortDateC(): void {
    this.sortByUpC = 0;
    this.sortByStC = 0;
    if (this.sortByDateC !== 2) {
      ++this.sortByDateC;
    } else {
      this.sortByDateC = 0;
    }
    switch (this.sortByDateC) {
      case 2:
        this.com = this.com.sort(function (a, b) {
          var dateA = new Date(a.dateEdited), dateB = new Date(b.dateEdited)
          return dateA.getTime() - dateB.getTime()
        });
        break;
      default:
        this.com = this.com.sort(function (a, b) {
          var dateA = new Date(a.dateEdited), dateB = new Date(b.dateEdited)
          return dateB.getTime() - dateA.getTime()
        });
        break;
    }
  }
}
