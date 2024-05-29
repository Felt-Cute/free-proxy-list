package com.dcat23.freeproxylist.config;

import com.dcat23.freeproxylist.dto.Anonymity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AnonymityConverter implements Converter<String, Anonymity> {
    @Override
    public Anonymity convert(String source) {
        try {
            return Anonymity.valueOf(source
                    .strip()
                    .replaceAll("[ +-]+", "_")
                    .toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid option value: " + source);
        }
    }
}
