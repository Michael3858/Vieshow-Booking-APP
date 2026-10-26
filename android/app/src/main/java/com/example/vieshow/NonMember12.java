package com.example.vieshow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NonMember12 extends AppCompatActivity {

    ListView listView;
    ArrayList<String> movieIDlist = new ArrayList<>();
    ArrayList<String> movienamelist = new ArrayList<>();
    ArrayList<String> screeningtimelist = new ArrayList<>();
    ArrayList<String> IDlist = new ArrayList<>();
    ArrayList<String> timelist = new ArrayList<>();
    ArrayList<String> timelist1 = new ArrayList<>();
    ArrayList<String> timelist2 = new ArrayList<>();
    ArrayList<String> screeningIDlist = new ArrayList<>();
    ArrayList<String> auditoriumIDlist = new ArrayList<>();
    ArrayList<String> Title = new ArrayList<>();
    ArrayList<String> sec = new ArrayList<>();
    ArrayList<String> third = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date curDate;
    String str, str1, str2, ID, IP;
    String result1, result2, result3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_member12);

        SharedPreferences user = getSharedPreferences("IPaddress", MODE_PRIVATE);
        IP = user.getString("IP", "");

        movieIDlist.clear();
        movienamelist.clear();
        screeningtimelist.clear();
        IDlist.clear();
        timelist.clear();
        timelist1.clear();
        timelist2.clear();
        screeningIDlist.clear();
        auditoriumIDlist.clear();

        SharedPreferences List1 = getSharedPreferences("movieIDlist", MODE_PRIVATE);
        int num1 = List1.getInt("num", 0);
        for (int i = 0; i < num1; i++) {
            String item1 = List1.getString("item_" + i, null);
            movieIDlist.add(item1);
        }
        SharedPreferences List2 = getSharedPreferences("movienamelist", MODE_PRIVATE);
        int num2 = List2.getInt("num", 0);
        for (int i = 0; i < num2; i++) {
            String item2 = List2.getString("item_" + i, null);
            movienamelist.add(item2);
        }
        SharedPreferences List3 = getSharedPreferences("screeningtimelist", MODE_PRIVATE);
        int num3 = List3.getInt("num", 0);
        for (int i = 0; i < num3; i++) {
            String item3 = List3.getString("item_" + i, null);
            screeningtimelist.add(item3);
        }
        curDate = new Date(System.currentTimeMillis());
        str = formatter.format(curDate);
        str1 = formatter2.format(curDate);
        Calendar c = Calendar.getInstance();
        c.setTime(curDate);
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = c.getTime();
        str2 = formatter.format(tomorrow);
        String secline = str + "\n";
        String thirdline = str2 + "\n";
        for(int i = movieIDlist.size() - 1; i >= 0; i--){
            try {
                Date date1 = formatter2.parse(screeningtimelist.get(i));
                Date date2 = formatter2.parse(str1);
                if(date1.getTime() <= date2.getTime()){
                    movieIDlist.remove(i);
                    movienamelist.remove(i);
                    screeningtimelist.remove(i);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < movieIDlist.size(); i++){
            if(i == 0){
                Title.add(movienamelist.get(i));
                IDlist.add(movieIDlist.get(i));
            }
            if(i != 0){
                if(!movieIDlist.get(i).equals(movieIDlist.get(i - 1))){
                    sec.add(secline);
                    third.add(thirdline);
                    secline = str + "\n";
                    thirdline = str2 + "\n";
                    Title.add(movienamelist.get(i));
                    IDlist.add(movieIDlist.get(i));
                }
            }
            String screening_time = screeningtimelist.get(i);
            String d = "";
            String t = "";
            int j;
            for(j = 0; screening_time.charAt(j) != ' '; j++){
                d += screening_time.charAt(j);
            }
            t = screening_time.substring(j + 1);
            if(d.equals(str)){
                secline = secline + t + " ";
            }
            else if(d.equals(str2)){
                thirdline = thirdline + t + " ";
            }
            if(i == movieIDlist.size() - 1){
                sec.add(secline);
                third.add(thirdline);
            }
        }

        /*Title.add("(數位 國)角落小夥伴電影版：藍色月夜的魔法之子 (普遍級)");
        sec.add("11月22日 星期二\n10:10");
        third.add("11月23日 星期三\n10:10");
        Title.add("(數位)黑豹2：瓦干達萬歲 (保護級)");
        sec.add("11月22日 星期二\n10:45 11:30 13:45 14:30 15:15 16:45 17:30 19:45 20:30 21:15 22:45");
        third.add("11月23日 星期三\n10:45 11:30 13:45 14:30 15:15 16:45 17:30 19:45 20:30 21:15 22:45");
        Title.add("(數位)即使這份戀情今晚會從世上消失 (普遍級)");
        sec.add("11月22日 星期二\n10:05 17:00");
        third.add("11月23日 星期三\n10:05 18:20");
        Title.add("(數位)黑亞當 (保護級)");
        sec.add("11月22日 星期二\n12:20 18:50 22:40");
        third.add("11月23日 星期三\n12:20 18:50 22:40");*/

        listView = findViewById(R.id.listview);
        MyAdapter myAdapter = new MyAdapter(this,Title,sec,third);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ID = IDlist.get(position);
                Thread thread = new Thread(mutiThread);
                thread.start();
            }
        });
    }
    private Runnable mutiThread = new Runnable(){
        public void run()
        {
            try {
                timelist.clear();
                timelist1.clear();
                timelist2.clear();
                screeningIDlist.clear();
                auditoriumIDlist.clear();
                //ID = movieIDlist.get(listView.getSelectedItemPosition());
                URL url = new URL(ApiConfig.url("movietimelist.php"));
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
                String post_data = URLEncoder.encode("ID", "UTF-8")+"="+URLEncoder.encode(ID, "UTF-8")+"&"
                        +URLEncoder.encode("date1", "UTF-8")+"="+URLEncoder.encode(str, "UTF-8")+"&"
                        +URLEncoder.encode("date2", "UTF-8")+"="+URLEncoder.encode(str2, "UTF-8");
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
                        for(int i = 0; i < dataJson.length(); i++){
                            JSONObject info = dataJson.getJSONObject(i);
                            String time = info.getString("Screenings_Time");
                            String screeningID = info.getString("Screenings_ID");
                            String auditoriumID = info.getString("Auditorium_ID");
                            result1 = time.toString();
                            result2 = screeningID.toString();
                            result3 = auditoriumID.toString();
                            timelist.add(result1);
                            screeningIDlist.add(result2);
                            auditoriumIDlist.add(result3);
                        }
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
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    for(int i = timelist.size() - 1; i >= 0; i--){
                        try {
                            Date date1 = formatter2.parse(timelist.get(i));
                            Date date2 = formatter2.parse(str1);
                            if(date1.getTime() <= date2.getTime()){
                                timelist.remove(i);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    for(int i = 0; i < timelist.size(); i++){
                        String screening_time = timelist.get(i);
                        String d = "";
                        String t = "";
                        int j;
                        for(j = 0; screening_time.charAt(j) != ' '; j++){
                            d += screening_time.charAt(j);
                        }
                        t = screening_time.substring(j + 1);
                        if(d.equals(str)){
                            timelist1.add(t);
                        }
                        else if(d.equals(str2)){
                            timelist2.add(t);
                        }
                    }
                    SharedPreferences.Editor edit1 = getSharedPreferences("datelist", MODE_PRIVATE).edit();
                    edit1.putInt("num", 2);
                    edit1.putString("item_0", str);
                    edit1.putString("item_1", str2);
                    edit1.commit();
                    SharedPreferences.Editor edit2 = getSharedPreferences("timelist1", MODE_PRIVATE).edit();
                    edit2.putInt("num", timelist1.size());
                    for(int i = 0; i < timelist1.size(); i++){
                        edit2.putString("item_"+i, timelist1.get(i));
                    }
                    edit2.commit();
                    SharedPreferences.Editor edit3 = getSharedPreferences("timelist2", MODE_PRIVATE).edit();
                    edit3.putInt("num", timelist2.size());
                    for(int i = 0; i < timelist2.size(); i++){
                        edit3.putString("item_"+i, timelist2.get(i));
                    }
                    edit3.commit();
                    SharedPreferences.Editor edit4 = getSharedPreferences("screeningIDlist", MODE_PRIVATE).edit();
                    edit4.putInt("num", screeningIDlist.size());
                    for(int i = 0; i < screeningIDlist.size(); i++){
                        edit4.putString("item_"+i, screeningIDlist.get(i));
                    }
                    edit4.commit();
                    SharedPreferences.Editor edit5 = getSharedPreferences("auditoriumIDlist", MODE_PRIVATE).edit();
                    edit5.putInt("num", auditoriumIDlist.size());
                    for(int i = 0; i < auditoriumIDlist.size(); i++){
                        edit5.putString("item_"+i, auditoriumIDlist.get(i));
                    }
                    edit5.commit();
                    Intent intent = new Intent();
                    intent.setClass(NonMember12.this,NonMember13.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    };
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> rTitle;
        ArrayList<String> rsec;
        ArrayList<String> rthird;

        MyAdapter (Context context, ArrayList<String> title, ArrayList<String> sec, ArrayList<String> third){
            super(context,R.layout.listview_style3,R.id.title1,title);
            this.context = context;
            this.rTitle = title;
            this.rsec = sec;
            this.rthird = third;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.listview_style3,parent,false);
            TextView textView = row.findViewById(R.id.title1);
            TextView textView1 = row.findViewById(R.id.title2);
            TextView textView2 = row.findViewById(R.id.title3);
            textView.setText(rTitle.get(position));
            textView1.setText(rsec.get(position));
            textView2.setText(rthird.get(position));
            return row;
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
            intent.setClass(NonMember12.this, NonMember1.class);
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