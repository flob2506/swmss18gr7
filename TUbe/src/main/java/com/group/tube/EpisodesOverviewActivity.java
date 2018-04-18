package com.group.tube;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.group.tube.ArrayAdapter.EpisodeArrayAdapter;
import com.group.tube.Models.Episodes;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EpisodesOverviewActivity extends AppCompatActivity {
    ArrayList<Episodes> episodes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episodes_overview);
        initializeListView();
    }


    private void initializeListView() {
        final ListView listView = findViewById(R.id.listViewEpisodes);
        episodes = TestDataGenerator.getRandomEpisodeList();
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
