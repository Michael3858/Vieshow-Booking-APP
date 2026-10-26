package com.example.vieshow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NonMember8 extends AppCompatActivity {

    ListView listView;
    ArrayList<String> IDlist = new ArrayList<>();
    ArrayList<String> imagelist = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> engnamelist = new ArrayList<>();
    ArrayList<String> addresslist = new ArrayList<>();
    ArrayList<String> phonenumlist = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> Title = new ArrayList<>();
    ArrayList<String> sec = new ArrayList<>();
    ArrayList<String> third = new ArrayList<>();
    ArrayList<String> fourth = new ArrayList<>();
    ArrayList<String> theaterID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_member8);

        SharedPreferences List1 = getSharedPreferences("ListString1", MODE_PRIVATE);
        int num1 = List1.getInt("num", 0);
        for (int i = 0; i < num1; i++) {
            String item1 = List1.getString("item_" + i, null);
            IDlist.add(item1);
        }
        SharedPreferences List2 = getSharedPreferences("ListString2", MODE_PRIVATE);
        int num2 = List2.getInt("num", 0);
        for (int i = 0; i < num2; i++) {
            String item2 = List2.getString("item_" + i, null);
            imagelist.add(item2);
        }
        SharedPreferences List3 = getSharedPreferences("ListString3", MODE_PRIVATE);
        int num3 = List3.getInt("num", 0);
        for (int i = 0; i < num3; i++) {
            String item3 = List3.getString("item_" + i, null);
            namelist.add(item3);
        }
        SharedPreferences List4 = getSharedPreferences("ListString4", MODE_PRIVATE);
        int num4 = List4.getInt("num", 0);
        for (int i = 0; i < num4; i++) {
            String item4 = List4.getString("item_" + i, null);
            engnamelist.add(item4);
        }
        SharedPreferences List5 = getSharedPreferences("ListString5", MODE_PRIVATE);
        int num5 = List5.getInt("num", 0);
        for (int i = 0; i < num5; i++) {
            String item5 = List5.getString("item_" + i, null);
            addresslist.add(item5);
        }
        SharedPreferences List6 = getSharedPreferences("ListString6", MODE_PRIVATE);
        int num6 = List6.getInt("num", 0);
        for (int i = 0; i < num6; i++) {
            String item6 = List6.getString("item_" + i, null);
            phonenumlist.add(item6);
        }
        for (int i = 0; i < imagelist.size(); i++) {
            if(!namelist.get(i).equals("MUVIE CINEMAS")){
                image.add(imagelist.get(i));
                Title.add(namelist.get(i));
                sec.add(engnamelist.get(i));
                third.add(addresslist.get(i));
                fourth.add(phonenumlist.get(i));
                theaterID.add(IDlist.get(i));
            }
        }

        /*image.add("https://www.vscinemas.com.tw/vsweb/upload/about/about_20160912003.jpg");
        Title.add("台北信義威秀影城");
        sec.add("Vie Show Cinemas Taipei Hsin Yi");
        third.add("影城地址：台北市信義區松壽路20號");
        fourth.add("服務專線：(02) 8780-5566");
        image.add("https://www.vscinemas.com.tw/vsweb/upload/about/about_20160912006.jpg");
        Title.add("台北京站威秀影城");
        sec.add("Vie Show Cinemas Taipei Qsquare");
        third.add("影城地址：台北市大同區市民大道一段209號5樓");
        fourth.add("服務專線：(02) 2552-5511");
        image.add("https://www.vscinemas.com.tw/vsweb/upload/about/about_20160912012.jpg");
        Title.add("板橋大遠百威秀影城");
        sec.add("Vie Show Cinemas Banciao Mega City");
        third.add("影城地址：新北市板橋區新站路28號10樓");
        fourth.add("服務專線：(02) 7738-6608");
        image.add("https://www.vscinemas.com.tw/vsweb/upload/about/about_20210512003.jpg");
        Title.add("中和環球威秀影城");
        sec.add("Vie Show Cinemas Global Mall");
        third.add("影城地址：新北市中和區中山路三段122號4樓 (Global Mall新北中和)");
        fourth.add("服務專線：(02)8226-6063");*/

        listView = findViewById(R.id.listview);
        MyAdapter myAdapter = new MyAdapter(this,image,Title,sec,third,fourth);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ID = theaterID.get(position);
                Intent intent = new Intent();
                intent.setClass(NonMember8.this,NonMember9.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", ID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> rimage;
        ArrayList<String> rTitle;
        ArrayList<String> rsec;
        ArrayList<String> rthird;
        ArrayList<String> rfourth;

        MyAdapter (Context context, ArrayList<String> image, ArrayList<String> title, ArrayList<String> sec, ArrayList<String> third, ArrayList<String> fourth){
            super(context,R.layout.listview_style2,R.id.title1,title);
            this.context = context;
            this.rimage = image;
            this.rTitle = title;
            this.rsec = sec;
            this.rthird = third;
            this.rfourth = fourth;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.listview_style2,parent,false);
            ImageView imageView = row.findViewById(R.id.title1);
            TextView textView = row.findViewById(R.id.title2);
            TextView textView1 = row.findViewById(R.id.title3);
            TextView textView2 = row.findViewById(R.id.title4);
            TextView textView3 = row.findViewById(R.id.title5);
            Picasso.with(NonMember8.this).load(rimage.get(position)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).fit().into(imageView);
            textView.setText(rTitle.get(position));
            textView1.setText(rsec.get(position));
            textView2.setText(rthird.get(position));
            textView3.setText(rfourth.get(position));
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
            intent.setClass(NonMember8.this, NonMember1.class);
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