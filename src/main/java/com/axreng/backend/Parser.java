package com.axreng.backend;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {

    public Set<String> parse(InputStream inputStream, String regex) {
        Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines();
        Pattern p = Pattern.compile(regex);
        return lines
                .parallel()
                .flatMap(l -> {
                    Matcher m = p.matcher(l);
                    Set<String> set = new HashSet<>();
                    while (m.find()) {
                        set.add(m.group(1));
                    }
                    return set.stream();
                })
                .collect(Collectors.toSet());
    }

}
