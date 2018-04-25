package mmalla.android.com.helpabake.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import mmalla.android.com.helpabake.MainActivity;
import mmalla.android.com.helpabake.R;

/**
 * TODO Need to add the code to add custom recipe
 * and figure out how to do that
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    List<String> collection = new ArrayList<String>();
    Context mContext;
    Intent mIntent;



    public WidgetDataProvider(Context mContext, Intent mIntent) {
        this.mContext = mContext;
        this.mIntent = mIntent;
    }

    private void initData() {
        collection.clear();
        for (int i = 0; i <= 10; i++) {
            collection.add("ListView " + i);
        }
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_row);

        String text = collection.get(position);

        remoteViews.setTextViewText(R.id.recipe_name, text);
        remoteViews.setTextColor(R.id.recipe_name, Color.BLACK);

        Bundle extras = new Bundle();
        extras.putString(MainActivity.RECIPE_NAME_FROM_WIDGET, text);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.recipe_name, fillInIntent);

        return remoteViews;
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
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
