export class ListingFilter {
  page: number;
  size: number;
  keyword: string;
}

export class KeyValuePair {

  key: string;
  value: string;

}

export class Pagination<T> {
  content: T[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: any;
  size: number;
  sort: any;
  totalElements: number;
  totalPages: number;
}

export class DisplayEntity {

  id: number;
  progName: string;
  display: string;
  error_Name: string;
  nomTable: string;
  descSol: string;
  indiceGravity: number;
  userSol: string;
  freeColumn1: string;
  rate: number;

}

export class UploadResponse {
  lines: string[];
  solutions: DisplayEntity[];
  error: string;
}
