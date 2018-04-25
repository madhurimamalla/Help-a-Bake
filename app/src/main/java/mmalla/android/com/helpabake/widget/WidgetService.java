package mmalla.android.com.helpabake.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
         //return remote view factory here
        return new WidgetDataProvider(this, intent);
    }
}
