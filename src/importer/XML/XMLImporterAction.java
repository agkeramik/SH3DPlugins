package importer.XML;

import javax.swing.JFrame;

import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.UserPreferences;
import com.eteks.sweethome3d.plugin.PluginAction;
import com.eteks.sweethome3d.viewcontroller.HomeController;

public class XMLImporterAction extends PluginAction {

	Home home;
	UserPreferences userPreferences;
	HomeController homeController;

	public XMLImporterAction(Home home, UserPreferences userPreferences,
			HomeController homeController) {
		this.home = home;
		this.userPreferences = userPreferences;
		this.homeController = homeController;
		putPropertyValue(Property.NAME, "Import Room");
		putPropertyValue(Property.MENU, "Tools");
		setEnabled(true);

	}

	@Override
	public void execute() {
		// Create a frame in order to take the fileName		
		JFrame frame = new GetStringDialogView(home,userPreferences);
		frame.setVisible(true);
	}

}
