package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Member3 extends AppCompatActivity {

    ImageButton back;
    Button button1, button2;
    EditText AccountET, IDET, PasswordET, PasswordET2;
    ArrayList<String> Accountlist = new ArrayList<>();
    ArrayList<String> IDlist = new ArrayList<>();
    String IP;
    String result1, result2, response;
    Boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member3);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        getallAccID();

        AccountET = findViewById(R.id.Account);
        IDET = findViewById(R.id.ID);
        PasswordET = findViewById(R.id.Password);
        PasswordET2 = findViewById(R.id.Password2);

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();
            }
        });
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Account = AccountET.getText().toString();
                String ID = IDET.getText().toString().toUpperCase();
                int same = 0;
                int i;
                for(i = 0; i < Accountlist.size(); i++){
                    if(Account.equals(Accountlist.get(i))){
                        same = 1;
                        break;
                    }
                }
                if(same == 0){
                    Toast.makeText(Member3.this, "請輸入正確的帳號!", Toast.LENGTH_SHORT).show();
                }
                else if(same == 1){
                    if(ID.equals(IDlist.get(i))){
                        Toast.makeText(Member3.this, "驗證通過!", Toast.LENGTH_SHORT).show();
                        check = true;
                    }
                    else{
                        Toast.makeText(Member3.this, "請輸入正確的身分證或居留證號碼!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password = PasswordET.getText().toString();
                String Password2 = PasswordET2.getText().toString();
                if(check == false){
                    Toast.makeText(Member3.this, "請先通過驗證!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(Member3.this, "請輸入密碼!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Password2) || !Password2.equals(Password)) {
                    Toast.makeText(Member3.this, "請重新輸入密碼!", Toast.LENGTH_SHORT).show();
                }
                else{
                    fix();
                    Toast toast = Toast.makeText(Member3.this, "更改成功!", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent();
                    finish();
                }
            }
        });
    }

    private void getallAccID() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        response = executeHttpGet();
                    }
                }

        ).start();
    }

    private String executeHttpGet() {

        HttpURLConnection con=null;
        InputStream in=null;
        String path=ApiConfig.url("getallAccID.php"); //[local API server]需改成自己的ip
        try {
            con= (HttpURLConnection) new URL(path).openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoInput(true);
            con.setRequestMethod("GET");
            if(con.getResponseCode()==200){

                in=con.getInputStream();
                return parseInfo(in);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String parseInfo(InputStream in) throws IOException, JSONException {
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        StringBuilder sb=new StringBuilder();
        String line=null;
        while ((line=br.readLine())!=null){
            sb.append(line+"\n");
            JSONArray dataJson = new JSONArray(line);
            for(int i = 0; i < dataJson.length(); i++){
                JSONObject info = dataJson.getJSONObject(i);
                String Acc = info.getString("User_Email");
                String ID = info.getString("User_IC");
                result1 = Acc.toString();
                Accountlist.add(result1);
                result2 = ID.toString();
                IDlist.add(result2);
            }
        }
        return sb.toString();
    }

    private void fix() {
        String account = AccountET.getText().toString();
        String password = PasswordET.getText().toString();
        try {
            jsonObject.put("account", account);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
        send();
    }

    private void send() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                executeHttpPost();
            }
        }).start();
    }

    JSONObject jsonObject = new JSONObject();

    private void executeHttpPost() {
        String path = ApiConfig.url("forgetpw.php");
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);  //開啟輸出流，以便向伺服器提交資料
            conn.setDoInput(true);  //開啟輸入流，以便從伺服器獲取資料
            conn.setUseCaches(false);//使用Post方式不能使用快取
            conn.setRequestMethod("POST");  //設定以Post方式提交資料
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("accept", "application/json");

            // 往伺服器裡面傳送資料
            String Json = jsonObject.toString();

            System.out.println("-----------" + Json);

            if (Json != null && !TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 設定檔案長度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("upload: ", "doJsonPost: " + conn.getResponseCode());//如輸出200，則對了
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 設置要用哪個menu檔做為選單
        getMenuInflater().inflate(R.menu.menu_without_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 取得點選項目的id
        int id = item.getItemId();

        // 依照id判斷點了哪個項目並做相應事件
        if (id == R.id.action_help) {
            // 按下「使用說明」要做的事
            Toast.makeText(this, "使用說明", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_about) {
            // 按下「關於」要做的事
            Toast.makeText(this, "關於", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}