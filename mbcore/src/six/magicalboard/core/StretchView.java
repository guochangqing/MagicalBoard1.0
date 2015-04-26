package six.magicalboard.core;

import six.magicalboard.core.logic.DataCenter;
import six.magicalboard.core.logic.GestureDetector;
import six.magicalboard.core.logic.GestureDetector.SimpleOnGestureListener;
import six.magicalboard.core.logic.StretchRender;
import six.magicalboard.core.utils.IUtil;
import six.magicalboard.core.utils.RectUtil;
import six.magicalboard.core.utils.UIUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("DrawAllocation")
public class StretchView extends View{
	
	private static final int SCALEWIDTH = 16;
	
	private static final int SCALEHEIGHT = 9;
	
	private static final int SCALEDIS = 60;
	//数据中心
	private DataCenter datacenter = null;
	
	private ActionWrapper wrapper;
	
	private ViewChangeListener vclistener;
	
	
	public void setViewChangelistener(ViewChangeListener vclistener) {
		this.vclistener = vclistener;
	}

	public ActionWrapper getWrapper() {
		return wrapper;
	}

	public void setActionChangeListener(ActionChangeListener acl){
		if(null != datacenter){
			datacenter.setListener(acl);
		}
	}
	public void setPaintChangeListener(PaintChangeListener acl){
		if(null != datacenter && datacenter.getPolicy() != null){
			datacenter.getPolicy().setListener(acl);
		}
	}
	//画笔
	private Paint mPaint = null;
	
	//画笔参数
	private PaintFlagsDrawFilter mSetfil = null;
	
	private Bitmap bgbitmap = null;
	
	private Canvas bgCanvas = null;
	//画布
	private Bitmap forebitmap = null;
	
	private Canvas m_penCanvas = null;
	//可绘制区域
	private Rect rect = null;
	
	GestureDetector gestureDetector = null;
	//背景视图可用
	private boolean isBgCanvasUsed = Constants.DEFAULTBGCANVASUSED;
	
	public Rect getRect() {
		return new Rect(0,0,SCALEWIDTH*SCALEDIS,SCALEHEIGHT*SCALEDIS);
	}
	
	public Canvas getBgCanvas() {
		return bgCanvas;
	}
	
	public boolean isBgCanvasUsed() {
		return isBgCanvasUsed;
	}

	public StretchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	public StretchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	@SuppressWarnings("deprecation")
	private void init(Context context){
		setFocusable(true);
		setFocusableInTouchMode(true);
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(Constants.DEFAULTBOARDCOLOR);
		m_penCanvas = new Canvas();
		bgCanvas = new Canvas();
		gestureDetector = new GestureDetector(new SketchTextureGestureDetector());
		StretchRender render = new StretchRender(this,m_penCanvas);
		datacenter = new DataCenter(context,render);
		wrapper = new ActionWrapper(datacenter);
		setBackgroundColor(Color.BLACK);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(event);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = measureWidth(widthMeasureSpec);
		int measuredHeight = measureHeight(heightMeasureSpec);
		if(rect==null){
			rect = new Rect();
		}
		int w,h;
		int a1 = measuredWidth*SCALEHEIGHT/SCALEWIDTH;
		if(a1>measuredHeight){
			//按高
			w = measuredHeight*SCALEWIDTH/SCALEHEIGHT;
			h = measuredHeight;
		}else{
			w = measuredWidth;
			h = measuredWidth*SCALEHEIGHT/SCALEWIDTH;
		}
		rect.set((measuredWidth-w)/2, (measuredHeight-h)/2, (measuredWidth-w)/2+w, (measuredHeight-h)/2+h);
		setMeasuredDimension(measuredWidth,measuredHeight);
	}
	
	private int measureWidth(int measureSpec){
		int specMode=MeasureSpec.getMode(measureSpec);
		int specSize=MeasureSpec.getSize(measureSpec);
		//如果没有限制默认大小是指定的.
		int result=0;
		if(specMode==MeasureSpec.AT_MOST){
			//理想尺寸的计算你的控制
			//在这个最大的尺寸。
			//如果你的控制填充的可用空间
			//返回外面的束缚。
			result=specSize;
		}else if(specMode==MeasureSpec.EXACTLY){
			//如果你的控制能符合这些界限返回那个价值.
			result=specSize;
		}
		return result;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
	}
	private int measureHeight(int measureSpec){
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		//如果没有限制默认大小是指定的.
		int result=0;
		if(specMode==MeasureSpec.AT_MOST){
			//你理想的大小的计算
			//在这个最大值控制.
			//如果你能控制的充满可用
			//外太空返回束缚.
			result=specSize;
		}else if(specMode==MeasureSpec.EXACTLY){
			//如果你的控制能符合这些界限返回那个价值.
			result=specSize;
		}
		return result;
	}
	public void cleanBackground(){
		if(null != bgbitmap && !bgbitmap.isRecycled()){
			bgbitmap.eraseColor(Color.TRANSPARENT);
		}
	}
	public void cleanForeground(){
		if(null != forebitmap && !forebitmap.isRecycled()){
			forebitmap.eraseColor(Color.TRANSPARENT);
		}
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawRect(rect, mPaint);
		if(null == forebitmap || forebitmap.isRecycled()){
			forebitmap = Bitmap.createBitmap(SCALEWIDTH*SCALEDIS, SCALEHEIGHT*SCALEDIS, Bitmap.Config.ARGB_8888);
			m_penCanvas.setBitmap(forebitmap);
			if(isBgCanvasUsed){
				bgbitmap = Bitmap.createBitmap(SCALEWIDTH*SCALEDIS, SCALEHEIGHT*SCALEDIS, Bitmap.Config.ARGB_8888);
				bgCanvas.setBitmap(bgbitmap);
			}
			if(null != datacenter){
				datacenter.inflateFinish();
			}
			if(null != vclistener){
				vclistener.onInflateFinish();
			}
		}
		if(mSetfil==null){
			mSetfil = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
			canvas.setDrawFilter(mSetfil);
		}
		if(isBgCanvasUsed){
			UIUtil.drawBitmap(bgbitmap, rect, canvas, mPaint, true);
		}
		UIUtil.drawBitmap(forebitmap, rect, canvas, mPaint, true);
	}
	/**
	 * 坐标转换
	 * */
	private PointF goConvert(float x,float y){
		return new PointF((x-rect.left)*SCALEWIDTH*SCALEDIS/rect.width(),(y-rect.top)*SCALEHEIGHT*SCALEDIS/rect.height());
	}
	int identifier = 0;
	private class SketchTextureGestureDetector extends SimpleOnGestureListener{

		
		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			if(RectUtil.overlap(rect, e)){
				if(null != datacenter){
					identifier = IUtil.getIdentifier();
					datacenter.updateLocation(0,goConvert(e.getX(),e.getY()),true,identifier);
				}
				return true;
			}else{
				return false;
			}
		}
		@Override
		public boolean onUp(MotionEvent e) {
			// TODO Auto-generated method stub
			if(RectUtil.overlap(rect, e)){
				if(null != datacenter){
					datacenter.updateLocation(2,goConvert(e.getX(),e.getY()),true,identifier);
				}
				return true;
			}else{
				return false;
			}
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			if(RectUtil.overlap(rect, e1)){
				if(null != datacenter){
					datacenter.updateLocation(1,goConvert(e2.getX(),e2.getY()),true,identifier);
				}
				return true;
			}else{
				return false;
			}
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
}
