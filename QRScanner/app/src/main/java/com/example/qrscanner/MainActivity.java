package com.example.qrscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int INTERNET_REQUEST_CODE = 201;
    CodeScanner codeScanner;
    CodeScannerView codeScannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codeScannerView = findViewById(R.id.scanner);
        codeScanner = new CodeScanner(this,codeScannerView);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setScanMode(ScanMode.SINGLE);
        setPermissions();

        codeScanner.setDecodeCallback(result -> runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                intent.putExtra("result",result.getText().toString());
                startActivity(intent);
            }
        }));
        codeScannerView.setOnClickListener(v -> codeScanner.startPreview());



    }


    private void setPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){

        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},INTERNET_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==CAMERA_REQUEST_CODE){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
}