package six.magicalboard.core;

import android.os.Environment;


public class Constants {
	//画笔风格
	public static final int DRAWMODEPEN = 0;
	//板擦风格
	public static final int DRAWMODEERASER = 1;
	
	//**************************************************************
	//图绘制物
	public static final int DRAWABLEIMAGE = 0;
	//点线绘制物
	public static final int DRAWABLESKETCH = 1;
	
	///////////////////////////////////////////////////////////////
	
	//默认白板的颜色
	public static int DEFAULTBOARDCOLOR = 0xffffffff;
	//默认画笔颜色
	public static int DEFAULTPAINTCOLOR = 0xff000000;
	//默认画笔宽度
	public static int DEFAULTPAINTWIDTH = 5;
	//画笔样式，true＝实线--false＝虚线
	public static boolean DEFAULTPAINTSTYLE = true;
	//默认板擦宽度
	public static int DEFAULTERASERWIDTH = 30;
	//默认发送频率，-1＝＝以系统默认；大于等于0（单位：毫秒）
	public static int DEFAULTRASENDRATE = 50;
	//背景画布可用
	public static boolean DEFAULTBGCANVASUSED = true;
	
	public static int DEFAULTPICTUREID = -1;
	
	public static boolean DEBUG = true;
	//默认保存路径
	public static String DEFAULTSAVEPATH = Environment.getExternalStorageDirectory() + "/";
	
	public static void setDefaultBoardColor(int color){
		DEFAULTBOARDCOLOR = color;
	}
	public static void setDefaultPenColor(int color){
		DEFAULTPAINTCOLOR = color;
	}
	public static void setDefaultPenWidth(int width){
		DEFAULTPAINTWIDTH = width;
	}
	/**
	 * @param b true is stroke
	 * */
	public static void setDefaultPenStyle(boolean b){
		DEFAULTPAINTSTYLE = b;
	}
	public static void setDefaultEraseWidth(int width){
		DEFAULTERASERWIDTH = width;
	}
	public static void setDefaultSendRate(int rate){
		DEFAULTRASENDRATE = rate;
	}
	public static void setDefaultBgCanvasUsed(boolean b){
		DEFAULTBGCANVASUSED = b;
	}
	public static void setDefaultPicture(int resid){
		DEFAULTPICTUREID = resid;
	}
	public static void setDefaultSavePath(String path){
		DEFAULTSAVEPATH = path;
	}
	public static boolean isDEBUG() {
		return DEBUG;
	}
	public static void setDeBug(boolean dEBUG) {
		DEBUG = dEBUG;
	}
	
	
}
