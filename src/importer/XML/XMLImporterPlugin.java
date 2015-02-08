package importer.XML;

import com.eteks.sweethome3d.plugin.Plugin;
import com.eteks.sweethome3d.plugin.PluginAction;

public class XMLImporterPlugin extends Plugin{

	@Override
	public PluginAction[] getActions() {
		// TODO Auto-generated method stub
		return new PluginAction[] {new XMLImporterAction(getHome(),getUserPreferences(),getHomeController())};
	}

}
