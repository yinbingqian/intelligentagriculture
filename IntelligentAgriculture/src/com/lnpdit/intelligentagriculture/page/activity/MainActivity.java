package com.lnpdit.intelligentagriculture.page.activity;
import java.util.List;

import com.lnpdit.intelligentagriculture.R;
import com.lnpdit.intelligentagriculture.entity.DataInfoUn;
import com.lnpdit.intelligentagriculture.page.adapter.MonitorListAdapter;
import com.lnpdit.intelligentagriculture.pulltorefresh.library.PullToRefreshBase;
import com.lnpdit.intelligentagriculture.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.lnpdit.intelligentagriculture.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.lnpdit.intelligentagriculture.pulltorefresh.library.PullToRefreshListView;
import com.lnpdit.intelligentagriculture.webservice.SoapRes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity{
	/** Called when the activity is first created. */

	Button m_btLogin;
	Button back_bt;
    private PullToRefreshListView listview_monitorlist;
    private ListView monitorListView;
    private int pageIndex = 1;
    Context context;
    private List<DataInfoUn> monitorList;
    private MonitorListAdapter monitorlistAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_main);

		initView();
		 
		setListeners();
        monitorlistAdapter = new MonitorListAdapter(context,monitorList);
        monitorListView.setAdapter(monitorlistAdapter);
       
	
	}

	public void jumpToRealPlayActivity() {
		Intent intent = new Intent();
		intent.setClass(this, RealPlayActivity.class);
		// intent.setClass(this, ItemListActivity.class);
		intent.putExtra("ip", "223.100.225.101");
		intent.putExtra("port", "9000");
		intent.putExtra("username", "system");
		intent.putExtra("password", "123456");
		intent.putExtra("channelId", "1000001$1$0$12");
		intent.putExtra("channelName", "��������ˮ��-���");
		startActivity(intent);
	}

//    private void initData() {
//        pageIndex = 1;
//        String[] property_va = new String[] { "10", pageIndex + "" };
//        soapService.GetDateListUn(property_va, false);
//    }

    private void initView() {
        listview_monitorlist = (PullToRefreshListView) findViewById(R.id.listview_monitorlist);
        monitorListView = listview_monitorlist.getRefreshableView();
        m_btLogin = (Button) findViewById(R.id.buttonLogin);
        back_bt = (Button) findViewById(R.id.back_bt);

        m_btLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                jumpToRealPlayActivity();
            }
        });

        back_bt.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
    

    private void setListeners() {
        listview_monitorlist.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {

                jumpToRealPlayActivity();
//                Intent intent = new Intent();
//                intent.setClass(context, AlarmUnInformationActivity.class);
//                intent.putExtra("Id", alarmunList.get(position - 1).getId());
//                intent.putExtra("tagName",alarmunList.get(position - 1).getTagName());
//                intent.putExtra("CTime",alarmunList.get(position - 1).getCTime());
//                intent.putExtra("type", "1");
//                startActivity(intent);
            }
        });
        listview_monitorlist
                .setOnRefreshListener(new OnRefreshListener<ListView>() {

                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ListView> refreshView) {
//                        pageIndex = 1;
//                        String[] property_va = new String[] { "10",
//                                pageIndex + "" };
//                        soapService.GetDateListUn(property_va, false);

                    }
                });

        // end of list
        listview_monitorlist
                .setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

                    @Override
                    public void onLastItemVisible() {
//                        String[] property_va = new String[] { "10",
//                                ++pageIndex + "" };
//                        soapService.GetDateListUn(property_va, true);

                    }
                });
    }

    public void onEvent(SoapRes obj) {
//        if (obj.getCode().equals(SOAP_UTILS.METHOD.GETDATELISTUN)) {
//            if (obj.isPage()) {
//                for (DataInfoUn bean : (List<DataInfoUn>) obj.getObj()) {
//                    monitorList.add(bean);
//                }
//                monitorlistAdapter.notifyDataSetChanged();
//            } else {
//                monitorList = (List<DataInfoUn>) obj.getObj();
//                if (monitorList != null) {
//                    if (monitorList.size() != 0) {
//                        monitorlistAdapter = new MonitorListAdapter(context,monitorList);
//                        monitorListView.setAdapter(monitorlistAdapter);
//                    }
//                }
//            }
//            listview_monitorlist.onRefreshComplete();
        }
//    }

}