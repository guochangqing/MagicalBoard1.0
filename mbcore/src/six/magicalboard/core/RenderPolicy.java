package six.magicalboard.core;


public class RenderPolicy{
	
	private int drawmode = Constants.DRAWMODEPEN;
	
	public int getDrawmode() {
		return drawmode;
	}

	public void setDrawmode(int drawmode,boolean b) {
		this.drawmode = drawmode;
		if(b && null != listener){
			listener.onDrawModeChange(drawmode);
		}
	}
	private PaintChangeListener listener;
	
	public void setListener(PaintChangeListener listener) {
		this.listener = listener;
	}
	//画笔颜色
	private int penColor = Constants.DEFAULTPAINTCOLOR;

	public int getPenColor() {
		return penColor;
	}
	
	public void setPenColor(int penColor,boolean b) {
		this.penColor = penColor;
		if(b && null != listener){
			listener.onPenColorChange(penColor);
		}
	}
	//画笔宽度
	private int penWidth = Constants.DEFAULTPAINTWIDTH;

	public int getPenWidth() {
		return penWidth;
	}

	public void setPenWidth(int penWidth,boolean b) {
		this.penWidth = penWidth;
		if(b && null != listener){
			listener.onPenWidthChange(penWidth);
		}
	}
	//画笔样式
	private boolean penStyle = Constants.DEFAULTPAINTSTYLE;

	public boolean isPenStyle() {
		return penStyle;
	}

	public void setPenStyle(boolean penStyle,boolean b) {
		this.penStyle = penStyle;
		if(b && null != listener){
			listener.onPenStyleChange(penStyle);
		}
	}
}
