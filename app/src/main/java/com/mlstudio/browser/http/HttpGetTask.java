package com.mlstudio.browser.http;

import android.os.AsyncTask;
import android.os.Build;

import com.mlstudio.browser.preference.PreferenceUtils;
import com.mlstudio.browser.utils.HttpReqUtil;
import com.mlstudio.browser.utils.URLUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpGetTask {


	private static ArrayList<NameValuePair> getNameValueList(HashMap<String, String> queryMap) {
		ArrayList<NameValuePair> list = new ArrayList<>();
		for (Object o : queryMap.entrySet()) {
			Map.Entry entry = (Map.Entry) o;
			try {
				list.add(new NameValuePair((String) entry.getKey(), (String) entry.getValue()));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return list;
	}


	public static void execute(OnTaskCompleted listener,HashMap<String, String> queryMap,String url) {

		execute(listener, getNameValueList(queryMap), url);
	}


	public static void execute(OnTaskCompleted listener,List<NameValuePair> paramsInput,String url){

		ArrayList<NameValuePair> params = new ArrayList<>();
		if(paramsInput!=null) {
			for (NameValuePair n : paramsInput) {
				String v = n.getValue();
				if (v != null && !v.equals("")) {
					params.add(n);
				}
			}
		}

		HttpReqUtil.addNameValueIfNotExist(params, "userId", PreferenceUtils.getUserId());
		HttpReqUtil.addNameValueIfNotExist(params, "accessKey", PreferenceUtils.getAccessKey());


		url =url+"?"+ URLUtil.getQueryString(params);
		HttpGetTaskInner task=new HttpGetTaskInner(listener);
		executeHttpTask(task, url);
	}

	public static void execute(OnTaskCompleted listener,String url){
		HttpGetTaskInner task=new HttpGetTaskInner(listener);
		executeHttpTask(task, url);
	}


	private static void executeHttpTask(HttpGetTaskInner task,String url){

		if(task==null){
			return;
		}
		int sdkInt= Build.VERSION.SDK_INT;
		if(sdkInt>=11) {
			task.executeOnExecutor(
					AsyncTask.THREAD_POOL_EXECUTOR, url);
		}else{
			task.execute(url);
		}
	}
}
