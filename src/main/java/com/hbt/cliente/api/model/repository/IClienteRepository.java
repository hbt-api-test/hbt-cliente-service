package com.hbt.cliente.api.model.repository;

import com.hbt.cliente.api.model.entity.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;


import javax.inject.Singleton;


@Singleton
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

}
