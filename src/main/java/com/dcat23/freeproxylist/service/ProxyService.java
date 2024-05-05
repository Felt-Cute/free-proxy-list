package com.dcat23.freeproxylist.service;

import com.dcat23.freeproxylist.dto.Anonymity;
import com.dcat23.freeproxylist.dto.ProxyResponse;
import com.dcat23.freeproxylist.model.ProxyElement;
import com.dcat23.freeproxylist.repository.ProxyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public void fetch() {
        log.info("Fetching proxies");
        List<ProxyElement> proxies = scrape()
                .stream()
                .map(this::checkExists)
                .toList();
        repo.saveAll(proxies);
        log.info("Fetched proxies");
    }

    private ProxyElement checkExists(ProxyElement p) {
        Optional<ProxyElement> byIpAddress = repo.findByIpAddress(p.getIpAddress());
        byIpAddress.ifPresent(proxyElement -> p.setId(proxyElement.getId()));
        return p;
    }

    public List<ProxyResponse> getProxies(Anonymity tier, String code) {
        List<ProxyElement> proxies;
        Sort sort = Sort.by(Sort.Direction.DESC, "lastChecked");
        if (tier == null && code == null) {
            proxies = repo.findAll(sort);
        } else if (tier == null) {
            proxies = repo.findProxyElementsByCode(code, sort);
        } else if (code == null){
            proxies = repo.findProxyElementsByAnonymity(tier, sort);
        } else {
            proxies = repo.findProxyElementsByAnonymityAndCode(tier, code, sort);
        }
        return proxies.stream().map(ProxyElement::asResponse).toList();
    }
}
