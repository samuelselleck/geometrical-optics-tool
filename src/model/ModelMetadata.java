package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelMetadata implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<LensMaterial> refractionMaterials;
	private int ambientMaterialID;
	
	public ModelMetadata() {
		refractionMaterials = new ArrayList<>();
		
		refractionMaterials.add(new LensMaterial("Glass",
				new double[] {0.8, 0.8, 0.9},
				1.03961212, 0.231792344, 1.01046945, 6.00069867e-3, 2.00179144e-2, 103.560653));
		refractionMaterials.add(new LensMaterial("Saphire",
				new double[] {1, 0.2, 0.5},
				1.43134930, 0.65054713, 5.3414021, 5.2799261e-3, 1.42382647e-2, 325.017834));
		refractionMaterials.add(new LensMaterial("Constant (Glass-like, n = 1.5)",
				new double[] {1, 1, 1},
				1.5*1.5 - 1, 0, 0, 0, 0, 0));
		refractionMaterials.add(new LensMaterial("Constant (Air-like, n = 1)",
				new double[] {1, 1, 1},
				1*1 - 1, 0, 0, 0, 0, 0));
		refractionMaterials.add(new LensMaterial("Constant (Water-like, n = 1.33)",
				new double[] {1, 1, 1},
				1.33*1.33 - 1, 0, 0, 0, 0, 0));
		
		ambientMaterialID = 3;
	}

	public LensMaterial getLensMaterial(int i) {
		return refractionMaterials.get(i);
	}

	public void addLensMaterial(LensMaterial newLensMaterial) {
		refractionMaterials.add(newLensMaterial);
	}

	public List<LensMaterial> getLensMaterials() {
		return refractionMaterials;
	}

}
