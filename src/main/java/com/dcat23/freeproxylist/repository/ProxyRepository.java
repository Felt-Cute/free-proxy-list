package com.dcat23.freeproxylist.repository;

import com.dcat23.freeproxylist.model.ProxyElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProxyRepository extends JpaRepository<ProxyElement, Long> {

    Optional<ProxyElement> findByIpAddress(String ipAddress);
}
