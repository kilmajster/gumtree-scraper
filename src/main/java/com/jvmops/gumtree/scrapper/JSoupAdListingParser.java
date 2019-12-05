package com.jvmops.gumtree.scrapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.jvmops.gumtree.scrapper.ScrapJob.GUMTREE_URL;

@Component
@Slf4j
@AllArgsConstructor
class JSoupAdListingParser {
    private HtmlProvider htmlProvider;

    List<ListedAd> scrap(String adListingUrl) {
        String adListingHtml = htmlProvider.get(adListingUrl);
        Document adListing = Jsoup.parse(adListingHtml);
        return selectAdsFrom(adListing).stream()
                .map(this::parseListedAd)
                .collect(Collectors.toUnmodifiableList());
    }

    private Elements selectAdsFrom(Document document) {
        return document.select("div.results")
                .select("div.view")
                .select("div.tileV1");
    }

    private ListedAd parseListedAd(Element adFromList) {
        Element title = adFromList.select("div.title").first();
        String url = title.select("a[href]").attr("href");
        String price = adFromList.selectFirst("span.ad-price").text()
                .replace("zł", "")
                .trim()
                .replace(" ", "");

        return ListedAd.builder()
                .url(GUMTREE_URL + url)
                .title(title.text().trim())
                .price(Integer.valueOf(price))
                .build();
    }
}
