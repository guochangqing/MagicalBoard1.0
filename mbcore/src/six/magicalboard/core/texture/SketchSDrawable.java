package six.magicalboard.core.texture;

import java.util.ArrayList;

import six.magicalboard.core.Constants;
import six.magicalboard.core.InfoBase;
import six.magicalboard.core.RenderPolicy;
import six.magicalboard.core.logic.StretchRender;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

public class SketchSDrawable extends SDrawable{

	public SketchSDrawable(Context context,StretchRender render,Rect rect) {
		super(context,render,rect);
		// TODO Auto-generated constructor stub
		arrs = new ArrayList<PointF>();
	}
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isHasPath()){
			Path pp = getPath();
			if(null != pp){
				canvas.drawPath(pp, mPaint);
			}
		}
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		if(null != arrs){
			arrs.clear();
			arrs = null;
		}
	}

	public boolean isHasPath(){
		return null != arrs && arrs.size()>0;
	}
	private ArrayList<PointF> arrs = null;
	
	public void addPoint(float x,float y){
		addPoint(new PointF(x,y));
	}
	public void addPoint(PointF entity){
		arrs.add(entity);
	}
	protected int drawmode,penColor,penWidth;
	protected boolean penStyle;
	
	public int getDrawmode() {
		return drawmode;
	}

	public int getPenColor() {
		return penColor;
	}

	public int getPenWidth() {
		return penWidth;
	}

	public boolean isPenStyle() {
		return penStyle;
	}

	public void setPaintByPolicy(RenderPolicy policy){
		drawmode = policy.getDrawmode();
		penColor = policy.getPenColor();
		penWidth = policy.getPenWidth();
		penStyle = policy.isPenStyle();
		mPaint.setFilterBitmap(true);
		mPaint.setDither(true);
		mPaint.setSubpixelText(true);
		mPaint.setAntiAlias(true);
		if(policy.getDrawmode() == Constants.DRAWMODEPEN){
			mPaint.setColor(policy.getPenColor());
			if(!policy.isPenStyle()){
				mPaint.setPathEffect(new DashPathEffect(new float[] {policy.getPenWidth(),policy.getPenWidth()*2,policy.getPenWidth(),policy.getPenWidth()*2}, 1));
			}
		}else{
			mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
		}
		mPaint.setStrokeWidth(policy.getPenWidth());
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
	}
	public Path getPath(){
		if(null != arrs && arrs.size()>0){
			Path m_penPath = new Path();
			m_penPath.reset();
			for(int i=0;i<arrs.size();i++){
				PointF pe = arrs.get(i);
				if(i == 0){
					m_penPath.moveTo(pe.x, pe.y);
				}else{
					m_penPath.lineTo(pe.x, pe.y);
				}
			}
			return m_penPath;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void parse(InfoBase info) {
		// TODO Auto-generated method stub
		if(null != info){
			arrs = info.getList("arrs");
			drawmode = info.getInt("drawmode");
			penColor = info.getInt("pencolor");
			penWidth = info.getInt("penwidth");
			penStyle = info.getBool("penstyle");
		}
	}

	@Override
	public InfoBase generate(int identifier) {
		// TODO Auto-generated method stub
		InfoBase info = new InfoBase();
		info.setType(Constants.DRAWABLESKETCH);
		info.setIdentifier(identifier);
		if(null != arrs && arrs.size()>0){
			info.putList("arrs", deepCopy(arrs));
		}
		info.putInt("drawmode", drawmode);
		info.putInt("pencolor", penColor);
		info.putInt("penwidth", penWidth);
		info.putBool("penstyle", penStyle);
		return info;
	}
	public ArrayList<PointF> deepCopy(ArrayList<PointF> src){   
		if(null != arrs && arrs.size()>0){
			ArrayList<PointF> arrss = new ArrayList<PointF>();
			for(PointF f:src){
				arrss.add(new PointF(f.x,f.y));
			}
			return arrss;
		}
		return null;
    }   
}
