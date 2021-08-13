import {Component, OnDestroy, OnInit} from '@angular/core';
import {AppService} from '../shared/app.service';
import {UploadResponse} from '../shared/app.models';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.html']
})
export class UploadComponent implements OnInit, OnDestroy {

  selectedFile: File;
  copy: boolean = false;
  file: File;
  model: UploadResponse;

  constructor(private service: AppService) {
  }

  chooseFile(e) {
    this.selectedFile = e.target.files.item(0);
  }

  uploadFile(f) {

    this.service.scanStream(this.selectedFile, this.copy).subscribe(data => {

      console.log('Scanned file ', data);

      this.model = data;

    }, error => console.error(error));
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
  }

}
