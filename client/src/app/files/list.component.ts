import {Component, OnDestroy, OnInit} from '@angular/core';
import {KeyValuePair} from '../shared/app.models';
import {AppService} from '../shared/app.service';
import {Subscription} from 'rxjs';
import {NotificationsService} from 'angular2-notifications';
import {Router} from '@angular/router';

@Component({
  selector: 'app-file-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class FilesComponent implements OnInit, OnDestroy {

  model: KeyValuePair[];
  subs: Subscription;

  constructor(private  service: AppService,
              private notify: NotificationsService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loadListing();
  }

  deleteItem(file: string) {
    this.subs = this.service.deleteFile(file).subscribe(data => {

      if (data) {
        this.notify.error('Deleted', 'File deleting successfully');
        this.loadListing();
      }

    }, error => console.error(error));
  }

  loadListing() {
    this.subs = this.service.getFiles().subscribe(data => {

      console.log('Files ', data);

      this.model = data;

    }, error => console.error(error));
  }

  ngOnDestroy(): void {
    if (this.subs) {
      this.subs.unsubscribe();
    }
  }


}
