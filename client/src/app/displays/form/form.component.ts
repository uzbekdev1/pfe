import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {DisplayEntity} from '../../shared/app.models';
import {AppService} from '../../shared/app.service';
import {ActivatedRoute, Router} from '@angular/router';
import {NotificationsService} from 'angular2-notifications';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-displays',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.html']
})
export class DisplayFormComponent implements OnInit {

  model: DisplayEntity;
  id: number;
  subs: Subscription;

  constructor(private  service: AppService,
              private router: Router,
              private active: ActivatedRoute,
              private notify: NotificationsService) {
  }

  ngOnInit(): void {
    this.subs = this.active.params.subscribe(params => {
      if (params['id']) {
        this.id = +params['id'];

        this.loadItem();
      } else {
        this.model = new DisplayEntity();
      }
    });
  }

  saveForm(f: NgForm) {

    console.log('Form data ', this.model);

    if (f.valid) {

      if (this.id) {
        this.service.updateDisplay(this.id, this.model).subscribe(data => {

          this.notify.success('Success', 'Updated item!');
          this.router.navigate(['/displays']);

        });
      } else {
        this.service.addDisplay(this.model).subscribe(data => {

          this.notify.success('Success', 'Added new item!');
          this.router.navigate(['/displays']);

        });
      }

    }
  }


  loadItem() {

    this.service.getDisplay(this.id).subscribe(data => {

      console.log('Load display ', data);

      this.model = data;
    });

  }

}
