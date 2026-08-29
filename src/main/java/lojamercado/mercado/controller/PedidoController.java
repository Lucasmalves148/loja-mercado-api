package lojamercado.mercado.controller;

import java.math.BigDecimal;
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
import lojamercado.mercado.dto.request.PedidoRequest;
import lojamercado.mercado.dto.response.PedidoResponse;
import lojamercado.mercado.enumerate.Status;
import lojamercado.mercado.service.PedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping()
    public ResponseEntity<PedidoResponse> criarPedido(@Valid @RequestBody PedidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criarPedido(request));
    }

    @GetMapping()
    public ResponseEntity<List<PedidoResponse>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarTodosPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> encontrarPedidoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.encontrarPedidoPorId(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{cod}")
    public ResponseEntity<List<PedidoResponse>> exibirPedidoPorStatus(@RequestParam int cod) {
        return ResponseEntity.ok(pedidoService.exibirPedidosPorStatus(cod));
    }

    @GetMapping("/total")
    public ResponseEntity<Long> totalDePedidos() {
        return ResponseEntity.ok(pedidoService.totalDePedidos());
    }

    @GetMapping("/total-pedido/{id}")
    public ResponseEntity<BigDecimal> calcularTotalPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.calcularTotalPedido(id));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<PedidoResponse> atualizarStatus(@Valid @PathVariable Long id, int cod) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, Status.valorDoStatus(cod)));
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<List<PedidoResponse>> buscarPedidosClientes(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.buscarPedidosCliente(id));
    }
}