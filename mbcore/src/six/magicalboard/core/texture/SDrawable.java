package six.magicalboard.core.texture;

import six.magicalboard.core.InfoBase;
import six.magicalboard.core.logic.StretchRender;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class SDrawable {
	//总结构大小
	protected Rect texturerect;
	//是否可以获取焦点
	protected boolean isFocus = false;
	
	protected Paint mPaint = null;
	
	protected Context context;
	
	protected StretchRender render;
	

	public Context getContext() {
		return context;
	}

	public abstract void parse(InfoBase info);
	
	public abstract InfoBase generate(int identifier);

	public abstract void draw(Canvas canvas);
	
	
	public boolean isFocus() {
		return isFocus;
	}

	public void setFocus(boolean isFocus) {
		this.isFocus = isFocus;
	}

	public Rect getTexturerect() {
		return texturerect;
	}
	
	public SDrawable(Context context,StretchRender render,Rect rect){
		this.context = context;
		this.render = render;
		this.texturerect = rect;
		mPaint = new Paint();
	}
	
	public void destroy(){
		texturerect = null;
		isFocus = false;
		mPaint = null;
	}
}
