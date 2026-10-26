package com.example.vieshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class Member36 extends AppCompatActivity {

    ImageButton back;
    TextView ID, balance, topup, email, money;
    EditText phonenum, cardnum, securitycode;
    Spinner amount, month, year;
    CheckBox check;
    Button confirm;
    String IP, acc;
    String result1, result2, result3;
    int b, m = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member36);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");
        SharedPreferences user1 = getSharedPreferences("account",MODE_PRIVATE);
        acc = user1.getString("acc","");
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
        ID = (TextView) findViewById(R.id.ID);
        balance = (TextView) findViewById(R.id.balance);
        topup = (TextView) findViewById(R.id.topup);
        topup.setText("儲值金額 NT$1000");
        String[] amountlist = {"儲值 NT$1000", "儲值 NT$2000", "儲值 NT$3000", "儲值 NT$4000", "儲值 NT$5000", "儲值 NT$6000", "儲值 NT$7000", "儲值 NT$8000", "儲值 NT$9000", "儲值 NT$10000"};
        amount = (Spinner) findViewById(R.id.amount);
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, amountlist);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount.setAdapter(adapter1);
        amount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        topup.setText("儲值金額 NT$1000");
                        money.setText("NT$1000");
                        m = 1000;
                        break;
                    case 1:
                        topup.setText("儲值金額 NT$2000");
                        money.setText("NT$2000");
                        m = 2000;
                        break;
                    case 2:
                        topup.setText("儲值金額 NT$3000");
                        money.setText("NT$3000");
                        m = 3000;
                        break;
                    case 3:
                        topup.setText("儲值金額 NT$4000");
                        money.setText("NT$4000");
                        m = 4000;
                        break;
                    case 4:
                        topup.setText("儲值金額 NT$5000");
                        money.setText("NT$5000");
                        m = 5000;
                        break;
                    case 5:
                        topup.setText("儲值金額 NT$6000");
                        money.setText("NT$6000");
                        m = 6000;
                        break;
                    case 6:
                        topup.setText("儲值金額 NT$7000");
                        money.setText("NT$7000");
                        m = 7000;
                        break;
                    case 7:
                        topup.setText("儲值金額 NT$8000");
                        money.setText("NT$8000");
                        m = 8000;
                        break;
                    case 8:
                        topup.setText("儲值金額 NT$9000");
                        money.setText("NT$9000");
                        m = 9000;
                        break;
                    case 9:
                        topup.setText("儲值金額 NT$10000");
                        money.setText("NT$10000");
                        m = 10000;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        email = (TextView) findViewById(R.id.email);
        email.setText(acc);
        phonenum = (EditText) findViewById(R.id.phonenum);
        cardnum = (EditText) findViewById(R.id.cardnum);
        securitycode = (EditText) findViewById(R.id.securitycode);
        String[] monthlist = {"月份", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        month = (Spinner) findViewById(R.id.month);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, monthlist);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(adapter2);
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] yearlist = {"年份", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33"};
        year = (Spinner) findViewById(R.id.year);
        ArrayAdapter adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearlist);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter3);
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        money = (TextView) findViewById(R.id.money);
        money.setText("NT$1000");
        check = (CheckBox) findViewById(R.id.check);
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardnum.getText().toString().equals("")) {
                    Toast.makeText(Member36.this, "請輸入信用卡卡號!", Toast.LENGTH_SHORT).show();
                } else if (securitycode.getText().toString().equals("")) {
                    Toast.makeText(Member36.this, "請輸入卡片安全碼!", Toast.LENGTH_SHORT).show();
                } else if (month.getSelectedItemPosition() == 0 || year.getSelectedItemPosition() == 0) {
                    Toast.makeText(Member36.this, "請選擇卡片到期日!", Toast.LENGTH_SHORT).show();
                } else if (check.isChecked() == false) {
                    Toast.makeText(Member36.this, "請先勾選同意上述政策!", Toast.LENGTH_SHORT).show();
                }
                else{
                    top_up(m);
                    Toast.makeText(Member36.this, "儲值成功!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(Member36.this, Member4.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void top_up(int m) {
        String account = acc;
        String money = Integer.toString(m + b);
        try {
            jsonObject.put("User_Money", money);
            jsonObject.put("User_Email", account);
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
        String path = ApiConfig.url("topup.php");
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
    private Runnable mutiThread = new Runnable() {
        public void run() {
            try {
                URL url = new URL(ApiConfig.url("user.php"));
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
                String post_data = URLEncoder.encode("acc", "UTF-8") + "=" + URLEncoder.encode(acc, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                // 建立取得回應的物件
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream = connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // 讀取輸入串流的資料
                    String line = null; // 宣告讀取用的字串
                    while ((line = bufReader.readLine()) != null) {
                        JSONArray dataJson = new JSONArray(line);
                        for (int i = 0; i < dataJson.length(); i++) {
                            JSONObject info = dataJson.getJSONObject(i);
                            String ID = info.getString("User_ID");
                            String money = info.getString("User_Money");
                            String phonenum = info.getString("User_PhoneNum");
                            result1 = ID.toString();
                            result2 = money.toString();
                            result3 = phonenum.toString();
                        }
                    }
                    inputStream.close(); // 關閉輸入串流
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch (Exception e) { // 如果出事，回傳錯誤訊息
                result1 = e.toString();
                result2 = e.toString();
                result3 = e.toString();
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    ID.setText("會員編號 " + result1);
                    balance.setText("目前餘額 NT$" + result2);
                    b = Integer.parseInt(result2);
                    phonenum.setText(result3);
                }
            });
        }
    };
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
            intent.setClass(Member36.this, Member4.class);
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