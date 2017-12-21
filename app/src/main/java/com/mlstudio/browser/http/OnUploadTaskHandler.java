
package com.mlstudio.browser.http;

public interface OnUploadTaskHandler {
	
	void onTaskCompleted(String result);
	void onUploadeBytes(int count, int total);
	void onUploadError();
	
}
