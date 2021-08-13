import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';

import {AppComponent} from './app.component';
import {NavMenuComponent} from './layout/nav-menu.component';
import {HomeComponent} from './home/home.component';
import {DisplaysComponent} from './displays/displays.component';
import {UploadComponent} from './upload/upload.component';
import {LoadingBarHttpClientModule} from '@ngx-loading-bar/http-client';
import {LoadingBarRouterModule} from '@ngx-loading-bar/router';
import {LoadingBarModule} from '@ngx-loading-bar/core';
import {AppService} from './shared/app.service';
import {SimpleNotificationsModule} from 'angular2-notifications';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import {PaginationComponent} from './shared/controls/pagination.component';
import {DisplayFormComponent} from './displays/form/form.component';
import {FilesComponent} from './files/list.component';
import {ScanComponent} from './files/scan/scan.component';

@NgModule({
  declarations: [
    AppComponent,
    NavMenuComponent,
    HomeComponent,
    DisplaysComponent,
    DisplayFormComponent,
    UploadComponent,
    FilesComponent,
    ScanComponent,
    PaginationComponent
  ],
  imports: [
    BrowserModule.withServerTransition({
      appId: 'ng-cli-universal'
    }),
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    BrowserAnimationsModule,
    HttpClientModule,
    RouterModule.forRoot([
      {path: '', redirectTo: '/home', pathMatch: 'full'},
      {path: 'home', component: HomeComponent},
      {path: 'upload', component: UploadComponent},
      {path: 'files', component: FilesComponent},
      {path: 'displays', component: DisplaysComponent},
      {path: 'displays/add', component: DisplayFormComponent},
      {path: 'displays/edit/:id', component: DisplayFormComponent},
      {path: 'scan', component: ScanComponent},
      {path: '**', component: HomeComponent}
    ]),
    LoadingBarModule,
    LoadingBarHttpClientModule,
    LoadingBarRouterModule,
    SimpleNotificationsModule.forRoot(),
    SweetAlert2Module.forRoot({
      showCancelButton: true,
      reverseButtons: true
    })
  ],
  providers: [AppService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
