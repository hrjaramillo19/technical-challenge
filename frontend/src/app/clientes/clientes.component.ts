import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  // para ngFor
import { ClienteService } from '../services/cliente.service';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [
    CommonModule,      // necesario para ngFor, ngIf
  ],
  templateUrl: './clientes.component.html'
})
export class ClientesComponent implements OnInit {
  clientes: any[] = [];
  expandedIndex: number | null = null;
  cuentasCliente: any[] = [];

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.clienteService.getClientes().subscribe(data => {
      this.clientes = data;
    });
  }

  toggleCuentas(index: number, clienteId: number) {
    console.log('toggleCuentas', index, clienteId);
    if (this.expandedIndex === index) {
      this.expandedIndex = null;
      this.cuentasCliente = [];
    } else {
      this.expandedIndex = index;
      this.clienteService.getCuentasPorCliente(clienteId).subscribe(response => {
        this.cuentasCliente = response;
      });
    }
  }
}
