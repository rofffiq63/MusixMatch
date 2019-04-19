package under.the.bridge.data.database.manager;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import under.the.bridge.data.database.table.DaoSession;
import under.the.bridge.data.database.table.SchedulerTable;
import under.the.bridge.data.database.table.WishlistTable;

public interface InDatabaseManager {

    void closeDbConnections();

    /**
     * Delete all tables and content from our database
     */
    void dropDatabase();

    DaoSession getReadSession();

    List<WishlistTable> getWishlist();
    List<SchedulerTable> getSchedule(String date);

    boolean addWishlist(@Nullable WishlistTable wishlistTable);
    boolean removeWishlist(@Nullable int trackId);

    boolean addSchedule(SchedulerTable schedulerTable);
}
