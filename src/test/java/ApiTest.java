import core.GitApi;
import core.models.Repo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ApiTest {

    GitApi api;


    @Before
    public void Init(){
        api = new GitApi();
    }
    @Test
    public void getMostStarredTest() throws IOException, URISyntaxException {
        Assert.assertEquals("[freeCodeCamp/freeCodeCamp, twbs/bootstrap, EbookFoundation/free-programming-books, facebook/react, tensorflow/tensorflow, vuejs/vue, sindresorhus/awesome, getify/You-Dont-Know-JS, d3/d3, robbyrussell/oh-my-zsh]",api.getMostStarredRepos().toString());
    }
    @Test
    public void getMostCommitedTest() throws InterruptedException, IOException, URISyntaxException {
        ArrayList<Repo> reps= api.getTenMostCommited("2039-11-11","2040-11-11");
        Assert.assertEquals(0,reps.size());
    }
    @Test
    public void getPushedTest() throws IOException, URISyntaxException {
        ArrayList<Repo> reps= api.getPushedRepos("2039-11-11","2040-11-11");
        Assert.assertEquals(0,reps.size());
    }
    @Test
    public void getMostCommitsTest() throws IOException, URISyntaxException {
        ArrayList<Repo> reps = api.getMostStarredRepos();
        api.getMostCommitsAndContributors(reps,"2017-10-17","2017-10-20");
        Assert.assertEquals(3,reps.get(9).getCommitsNum());
    }

}
