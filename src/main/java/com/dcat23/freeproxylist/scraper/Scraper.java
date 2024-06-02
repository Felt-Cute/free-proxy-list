package com.dcat23.freeproxylist.scraper;

import com.dcat23.freeproxylist.dto.Anonymity;
import com.dcat23.freeproxylist.model.ProxyElement;
import lombok.extern.slf4j.Slf4j;
import org.htmlunit.WebClient;
import org.htmlunit.html.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Scraper {
    private static final String BASE_URL = "https://free-proxy-list.net/";
    private static final String TABLE_SELECTOR = "#list > div > div.table-responsive > div > table > tbody";

    public static List<ProxyElement> scrape() {
        log.debug("Scraper start");
        HtmlTableBody table = getPage().querySelector(TABLE_SELECTOR);
        DomNodeList<HtmlElement> rows = table.getElementsByTagName("tr");
        log.debug("Extracting elements");
        return rows.subList(0,100)
                .stream()
                .map(Scraper::extractRow)
                .toList();
    }

     static ProxyElement extractRow(DomElement el) {
        DomNodeList<HtmlElement> td = el.getElementsByTagName("td");
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < td.size(); i++) {
            map.put(i, td.get(i).getTextContent());
        }

        ProxyElement p = new ProxyElement();

        p.setIpAddress(map.get(0));
        p.setPort(Integer.parseInt(map.get(1)));
        p.setCode(map.get(2));
        p.setCountry(map.get(3));
        p.setAnonymity(Anonymity.of(map.get(4)));
        p.setGoogle(map.get(5).equals("yes"));
        p.setHttps(map.get(6).equals("yes"));
        p.setLastChecked(map.get(7));

        return p;
    }

    static HtmlPage getPage() {
        HtmlPage page;
        try(WebClient client = Scraper.webClient())
        {
            page = client.getPage(BASE_URL);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return page;
    }

    static WebClient webClient() {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        return client;
    }
}
