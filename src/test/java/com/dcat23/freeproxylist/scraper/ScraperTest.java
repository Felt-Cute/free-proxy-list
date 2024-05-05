package com.dcat23.freeproxylist.scraper;

import com.dcat23.freeproxylist.model.ProxyElement;
import lombok.extern.slf4j.Slf4j;
import org.htmlunit.html.*;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Objects;

import static com.dcat23.freeproxylist.scraper.Scraper.getPage;
import static com.dcat23.freeproxylist.scraper.Scraper.scrape;
import static org.assertj.core.api.Assertions.*;

@Slf4j
class ScraperTest {

    @Test
    void Scraper_page_shouldFindTitle() {
        final String expected = "Free Proxy List - Just Checked Proxy List";
        assertThat(getPage().getTitleText()).isEqualTo(expected);
    }

    @Test @Disabled
    void Scraper_page_shouldFindRows() {
        HtmlTableBody tbody =  getPage().querySelector("#list > div > div.table-responsive > div > table > tbody");
        DomNodeList<HtmlElement> rows = tbody.getElementsByTagName("tr");
        rows.stream().map(Scraper::extractRow)
                .map(ProxyElement::toString)
                .forEach(log::info);
    }

    @Test
    void Scraper_scrape_shouldWork() {
        List<ProxyElement> proxies = scrape();
        assertThat(proxies.isEmpty()).isFalse();

        boolean hasIp = proxies.stream()
                .map(ProxyElement::getIpAddress)
                .allMatch(Objects::nonNull);

        assertThat(hasIp).isTrue();
    }
}