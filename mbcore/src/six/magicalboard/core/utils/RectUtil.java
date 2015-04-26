package six.magicalboard.core.utils;

import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * 矩形处理工具
 * @author kuruwazhangqing
 *
 */
public class RectUtil {
	/**
	 * 向上拓展高度
	 * @param rect
	 * @param value
	 */
	public static void widenTop(Rect rect,int value){
		if(rect!=null){
			rect.top-=value;
		}
	}
	/**
	 * 向左拓展宽度
	 * @param rect
	 * @param value
	 */
	public static void widenLeft(Rect rect,int value){
		if(rect!=null){
			rect.left-=value;
		}
	}
	/**
	 * 向右拓展宽度
	 * @param rect
	 * @param value
	 */
	public static void widenRight(Rect rect,int value){
		if(rect!=null){
			rect.right += value;
		}
	}
	/**
	 * 向下拓展高度
	 * @param rect
	 * @param value
	 */
	public static void widenBottom(Rect rect,int value){
		if(rect!=null){
			rect.bottom += value;
		}
	}
	/**
	 * 四个方向拓展
	 * @param rect
	 * @param value
	 */
	public static void widen(Rect rect,int value){
		if(rect!=null){
			widenLeft(rect,value);
			widenRight(rect,value);
			widenTop(rect,value);
			widenBottom(rect,value);
		}
	}
	/**
	 * 左边减少宽度
	 * @param rect
	 * @param value
	 */
	public static void cutLeft(Rect rect,int value){
		if(rect!=null){
			rect.left += value;
		}
	}
	/**
	 * 右边减少宽度
	 * @param rect
	 * @param value
	 */
	public static void cutRight(Rect rect,int value){
		if(rect!=null){
			rect.right -= value;
		}
	}
	/**
	 * 顶部减少高度
	 * @param rect
	 * @param value
	 */
	public static void cutTop(Rect rect,int value){
		if(rect!=null){
			rect.top += value;
		}
	}
	/**
	 * 底部减少高度
	 * @param rect
	 * @param value
	 */
	public static void cutBottom(Rect rect,int value){
		if(rect!=null){
			rect.bottom -= value;
		}
	}
	/**
	 * 四个方向减少
	 * @param rect
	 * @param value
	 */
	public static void cut(Rect rect,int value){
		if(rect!=null){
			cutLeft(rect,value);
			cutRight(rect,value);
			cutTop(rect,value);
			cutBottom(rect,value);
		}
	}
	/**
	 * 复制矩形
	 * @param src
	 * @param dest
	 */
	public static void copyRect(Rect src,Rect dest){
		if(src!=null&&dest!=null){
			dest.left = src.left;
			dest.right = src.right;
			dest.top = src.top;
			dest.bottom = src.bottom;
		}
	}
	/**
	 * 矩形移动到指定位置(以LEFT|TOP为参考点)
	 * @param rect
	 * @param tox
	 * @param toy
	 */
	public static void moveTo(Rect rect,int tox,int toy){
		if(rect==null){
			return;
		}
		int offx = tox-rect.left;
		int offy = toy-rect.top;
		moveBy(rect,offx,offy);
	}
	/**
	 * 矩形移动指定偏移(以LEFT|TOP为参考点)
	 * @param rect
	 * @param offx
	 * @param offy
	 */
	public static void moveBy(Rect rect,int offx,int offy){
		if(rect==null){
			return;
		}
		rect.set(rect.left+offx, rect.top+offy, rect.right+offx, rect.bottom+offy);
	}
	/**
	 * 检测两个矩形是否有重叠
	 * @param rect1
	 * @param rect2
	 * @return
	 */
	public static boolean overlap(Rect rect1,Rect rect2){
		if(rect1==null||rect2==null){
			return false;
		}
		int x1 = Math.max(rect1.left,rect2.left);
		int y1 = Math.max(rect1.top,rect2.top);
		int x2 = Math.min(rect1.right, rect2.right);
		int y2 = Math.min(rect1.bottom, rect2.bottom);
		return x1<x2&&y1<y2;
	}
	public static boolean overlap(Rect rect,MotionEvent e){
		if(rect==null||e==null){
			return false;
		}
		if(e.getX()>=rect.left&&e.getX()<=rect.right&&
				e.getY()>=rect.top&&e.getY()<=rect.bottom){
			return true;
		}
		return false;
	}
}
