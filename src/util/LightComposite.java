package util;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class LightComposite implements Composite {

	@Override
	public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
		CompositeContext context = new LightCompositeContext(srcColorModel, dstColorModel);
		return context;
	}
	
	static class LightCompositeContext implements CompositeContext {

		private final ColorModel srcCM, dstCM;
		
		final static int PRECBITS = 22;
        final static int SRCALPHA = (int) ((1 << PRECBITS) * 0.667);
		
		public LightCompositeContext(ColorModel srcColorModel, ColorModel dstColorModel) {
			this.srcCM = srcColorModel;
			this.dstCM = dstColorModel;
		}
		
		//https://stackoverflow.com/questions/13629803/how-can-i-implement-java-awt-composite-efficiently
		@Override
		public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
			final int w = Math.min(src.getWidth(), dstIn.getWidth());
            final int h = Math.min(src.getHeight(), dstIn.getHeight());
            for (int y = 0; y < h; ++y) {
                for (int x = 0; x < w; ++x) {
                    int rgb1 = srcCM.getRGB(src.getDataElements(x, y, null));
                    int a1 = ((rgb1 >>> 24) * SRCALPHA) >> PRECBITS;
        			int r1 = (rgb1 >> 16) & 0xFF;
                    int g1 = (rgb1 >>  8) & 0xFF;
                    int b1 = (rgb1      ) & 0xFF;
                    
                    int rgb2 = dstCM.getRGB(dstIn.getDataElements(x, y, null));
                    int a2 = rgb2 >>> 24;
                    int r2 = (rgb2 >> 16) & 0xFF;
                    int g2 = (rgb2 >>  8) & 0xFF;
                    int b2 = (rgb2      ) & 0xFF;
                    r1 = r1 * a1 / 255;
                    g1 = g1 * a1 / 255;
                    b1 = b1 * a1 / 255;
                    // mix the two pixels
                    final int ta = a2 * (255 - a1);
                    r2 = r1 + (r2 * ta / (255*255));
                    g2 = g1 + (g2 * ta / (255*255));
                    b2 = b1 + (b2 * ta / (255*255));
                    r2 = Math.max(r1, r2);
                    g2 = Math.max(g1, g2);
                    b2 = Math.max(b1, b2);
                    
                    a2 = a1 + (ta / 255);
                    rgb2 = (a2 << 24) | (r2 << 16) | (g2 << 8) | b2; 
                    Object data = dstCM.getDataElements(rgb2, null);
                    dstOut.setDataElements(x, y, data);
                }
            }
		}

		@Override
		public void dispose() {
			
		}
		
	}
}
