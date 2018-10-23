package com.bignerdranch.android.geoquiz;

/**
 * Created by luofeng on 2017/12/21.
 */

public class QuestionDbSchema {
    public static final class QuestionTable{
        public static final String NAME="questions";//描述数据表名

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TXT = "txt";
            public static final String TUREORFALSE = "tureOrFalse";
        }
    }
}
