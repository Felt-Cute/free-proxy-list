package com.dcat23.freeproxylist.repository;

import com.dcat23.freeproxylist.dto.Anonymity;
import com.dcat23.freeproxylist.model.ProxyElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProxyRepository extends JpaRepository<ProxyElement, Long> {

    Optional<ProxyElement> findByIpAddress(String ipAddress);
    Page<ProxyElement> findProxyElementsByCode(String code, Pageable sort);
    Page<ProxyElement> findProxyElementsByAnonymity(Anonymity anonymity, Pageable sort);
    Page<ProxyElement> findProxyElementsByAnonymityAndCode(Anonymity anonymity, String code , Pageable sort);
}
