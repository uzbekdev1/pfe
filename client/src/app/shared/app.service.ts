import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {DisplayEntity, Pagination, ListingFilter, UploadResponse, KeyValuePair} from './app.models';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http: HttpClient) {
  }

  scanStream(file: File, copy: boolean): Observable<UploadResponse> {
    let formData = new FormData();

    formData.append('file', file);

    return this.http.post<UploadResponse>(environment.apiUrl + '/api/solutions/stream-scan?copy=' + copy, formData);
  }

  scanFile(file: string): Observable<UploadResponse> {

    return this.http.post<UploadResponse>(environment.apiUrl + '/api/solutions/file-scan?file=' + file, {});
  }

  filterDisplays(filter: ListingFilter): Observable<Pagination<DisplayEntity>> {

    return this.http.get<Pagination<DisplayEntity>>(environment.apiUrl + '/api/displays/filter?page=' + filter.page + '&size=' + filter.size + '&keyword=' + (filter.keyword || ''));
  }

  addDisplay(model: DisplayEntity): Observable<DisplayEntity> {

    return this.http.post<DisplayEntity>(environment.apiUrl + '/api/displays/add', model);
  }

  updateDisplay(id: number, model: DisplayEntity): Observable<DisplayEntity> {

    return this.http.post<DisplayEntity>(environment.apiUrl + '/api/displays/update/' + id, model);
  }

  getDisplay(id: number): Observable<DisplayEntity> {

    return this.http.get<DisplayEntity>(environment.apiUrl + '/api/displays/get/' + id);
  }

  deleteDisplay(id: number) {

    return this.http.post(environment.apiUrl + '/api/displays/delete/' + id, {});
  }

  getFiles() {
    return this.http.get<KeyValuePair[]>(environment.apiUrl + '/api/files/list');
  }

  deleteFile(file: string): Observable<boolean> {

    return this.http.get<boolean>(environment.apiUrl + '/api/files/delete?file=' + file);
  }

}
