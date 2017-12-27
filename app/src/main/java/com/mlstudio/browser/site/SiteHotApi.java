package com.mlstudio.browser.site;


import com.mlstudio.browser.config.ServerConfig;
import com.mlstudio.browser.http.HttpPostTask;
import com.mlstudio.browser.http.NameValuePair;
import com.mlstudio.browser.http.OnTaskCompleted;

import java.util.ArrayList;
import java.util.List;

public class SiteHotApi {

	static String API_HOST = ServerConfig.API_HOST;// "10.0.2.2";
	static int API_PORT = ServerConfig.API_PORT;// "10.0.2.2";
	static String URL_Site_Hot_Query = "http://" + API_HOST + ":"
			+ API_PORT + "/api/web/site/hot/query";
    private static SiteHotApi mInstance = null;

	protected SiteHotApi() {
		
	}

	public static SiteHotApi getInstance() {
		if (mInstance == null) {
			mInstance = new SiteHotApi();
			mInstance.init();
		}
		return mInstance;
	}

	public void init() {

	}
	
	public void query(String tag,int p, OnTaskCompleted listener) {
        List<NameValuePair> h = new ArrayList<>();
        h.add(new NameValuePair("p", String.valueOf(p)));
		h.add(new NameValuePair("tag", tag));
		HttpPostTask.execute(listener, h, URL_Site_Hot_Query);
	}

	

}
