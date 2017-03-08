import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeComponent } from '../pages/home/home.component';
import { CreateComponent } from './../pages/create/create.component';
import { CompleteComponent } from './../pages/complete/complete.component';
import { AuthGuard } from './auth.guard';

@NgModule({
  imports: [
    RouterModule.forRoot([
      {
        path: '',
        component: HomeComponent
      },
      {
        path: 'create',
        component: CreateComponent,
        canActivate: [
          AuthGuard
        ]
      },
      {
        path: 'complete',
        component: CompleteComponent,
        canActivate: [
          AuthGuard
        ]
      },
      {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
      }
    ])
  ],
  providers: [
    AuthGuard
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}
