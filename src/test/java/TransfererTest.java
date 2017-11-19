import org.apache.http.client.utils.URIBuilder;
import org.junit.Assert;
import org.junit.Test;
import web.HttpTransferer;

import java.io.IOException;
import java.net.URISyntaxException;

public class TransfererTest {
private final String exampleResult = "{\"message\":\"Bad credentials\",\"documentation_url\":\"https://developer.github.com/v3\"}";
@Test
public void HttpTest() throws URISyntaxException, IOException {
    String res = HttpTransferer.getHttpStringResponse(new URIBuilder()
            .setScheme("https")
            .setHost("api.github.com")
            .setPath("/search/repositories").build(),"asd");
    Assert.assertEquals(exampleResult,res);
}
}
