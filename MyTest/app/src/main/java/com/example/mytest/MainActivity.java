package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    Button loginButton, joinButton;
    EditText id, pw;
    String loginStr;
    Boolean canLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.loginButton);
        joinButton = (Button) findViewById(R.id.joinButton);

        id = (EditText) findViewById(R.id.id);
        pw = (EditText) findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginStr = "[로그인을 할 수 없습니다.]";
                canLogin = true;

                if (!hasTxt(id) || !hasTxt(pw)) {
                    loginStr += "\n로그인 정보를 입력해주세요.";
                    canLogin = false;
                } else {
                    String nId = id.getText().toString() + ".txt";
                    String nPW = pw.getText().toString();
                    String rPW = "";
                    Boolean chkID;
                    try {
                        FileInputStream fis = openFileInput(nId);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                        rPW = reader.readLine();
                        chkID = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        chkID = false;
                    }
                    if (chkID) {
                        if (!nPW.equals(rPW)) {
                            loginStr += "\n비밀번호가 맞지 않습니다";
                            canLogin = false;
                        }
                    } else {
                        loginStr += "\n등록되지 않은 아이디입니다";
                        canLogin = false;
                    }
                }
                if (!canLogin) {
                    Toast.makeText(getApplicationContext(), loginStr, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentThird = new Intent(getApplicationContext(), ThirdActivity.class);
                    startActivity(intentThird);
                }
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentSecond = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intentSecond);
            }
        });
    }

    private boolean hasTxt(EditText et) {
        return (et.getText().toString().trim().length() > 0);
    }
}