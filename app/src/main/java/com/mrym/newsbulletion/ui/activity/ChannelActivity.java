package com.mrym.newsbulletion.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrym.newsbulletion.R;
import com.mrym.newsbulletion.db.GreenDaoManager;
import com.mrym.newsbulletion.db.entity.ChannelSelected;
import com.mrym.newsbulletion.db.entity.ChannelunSelected;
import com.mrym.newsbulletion.db.gen.ChannelSelectedDao;
import com.mrym.newsbulletion.db.gen.ChannelunSelectedDao;
import com.mrym.newsbulletion.db.other.NewsChannelTableManager;
import com.mrym.newsbulletion.mvp.BasePresenter;
import com.mrym.newsbulletion.ui.BaseActivity;
import com.mrym.newsbulletion.utils.statusbar.StatusBarCompat;
import com.mrym.newsbulletion.widget.DragAdapter;
import com.mrym.newsbulletion.widget.DragGrid;
import com.mrym.newsbulletion.widget.OtherAdapter;
import com.mrym.newsbulletion.widget.OtherGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 频道管理
 */
public class ChannelActivity extends BaseActivity implements OnItemClickListener {
    public static String TAG = "ChannelActivity";
    /**
     * 用户栏目的GRIDVIEW
     */
    private DragGrid userGridView;
    /**
     * 其它栏目的GRIDVIEW
     */
    private OtherGridView otherGridView;
    /**
     * 用户栏目对应的适配器，可以拖动
     */
    DragAdapter userAdapter;
    /**
     * 其它栏目对应的适配器
     */
    OtherAdapter otherAdapter;
    /**
     * 其它栏目列表
     */
    List<ChannelunSelected> otherChannelList = new ArrayList<>();
    /**
     * 用户栏目列表
     */
    List<ChannelSelected> userChannelList = new ArrayList<>();
    /**
     * 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。
     */
    boolean isMove = false;
    @BindView(R.id.leftback_toobar_l1)
    RelativeLayout back;
    @BindView(R.id.left_back_title)
    TextView mTitle;
    @BindView(R.id.header)
    LinearLayout header;

    /**
     * 初始化数据
     */
    private void initData() {
        userChannelList = NewsChannelTableManager.loadNewsChannelsStatic();
        otherChannelList = NewsChannelTableManager.loadNewsChannelsMine();
        userAdapter = new DragAdapter(this, userChannelList);
        userGridView.setAdapter(userAdapter);
        otherAdapter = new OtherAdapter(this, otherChannelList);
        otherGridView.setAdapter(otherAdapter);
        //设置GRIDVIEW的ITEM的点击监听
        otherGridView.setOnItemClickListener(this);
        userGridView.setOnItemClickListener(this);
    }

    /**
     * 入口
     *
     * @param mContext
     */
    public static void startAction(Activity mContext) {
        Intent intent = new Intent(mContext, ChannelActivity.class);
        mContext.startActivityForResult(intent, 5);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    /**
     * GRIDVIEW对应的ITEM点击监听接口
     */
    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
        //如果点击的时候，之前动画还没结束，那么就让点击事件无效
        if (isMove) {
            return;
        }
        switch (parent.getId()) {
            case R.id.userGridView:
                //position为 0，1 的不可以进行任何操作
                if (position != 0 && position != 1) {
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final ChannelSelected channel = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
                        ChannelunSelected channelunSelected = new ChannelunSelected();
                        channelunSelected.setNewsChannelId(channel.getNewsChannelId());
                        channelunSelected.setNewsChannelName(channel.getNewsChannelName());
                        channelunSelected.setNewsChannelType(channel.getNewsChannelType());
                        channelunSelected.setNewsChannelSelect(true);
                        otherAdapter.setVisible(false);
                        //添加到最后一个
                        otherAdapter.addItem(channelunSelected);
                        try {
                            ChannelSelectedDao channelSelectedDao = GreenDaoManager.getInstance().getSession().getChannelSelectedDao();
                            channelSelectedDao.delete(channel);
                            ChannelunSelectedDao channelunSelectedDao = GreenDaoManager.getInstance().getSession().getChannelunSelectedDao();
                            channelunSelectedDao.insert(channelunSelected);
                        } catch (Exception e) {
                            Log.i(TAG, "数据库操作失败");
                        }
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation, endLocation, userGridView);
                                    userAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
                    }
                }
                break;
            case R.id.otherGridView:
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelunSelected channelunSelected = ((OtherAdapter) parent.getAdapter()).getItem(position);
                    userAdapter.setVisible(false);
                    //添加到最后一个
                    ChannelSelected channelSelected = new ChannelSelected();
                    channelSelected.setNewsChannelId(channelunSelected.getNewsChannelId());
                    channelSelected.setNewsChannelName(channelunSelected.getNewsChannelName());
                    channelSelected.setNewsChannelType(channelunSelected.getNewsChannelType());
                    channelSelected.setNewsChannelSelect(true);
                    try {
                        ChannelSelectedDao channelSelectedDao = GreenDaoManager.getInstance().getSession().getChannelSelectedDao();
                        channelSelectedDao.insert(channelSelected);
                        ChannelunSelectedDao channelunSelectedDao = GreenDaoManager.getInstance().getSession().getChannelunSelectedDao();
                        channelunSelectedDao.delete(channelunSelected);
                    } catch (Exception e) {
                        Log.i(TAG, "数据库操作失败");
                    }
                    userAdapter.addItem(channelSelected);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation, endLocation, otherGridView);
                                otherAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 点击ITEM移动动画
     *
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param clickGridView
     */
    private void MoveAnim(View moveView, int[] startLocation, int[] endLocation,
                          final GridView clickGridView) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
                if (clickGridView instanceof DragGrid) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    userAdapter.remove();
                } else {
                    userAdapter.setVisible(true);
                    userAdapter.notifyDataSetChanged();
                    otherAdapter.remove();
                }
                isMove = false;
            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     *
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 获取点击的Item的对应View，
     *
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected String getTag() {
        return null;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_channel;
    }

    @Override
    protected void setUpView() {
        userGridView = (DragGrid) findViewById(R.id.userGridView);
        otherGridView = (OtherGridView) findViewById(R.id.otherGridView);
        StatusBarCompat.translucentStatusBar(ChannelActivity.this, true);
//        dynamicAddView(header, "background", R.color.primary_dark);
        mTitle.setText("频道设置");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
    }

    @Override
    protected void destroyActivityBefore() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userGridView = null;
        otherGridView = null;
        userChannelList.clear();
        userChannelList = null;
        otherChannelList.clear();
        otherChannelList = null;
        otherAdapter = null;
        userAdapter = null;
    }

    @Override
    public void onBackPressed() {
        if (userAdapter.isListChanged()) {
            setResult(5);
            finish();
            Log.d(TAG, "数据发生改变");
        } else {
            super.onBackPressed();
        }
    }
}
