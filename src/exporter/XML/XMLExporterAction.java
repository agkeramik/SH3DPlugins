package exporter.XML;

import javax.swing.JFrame;


import UI.GetStringDialogView;

import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.UserPreferences;
import com.eteks.sweethome3d.plugin.PluginAction;
import com.eteks.sweethome3d.viewcontroller.HomeController;

public class XMLExporterAction extends PluginAction {

	Home home;
	UserPreferences userPreferences;
	HomeController homeController;

	public XMLExporterAction(Home home, UserPreferences userPreferences,
			HomeController homeController) {
		this.home = home;
		this.userPreferences = userPreferences;
		this.homeController = homeController;
		putPropertyValue(Property.NAME, "Export Model");
		putPropertyValue(Property.MENU, "Tools");
		setEnabled(true);

	}

	@Override
	public void execute() {
		// Create a frame in order to take the fileName
		String homeName=home.getName();
		String fileName="";
		//For Linux only
		for (int i=homeName.length()-1;i>=0;--i){
			if (homeName.charAt(i)=='/'){
				fileName=homeName.substring(i+1,homeName.length()-5);
				fileName+=".xml";
				break;
			}
		}
		RoomExporter re=new RoomExporter();
		re.export(fileName, home);
		
//		JFrame frame = new GetStringDialogView(home);
//		frame.setVisible(true);
	}

}
