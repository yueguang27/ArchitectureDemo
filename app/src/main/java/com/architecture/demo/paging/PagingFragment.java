package com.architecture.demo.paging;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.architecture.demo.R;
import com.architecture.demo.room.StudentEntity;

/**
 * Created by cym on 18-9-11.
 */
public class PagingFragment extends android.support.v4.app.Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    MAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    PagingViewModel mPagingViewModel;

    public static PagingFragment getInstance() {
        return new PagingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        initView();
        return view;
    }

    private void initView() {
        if (getContext() == null) return;
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new MAdapter();
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPagingViewModel == null) {
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
                refreshData();
            }
        });
        initViewModel();
    }

    private void initViewModel() {
        if (getActivity() == null) return;
        mPagingViewModel = ViewModelProviders.of(getActivity()).get(PagingViewModel.class);
        mPagingViewModel.getData().observe(this, new Observer<PagedList<StudentEntity>>() {
            @Override
            public void onChanged(@Nullable PagedList<StudentEntity> pagedList) {
                swipeRefreshLayout.setRefreshing(false);
                if (mAdapter != null) {
                    mAdapter.submitList(pagedList);
                }
            }
        });
    }

    public void refreshData() {
        if (mPagingViewModel == null) return;
        PagedList<StudentEntity> value = mPagingViewModel.getData().getValue();
        if (value != null) {
            DataSource<?, StudentEntity> dataSource = value.getDataSource();
            dataSource.invalidate();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    class MAdapter extends PagedListAdapter<StudentEntity, MViewHolder> {

        MAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_view, parent, false);
            return new MViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
            StudentEntity item = getItem(position);
            String text = "";
            if (item != null) {
                text = item.getNumber() + ":" + item.getName();
            }
            holder.textView.setText(text);
        }
    }

    class MViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        MViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }

    public static final DiffUtil.ItemCallback<StudentEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<StudentEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull StudentEntity oldUser, @NonNull StudentEntity newUser) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUser.getId() == newUser.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull StudentEntity oldUser, @NonNull StudentEntity newUser) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldUser.equals(newUser);
                }
            };
}
