package six.magicalboard.core.texture;

import java.io.File;

import six.magicalboard.core.Constants;
import six.magicalboard.core.InfoBase;
import six.magicalboard.core.logic.StretchRender;
import six.magicalboard.core.utils.BitmapUtil;
import six.magicalboard.core.utils.Md5;
import six.magicalboard.core.utils.UIUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class ImageSDrawable extends SDrawable{

	private Bitmap image;
	
	private int resid = -1;
	
	private String resuri = null;
	
	int mode = 0;
	
	String path;
	
	public int getResid() {
		return resid;
	}

	public void setResid(int resid) {
		if(resid>-1){
			this.resid = resid;
			mode = 0;
		}
	}

	public String getResuri() {
		return resuri;
	}

	public void setResuri(String resuri) {
		if(null != resuri){
			this.resuri = resuri;
			mode = 1;
			path = Constants.DEFAULTSAVEPATH + Md5.encode(resuri);
		}
	}

	public Bitmap getForeImage() {
		return image;
	}

	public void setForeImage(Bitmap image) {
		this.image = image;
	}

	public ImageSDrawable(Context context,StretchRender render,Rect rect) {
		super(context,render,rect);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(null != image && !image.isRecycled()){
			UIUtil.drawBitmap(image, this.texturerect, canvas, mPaint, true);
		}
	}
	public void loadImage(){
		if(mode == 0){
			if(resid>-1){
				image = BitmapFactory.decodeResource(context.getResources(), resid);
			}else{
				image = BitmapFactory.decodeResource(context.getResources(), Constants.DEFAULTPICTUREID);
			}
		}else{
			if(null != resuri && resuri.length()>0){
				//本地
				if(isFileExist(resuri)){
					image = BitmapUtil.getBitmap4Path(resuri, texturerect.width(), texturerect.height());
				}else{
					image = BitmapFactory.decodeResource(context.getResources(), Constants.DEFAULTPICTUREID);
				}
			}else{
				image = BitmapFactory.decodeResource(context.getResources(), Constants.DEFAULTPICTUREID);
			}
		}
	}
	
	private boolean isFileExist(String path){
		return new File(path).exists();
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		if(null != image && !image.isRecycled()){
			image.recycle();
			image = null;
		}
	}

	@Override
	public void parse(InfoBase info) {
		// TODO Auto-generated method stub
		if(null != info){
			setResid(info.getInt("resid"));
			setResuri(info.getString("resuri"));
		}
	}

	@Override
	public InfoBase generate(int identifier) {
		// TODO Auto-generated method stub
		InfoBase info = new InfoBase();
		info.setType(Constants.DRAWABLEIMAGE);
		info.setIdentifier(identifier);
		if(mode == 0){
			info.putInt("resid", resid);
		}else{
			info.putString("resuri", resuri);
		}
		return info;
	}
	
}
