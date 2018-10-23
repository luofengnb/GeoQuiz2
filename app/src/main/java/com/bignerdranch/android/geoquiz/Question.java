package com.bignerdranch.android.geoquiz;

/**
 * Created by luofeng on 2017/11/6.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private String test ="hello";

    public Question(int textResId,boolean answerTrue){
        mAnswerTrue=answerTrue;
        mTextResId=textResId;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
