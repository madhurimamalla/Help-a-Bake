package mmalla.android.com.helpabake.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

import mmalla.android.com.helpabake.R;
import mmalla.android.com.helpabake.RecipeDetailsActivity;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link HelpABakeWidgetConfigureActivity HelpABakeWidgetConfigureActivity}
 */
public class HelpABakeWidgetProvider extends AppWidgetProvider {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object from the widget XML layout
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.help_abake_widget);

        // Set up the collection depending on which
        // version is running on the device
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setRemoteAdapter(context, views);
        } else {
            setRemoteAdapterV11(context, views);
        }

        Intent appIntent = new Intent(context, RecipeDetailsActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * @param context
     * @param views
     */
    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Context context, RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_grid_view,
                new Intent(context, WidgetService.class));
    }

    /**
     * @param context
     * @param views
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Context context, RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_grid_view,
                new Intent(context, WidgetService.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Timber.d("Widget is going to be deleted now");
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            HelpABakeWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        Timber.d("onEnabled()... in the widget");
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        Timber.d("Inside onAppWidgetOptionsChanged!");
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("Inside onReceive() in the widget");
        super.onReceive(context, intent);
    }
}

