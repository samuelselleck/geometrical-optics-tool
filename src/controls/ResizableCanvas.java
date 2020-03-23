package controls;

import javafx.scene.canvas.Canvas;

public class ResizableCanvas extends Canvas {

	public ResizableCanvas() {
		super(10, 10);
	}
		@Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double maxHeight(double width) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double maxWidth(double height) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double minWidth(double height) {
        return 1D;
    }

    @Override
    public double minHeight(double width) {
        return 1D;
    }

    @Override
    public void resize(double width, double height) {
    	if(!widthProperty().isBound()) {
    		this.setWidth(width);
    	}
    	if(!heightProperty().isBound()) {
    		this.setHeight(height);
    	}
    }
}
