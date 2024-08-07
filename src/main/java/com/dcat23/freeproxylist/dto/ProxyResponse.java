package com.dcat23.freeproxylist.dto;

public record ProxyResponse(
        String ipAddress,
        String code,
        String country,
        Anonymity tier,
        int port,
        String url
) { }
