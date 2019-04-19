package under.the.bridge.data.database.table;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import under.the.bridge.data.database.table.SchedulerTable;
import under.the.bridge.data.database.table.WishlistTable;

import under.the.bridge.data.database.table.SchedulerTableDao;
import under.the.bridge.data.database.table.WishlistTableDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig schedulerTableDaoConfig;
    private final DaoConfig wishlistTableDaoConfig;

    private final SchedulerTableDao schedulerTableDao;
    private final WishlistTableDao wishlistTableDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        schedulerTableDaoConfig = daoConfigMap.get(SchedulerTableDao.class).clone();
        schedulerTableDaoConfig.initIdentityScope(type);

        wishlistTableDaoConfig = daoConfigMap.get(WishlistTableDao.class).clone();
        wishlistTableDaoConfig.initIdentityScope(type);

        schedulerTableDao = new SchedulerTableDao(schedulerTableDaoConfig, this);
        wishlistTableDao = new WishlistTableDao(wishlistTableDaoConfig, this);

        registerDao(SchedulerTable.class, schedulerTableDao);
        registerDao(WishlistTable.class, wishlistTableDao);
    }
    
    public void clear() {
        schedulerTableDaoConfig.clearIdentityScope();
        wishlistTableDaoConfig.clearIdentityScope();
    }

    public SchedulerTableDao getSchedulerTableDao() {
        return schedulerTableDao;
    }

    public WishlistTableDao getWishlistTableDao() {
        return wishlistTableDao;
    }

}
