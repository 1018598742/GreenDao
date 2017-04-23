package com.fta.greendaotest;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * 文件描述：
 * 作者： Created by fta on 2017/4/23.
 */
//指定唯一索引并降序排列(DESC为降序排列，ASC为升序排列)
@Entity(indexes = {
        @Index(value = "text,date DESC", unique = true)
})
public class Note {
    @Id
    private Long id;//必须为 Long 类型，否则会报主键唯一错误
    @NotNull
    private String text;
    private String comment;
    private Date date;

    @Convert(converter = NoteTypeConverter.class, columnType = String.class)
    private NoteType type;

    //写完上面的代码点击 Build -> Make.. 下面的代码会自动生成，注意，使用的是 GreenDao 3

    @Generated(hash = 59778150)
    public Note(Long id, @NotNull String text, String comment, Date date,
                NoteType type) {
        this.id = id;
        this.text = text;
        this.comment = comment;
        this.date = date;
        this.type = type;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(@NotNull String text) {
        this.text = text;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public NoteType getType() {
        return this.type;
    }

    public void setType(NoteType type) {
        this.type = type;
    }


}
