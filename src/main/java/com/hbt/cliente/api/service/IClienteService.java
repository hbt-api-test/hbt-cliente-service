package com.hbt.cliente.api.service;


import com.hbt.cliente.api.model.entity.Cliente;
import com.hbt.cliente.api.model.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {

    List<Cliente> findAllClients();
    Page<Cliente> findAllClients(Pageable pageable);
    Cliente findClientId(Long id);
    Cliente create(Cliente cliente);
    Cliente edit(Cliente cliente, Long id);
    void delete(Long id);
    List<Region> findAllRegiones();

}
