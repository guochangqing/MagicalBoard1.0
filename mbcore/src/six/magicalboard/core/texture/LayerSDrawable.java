package six.magicalboard.core.texture;

import six.magicalboard.core.Constants;
import six.magicalboard.core.InfoBase;
import six.magicalboard.core.logic.StretchRender;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class LayerSDrawable extends SDrawable{
	
	public LayerSDrawable(Context context,StretchRender render,Rect rect) {
		super(context,render,rect);
		// TODO Auto-generated constructor stub
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(Constants.DEFAULTBOARDCOLOR);
	}
	

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawRect(this.texturerect, mPaint); 
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}


	@Override
	public void parse(InfoBase info) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public InfoBase generate(int identifier) {
		// TODO Auto-generated method stub
		return null;
	}
}
