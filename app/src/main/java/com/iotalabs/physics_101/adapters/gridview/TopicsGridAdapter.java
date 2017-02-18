package com.iotalabs.physics_101.adapters.gridview;

import java.util.ArrayList;

import com.iotalabs.physics_101.R;
import com.iotalabs.physics_101.entity.TopicDO;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by karangarg on 28/01/17.
 */

public class TopicsGridAdapter extends BaseAdapter {

    private ArrayList<TopicDO> topicsList;
    private Context mContext;
    private static String TAG = TopicsGridAdapter.class.getSimpleName();

    public TopicsGridAdapter(Context context, ArrayList<TopicDO> topicsList) {
        this.topicsList = topicsList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return topicsList.size();
    }

    @Override
    public Object getItem(int position) {
        return topicsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder gridViewHolder;

        if (convertView == null) {

            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.gridview_item_topics, parent, false);

            // initialize the view holder
            gridViewHolder = new GridViewHolder();
            gridViewHolder.posterImage = (ImageView) convertView.findViewById(R.id.itemTopicImage);
            gridViewHolder.topicName = (TextView) convertView.findViewById(R.id.itemTopicName);
            gridViewHolder.numberOfSubTopics = (TextView) convertView.findViewById(R.id.itemNumberOfSubTopics);
            gridViewHolder.hoursRequired = (TextView) convertView.findViewById(R.id.itemHoursRequired);
            convertView.setTag(gridViewHolder);
        }
        else {
            // recycle the already inflated view
            gridViewHolder = (GridViewHolder) convertView.getTag();
        }

        // update the item view
        final TopicDO topicDO = topicsList.get(position);
        gridViewHolder.topicName.setText(topicDO.getTopicName());
        gridViewHolder.numberOfSubTopics.setText(String.valueOf(topicDO.getNumberOfSubTopics()));
        gridViewHolder.hoursRequired.setText(String.valueOf(topicDO.getHoursRequired()));
        Picasso.with(mContext).load(topicDO.getImageURL()).into(gridViewHolder.posterImage);

        return convertView;
    }

    private static class GridViewHolder {
        ImageView posterImage;
        TextView topicName;
        TextView numberOfSubTopics;
        TextView hoursRequired;
    }
}
