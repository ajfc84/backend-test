package whitebox;

import com.axreng.backend.Crawler;
import com.axreng.backend.Matcher;
import com.axreng.backend.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class CrawlerConditionTests {
    private Crawler crawlerMock = Mockito.mock(Crawler.class);

    @Test
    public void ifMax_urlsEqualTo0AndVisitedUrlsSizeGreaterThanMax_urlsThenReturnVisitedUrls() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, MalformedURLException, NoSuchFieldException {
        Field MAX_URLS = Crawler.class.getDeclaredField("MAX_URLS");
        MAX_URLS.setAccessible(true);
        MAX_URLS.set(crawlerMock, 0);
        Method loopMethod = Crawler.class.getDeclaredMethod("loop", List.class, Queue.class, FileWriter.class, String.class, int.class);
        loopMethod.setAccessible(true);
        List<URL> visitedUrlsMock = Mockito.mock(List.class);
        Mockito.when(visitedUrlsMock.size()).thenReturn(1);
        int count = (int) loopMethod.invoke(crawlerMock, visitedUrlsMock, null, null, null, 0);
        Assertions.assertEquals(0, count);
    }

    @Test
    public void ifMax_urlsEqualTo1ThenReturnCountOf1Element() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, NoSuchFieldException {
        URL baseUrl = new URL("http://www.google.com");
        Field baseUrlField = Crawler.class.getDeclaredField("baseUrl");
        baseUrlField.setAccessible(true);
        baseUrlField.set(crawlerMock, baseUrl);
        URL urlMock = Mockito.mock(URL.class);
        InputStream inputStream = Mockito.mock(InputStream.class);
        Mockito.when(urlMock.openStream()).thenReturn(inputStream);
        Field MAX_URLSField = Crawler.class.getDeclaredField("MAX_URLS");
        MAX_URLSField.setAccessible(true);
        MAX_URLSField.set(crawlerMock, 1);
        Field parserField = Crawler.class.getDeclaredField("parser");
        parserField.setAccessible(true);
        Parser parserMock = Mockito.mock(Parser.class);
        parserField.set(crawlerMock, parserMock);
        Set<String> parsedUrls = new HashSet<>();
        parsedUrls.add("/relativeUrlTest");
        Mockito.when(parserMock.parse(inputStream, "(?s)<\\s*a\\s+.*?href\\s*=\\s*['\"]([^\\s>]*)['\"]")).thenReturn(parsedUrls);
        Field matcherField = Crawler.class.getDeclaredField("matcher");
        matcherField.setAccessible(true);
        Matcher matcherMock = Mockito.mock(Matcher.class);
        matcherField.set(crawlerMock, matcherMock);
        Mockito.when(matcherMock.match(inputStream, null)).thenReturn(true);
        Method loopMethod = Crawler.class.getDeclaredMethod("loop", List.class, Queue.class, FileWriter.class, String.class, int.class);
        loopMethod.setAccessible(true);
        List<URL> visitedUrls = new LinkedList<>();
        Queue<URL> urlsToVisit = Mockito.mock(Queue.class);
        Mockito.when(urlsToVisit.poll()).thenReturn(urlMock);
        FileWriter fileWriter = Mockito.mock(FileWriter.class);
        int count = (int) loopMethod.invoke(crawlerMock, visitedUrls, urlsToVisit, fileWriter, null, 0);
        Assertions.assertEquals(1, count);
    }

    @Test
    public void ifMax_urlsEqualTo100ThenReturnCountOf100Element() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, NoSuchFieldException {
        URL baseUrl = new URL("http://www.google.com");
        Field baseUrlField = Crawler.class.getDeclaredField("baseUrl");
        baseUrlField.setAccessible(true);
        baseUrlField.set(crawlerMock, baseUrl);
        URL urlMock = Mockito.mock(URL.class);
        InputStream inputStream = Mockito.mock(InputStream.class);
        Mockito.when(urlMock.openStream()).thenReturn(inputStream);
        Field MAX_URLSField = Crawler.class.getDeclaredField("MAX_URLS");
        MAX_URLSField.setAccessible(true);
        MAX_URLSField.set(crawlerMock, 100);
        Field parserField = Crawler.class.getDeclaredField("parser");
        parserField.setAccessible(true);
        Parser parserMock = Mockito.mock(Parser.class);
        parserField.set(crawlerMock, parserMock);
        Set<String> parsedUrls = new HashSet<>();
        parsedUrls.add("/relativeUrlTest");
        Mockito.when(parserMock.parse(inputStream, "(?s)<\\s*a\\s+.*?href\\s*=\\s*['\"]([^\\s>]*)['\"]")).thenReturn(parsedUrls);
        Field matcherField = Crawler.class.getDeclaredField("matcher");
        matcherField.setAccessible(true);
        Matcher matcherMock = Mockito.mock(Matcher.class);
        matcherField.set(crawlerMock, matcherMock);
        Mockito.when(matcherMock.match(inputStream, null)).thenReturn(true);
        Method loopMethod = Crawler.class.getDeclaredMethod("loop", List.class, Queue.class, FileWriter.class, String.class, int.class);
        loopMethod.setAccessible(true);
        List<URL> visitedUrls = new LinkedList<>();
        Queue<URL> urlsToVisit = Mockito.mock(Queue.class);
        Mockito.when(urlsToVisit.poll()).thenReturn(urlMock);
        FileWriter fileWriter = Mockito.mock(FileWriter.class);
        int count = (int) loopMethod.invoke(crawlerMock, visitedUrls, urlsToVisit, fileWriter, null, 0);
        Assertions.assertEquals(100, count);
    }

}
