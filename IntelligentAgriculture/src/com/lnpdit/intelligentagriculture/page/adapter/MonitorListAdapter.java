package com.lnpdit.intelligentagriculture.page.adapter;

import java.util.List;

import com.lnpdit.intelligentagriculture.R;
import com.lnpdit.intelligentagriculture.entity.DataInfoUn;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


@SuppressLint("InflateParams")
public class MonitorListAdapter extends BaseAdapter {
    private class buttonViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4; 
        ImageView imageView1;
    }


    private List<DataInfoUn> mAppList;
    private LayoutInflater mInflater;
    private Context mContext;
    private buttonViewHolder holder;

    public MonitorListAdapter(Context context, List<DataInfoUn> appList) {
        mAppList = appList;
        mContext = context;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int positon) {
        mAppList.remove(positon);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            holder = (buttonViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.item_monitor, null);
            holder = new buttonViewHolder();
            holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            holder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
            holder.textView3 = (TextView) convertView.findViewById(R.id.textView3);
            holder.textView4 = (TextView) convertView.findViewById(R.id.textView4);;
            holder.imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        }
//        DataInfoUn appInfo = mAppList.get(position);
//        if (appInfo != null) {
//            String type_str = appInfo.getTagName();
//            String time = appInfo.getCTime();
//            try{              
//                holder.type_tv.setText("事件类型："+type_str);
//                holder.time_tv.setText("时间："+time);
//                convertView.setOnClickListener(new AdapterListener(position, appInfo));
//            }catch(Exception e){
//                
//            }
//        }

        holder.imageView1.setBackgroundResource(R.drawable.monitorpic);
        holder.textView1.setText("111");
        holder.textView2.setText("111");
        holder.textView3.setText("111");
        holder.textView4.setText("111");
        
        return convertView;
    }

//    class AdapterListener implements OnClickListener{
//        private int position;
//        private DataInfoUn transfordate;
//
//        public AdapterListener(int pos, DataInfoUn td) {
//            // TODO Auto-generated constructor stub
//            position = pos;
//            transfordate = td;
//        }

//        @Override
//        public void onClick(View v) {
//             TODO Auto-generated method stub
//            String Id = transfordate.getId();
//            String TagName = transfordate.getTagName();
//            String CTime = transfordate.getCTime();
//            
//            Intent intent = new Intent();
//            intent.putExtra("Id", Id);
//            intent.putExtra("TagName", TagName);
//            intent.putExtra("CTime", CTime);
//            intent.putExtra("type", "0");
//            
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//
//            intent.setClass(mContext, AlarmUnInformationActivity.class);
//            mContext.startActivity(intent);
//            
//        }
//    }
}