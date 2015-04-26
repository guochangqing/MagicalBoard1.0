package six.magicalboard.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.widget.Toast;

public class UIUtil {
	
	/**
	 * 以矩形的水平中点｜顶点来绘制位图
	 * @param bitmap
	 * @param rect
	 * @param canvas
	 * @param paint
	 * @param isScale
	 */
	public static void drawBitmap(Bitmap bitmap,Rect rect,Canvas canvas,Paint paint,boolean isScale){
		if(bitmap==null||bitmap.isRecycled()||rect==null){
			return;
		}
		int l = 0;
		int r = 0;
		int t = 0;
		int b = 0;
		if(!isScale){
			l = rect.left+(rect.width()-bitmap.getWidth())/2;
			t = rect.top+(rect.height()-bitmap.getHeight())/2;
			r = l+bitmap.getWidth();
			b = t+bitmap.getHeight();
		}else{
			float a1 = (float)bitmap.getWidth()/rect.width();
			int t1 = (int)(bitmap.getHeight()/a1);
			if(t1>=rect.height()){
				//按高
				float a2 = (float)rect.height()/bitmap.getHeight();
				l = rect.left+(rect.width()-(int)(bitmap.getWidth()*a2))/2;
				r = l+(int)(bitmap.getWidth()*a2);
				t = rect.top;
				b = rect.bottom;
			}else{
				float a2 = (float)rect.width()/bitmap.getWidth();
				l = rect.left;
				r = rect.right;
				t = rect.top + (rect.height() - (int)(bitmap.getHeight()*a2))/2;
				b = t + (int)(bitmap.getHeight()*a2);
			}
		}
		canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),new Rect(l,t,r,b), paint);
	}
	public static void drawLine(Canvas canvas,Paint paint,int fx,int fy,int tx,int ty,int color,int strokewidth){
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(strokewidth);
        paint.setColor(color);
        Path path = new Path();
        path.moveTo(fx, fy);
        path.lineTo(tx, ty);
        PathEffect effects = new DashPathEffect(new float[] { 10,10, 10, 10 }, 1);
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
	}
	/** 
     * Bitmap转化为drawable 
     * @param bitmap 
     * @return 
     */  
    public static Drawable bitmap2Drawable(Bitmap bitmap){  
        return new BitmapDrawable(bitmap) ;  
    }  
      
    /** 
     * Drawable 转 bitmap <br/>
     * Drawable 转 bitmap时需要区分drawable是属于哪一类的图标，如果是NinePatchDrawable就不能直接转为BitmapDrawable来取得Bitmap了
     * @param drawable 
     * @return 
     */  
    public static Bitmap drawable2Bitmap(Drawable drawable){  
        if(drawable instanceof BitmapDrawable){  
            return ((BitmapDrawable)drawable).getBitmap() ;  
        }else if(drawable instanceof NinePatchDrawable){  
            Bitmap bitmap = Bitmap  
                    .createBitmap(  
                            drawable.getIntrinsicWidth(),  
                            drawable.getIntrinsicHeight(),  
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  
                                    : Bitmap.Config.RGB_565);  
            Canvas canvas = new Canvas(bitmap);  
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),  
                    drawable.getIntrinsicHeight());  
            drawable.draw(canvas);  
            return bitmap;  
        }else{  
            return null ;  
        }  
    }  
    /***************************************************************************
	 * Toast显示
	 */
	public static void displayToast(Context context, int resId) {
		if(context != null) {
			Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
		}
	}
	public static void displayToast(Context context, String msg) {
		if(context != null) {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}
}
