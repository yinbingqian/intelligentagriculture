package com.lnpdit.intelligentagriculture.page.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.company.PlaySDK.IPlaySDK;
import com.dh.DpsdkCore.Enc_Channel_Info_Ex_t;
import com.dh.DpsdkCore.Get_RealStream_Info_t;
import com.dh.DpsdkCore.IDpsdkCore;
import com.dh.DpsdkCore.Login_Info_t;
import com.dh.DpsdkCore.Ptz_Direct_Info_t;
import com.dh.DpsdkCore.Ptz_Operation_Info_t;
import com.dh.DpsdkCore.Return_Value_Info_t;
import com.dh.DpsdkCore.fDPSDKStatusCallback;
import com.dh.DpsdkCore.fMediaDataCallback;
import com.lnpdit.intelligentagriculture.R;

public class RealPlayActivity extends Activity {

	// ��¼����

	static IDpsdkCore dpsdkcore = new IDpsdkCore();
	Resources res;

	// ����Ƿ��һ�ε���
	private String isfirstLogin;
	static int m_nLastError = 0;
	static Return_Value_Info_t m_ReValue = new Return_Value_Info_t();

	private Button btLeft;
	private Button btRight;
	private Button btTop;
	private Button btBottom;
	private Button btAddZoom;
	private Button btReduceZoom;
	private Button btAddFocus;
	private Button btReduceFocus;
	private Button btAddAperture;
	private Button btReduceAperture;

	private Button btnCaptureImg;
	public final static String IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/snapshot/";
	public final static String IMGSTR = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
	private static final int PicFormat_JPEG = 1;

	private TextView etCam;
	private TextView tvRet;

	String ip;
	String port;
	String username;
	String password;

	private byte[] m_szCameraId = null;
	private static int m_pDLLHandle = 0;
	SurfaceView m_svPlayer = null;
	private int m_nPort = 0;
	private int m_nSeq = 0;
	private int mTimeOut = 30 * 1000;
    // ��ʱʱ��
    private static final int DPSDK_CORE_DEFAULT_TIMEOUT = 600000;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_real_play);

		// ���ҿؼ�
		findViews();

		ip = getIntent().getStringExtra("ip");
		port = getIntent().getStringExtra("port");
		username = getIntent().getStringExtra("username");
		password = getIntent().getStringExtra("password");

		m_szCameraId = getIntent().getStringExtra("channelId").getBytes();
		etCam.setText(getIntent().getStringExtra("channelName"));
		// int nRet;
		m_nPort = IPlaySDK.PLAYGetFreePort();

		// ��¼���
		Log.d("onCreate:", m_nLastError + "");
		int nType = 1;
		m_nLastError = IDpsdkCore.DPSDK_Create(nType, m_ReValue);

		IDpsdkCore.DPSDK_SetDPSDKStatusCallback(m_ReValue.nReturnValue, new fDPSDKStatusCallback() {

			@Override
			public void invoke(int nPDLLHandle, int nStatus) {
				Log.v("fDPSDKStatusCallback", "nStatus = " + nStatus);
			}
		});

		Log.d("DpsdkCreate:", m_nLastError + "");

		new LoginTask().execute();

	}

	// ��¼���

	private void saveLoginInfo() {
		SharedPreferences sp = getSharedPreferences("LOGININFO", 0);
		Editor ed = sp.edit();
		StringBuilder sb = new StringBuilder();
		sb.append(ip).append(",").append(port).append(",").append(password).append(",").append(username);
		ed.putString("INFO", sb.toString());
		ed.putString("ISFIRSTLOGIN", "false");
		ed.commit();
		Log.i("TestDpsdkCoreActivity", "saveLoginInfo" + sb.toString());
	}

	static public int getDpsdkHandle() {
		if (m_pDLLHandle == 1)
			return m_ReValue.nReturnValue;
		else
			return 0;
	}

	public void Logout() {
		if (m_pDLLHandle == 0) {
			return;
		}
		int nRet = IDpsdkCore.DPSDK_Logout(m_ReValue.nReturnValue, 30000);

		if (0 == nRet) {
			m_pDLLHandle = 0;
		}
	}

	class LoginTask extends AsyncTask<Void, Integer, Integer> {

		@Override
		protected Integer doInBackground(Void... arg0) { // �ڴ˴�����UI�ᵼ���쳣
			if (m_pDLLHandle != 0) {
				IDpsdkCore.DPSDK_Logout(m_ReValue.nReturnValue, 30000);
				m_pDLLHandle = 0;
			}
			Login_Info_t loginInfo = new Login_Info_t();
			Integer error = Integer.valueOf(0);
			loginInfo.szIp = ip.getBytes();
			String strPort = port.trim();
			loginInfo.nPort = Integer.parseInt(strPort);
			loginInfo.szUsername = username.getBytes();
			loginInfo.szPassword = password.getBytes();
			loginInfo.nProtocol = 2;
			saveLoginInfo();
			int nRet = IDpsdkCore.DPSDK_Login(m_ReValue.nReturnValue, loginInfo, 30000);
			return nRet;
		}

		@Override
		protected void onPostExecute(Integer result) {

			super.onPostExecute(result);
			if (result == 0) {
				Log.d("DpsdkLogin success:", result + "");
				IDpsdkCore.DPSDK_SetCompressType(m_ReValue.nReturnValue, 0);
				m_pDLLHandle = 1;
				

		        Return_Value_Info_t rvInfo2 = new Return_Value_Info_t();
		        int ret = IDpsdkCore.DPSDK_LoadDGroupInfo(m_pDLLHandle, rvInfo2, DPSDK_CORE_DEFAULT_TIMEOUT);

				openVideo();
			} else {
				Log.d("DpsdkLogin failed:", result + "");
				Toast.makeText(getApplicationContext(), "login failed" + result, Toast.LENGTH_SHORT).show();
				m_pDLLHandle = 0;
			}
		}

	}

	// ����Ƶ
	private void openVideo() {

		// ���ü�����
		setListener();

		SurfaceHolder holder = m_svPlayer.getHolder();
		holder.addCallback(new Callback() {
			public void surfaceCreated(SurfaceHolder holder) {
				Log.d("xss", "surfaceCreated");
				IPlaySDK.InitSurface(m_nPort, m_svPlayer);
			}

			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				Log.d("xss", "surfaceChanged");
			}

			public void surfaceDestroyed(SurfaceHolder holder) {
				Log.d("xss", "surfaceDestroyed");
			}
		});

		final fMediaDataCallback fm = new fMediaDataCallback() {

			@Override
			public void invoke(int nPDLLHandle, int nSeq, int nMediaType, byte[] szNodeId, int nParamVal, byte[] szData,
					int nDataLen) {

				int ret = IPlaySDK.PLAYInputData(m_nPort, szData, nDataLen);
				if (ret == 1) {
					Log.e("xss", "playing success=" + nSeq + " package size=" + nDataLen);
				} else {
					Log.e("xss", "playing failed=" + nSeq + " package size=" + nDataLen);
				}
			}
		};

		if (!StartRealPlay()) {
			Log.e("xss", "StartRealPlay failed!");
			Toast.makeText(getApplicationContext(), "Open video failed!", Toast.LENGTH_SHORT).show();
			return;
		}

		try {
			Return_Value_Info_t retVal = new Return_Value_Info_t();

			Get_RealStream_Info_t getRealStreamInfo = new Get_RealStream_Info_t();
			// m_szCameraId = etCam.getText().toString().getBytes();

			String cameraId = new String(m_szCameraId);

			System.arraycopy(m_szCameraId, 0, getRealStreamInfo.szCameraId, 0, m_szCameraId.length);
			// getRealStreamInfo.szCameraId =
			// "1000096$1$0$0".getBytes();
			getRealStreamInfo.nMediaType = 1;
			getRealStreamInfo.nRight = 1;
			getRealStreamInfo.nStreamType = 1;
			getRealStreamInfo.nTransType = 1;
			Enc_Channel_Info_Ex_t ChannelInfo = new Enc_Channel_Info_Ex_t();

			IDpsdkCore.DPSDK_GetChannelInfoById(m_pDLLHandle, m_szCameraId, ChannelInfo);
			int ret = IDpsdkCore.DPSDK_GetRealStream(m_pDLLHandle, retVal, getRealStreamInfo, fm, mTimeOut);
			if (ret == 0) {
				m_nSeq = retVal.nReturnValue;
				Log.e("xss DPSDK_GetRealStream success!", ret + "");
				Toast.makeText(getApplicationContext(), "Open video success!", Toast.LENGTH_SHORT).show();
			} else {
				StopRealPlay();
				Log.e("xss DPSDK_GetRealStream failed!", ret + "");
				Toast.makeText(getApplicationContext(), "Open video failed!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e("xss", e.toString());
		}

	}

	private void findViews() {
		btLeft = (Button) findViewById(R.id.button_ptz_left);
		btRight = (Button) findViewById(R.id.button_right);
		btTop = (Button) findViewById(R.id.button_top);
		btBottom = (Button) findViewById(R.id.button_bottom);
		btAddZoom = (Button) findViewById(R.id.button_add_zoom);
		btReduceZoom = (Button) findViewById(R.id.button_reduce_zoom);
		btAddFocus = (Button) findViewById(R.id.button_add_focus);
		btReduceFocus = (Button) findViewById(R.id.button_reduce_focus);
		btAddAperture = (Button) findViewById(R.id.button_add_aperture);
		btReduceAperture = (Button) findViewById(R.id.button_reduce_aperture);
		etCam = (TextView) findViewById(R.id.et_cam_id);
		tvRet = (TextView) findViewById(R.id.tv_excute_result);
		m_svPlayer = (SurfaceView) findViewById(R.id.sv_player);
		btnCaptureImg = (Button) findViewById(R.id.capture_img);
	}

	private void setListener() {

		btnCaptureImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				captureBitmap();
				// saveIntoMediaCore();
			}
		});

		btLeft.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {

				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					Ptz_Direct_Info_t ptzDirectInfo = new Ptz_Direct_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzDirectInfo.szCameraId, 0, m_szCameraId.length);
					ptzDirectInfo.bStop = false;
					ptzDirectInfo.nDirect = 3;
					ptzDirectInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzDirection(m_pDLLHandle, ptzDirectInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzDirection success!");
					} else {
						Log.e("xss", "DPSDK_PtzDirection failed!");
					}
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					Ptz_Direct_Info_t ptzDirectInfo = new Ptz_Direct_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzDirectInfo.szCameraId, 0, m_szCameraId.length);
					ptzDirectInfo.bStop = true;
					ptzDirectInfo.nDirect = 3;
					ptzDirectInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzDirection(m_pDLLHandle, ptzDirectInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzDirection success!");
					} else {
						Log.e("xss", "DPSDK_PtzDirection failed!");
					}
				}

				return false;
			}
		});

		btRight.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {

				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					Ptz_Direct_Info_t ptzDirectInfo = new Ptz_Direct_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzDirectInfo.szCameraId, 0, m_szCameraId.length);
					ptzDirectInfo.bStop = false;
					ptzDirectInfo.nDirect = 4;
					ptzDirectInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzDirection(m_pDLLHandle, ptzDirectInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzDirection success!");
					} else {
						Log.e("xss", "DPSDK_PtzDirection failed!");
					}
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					Ptz_Direct_Info_t ptzDirectInfo = new Ptz_Direct_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzDirectInfo.szCameraId, 0, m_szCameraId.length);
					ptzDirectInfo.bStop = true;
					ptzDirectInfo.nDirect = 4;
					ptzDirectInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzDirection(m_pDLLHandle, ptzDirectInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzDirection success!");
					} else {
						Log.e("xss", "DPSDK_PtzDirection failed!");
					}
				}

				return false;
			}
		});

		btTop.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					Ptz_Direct_Info_t ptzDirectInfo = new Ptz_Direct_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzDirectInfo.szCameraId, 0, m_szCameraId.length);
					ptzDirectInfo.bStop = false;
					ptzDirectInfo.nDirect = 1;
					ptzDirectInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzDirection(m_pDLLHandle, ptzDirectInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzDirection success!");
					} else {
						Log.e("xss", "DPSDK_PtzDirection failed!");
					}
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					Ptz_Direct_Info_t ptzDirectInfo = new Ptz_Direct_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzDirectInfo.szCameraId, 0, m_szCameraId.length);
					ptzDirectInfo.bStop = true;
					ptzDirectInfo.nDirect = 1;
					ptzDirectInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzDirection(m_pDLLHandle, ptzDirectInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzDirection success!");
					} else {
						Log.e("xss", "DPSDK_PtzDirection failed!");
					}
				}

				return false;
			}
		});

		btBottom.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					Ptz_Direct_Info_t ptzDirectInfo = new Ptz_Direct_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzDirectInfo.szCameraId, 0, m_szCameraId.length);
					ptzDirectInfo.bStop = false;
					ptzDirectInfo.nDirect = 2;
					ptzDirectInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzDirection(m_pDLLHandle, ptzDirectInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzDirection success!");
					} else {
						Log.e("xss", "DPSDK_PtzDirection failed!");
					}
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					Ptz_Direct_Info_t ptzDirectInfo = new Ptz_Direct_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzDirectInfo.szCameraId, 0, m_szCameraId.length);
					ptzDirectInfo.bStop = true;
					ptzDirectInfo.nDirect = 2;
					ptzDirectInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzDirection(m_pDLLHandle, ptzDirectInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzDirection success!");
					} else {
						Log.e("xss", "DPSDK_PtzDirection failed!");
					}
				}

				return false;
			}
		});

		btAddZoom.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					Ptz_Operation_Info_t ptzOperationInfo = new Ptz_Operation_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzOperationInfo.szCameraId, 0, m_szCameraId.length);
					ptzOperationInfo.bStop = false;
					ptzOperationInfo.nOperation = 0;
					ptzOperationInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzCameraOperation(m_pDLLHandle, ptzOperationInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzCameraOperation success!");
					} else {
						Log.e("xss", "DPSDK_PtzCameraOperation failed!");
					}
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					Ptz_Operation_Info_t ptzOperationInfo = new Ptz_Operation_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzOperationInfo.szCameraId, 0, m_szCameraId.length);
					ptzOperationInfo.bStop = true;
					ptzOperationInfo.nOperation = 0;
					ptzOperationInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzCameraOperation(m_pDLLHandle, ptzOperationInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzCameraOperation success!");
					} else {
						Log.e("xss", "DPSDK_PtzCameraOperation failed!");
					}
				}

				return false;
			}
		});

		btReduceZoom.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					Ptz_Operation_Info_t ptzOperationInfo = new Ptz_Operation_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzOperationInfo.szCameraId, 0, m_szCameraId.length);
					ptzOperationInfo.bStop = false;
					ptzOperationInfo.nOperation = 3;
					ptzOperationInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzCameraOperation(m_pDLLHandle, ptzOperationInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzCameraOperation success!");
					} else {
						Log.e("xss", "DPSDK_PtzCameraOperation failed!");
					}
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					Ptz_Operation_Info_t ptzOperationInfo = new Ptz_Operation_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzOperationInfo.szCameraId, 0, m_szCameraId.length);
					ptzOperationInfo.bStop = true;
					ptzOperationInfo.nOperation = 3;
					ptzOperationInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzCameraOperation(m_pDLLHandle, ptzOperationInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzCameraOperation success!");
					} else {
						Log.e("xss", "DPSDK_PtzCameraOperation failed!");
					}
				}

				return false;
			}
		});

		btAddFocus.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					Ptz_Operation_Info_t ptzOperationInfo = new Ptz_Operation_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzOperationInfo.szCameraId, 0, m_szCameraId.length);
					ptzOperationInfo.bStop = false;
					ptzOperationInfo.nOperation = 1;
					ptzOperationInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzCameraOperation(m_pDLLHandle, ptzOperationInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzCameraOperation success!");
					} else {
						Log.e("xss", "DPSDK_PtzCameraOperation failed!");
					}
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					Ptz_Operation_Info_t ptzOperationInfo = new Ptz_Operation_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzOperationInfo.szCameraId, 0, m_szCameraId.length);
					ptzOperationInfo.bStop = true;
					ptzOperationInfo.nOperation = 1;
					ptzOperationInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzCameraOperation(m_pDLLHandle, ptzOperationInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzCameraOperation success!");
					} else {
						Log.e("xss", "DPSDK_PtzCameraOperation failed!");
					}
				}

				return false;
			}
		});

		btReduceFocus.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {

				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					Ptz_Operation_Info_t ptzOperationInfo = new Ptz_Operation_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzOperationInfo.szCameraId, 0, m_szCameraId.length);
					ptzOperationInfo.bStop = false;
					ptzOperationInfo.nOperation = 4;
					ptzOperationInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzCameraOperation(m_pDLLHandle, ptzOperationInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzCameraOperation success!");
					} else {
						Log.e("xss", "DPSDK_PtzCameraOperation failed!");
					}
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					Ptz_Operation_Info_t ptzOperationInfo = new Ptz_Operation_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzOperationInfo.szCameraId, 0, m_szCameraId.length);
					ptzOperationInfo.bStop = true;
					ptzOperationInfo.nOperation = 4;
					ptzOperationInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzCameraOperation(m_pDLLHandle, ptzOperationInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzCameraOperation success!");
					} else {
						Log.e("xss", "DPSDK_PtzCameraOperation failed!");
					}
				}

				return false;
			}
		});

		btAddAperture.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					Ptz_Operation_Info_t ptzOperationInfo = new Ptz_Operation_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzOperationInfo.szCameraId, 0, m_szCameraId.length);
					ptzOperationInfo.bStop = false;
					ptzOperationInfo.nOperation = 2;
					ptzOperationInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzCameraOperation(m_pDLLHandle, ptzOperationInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzCameraOperation success!");
					} else {
						Log.e("xss", "DPSDK_PtzCameraOperation failed!");
					}
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					Ptz_Operation_Info_t ptzOperationInfo = new Ptz_Operation_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzOperationInfo.szCameraId, 0, m_szCameraId.length);
					ptzOperationInfo.bStop = true;
					ptzOperationInfo.nOperation = 2;
					ptzOperationInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzCameraOperation(m_pDLLHandle, ptzOperationInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzCameraOperation success!");
					} else {
						Log.e("xss", "DPSDK_PtzCameraOperation failed!");
					}
				}

				return false;
			}
		});

		btReduceAperture.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					Ptz_Operation_Info_t ptzOperationInfo = new Ptz_Operation_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzOperationInfo.szCameraId, 0, m_szCameraId.length);
					ptzOperationInfo.bStop = false;
					ptzOperationInfo.nOperation = 5;
					ptzOperationInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzCameraOperation(m_pDLLHandle, ptzOperationInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzCameraOperation success!");
					} else {
						Log.e("xss", "DPSDK_PtzCameraOperation failed!");
					}
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					Ptz_Operation_Info_t ptzOperationInfo = new Ptz_Operation_Info_t();
					System.arraycopy(m_szCameraId, 0, ptzOperationInfo.szCameraId, 0, m_szCameraId.length);
					ptzOperationInfo.bStop = true;
					ptzOperationInfo.nOperation = 5;
					ptzOperationInfo.nStep = 4;

					int ret = IDpsdkCore.DPSDK_PtzCameraOperation(m_pDLLHandle, ptzOperationInfo, mTimeOut);
					if (ret == 0) {
						Log.e("xss", "DPSDK_PtzCameraOperation success!");
					} else {
						Log.e("xss", "DPSDK_PtzCameraOperation failed!");
					}
				}

				return false;
			}
		});
	}

	/**
	 * �����ļ��� �����ͼ ͼƬ
	 */
	private void captureBitmap() {

		String path = IMAGE_PATH + IMGSTR;
		// �ȴ���һ���ļ���
		File dir = new File(IMAGE_PATH);
		File file = new File(IMAGE_PATH, IMGSTR);
		if (!dir.exists()) {
			dir.mkdir();
		} else {
			if (file.exists()) {
				file.delete();
			}
		}

		int result = IPlaySDK.PLAYCatchPicEx(m_nPort, path, PicFormat_JPEG);
		Log.i("PLAYCatchPicEx", String.valueOf(result));
		if (result > 0) {
			showToast(R.string.snapsucess);
			saveIntoMediaCore();
		} else {
			showToast(R.string.snapfail);
		}
	}

	private void saveIntoMediaCore() {
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		// intent.setAction(MEDIA_ROUTER_SERVICE);
		Uri uri = Uri.parse(IMAGE_PATH + IMGSTR);
		intent.setData(uri);
		RealPlayActivity.this.setIntent(intent);
	}

	private void showToast(int str) {
		Toast.makeText(getApplicationContext(), getResources().getString(str), Toast.LENGTH_SHORT).show();
	}

	public void StopRealPlay() {
		try {
			IPlaySDK.PLAYStopSoundShare(m_nPort);
			IPlaySDK.PLAYStop(m_nPort);
			IPlaySDK.PLAYCloseStream(m_nPort);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean StartRealPlay() {
		if (m_svPlayer == null)
			return false;

		boolean bOpenRet = IPlaySDK.PLAYOpenStream(m_nPort, null, 0, 1500 * 1024) == 0 ? false : true;
		if (bOpenRet) {
			boolean bPlayRet = IPlaySDK.PLAYPlay(m_nPort, m_svPlayer) == 0 ? false : true;
			Log.i("StartRealPlay", "StartRealPlay1");
			if (bPlayRet) {
				boolean bSuccess = IPlaySDK.PLAYPlaySoundShare(m_nPort) == 0 ? false : true;

				Log.i("StartRealPlay", "StartRealPlay2");
				if (!bSuccess) {
					IPlaySDK.PLAYStop(m_nPort);
					IPlaySDK.PLAYCloseStream(m_nPort);
					Log.i("StartRealPlay", "StartRealPlay3");
					return false;
				}
			} else {
				IPlaySDK.PLAYCloseStream(m_nPort);
				Log.i("StartRealPlay", "StartRealPlay4");
				return false;
			}
		} else {
			Log.i("StartRealPlay", "StartRealPlay5");
			return false;
		}

		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

}
