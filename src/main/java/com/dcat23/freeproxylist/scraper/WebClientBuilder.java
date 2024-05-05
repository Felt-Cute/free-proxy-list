package com.dcat23.freeproxylist.scraper;

import org.htmlunit.WebClient;

public class WebClientBuilder {
    private boolean cssEnabled = false;
    private boolean jsEnabled = false;

    public WebClient build() {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(cssEnabled);
        client.getOptions().setJavaScriptEnabled(jsEnabled);
        return client;
    }

    public WebClientBuilder enableCss() {
        cssEnabled = true;
        return this;
    }
    public WebClientBuilder enableJs() {
        jsEnabled = true;
        return this;
    }
}
