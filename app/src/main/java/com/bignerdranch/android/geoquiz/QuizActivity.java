package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_TURE_OR_WRONG="ture_or_wrong";
    private static final String KEY_RIGHT_TIMES = "right_times";
    private static final String KEY_WRONG_TIMES = "wrong_times";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPriviousButton;
    private TextView mQuestionTextView;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true)
    };

    private  boolean[] isAnswer = new boolean[mQuestionBank.length];
    private int mCurrentIndex = 0;
    private int rightTimes = 0,wrongTimes=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if(savedInstanceState != null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
            isAnswer=savedInstanceState.getBooleanArray(KEY_TURE_OR_WRONG);
            rightTimes=savedInstanceState.getInt(KEY_RIGHT_TIMES,0);
            wrongTimes=savedInstanceState.getInt(KEY_WRONG_TIMES,0);
        }

        mContext=QuizActivity.this.getApplicationContext();
        mDatabase=new QuestionBaseHelper(mContext).getWritableDatabase();

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mTrueButton=(Button)findViewById(R.id.true_button);
        mFalseButton=(Button)findViewById(R.id.false_button);
        updateInformation();
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){//点击文字区域跳到下一题
            @Override
            public void onClick(View v){
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                updateInformation();
            }
        });


        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });
        mPriviousButton=(ImageButton)findViewById(R.id.previous_button);
        mPriviousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex+mQuestionBank.length-1)%mQuestionBank.length;
                updateInformation();
            }
        });
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                updateInformation();
            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putInt(KEY_RIGHT_TIMES,rightTimes);
        savedInstanceState.putInt(KEY_WRONG_TIMES,wrongTimes);
        savedInstanceState.putBooleanArray(KEY_TURE_OR_WRONG,isAnswer);
    }
    private void updateInformation(){
        Log.e(TAG+"123","Update Information ");
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        if(isAnswer[mCurrentIndex]){

            mTrueButton.setVisibility(View.INVISIBLE);
            mFalseButton.setVisibility(View.INVISIBLE);
        }else{

            mTrueButton.setVisibility(View.VISIBLE);
            mFalseButton.setVisibility(View.VISIBLE);
        }
    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();//当前问题的正确性
        int messageResId = 0;
        if(isAnswer[mCurrentIndex]){
            messageResId=R.string.answered_toast;
        }else if(userPressedTrue==answerIsTrue){
            rightTimes++;
            messageResId = R.string.correct_toast;
        }else{
            wrongTimes++;
            messageResId = R.string.incorrent_toast;
        }
        isAnswer[mCurrentIndex]=true;
        mTrueButton.setVisibility(View.INVISIBLE);
        mFalseButton.setVisibility(View.INVISIBLE);

        Toast toast=Toast.makeText(this,messageResId,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
        Toast.makeText(this,"right:"+rightTimes+"  wrong:"+wrongTimes,Toast.LENGTH_LONG).show();
        if(rightTimes+wrongTimes==mQuestionBank.length){
            int correctRate=(int)(rightTimes*1.0/(rightTimes+wrongTimes)*100);
            Toast.makeText(this,"Congratulations!You finish it!\nCorrect rate:"+correctRate+"%",Toast.LENGTH_LONG).show();
        }
    }

}
