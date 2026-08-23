package lojamercado.mercado.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lojamercado.mercado.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    public void deleteById(Long id);
    Optional<Cliente> findByEmailIgnoreCase(String email);
}