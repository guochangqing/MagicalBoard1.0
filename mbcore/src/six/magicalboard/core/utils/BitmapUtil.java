package six.magicalboard.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;

public class BitmapUtil {
	public static final int UNCONSTRAINED = -1;
	public static Bitmap getBitmap4Path(String path,int swidth,int sheight){
		Bitmap mBitmap = null;
		if(path!=null){
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			InputStream is = null;
			try {
				is = new FileInputStream(path);
				mBitmap = BitmapFactory.decodeStream(is, null, opt);
			}catch(IOException e){
				e.printStackTrace();
			}catch(OutOfMemoryError ee){
				ee.printStackTrace();
				mBitmap = getBitmapByPath(path,
						getOptions(path), swidth, sheight);
			}finally{
				if(is!=null){
					try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return mBitmap;
	}
	/*
	 * 获得设置信息
	 */
	public static Options getOptions(String path) {
		Options options = new Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inJustDecodeBounds = true;// 只描边，不读取数据
		BitmapFactory.decodeFile(path, options);
		return options;
	}

	/**
	 * 获得图像
	 * 
	 * @param path
	 * @param options
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Bitmap getBitmapByPath(String path, Options options,
			int screenWidth, int screenHeight){
		Bitmap b = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				throw new FileNotFoundException();
			}
			FileInputStream in = null;
			in = new FileInputStream(file);
			if (options != null) {
				Rect r = getScreenRegion(screenWidth, screenHeight);
				int w = r.width();
				int h = r.height();
				int maxSize = w > h ? w : h;
				int inSimpleSize = computeSampleSize(options, maxSize, w * h);
				options.inSampleSize = inSimpleSize; // 设置缩放比例
				options.inJustDecodeBounds = false;
			}
			b = BitmapFactory.decodeStream(in, null, options);
			in.close();
		}catch(OutOfMemoryError e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	private static Rect getScreenRegion(int width, int height) {
		return new Rect(0, 0, width, height);
	}

	/**
	 * 获取需要进行缩放的比例，即options.inSampleSize
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 : (int) Math
				.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == UNCONSTRAINED) ? 128 : (int) Math
				.min(Math.floor(w / minSideLength),
						Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == UNCONSTRAINED)
				&& (minSideLength == UNCONSTRAINED)) {
			return 1;
		} else if (minSideLength == UNCONSTRAINED) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
	
}
