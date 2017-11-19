import core.GitApi;
import core.models.Repo;
import org.apache.http.client.utils.URIBuilder;
import web.HttpTransferer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        GitApi api = new GitApi();
        ArrayList<Repo> reps = api.getMostStarredRepos();
        String r = reps.toString();
        api.getMostCommitsAndContributors(reps,"2017-10-17","2017-10-20");


    }
}
