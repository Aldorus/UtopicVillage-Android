package com.exod.utopicvillage.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.activity.YourAskingHelpActivity;

public class NotificationHelper {
    private Context mContext;
    private int NOTIFICATION_ID = 42;
    private Notification mNotification;
    private NotificationManager mNotificationManager;
    private PendingIntent mContentIntent;
    private CharSequence mContentTitle;
    
    public NotificationHelper(Context context){
        mContext = context;
    }

    /**
     * Put the notification into the status bar
     */
    public void createNotification(String userName,String msg) {
        //get the notification manager
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        //create the notification
        int icon = R.drawable.ic_launcher_utopic;
        CharSequence tickerText = mContext.getString(R.string.app_name); //Initial text that appears in the status bar
        mNotification = new Notification(icon, tickerText, 1);

        //create the content which is shown in the notification pulldown
        mContentTitle = mContext.getString(R.string.app_name); //Full title of the notification in the pull down
        CharSequence contentText = userName+" : "+msg+""; //Text of the notification in the pull down

        //you have to set a PendingIntent on a notification to tell the system what you want it to do when the notification is selected
        //I don't want to use this here so I'm just creating a blank one
        Intent notificationIntent = new Intent(mContext,YourAskingHelpActivity.class);
        mContentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);

        //add the additional content and intent to the notification
        mNotification.setLatestEventInfo(mContext, mContentTitle, contentText, mContentIntent);
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        //show the notification
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }
    
    public void deleteNotification(){ 
        final NotificationManager notificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE); 
        //la suppression de la notification se fait grâce à son ID 
        notificationManager.cancel(NOTIFICATION_ID); 
    } 
}
