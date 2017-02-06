package com.iotalabs.physics_101.activities;

import com.iotalabs.physics_101.R;
import com.iotalabs.physics_101.fragments.TopicDetailFragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by karangarg on 28/01/17.
 */

public class TopicDetailsActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_details);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.topic_detail_container, new TopicDetailFragment())
                .commit();
        }

    }

}
