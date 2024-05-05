package com.dcat23.freeproxylist.service;

import com.dcat23.freeproxylist.dto.Anonymity;
import com.dcat23.freeproxylist.model.ProxyElement;
import com.dcat23.freeproxylist.repository.ProxyRepository;
import com.dcat23.freeproxylist.scraper.Scraper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ProxyService {

    ProxyRepository repo;

    public void fetch() {
        List<ProxyElement> proxies = Scraper.scrape()
                .stream()
                .map(this::checkExists)
                .toList();
        repo.saveAll(proxies);
    }

    private ProxyElement checkExists(ProxyElement p) {
        Optional<ProxyElement> byIpAddress = repo.findByIpAddress(p.getIpAddress());
        byIpAddress.ifPresent(proxyElement -> p.setId(proxyElement.getId()));
        return p;
    }

    public List<ProxyElement> getProxies(Anonymity tier, String code) {
        return repo.findAll();
    }
}
