import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface Cliente {
  clienteId: number;
  nombre: string;
  genero: string;
  edad: number;
  identificacion: string;
  direccion?: string;
  telefono?: string;
  estado: boolean;
  contrasena?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private apiUrlCliente = 'http://localhost:8080/api/clientes';
  private apiUrlReporte = 'http://localhost:8081/api/reportes?fecha=2025-01-01,2025-05-30&';

  constructor(private http: HttpClient) { }

  getClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.apiUrlCliente);
  }

  getCuentasPorCliente(clienteId: number) {
    return this.http.get<any[]>(`${this.apiUrlReporte}cliente=${clienteId}`);
  }
}
