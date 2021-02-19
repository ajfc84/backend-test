package com.axreng.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Crawler {
    private URL baseUrl;
    private Parser parser;
    private Matcher matcher;
    private final Integer MAX_URLS;
    private Logger logger = null;

    public Crawler(URL baseUrl, Integer MAX_URLS) {
        this.baseUrl = baseUrl;
        this.MAX_URLS = MAX_URLS;
        this.parser = new Parser();
        this.matcher = new Matcher();
        logger = LoggerFactory.getLogger(Main.class);
    }

    public void crawl(String id, String sequence) {
        List<URL> visitedUrls = new LinkedList<>();
        Queue<URL> urlsToVisit = new LinkedList<>();
        urlsToVisit.add(baseUrl);
        try(FileWriter fileWriter = new FileWriter("/tmp/axreng_" + id, false)) {
            loop(visitedUrls, urlsToVisit, fileWriter, sequence, 0);
            fileWriter.write("#DONE#");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int loop(List<URL> visitedUrls, Queue<URL> urlsToVisit, FileWriter fileWriter, String sequence, int count) {
        if(MAX_URLS >= 0 && visitedUrls.size() >= MAX_URLS) return count;
//        if(MAX_URLS >= 0 && count >= MAX_URLS) return;
        else {
            URL visitingUrl = urlsToVisit.poll();
            try {
                if (this.matcher.match(visitingUrl.openStream(), sequence)) {
                    try {
                        fileWriter.write(visitingUrl + "\n");
                        fileWriter.flush();
                        count++;
                    } catch (IOException e) {
                        throw new MatchException("Could not save matched" + sequence + " on " + visitingUrl + "!");
                    }
                }
                visitedUrls.add(visitingUrl);
                Set<String> parsedUrls = this.parser.parse(visitingUrl.openStream(), "(?s)<\\s*a\\s+.*?href\\s*=\\s*['\"]([^\\s>]*)['\"]");
                parsedUrls.stream().parallel()
                        .filter(parsed -> Util.isUrlAbsoluteBaseOrRelative(this.baseUrl, parsed))
                        .map(parsed -> Util.anchorLinkToAbsoluteUrl(this.baseUrl, parsed))
                        .forEach(u -> {
                            if (!visitedUrls.contains(u) && !urlsToVisit.contains(u)) urlsToVisit.add(u);
                        });
            } catch (IOException e) {
                logger.info("Connection refused on: " + visitingUrl);
            }
            return loop(visitedUrls, urlsToVisit, fileWriter, sequence, count);
        }
    }

}
