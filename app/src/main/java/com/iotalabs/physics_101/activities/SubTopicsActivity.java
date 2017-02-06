package com.iotalabs.physics_101.activities;

import com.iotalabs.physics_101.R;
import com.iotalabs.physics_101.fragments.TopicDetailFragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by karangarg on 28/01/17.
 */

public class SubTopicsActivity extends AppCompatActivity {

    private static final String DETAILS_FRAGMENT_TAG = "DFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtopic);

        if (findViewById(R.id.topic_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.topic_detail_container, new TopicDetailFragment(), DETAILS_FRAGMENT_TAG)
                    .commit();
            }
        }
    }
}
