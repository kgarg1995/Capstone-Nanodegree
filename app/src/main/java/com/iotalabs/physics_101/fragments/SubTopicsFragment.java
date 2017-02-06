package com.iotalabs.physics_101.fragments;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.iotalabs.physics_101.R;
import com.iotalabs.physics_101.adapters.recyclerview.SubTopicRecyclerAdapter;
import com.iotalabs.physics_101.entity.SubTopicDO;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by karangarg on 28/01/17.
 */

public class SubTopicsFragment extends Fragment {

    private AppCompatActivity activity;
    private String topicId, topicName;
    private String TAG = SubTopicsFragment.class.getSimpleName();
    private OkHttpClient client;
    private String GET_SUBTOPIC_URL = "http://10.51.0.235:8080/subtopics?topic_id=";
    private ArrayList<SubTopicDO> subTopicsList;

    private String KEY_SAVEDINSTANCE_DATA = "subTopicDataSet";

    private static String TAG_RESULT = "results";
    private static String TAG_SUBTOPIC_ID = "id";
    private static String TAG_SUBTOPIC_NAME = "name";
    private static String TAG_SUBTOPIC_THUMBNAIL_URL = "thumbnailURL";
    private static String TAG_SUBTOPIC_IAMGE_URL = "imageURL";
    private static String TAG_SUBTOPIC_DESCRIPTION = "description";
    private static String TAG_SUBTOPIC_HOURS_REQUIRED = "hoursRequired";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AppCompatActivity) getActivity();

        Intent i = activity.getIntent();
        if (i != null) {
            topicId = i.getStringExtra(getString(R.string.topic_name_intent_key));
            topicName = i.getStringExtra(getString(R.string.topic_id_intent_key));
        } else {
            topicName = null;
            topicId = null;
        }

        client = new OkHttpClient();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_subtopic, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbarSubTopicActivity);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewSubTopicList);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(activity);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        if(topicName != null) {
            activity.getSupportActionBar().setTitle(topicName);
        }

        if (savedInstanceState == null || !savedInstanceState.containsKey(KEY_SAVEDINSTANCE_DATA)) {
            //NO DATA SAVED, FETCH FROM INTERNET
            if (topicId != null) {
                FetchSubTopics fetchSubTopics = new FetchSubTopics();
                fetchSubTopics.execute(GET_SUBTOPIC_URL + topicId);
            }
        }
        else {
            subTopicsList = savedInstanceState.getParcelableArrayList(KEY_SAVEDINSTANCE_DATA);
            if (subTopicsList != null) {
                mAdapter = new SubTopicRecyclerAdapter(activity, subTopicsList);
                mRecyclerView.setAdapter(mAdapter);
            }
            else {
                if (topicId != null) {
                    FetchSubTopics fetchSubTopics = new FetchSubTopics();
                    fetchSubTopics.execute(GET_SUBTOPIC_URL + topicId);
                }
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_SAVEDINSTANCE_DATA , subTopicsList);
        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == android.R.id.home){
            // Do stuff
            activity.finish();
        }
        return true;
    }

    String run(String url) throws IOException {

        Log.d(TAG, "URL " + url);

        Request request = new Request.Builder()
            .url(url)
            .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private class FetchSubTopics extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            String JSONResult = null;
            try {
                JSONResult = run(params[0]);
            } catch (IOException e) {
                Log.e(TAG, "OkHTTP error " + e.getMessage());
                JSONResult = null;
            }
            return JSONResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            subTopicsList = new ArrayList<>();

            if (result != null) {
                try {
                    Log.d(TAG, "result " + result);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultArray = jsonObject.getJSONArray(TAG_RESULT);
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject arrayObject = resultArray.getJSONObject(i);
                        SubTopicDO subTopicDO = new SubTopicDO();

                        subTopicDO.setId(arrayObject.getString(TAG_SUBTOPIC_ID));
                        subTopicDO.setSubTopicName(arrayObject.getString(TAG_SUBTOPIC_NAME));
                        subTopicDO.setThumbnailURL(arrayObject.getString(TAG_SUBTOPIC_THUMBNAIL_URL));
                        subTopicDO.setImageURL(arrayObject.getString(TAG_SUBTOPIC_IAMGE_URL));
                        subTopicDO.setSubTopicDescription(arrayObject.getString(TAG_SUBTOPIC_DESCRIPTION));
                        subTopicDO.setHoursRequired(arrayObject.getString(TAG_SUBTOPIC_HOURS_REQUIRED));

                        subTopicsList.add(subTopicDO);
                    }

                    //TODO call adapter here
                    mAdapter = new SubTopicRecyclerAdapter(activity, subTopicsList);
                    mRecyclerView.setAdapter(mAdapter);

                } catch (Exception e) {
                    Log.e(TAG, "JSON error " + e.getMessage());
                }

            }

        }
    }


}
