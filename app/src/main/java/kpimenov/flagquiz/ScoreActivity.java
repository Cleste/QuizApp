package kpimenov.flagquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;

public class ScoreActivity extends AppCompatActivity {

    private TextView mScore, mResult, mValue, mTop;
    private RatingBar mRat;
    String sScore, top,last,name;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedPreferences.Editor editor = getSharedPreferences("RESUME", MODE_PRIVATE).edit();
        editor.putString("back", "0");
        editor.apply();

        mScore = findViewById(R.id.mScore);
        mResult = findViewById(R.id.txtResult);
        mValue = findViewById(R.id.txtValue);
        mRat = findViewById(R.id.rat);
        mTop = findViewById(R.id.txtTop);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        sScore = bundle.getString("one");

        mScore.setText(String.valueOf(sScore));

        setRating();
        CountValue();

        findViewById(R.id.review).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("RESUME", MODE_PRIVATE).edit();
                editor.putString("back", "2");
                editor.apply();
                finish();
            }
        });

        findViewById(R.id.Rerun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("RESUME", MODE_PRIVATE).edit();
                editor.putString("back", "3");
                editor.apply();
                finish();
            }
        });

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        top = user.get(SessionManager.KEY_TOP);
        last = user.get(SessionManager.KEY_LAST);
        name = user.get(SessionManager.KEY_NAME);

        setScore();
    }

    private void setRating(){
        Double intScore = Double.valueOf(sScore);
        if (intScore < 5.0){
            mResult.setText("Можно и лучше!");
            mRat.setRating((float)1.5);
        } else if (intScore > 5.0 && intScore < 6.5){
            mResult.setText("Хороший результат!");
            mRat.setRating((float)2.5);
        }else if (intScore > 6.5 && intScore < 8.0){
            mResult.setText("Отличный результат!");
            mRat.setRating((float)4.0);
        }else if (intScore > 8.0 && intScore < 10.0){
            mResult.setText("Вы справились превосходно!");
            mRat.setRating((float)4.5);
        }

        if (intScore == 0.0){
            mResult.setText("Упс!");
            mRat.setRating((float)0.0);
        }

        if (intScore == 10.0){
            mResult.setText("Идеально!");
            mRat.setRating((float)5.0);
        }
    }

    private void CountValue(){
        Double Score = Double.valueOf(sScore);
        Double Scores = Score / 10;

        Double val = Scores * 100;

        mValue.append(String.valueOf(val));
    }

    private void setScore(){

        sessionManager.createLoginSession(name,String.valueOf(top),sScore);

        Float tops = Float.valueOf(top);
        Float lasts = Float.valueOf(sScore);
        if (lasts > tops){
            mTop.append(sScore);
            sessionManager.createLoginSession(name,sScore,sScore);
        }else {
            mTop.append(top);
            sessionManager.createLoginSession(name,top,sScore);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor editor = getSharedPreferences("RESUME", MODE_PRIVATE).edit();
        editor.putString("back", "1");
        editor.apply();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out_from_view);
    }
}
