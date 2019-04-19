package under.the.bridge.data.database.manager;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.google.gson.Gson;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import under.the.bridge.data.database.table.DaoMaster;
import under.the.bridge.data.database.table.DaoSession;
import under.the.bridge.data.database.table.SchedulerTable;
import under.the.bridge.data.database.table.SchedulerTableDao;
import under.the.bridge.data.database.table.WishlistTable;
import under.the.bridge.data.database.table.WishlistTableDao;

public class DatabaseManager implements InDatabaseManager, AsyncOperationListener {


    /**
     * Class tag. Used for debug.
     */
    private static final String TAG = DatabaseManager.class.getCanonicalName();
    /**
     * Instance of DatabaseManager
     */
    private static DatabaseManager instance;
    /**
     * The Android Activity reference for access to DatabaseManager.
     */
    private Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private Database database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private List<AsyncOperation> completedOperations;
    private Gson gson;

    /**
     * Constructs a new DatabaseManager with the specified arguments.
     *
     * @param context The Android {@link Context}.
     */
    public DatabaseManager(final Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context, "MUSIX_DATABASE");
        mHelper.setLoadSQLCipherNativeLibs(true);
        completedOperations = new CopyOnWriteArrayList<>();
    }

    /**
     * @param context The Android {@link Context}.
     * @return this.instance
     */

    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }

        return instance;
    }

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
//        gson = new Gson();
//        System.out.println(gson.toJson(operation));
        completedOperations.add(operation);
    }

    private void assertWaitForCompletion1Sec() {
        asyncSession.waitForCompletion(1000);
        asyncSession.isCompleted();
    }

    /**
     * Query for readable DB
     */
    public void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDb();
//        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    /**
     * Query for writable DB
     */
    public void openWritableDb() throws SQLiteException {
//        database = mHelper.getWritableDatabase();
        database = mHelper.getWritableDb();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    @Override
    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
//            daoSession = null;
        }
        if (database != null) {
            database.close();
        }
        if (mHelper != null) {
            mHelper.close();
//            mHelper = null;
        }
//        if (instance != null) {
//            instance = null;
//        }
    }

    @Override
    public synchronized void dropDatabase() {
        try {
            openWritableDb();
            DaoMaster.dropAllTables(database, true); // drops all tables
            mHelper.onCreate(database);              // creates the tables
            asyncSession.deleteAll(WishlistTable.class);    // clear all elements from a table
            // clear all elements from a table
//            asyncSession.deleteAll(DBUserDetails.class);
//            asyncSession.deleteAll(DBPhoneNumber.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized DaoSession getReadSession() {
        if (daoSession == null)
            openReadableDb();
        return daoSession;
    }

    @Override
    public List<WishlistTable> getWishlist() {
        try {
            openReadableDb();
            WishlistTableDao wishlistTableDao = daoSession.getWishlistTableDao();
            List<WishlistTable> wishlistTables = new ArrayList<>(wishlistTableDao.loadAll());
            closeDbConnections();
            return wishlistTables;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<SchedulerTable> getSchedule(String date) {
        try {
            openReadableDb();
            SchedulerTableDao schedulerTableDao = daoSession.getSchedulerTableDao();
            QueryBuilder queryBuilder = schedulerTableDao.queryBuilder()
                    .where(SchedulerTableDao.Properties.Date.eq(date));
            List<SchedulerTable> schedules = queryBuilder.list();
            closeDbConnections();
            return schedules;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean addWishlist(@Nullable WishlistTable wishlistTable) {
        try {
            openWritableDb();
            daoSession.insertOrReplace(wishlistTable);
            closeDbConnections();
            System.out.println("Wishlist updated");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeWishlist(@Nullable int trackId) {
        try {
            openWritableDb();
            WishlistTableDao wishlist = daoSession.getWishlistTableDao();
            QueryBuilder<WishlistTable> queryBuilder = wishlist.queryBuilder()
                    .where(WishlistTableDao.Properties.SongId.eq(String.valueOf(trackId)));
            WishlistTable wishlistTable = queryBuilder.list().get(0);
            wishlist.delete(wishlistTable);
            closeDbConnections();
            System.out.println("Wishlist updated");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addSchedule(SchedulerTable schedulerTable) {
        try {
            openWritableDb();
            daoSession.insertOrReplace(schedulerTable);
            closeDbConnections();
            System.out.println("Schedule updated");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
