package com.iotalabs.physics_101.activities;

import java.util.ArrayList;
import java.util.List;

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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class TopicsActivity extends AppCompatActivity {

    private String KEY_SAVEDINSTANCE_DATA = "topicsDataSet";
    private ArrayList<TopicDO> topicsArrayList;
    private String TAG = TopicsActivity.class.getSimpleName();

    private String TAG_TOPIC_NAME = "topicName";
    private String TAG_IMAGE_URL = "imageURL";
    private String TAG_NUMBER_OF_SUBTOPICS = "numberOfSubTopics";
    private String TAG_NUMBER_OF_HOURS_REQUIRED = "hoursRequired";
    private String TAG_ID = "topicId";

    private String GET_TOPIC_URL = "http://10.51.0.235:8080/topics";

    private GridView topicGridView;

    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTopicActivity);
        setSupportActionBar(toolbar);

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
            fetchMovies.execute();
        }
        else {
            topicsArrayList = savedInstanceState.getParcelableArrayList(KEY_SAVEDINSTANCE_DATA);
            if (topicsArrayList != null) {
                topicGridView.setAdapter(new TopicsGridAdapter(TopicsActivity.this, topicsArrayList));
            }
            else {
                FetchTopics fetchMovies = new FetchTopics();
                fetchMovies.execute();
            }
        }

        topicGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(TopicsActivity.this, SubTopicsFragment.class);
                intent.putExtra(getString(R.string.topic_id_intent_key), topicsArrayList.get(position).getTopicId());
                intent
                    .putExtra(getString(R.string.topic_name_intent_key), topicsArrayList.get(position).getTopicName());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_SAVEDINSTANCE_DATA, topicsArrayList);
        super.onSaveInstanceState(outState);

    }

    private class FetchTopics extends AsyncTask<Void, ArrayList<TopicDO>, ArrayList<TopicDO>> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TopicsActivity.this);
            progressDialog.setTitle("LOADING");
            progressDialog.show();
        }

        @Override
        protected ArrayList<TopicDO> doInBackground(Void... params) {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Topics");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> topicsList, ParseException e) {
                    if (e == null) {
                        topicsArrayList = new ArrayList<>();
                        for(ParseObject topic : topicsList) {
                            TopicDO topicDO =  new TopicDO();
                            topicDO.setTopicId(topic.getInt(TAG_ID));
                            topicDO.setTopicName(topic.getString(TAG_TOPIC_NAME));
                            topicDO.setHoursRequired(topic.getInt(TAG_NUMBER_OF_HOURS_REQUIRED));
                            topicDO.setNumberOfSubTopics(topic.getInt(TAG_NUMBER_OF_SUBTOPICS));
                            topicDO.setImageURL(topic.getString(TAG_IMAGE_URL));
                            topicsArrayList.add(topicDO);
                        }
                    }
                    else {
                        topicsArrayList = null;
                    }
                }
            });
            return topicsArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<TopicDO> topicDOList) {
            super.onPostExecute(topicDOList);

            progressDialog.dismiss();

            if (topicDOList != null) {
                TopicsGridAdapter topicsGridAdapter = new TopicsGridAdapter(TopicsActivity.this, topicDOList);
                topicGridView.setAdapter(topicsGridAdapter);
            }
        }
    }

}
