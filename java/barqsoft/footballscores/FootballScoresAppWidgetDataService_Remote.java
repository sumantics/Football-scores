package barqsoft.footballscores;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FootballScoresAppWidgetDataService_Remote extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        FootballScoresAppWidgetDataProvider_Remote dataProvider = new FootballScoresAppWidgetDataProvider_Remote(
                getApplicationContext(), intent);
        return dataProvider;
    }
}
