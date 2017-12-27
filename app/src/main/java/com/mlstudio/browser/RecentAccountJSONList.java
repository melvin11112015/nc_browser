package com.mlstudio.browser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by xuming on 2016/3/7.
 */
public class RecentAccountJSONList {

    public static ArrayList<RecentAccountJSON> parse(String jsonstr) {
        ArrayList<RecentAccountJSON> list = new ArrayList<>();
        try {
            JSONArray jsonResult = new JSONArray(jsonstr);
            int lenDocs=jsonResult.length();
            for(int i=0;i<lenDocs;i++){
                JSONObject docObj=jsonResult.getJSONObject(i);
                RecentAccountJSON info=new RecentAccountJSON(docObj);
                list.add(info);
            }

        } catch (Exception e) {

        }
        return list;
    }

    public static String convertJSONString(ArrayList<RecentAccountJSON> list) {

        StringBuilder result = new StringBuilder("[");
        for(int i=0;i<list.size();i++) {
            RecentAccountJSON json = list.get(i);
            result.append("{");
            result.append("'userId':'").append(json.getUserId()).append("',");
            result.append("'accessKey':'").append(json.getAccessKey()).append("',");
            result.append("'name':'").append(json.getName()).append("',");
            result.append("'photo':'").append(json.getPhoto()).append("'");
            result.append("}");
            if(i<list.size()-1){
                result.append(",");
            }

        }
        result.append("]");
        return result.toString();
    }

    public static boolean isContainUserId(ArrayList<RecentAccountJSON> list,String userId) {
        for(int i=0;i<list.size();i++){
            RecentAccountJSON json=list.get(i);
            if(json.getUserId().equals(userId)){
                return true;
            }
        }
        return false;
    }

    public static boolean removeByUserId(ArrayList<RecentAccountJSON> list,String userId) {
        for(int i=0;i<list.size();i++){
            RecentAccountJSON json=list.get(i);
            if(json.getUserId().equals(userId)){
                list.remove(i);
                return true;
            }
        }
        return false;
    }

    public static void addItem(ArrayList<RecentAccountJSON> list, RecentAccountJSON item) {
        if(item==null){
            return;
        }
        if(isContainUserId(list,item.getUserId())){
            removeByUserId(list,item.getUserId());
        }
        list.add(0,item);
    }
}

