package com.dongyang.gg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.dongyang.gg.MainActivity.nowid;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.CustomViewHolder> {

    private ArrayList<DetailData> mList = null;
    private Activity context = null;

    public String IP_ADDRESS="15.164.98.47";
    private String TAG="uprice";
    //int result2=0;
    String updateprice=null;
    String inum="3";//수정해야함

//    String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"storage/emulated/0/DCIM/Camera";
//    File directory=new File(path);
//    File[] files=directory.listFiles();
    String filename;

    List<String> filesNameList = new ArrayList<>();



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_list_view, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

//        Intent intent= getIntent();
//        String inum=intent.getStringExtra("inum");
        //어디에 넣을지 위치를 모르겠음 getIntent가 안먹음



        return viewHolder;
    }



    public DetailAdapter(Activity context, ArrayList<DetailData> list) {
        this.context = context;
        this.mList = list;
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView pic;
        protected TextView name;
        protected TextView price;
        protected TextView intro;
        protected ImageView update;
        protected EditText udp;
        protected ImageView addwish;



        public CustomViewHolder(View view) {
            super(view);
            this.pic = (ImageView) view.findViewById(R.id.textView_list_pic);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.price = (TextView) view.findViewById(R.id.textView_list_price);
            this.intro = (TextView) view.findViewById(R.id.textView_list_intro);
            this.udp=(EditText)view.findViewById(R.id.upprice);
            this.update=(ImageView) view.findViewById(R.id.btn_update);
            this.addwish=(ImageView) view.findViewById(R.id.btn_wish);



            this.update.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {



                    updateprice=udp.getText().toString();
                    if(udp.getText().toString().equals("")){
                        Toast.makeText(context, "입찰가를 입력하세요", Toast.LENGTH_LONG).show();
                        //Log.d("updateprice - ", updateprice);
                    }else {
                        //Log.e("updateprice - ", updateprice);

                        InsertData task = new InsertData();
                        task.execute("http://" + IP_ADDRESS + "/update_i.php", inum, updateprice);

                        udp.setText("");
                    }

                }
            });

            this.addwish.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                        //Log.e("updateprice - ", updateprice);

                        AddWish task = new AddWish();
                        task.execute("http://" + IP_ADDRESS + "/addwish.php", "3", "lee");

                        udp.setText("");
                    }


            });

        }
    }





    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        filename=mList.get(position).getItem_pic();
        //viewholder.pic.setText(mList.get(position).getItem_pic());
        viewholder.name.setText(mList.get(position).getItem_name());
        viewholder.price.setText(mList.get(position).getItem_price());
        viewholder.intro.setText(mList.get(position).getItem_intro());
        //viewholder.num.setText(mList.get(position).getItem_num());

        String url = "http://"+IP_ADDRESS+"/"+filename;
        //Uri uri=url.toUri().buildUpon().scheme("https").build();
        Uri uri=Uri.parse("http://"+IP_ADDRESS+"/"+filename);

        try {

            Glide.with(context)
                    .load(uri)
                    .override(300, 300)
                    //.placeholder(R.drawable.ic_launcher_background)
                    //.error(R.mipmap.ic_launcher)
                    .into(viewholder.pic);
        } catch (Exception e) {
            Log.d("error - ", e.toString());
        }

        updateprice=viewholder.udp.getText().toString();
//        viewholder.update.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//               InsertData task= new InsertData();
//                task.execute("http://"+IP_ADDRESS+"/update_i.php", "0", updateprice);
//
//                //Intent intent = new Intent(context.getApplicationContext(), DetailListActivity.class);//수정
//               //intent.putExtra("i_num", inum);//update_i.php 실행
//                //context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        private int result2;
        private int updateprice = 1000;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(context,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            result2 = Integer.parseInt(result);
            Log.d("UPDATE", "" + updateprice);
            Log.d("result2 - ", result2+"/"+updateprice);
            //Toast.makeText(context, result2, Toast.LENGTH_LONG).show();

            if(result2==1)
            {
                Toast.makeText(context, "실패", Toast.LENGTH_LONG).show();

            }
            else if(result2==2)
            {
                Toast.makeText(context, "성공", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context, "실패2 ", Toast.LENGTH_LONG).show();
            }
            Log.d("TESTTAG", "POST response  - " + result);
        }
        @Override
        protected String doInBackground(String... params) {
            String num = (String)params[1];
            String upprice = (String)params[2];

            String serverURL = (String)params[0];

            //serverURL = serverURL+"?" + "num=" + num + "&price=" + upprice;
            String postParam = "num=" + num + "&price=" + upprice;
            System.out.println("SERVERURL : " + serverURL);
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                OutputStream os = httpURLConnection.getOutputStream();
                os.write(postParam.getBytes("UTF-8"));
                os.flush();
                os.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("TESTTAG", "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString();

            } catch (Exception e) {
                Log.d("TAG", "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }

    class AddWish extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        private int result2;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progressDialog=ProgressDialog.show(context,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //result2 = Integer.parseInt(result);
                        //Toast.makeText(context, result2, Toast.LENGTH_LONG).show();

//            if (result2 == 1) {
//                Toast.makeText(context, "위시 성공", Toast.LENGTH_LONG).show();
//
//            } else if (result2 == 2) {
//                Toast.makeText(context, "위시 실패", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(context, "실패2 ", Toast.LENGTH_LONG).show();
//            }
            Log.d("TESTTAG", "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params){
            String num = (String)params[1];
            String id=(String)params[2];


            String serverURL=(String)params[0];
            String postparameters="&num="+num+"&id="+id;

            try{
                URL url=new URL(serverURL);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream=httpURLConnection.getOutputStream();
                outputStream.write(postparameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode=httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code-"+responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode==HttpURLConnection.HTTP_OK){
                    inputStream=httpURLConnection.getInputStream();
                }
                else{
                    inputStream=httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader=new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                StringBuilder sb=new StringBuilder();
                String line=null;

                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();
            }catch(Exception e ){
                Log.d(TAG, "InsertData: Error", e);

                return new String("Error:"+e.getMessage());
            }

        }
    }


}


