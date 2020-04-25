import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Invoice } from './invoice'
import {HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class InvoiceService {

  private baseUrl = 'http://localhost:8080/invoices';

  constructor(private http: HttpClient) { }

  getInvoice(id: string): Observable<any> {
    return this.http.get(this.baseUrl + '/' + id);
  }

  createInvoice(invoice: Object): Observable<any> {
    return this.http.post(this.baseUrl, invoice);
  }

  deleteInvoice(id: string): Observable<any> {
    return this.http.delete(this.baseUrl + '/' + id, { responseType: 'text'});
  }

  getInvoices(): Observable<any> {
    return this.http.get<Invoice[]>(this.baseUrl);
  }

  getInvoiceAsPdf(id: string) {
  const httpOptions = {
    'responseType'  : 'arraybuffer' as 'json',
    headers: new HttpHeaders({
    'Accept' : 'application/pdf' })
  };
  return this.http.get<any>(this.baseUrl + '/' + id, httpOptions);
  }

  getInvoicesAsZip() {
  const httpOptions = {
      'responseType'  : 'arraybuffer' as 'json',
      headers: new HttpHeaders({
      'Accept' : 'application/zip' })
    };
  return this.http.get<any>(this.baseUrl, httpOptions);
  }
}
