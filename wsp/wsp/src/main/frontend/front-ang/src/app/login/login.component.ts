import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Student } from '../student/student.model';
import { StudentService } from '../student/student.service';
import { LoginService } from './login.service';
import { register } from './register';
import { UserCredentials } from './user-credentials.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  authenticating = false; // to show loading
  loginFailed = false; // to show login failed message
  random:string ="";
  success:string ="";
  success2:string ="";
  log:string ="";
  log2:string ="";
  userCredentials: UserCredentials = {} as UserCredentials;
  signup: register = {} as register;
  ssd : string;
  reg :Number = 0;
  constructor(
    private loginService: LoginService,
    private router: Router,
    private studentService: StudentService
  ) {
    this.ssd="";
  }
  ngOnInit(): void {
    this.success2 = '';
    this.success = '';
    this.signup.email = '';
    this.signup.password = '';
    this.random='';
    this.userCredentials.email=''
    this.userCredentials.password=''
  }
  emailPattern = "^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"; 
  signUp() {
    console.log(this.signup.email)
    if(this.signup.email==='' || this.signup.password === '' || this.random === '' ){
      this.success='Please fill in all the required fields.'
    }else if(!this.signup.email.match(this.emailPattern)){
      this.success='E-mail address is wrong!'
    }else if(this.signup.password!==this.random){
      this.success="Passwords don't match!"
      this.signup.password = '';
      this.random='';
    }else{
      // this.studentService.save(this.signup).subscribe(result => this.gotoLogin());
      this.studentService.save(this.signup).subscribe(
        (response: Number) =>{
          console.log(response)
           this.reg = response;
           if(this.reg !== 999){
            this.success='Registration successful'
            this.success2=''
            this.gotoLogin()
          }else {
            this.success="E-mail address isn't an edu address or the"
            this.success2="university isn't yet registered."
          }
          // this.gotoLogin()
        },
        (error: HttpErrorResponse)=>{
          this.success="Error."
        })
      // this.gotoLogin()
      this.signup.email = '';
      this.signup.password = '';
      this.random='';
        
      // if(this.reg !== 999){
      //   this.success='Registration successful'
      // }else {
      //   this.success="E-mail address isn't an edu address or the university isn't yet registered."
      // }
      
    } 
  }
  gotoLogin() {
    location.reload();
  }


  login() {
    if(this.userCredentials.email==='' || this.userCredentials.password ==='' ){
      this.log='Please fill in all the required fields.'
    }else if(!this.userCredentials.email.match(this.emailPattern)){
      this.log="E-mail address isn't an edu address or the"
      this.log2 =" university isn't yet registered.";
    }else{

    this.authenticating = true;
    this.loginFailed = false;

    // this.loginService.authenticate(this.userCredentials).subscribe(
    //   (student: Student) => this.successfulLogin(student),
    //   () => this.loginFailed = true,
    // ).add(() => this.authenticating = false);
    
  
      this.loginService.authenticate(this.userCredentials).subscribe(
        (student: Student) =>{
          this.successfulLogin(student);
        },
        (error: HttpErrorResponse)=>{
          this.log="The email address or password is incorrect.";
          this.log2 ="Please try again";
          this.authenticating = false
        }
      )

    // if(this.loginFailed){
    //   this.log="Nije ispravna lozinka ili korisničko ime!";
    // }
    }
  }

  successfulLogin(student: Student) {
    const data = JSON.stringify(student)
    localStorage.setItem('current-user',data);
    this.router.navigate(['/main-component']);
  }
}
