package com.axreng.backend;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Matcher {

    public Boolean match(InputStream inputStream, String sequence) {
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().parallel().anyMatch(l -> l.contains(sequence));
    }

}
