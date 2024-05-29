package com.dcat23.freeproxylist.dto;

import java.util.List;

public record RestProxyResponse(
        List<ProxyResponse> proxyList,
        int pageNumber,
        long totalElements,
        int totalPages
) {
}
