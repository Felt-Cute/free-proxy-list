package com.dcat23.freeproxylist.controller;

import com.dcat23.freeproxylist.dto.Anonymity;
import com.dcat23.freeproxylist.service.ProxyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/fpl")
@AllArgsConstructor
public class ProxyController {

    ProxyService service;

    @GetMapping
    ResponseEntity<?> getProxies(
            @RequestParam(required = false) Anonymity tier,
            @RequestParam(required = false) String code,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getProxies(tier, code, page, limit));
    }
}
