package settings;

import java.util.HashMap;
import java.util.Map;

import optics_objects.lights.*;
import optics_objects.materials.*;
import optics_objects.templates.OpticsObject;

public class SettingsMap {
	private static Map<Class<?>, SettingsIdWrapper> map;
	
	public static void initSettingsMap(){
		map = new HashMap<Class<?>, SettingsIdWrapper>();
		
		add(Prism.class,LENS_SETTINGS,0);
		add(ConvexLens.class,LENS_SETTINGS,1);
		add(OptimalConvexLens.class,LENS_SETTINGS,2);
		add(ConcaveLens.class, LENS_SETTINGS, 3);
		add(SettingsMap.class, LENS_SETTINGS,4); //Hack, anv�nder bara en godtycklig klass
		add(RectangleLens.class, LENS_SETTINGS,5);
		
		add(PointLightSource.class, LIGHT_SETTINGS,0);
		add(ConeLightSource.class, LIGHT_SETTINGS,1);
		add(BeamLightSource.class, LIGHT_SETTINGS,2);
		
		add(FlatMirror.class, MIRROR_SETTINGS,0);
		add(RoundedMirror.class, MIRROR_SETTINGS,1);
		
		add(RectangleWall.class, WALL_SETTINGS,0);

		add(DiffractionGrating.class, DIFFRACTION_GRATING_SETTINGS, 0);
		
	}
	
	private static void add(Class c, int setting, int creator) {
		map.put(c, new SettingsIdWrapper(setting, creator));
	}
	
	private static SettingsIdWrapper getIdWrapper(OpticsObject obj) {
		
		//Fult undantag f�r bollar :(
		if(obj instanceof Prism) {
			if(((Prism)obj).getPointCount()>20){
				return map.get(SettingsMap.class);
			}
		}
		
		return map.get(obj.getClass());
	}
	
	public static int getSettingId(OpticsObject obj) {
		return getIdWrapper(obj).getSettingId();
	}
	
	public static int getCreatorId(OpticsObject obj) {
		return getIdWrapper(obj).getCreatorId();
	}

	public static final int LENS_SETTINGS = 0;
	public static final int LIGHT_SETTINGS = 1;
	public static final int MIRROR_SETTINGS = 2;
	public static final int WALL_SETTINGS = 3;
	public static final int DIFFRACTION_GRATING_SETTINGS = 4;
	
	private static class SettingsIdWrapper{
		private int settingId, creatorId;
		
		public SettingsIdWrapper(int setting, int creator) {
			settingId = setting;
			creatorId = creator;
		}
		
		public int getSettingId() {
			return settingId;
		}
		
		public int getCreatorId() {
			return creatorId;
		}
	}
}
