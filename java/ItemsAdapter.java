package com.dongyang.gg;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.CustomViewHolder> {

    private static String IP_ADDRESS = "15.164.98.47";
    private ArrayList<ItemData> mList = null;
    private Activity context = null;

    String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"storage/emulated/0/DCIM/Camera";
    File directory=new File(path);
    File[] files=directory.listFiles();
    String filename;

    List<String> filesNameList = new ArrayList<>();


//디테일 눌렀을때 하나로 정해놔야 이동함
//intent 전송 어디에 넣어야 하는지 모르겠음


    public ItemsAdapter(Activity context, ArrayList<ItemData> list) {
        this.context = context;
        this.mList = list;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView pic;
        protected TextView name;
        protected TextView price;
        protected ImageView num;







        public CustomViewHolder(View view) {
            super(view);



            this.pic = (ImageView) view.findViewById(R.id.textView_list_pic);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.price = (TextView) view.findViewById(R.id.textView_list_price);
            this.num=(ImageView) view.findViewById(R.id.btn_list_detail);

//            for(int i=0; i<files.length; i++){
//
//                filesNameList.add(files[i].getName());
//            }
//            for(int i=0;i<files.length;i++){
//                Log.d("filenamelist - ", filesNameList.get(i));
//            }

            //img = (ImageView) findViewById(R.id.imageview);
//            String url = "http://"+IP_ADDRESS+"/uploads/"+filename;
//            //Uri uri=url.toUri().buildUpon().scheme("https").build();
//            Uri uri=Uri.parse("http://"+IP_ADDRESS+"/uploads/"+filename);
//
//            try {
//
//                Glide.with(context)
//                        .load(uri)
//                        //.override(300, 300)
//                        //.placeholder(R.drawable.ic_launcher_background)
//                        //.error(R.mipmap.ic_launcher)
//                        .into(this.pic);
//            } catch (Exception e) {
//                Log.d("error - ", e.toString());
//            }
            this.num.setOnClickListener(new View.OnClickListener() {
                private int num;


                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), DetailListActivity.class);
                    intent.putExtra("inum", num);

                    context.startActivity(intent);
                }
            });

        }
    }




    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_view, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);




        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {


        filename=mList.get(position).getItem_pic();
        viewholder.name.setText(mList.get(position).getItem_name());
        viewholder.price.setText(mList.get(position).getItem_price());
        //viewholder.num.setText(mList.get(position).getItem_num());

//        for(int i=0; i<files.length; i++){
//
//            filesNameList.add(files[i].getName());
//        }
//        for(int i=0;i<files.length;i++){
//            Log.d("filenamelist - ", filesNameList.get(i));
//        }

        //img = (ImageView) findViewById(R.id.imageview);
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

         String inum=mList.get(position).getItem_num();
        viewholder.num.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), DetailListActivity.class);
               intent.putExtra("i_num", inum);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }




}