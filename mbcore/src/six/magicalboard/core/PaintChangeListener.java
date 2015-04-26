package six.magicalboard.core;

public interface PaintChangeListener {
	
	public void onDrawModeChange(int mode);
	
	public void onPenColorChange(int color);
	
	public void onPenWidthChange(int width);
	
	public void onPenStyleChange(boolean b);
}
