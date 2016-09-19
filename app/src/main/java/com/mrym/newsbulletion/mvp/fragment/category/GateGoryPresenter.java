package com.mrym.newsbulletion.mvp.fragment.category;

import android.text.TextUtils;
import android.util.Log;

import com.mrym.newsbulletion.domain.constans.GlobalVariable;
import com.mrym.newsbulletion.domain.modle.NewsEntity;
import com.mrym.newsbulletion.mvp.BasePresenter;
import com.mrym.newsbulletion.mvp.fragment.mine.MineView;
import com.mrym.newsbulletion.rxjava.ApiCallback;
import com.mrym.newsbulletion.rxjava.SubscriberCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jian on 2016/9/14.
 * Email: 798774875@qq.com
 * Github: https://github.com/moruoyiming
 */
public class GateGoryPresenter extends BasePresenter<GateGoryView> {

    public GateGoryPresenter(GateGoryView getGoryView) {
        attachView(getGoryView);
    }

    public void getGategoryData(String gateGory, int pageIndex) {
//        Log.i("pageIndex", "当前页数：" + pageIndex);
        List<NewsEntity> newsList = new ArrayList<NewsEntity>();
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setId(0);
        newsEntity.setNewsId(0);
        newsEntity.setCollectStatus(false);
        newsEntity.setCommentNum(10);
        newsEntity.setInterestedStatus(true);
        newsEntity.setLikeStatus(true);
        newsEntity.setReadStatus(false);
        newsEntity.setNewsCategory("推荐");
        newsEntity.setNewsCategoryId(1);
        newsEntity.setAuthor("腾讯网");
        newsEntity.setHeadimg("http://p0.qhimg.com/t0136dd69d77510bbcb.jpg?size=780x520");
        newsEntity.setTitle("可以用谷歌眼镜做的10件酷事：导航、玩游戏");
        String url11 = "http://img1.imgtn.bdimg.com/it/u=1205280251,2634350720&fm=21&gp=0.jpg";
        String url21 = "http://p0.qhimg.com/t019c6684eeb0484ea1.jpg?size=1500x1000";
        String url31 = "http://p0.qhimg.com/t0107a32d125af97c90.jpg?size=1500x1000";
        newsEntity.setPicOne(url11);
        newsEntity.setPicTwo(url21);
        newsEntity.setPicThr(url31);
        List<String> url_list2 = new ArrayList<String>();
        url_list2.add(url11);
        url_list2.add(url21);
        url_list2.add(url31);
        newsEntity.setNewType(3);
        newsEntity.setPicList(url_list2);
        newsEntity.setPublishTime(Long.valueOf(1474188530));
        newsEntity.setReadStatus(false);
        newsEntity.setSource("手机腾讯网");
        newsEntity.setSummary("腾讯数码讯（编译：Gin）谷歌眼镜可能是目前最酷的可穿戴数码设备，你可以戴着它去任何地方（只要法律法规允许或是没有引起众怒），作为手机的第二块“增强现实显示屏”来使用。另外，虽然它仍未正式销售，但谷歌近日在美国市场举行了仅限一天的开放购买活动，价格则为1500美元（约合人民币9330元），虽然仍十分昂贵，但至少可以满足一些尝鲜者的需求，也预示着谷歌眼镜的公开大规模销售离我们越来越近了。");
        newsEntity.setMark(1);
        newsList.add(newsEntity);
        NewsEntity newsEntity2 = new NewsEntity();
        newsEntity2.setId(1);
        newsEntity2.setNewsId(1);
        newsEntity2.setCollectStatus(false);
        newsEntity2.setCommentNum(10);
        newsEntity2.setInterestedStatus(true);
        newsEntity2.setLikeStatus(true);
        newsEntity2.setReadStatus(false);
        newsEntity2.setNewsCategory("推荐");
        newsEntity2.setNewsCategoryId(1);
        newsEntity2.setAuthor("百度");
        newsEntity2.setHeadimg("http://p0.qhimg.com/t0136dd69d77510bbcb.jpg?size=780x520");
        newsEntity2.setTitle("习近平谈中国人民抗日战争");
        String url12 = "http://himg2.huanqiu.com/attachment2010/2016/0918/20160918083147693.jpg";
        newsEntity2.setPicOne(url12);
        List<String> url_list12 = new ArrayList<String>();
        url_list12.add(url12);
        newsEntity2.setNewType(2);
        newsEntity2.setPicList(url_list2);
        newsEntity2.setPublishTime(Long.valueOf(1474188530));
        newsEntity2.setReadStatus(false);
        newsEntity2.setSource("百度");
        newsEntity2.setSummary("2015年9月3日，纪念中国人民抗日战争暨世界反法西斯战争胜利70周年大会在北京天安门广场隆重举行。这是中共中央总书记、国家主席、中央军委主席习近平在大会上发表重要讲话。新华社记者兰红光摄");
        newsEntity2.setMark(1);
        newsList.add(newsEntity2);
        for (int i = 2; i < 10; i++) {
            NewsEntity news = new NewsEntity();
            news.setId(i);
            news.setNewsId(i);
            news.setCollectStatus(false);
            news.setCommentNum(i + 10);
            news.setInterestedStatus(true);
            news.setLikeStatus(true);
            news.setReadStatus(false);
            news.setNewsCategory("推荐");
            news.setNewsCategoryId(1);
            news.setAuthor("腾讯网");
            news.setHeadimg("http://p0.qhimg.com/t0136dd69d77510bbcb.jpg?size=780x520");
            List<String> url_list = new ArrayList<String>();
            if (i % 2 == 1) {
                String url1 = "http://d.hiphotos.baidu.com/image/h%3D200/sign=d9896d755f2c11dfc1d1b82353266255/342ac65c1038534340a22db79b13b07eca808833.jpg";
                String url2 = "http://img4.imgtn.bdimg.com/it/u=1817423038,3367055410&fm=21&gp=0.jpg";
                String url3 = "http://img5.imgtn.bdimg.com/it/u=1614013440,1930745513&fm=21&gp=0.jpg";
                news.setPicOne(url1);
                news.setPicTwo(url2);
                news.setPicThr(url3);
                url_list.add(url1);
                url_list.add(url2);
                url_list.add(url3);
                news.setTitle("可以用谷歌眼镜做的10件酷事：导航、玩游戏");
                news.setNewType(3);
            } else {
                news.setTitle("AA用车:智能短租租车平台");
                String url = "http://p0.qhimg.com/t010ad40562d5e4fed8.jpg?size=750x1125";
                news.setPicOne(url);
                url_list.add(url);
                news.setNewType(1);
            }
            news.setPicList(url_list);
            news.setPublishTime(Long.valueOf(i));
            news.setReadStatus(false);
            news.setSource("手机腾讯网");
            news.setSummary("腾讯数码讯（编译：Gin）谷歌眼镜可能是目前最酷的可穿戴数码设备，你可以戴着它去任何地方（只要法律法规允许或是没有引起众怒），作为手机的第二块“增强现实显示屏”来使用。另外，虽然它仍未正式销售，但谷歌近日在美国市场举行了仅限一天的开放购买活动，价格则为1500美元（约合人民币9330元），虽然仍十分昂贵，但至少可以满足一些尝鲜者的需求，也预示着谷歌眼镜的公开大规模销售离我们越来越近了。");
            news.setMark(i);
            if (i == 4) {
                news.setTitle("部落战争强势回归");
                news.setLocal("推广");
                news.setLarge(true);
                String url = "http://b.hiphotos.baidu.com/image/h%3D200/sign=203ed536fe03738dc14a0b22831ab073/91529822720e0cf34ef5e46d0246f21fbe09aa22.jpg";
                news.setPicOne(url);
                url_list.clear();
                url_list.add(url);
                news.setNewType(2);
            } else {
                news.setLarge(false);
            }
            newsList.add(news);
        }
        mvpView.loadComplete();
        mvpView.loadingSuccess(newsList);
//        addSubscription(apiStores.getCategoryNews(gateGory), new SubscriberCallBack<>(new ApiCallback<GateGoryModel>() {
//            @Override
//            public void onSuccess(GateGoryModel model) {
////                if (TextUtils.equals(model.getState(), GlobalVariable.SUCCESS)) {
////                    mvpView.loadingSuccess(model.getNews());
////                } else {
////                    mvpView.loadingError("加载失败！");
////                }
//                Log.i("GateGoryPresenter",model.toString());
//
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
////                mvpView.loadingError(msg);
//            }
//
//            @Override
//            public void onCompleted() {
//            }
//        }));
    }

}