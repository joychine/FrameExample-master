package com.otcyan.jwidget;

import com.trello.rxlifecycle.components.support.RxFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by 01096612 on 2016/6/28.
 *
 */
public abstract  class BaseFragment extends RxFragment {

    protected AppCompatActivity mActivity;
    private Dialog loadingDialog;
    protected Toolbar mToolbar ;
    protected TextView mToolbarTitle ;

    /**
     * 获得全局的，防止使用getActivity()为空
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (AppCompatActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {
        View view = LayoutInflater.from(mActivity)
                .inflate(getLayoutId(), container, false);
        ButterKnife.bind(this,view);
        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(hasToolBar()){
            initToolBar();
        }
        initData();
    }

    private void initToolBar(){
        mToolbar = ButterKnife.findById(mActivity, R.id.toolbar) ;
        mToolbarTitle = ButterKnife.findById(mActivity , R.id.toolbar_base_title) ;
        mActivity.setSupportActionBar(mToolbar);
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (null != actionBar) {
            actionBar.setTitle(mActivity.getTitle());
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void showProgressDialog() {
        if (null == loadingDialog) {
            loadingDialog = new Dialog(mActivity, R.style.dl_base_loading);
            loadingDialog.setContentView(R.layout.view_loading);
        }
        if (loadingDialog.isShowing()) return;
        if (mActivity.hasWindowFocus()) loadingDialog.show();
    }

    protected void hideProgressDialog() {
        if (null != loadingDialog && loadingDialog.isShowing()) loadingDialog.dismiss();
    }


    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 该抽象方法就是 初始化view
     * @param view
     * @param savedInstanceState
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 执行数据的加载
     */
    protected abstract void initData();

    protected  abstract  boolean hasToolBar();

}
