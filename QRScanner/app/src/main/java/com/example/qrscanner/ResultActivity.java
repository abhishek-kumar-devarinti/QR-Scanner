package com.example.qrscanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultActivity extends AppCompatActivity {
    String result;
    TextView resultView;
    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle b = getIntent().getExtras();


        assert b != null;
        result = b.getString("result");

        resultView = findViewById(R.id.result);
        resultView.setText(result);


        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(result);//replace with string to compare
        if(m.find()) {
            resultView.setTextColor(getResources().getColor(R.color.purple_700));
            resultView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!result.startsWith("http://") && !result.startsWith("https://")){
                        result = "https://" + result;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
                    startActivity(intent);
                }
            });
        }


    }





}


