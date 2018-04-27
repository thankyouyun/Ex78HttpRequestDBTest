package com.goodyun.ex78httprequestdbtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by alfo6-19 on 2018-04-18.
 */

public class TalkAdapter extends BaseAdapter {

    LayoutInflater inflater;

    ArrayList<TalkItem> talkitems;

    public TalkAdapter(LayoutInflater inflater, ArrayList<TalkItem> talkitems) {
        this.inflater = inflater;
        this.talkitems = talkitems;
    }

    @Override
    public int getCount() {

        return talkitems.size();
    }

    @Override
    public Object getItem(int position) {
        return talkitems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null){
            view = inflater.inflate(R.layout.list_item,viewGroup,false);

        }
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvDate = view.findViewById(R.id.tv_date);
        TextView tvMsg = view.findViewById(R.id.tv_msg);
        ImageView iv = view.findViewById(R.id.iv);

        TalkItem talkItem = talkitems.get(position);
        tvName.setText(talkItem.getName());
        tvDate.setText(talkItem.getDate());
        tvMsg.setText(talkItem.getMsg());
        Glide.with(view).load(talkItem.getImgPath()).into(iv);


        return view;
    }
}
