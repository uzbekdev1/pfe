import {Component, OnInit, TemplateRef} from '@angular/core';
import {AppService} from '../shared/app.service';
import {DisplayEntity, ListingFilter, Pagination} from '../shared/app.models';
import {NgForm} from '@angular/forms';
import {NotificationsService} from 'angular2-notifications';

@Component({
  selector: 'app-displays',
  templateUrl: './displays.component.html',
  styleUrls: ['./displays.component.html']
})
export class DisplaysComponent implements OnInit {

  model: Pagination<DisplayEntity>;
  filter: ListingFilter = new ListingFilter();

  constructor(private  service: AppService,
              private notify: NotificationsService) {

    this.filter.page = 1;
    this.filter.size = 20;

  }

  ngOnInit(): void {
    this.loadItems();
  }

  searchForm(f: NgForm) {
    if (f.valid) {

      this.filter.page = 1;

      this.loadItems();

    }
  }

  goToPage(page: number): void {
    this.filter.page = page;

    this.loadItems();
  }

  onNext(): void {
    this.filter.page++;

    this.loadItems();
  }

  onPrev(): void {
    this.filter.page--;

    this.loadItems();
  }

  deleteItem(item: DisplayEntity) {
    this.service.deleteDisplay(item.id).subscribe(data => {

      this.notify.success('Success', 'Deleted!');

      this.loadItems();
    });
  }

  loadItems() {

    console.log('Filter form ', this.filter);

    this.service.filterDisplays(this.filter).subscribe(data => {

      console.log('Filter displays ', data);

      this.model = data;

    });

  }

}
