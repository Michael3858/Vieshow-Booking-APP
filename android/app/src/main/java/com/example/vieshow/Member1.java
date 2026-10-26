package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Member1 extends AppCompatActivity {

    Button button1, button2;
    EditText AccountET, PasswordET;
    CheckBox check;
    String Account, Password, IP;
    String result, result1, result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member1);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        AccountET = findViewById(R.id.Account);
        PasswordET = findViewById(R.id.Password);
        check = findViewById(R.id.remember);
        output();

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Member1.this,Member3.class);
                startActivity(intent);
            }
        });
    }

    public void OnLogin(View view){
        Account = AccountET.getText().toString();
        Password = PasswordET.getText().toString();
        Thread thread = new Thread(mutiThread);
        thread.start();
    }

    private Runnable mutiThread = new Runnable(){
        public void run()
        {
            try {
                String account = AccountET.getText().toString();
                String password = PasswordET.getText().toString();
                URL url = new URL(ApiConfig.url("login.php"));
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("account", "UTF-8")+"="+URLEncoder.encode(account, "UTF-8")+"&"
                        +URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                // 建立取得回應的物件
                if(responseCode == HttpURLConnection.HTTP_OK){
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream = connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // 讀取輸入串流的資料
                    //String box = "";
                    String line = null; // 宣告讀取用的字串
                    while((line = bufReader.readLine()) != null) {
                        JSONArray dataJson = new JSONArray(line);
                        int i = dataJson.length()-1;
                        JSONObject info = dataJson.getJSONObject(i);
                        String acc = info.getString("User_Email");
                        String pw = info.getString("Password");
                        result1 = acc.toString();
                        result2 = pw.toString();
                        //box += line + "\n";
                    }
                    inputStream.close(); // 關閉輸入串流
                    //result = box;
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) { // 如果出事，回傳錯誤訊息
                result1 = e.toString();
                result2 = e.toString();
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    if (TextUtils.isEmpty(Account)){
                        Toast.makeText(Member1.this, "請輸入帳號", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(Password)) {
                        Toast.makeText(Member1.this, "請輸入密碼", Toast.LENGTH_SHORT).show();
                    }else if(Account.equals(result1) && Password.equals(result2)){
                        input(); ///登入成功的話才執行這個副程式
                        Toast toast = Toast.makeText(Member1.this, "登入成功!", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent();
                        intent.setClass(Member1.this ,Member4.class);
                        Member1.this.startActivity(intent);
                    }else {
                        Toast.makeText(Member1.this, "帳號或密碼錯誤!", Toast.LENGTH_LONG).show();
                        AccountET.setText(""); //清空
                        PasswordET.setText("");
                    }
                }
            });
        }
    };

    private void input() {

        SharedPreferences user = getSharedPreferences("remember", MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        if (check.isChecked()) { //如果記住密碼那個選項有打勾就把這次成功登入的資料儲存起來
            edit.putString("account",AccountET.getText().toString());
            edit.putString("password",PasswordET.getText().toString());
            edit.putBoolean("remember",true);  //把我自己設的remember狀態設為true

        } else {
            edit.remove("account"); //若上次沒打勾就把它清除記住的資料
            edit.remove("password");
            edit.putBoolean("remember",false);
        }
        edit.commit();
        SharedPreferences user1 = getSharedPreferences("account", MODE_PRIVATE);
        SharedPreferences.Editor edit1 = user1.edit();
        edit1.putString("acc", AccountET.getText().toString());
        edit1.commit();
    }


    private void output(){
        SharedPreferences user = getSharedPreferences("remember",MODE_PRIVATE);
        String account2 = user.getString("account","");  //若沒取得就是沒任何東西 " "
        String password2 = user.getString("password","");
        boolean remember = user.getBoolean("remember",false);
        //在input()我是寫成若打勾則讓它remember設為true，所以若沒取到則是false

        AccountET.setText(account2); //有取到先前給予的資料的話就會顯示出來
        PasswordET.setText(password2);
        check.setChecked(remember);//並且利用remember的狀態判斷上次是否打勾，有的話就顯示打勾
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