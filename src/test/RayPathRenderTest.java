package test;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import optics_objects.LightSource;
import optics_objects.Material;
import optics_objects.PointLightSource;
import optics_objects.Prism;
import util.Vector2d;

public class RayPathRenderTest {
	LightSource l;
	ArrayList<Material> materials;
	
	@Before
	public void setUp() throws Exception {
		materials = new ArrayList<>();
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 10; j++) {
			Vector2d pos = new Vector2d(i*250, j*250);
			Material mat = new Prism(pos, 1000, 100, 1.5);
			materials.add(mat);
			}
		}
		
		l = new PointLightSource(new Vector2d(100*250/2, 10*250/2), 2000);	
	}

	@After
	public void tearDown() throws Exception {
		materials.clear();
		l = null;
	}

	@Test
	public void testRayCalc() {
		l.calculateRayPaths(materials);
	}

}
