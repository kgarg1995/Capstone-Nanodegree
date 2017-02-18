package com.iotalabs.physics_101.fragments;

import java.util.ArrayList;
import java.util.List;

import com.iotalabs.physics_101.R;
import com.iotalabs.physics_101.adapters.recyclerview.SubTopicRecyclerAdapter;
import com.iotalabs.physics_101.entity.SubTopicDO;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by karangarg on 28/01/17.
 */

public class SubTopicsFragment extends Fragment {

    private AppCompatActivity activity;
    private String topicId, topicName;
    private String TAG = SubTopicsFragment.class.getSimpleName();
    private String GET_SUBTOPIC_URL = "http://10.51.0.235:8080/subtopics?topic_id=";
    private ArrayList<SubTopicDO> subTopicsList;

    private String KEY_SAVEDINSTANCE_DATA = "subTopicDataSet";

    private static String TAG_TOPIC_ID = "topicId";
    private static String TAG_SUBTOPIC_ID = "subTopicId";
    private static String TAG_SUBTOPIC_NAME = "subTopicName";
    private static String TAG_SUBTOPIC_THUMBNAIL_URL = "thumbnailURL";
    private static String TAG_SUBTOPIC_IAMGE_URL = "imageURL";
    private static String TAG_SUBTOPIC_DESCRIPTION = "subTopicDescription";
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
        }
        else {
            topicName = null;
            topicId = null;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState)
    {
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

        if (topicName != null) {
            activity.getSupportActionBar().setTitle(topicName);
        }

        if (savedInstanceState == null || !savedInstanceState.containsKey(KEY_SAVEDINSTANCE_DATA)) {
            //NO DATA SAVED, FETCH FROM INTERNET
            if (topicId != null) {
                FetchSubTopics fetchSubTopics = new FetchSubTopics();
                fetchSubTopics.execute(topicId);
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
                    fetchSubTopics.execute(topicId);
                }
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_SAVEDINSTANCE_DATA, subTopicsList);
        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            // Do stuff
            activity.finish();
        }
        return true;
    }


    private class FetchSubTopics extends AsyncTask<String, ArrayList<SubTopicDO>, ArrayList<SubTopicDO>> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity.getBaseContext());
            progressDialog.setTitle("LOADING");
            progressDialog.show();
        }

        @Override
        protected ArrayList<SubTopicDO> doInBackground(String... params) {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("SubTopics");
            query.whereEqualTo("topicId", params[0]);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> subTopicsDOList, ParseException e) {
                    if (e == null) {
                        subTopicsList = new ArrayList<>();
                        for (ParseObject subTopic : subTopicsDOList) {
                            SubTopicDO subTopicDO = new SubTopicDO();

                            subTopicDO.setSubTopicId(subTopic.getInt(TAG_SUBTOPIC_ID));
                            subTopicDO.setTopicId(subTopic.getInt(TAG_TOPIC_ID));
                            subTopicDO.setSubTopicName(subTopic.getString(TAG_SUBTOPIC_NAME));
                            subTopicDO.setThumbnailURL(subTopic.getString(TAG_SUBTOPIC_THUMBNAIL_URL));
                            subTopicDO.setImageURL(subTopic.getString(TAG_SUBTOPIC_IAMGE_URL));
                            subTopicDO.setSubTopicDescription(subTopic.getString(TAG_SUBTOPIC_DESCRIPTION));
                            subTopicDO.setHoursRequired(subTopic.getInt(TAG_SUBTOPIC_HOURS_REQUIRED));

                            subTopicsList.add(subTopicDO);
                        }
                    }
                    else {
                        subTopicsList = null;
                    }
                }
            });

            return subTopicsList;
        }

        @Override
        protected void onPostExecute(ArrayList<SubTopicDO> subTopicsList) {
            super.onPostExecute(subTopicsList);

            progressDialog.dismiss();

            //TODO call adapter here
            if(subTopicsList != null) {
                mAdapter = new SubTopicRecyclerAdapter(activity, subTopicsList);
                mRecyclerView.setAdapter(mAdapter);
            }
        }

    }

}
