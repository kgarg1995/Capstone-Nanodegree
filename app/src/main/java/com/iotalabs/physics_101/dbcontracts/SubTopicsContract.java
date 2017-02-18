package com.iotalabs.physics_101.dbcontracts;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by karangarg on 05/02/17.
 */

public class SubTopicsContract {

    public static final String CONTENT_AUTHORITY = "com.iotalabs.physics_101";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SUB_TOPICS = "subTopics";

    public static final class SubTopicsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUB_TOPICS).build();

        public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUB_TOPICS;
        public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUB_TOPICS;

        public static final String TABLE_NAME = "subtopics";

        public static final String HOURS_REQUIRED = "hoursRequired";

        public static final String SUB_TOPIC_DESCRIPTION = "subTopicDescription";

        public static final String SUB_TOPIC_NAME = "subTopicName";

        public static final String IMAGE_URL = "imageURL";

        public static final String THUMBNAIL_URL = "thumbnailURL";

        public static Uri buildSubTopicsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildSubTopicsUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildSubTopicsWithDetailsUri(int id) {
            return CONTENT_URI.buildUpon().appendQueryParameter(_ID, Integer.toString(id)).build();
        }
    }
}


