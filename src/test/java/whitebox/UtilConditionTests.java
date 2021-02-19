package whitebox;

import com.axreng.backend.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class UtilConditionTests {
    private URL baseUrl = new URL("http://www.google.com");

    public UtilConditionTests() throws MalformedURLException {
    }

    @Test
    public void startsWithBaseUrl() throws MalformedURLException {
        String parsedUrl = "http://www.google.com/something/";
        Assertions.assertTrue(Util.isUrlAbsoluteBaseOrRelative(baseUrl, parsedUrl));
    }

    @Test
    public void startsWithAbsolutePathDoubleParenthesis() throws MalformedURLException {
        String parsedUrl = "//www.google.com/something/";
        Assertions.assertTrue(Util.isUrlAbsoluteBaseOrRelative(baseUrl, parsedUrl));
    }

    @Test
    public void startsWithRelativePathSingleParenthesis() throws MalformedURLException {
        String parsedUrl = "/something/";
        Assertions.assertTrue(Util.isUrlAbsoluteBaseOrRelative(baseUrl, parsedUrl));
    }

    @Test
    public void startsWithRelativeCurrentDirPath() throws MalformedURLException {
        String parsedUrl = "./something/";
        Assertions.assertTrue(Util.isUrlAbsoluteBaseOrRelative(baseUrl, parsedUrl));
    }

    @Test
    public void startsWithRelativePathStartingWithoutForwardSlash() throws MalformedURLException {
        String parsedUrl = "something/";
        Assertions.assertTrue(Util.isUrlAbsoluteBaseOrRelative(baseUrl, parsedUrl));
    }

    @Test
    public void doesNotStartsWithAnchor() throws MalformedURLException {
        String parsedUrl = "#something";
        Assertions.assertFalse(Util.isUrlAbsoluteBaseOrRelative(baseUrl, parsedUrl));
    }

    @Test
    public void doesNotStartWithJavascriptProtocol() throws MalformedURLException {
        String parsedUrl = "javascript:";
        Assertions.assertFalse(Util.isUrlAbsoluteBaseOrRelative(baseUrl, parsedUrl));
    }

    @Test
    public void ifStartsWithBaseUrlJustReturnParsedUrl() throws MalformedURLException {
        String parsedUrl = "http://www.google.com/something/";
        Assertions.assertEquals(new URL(parsedUrl), Util.anchorLinkToAbsoluteUrl(baseUrl, parsedUrl));
    }

    @Test
    public void ifStartsWithAbsolutePathDoubleParenthesisReturnAbsoluteUrl() throws MalformedURLException {
        String parsedUrl = "//www.google.com/something/";
        Assertions.assertEquals(new URL("http://www.google.com/something/"), Util.anchorLinkToAbsoluteUrl(baseUrl, parsedUrl));
    }

    @Test
    public void ifStartsWithRelativePathSingleParenthesisReturnAbsolutUrl() throws MalformedURLException {
        String parsedUrl = "/something/";
        Assertions.assertEquals(new URL("http://www.google.com/something/"), Util.anchorLinkToAbsoluteUrl(baseUrl, parsedUrl));
    }

    @Test
    public void ifStartsWithRelativeCurrentDirPathReturnAbsoluteUrl() throws MalformedURLException {
        String parsedUrl = "./something/";
        Assertions.assertEquals(new URL("http://www.google.com/something/"), Util.anchorLinkToAbsoluteUrl(baseUrl, parsedUrl));
    }

    @Test
    public void ifStartsWithRelativePathStartingWithoutForwardSlashReturnAbsoluteUrl() throws MalformedURLException {
        String parsedUrl = "something/";
        Assertions.assertEquals(new URL("http://www.google.com/something/"), Util.anchorLinkToAbsoluteUrl(baseUrl, parsedUrl));
    }

    @Test
    public void converts10DigitsHashto8DigitsId() {
        Assertions.assertEquals("12345678", Util.requestHashTo8DigitId(1234567890));
    }

    @Test
    public void stripLastPathForwardSlash() {
        Assertions.assertEquals("http://www.google.com", Util.stripLastForwardSlash(baseUrl + "/"));
    }

    @Test
    public void dontStripLastPathForwardSlash() {
        Assertions.assertEquals("http://www.google.com", Util.stripLastForwardSlash(baseUrl.toString()));
    }

    @Test
    public void payFacebookTestCase() {
        String parsedUrl = "https://pay.facebook.com/";
        Assertions.assertFalse(Util.isUrlAbsoluteBaseOrRelative(baseUrl, parsedUrl));
    }

    @Test
    public void oculusTestCase() {
        String parsedUrl = "https://www.oculus.com/";
        Assertions.assertFalse(Util.isUrlAbsoluteBaseOrRelative(baseUrl, parsedUrl));
    }

}
