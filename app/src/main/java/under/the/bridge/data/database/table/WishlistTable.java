package under.the.bridge.data.database.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class WishlistTable {

    @org.greenrobot.greendao.annotation.Id(autoincrement = true)

    private Long Id;

    private int songId;
    private String artist;
    private String title;

    public WishlistTable(int artistId, String artist, String title){
        this.songId = artistId;
        this.artist = artist;
        this.title = title;
    }

    @Generated(hash = 834028609)
    public WishlistTable(Long Id, int songId, String artist, String title) {
        this.Id = Id;
        this.songId = songId;
        this.artist = artist;
        this.title = title;
    }

    @Generated(hash = 1596090085)
    public WishlistTable() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public int getSongId() {
        return this.songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
