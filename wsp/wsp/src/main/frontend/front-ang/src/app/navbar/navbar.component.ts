import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Input,  Output,EventEmitter } from '@angular/core';
import { ActivatedRoute, Router, UrlSegment } from '@angular/router';
import { Student } from '../student/student.model';
declare var jQuery: any;


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit,AfterViewInit {
  loggedUser: Student =JSON.parse(localStorage.getItem('current-user') || '{}');

  public codeValue: string;
  constructor(
    private route:ActivatedRoute,
    private router: Router) {
    this.codeValue = "";
   }
  
  ngOnInit(): void {
    
   
      // this.userService.getCurrentUser().subscribe((currentUser: User) => {
      //   this.userService.currentUser = currentUser;
      // });
    
  }
  ngAfterViewInit(): void {

  }
  getUniId():number{
    this.loggedUser=JSON.parse(localStorage.getItem('current-user') || '{}');
    return this.loggedUser.universityID;
  }


  logout() {
    localStorage.removeItem('current-user')
    console.log("logout")
  }

  isUserLoggedIn(): boolean {
    if(localStorage.getItem('current-user') !== null){
      return true
    }else
    return false;
  }

}
