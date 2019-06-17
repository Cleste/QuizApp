package kpimenov.flagquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class InputNameActivity extends AppCompatActivity {

    private EditText name;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);

        name = findViewById(R.id.name);
        final TextView bStart = findViewById(R.id.bStart);

        sessionManager = new SessionManager(InputNameActivity.this);
        sessionManager.checkLogin();

        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bStart.setText("Начало работы...");

                findViewById(R.id.progress).setVisibility(View.VISIBLE);
                sessionManager.createLoginSession(name.getText().toString(), "0.0", "0.0");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(InputNameActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out_from_view);
                        finish();
                    }
                }, 1000);

            }
        });

    }
}
