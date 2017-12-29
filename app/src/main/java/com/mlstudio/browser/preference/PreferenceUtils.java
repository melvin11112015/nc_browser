package com.mlstudio.browser.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.mlstudio.browser.AccountInfo;
import com.mlstudio.browser.MainApp;
import com.mlstudio.browser.RecentAccountJSON;
import com.mlstudio.browser.RecentAccountJSONList;
import com.mlstudio.browser.utils.RandomUtil;
import com.mlstudio.browser.utils.StringUtils;

import java.util.ArrayList;

public class PreferenceUtils {
	public static int CollectPublic = 0;
	public static int CollectPrivate = 1;
	public static String getUserId() {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MainApp.getInstance());
		return settings.getString(PreferenceConstants.UserId, "");
	}

    public static void setUserId(final String value) {
        if (StringUtils.isEmpty(value)) {
            setAnonymous();
            return;
        }
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(MainApp.getInstance());
		settings.edit().putString(PreferenceConstants.UserId, value).apply();
	}

	public static String getCity() {
		return getUserPrefStr(PreferenceConstants.City);
	}

    public static void setCity(final String value) {
        setUserPrefStr(PreferenceConstants.City, value);
    }

	public static int getWebShareType() {
		return getUserPrefInt(PreferenceConstants.WebShareType);
	}

	public static void setWebShareType(final int value) {
		setUserPrefInt(PreferenceConstants.WebShareType, value);
	}

	public static int getCollectType() {
		return getUserPrefInt(PreferenceConstants.CollectType);
	}

    public static void setCollectType(final int value) {
        setUserPrefInt(PreferenceConstants.CollectType, value);
    }

	public static void setUserPrefInt(final String key,final int value) {
		String userId=PreferenceUtils.getUserId();
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MainApp.getInstance());
		settings.edit().putInt(userId + ":" + key, value).apply();
	}

	public static int getUserPrefInt(final String key) {
		String userId=PreferenceUtils.getUserId();
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MainApp.getInstance());
		return settings.getInt(userId + ":" + key, 0);
	}

	public static boolean getUserPrefBool(final String key) {
		String userId=PreferenceUtils.getUserId();
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MainApp.getInstance());
		return settings.getBoolean(userId + ":" + key, false);
	}

	public static void setUserPrefBool(final String key,final boolean value) {
		String userId=PreferenceUtils.getUserId();
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MainApp.getInstance());
		settings.edit().putBoolean(userId + ":" + key, value).apply();
	}

	public static void setUserPrefStr(final String key,final String value) {
		String userId=PreferenceUtils.getUserId();
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MainApp.getInstance());
		settings.edit().putString(userId + ":" + key, value).apply();
	}

	public static String getUserPrefStr(final String key) {
		String userId=PreferenceUtils.getUserId();
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MainApp.getInstance());
		return settings.getString(userId + ":" + key, "");
	}

	public static String getAccessKey() {
		return getUserPrefStr(PreferenceConstants.AccessKey);
	}

    public static void setAccessKey(final String value) {
        setUserPrefStr(PreferenceConstants.AccessKey, value);
    }

	public static void clearPreference(Context context,
			final SharedPreferences p) {
		final Editor editor = p.edit();
		editor.clear();
		editor.apply();

	}

	public static boolean getPrefBoolean(Context context, final String key,
										 final boolean defaultValue) {

		String userId=PreferenceUtils.getUserId();
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getBoolean(userId+":"+key, defaultValue);

	}



	public static AccountInfo getAccountInfo() {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MainApp.getInstance());
		String userId= settings.getString(PreferenceConstants.UserId, "");

		String accessKey= settings.getString(userId+":"+PreferenceConstants.AccessKey, "");
		String chatId= settings.getString(userId+":"+PreferenceConstants.ChatId, "");
		String chatPwd= settings.getString(userId+":"+PreferenceConstants.ChatPwd, "");
		String UserName= settings.getString(userId+":"+PreferenceConstants.UserName, "");
		String UserSign= settings.getString(userId+":"+PreferenceConstants.UserSign, "");
		String UserPhoto= settings.getString(userId+":"+PreferenceConstants.UserPhoto, "");
		return new AccountInfo(userId, accessKey, chatId, chatPwd, UserName, "", UserSign, UserPhoto, "");
	}


	public static void setAccountInfo(String userId, String accessKey, String chatId, String chatPwd, String userName, String userPhoto, String uploadId) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MainApp.getInstance());
		settings.edit()
				.putString(PreferenceConstants.UserId, userId)

				.putString(userId + ":" + PreferenceConstants.AccessKey, accessKey)
				.putString(userId + ":" + PreferenceConstants.ChatId, chatId)
				.putString(userId+":"+PreferenceConstants.ChatPwd, chatPwd)
				.putString(userId+":"+PreferenceConstants.UserName, userName)
				.putString(userId + ":" + PreferenceConstants.UserPhoto, userPhoto)
				.putString(userId + ":" + PreferenceConstants.UploadId, uploadId)
				.apply();

		addRecentAccount();

	}

	public static String getChatId(){
		return getUserPrefStr(PreferenceConstants.ChatId);

	}


	public static String getChatPwd(){
		return getUserPrefStr(PreferenceConstants.ChatPwd);

	}

	public static ArrayList<String> getPicTags() {
		String s=getUserPrefStr(PreferenceConstants.PicTags);
		return StringUtils.fromArrayStr(s);
	}

    public static void setPicTags(ArrayList<String> picTags) {

        String s = StringUtils.toArrayStr(picTags);
        setUserPrefStr(PreferenceConstants.PicTags, s);


    }

	public static ArrayList<String> getNewsTags() {
		String s=getUserPrefStr(PreferenceConstants.NewsTags);
		return StringUtils.fromArrayStr(s);
	}

    public static void setNewsTags(ArrayList<String> picTags) {
        String s = StringUtils.toArrayStr(picTags);
        setUserPrefStr(PreferenceConstants.NewsTags, s);
    }

	public static String getUploadId() {
		return getUserPrefStr(PreferenceConstants.UploadId);
	}

	public static void setAnonymous() {
		String s = getUserId();
		if (s.contains("Anonymous")) {
			return;
		}
		String userId = "Anonymous_" + RandomUtil.secureRandomString();
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MainApp.getInstance());
		settings.edit().putString(PreferenceConstants.UserId, userId)
				.putString(userId + ":" + PreferenceConstants.AccessKey, "")
				.putString(userId + ":" + PreferenceConstants.ChatId, "")
				.putString(userId + ":" + PreferenceConstants.ChatPwd, "")
				.apply();
	}

	public static boolean isUserVisitor() {
		String userId = getUserId();
		if (StringUtils.isEmpty(userId)) {
			return true;
		}
		String accessKey = getAccessKey();
		return StringUtils.isEmpty(accessKey) || userId.contains("Anonymous_");
	}

	public static boolean isBookmarkInited(){
		return getUserPrefBool(PreferenceConstants.BookmarkInited);
	}
	public static void setBookmarkInited(){
		 setUserPrefBool(PreferenceConstants.BookmarkInited, true);
	}

	public static void setUserPhoto(String photo) {
		setUserPrefStr(PreferenceConstants.UserPhoto, photo);
	}


	private static void addRecentAccount() {
		AccountInfo info=getAccountInfo();
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MainApp.getInstance());
		String jsonstr=settings.getString(PreferenceConstants.RecentAccount, "");
		ArrayList<RecentAccountJSON> list=RecentAccountJSONList.parse(jsonstr);
		if(info!=null&&!info.getUserId().contains("Anonymous_")) {
			RecentAccountJSON ajson=new RecentAccountJSON(info.getUserId(), info.getAccessKey(), info.getName(), info.getPhoto(), info.getSign());
			RecentAccountJSONList.addItem(list, ajson);
		}

		String saveJsonStr=RecentAccountJSONList.convertJSONString(list);
		settings.edit().putString(PreferenceConstants.RecentAccount, saveJsonStr)
				.apply();
	}

	public static ArrayList<RecentAccountJSON> getRecentAccountList() {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MainApp.getInstance());
		String jsonstr=settings.getString(PreferenceConstants.RecentAccount, "");
		return RecentAccountJSONList.parse(jsonstr);
	}
}
