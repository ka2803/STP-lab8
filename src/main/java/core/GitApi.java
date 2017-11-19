package core;

import core.models.Contributor;
import core.models.Repo;
import org.apache.http.client.utils.URIBuilder;
import web.HttpTransferer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class GitApi {
    private final String GIT_TOKEN = "ffd1501d40fc9e843a5e516c70f6168d7a9397e8";

    public ArrayList<Repo> getMostStarredRepos() throws URISyntaxException, IOException {
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
        String jsonString = HttpTransferer.getHttpStringResponse(uri,GIT_TOKEN);
        return GitParser.parseRepos(jsonString);

    }
    public ArrayList<Repo> getPushedRepos(String startDate, String endDate) throws URISyntaxException, IOException {
        ArrayList<Repo> result = new ArrayList<>();
        StringBuilder buildDate = new StringBuilder();
        buildDate.append("pushed:");
        buildDate.append(startDate);
        buildDate.append("..");
        buildDate.append(endDate);
        int page = 1;
        while(true){
            URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("api.github.com")
                .setPath("/search/repositories")
                .setParameter("q",buildDate.toString())
                .setParameter("page", String.valueOf(page))
                .setParameter("per_page", "100")
                .build();
            String response = HttpTransferer.getHttpStringResponse(uri,GIT_TOKEN);
            ArrayList<Repo> tmpRes = GitParser.parseRepos(response);
            if(tmpRes.size()==0){
                break;
            }
            result.addAll(tmpRes);
            page++;
        }

        return result;
    }
    public ArrayList<Repo> getTenMostCommited(String startDate, String endDate) throws IOException, URISyntaxException, InterruptedException {
        ArrayList<Repo> result = getPushedRepos(startDate,endDate);
        getMostCommitsAndContributors(result,startDate,endDate);
        if(result.size()>=10)
            result= (ArrayList<Repo>) result.subList(0,10);
        return result;
    }

    public void getMostCommitsAndContributors(ArrayList<Repo> repos,String startDate,String endDate) throws IOException, URISyntaxException {
        for(int i =0;i<repos.size();i++){
            commitNums(repos.get(i),startDate,endDate);
        }
        repos.sort(new Comparator<Repo>() {
            @Override
            public int compare(Repo o1, Repo o2) {
                if(o1.getCommitsNum()>o2.getCommitsNum())
                    return 1;
                else if(o1.getCommitsNum()==o2.getCommitsNum())
                    return 0;
                else
                    return -1;
            }
        });

    }
    public void commitNums(Repo repa,String startDate,String endDate) throws URISyntaxException, IOException {
        int page=1;
        Hashtable<String,Integer> commits = new Hashtable<>();
        while(true){
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.github.com")
                    .setPath("/repos/"+repa.getName()+"/commits")
                    .setParameter("since", startDate)
                    .setParameter("until", endDate)
                    .setParameter("page", String.valueOf(page))
                    .setParameter("per_page", "50")
                    .build();
            String response = HttpTransferer.getHttpStringResponse(uri,GIT_TOKEN);

            if(!GitParser.commitsParse(response,commits))
                break;
            page++;
        }
        repa.setCommitsNum(countCommits(commits));
        repa.setTopContributors(getContributors(commits));
    }
    private int countCommits(Hashtable<String,Integer> table){
        int count =0;

        for(int i=0;i<table.size();i++){
            count+=table.get(table.keySet().iterator().next());
        }
        return count;
    }
    public ArrayList<Contributor> getContributors(Hashtable<String,Integer> commits){
        ArrayList<Contributor> result = new ArrayList<>();
        for (Map.Entry<String,Integer> entry:
                commits.entrySet()
             ) {
            Contributor tmp = new Contributor();
            String key = entry.getKey();
            tmp.setLogin(key);
            tmp.setCommitNum(entry.getValue());
            result.add(tmp);

        }
        result.sort(new Comparator<Contributor>() {
            @Override
            public int compare(Contributor o1, Contributor o2) {
                if(o1.getCommitNum()>o2.getCommitNum())
                    return -1;
                else if(o1.getCommitNum()==o2.getCommitNum()){
                    return 0;
                }else{
                    return 1;
                }
            }
        });
        if(result.size()>=5)
            result= new ArrayList<Contributor>(result.subList(0,5));

        return result;
    }
}
