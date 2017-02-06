package com.iotalabs.physics_101.widget;

import com.iotalabs.physics_101.R;
import com.iotalabs.physics_101.widget.service.AppWidgetService;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Created by karangarg on 03/02/17.
 */

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent widgetServiceIntent = new Intent(context, AppWidgetService.class);
            widgetServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            widgetServiceIntent.setData(Uri.parse(widgetServiceIntent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            remoteViews.setRemoteAdapter(R.id.widget_list_view, widgetServiceIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

}

