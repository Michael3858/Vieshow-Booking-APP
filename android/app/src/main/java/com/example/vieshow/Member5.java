package com.example.vieshow;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Member5 extends AppCompatActivity {

    ImageButton back;
    TextView account, number, money;
    EditText nameET, dateET, phonenumberET;
    RadioGroup gender;
    RadioButton men, women, notpublic;
    Button button1, button2, button3;
    Dialog dialog;
    View viewdialog;
    EditText oldpassword, newpassword;
    Button cancel, confirm;
    String Account, result1, result2, result3, result4, result5, result6, result7, Password;
    String IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member5);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        account = findViewById(R.id.account);
        number = findViewById(R.id.number);
        nameET = findViewById(R.id.name);
        gender = findViewById(R.id.radioGroup);
        men = findViewById(R.id.men);
        women = findViewById(R.id.women);
        notpublic = findViewById(R.id.notpublic);
        dateET = findViewById(R.id.date);
        phonenumberET = findViewById(R.id.phonenumber);
        money = findViewById(R.id.money);

        SharedPreferences user1 = getSharedPreferences("account", MODE_PRIVATE);
        Account = user1.getString("acc", "");
        account.setText(Account);

        Thread thread = new Thread(mutiThread);
        thread.start();

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
                getDialog();
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify();
                Toast toast = Toast.makeText(Member5.this, "修改成功", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Member5.this,Member36.class);
                startActivity(intent);
            }
        });
    }

    private Runnable mutiThread = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL(ApiConfig.url("info.php"));
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
                String post_data = URLEncoder.encode("account", "UTF-8")+"="+URLEncoder.encode(Account, "UTF-8");
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
                    String line = null; // 宣告讀取用的字串
                    while((line = bufReader.readLine()) != null) {
                        JSONArray dataJson = new JSONArray(line);
                        int i = dataJson.length()-1;
                        JSONObject info = dataJson.getJSONObject(i);
                        String number = info.getString("User_ID");
                        String name = info.getString("User_Name");
                        String gender = info.getString("User_Gender");
                        String date = info.getString("User_Birthday");
                        String phoneNum = info.getString("User_PhoneNum");
                        String password = info.getString("Password");
                        String money = info.getString("User_Money");
                        result1 = number.toString();
                        result2 = name.toString();
                        result3 = gender.toString();
                        result4 = date.toString();
                        result5 = phoneNum.toString();
                        result6 = password.toString();
                        result7 = money.toString();
                    }
                    inputStream.close(); // 關閉輸入串流
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) { // 如果出事，回傳錯誤訊息
                result1 = e.toString();
                result2 = e.toString();
                result3 = e.toString();
                result4 = e.toString();
                result5 = e.toString();
                result6 = e.toString();
                result7 = e.toString();
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    number.setText(result1);
                    nameET.setText(result2);
                    if(result3.equals("男")){
                        men.setChecked(true);
                    }
                    else if(result3.equals("女")){
                        women.setChecked(true);
                    }
                    else if(result3.equals("不公開")){
                        notpublic.setChecked(true);
                    }
                    else{
                        men.setChecked(false);
                        women.setChecked(false);
                        notpublic.setChecked(false);
                    }
                    dateET.setText(result4);
                    phonenumberET.setText(result5);
                    Password = result6;
                    money.setText("$" + result7);
                }
            });
        }
    };

    private void modify() {
        String acc = account.getText().toString();
        String name = nameET.getText().toString();
        String gen = "";
        if (gender.getCheckedRadioButtonId() == R.id.men) {
            gen = "男";
        }
        else if (gender.getCheckedRadioButtonId() == R.id.women) {
            gen = "女";
        }
        else if (gender.getCheckedRadioButtonId() == R.id.notpublic) {
            gen = "不公開";
        }
        String date = dateET.getText().toString();
        String phoneNum = phonenumberET.getText().toString();
        try {
            jsonObject.put("account", acc);
            jsonObject.put("name", name);
            jsonObject.put("gender", gen);
            jsonObject.put("date", date);
            jsonObject.put("phoneNum", phoneNum);
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
        String path = ApiConfig.url("modify.php");
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

    private void getDialog(){
        dialog = new Dialog(Member5.this);
        viewdialog = getLayoutInflater().inflate(R.layout.dialog, null);
        dialog.setContentView(viewdialog);
        oldpassword = viewdialog.findViewById(R.id.oldpassword);
        newpassword = viewdialog.findViewById(R.id.newpassword);
        cancel = viewdialog.findViewById(R.id.cancel);
        confirm = viewdialog.findViewById(R.id.confirm);
        dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(oldpassword.getText().toString())){
                    Toast.makeText(Member5.this, "請輸入舊密碼!", Toast.LENGTH_SHORT).show();
                }
                else if(!oldpassword.getText().toString().equals(Password)){
                    Toast.makeText(Member5.this, "舊密碼錯誤!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(newpassword.getText().toString())){
                    Toast.makeText(Member5.this, "請輸入新密碼!", Toast.LENGTH_SHORT).show();
                }
                else{
                    changepw();
                    Toast.makeText(Member5.this, "修改成功!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    private void changepw() {
        String acc = account.getText().toString();
        String pw = newpassword.getText().toString();
        try {
            jsonObject2.put("account", acc);
            jsonObject2.put("password", pw);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
        send2();
    }

    private void send2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                executeHttpPost2();
            }
        }).start();
    }

    JSONObject jsonObject2 = new JSONObject();

    private void executeHttpPost2() {
        String path = ApiConfig.url("changepw.php");
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
            String Json = jsonObject2.toString();

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
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 取得點選項目的id
        int id = item.getItemId();

        // 依照id判斷點了哪個項目並做相應事件
        if (id == R.id.action_home) {
            // 按下「設定」要做的事
            Intent intent = new Intent();
            intent.setClass(Member5.this, Member4.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_help) {
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