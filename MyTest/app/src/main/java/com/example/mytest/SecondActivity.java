package com.example.mytest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class SecondActivity extends Activity {
    TextView joinText;
    EditText id, password, name, hp, address;
    RadioButton accept;
    Boolean isError;
    String errorStr;
    String string1 = "1. 개인정보의 수집항목\n";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        hp = (EditText) findViewById(R.id.phone_number);
        address = (EditText) findViewById(R.id.address);

        accept = (RadioButton) findViewById(R.id.accept);

        joinText = (TextView) findViewById(R.id.personal_consent);

        Button joinBtn = (Button) findViewById(R.id.join);

        joinText.setText(string1);
        joinText.setMovementMethod(new ScrollingMovementMethod());


        joinBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                errorStr = "[회원가입을 할 수 없습니다.]";
                isError = false;

                if (hasTxt(id) && hasTxt(password) && hasTxt(name) && hasTxt(hp) && hasTxt(address)) {
                    if (!chkID(id)) {
                        errorStr += "\n이미 사용중인 아이디 입니다.";
                        isError = true;
                    }
                    if (!chkPW(password)) {
                        errorStr += "\n비밀번호가 양식에 맞지 않습니다.";
                        isError = true;
                    }
                }
                else {
                    isError = true;
                    errorStr += "\n모든 항목을 채워주세요.";
                }

                if(!accept.isChecked()){
                    isError = true;
                    errorStr += "\n개인정보수집에 동의가 필요합니다";
                }

                if(isError){
                    Toast.makeText(getApplicationContext(), errorStr, Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        FileOutputStream fos = null;
                        String fn = chnStr(id)+".txt";
                        fos = openFileOutput(fn, Context.MODE_PRIVATE);
                        PrintWriter writer = new PrintWriter(fos);
                        writer.println(chnStr(password));
                        writer.close();
                        Toast.makeText(getApplicationContext(), "회원가입이 완료 되었습니다.", Toast.LENGTH_SHORT).show();

                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }catch(IOException e){
                        e.printStackTrace();;
                    }

                    finish();

                }

            }
        });
    }

    private boolean chkID(EditText et){
        String nId = chnStr(et) + ".txt";
        try {
            openFileInput(nId);
        } catch(Exception e){
            e.printStackTrace();
            return true;
        }
        return false;
    }

    private String chnStr(EditText et){ return (et.getText().toString());}

    private boolean hasTxt(EditText et){
        return (et.getText().toString().trim().length() > 0);
    }

    private boolean chkPW(EditText et){
        String str = et.getText().toString();
        char[] chr = str.toCharArray();
        int len = str.trim().length();
        int num = 0;
        int eng = 0;

        if(len >= 6){
            for(int i=0; i<len; i++){
                if (chr[i] >= 48 && chr[i] <= 57) num++;
                else if((chr[i]>=97 && chr[i]<=122)||(chr[i]>=65&&chr[i]<=90)) eng++;
            }
            if(num>0 && eng>0) return true;
        }

        return false;
    }
}