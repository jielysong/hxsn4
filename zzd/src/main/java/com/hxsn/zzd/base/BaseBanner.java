package com.hxsn.zzd.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hxsn.library.utils.ExceptionUtil;
import com.hxsn.zzd.R;
import com.hxsn.zzd.TApplication;
import com.hxsn.zzd.activity.LoginActivity;
import com.hxsn.zzd.biz.ImageBiz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * desc: 轮播图Created by jiely on 2015/8/28.
 */
public class BaseBanner {
    private static final String TAG = "baseBanner";
    private static final int STOP = 33;//结束异步任务的消息
    private static final int FINISH = 22;//异步任务完成一个循环
    private static final int REFRASH = 11;//更新UI
    private static ViewPager viewPager;
    private Integer[] resArray;
    private List<ImageView> imageList;//轮播图集合
    private Context context;
    private Activity activity;
    private int bgResId;//点或线的资源id
    private int margin; //指示点的间距
    private ViewGroup groupView;            //点或线的容器
    private int lastPosition;
    private SpannerHandler spannerHandler;
    private ScheduledExecutorService scheduled;
    private ViewPagerTask pagerTask;
    private int currPage = 0;
    private boolean isUrlBanner;
    private boolean isEndBanner = false;
    private int length = 0;
    private List<String> imgIds;

    public BaseBanner(Context context, Activity activity, ViewPager viewPager, ViewGroup groupView) {
        super();
        this.context = context;
        this.activity = activity;
        BaseBanner.viewPager = viewPager;
        this.groupView = groupView;
        resArray = new Integer[]{R.drawable.index01, R.drawable.index02, R.drawable.index03}; // 首次使用程序的显示的欢迎图片
    }


    public void init(List<String> ids) {
        setMargin(20);
        bgResId = R.drawable.bg_point;
        isUrlBanner = true;

        if (ids.size() != 0) {
            imgIds = ids;
            length = imgIds.size();
        } else {
            length = 0;
        }

        addBanner();
    }

    public void init() {
        isUrlBanner = false;
        setMargin(20);
        bgResId = R.drawable.bg_point;
        length = resArray.length;
        addBanner();
    }

    public void setBgResId(int bgResId) {
        this.bgResId = bgResId;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }


    /**
     * description:初始化Banner数据
     * auther:jiely
     * 2015-8-12上午10:42:37
     */
    private void addBanner() {
        imageList = new ArrayList<ImageView>();

        for (int i = 0; i < length; i++) {
            //初始化图片资源
            ImageView imageView = new ImageView(TApplication.context);
            if (!isUrlBanner) {
                imageView.setBackgroundResource(resArray[i]);
            } else {
                ImageBiz imageBiz = new ImageBiz(false);
                imageBiz.execute(imgIds.get(i), imageView);
            }

            imageList.add(imageView);

            //添加指示点
            ImageView lineView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.rightMargin = margin;
            lineView.setLayoutParams(params);

            lineView.setBackgroundResource(bgResId);
            if (i == 0) {
                lineView.setEnabled(true);
            } else {
                lineView.setEnabled(false);
            }
            groupView.addView(lineView);
        }

        viewPager.setAdapter(new BannerAdapter());

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                position = position % length;
                //改变指示点的状态
                //把当前点enbale 为true
                groupView.getChildAt(position).setEnabled(true);
                //把上一个点设为false
                groupView.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;
                //Log.d("banner", "轮播图的红点点+position="+position);
            }

        });
    }

    /**
     * description:开启轮播图
     * auther:jiely
     * 2015-8-12上午10:52:42
     */
    public void start() {
        //addBanner();
        //开启定时器，每隔2秒自动播放下一张（通过调用线程实现）（与Timer类似，可使用Timer代替）
        scheduled = Executors.newSingleThreadScheduledExecutor();
        //设置一个线程，该线程用于通知UI线程变换图片
        spannerHandler = new SpannerHandler();
        pagerTask = new ViewPagerTask();
        scheduled.scheduleAtFixedRate(pagerTask, 2, 4, TimeUnit.SECONDS);

    }

    public void stop() {
        if (scheduled != null && spannerHandler != null) {
            scheduled.shutdown();
            spannerHandler.removeCallbacks(pagerTask);

            Message msg = spannerHandler.obtainMessage();
            msg.what = STOP;
            spannerHandler.sendMessage(msg);
        }
    }

    /**
     * Created by Administrator on 16-4-7.
     */
    public static class MyBaseAdapter<T> extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    /**
     * desc:轮播图适配器
     *
     * @author jiely
     *         2015-8-12
     */
    private class BannerAdapter extends PagerAdapter {

        @Override
        /**
         * 获得页面的总数
         */
        public int getCount() {
            if (length == 0) {
                return resArray.length;//Integer.MAX_VALUE;
            }

            return length;
        }

        @Override
        /**
         * 获得相应位置上的view
         * container  view的容器，其实就是viewpager自身
         * position 	相应的位置
         */
        public Object instantiateItem(ViewGroup container, int position) {

            // 给 container 添加一个view
            if (length == 0) {
                return null;
            }
            ImageView imageView = imageList.get(position % length);

            if (position < length) {
                container.addView(imageView);
            }
            //返回一个和该view相对的object
            return imageView;
        }

        @Override
        /**
         * 判断 view和object的对应关系
         */
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        /**
         * 销毁对应位置上的object
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    //轮播图任务
    private class ViewPagerTask implements Runnable {

        @Override
        public void run() {
            //改变当前页面的值
            try {
                currPage++;
                Message msg = spannerHandler.obtainMessage();
                if (currPage == length) {
                    currPage = 0;
                    isEndBanner = true;
                    msg.what = FINISH;
                } else {
                    msg.what = REFRASH;
                }

                Bundle bundle = new Bundle();
                bundle.putInt("current", currPage);
                msg.setData(bundle);

                spannerHandler.sendMessage(msg);//发送消息给UI线程
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
        }
    }

    private class SpannerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRASH:
                    Bundle bundle = msg.getData();
                    int current = bundle.getInt("current");
                    viewPager.setCurrentItem(current);
                    break;
                case FINISH:
                    Intent intent = new Intent();
                    intent.setClass(context, LoginActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                    break;
                case STOP:
                    spannerHandler.removeCallbacks(pagerTask);
                    break;
            }

        }
    }
}
