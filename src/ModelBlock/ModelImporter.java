package ModelBlock;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import com.eteks.sweethome3d.model.CatalogPieceOfFurniture;
import com.eteks.sweethome3d.model.FurnitureCategory;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.UserPreferences;



public class ModelImporter {
	UserPreferences userPreferences;
	Home home;

	public ModelImporter(UserPreferences userPreferences, Home home) {
		this.userPreferences = userPreferences;
		this.home = home;
	}

	public ModelBlock importModelBlock(String fileName) {
		ModelBlock modelBlock = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(fileName));

			Element modelBlockElement = (Element) (document
					.getElementsByTagName("ModelBlock").item(0));
			float mbWidth = Float.parseFloat(modelBlockElement
					.getAttribute("width"));
			float mbDepth = Float.parseFloat(modelBlockElement
					.getAttribute("depth"));
			try {
				modelBlock = new ModelBlock(mbWidth, mbDepth, home);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			NodeList furnituresNodes = document
					.getElementsByTagName("Furniture");
			for (int i = 0; i < furnituresNodes.getLength(); ++i) {
				Element furnitureElement = (Element) furnituresNodes.item(i);
				float width = Float.parseFloat(furnitureElement
						.getAttribute("width"));
				float depth = Float.parseFloat(furnitureElement
						.getAttribute("depth"));
				float height = Float.parseFloat(furnitureElement
						.getAttribute("height"));
				float rotation = Float.parseFloat(furnitureElement
						.getAttribute("rotation"));
				Element positionElementOfFurniture = (Element) (furnitureElement
						.getElementsByTagName("Position").item(0));
				float posX = Float.parseFloat(positionElementOfFurniture
						.getAttribute("posX"));
				float posY = Float.parseFloat(positionElementOfFurniture
						.getAttribute("posY"));
				Element catalogIdElement = (Element) (furnitureElement
						.getElementsByTagName("CatalogId").item(0));
				String catalogStringId = catalogIdElement.getTextContent();
				HomePieceOfFurniture homePiece = getCatalogPieceWithId(catalogStringId);
//				System.out.println(catalogStringId + "\nposX=" + posX
//						+ "\nposY=" + posY + "\nrotation=" + rotation
//						+ "\nDepth=" + depth+"\nwidth="+width);
				if (homePiece == null)
					continue;
				homePiece.setWidth(width);
				homePiece.setDepth(depth);
				homePiece.setHeight(height);
				homePiece.setAngle(rotation);
				homePiece.setX(posX);
				homePiece.setY(posY);

				modelBlock.addHomePieceOfFurniture(homePiece);
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelBlock;
	}

	private HomePieceOfFurniture getCatalogPieceWithId(String id) {
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
