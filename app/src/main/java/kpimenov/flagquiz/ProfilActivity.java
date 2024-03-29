package kpimenov.flagquiz;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    String name, top, last;
    TextView vlast, vtop, vnameView;
    RelativeLayout vnamesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        vlast = findViewById(R.id.last);
        vtop = findViewById(R.id.top);
        vnameView = findViewById(R.id.vname);
        vnamesButton = findViewById(R.id.names);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        top = user.get(SessionManager.KEY_TOP);
        last = user.get(SessionManager.KEY_LAST);
        name = user.get(SessionManager.KEY_NAME);

        vlast.setText(last);
        vtop.setText(top);
        vnameView.setText(name);

        findViewById(R.id.names).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View views = LayoutInflater.from(ProfilActivity.this).inflate(R.layout.view_dialog, null);
                final AlertDialog dialog = new AlertDialog.Builder(ProfilActivity.this).create();
                final EditText vname = views.findViewById(R.id.name);
                final TextView vsave = views.findViewById(R.id.save);
                final TextView vcencel = views.findViewById(R.id.cencel);
                vname.setText(name);
                dialog.setView(views);
                dialog.show();

                vcencel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                vsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sessionManager.createLoginSession(vname.getText().toString(),top,last);
                        vnameView.setText(vname.getText().toString());
                        dialog.dismiss();
                    }
                });


            }
        });

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  ResPer();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_close, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.close) {
           onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out_from_view);
    }

    public void ResPer(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfilActivity.this);
        alertDialog.setTitle("Подтверждение");
        alertDialog.setMessage("Вы уверены, что хотите сбросить все данные? ");

        alertDialog.setNegativeButton((CharSequence) " ДА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences.Editor editor = getSharedPreferences("RESUME", MODE_PRIVATE).edit();
                editor.putString("back", "4");
                editor.apply();

                sessionManager.resetUser();
                vlast.setText("0.0");
                vtop.setText("0.0");
                vnameView.setText("Имя пользователя");
                dialog.dismiss();
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
}
