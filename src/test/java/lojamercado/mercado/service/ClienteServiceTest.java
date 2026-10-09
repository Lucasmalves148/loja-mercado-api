package lojamercado.mercado.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import lojamercado.mercado.dto.request.ClienteRequest;
import lojamercado.mercado.dto.response.ClienteResponse;
import lojamercado.mercado.entity.Cliente;
import lojamercado.mercado.exceptions.ClienteNotFoundException;
import lojamercado.mercado.map.Mapper;
import lojamercado.mercado.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private Mapper mapper;

    @Test
    @DisplayName("Deve criar um cliente com sucesso")
    public void deveCriarClienteComSucesso() {
        ClienteRequest request = new ClienteRequest();
        request.setNome("Lucas");
        request.setEmail("lucas@email.com");

        Cliente clienteSalvo = new Cliente(1L, "Lucas", "lucas@email.com");
        ClienteResponse responseEsperada = new ClienteResponse();
        responseEsperada.setId(1L);
        responseEsperada.setNome("Lucas");
        responseEsperada.setEmail("lucas@email.com");

        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(clienteSalvo);
        Mockito.when(mapper.clienteToResponse(clienteSalvo)).thenReturn(responseEsperada);

        ClienteResponse response = clienteService.criarCliente(request);
        
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Lucas", response.getNome());
        Mockito.verify(clienteRepository).save(Mockito.any(Cliente.class));
    }

    @Test
    @DisplayName("Deve retornar um cliente quando buscar por ID existente")
    public void deveBuscarClientePorIdComSucesso() {
        Cliente cliente = new Cliente(1L, "Lucas", "lucas@email.com");
        ClienteResponse responseEsperada = new ClienteResponse();
        responseEsperada.setId(1L);
        responseEsperada.setNome("Lucas");
        responseEsperada.setEmail("lucas@email.com");

        Mockito.when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        Mockito.when(mapper.clienteToResponse(cliente)).thenReturn(responseEsperada);

        ClienteResponse response = clienteService.encontrarClientePorId(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L, response.getId());
        Mockito.verify(clienteRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente com ID inexistente")
    public void deveLancarExcecaoAoBuscarClienteInexistente() {
        Long idInexistente = 999L;

        Mockito.when(clienteRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Assertions.assertThrows(ClienteNotFoundException.class,
                () -> clienteService.encontrarClientePorId(idInexistente));
    }

    @Test
    @DisplayName("Deve retornar um cliente ao buscar por email existente")
    public void deveBuscarClientePorEmailComSucesso() {
        Cliente cliente = new Cliente(1L, "Lucas", "lucas@email.com");
        ClienteResponse responseEsperada = new ClienteResponse();
        responseEsperada.setId(1L);
        responseEsperada.setNome("Lucas");
        responseEsperada.setEmail("lucas@email.com");

        Mockito.when(clienteRepository.findByEmailIgnoreCase("lucas@email.com")).thenReturn(Optional.of(cliente));
        Mockito.when(mapper.clienteToResponse(cliente)).thenReturn(responseEsperada);

        ClienteResponse response = clienteService.encontrarClientePorEmail("lucas@email.com");

        Assertions.assertNotNull(response);
        Assertions.assertEquals("lucas@email.com", response.getEmail());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente com email inexistente")
    public void deveLancarExcecaoAoBuscarClientePorEmailInexistente() {
        String emailInexistente = "naoexiste@email.com";

        Mockito.when(clienteRepository.findByEmailIgnoreCase(emailInexistente)).thenReturn(Optional.empty());

        Assertions.assertThrows(ClienteNotFoundException.class,
                () -> clienteService.encontrarClientePorEmail(emailInexistente));
    }

    @Test
    @DisplayName("Deve retornar uma lista de clientes")
    public void deveListarClientes() {
        Cliente c1 = new Cliente(1L, "Lucas", "lucas@email.com");
        Cliente c2 = new Cliente(2L, "Maria", "maria@email.com");

        ClienteResponse r1 = new ClienteResponse();
        r1.setId(1L);
        r1.setNome("Lucas");
        r1.setEmail("lucas@email.com");

        ClienteResponse r2 = new ClienteResponse();
        r2.setId(2L);
        r2.setNome("Maria");
        r2.setEmail("maria@email.com");

        Mockito.when(clienteRepository.findAll()).thenReturn(List.of(c1, c2));
        Mockito.when(mapper.clienteToResponse(c1)).thenReturn(r1);
        Mockito.when(mapper.clienteToResponse(c2)).thenReturn(r2);

        List<ClienteResponse> response = clienteService.listarClientes();

        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals("Lucas", response.get(0).getNome());
    }

    @Test
    @DisplayName("Deve deletar um cliente existente")
    public void deveDeletarClienteComSucesso() {
        Long id = 1L;

        Mockito.when(clienteRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(clienteRepository).deleteById(id);

        clienteService.deletarCliente(id);

        Mockito.verify(clienteRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar cliente inexistente")
    public void deveLancarExcecaoAoDeletarClienteInexistente() {
        Long idInexistente = 999L;

        Mockito.when(clienteRepository.existsById(idInexistente)).thenReturn(false);

        Assertions.assertThrows(ClienteNotFoundException.class,
                () -> clienteService.deletarCliente(idInexistente));
    }

    @Test
    @DisplayName("Deve atualizar os dados de um cliente existente")
    public void deveAtualizarClienteComSucesso() {
        Long id = 1L;
        ClienteRequest request = new ClienteRequest();
        request.setNome("Lucas Atualizado");
        request.setEmail("lucas.novo@email.com");

        Cliente clienteExistente = new Cliente(1L, "Lucas", "lucas@email.com");
        Cliente clienteAtualizado = new Cliente(1L, "Lucas Atualizado", "lucas.novo@email.com");

        ClienteResponse responseEsperada = new ClienteResponse();
        responseEsperada.setId(1L);
        responseEsperada.setNome("Lucas Atualizado");
        responseEsperada.setEmail("lucas.novo@email.com");

        Mockito.when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(clienteAtualizado);
        Mockito.when(mapper.clienteToResponse(clienteAtualizado)).thenReturn(responseEsperada);

        ClienteResponse response = clienteService.atualizarCliente(id, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Lucas Atualizado", response.getNome());
        Assertions.assertEquals("lucas.novo@email.com", response.getEmail());
        Mockito.verify(clienteRepository).save(Mockito.any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar cliente inexistente")
    public void deveLancarExcecaoAoAtualizarClienteInexistente() {
        Long idInexistente = 999L;
        ClienteRequest request = new ClienteRequest();
        request.setNome("Novo Nome");
        request.setEmail("novo@email.com");

        Mockito.when(clienteRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Assertions.assertThrows(ClienteNotFoundException.class,
                () -> clienteService.atualizarCliente(idInexistente, request));
    }
}