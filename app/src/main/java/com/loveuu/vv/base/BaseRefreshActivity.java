package com.loveuu.vv.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loveuu.vv.R;
import com.loveuu.vv.utils.UIUtils;

import org.byteam.superadapter.SuperAdapter;

import butterknife.BindView;

/**
 * Created by Wayne on 2016/7/27.
 * Email: loveuu715@163.com
 */
public abstract class BaseRefreshActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.xrcv_base_recyclerView)
    protected XRecyclerView mRecyclerView;
    @BindView(R.id.srl_base_refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private SpacesItemDecoration itemDecoration;
    protected int pageNo = 1;

    @Override
    public int bindLayout() {
        return R.layout.activity_base_refresh;
    }

    @Override
    public void init() {
        initToolbar();
        initRecyclerView();
    }

    public void initRecyclerView() {
        itemDecoration = new SpacesItemDecoration(UIUtils.dip2px(mContext, 12));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeRefreshLayout != null) {
                    refreshData(swipeRefreshLayout);
                }
            }
        };
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefreshListener.onRefresh(); // 第一次必须手动调用，直接调用setRefreshing不会触发onRefresh方法
            }
        }, 50);


        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (mRecyclerView != null) {
                    loadData(mRecyclerView);
                }
            }
        });
        SuperAdapter adapter = getAdapter();
        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
        }
        View headView = getHeaderView();
        if (headView != null) {
            mRecyclerView.addHeaderView(headView);
        }
    }

    protected void stopRefresh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 500);
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        mRecyclerView.setPadding(left, top, right, bottom);
    }

    public void setItemDecoration(int space) {
        mRecyclerView.removeItemDecoration(itemDecoration);
        itemDecoration = new SpacesItemDecoration(space);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    protected void stopLoadMore() {
        if (mRecyclerView != null) {
            mRecyclerView.loadMoreComplete();
        }
    }

    public abstract void initToolbar();

    protected abstract SuperAdapter getAdapter();

    protected abstract void refreshData(SwipeRefreshLayout refreshLayout);

    protected abstract void loadData(XRecyclerView recycleView);

    protected View getHeaderView() {
        return null;
    }
}
