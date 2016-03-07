package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class FootballScoresAppWidgetProvider extends AppWidgetProvider {
    String tag = "FootballScoresAppWidgetProvider";
    public static String receiveActionName = "MYACTION";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(tag, "onReceive" + intent.getAction());
        if(intent.getAction().equals(receiveActionName)) { //Ignore for all other events
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            populateIntent(context, manager, 1, intent);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Control comes here when widget is attached to host(reboot/homeScreenInstall etc)
        //RemoteViews : this sets up the ListView
        //Service point to the DataProvider
        //Note : onReceive(action=UPDATE) is called before this
        for (int widgetId : appWidgetIds) {
            RemoteViews mView = initViews(context, appWidgetManager, widgetId);
            appWidgetManager.updateAppWidget(widgetId, mView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    private RemoteViews populateIntent(Context context, AppWidgetManager mgr, int widgetId, Intent intent){
        Intent footballIntent = new Intent(context, MainActivity.class);
        footballIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        footballIntent.setData(Uri.parse(footballIntent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, footballIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_provider_layout);
        remoteViews.setOnClickPendingIntent(R.id.container, pendingIntent);
        mgr.updateAppWidget(widgetId, remoteViews);
        return remoteViews;
    }
    private RemoteViews initViews(Context context,
                                  AppWidgetManager widgetManager, int widgetId) {
        // Adding collection list item handler
        Intent intent = new Intent(context, FootballScoresAppWidgetDataService_Remote.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Instantiate the RemoteViews object for the app widget layout.
        RemoteViews mView = new RemoteViews(context.getPackageName(), R.layout.app_widget_provider_layout);
        mView.setRemoteAdapter(widgetId, R.id.widgetCollectionList, intent);
        mView.setEmptyView(R.id.widgetCollectionList, R.id.list_item);
        widgetManager.updateAppWidget(widgetId, mView);
        return mView;
    }

}
