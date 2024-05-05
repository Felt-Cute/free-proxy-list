package com.dcat23.freeproxylist.service;

import com.dcat23.freeproxylist.model.ProxyElement;
import com.dcat23.freeproxylist.repository.ProxyRepository;
import com.dcat23.freeproxylist.scraper.Scraper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ProxyService {

    ProxyRepository repo;

    public void fetch() {
        Scraper.scrape().stream()
            .map(this::checkExists)
            .forEach(this::save);

    }

    private void save(ProxyElement p) {
        repo.save(p);
    }

    private ProxyElement checkExists(ProxyElement p) {
        return repo.findByIpAddress(p.getIpAddress()).orElse(p);
    }
}
