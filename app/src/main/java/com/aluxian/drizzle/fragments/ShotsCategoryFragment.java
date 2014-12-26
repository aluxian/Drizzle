package com.aluxian.drizzle.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.aluxian.drizzle.R;
import com.aluxian.drizzle.api.Params;
import com.aluxian.drizzle.recycler.GridItemAnimator;
import com.aluxian.drizzle.recycler.ShotsGridAdapter;

public class ShotsCategoryFragment extends Fragment {

    private static final String ARG_CATEGORY_NAME = "category_name";
    private ShotsGridAdapter gridAdapter;

    public static ShotsCategoryFragment newInstance(String categoryName) {
        ShotsCategoryFragment fragment = new ShotsCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY_NAME, categoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Params.List category = Params.List.valueOf(getArguments().getString(ARG_CATEGORY_NAME));
        View view = inflater.inflate(R.layout.fragment_shots_category, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.grid);

        ((SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh))
                .setColorSchemeResources(R.color.primary_dark);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        GridItemAnimator animator = new GridItemAnimator(layoutManager);
        animator.setSupportsChangeAnimations(true);
        animator.setAddDuration(500);

        recyclerView.setItemAnimator(animator);
        recyclerView.setHasFixedSize(true);

        gridAdapter = new ShotsGridAdapter(getActivity(), layoutManager, category, Params.Timeframe.NOW, Params.Sort.POPULAR,
                (ProgressBar) view.findViewById(R.id.loading_indicator));
        recyclerView.setAdapter(gridAdapter);

        return view;
    }

    public void setTimeframeParam(Params.Timeframe timeframe) {
        gridAdapter.setTimeframeParam(timeframe);
    }

    public void setSortParam(Params.Sort sort) {
        gridAdapter.setSortParam(sort);
    }

    public void applyParams() {
        // TODO
    }

    public Params.Timeframe getTimeframeParam() {
        return gridAdapter.getTimeframeParam();
    }

    public Params.Sort getSortParam() {
        return gridAdapter.getSortParam();
    }

}