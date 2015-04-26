package six.magicalboard.core;

import android.graphics.PointF;


public interface ActionChangeListener {
	//动作
	public void onPointGenerate(int status,PointF ...subentity); 
	//动作弹出
	public void onActionPop();
	//动作清空
	public void onActionClear();
	
	public void onAddPicture(int resid);
	
	public void onAddPicture(String uri);
}
