package com.fta.greendaotest;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * 文件描述：
 * 作者： Created by fta on 2017/4/23.
 */

public class NoteTypeConverter implements PropertyConverter<NoteType,String> {

    @Override
    public NoteType convertToEntityProperty(String databaseValue) {
        return NoteType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(NoteType entityProperty) {
        return entityProperty.name();
    }
}
