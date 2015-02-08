package exporter.XML;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.Wall;

public class RoomExporter {
	public void export(String fileName, Home home) {
		FileWriter stream;
		try {			
			stream = new FileWriter(fileName);
			stream.write("<Room>\n");
			stream.write("<Furnitures>\n");
			for (HomePieceOfFurniture piece : home.getFurniture())
				writeFurniture(stream, piece);
			stream.write("</Furnitures>\n");
			stream.write("<Walls>\n");
			for (Wall wall : home.getWalls())
				writeWall(stream, wall);
			stream.write("</Walls>\n");
			stream.write("</Room>\n");
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeFurniture(FileWriter stream, HomePieceOfFurniture piece) {
		if (piece.isDoorOrWindow()) {
			try {
				stream.write("<DoorOrWindow width=\"" + piece.getWidth()
						+ "\" depth=\"" + piece.getDepth() + "\" height=\""
						+ piece.getHeight() + "\" rotation=\""
						+ piece.getAngle() + "\">\n");
				stream.write("<CatalogId>" + piece.getCatalogId()
						+ "</CatalogId>\n");
				stream.write("<Name>" + piece.getName() + "</Name>\n");
				stream.write("<Position posX=\"" + piece.getX() + "\" posY=\""
						+ piece.getY() + "\"/>\n");
				stream.write("</DoorOrWindow>\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				if (piece.getCatalogId().equals("Renouzate#Table1x1")
						|| piece.getCatalogId().equals("Renouzate#Table2x2")
						|| piece.getCatalogId().equals("Renouzate#Table3x3")) 
					piece.setAngle((int) (piece.getAngle()*180./Math.PI) % 90);
				stream.write("<Furniture width=\"" + piece.getWidth()
						+ "\" depth=\"" + piece.getDepth() + "\" height=\""
						+ piece.getHeight() + "\" rotation=\""
						+ piece.getAngle() + "\">\n");
				stream.write("<CatalogId>" + piece.getCatalogId()
						+ "</CatalogId>\n");
				stream.write("<Name>" + piece.getName() + "</Name>\n");
				stream.write("<Position posX=\"" + piece.getX() + "\" posY=\""
						+ piece.getY() + "\"/>\n");
				stream.write("</Furniture>\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeWall(FileWriter stream, Wall wall) {
		try {
			stream.write("<Wall>\n");
			stream.write("<Start x=\"" + wall.getXStart() + "\" y=\""
					+ wall.getYStart() + "\"/>");
			stream.write("<End x=\"" + wall.getXEnd() + "\" y=\""
					+ wall.getYEnd() + "\"/>");
			stream.write("</Wall>\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
