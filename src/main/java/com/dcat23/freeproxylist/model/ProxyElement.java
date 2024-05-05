package com.dcat23.freeproxylist.model;

import com.dcat23.freeproxylist.dto.Anonymity;
import jakarta.persistence.*;
import lombok.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Entity
public class ProxyElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 15, unique = true)
    private String ipAddress;
    private int port;
    @Column(length = 2)
    private String code;
    @Column(length = 30)
    private String country;
    @Enumerated(value = EnumType.STRING)
    private Anonymity anonymity;
    private boolean google;
    private boolean https;
    private LocalDateTime lastChecked;

    public void setLastChecked(String lastChecked) {
        String[] split = lastChecked.split(" ");
        int value = Integer.parseInt(split[0]);

        ChronoUnit timeUnit = switch (split[1]) {
            case "mins" -> ChronoUnit.MINUTES;
            case "hours" -> ChronoUnit.HOURS;
            default -> ChronoUnit.SECONDS;
        };

        LocalDateTime now = LocalDateTime.now();
        this.lastChecked = now.minus(value, timeUnit) ;
    }

    public void setIpAddress(String ipAddress) {
        try {
            this.ipAddress = InetAddress.getByName(ipAddress)
                    .toString();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
