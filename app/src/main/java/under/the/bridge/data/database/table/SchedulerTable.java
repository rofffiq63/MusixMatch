package under.the.bridge.data.database.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SchedulerTable {

    @org.greenrobot.greendao.annotation.Id(autoincrement = true)

    private Long Id;

    private String content;
    private String date;
    private String time;

    public SchedulerTable(String content, String date, String time) {
        this.content = content;
        this.date = date;
        this.time = time;
    }

    @Generated(hash = 1385731807)
    public SchedulerTable(Long Id, String content, String date, String time) {
        this.Id = Id;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    @Generated(hash = 1468518034)
    public SchedulerTable() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
