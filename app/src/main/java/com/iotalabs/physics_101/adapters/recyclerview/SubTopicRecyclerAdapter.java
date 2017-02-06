package com.iotalabs.physics_101.adapters.recyclerview;

import java.util.ArrayList;

import com.iotalabs.physics_101.R;
import com.iotalabs.physics_101.activities.TopicDetailsActivity;
import com.iotalabs.physics_101.entity.SubTopicDO;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by karangarg on 28/01/17.
 */

public class SubTopicRecyclerAdapter extends
    RecyclerView.Adapter<SubTopicRecyclerAdapter.ReviewViewHolder> {

    private String TAG = SubTopicRecyclerAdapter.class.getSimpleName();
    private ArrayList<SubTopicDO> subTopicsList;
    private Context mContext;

    public SubTopicRecyclerAdapter(Context context, ArrayList<SubTopicDO> subTopicsList) {
        this.subTopicsList = subTopicsList;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return subTopicsList.size();
    }


    @Override
    public void onBindViewHolder(final ReviewViewHolder ReviewViewHolder, int i) {

        SubTopicDO subTopicDO = subTopicsList.get(i);
        ReviewViewHolder.subTopicName.setText(subTopicDO.getSubTopicName());
        ReviewViewHolder.subTopicHoursRequired.setText(subTopicDO.getHoursRequired());
        Picasso.with(mContext).load(subTopicDO.getThumbnailURL()).into(ReviewViewHolder.subTopicImage);

    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView;
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
            R.layout.recyclerview_item_subtopics, viewGroup, false);
        return new ReviewViewHolder(itemView, subTopicsList, mContext);

    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView subTopicName, subTopicHoursRequired;
        ImageView subTopicImage;
        LinearLayout subTopicParentView;
        ArrayList<SubTopicDO> subTopicsList;
        Context mContext;

        ReviewViewHolder(View v, ArrayList<SubTopicDO> subTopicsList, Context context) {
            super(v);
            this.mContext = context;
            this.subTopicsList = subTopicsList;

            subTopicImage = (ImageView) v.findViewById(R.id.itemSubTopicImage);
            subTopicName = (TextView) v.findViewById(R.id.itemSubTopicName);
            subTopicHoursRequired = (TextView) v.findViewById(R.id.itemSubTopicHoursRequired);
            subTopicParentView = (LinearLayout) v.findViewById(R.id.recyclerViewItemParentView);

            subTopicParentView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.recyclerViewItemParentView) {
                Intent intent = new Intent(mContext, TopicDetailsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putParcelable(mContext.getString(R.string.sub_topic_intent_key),
                    subTopicsList.get(getAdapterPosition()));
                intent.putExtras(mBundle);
                mContext.startActivity(intent);
            }
        }
    }


}
