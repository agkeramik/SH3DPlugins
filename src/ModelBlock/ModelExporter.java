package ModelBlock;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.eteks.sweethome3d.model.HomePieceOfFurniture;

public class ModelExporter {
	private FileWriter stream;

	public void ExportModel(List<HomePieceOfFurniture> furnitureList,
			String fileName) {
		try {
			stream = new FileWriter(fileName);

		} catch (IOException e) {
			// TODO show the errors
			e.printStackTrace();
			return;
		}
		float xmin = Float.MAX_VALUE, ymin = Float.MAX_VALUE, xmax = Float.MIN_VALUE, ymax = Float.MIN_VALUE;
		for (HomePieceOfFurniture piece : furnitureList) {
			if (xmin > piece.getX() - 0.5f * piece.getWidth())
				xmin = piece.getX() - 0.5f * piece.getWidth();
			if (ymin > piece.getY() - 0.5f * piece.getDepth())
				ymin = piece.getY() - 0.5f * piece.getDepth();
			if (xmax < piece.getX() + 0.5f * piece.getWidth())
				xmax = piece.getX() + 0.5f * piece.getWidth();
			if (ymax < piece.getY() + 0.5f * piece.getDepth())
				ymax = piece.getY() + 0.5f * piece.getDepth();
		}

		float shiftX = -(xmin + xmax) * 0.5F, shiftY = -(ymin + ymax) * 0.5F;
		try {
			stream.write("<ModelBlock width=\"" + (xmax - xmin) + "\" depth=\""
					+ (ymax - ymin) + "\">");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (HomePieceOfFurniture piece : furnitureList) {
			write(piece, shiftX, shiftY);
		}
		try {
			stream.write("</ModelBlock>");
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		stream = null;
	}

	private void write(HomePieceOfFurniture piece, float shiftX, float shiftY) {
		try {
			stream.write("<Furniture width=\"" + piece.getWidth()
					+ "\" depth=\"" + piece.getDepth() + "\" height=\"" + piece.getHeight()
					+ "\" rotation=\""+ piece.getAngle() + "\">");
			stream.write("<CatalogId>" + piece.getCatalogId() + "</CatalogId>");
			stream.write("<Name>" + piece.getName() + "</Name>");
			stream.write("<Position posX=\"" + (piece.getX() + shiftX)
					+ "\" posY=\"" + (piece.getY() + shiftY) + "\"/>");
			stream.write("</Furniture>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
