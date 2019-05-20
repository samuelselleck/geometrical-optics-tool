package gui.optics_object_creators;

import gui.OpticsView;

public abstract class LightSourceCreator extends OpticsObjectCreator {

	public LightSourceCreator(OpticsView view) {
		super(view);
		this.addElement(new GraphElement());
	}

}
