package com.dcat23.freeproxylist.service;

import com.dcat23.freeproxylist.dto.Anonymity;
import com.dcat23.freeproxylist.dto.ProxyResponse;
import com.dcat23.freeproxylist.dto.RestProxyResponse;
import com.dcat23.freeproxylist.model.ProxyElement;
import com.dcat23.freeproxylist.repository.ProxyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.dcat23.freeproxylist.scraper.Scraper.*;

@Slf4j
@Service
@AllArgsConstructor
public class ProxyService {

    ProxyRepository repo;

    public void init() {
        log.info("Initialize proxies");
        List<ProxyElement> proxies = scrape()
                .stream()
                .filter(ProxyElement.distinctByAddress())
                .toList();
        List<ProxyElement> saved = repo.saveAll(proxies);
        log.info("{} initial proxies", saved.size());
    }

    public void fetch() {
        log.info("Fetching proxies");
        List<ProxyElement> proxies = scrape()
                .stream()
                .filter(ProxyElement.distinctByAddress())
                .map(this::checkExists)
                .toList();
        List<ProxyElement> saved = repo.saveAll(proxies);
        log.info("Fetched {} proxies", saved.size());
    }

    private ProxyElement checkExists(ProxyElement p) {
        Optional<ProxyElement> byIpAddress = repo.findByIpAddress(p.getIpAddress());
        byIpAddress.ifPresent(proxyElement -> p.setId(proxyElement.getId()));
        return p;
    }

    public RestProxyResponse getProxies(Anonymity tier, String countryCode, int page, int limit) {
        Page<ProxyElement> proxyPage;
        Sort sort = Sort.by("lastChecked").descending();
        Pageable pageable = PageRequest.of(page, limit, sort);

        if (tier == null && countryCode == null) {
            proxyPage = repo.findAll(pageable);
        } else if (tier == null) {
            proxyPage = repo.findProxyElementsByCode(countryCode, pageable);
        } else if (countryCode == null){
            proxyPage = repo.findProxyElementsByAnonymity(tier, pageable);
        } else {
            proxyPage = repo.findProxyElementsByAnonymityAndCode(tier, countryCode, pageable);
        }
        List<ProxyResponse> proxyList = proxyPage.getContent().stream()
                .map(ProxyElement::asResponse)
                .toList();

        log.info("✅ Retrieved {} proxies", proxyList.size());
        return new RestProxyResponse(
                proxyList,
                proxyPage.getNumber(),
                proxyPage.getTotalElements(),
                proxyPage.getTotalPages()
        );
    }
}
