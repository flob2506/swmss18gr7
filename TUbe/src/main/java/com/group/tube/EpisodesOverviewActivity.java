package com.group.tube;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.group.tube.ArrayAdapter.EpisodeArrayAdapter;
import com.group.tube.Comparators.DateSortComparator;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episodes;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import android.widget.AdapterView.OnItemClickListener;


public class EpisodesOverviewActivity extends AppCompatActivity

{
    ArrayList<Episodes> episodes = new ArrayList<>();
    public static final String EXTRA_MESSAGE_EPISODE = "com.group.tube.coursesOverviewActivity.MESSAGE";

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episodes_overview);
        final Intent intent = new Intent(this, MainActivity.class);
        listView = findViewById(R.id.listViewEpisodes);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                intent.putExtra(EXTRA_MESSAGE_EPISODE, episodes.get(position).getId().toString());
                Log.d("msg",episodes.get(position).getId().toString() );
                startActivity(intent);
            }
        });
        listView = this.findViewById(R.id.listViewEpisodes);

        Intent get_intent = getIntent();
        String course_id = get_intent.getStringExtra(CoursesOverviewActivity.EXTRA_MESSAGE);
        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.loadEpisodes(new AsyncResponse<ArrayList<Episodes>>() {
            @Override
            public void processFinish(ArrayList<Episodes> response) {
                episodes = response;
                initializeListView(episodes);
            }
        }, course_id);
    }


    private void initializeListView(ArrayList<Episodes> episodes) {
        listView = findViewById(R.id.listViewEpisodes);
        this.episodes = episodes;
        Collections.sort(episodes, new DateSortComparator());
        EpisodeArrayAdapter arrayAdapter = new EpisodeArrayAdapter(this, episodes);
        listView.setAdapter(arrayAdapter);
    }

    /*

        ArrayList<Episodes> episodes = new ArrayList<Episodes>();
        episodes.add(new Episodes("i bims die ID1","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at"));
        episodes.add(new Episodes("i bims die ID2","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at"));
        episodes.add(new Episodes("i bims die ID3","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at"));
        episodes.add(new Episodes("i bims die ID4","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at"));
        episodes.add(new Episodes("i bims die ID5","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at"));
        ArrayAdapter<Episodes> arrayAdapter = new ArrayAdapter<Episodes>(this, R.layout.episodes_overview_item, episodes);
     */
}