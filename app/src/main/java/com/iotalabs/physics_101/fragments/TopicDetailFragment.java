package com.iotalabs.physics_101.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.iotalabs.physics_101.R;
import com.iotalabs.physics_101.dbcontracts.SubTopicsContract;
import com.iotalabs.physics_101.entity.SubTopicDO;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import okhttp3.OkHttpClient;

/**
 * Created by karangarg on 28/01/17.
 */

public class TopicDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Bitmap> {

    private static final int LOADER_ID = 1010;
    private SubTopicDO subTopicDO;
    private OkHttpClient client;
    private AppCompatActivity activity;
    private ImageView topicDetailImage;
    private TextView topicDetailsDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AppCompatActivity) getActivity();

        Intent i = activity.getIntent();
        if (i != null) {
            subTopicDO = i.getParcelableExtra(getString(R.string.sub_topic_intent_key));
        } else {
            subTopicDO = null;
        }

        client = new OkHttpClient();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_topic_details, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbarTopicDetails);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        topicDetailImage = (ImageView) rootView.findViewById(R.id.topicDetailsImage);
        topicDetailsDescription = (TextView) rootView.findViewById(R.id.topicDetailsDescription);


        if (subTopicDO != null) {
            activity.getSupportActionBar().setTitle(subTopicDO.getSubTopicName());
            topicDetailsDescription.setText(subTopicDO.getSubTopicDescription());

            addSubTopics(subTopicDO);
        }
        activity.getSupportLoaderManager().initLoader(LOADER_ID , null, this).forceLoad();
        return rootView;
    }

    private void addSubTopics(SubTopicDO subTopicDO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SubTopicsContract.SubTopicsEntry._ID, subTopicDO.getSubTopicId());
        contentValues.put(SubTopicsContract.SubTopicsEntry.HOURS_REQUIRED, subTopicDO.getHoursRequired());
        contentValues.put(SubTopicsContract.SubTopicsEntry.IMAGE_URL, subTopicDO.getImageURL());
        contentValues.put(SubTopicsContract.SubTopicsEntry.SUB_TOPIC_NAME, subTopicDO.getSubTopicName());
        contentValues.put(SubTopicsContract.SubTopicsEntry.THUMBNAIL_URL, subTopicDO.getThumbnailURL());
        contentValues.put(SubTopicsContract.SubTopicsEntry.SUB_TOPIC_DESCRIPTION, subTopicDO.getSubTopicDescription());

        getContext().getContentResolver().insert(
            SubTopicsContract.SubTopicsEntry.buildSubTopicsUri(), contentValues);
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

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<Bitmap>(activity) {

            @Override
            public Bitmap loadInBackground() {
                Bitmap bitmap = null;
                String url = subTopicDO.getImageURL();
                try {
                    InputStream is = (InputStream) new URL(url).openConnection().getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }
        };

    }

    @Override
    public void onLoadFinished(Loader loader, Bitmap data) {
        topicDetailImage.setImageBitmap(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
