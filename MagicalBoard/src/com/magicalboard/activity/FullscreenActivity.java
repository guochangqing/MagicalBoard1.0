package com.magicalboard.activity;

import java.util.ArrayList;

import six.magicalboard.core.ActionChangeListener;
import six.magicalboard.core.ActionWrapper;
import six.magicalboard.core.InfoBase;
import six.magicalboard.core.StretchView;
import six.magicalboard.core.ViewChangeListener;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;

import com.omusic.magicalboard.R;

public class FullscreenActivity extends Activity implements ViewChangeListener{

	StretchView view = null;
	ArrayList<InfoBase> info = null;
	ActionWrapper dc = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		view = (StretchView)findViewById(R.id.fullscreenview);
		view.setViewChangelistener(this);
		view.setActionChangeListener(acl1);
		dc = view.getWrapper();
		info = this.getIntent().getParcelableArrayListExtra("data");
	}
	@Override
	public void onInflateFinish() {
		// TODO Auto-generated method stub
		dc.writeAllDrawable(info);
		dc.requestPaint();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dc.clearActions(false);
	}

	ActionChangeListener acl1 = new ActionChangeListener() {

		

		@Override
		public void onAddPicture(int resid) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onAddPicture(String uri) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPointGenerate(int status, PointF... subentity) {
			// TODO Auto-generated method stub
			MainActivity.dc1.pushPointArray(status, subentity);
			MainActivity.dc2.pushPointArray(status, subentity);
		}

		@Override
		public void onActionPop() {
			// TODO Auto-generated method stub
		}

		@Override
		public void onActionClear() {
			// TODO Auto-generated method stub
			
		}
		
		
		
	};
}
