import core.models.Contributor;
import core.models.Repo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ModelsTest {
    private Repo repa;


    @Before
    public void Init(){
        repa = new Repo();
    }
    @Test
    public void nameTest(){
        repa.setName("repository");
        Assert.assertEquals("repository",repa.getName());
    }
    @Test
    public void urlTest(){
        repa.setUrl("url");
        Assert.assertEquals("url",repa.getUrl());
    }
    @Test
    public void langTest(){
        repa.setLanguage("lang");
        Assert.assertEquals("lang",repa.getLanguage());
    }
    @Test
    public void starsTest(){
        repa.setStars(0);
        Assert.assertEquals(0,repa.getStars());
    }
    @Test
    public void descrTest(){
        repa.setDescription("url");
        Assert.assertEquals("url",repa.getDescription());
    }
    @Test
    public void commitsTest(){
        repa.setCommitsNum(0);
        Assert.assertEquals(0,repa.getCommitsNum());
    }
    @Test
    public void contributorsTest(){
        Contributor contr = new Contributor();
        contr.setCommitNum(0);
        contr.setLogin("login");

        ArrayList<Contributor> list = new ArrayList<Contributor>();
        list.add(contr);
        repa.setTopContributors(list);

        Assert.assertEquals(0,contr.getCommitNum());
        Assert.assertEquals("login",contr.getLogin());
        Assert.assertEquals(list,repa.getTopContributors());
    }

}
