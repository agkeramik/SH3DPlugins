package importer.XML;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eteks.sweethome3d.model.CatalogPieceOfFurniture;
import com.eteks.sweethome3d.model.FurnitureCategory;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.UserPreferences;

public class RoomImporter {
	public void importRoom(String fileName,UserPreferences userPreferences,Home home){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(fileName));
			NodeList list=document.getElementsByTagName("Furniture");
			for(int i=0;i<list.getLength();++i){
				Element element=(Element) list.item(i);
				String catalogId=((Element)(element.getElementsByTagName("CatalogId").item(0))).getTextContent();
				HomePieceOfFurniture piece=getCatalogPieceWithId(catalogId, userPreferences);
				piece.setAngle(Float.parseFloat(element.getAttribute("rotation")));
				piece.setWidth(Float.valueOf(""+element.getAttribute("width")));
				piece.setHeight(Float.valueOf(""+element.getAttribute("height")));
				piece.setDepth(Float.valueOf(""+element.getAttribute("depth")));
				Element position=(Element)(element.getElementsByTagName("Position").item(0));
				piece.setX(Float.valueOf(""+position.getAttribute("posX")));
				piece.setY(Float.valueOf(""+position.getAttribute("posY")));
				home.addPieceOfFurniture(piece);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private HomePieceOfFurniture getCatalogPieceWithId(String id,UserPreferences userPreferences) {
		for (FurnitureCategory cat : userPreferences.getFurnitureCatalog()
				.getCategories()) {
			for (CatalogPieceOfFurniture cp : cat.getFurniture()) {
				if (cp.getId().equals(id)) {
					return new HomePieceOfFurniture(cp);
				}
			}
		}
		return null;
	}
}
