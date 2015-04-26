package six.magicalboard.core.logic;

import java.util.ArrayList;
import java.util.HashMap;

import six.magicalboard.core.StretchView;
import six.magicalboard.core.texture.ImageSDrawable;
import six.magicalboard.core.texture.SDrawable;
import android.graphics.Canvas;
import android.graphics.Rect;

public final class StretchRender{

	private Canvas mCanvas;
	
	private StretchView view = null;
	
	public void setRenderCanvas(Canvas mCanvas) {
		this.mCanvas = mCanvas;
	}
	
	public Canvas getRenderCanvas(){
		return this.mCanvas;
	}
	public Rect getRenderRect(){
		return view==null?null:view.getRect();
	}
	public StretchRender(StretchView view , Canvas mCanvas){
		this.view = view;
		this.mCanvas = mCanvas;
	}
	public void notifyDataChanged() {
		// TODO Auto-generated method stub
		if(null != view){
			view.postInvalidate();
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void draw(SDrawable d){
		if(null != d){
			if(d instanceof ImageSDrawable){
				view.cleanBackground();
				d.draw(view.getBgCanvas());
			}else{
				d.draw(mCanvas);
			}
			notifyDataChanged();
		}
	}
	public void draw(ArrayList<Integer> arrs,HashMap<Integer,SDrawable> maps){
		view.cleanBackground();
		view.cleanForeground();
		if(null != arrs && arrs.size()>0){
			for(Integer id: arrs){
				SDrawable d = maps.get(id);
				if(null != d){
					if(d instanceof ImageSDrawable){
						d.draw(view.getBgCanvas());
					}else{
						d.draw(mCanvas);
					}
				}
			}
			
		}
		notifyDataChanged();
	}
	public void draw(){
		view.cleanBackground();
		view.cleanForeground();
		notifyDataChanged();
	}
}
