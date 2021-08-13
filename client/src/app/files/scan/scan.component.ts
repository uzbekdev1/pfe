import {Component, OnDestroy, OnInit} from '@angular/core';
import {AppService} from '../../shared/app.service';
import {UploadResponse} from '../../shared/app.models';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {NotificationsService} from 'angular2-notifications';

@Component({
  selector: 'app-scan',
  templateUrl: './scan.component.html',
  styleUrls: ['./scan.component.html']
})
export class ScanComponent implements OnInit, OnDestroy {

  file: string;
  model: UploadResponse;
  subs: Subscription;

  constructor(private service: AppService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private notifications: NotificationsService) {
  }

  scanFile() {

    this.service.scanFile(this.file).subscribe(data => {

      console.log('Scanned file ', data);

      this.model = data;

    }, error => console.error(error));
  }


  ngOnInit(): void {
    this.subs = this.activatedRoute.queryParams.subscribe((param: any) => {

      if (param['file']) {
        this.file = param['file'];

        this.scanFile();
      } else {

        this.notifications.error('Error', 'No found file!');

        this.router.navigate(['/files']);
      }

    });
  }


  ngOnDestroy(): void {
    if (this.subs) {
      this.subs.unsubscribe();
    }
  }

}
