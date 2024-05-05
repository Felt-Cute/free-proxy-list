package com.dcat23.freeproxylist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Anonymity {

    ELITE("elite proxy"),
    ANONYMOUS("anonymous"),
    TRANSPARENT("transparent");

    private final String td;


    public static Anonymity of(String td) {
        for (Anonymity e : Anonymity.values()) {
            if (e.td.equals(td)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum constant with value: " + td);
    }
}
