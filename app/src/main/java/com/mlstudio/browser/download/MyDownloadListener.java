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
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;

import com.mlstudio.browser.R;
import com.mlstudio.browser.constant.Constants;
import com.mlstudio.browser.utils.ToastUtil;

import java.io.File;


public class MyDownloadListener implements DownloadListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private final Activity mActivity;
    private String fileName;

    public MyDownloadListener(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadStart(final String url, final String userAgent,
                                final String contentDisposition, final String mimetype, long contentLength) {
        fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
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

                        //Android6.0动态申请SD卡读写的权限
                        verifyStoragePermissions(mActivity);

                        // 设置下载文件的保存位置
                        File saveFile = new File(Environment.getExternalStorageDirectory(), fileName);
                        request.setDestinationUri(Uri.fromFile(saveFile));

                        long downloadId;

                        // 获取下载管理器服务的实例, 添加下载任务
                        DownloadManager manager = (DownloadManager) mActivity.getSystemService(Context.DOWNLOAD_SERVICE);

                        try {
                            // 将下载请求加入下载队列, 返回一个下载ID
                            downloadId = manager.enqueue(request);
                        } catch (SecurityException se) {
                            se.printStackTrace();
                            ToastUtil.showMessage("请授予应用写权限后重新下载");
                            return;
                        }

                        // 如果中途想取消下载, 可以调用remove方法, 根据返回的下载ID取消下载, 取消下载后下载保存的文件将被删除
                        // manager.remove(downloadId);
                        MyDownloadManagerReceiver mDownloadReceiver = new MyDownloadManagerReceiver();
                        IntentFilter filter = new IntentFilter();
                        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
                        mActivity.registerReceiver(mDownloadReceiver, filter);

                        // 创建一个查询对象
                        DownloadManager.Query query = new DownloadManager.Query();

                        // 根据 下载ID 过滤结果
                        query.setFilterById(downloadId);

                        // 执行查询, 返回一个 Cursor (相当于查询数据库)
                        Cursor cursor = manager.query(query);
                        try {
                            // 下载文件在本地保存的路径（Android 7.0 以后 COLUMN_LOCAL_FILENAME 字段被弃用, 需要用 COLUMN_LOCAL_URI 字段来获取本地文件路径的 Uri）
                            String localFilename = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));

                            ToastUtil.showMessage("下载到:" + localFilename);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cursor.close();
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
