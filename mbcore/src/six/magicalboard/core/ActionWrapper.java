package six.magicalboard.core;

import java.util.ArrayList;

import six.magicalboard.core.logic.DataCenter;
import six.magicalboard.core.utils.IUtil;
import android.graphics.PointF;

public class ActionWrapper {

	private DataCenter datacenter;
	
	public ActionWrapper(DataCenter datacenter){
		this.datacenter = datacenter;
	}
	private boolean checkDatacenter(){
		return null != datacenter && null != datacenter.getPolicy();
	}
	/**
	 * 获取当前绘制策略
	 * */
	public RenderPolicy getRenderPolicy(){
		if(null != datacenter){
			return datacenter.getPolicy();
		}
		return null;
	}
	/**
	 * @param b 主动调用为true，接口调用为false
	 * @param pcolor
	 * */
	public void setPenColor(int pcolor,boolean b){
		if(checkDatacenter()){
			datacenter.getPolicy().setPenColor(pcolor,b);
		}
	}
	/**
	 * @param b 主动调用为true，接口调用为false
	 * @param width
	 * */
	public void setPenWidth(int width,boolean b){
		if(checkDatacenter()){
			datacenter.getPolicy().setPenWidth(width, b);
		}
	}
	/**
	 * @param b 主动调用为true，接口调用为false
	 * @param style
	 * */
	public void setPenStyle(boolean style,boolean b){
		if(checkDatacenter()){
			datacenter.getPolicy().setPenStyle(style, b);
		}
	}
	public void setDrawMode(int mode,boolean b){
		if(checkDatacenter()){
			datacenter.getPolicy().setDrawmode(mode,b);
		}
	}
	/**
	 * @param b 主动调用为true，接口调用为false
	 * */
	public void popAction(boolean b){
		if(checkDatacenter()){
			datacenter.popAction(b);
		}
	}
	int identifier = 0;
	public void pushPoint(int status,PointF subentity){
		if(checkDatacenter()){
			if(status == 0){
				identifier = IUtil.getIdentifier();
			}
			datacenter.updateLocation(status, subentity, false,identifier);
		}
	}
	public void pushPointArray(int status,PointF ...subentity){
		if(checkDatacenter()){
			if(subentity!=null && subentity.length>0){
				if(status == 0){
					identifier = IUtil.getIdentifier();
				}
				for(int i=0;i<subentity.length;i++){
					datacenter.updateLocation(status, subentity[i], false,identifier);
				}
			}
		}
	}
	/**
	 * @param b 主动调用为true，接口调用为false
	 * */
	public void clearActions(boolean b){
		if(checkDatacenter()){
			datacenter.clearActions(b);
		}
	}
	/**
	 * @param b 主动调用为true，接口调用为false
	 * @param resid 
	 * */
	public void addPicture(int resid,boolean b){
		if(checkDatacenter()){
			datacenter.addPicture(resid,b);
		}
	}
	/**
	 * @param b 主动调用为true，接口调用为false
	 * @param uri 
	 * */
	public void addPicture(String uri,boolean b){
		if(checkDatacenter()){
			datacenter.addPicture(uri,b);
		}
	}
	/**
	 * 
	 * */
	public void writeAllDrawable(ArrayList<InfoBase> arr){
		if(checkDatacenter()){
			datacenter.writeAllDrawable(arr);
		}
	}
	public ArrayList<InfoBase> readAllDrawable(){
		if(checkDatacenter()){
			return datacenter.readAllDrawable();
		}
		return null;
	}
	/**
	 * 重新绘制
	 * */
	public void requestPaint(){
		if(checkDatacenter()){
			datacenter.requestPaint();
		}
	}
}
