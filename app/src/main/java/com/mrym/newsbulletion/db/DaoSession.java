package com.mrym.newsbulletion.db;

import com.mrym.newsbulletion.db.entity.NewsChannelTableDB;
import com.mrym.newsbulletion.db.entity.NewsChannelTableDBDao;
import com.mrym.newsbulletion.db.entity.VideoChannelTableDB;
import com.mrym.newsbulletion.db.entity.VideoChannelTableDBDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig newsChannelTableDBDaoConfig;
    private final DaoConfig videoChannelTableDBDaoConfig;

    private final NewsChannelTableDBDao newsChannelTableDBDao;
    private final VideoChannelTableDBDao videoChannelTableDBDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        newsChannelTableDBDaoConfig = daoConfigMap.get(NewsChannelTableDBDao.class).clone();
        newsChannelTableDBDaoConfig.initIdentityScope(type);

        videoChannelTableDBDaoConfig = daoConfigMap.get(VideoChannelTableDBDao.class).clone();
        videoChannelTableDBDaoConfig.initIdentityScope(type);

        newsChannelTableDBDao = new NewsChannelTableDBDao(newsChannelTableDBDaoConfig, this);
        videoChannelTableDBDao = new VideoChannelTableDBDao(videoChannelTableDBDaoConfig, this);

        registerDao(NewsChannelTableDB.class, newsChannelTableDBDao);
        registerDao(VideoChannelTableDB.class, videoChannelTableDBDao);
    }
    
    public void clear() {
        newsChannelTableDBDaoConfig.clearIdentityScope();
        videoChannelTableDBDaoConfig.clearIdentityScope();
    }

    public NewsChannelTableDBDao getNewsChannelTableDBDao() {
        return newsChannelTableDBDao;
    }

    public VideoChannelTableDBDao getVideoChannelTableDBDao() {
        return videoChannelTableDBDao;
    }

}
