package lojamercado.mercado.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lojamercado.mercado.dto.request.ClienteRequest;
import lojamercado.mercado.dto.response.ClienteResponse;
import lojamercado.mercado.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping()
    public ResponseEntity<ClienteResponse> criarCliente(@Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criarCliente(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<ClienteResponse>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> encontrarClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.encontrarClientePorId(id));
    }

    @GetMapping("/email")
    public ResponseEntity<ClienteResponse> encontrarClientePorEmail(@RequestParam String email) {
        return ResponseEntity.ok(clienteService.encontrarClientePorEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {

        return ResponseEntity.ok(
                clienteService.atualizarCliente(id, request));
    }

}