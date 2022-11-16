import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommentsComponent } from './comments/comments.component';
import { LoginComponent } from './login/login.component';
import { MainComponent } from './main/main.component';
import { SubjectsComponent } from './subjects/subjects.component';
import { UniversityComponent } from './university/university.component';

const routes: Routes = [
  { path: 'main-component', component: MainComponent},
  { path: 'university-component/:id', component: UniversityComponent},
  { path: 'subject-component/:id', component: SubjectsComponent},
  { path: 'comments-component', component: CommentsComponent},
  { path: 'login-component', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    scrollPositionRestoration: 'enabled', // Add options right here
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
