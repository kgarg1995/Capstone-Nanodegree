package com.iotalabs.physics_101.widget.service;

import com.iotalabs.physics_101.R;
import com.iotalabs.physics_101.dbcontracts.SubTopicsContract;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by karangarg on 05/02/17.
 */

public class AppWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new SubTopicsRemoteViewsFactory(AppWidgetService.this);
    }
}

class SubTopicsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Cursor mCursor;

    public SubTopicsRemoteViewsFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long token = Binder.clearCallingIdentity();
        try {
            if (mCursor != null) {
                mCursor.close();
            }

            mCursor = mContext.getContentResolver().query(SubTopicsContract.SubTopicsEntry.CONTENT_URI,
                new String[] { SubTopicsContract.SubTopicsEntry._ID, SubTopicsContract.SubTopicsEntry.HOURS_REQUIRED,
                    SubTopicsContract.SubTopicsEntry.SUB_TOPIC_DESCRIPTION, SubTopicsContract.SubTopicsEntry.SUB_TOPIC_NAME,
                    SubTopicsContract.SubTopicsEntry.IMAGE_URL,
                    SubTopicsContract.SubTopicsEntry.THUMBNAIL_URL }, null, null, null);
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    @Override
    public void onDestroy() {
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteView = new RemoteViews(mContext.getPackageName(),
            R.layout.widget_layout_item);

        mCursor.moveToPosition(position);
        String subTopicDescription = mCursor.getString(1);
        remoteView.setTextViewText(R.id.widgetItemSubTopicDesc, subTopicDescription);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
