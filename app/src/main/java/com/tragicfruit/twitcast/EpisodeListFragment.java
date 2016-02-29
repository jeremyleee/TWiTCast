package com.tragicfruit.twitcast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jeremy on 29/02/2016.
 */
public class EpisodeListFragment extends Fragment {
    private static final String ARG_SHOW_ID = "show_id";

    private RecyclerView mRecyclerView;
    private Show mShow;
    private List<Episode> mEpisodeList;

    public static EpisodeListFragment newInstance(int showId) {
        Bundle args = new Bundle();
        args.putInt(ARG_SHOW_ID, showId);

        EpisodeListFragment fragment = new EpisodeListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int showId = getArguments().getInt(ARG_SHOW_ID);
        mShow = TWiTDatabase.get().getShow(showId);
        mEpisodeList = mShow.getEpisodes();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(mShow.getTitle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_episode_list, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_episode_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new EpisodeAdapter());

        return v;
    }

    private class EpisodeHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;

        public EpisodeHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView;
        }

        public void bindEpisode(Episode episode) {
            mTitleTextView.setText(episode.getTitle());
        }
    }

    private class EpisodeAdapter extends RecyclerView.Adapter<EpisodeHolder> {

        @Override
        public EpisodeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new EpisodeHolder(view);
        }

        @Override
        public void onBindViewHolder(EpisodeHolder holder, int position) {
            Episode episode = mEpisodeList.get(position);
            holder.bindEpisode(episode);
        }

        @Override
        public int getItemCount() {
            return mEpisodeList.size();
        }
    }
}