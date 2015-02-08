package engineplugin;

import com.eteks.sweethome3d.plugin.Plugin;
import com.eteks.sweethome3d.plugin.PluginAction;

public class LaunchEnginePlugin extends Plugin{

	@Override
	public PluginAction[] getActions() {
		// TODO Auto-generated method stub
		return new PluginAction[] {new LaunchEngine(getHome(),getUserPreferences())};
	}

}
