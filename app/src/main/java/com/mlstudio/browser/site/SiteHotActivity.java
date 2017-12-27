package com.mlstudio.browser.site;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.mlstudio.browser.ChannelItem;
import com.mlstudio.browser.R;
import com.mlstudio.browser.activity.AppViewPagerActivity;
import com.mlstudio.browser.history.UserHistoryListFragment;

import java.util.ArrayList;


public class SiteHotActivity extends AppViewPagerActivity {

	
	@Override
	public String getTitleString() {
		return getString(R.string.history);
	}

	@Override
	public ArrayList<String> getMenuList() {
		return new ArrayList<>();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}



	protected ArrayList<ChannelItem> getUserChannelLists(){
		return SiteChannels.channels;
	}


	protected Fragment initFragment(String channelName) {
		if(channelName.equals("历史")){
			return new UserHistoryListFragment();
		}

		if(!channelName.equals("热门")){
			SiteHotListFragment f = new SiteHotListFragment();
			f.setQueryTag(channelName);
			return f;
		}
		return new SiteHotListFragment();
	}


	

}
