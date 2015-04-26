package six.magicalboard.core.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import six.magicalboard.core.ActionChangeListener;
import six.magicalboard.core.Constants;
import six.magicalboard.core.InfoBase;
import six.magicalboard.core.RenderPolicy;
import six.magicalboard.core.texture.ImageSDrawable;
import six.magicalboard.core.texture.SDrawable;
import six.magicalboard.core.texture.SketchSDrawable;
import six.magicalboard.core.utils.IUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.os.SystemClock;
import android.util.Log;

@SuppressLint("UseSparseArrays")
public class DataCenter{
	
	private ArrayList<Integer> lists = null;
	
	private HashMap<Integer,SDrawable> pools;
	
	private ActionChangeListener listener = null;
	
	private RenderPolicy policy;
	
	private StretchRender render;
	
	private Context context;
	
	public Context getContext() {
		return context;
	}
	public ArrayList<Integer> getLists() {
		return lists;
	}
	
	public HashMap<Integer, SDrawable> getPools() {
		return pools;
	}
	public StretchRender getRender() {
		return render;
	}
	public RenderPolicy getPolicy() {
		// TODO Auto-generated method stub
		return policy;
	}
	public ActionChangeListener getListener() {
		return listener;
	}

	public void setListener(ActionChangeListener listener) {
		this.listener = listener;
	}
	

	public DataCenter(Context context,StretchRender render){
		this.context = context;
		this.render = render;
		lists = new ArrayList<Integer>();
		pools = new HashMap<Integer,SDrawable>();
		policy = new RenderPolicy();
	}
	/**
	 * 关连完成，实例化第一个owner
	 */
	public void inflateFinish(){
		if(null != render){
//			if(null == lists){
//				lists = new ArrayList<SDrawable>();
//			}
//			lists.clear();
			
		}
	}
	long lasttime = 0;
	boolean sb = true;
	ArrayList<PointF> temp = new ArrayList<PointF>();
	/**
	 * 更新坐标
	 * */
	public synchronized void updateLocation(int status,PointF entity,boolean b,int identifier){
		if(status == 0 || status == 1 || status == 2){
			//按下
			SDrawable ae;
			if(status == 0){
				ae = new SketchSDrawable(context,render,render.getRenderRect());
				((SketchSDrawable)ae).setPaintByPolicy(policy);
				if(null != ae){
					addDrawable(identifier,ae);
				}
			}else{
				ae = getDrawableByIdentifier(identifier);
			}
			if(null != ae){
				((SketchSDrawable)ae).addPoint(entity);
				if(policy.getDrawmode() == Constants.DRAWMODEPEN){
					render.draw(ae);
				}else{
					render.draw(lists,pools);
				}
				if(null != listener && b){
					if(Constants.DEFAULTRASENDRATE<=0){
						listener.onPointGenerate(status, entity);
					}else{
						if(status == 0){
							sb = true;
							temp.clear();
							listener.onPointGenerate(status, entity);
						}else if(status == 1){
							if(sb){
								sb = false;
								lasttime = SystemClock.currentThreadTimeMillis();
								listener.onPointGenerate(status, entity);
							}else{
								long tme = SystemClock.currentThreadTimeMillis()- lasttime;
								if(tme >= Constants.DEFAULTRASENDRATE){
									lasttime = SystemClock.currentThreadTimeMillis();
									listener.onPointGenerate(status,temp.toArray(new PointF[temp.size()]));
									temp.clear();
								}else{
									temp.add(entity);
								}
							}
						}else if(status == 2){
							sb = true;
							temp.add(entity);
							listener.onPointGenerate(status,temp.toArray(new PointF[temp.size()]));
						}
					}
				}
			}
		}
	}
	public synchronized void addPicture(int resid,boolean b){
		checkRemove();
		SDrawable action = new ImageSDrawable(context,render, render.getRenderRect());
		((ImageSDrawable)action).setResid(resid);
		((ImageSDrawable)action).loadImage();
		insertDrawable(IUtil.getIdentifier(),action,0);
		render.draw(action);
		if(b && null != listener){
			listener.onAddPicture(resid);
		}
	}
	public synchronized void addPicture(String uri,boolean b){
		checkRemove();
		SDrawable action = new ImageSDrawable(context,render, render.getRenderRect());
		((ImageSDrawable)action).setResuri(uri);
		((ImageSDrawable)action).loadImage();
		insertDrawable(IUtil.getIdentifier(),action,0);
		render.draw(action);
		if(b && null != listener){
			listener.onAddPicture(uri);
		}
	}
	public synchronized void popAction(boolean b){
		removeTopDrawable();
		render.draw(lists,pools);
		if(b && null != listener){
			listener.onActionPop();
		}
	}
	public synchronized void clearActions(boolean b){
		clearAllDrawable();
		render.draw();
		if(b && null != listener){
			listener.onActionClear();
		}
	}
	public synchronized void writeAllDrawable(ArrayList<InfoBase> arr){
		clearAllDrawable();
		if(null != arr && arr.size()>0){
			for(InfoBase info:arr){
				if(info.getType() == Constants.DRAWABLEIMAGE){
					checkRemove();
					SDrawable action = new ImageSDrawable(context,render, render.getRenderRect());
					action.parse(info);
					((ImageSDrawable)action).loadImage();
					insertDrawable(info.getIdentifier(),action,0);
				}else if(info.getType() == Constants.DRAWABLESKETCH){
					SketchSDrawable ae = new SketchSDrawable(context,render,render.getRenderRect());
					ae.parse(info);
					policy.setDrawmode(ae.getDrawmode(), false);
					policy.setPenColor(ae.getPenColor(), false);
					policy.setPenWidth(ae.getPenWidth(), false);
					policy.setPenStyle(ae.isPenStyle(), false);
					ae.setPaintByPolicy(policy);
					addDrawable(info.getIdentifier(),ae);
				}
			}
		}
	}
	public synchronized ArrayList<InfoBase> readAllDrawable(){
		if(null != lists && lists.size()>0){
			ArrayList<InfoBase> arrs = new ArrayList<InfoBase>();
			for(int key:lists){
				SDrawable ae = pools.get(key);
				if(null != ae){
					arrs.add(ae.generate(key));
				}
			}
			return arrs;
		}
		return null;
	}
	public synchronized void requestPaint(){
		
		render.draw(lists, pools);
	}
	//******************************************************************************************************************************
	private void clearAllDrawable(){
		Iterator<Integer> it = pools.keySet().iterator();
		while(it.hasNext()){
			pools.get(it.next()).destroy();
		}
		lists.clear();
		pools.clear();
	}
	private void addDrawable(int identifier,SDrawable action){
		if(pools.containsKey(identifier)){
			lists.remove(identifier);
			SDrawable d = pools.remove(identifier);
			if(null != d){
				d.destroy();
			}
		}
		lists.add(identifier);
		pools.put(identifier, action);
	}
	private void insertDrawable(int identifier,SDrawable action,int index){
		if(pools.containsKey(identifier)){
			lists.remove(identifier);
			SDrawable d = pools.remove(identifier);
			if(null != d){
				d.destroy();
			}
		}
		pools.put(identifier, action);
		lists.add(index, identifier);
	}
	private SDrawable getDrawableByIdentifier(int identifier){
		if(pools.containsKey(identifier)){
			return pools.get(identifier);
		}
		return null;
	}
	private boolean removeTopDrawable(){
		if(lists.size()<=0){
			return false;
		}
		SDrawable d = pools.remove(lists.remove(lists.size()-1));
		if(null != d){
			d.destroy();
		}
		return true;
	}
	private void checkRemove(){
		if(lists.size()>0){
			SDrawable d = getDrawableByIdentifier(lists.get(0));
			if(null != d && d instanceof ImageSDrawable){
				SDrawable dd = pools.remove(lists.remove(0));
				if(null != dd){
					dd.destroy();
				}
			}
		}
	}
	protected void traverse(){
		if(Constants.DEBUG){
			for(int i:lists){
				SDrawable d = pools.get(i);
				Log.e("", "类型::"+d);
			}
		}
	}
}
