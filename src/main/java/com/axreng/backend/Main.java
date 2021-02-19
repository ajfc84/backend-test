package com.axreng.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;

import static spark.Spark.get;
import static spark.Spark.post;

public class Main {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        try {
            final URL BASE_URL = new URL(Util.stripLastForwardSlash(System.getenv("BASE_URL")));
            final Integer MAX_RESULTS = Integer.parseInt(System.getenv("MAX_RESULTS"));
            logger.info("Found Configuration: BASE_URL=" + BASE_URL + ", MAX_RESULTS=" + MAX_RESULTS);
            Gson gson = new GsonBuilder().create();
            get("/crawl/:id", "application/json", (req, res) -> {
                String id = req.params("id");
                final GetResponse response = new GetResponse(id);
                try {
                    if (!Validate.isValidId(id)) throw new InvalidException("Invalid id: " + id);
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("/tmp/axreng_" + id))) {
                        bufferedReader.lines()
                                .forEach(l -> {
                                    if (l.startsWith("#DONE#")) response.setStatus("done");
                                    else response.getUrls().add(l);
                                });
                    } catch (FileNotFoundException e) {
                        throw new InvalidException("Invalid id: " + id);
                    }
                } catch (InvalidException e) {
                    e.printStackTrace();
                    logger.info("User trying invalid id: " + id);
                    res.status(400);
                    response.setStatus("Invalid id");
                }
                return gson.toJson(response);
            });
            post("/crawl", "application/json", (req, res) -> {
                final String id = Util.requestHashTo8DigitId(req.hashCode());
                PostResponse response =  new PostResponse(id);
                PostRequest request = gson.fromJson(req.body(), PostRequest.class);
                String keyword = request.getKeyword();
                try {
                    if (!Validate.isValidKeyword(keyword)) throw new InvalidException("Invalid keyword: " + keyword);
                    new Thread(() -> {
                        Crawler crawler = new Crawler(BASE_URL, MAX_RESULTS);
                        crawler.crawl(id, request.getKeyword());
                    }).start();
                } catch (InvalidException e) {
                    e.printStackTrace();
                    res.status(400);
                    response.setId("-1");
                }
                return gson.toJson(response);
            });
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Configuration values, please define BASE_URL and MAX_RESULTS correctly!");
        }
    }
}

