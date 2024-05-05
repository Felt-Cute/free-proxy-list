package com.dcat23.freeproxylist.repository;

import com.dcat23.freeproxylist.dto.Anonymity;
import com.dcat23.freeproxylist.model.ProxyElement;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProxyRepository extends JpaRepository<ProxyElement, Long> {

    Optional<ProxyElement> findByIpAddress(String ipAddress);
    List<ProxyElement> findProxyElementsByCode(String code, Sort sort);
    List<ProxyElement> findProxyElementsByAnonymity(Anonymity anonymity, Sort sort);
    List<ProxyElement> findProxyElementsByAnonymityAndCode(Anonymity anonymity, String code ,Sort sort);
}
