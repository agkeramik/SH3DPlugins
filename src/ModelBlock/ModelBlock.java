package ModelBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import VectorUtils.Vec3;

import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;

public class ModelBlock {
	float width, depth, X, Y, angle;
	List<HomePieceOfFurniture> entryPoints;
	List<HomePieceOfFurniture> focalPoints;
	List<HomePieceOfFurniture> furnitures;

	List<HomePieceOfFurniture> entryPointsHome;
	List<HomePieceOfFurniture> focalPointsHome;
	List<HomePieceOfFurniture> furnituresHome;

	Home home;

	public ModelBlock(float width, float depth, Home home) throws Exception {
		if (home == null)
			throw new NullPointerException("Home is NULL");
		this.width = width;
		this.depth = depth;
		this.home = home;
		entryPoints = new ArrayList<HomePieceOfFurniture>();
		focalPoints = new ArrayList<HomePieceOfFurniture>();
		furnitures = new ArrayList<HomePieceOfFurniture>();
		entryPointsHome = new ArrayList<HomePieceOfFurniture>();
		focalPointsHome = new ArrayList<HomePieceOfFurniture>();
		furnituresHome = new ArrayList<HomePieceOfFurniture>();
	}

	// TODO we are not guaranteeing that the width and the height are correct
	public void addHomePieceOfFurniture(HomePieceOfFurniture piece) {
		if (piece == null)
			return;
		HomePieceOfFurniture p = new HomePieceOfFurniture(piece);
		HomePieceOfFurniture ph = new HomePieceOfFurniture(piece);
		ph.setX(piece.getX() + X);
		ph.setY(piece.getY() + Y);
		ph.setAngle(ph.getAngle() + angle);

		if (piece.getName().equals("Entry Point")) {
			entryPoints.add(p);
			entryPointsHome.add(ph);

		} else if (piece.getName().equals("Focal Point")) {
			focalPoints.add(p);
			focalPointsHome.add(ph);
		} else {
			furnitures.add(p);
			furnituresHome.add(ph);
		}
		// Now we are drawing the entry points and focal points in the home
		// then we are not going to add them to the home
		home.addPieceOfFurniture(ph);
	}

	private void updateAbsoluteFurnitures(List<HomePieceOfFurniture> fixedList,
			List<HomePieceOfFurniture> homeList) {
		//if this is wrong then getFurniturewithShifts should be rechecked
		for (int i = 0; i < homeList.size(); ++i) {
			HomePieceOfFurniture piece = homeList.get(i);
			HomePieceOfFurniture fixedPiece = fixedList.get(i);
			Vec3 newPos=(new Vec3(fixedPiece.getX(), fixedPiece.getY())).rotateVector2D(angle);
			piece.setX(newPos.x + X);
			piece.setY(newPos.y + Y);
			piece.setAngle(fixedPiece.getAngle() + angle);
		}
	}

	public float getWidth() {
		return width;
	}

	public float getDepth() {
		return depth;
	}

	public float getX() {
		return X;
	}

	public void setX(float x) {
		X = x;
		updateAbsoluteFurnitures(focalPoints, focalPointsHome);
		updateAbsoluteFurnitures(entryPoints, entryPointsHome);
		updateAbsoluteFurnitures(furnitures, furnituresHome);
	}

	public float getY() {
		return Y;
	}

	public void setY(float y) {
		Y = y;
		updateAbsoluteFurnitures(focalPoints, focalPointsHome);
		updateAbsoluteFurnitures(entryPoints, entryPointsHome);
		updateAbsoluteFurnitures(furnitures, furnituresHome);
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
		updateAbsoluteFurnitures(focalPoints, focalPointsHome);
		updateAbsoluteFurnitures(entryPoints, entryPointsHome);
		updateAbsoluteFurnitures(furnitures, furnituresHome);
	}

	public List<HomePieceOfFurniture> getEntryPoints() {
		return Collections.unmodifiableList(entryPoints);
	}

	public List<HomePieceOfFurniture> getFocalPoints() {
		return Collections.unmodifiableList(focalPoints);
	}

	public List<HomePieceOfFurniture> getFurnitures() {
		return Collections.unmodifiableList(furnitures);
	}

	public List<HomePieceOfFurniture> getEntryPointsHome() {
		return Collections.unmodifiableList(entryPointsHome);
	}

	public List<HomePieceOfFurniture> getFocalPointsHome() {
		return Collections.unmodifiableList(focalPointsHome);
	}

	public List<HomePieceOfFurniture> getFurnituresHome() {
		return Collections.unmodifiableList(furnituresHome);
	}

	public List<HomePieceOfFurniture> getEntryPointsWithShifts(float shiftX,
			float shiftY, float angle) {
		return getFurnitureWithShifts(entryPoints, shiftX, shiftY, angle);
	}
	
	

	private List<HomePieceOfFurniture> getFurnitureWithShifts(
			List<HomePieceOfFurniture> furnitureList, float shiftX,
			float shiftY, float angle) {
		List<HomePieceOfFurniture> list = new ArrayList<>();
		for (HomePieceOfFurniture piece : furnitureList) {
			list.add(new HomePieceOfFurniture(piece));
		}
		for (int i = 0; i < list.size(); ++i) {
			HomePieceOfFurniture piece = list.get(i);
			HomePieceOfFurniture fixedPiece = furnitureList.get(i);
			Vec3 newPos=(new Vec3(fixedPiece.getX(), fixedPiece.getY())).rotateVector2D(angle);
			piece.setX(newPos.x + shiftX);
			piece.setY(newPos.y + shiftY);
			piece.setAngle(fixedPiece.getAngle() + angle);
		}
		return list;
	}

	public void setVisibility(boolean visibility) {
		for (HomePieceOfFurniture piece : entryPointsHome)
			piece.setVisible(visibility);
		for (HomePieceOfFurniture piece : focalPointsHome)
			piece.setVisible(visibility);
		for (HomePieceOfFurniture piece : furnituresHome)
			piece.setVisible(visibility);
	}
}
