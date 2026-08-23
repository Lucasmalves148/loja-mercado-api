package lojamercado.mercado.service;


import java.util.List;

import org.springframework.stereotype.Service;

import lojamercado.mercado.dto.request.ClienteRequest;
import lojamercado.mercado.dto.response.ClienteResponse;
import lojamercado.mercado.entity.Cliente;
import lojamercado.mercado.exceptions.ClienteNotFoundException;
import lojamercado.mercado.map.Mapper;
import lojamercado.mercado.repository.ClienteRepository;

@Service
public class ClienteService {
    
    
    private final ClienteRepository clienteRepository;
    private final Mapper mapper;

    public ClienteService(ClienteRepository clienteRepository, Mapper mapper){
        this.mapper = mapper;
        this.clienteRepository = clienteRepository;
    }

    public ClienteResponse criarCliente(ClienteRequest cRequest){
        Cliente cliente = new Cliente();
        
        cliente.setEmail(cRequest.getEmail());
        cliente.setNome(cRequest.getNome());

        cliente = clienteRepository.save(cliente);

        return mapper.clienteToResponse(cliente);
    }

    public void deletarCliente(Long id){
        if(!clienteRepository.existsById(id)){
            throw new ClienteNotFoundException("Não foi encontrado nenhum cliente com esse id");
        }

        clienteRepository.deleteById(id);
    }
    
    public List<ClienteResponse> listarClientes(){
       return clienteRepository.findAll()
            .stream()
            .map(mapper::clienteToResponse)
            .toList();
    }
    
    public ClienteResponse encontrarClientePorId(Long id){
        return clienteRepository.findById(id)
            .map(mapper::clienteToResponse)
            .orElseThrow(() -> new ClienteNotFoundException("Não foi encontrado nenhum cliente com este Id"));
        }

    public ClienteResponse encontrarClientePorEmail(String email){
            return clienteRepository.findByEmailIgnoreCase(email)
                .map(mapper::clienteToResponse)
                .orElseThrow(() -> new ClienteNotFoundException("Não foi encontrado nenhum cliente com este email."));
        }



}