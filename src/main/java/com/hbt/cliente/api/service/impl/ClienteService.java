package com.hbt.cliente.api.service.impl;

import com.hbt.cliente.api.client.IFileService;
import com.hbt.cliente.api.exception.ApiRequestException;
import com.hbt.cliente.api.model.entity.Cliente;
import com.hbt.cliente.api.model.entity.FileName;
import com.hbt.cliente.api.model.entity.Region;
import com.hbt.cliente.api.model.repository.IClienteRepository;
import com.hbt.cliente.api.model.repository.IRegionRepository;
import com.hbt.cliente.api.service.IClienteService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Slf4j
public class ClienteService implements IClienteService {

    @Inject
    IClienteRepository repository;

    @Inject
    IRegionRepository regionRepository;

    @Inject
    @RestClient
    IFileService service;

    @Override
    public List<Cliente> findAllClients() {
        return (List<Cliente>)repository.findAll();
    }

    @Override
    public Page<Cliente> findAllClients(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Cliente findClientId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiRequestException("El cliente " + id + " no se encuentra disponible."));
    }

    @Override
    public Cliente create(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public Cliente edit(Cliente cliente, Long id) {
        log.info("Ingreso al metodo edit: " + cliente);
        Cliente editClient = findClientId(id);

        log.info("Editar cliente: " + editClient);

        editClient.setNombre(cliente.getNombre());
        editClient.setApellido(cliente.getApellido());
        editClient.setEmail(cliente.getEmail());
        editClient.setCreateAt(cliente.getCreateAt());
        editClient.setRegion(cliente.getRegion());

        log.info("Cliente editado: " + editClient);

        return repository.save(editClient);
    }

    @Override
    public void delete(Long id) {

        FileName fileName = FileName.builder()
                .fileName(findClientId(id).getFoto())
                .build();

        service.delete(fileName);

        repository.deleteById(id);
    }

    @Override
    public List<Region> findAllRegiones() {
        return regionRepository.findAll();
    }
}
