package aptitude.sasurie.leisurebreaker;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;

import static aptitude.sasurie.leisurebreaker.LoginActivity.MyPREFERENCES;

public class MarkActivity extends AppCompatActivity {
    String testid,regno,mark;
    private TextView textView,textView9;
    SharedPreferences sharedpreferences;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        Intent intent = getIntent();
        MobileAds.initialize(this,
                "ca-app-pub-2052757055681240~3637998856");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(sharedpreferences.contains(Config.KEY_REGNO) && sharedpreferences.contains(Config.KEY_PASSWORD)){

        }else {
            if(sharedpreferences.contains(Config.KEY_REGNO) && sharedpreferences.contains(Config.KEY_PASSWORD)){

                Intent loginIntent = new Intent(MarkActivity.this,LoginActivity.class);
                startActivity(loginIntent);

                finish();   //finish current activity
            }

        }


        testid = intent.getStringExtra(Config.TEST_ID);
        regno=intent.getStringExtra(Config.KEY_REGNO);
        mark=intent.getStringExtra(Config.TEST_MARK);

        textView=findViewById(R.id.textmark);
        textView.setText(mark);
        textView9=findViewById(R.id.textView9);
        registerMark();
        textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MarkActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
    private void registerMark() {
        class AddUser extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MarkActivity.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MarkActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_REGNO,regno);
                params.put(Config.KEY_TESTID,testid);
                params.put(Config.TEST_MARK,mark);
                //params.put(Config.KEY_EMP_PHNO,phno);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_RESULT, params);
                return res;
            }
        }

        AddUser ae = new AddUser();
        ae.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
