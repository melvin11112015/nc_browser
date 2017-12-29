/*
 * Copyright 2014 A.C.R. Development
 */
package com.mlstudio.browser.download;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;

import com.mlstudio.browser.R;
import com.mlstudio.browser.constant.Constants;


public class MyDownloadListener implements DownloadListener {

    private final Activity mActivity;

    public MyDownloadListener(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onDownloadStart(final String url, final String userAgent,
                                final String contentDisposition, final String mimetype, long contentLength) {
        String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // 创建下载请求
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        /*
 * 设置在通知栏是否显示下载通知(下载进度), 有 3 个值可选:
 *    VISIBILITY_VISIBLE:                   下载过程中可见, 下载完后自动消失 (默认)
 *    VISIBILITY_VISIBLE_NOTIFY_COMPLETED:  下载过程中和下载完成后均可见
 *    VISIBILITY_HIDDEN:                    始终不显示通知
 */
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        //在通知栏中显示
                        request.setVisibleInDownloadsUi(true);
                        // 设置通知的标题和描述
                        //request.setTitle("通知标题XXX");
                        //request.setDescription("对于该请求文件的描述");
        /*
 * 设置允许使用的网络类型, 可选值:
 *     NETWORK_MOBILE:      移动网络
 *     NETWORK_WIFI:        WIFI网络
 *     NETWORK_BLUETOOTH:   蓝牙网络
 * 默认为所有网络都允许
 */
                        // request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                        // 添加请求头
                        // request.addRequestHeader("User-Agent", "Chrome Mozilla/5.0
                        // 设置下载文件的保存位置
                        //File saveFile = new File(Environment.getExternalStorageDirectory(), "demo.apk");
                        //request.setDestinationUri(Uri.fromFile(saveFile));

                        // 获取下载管理器服务的实例, 添加下载任务
                        DownloadManager manager = (DownloadManager) mActivity.getSystemService(Context.DOWNLOAD_SERVICE);

                        // 将下载请求加入下载队列, 返回一个下载ID
                        long downloadId = manager.enqueue(request);
                        // 如果中途想取消下载, 可以调用remove方法, 根据返回的下载ID取消下载, 取消下载后下载保存的文件将被删除
                        // manager.remove(downloadId);
                        MyDownloadManagerReceiver mDownloadReceiver = new MyDownloadManagerReceiver();
                        IntentFilter filter = new IntentFilter();
                        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
                        mActivity.registerReceiver(mDownloadReceiver, filter);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity); // dialog
        builder.setTitle(fileName).setMessage(mActivity.getResources().getString(R.string.dialog_download))
                .setPositiveButton(mActivity.getResources().getString(R.string.action_download),
                        dialogClickListener)
                .setNegativeButton(mActivity.getResources().getString(R.string.action_cancel),
                        dialogClickListener).show();
        Log.i(Constants.TAG, "Downloading" + fileName);

    }
}
