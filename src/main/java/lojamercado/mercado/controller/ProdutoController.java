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
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lojamercado.mercado.dto.request.ProdutoRequest;
import lojamercado.mercado.dto.response.ProdutoResponse;
import lojamercado.mercado.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping()
    public ResponseEntity<ProdutoResponse> criarProduto(@Valid @RequestBody ProdutoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criarProduto(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> exibirProdutosPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.exibirProdutoPorId(id));
    }

    @GetMapping("/nome")
    public ResponseEntity<List<ProdutoResponse>> exibirProdutosPorNome(@RequestParam String nome){
        return ResponseEntity.ok(produtoService.exibirProdutoPorNome(nome));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProdutoPorId(@PathVariable Long id){
        produtoService.deletarProdutoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<ProdutoResponse>> exibirTodosProdutos(){
        return ResponseEntity.ok(produtoService.exibirTodosProdutos());
    }

    @GetMapping("/sem-estoque")
    public ResponseEntity<List<ProdutoResponse>> exibirProdutosSemEstoque(){
        return ResponseEntity.ok(produtoService.exibirProdutosSemEstoque());
    }

    @PutMapping("/preco/{id}")
    public ResponseEntity<BigDecimal> alterarPrecoProduto(@PathVariable Long id, @Positive @RequestBody BigDecimal valor){
        return ResponseEntity.ok(produtoService.alterarPrecoProduto(id, valor));
    }

    @PutMapping("/quantidade/{id}")
    public ResponseEntity<Integer> alterarQuantidade(@PathVariable Long id, @RequestBody @PositiveOrZero int estoque){
        return ResponseEntity.ok(produtoService.alterarQuantidade(id, estoque));
    }



}
