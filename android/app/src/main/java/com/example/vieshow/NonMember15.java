package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import java.util.Arrays;

public class NonMember15 extends AppCompatActivity {

    ImageButton back;
    EditText IDET, AccountET, PasswordET, PasswordET2, NameET, DateET, PhoneNumET;
    Button button1, button2;
    ArrayList<String> IDlist = new ArrayList<>();
    ArrayList<String> UserIDlist = new ArrayList<>();
    ArrayList<String> UserEmaillist = new ArrayList<>();
    String result1, result2, result3, response;
    Boolean check = false;
    String IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_member15);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        getallUserID();

        IDET = findViewById(R.id.ID);
        AccountET = findViewById(R.id.Account);
        PasswordET = findViewById(R.id.Password);
        PasswordET2 = findViewById(R.id.Password2);
        NameET = findViewById(R.id.Name);
        DateET = findViewById(R.id.Date);
        PhoneNumET = findViewById(R.id.PhoneNum);

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
                check();
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = IDET.getText().toString();
                String Account = AccountET.getText().toString();
                String Password = PasswordET.getText().toString();
                String Password2 = PasswordET2.getText().toString();
                String Name = NameET.getText().toString();
                String Date = DateET.getText().toString();
                String PhoneNum = PhoneNumET.getText().toString();
                Boolean checkDate = true;
                if(Date.length() != 10){
                    checkDate = false;
                }
                else if(Date.length() == 10){
                    for(int i = 0; i < 4; i++){
                        if(Date.charAt(i) < 48 || Date.charAt(i) > 57){
                            checkDate = false;
                        }
                    }
                    if(Date.charAt(4) != 47){
                        checkDate = false;
                    }
                    for(int i = 5; i < 7; i++){
                        if(Date.charAt(i) < 48 || Date.charAt(i) > 57){
                            checkDate = false;
                        }
                    }
                    if(Date.charAt(7) != 47){
                        checkDate = false;
                    }
                    for(int i = 8; i < 10; i++){
                        if(Date.charAt(i) < 48 || Date.charAt(i) > 57){
                            checkDate = false;
                        }
                    }
                }
                int same = 0;
                for(int i = 0; i < UserEmaillist.size(); i++){
                    if(Account.equals(UserEmaillist.get(i))){
                        same = 1;
                        break;
                    }
                }
                if(TextUtils.isEmpty(ID) || check == false){
                    Toast.makeText(NonMember15.this, "請先通過資料驗證!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Account) || Patterns.EMAIL_ADDRESS.matcher(Account).matches() == false){
                    Toast.makeText(NonMember15.this, "請輸入正確的信箱!", Toast.LENGTH_SHORT).show();
                }
                else if(same == 1){
                    Toast.makeText(NonMember15.this, "此信箱已被註冊過!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Password)){
                    Toast.makeText(NonMember15.this, "請輸入密碼!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Password2) || !Password.equals(Password2)){
                    Toast.makeText(NonMember15.this, "請重新輸入密碼!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Name)){
                    Toast.makeText(NonMember15.this, "請輸入姓名!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Date) || checkDate == false){
                    Toast.makeText(NonMember15.this, "請輸入正確的生日格式!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(PhoneNum) || Patterns.PHONE.matcher(PhoneNum).matches() == false){
                    Toast.makeText(NonMember15.this, "請輸入正確的手機號碼!", Toast.LENGTH_SHORT).show();
                }
                else{
                    signup();
                    Toast toast = Toast.makeText(NonMember15.this, "註冊成功!", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent();
                    intent.setClass(NonMember15.this, Member1.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void signup() {
        String id = IDET.getText().toString();
        String account = AccountET.getText().toString();
        String password = PasswordET.getText().toString();
        String userid;
        while(true){
            int j = (int)(Math.random() * 10000000); //0~9999999
            userid = String.format("%07d", j);
            int n;
            for(n = 0; n < UserIDlist.size(); n++){
                if(UserIDlist.get(n).equals(userid)){
                    break;
                }
            }
            if(n == UserIDlist.size()){
                break;
            }
        }
        String name = NameET.getText().toString();
        String date = DateET.getText().toString();
        String phonenum = PhoneNumET.getText().toString();
        try {
            jsonObject.put("id", id);
            jsonObject.put("account", account);
            jsonObject.put("password", password);
            jsonObject.put("userid", userid);
            jsonObject.put("name", name);
            jsonObject.put("date", date);
            jsonObject.put("phonenum", phonenum);
            jsonObject.put("money", "0");
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
        String path = ApiConfig.url("signup.php");
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

    private void getallUserID() {
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
        String path=ApiConfig.url("getallUserID.php"); //[local API server]需改成自己的ip
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
                String ID = info.getString("User_IC");
                String UserID = info.getString("User_ID");
                String UserEmail = info.getString("User_Email");
                result1 = ID.toString();
                result2 = UserID.toString();
                result3 = UserEmail.toString();
                IDlist.add(result1);
                UserIDlist.add(result2);
                UserEmaillist.add(result3);
            }
        }
        return sb.toString();
    }

    private void check(){
        String id = IDET.getText().toString().toUpperCase();
        int same = 0;
        for(int i = 0; i < IDlist.size(); i++){
            if(id.equals(IDlist.get(i).toString().toUpperCase())){
                same = 1;
                break;
            }
        }
        Boolean bool = true;
        if(id.length() != 10){
            bool = false;
        }
        if(id.charAt(0) < 65 || id.charAt(0) > 90){
            bool = false;
        }
        for(int i = 1; i < id.length(); i++){
            if(id.charAt(i) < 48 || id.charAt(i) > 57){
                bool = false;
                break;
            }
        }
        if(same == 1) {
            Toast.makeText(NonMember15.this, "此身分證或居留證號碼已註冊過!", Toast.LENGTH_SHORT).show();
            check = false;
        }
        else if(bool == false){
            Toast.makeText(NonMember15.this, "身分證或居留證號碼格式錯誤!", Toast.LENGTH_SHORT).show();
            check = false;
        }
        else{
            char[] chararray = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
            int[] numarray = {10,11,12,13,14,15,16,17,34,18,19,20,21,22,35,23,24,25,26,27,28,29,32,30,31,33};
            char first = id.charAt(0);
            Character.toUpperCase(first); //強制轉大寫
            int index = 0;
            index = Arrays.binarySearch(chararray, first);
            int x = numarray[index]; //字母對應的數字
            int x1 = (int)Math.floor(x / 10); //x的十位數
            int x2 = x % 10; //x的個位數
            int sum = x1 + 9 * x2 + 8 * (int)(id.charAt(1) - 48) + 7 * (int)(id.charAt(2) - 48) + 6 * (int)(id.charAt(3) - 48) + 5 * (int)(id.charAt(4) - 48) + 4 * (int)(id.charAt(5) - 48) + 3 * (int)(id.charAt(6) - 48) + 2 * (int)(id.charAt(7) - 48) + (int)(id.charAt(8) - 48) + (int)(id.charAt(9) - 48);
            if(sum % 10 == 0){
                Toast.makeText(NonMember15.this, "通過驗證!", Toast.LENGTH_SHORT).show();
                check = true;
            }
            else{
                Toast.makeText(NonMember15.this, "身分證或居留證號碼格式錯誤!", Toast.LENGTH_SHORT).show();
                check = false;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 設置要用哪個menu檔做為選單
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 取得點選項目的id
        int id = item.getItemId();

        // 依照id判斷點了哪個項目並做相應事件
        if (id == R.id.action_home) {
            // 按下「設定」要做的事
            Intent intent = new Intent();
            intent.setClass(NonMember15.this, NonMember1.class);
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