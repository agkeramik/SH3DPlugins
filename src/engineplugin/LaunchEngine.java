package engineplugin;

import java.net.URL;
import java.net.URLClassLoader;


import ModelBlock.ModelBlock;
import ModelBlock.ModelImporter;

import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.UserPreferences;
import com.eteks.sweethome3d.plugin.PluginAction;

import engine.Engine01;
import engine.FitnessMaximizationBlockPlacement;

public class LaunchEngine extends PluginAction {

	Home home;
	UserPreferences userPreferences;

	public LaunchEngine(Home home, UserPreferences userPreferences) {
		this.home = home;
		this.userPreferences = userPreferences;
		putPropertyValue(Property.NAME, "Furnish");
		putPropertyValue(Property.MENU, "Tools");
		setEnabled(true);

	}

	@Override
	public void execute() {
		new Engine01(userPreferences, home);
	}

}
