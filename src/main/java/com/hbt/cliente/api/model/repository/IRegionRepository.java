package com.hbt.cliente.api.model.repository;


import com.hbt.cliente.api.model.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.inject.Singleton;

@Singleton
public interface IRegionRepository extends JpaRepository<Region, Long> {
}
