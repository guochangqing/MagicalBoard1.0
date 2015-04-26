package com.magicalboard.activity;

import six.magicalboard.core.ActionChangeListener;
import six.magicalboard.core.ActionWrapper;
import six.magicalboard.core.Constants;
import six.magicalboard.core.PaintChangeListener;
import six.magicalboard.core.StretchView;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.magicalboard.view.Panel;
import com.magicalboard.view.Panel.OnPanelListener;
import com.omusic.magicalboard.R;

public class MainActivity extends FragmentActivity implements OnClickListener{
	static final String LOGTAG = "MainActivity";
	Panel mPanel = null;
	LinearLayout layer1 = null;
	ScrollView layer1_scrollpool = null;
	StretchView board1 = null;
	StretchView board2 = null;
	public static ActionWrapper dc1 = null;
	public static ActionWrapper dc2 = null;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		Fragment f1 = (Fragment)this.getSupportFragmentManager().findFragmentById(R.id.fragment_drawboard1);
		board1 = (StretchView)f1.getView().findViewById(R.id.stretchview);
		board1.setActionChangeListener(acl1);
		board1.setPaintChangeListener(pcl);
		dc1 = board1.getWrapper();
		Fragment f2 = (Fragment)this.getSupportFragmentManager().findFragmentById(R.id.fragment_drawboard2);
		board2 = (StretchView)f2.getView().findViewById(R.id.stretchview);
		board2.setActionChangeListener(acl2);
		dc2 = board2.getWrapper();
		mPanel = (Panel) findViewById(R.id.topPanel);
		layer1 = (LinearLayout)findViewById(R.id.panelContent);
		layer1_scrollpool = (ScrollView)layer1.findViewById(R.id.layer1_scrollpool);
		layer1.findViewById(R.id.layer1_button1).setOnClickListener(this);
		layer1.findViewById(R.id.layer1_button2).setOnClickListener(this);
		layer1.findViewById(R.id.layer1_button3).setOnClickListener(this);
		layer1.findViewById(R.id.layer1_button4).setOnClickListener(this);
		layer1.findViewById(R.id.layer1_button5).setOnClickListener(this);
		layer1.findViewById(R.id.layer1_button6).setOnClickListener(this);
		layer1.findViewById(R.id.layer1_button8).setOnClickListener(this);
		mPanel.setOnPanelListener(new OnPanelListener() {

			@Override
			public void onPanelOpened(Panel panel) {
				// TODO Auto-generated method stub
				Log.e(LOGTAG, "onPanelOpened");
			}

			@Override
			public void onPanelClosed(Panel panel) {
				// TODO Auto-generated method stub
				Log.e(LOGTAG, "onPanelClosed");
				layer1_scrollpool.removeAllViews();
			}
		});
		//增加虚拟menu按钮
		try {  
		    getWindow().addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));  
		}catch (NoSuchFieldException e) {  
		    // Ignore since this field won't exist in most versions of Android  
		}catch (IllegalAccessException e) {  
		} 
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if(id == R.id.layer1_button1){
			//画笔
			dc1.setDrawMode(Constants.DRAWMODEPEN, true);
			resetLayer21(layer1_scrollpool);
		}else if(id == R.id.layer1_button2){
			//板擦
			dc1.setDrawMode(Constants.DRAWMODEERASER, true);
			dc1.setPenWidth(40,true);
//			resetLayer22(layer1_scrollpool);
		}else if(id == R.id.layer1_button3){
			//区域
			Intent itent = new Intent(this,FullscreenActivity.class);
			itent.putParcelableArrayListExtra("data",dc1.readAllDrawable());
			this.startActivity(itent);
		}else if(id == R.id.layer1_button4){
			//图层
			resetLayer23(layer1_scrollpool);
		}else if(id == R.id.layer1_button5){
			//后退
			dc1.popAction(true);
		}else if(id == R.id.layer1_button6){
			//前进
			
		}else if(id == R.id.layer1_button7){
			//文本
			
		}else if(id == R.id.layer1_button8){
			//图片
//			dc1.addPicture(R.drawable.ppt,true);
//			dc1.addPicture("http://a.hiphotos.baidu.com/album/w%3D2048/sign=d888657fbc3eb13544c7b0bb9226a9d3/a5c27d1ed21b0ef44357d1c9ddc451da80cb3eed.jpg", true);
			dc1.addPicture(Environment.getExternalStorageDirectory()+"/gou.jpg", true);
		}else if(id == R.id.layer2_button1){
			//画笔_大小
			resetLayer31((ViewGroup)findViewById(R.id.layer2_scrollpool));
		}else if(id == R.id.layer2_button2){
			//画笔_颜色
			resetLayer32((ViewGroup)findViewById(R.id.layer2_scrollpool));
		}else if(id == R.id.layer2_button3){
			//画笔_形状
			resetLayer33((ViewGroup)findViewById(R.id.layer2_scrollpool));
		}else if(id == R.id.layer2_button4){
			//板擦_大
		}else if(id == R.id.layer2_button5){
			//板擦_中
		}else if(id == R.id.layer2_button6){
			//板擦_小
		}else if(id == R.id.layer2_button7){
			//图层_增加
		}else if(id == R.id.layer2_button8){
			//图层_减少
			
		}else if(id == R.id.layer2_button9){
			//图层_合并
			
		}else if(id == R.id.layer3_button1){
			//画笔_大小_大
			dc1.setPenWidth(20,true);
		}else if(id == R.id.layer3_button2){
			//画笔_大小_中
			dc1.setPenWidth(10,true);
		}else if(id == R.id.layer3_button3){
			//画笔_大小_小
			dc1.setPenWidth(5,true);
		}else if(id == R.id.layer3_button4){
			//画笔_颜色_红
			dc1.setPenColor(0xffff0000, true);
		}else if(id == R.id.layer3_button5){
			//画笔_颜色_绿
			dc1.setPenColor(0xff00ff00, true);
		}else if(id == R.id.layer3_button6){
			//画笔_颜色_蓝
			dc1.setPenColor(0xff0000ff, true);
		}else if(id == R.id.layer3_button7){
			//画笔_形状_实线
			dc1.setPenStyle(true, true);
		}else if(id == R.id.layer3_button8){
			//画笔_形状_虚线
			dc1.setPenStyle(false, true);
		}
	}
	private void resetLayer21(ViewGroup view){
		view.removeAllViews();
		View v = View.inflate(this, R.layout.menu_layer2_1, null);
		view.addView(v);
		v.findViewById(R.id.layer2_button1).setOnClickListener(this);
		v.findViewById(R.id.layer2_button2).setOnClickListener(this);
		v.findViewById(R.id.layer2_button3).setOnClickListener(this);
	}
	protected void resetLayer22(ViewGroup view){
		view.removeAllViews();
		View v = View.inflate(this, R.layout.menu_layer2_2, null);
		view.addView(v);
		v.findViewById(R.id.layer2_button4).setOnClickListener(this);
		v.findViewById(R.id.layer2_button5).setOnClickListener(this);
		v.findViewById(R.id.layer2_button6).setOnClickListener(this);
	}
	private void resetLayer23(ViewGroup view){
		view.removeAllViews();
		View v = View.inflate(this, R.layout.menu_layer2_3, null);
		view.addView(v);
		v.findViewById(R.id.layer2_button7).setOnClickListener(this);
		v.findViewById(R.id.layer2_button8).setOnClickListener(this);
		v.findViewById(R.id.layer2_button9).setOnClickListener(this);
	}
	private void resetLayer31(ViewGroup view){
		view.removeAllViews();
		View v = View.inflate(this, R.layout.menu_layer3_1, null);
		view.addView(v);
		v.findViewById(R.id.layer3_button1).setOnClickListener(this);
		v.findViewById(R.id.layer3_button2).setOnClickListener(this);
		v.findViewById(R.id.layer3_button3).setOnClickListener(this);
	}
	private void resetLayer32(ViewGroup view){
		view.removeAllViews();
		View v = View.inflate(this, R.layout.menu_layer3_2, null);
		view.addView(v);
		v.findViewById(R.id.layer3_button4).setOnClickListener(this);
		v.findViewById(R.id.layer3_button5).setOnClickListener(this);
		v.findViewById(R.id.layer3_button6).setOnClickListener(this);
	}
	private void resetLayer33(ViewGroup view){
		view.removeAllViews();
		View v = View.inflate(this, R.layout.menu_layer3_3, null);
		view.addView(v);
		v.findViewById(R.id.layer3_button7).setOnClickListener(this);
		v.findViewById(R.id.layer3_button8).setOnClickListener(this);
	}
	ActionChangeListener acl1 = new ActionChangeListener() {

		

		@Override
		public void onAddPicture(int resid) {
			// TODO Auto-generated method stub
			dc2.addPicture(resid, false);
		}

		@Override
		public void onAddPicture(String uri) {
			// TODO Auto-generated method stub
			dc2.addPicture(uri, false);
		}

		@Override
		public void onPointGenerate(int status, PointF... subentity) {
			// TODO Auto-generated method stub
			dc2.pushPointArray(status, subentity);
		}

		@Override
		public void onActionPop() {
			// TODO Auto-generated method stub
			dc2.popAction(false);
		}

		@Override
		public void onActionClear() {
			// TODO Auto-generated method stub
			
		}
		
		
		
	};
	ActionChangeListener acl2 = new ActionChangeListener() {

		@Override
		public void onPointGenerate(int status, PointF... subentity) {
			// TODO Auto-generated method stub
			dc1.pushPointArray(status, subentity);
		}

		@Override
		public void onActionPop() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onActionClear() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAddPicture(int resid) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAddPicture(String uri) {
			// TODO Auto-generated method stub
			
		}
		
		
		
	};
	PaintChangeListener pcl = new PaintChangeListener() {
		
		@Override
		public void onDrawModeChange(int mode) {
			// TODO Auto-generated method stub
			dc2.setDrawMode(mode, false);
		}

		@Override
		public void onPenWidthChange(int width) {
			// TODO Auto-generated method stub
			dc2.setPenWidth(width, false);
		}
		
		@Override
		public void onPenStyleChange(boolean b) {
			// TODO Auto-generated method stub
			dc2.setPenStyle(b, false);
		}
		
		@Override
		public void onPenColorChange(int color) {
			// TODO Auto-generated method stub
			dc2.setPenColor(color, false);
		}
		
	};
}
