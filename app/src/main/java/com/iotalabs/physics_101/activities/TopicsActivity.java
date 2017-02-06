package com.iotalabs.physics_101.activities;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.iotalabs.physics_101.R;
import com.iotalabs.physics_101.adapters.gridview.TopicsGridAdapter;
import com.iotalabs.physics_101.applications.Physics101Application;
import com.iotalabs.physics_101.entity.TopicDO;
import com.iotalabs.physics_101.fragments.SubTopicsFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TopicsActivity extends AppCompatActivity {

    private String KEY_SAVEDINSTANCE_DATA = "topicsDataSet";
    private ArrayList<TopicDO> topicsArrayList;
    private String TAG = TopicsActivity.class.getSimpleName();

    private String TAG_RESULTS = "results";
    private String TAG_TOPIC_NAME = "topicName";
    private String TAG_IMAGE_URL = "imageURL";
    private String TAG_NUMBER_OF_SUBTOPICS = "noOfSubTopics";
    private String TAG_NUMBER_OF_HOURS_REQUIRED = "hoursRequired";
    private String TAG_ID = "id";

    private String GET_TOPIC_URL = "http://10.51.0.235:8080/topics";

    private GridView topicGridView;

    private OkHttpClient client;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTopicActivity);
        setSupportActionBar(toolbar);
        client = new OkHttpClient();

        //initialize admob instance
        MobileAds.initialize(getApplicationContext(), getString(R.string.admob_app_id));

        //save app open in GA
        Physics101Application application = (Physics101Application) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName(TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        //Display ads
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        topicGridView = (GridView) findViewById(R.id.gridViewListTopics);

        if (savedInstanceState == null || !savedInstanceState.containsKey(KEY_SAVEDINSTANCE_DATA)) {
            //NO DATA SAVED, FETCH FROM INTERNET
            FetchTopics fetchMovies = new FetchTopics();
            fetchMovies.execute(GET_TOPIC_URL);
        }
        else {
            topicsArrayList = savedInstanceState.getParcelableArrayList(KEY_SAVEDINSTANCE_DATA);
            if (topicsArrayList != null) {
                topicGridView.setAdapter(new TopicsGridAdapter(TopicsActivity.this, topicsArrayList));
            }
            else {
                FetchTopics fetchMovies = new FetchTopics();
                fetchMovies.execute(GET_TOPIC_URL);
            }
        }

        topicGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(TopicsActivity.this, SubTopicsFragment.class);
                intent.putExtra(getString(R.string.topic_id_intent_key), topicsArrayList.get(position).getId());
                intent
                    .putExtra(getString(R.string.topic_name_intent_key), topicsArrayList.get(position).getTopicName());
                startActivity(intent);
            }
        });

    }

    String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_SAVEDINSTANCE_DATA, topicsArrayList);
        super.onSaveInstanceState(outState);

    }

    private class FetchTopics extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return run(params[0]);
            }
            catch (IOException e) {
                Log.d(TAG, "okhttp error " + e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                Log.d(TAG, "result " + result);

                try {
                    JSONObject resultJSON = new JSONObject(result);
                    JSONArray resultJSONArray = resultJSON.getJSONArray(TAG_RESULTS);

                    topicsArrayList = new ArrayList<>();

                    for (int i = 0; i < resultJSONArray.length(); i++) {

                        JSONObject topicItem = resultJSONArray.getJSONObject(i);
                        TopicDO topic = new TopicDO();
                        topic.setId(topicItem.getString(TAG_ID));
                        topic.setTopicName(topicItem.getString(TAG_TOPIC_NAME));
                        topic.setHoursRequired(topicItem.getString(TAG_NUMBER_OF_HOURS_REQUIRED));
                        topic.setImageURL(topicItem.getString(TAG_IMAGE_URL));
                        topic.setNumberOfSubTopics(topicItem.getString(TAG_NUMBER_OF_SUBTOPICS));
                        Log.d(TAG, "Title " + topic.getId());

                        topicsArrayList.add(topic);
                    }

                    TopicsGridAdapter topicsGridAdapter = new TopicsGridAdapter(TopicsActivity.this, topicsArrayList);
                    topicGridView.setAdapter(topicsGridAdapter);

                }
                catch (Exception e) {
                    // Check log for errors
                    e.printStackTrace();
                }

            }
        }

    }

}
