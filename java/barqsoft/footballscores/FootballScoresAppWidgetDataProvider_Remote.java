package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import java.util.ArrayList;
import java.util.List;

public class FootballScoresAppWidgetDataProvider_Remote implements RemoteViewsFactory {
    List<CharSequence> mCollections = new ArrayList();

    Context mContext = null;

    public FootballScoresAppWidgetDataProvider_Remote(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCollections.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews mView = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        mView.setTextViewText(android.R.id.text1, mCollections.get(position));
        mView.setTextColor(android.R.id.text1, 0xfcf4b92f);
        //Log.v(save_tag, "fragment: " + String.valueOf(savedInstanceState.getInt("Pager_Current")));
        //Log.v(save_tag,"selected id: "+savedInstanceState.getInt("Selected_match"));
        /*
        final Intent footballIntent = new Intent(mContext, MainActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putInt("Pager_Current", position);
        bundle.putInt("Selected_match", 0);
        footballIntent.putExtras(bundle);
        mView.setOnClickFillInIntent(android.R.id.text1, footballIntent);
        */

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(FootballScoresAppWidgetProvider.receiveActionName);//mention in Manifest and check in Receiver
        final Bundle bundle = new Bundle();
        bundle.putString("Pager_Current", mCollections.get(position).toString());
        fillInIntent.putExtras(bundle);
        mView.setOnClickFillInIntent(android.R.id.text1, fillInIntent);
        //setOnClickPendingIntent() : not for list, 1 button for app
        //setOnClickFillInIntent()  : for a list, a single template is created for all items
        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {
        //can read DB etc
        mCollections.clear();
        mCollections.add("Yesterday");
        mCollections.add("Today");
        mCollections.add("Tomorrow");
    }

    @Override
    public void onDestroy() {

    }
}
