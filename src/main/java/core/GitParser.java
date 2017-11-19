package core;

import core.models.Repo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class GitParser {
    public static ArrayList<Repo> parseRepos(String jsonFromGit){
        JSONObject jsonObj = new JSONObject(jsonFromGit);


        ArrayList<Repo> repsList = new ArrayList<>();
        if(jsonObj.has("items")){
        JSONArray items = jsonObj.getJSONArray("items");

        for(int i = 0; i < items.length(); i++){
            Repo result = new Repo();
            result.setName(items.getJSONObject(i).getString("full_name"));
            result.setUrl(items.getJSONObject(i).getString("html_url"));
            result.setDescription((items.getJSONObject(i).isNull("description")) ? "" : items.getJSONObject(i).getString("description"));
            result.setLanguage((items.getJSONObject(i).isNull("language")) ? "" : items.getJSONObject(i).getString("language"));
            result.setStars(items.getJSONObject(i).getInt("stargazers_count"));
            repsList.add(result);
        }}
        return repsList;
    }


    public static boolean commitsParse(String jsonFromGit,Hashtable<String,Integer> resultMap){
        try{
            System.out.println("parsing");
            if(jsonFromGit.compareTo("[]")==0)
                return false;
        JSONArray items = new JSONArray(jsonFromGit);
        for(int i=0; i<items.length();i++){
            String key=items.getJSONObject(i).getJSONObject("committer").getString("login");
            if(resultMap.containsKey(key))
                resultMap.replace(key,resultMap.get(key)+1);
            else
                resultMap.put(key,1);
        }
        return true;
        }catch (Exception ex){
            return false;
        }
    }

}
