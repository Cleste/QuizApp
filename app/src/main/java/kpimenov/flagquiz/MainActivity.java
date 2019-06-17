package kpimenov.flagquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RadioButton answer1,answer2,answer3, answer61, answer62, answer63, answer64, answer9;
    private EditText answer4s, answer7;
    private CheckBox answer51, answer52, answer53, answer54, answer101, answer102;
    private RadioGroup radioGroup6;
    private Button bMore, bLess;
    private Spinner answer81, answer82, answer83;
    private ScrollView scrollView;

    Double score;
    int ans7 = 0;

    SessionManager sessionManager;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences.Editor editor = getSharedPreferences("RESUME", MODE_PRIVATE).edit();
        editor.putString("back", "0");
        editor.apply();

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);

        answer1 = findViewById(R.id.j1);
        answer2 = findViewById(R.id.j2);
        answer3 = findViewById(R.id.j3);
        answer4s = findViewById(R.id.j4);
        answer51 = findViewById(R.id.j51);
        answer52 = findViewById(R.id.j52);
        answer53 = findViewById(R.id.j53);
        answer54 = findViewById(R.id.j54);
        answer61 = findViewById(R.id.j61);
        answer62 = findViewById(R.id.j62);
        answer63 = findViewById(R.id.j63);
        answer64 = findViewById(R.id.j64);
        radioGroup6 = findViewById(R.id.rg6);
        bMore = findViewById(R.id.bMore);
        bLess = findViewById(R.id.bLess);
        answer7 = findViewById(R.id.j7);
        answer81 = findViewById(R.id.j81);
        answer82 = findViewById(R.id.j82);
        answer83 = findViewById(R.id.j83);
        answer9 = findViewById(R.id.j9);
        answer101 = findViewById(R.id.j101);
        answer102 = findViewById(R.id.j102);

        scrollView = findViewById(R.id.scroll);


        findViewById(R.id.bSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkanswer1to4();
            }
        });

        bMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans7 = Integer.parseInt(answer7.getText().toString());
                ans7++;
                answer7.setText(String.valueOf(ans7));
            }
        });

        bLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans7 = Integer.parseInt(answer7.getText().toString());
                ans7--;
                if (ans7 < 1){
                    answer7.setText("1");
                    Toast.makeText(MainActivity.this, "Минимальная сумма 1!", Toast.LENGTH_SHORT).show();
                }else {
                    answer7.setText(String.valueOf(ans7));
                }
            }
        });

        findViewById(R.id.bRerun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out_from_view);
                finish();
            }
        });

    }

    private void checkanswer1to4(){

        score = 0.0;

        if (answer1.isChecked()){
            score++;
        }
        if (answer2.isChecked()){
            score++;
        }
        if (answer3.isChecked()){
            score++;
        }
        String answer4 = answer4s.getText().toString();
        if (answer4.equals("France") || answer4.equals("france") || answer4.equals("франция") || answer4.equals("Франция")){
            score++;
        }

        if (answer51.isChecked()){
            score = score + 0.25;
        }

        if (answer52.isChecked()){
            score = score + 0.25;
        }

        if (answer53.isChecked()){
            score = score + 0.25;
        }

        if (answer54.isChecked()){
            score = score + 0.25;
        }

        if (answer61.isChecked()){
            score = score + 0.50;
        }

        if (answer62.isChecked()){
            score = score + 0.50;
        }

        if (answer63.isChecked()){
            score = score + 0.50;
        }

        if (answer64.isChecked()){
            score++;
        }

        if (answer7.getText().toString().equals("6")){
            score++;
        }

        if (answer81.getSelectedItem().equals("Черный") && answer82.getSelectedItem().equals("Красный") && answer83.getSelectedItem().equals("Оранжевый")){
            score++;
        }

        if (answer9.isChecked()){
            score++;
        }

        if (answer101.isChecked()){
            score = score + 0.50;
        }

        if (answer102.isChecked()){
            score = score + 0.50;
        }

        String sscore = String.valueOf(score);

        Intent i = new Intent(MainActivity.this, ScoreActivity.class);
        i.putExtra("one", sscore);
        startActivity(i);
        overridePendingTransition(R.anim.zoom_in_from_view, R.anim.zoom_out_from_view);

    }

    protected void onResume() {

        HashMap<String, String> user = sessionManager.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);

        super.onResume();
        SharedPreferences spref = getSharedPreferences("RESUME", MODE_PRIVATE);
        String back = spref.getString("back", null);

        if (back.equals("1")) {
            Toast.makeText(MainActivity.this, "Удачного прохождения " + name, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out_from_view);
            finish();
        } else if (back.equals("2")) {
            answer1.setBackgroundColor(Color.GREEN);
            answer2.setBackgroundColor(Color.GREEN);
            answer3.setBackgroundColor(Color.GREEN);
            answer4s.setBackgroundColor(Color.GREEN);
            answer4s.setText("France/Франция");
            answer51.setBackgroundColor(Color.GREEN);
            answer52.setBackgroundColor(Color.GREEN);
            answer53.setBackgroundColor(Color.GREEN);
            answer54.setBackgroundColor(Color.GREEN);
            answer64.setBackgroundColor(Color.GREEN);
            answer7.setBackgroundColor(Color.GREEN);
            answer7.setText("6");
            answer81.setBackgroundColor(Color.GREEN);
            answer81.setSelection(3);
            answer82.setBackgroundColor(Color.GREEN);
            answer82.setSelection(1);
            answer83.setBackgroundColor(Color.GREEN);
            answer83.setSelection(4);
            answer9.setBackgroundColor(Color.GREEN);
            answer101.setBackgroundColor(Color.GREEN);
            answer102.setBackgroundColor(Color.GREEN);
            findViewById(R.id.bSubmit).setVisibility(View.GONE);
            findViewById(R.id.bRerun).setVisibility(View.VISIBLE);

            scrollView.fullScroll(View.FOCUS_UP);
            Toast.makeText(MainActivity.this, "Посмотрите свои ответы " + name, Toast.LENGTH_SHORT).show();
        } else if (back.equals("3")) {
            Toast.makeText(MainActivity.this, name +  " вы перезапустили тест", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out_from_view);
            finish();
        } else if (back.equals("4")) {
            startActivity(new Intent(MainActivity.this, InputNameActivity.class));
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out_from_view);
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Удачного прохождения " + name, Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = getSharedPreferences("RESUME", MODE_PRIVATE).edit();
            editor.putString("back", "0");
            editor.apply();
        }
    }

    @Override
    public void onBackPressed() {

        quitPer();

    }

        public void quitPer(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Подтверждение");
        alertDialog.setMessage("Вы уверены, что хотите выйти? ");

        alertDialog.setNegativeButton((CharSequence) " Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        alertDialog.setPositiveButton((CharSequence) "Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profil) {

            startActivity(new Intent(MainActivity.this, ProfilActivity.class));
            overridePendingTransition(R.anim.zoom_in_from_view, R.anim.zoom_out_from_view);
        }
        if (id == R.id.about) {

            final View views = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_dialog_about, null);
            final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
            final ImageButton vclose = views.findViewById(R.id.close);
            dialog.setView(views);
            dialog.show();

            final RelativeLayout email = views.findViewById(R.id.email);
            final RelativeLayout phone = views.findViewById(R.id.phone);
            final RelativeLayout sms = views.findViewById(R.id.sms);
            final RelativeLayout wa = views.findViewById(R.id.wa);

            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("mailto:kpimen123@gmail.com"));
                    startActivity(intent);
                }
            });

            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("tel:+7981815693"));
                    startActivity(intent);

                }
            });

            sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("smsto:+79871815693"));
                    startActivity(intent);

                }
            });

            wa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("smsto:+79871815693"));
                    intent.setPackage("com.whatsapp");
                    startActivity(intent);

                }
            });

            vclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}
