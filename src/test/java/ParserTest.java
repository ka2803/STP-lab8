import com.sun.org.apache.regexp.internal.RE;
import core.GitApi;
import core.GitParser;
import core.models.Repo;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import web.HttpTransferer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;

public class ParserTest {
    private  Repo repa;
    private  GitApi api;
    @Before
    public void init(){
        repa = new Repo();
        api =new GitApi();
        repa.setName("freeCodeCamp/freeCodeCamp");
    }
    @Test
    public void parseReposTest() throws IOException, URISyntaxException {

        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("api.github.com")
                .setPath("/search/repositories")
                .setParameter("q","stars:>1000")
                .setParameter("sort", "stars")
                .setParameter("order", "desc")
                .setParameter("page","1")
                .setParameter("per_page","10")
                .build();
        String GIT_TOKEN = api.getToken();
        String jsonString = HttpTransferer.getHttpStringResponse(uri,GIT_TOKEN);
        ArrayList<Repo> reposTest = GitParser.parseRepos(jsonString);
        Assert.assertEquals("[freeCodeCamp/freeCodeCamp, twbs/bootstrap, EbookFoundation/free-programming-books, facebook/react, tensorflow/tensorflow, vuejs/vue, sindresorhus/awesome, getify/You-Dont-Know-JS, d3/d3, robbyrussell/oh-my-zsh]",reposTest.toString());
    }
    @Test
    public void commitsParseTest() throws URISyntaxException, IOException {

        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("api.github.com")
                .setPath("/repos/"+repa.getName()+"/commits")
                .setParameter("page", String.valueOf(1))
                .setParameter("per_page", "10")
                .build();
        String GIT_TOKEN = api.getToken();
        String jsonString = HttpTransferer.getHttpStringResponse(uri,GIT_TOKEN);
        boolean res = GitParser.commitsParse(jsonString,new Hashtable<>());
        Assert.assertEquals(true,res);
    }
}
