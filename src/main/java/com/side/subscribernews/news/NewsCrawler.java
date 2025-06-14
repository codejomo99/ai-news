package com.side.subscribernews.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class NewsCrawler {

	// 연합뉴스
	public List<NewsArticle> fetchYna() throws IOException {
		List<NewsArticle> articles = new ArrayList<>();

		Document doc = Jsoup.connect("https://www.yna.co.kr/news?site=navi_latest_depth01")
			.userAgent("Mozilla/5.0")
			.get();

		Elements newsItems = doc.select("ul.list01 > li[data-cid]");

		for (Element item : newsItems) {
			Element titleElem = item.selectFirst("strong.tit-wrap");
			Element contentElem = item.selectFirst("p.lead");
			Element linkElem = item.selectFirst("a[href]");

			// Null 체크
			if (titleElem == null || contentElem == null || linkElem == null) continue;

			String title = titleElem.text();
			String content = contentElem.text();
			String url = linkElem.absUrl("href");

			articles.add(NewsArticle.builder()
				.title(title)
				.content(content)
				.url(url)
				.build());
		}

		return articles;
	}


}