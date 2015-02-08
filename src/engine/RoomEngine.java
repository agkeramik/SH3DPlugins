package engine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import VectorUtils.Vec3;

import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomeDoorOrWindow;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.Room;

public class RoomEngine {
	private Room room;
	private List<HomeDoorOrWindow> windows;
	private Map<HomeDoorOrWindow, Integer> doors;
	private float[] wallAngles;

	public RoomEngine(Home home, int roomIndex) throws Exception {
		room = home.getRooms().get(roomIndex);
		windows = new ArrayList<>();
		doors = new HashMap<>();
		putPointsInPositiveDirection();
		for (HomePieceOfFurniture piece : home.getFurniture()) {
			// if the piece is a door or window check if it corresponds to the
			// room
			if (piece.isDoorOrWindow()) {
				HomeDoorOrWindow hdw = (HomeDoorOrWindow) (piece);
				int wallNb = isForRoom(hdw);
				if (wallNb != -1) {
					if (piece.getName().contains("Door")
							|| piece.getName().contains("door")) {
						doors.put(hdw, wallNb);
						piece.setColor(16711680);
					} else {
						windows.add(hdw);
						piece.setColor(65280);
					}
				}
			}
		}
	}

	public List<HomeDoorOrWindow> getWindows() {
		return windows;
	}

	public Map<HomeDoorOrWindow, Integer> getDoors() {
		return doors;
	}

	public Room getRoom() {
		return room;
	}

	// TODO should be removed from this class to another class
	private void putPointsInPositiveDirection() {
		int nbPoints = room.getPointCount();
		float points[][] = room.getPoints();
		float xmax = Float.MIN_VALUE;

		// find the rightMostLowest Vertex
		for (int i = 0; i < nbPoints; ++i) {
			if (points[i][0] > xmax) {
				xmax = points[i][0];
			}
		}
		int rightMostLowestIndex = 0;
		float ymin = Float.MAX_VALUE;
		for (int i = 0; i < nbPoints; ++i) {
			if (xmax == points[i][0]) {
				if (points[i][1] < ymin) {
					ymin = points[i][1];
					rightMostLowestIndex = i;
				}
			}
		}
		int prev = (rightMostLowestIndex - 1 + nbPoints) % nbPoints;
		int next = (rightMostLowestIndex + 1) % nbPoints;
		Vec3 v1 = new Vec3(points[prev][0] - points[rightMostLowestIndex][0],
				points[prev][1] - points[rightMostLowestIndex][1]);
		Vec3 v2 = new Vec3(points[next][0] - points[rightMostLowestIndex][0],
				points[next][1] - points[rightMostLowestIndex][1]);
		if (Vec3.crossProduct(v1, v2).z > 0) {
			float[][] pos = new float[room.getPointCount()][2];
			for (int i = 0; i < nbPoints; ++i) {
				pos[i][0] = points[nbPoints - 1 - i][0];
				pos[i][1] = points[nbPoints - 1 - i][1];
			}
			room.setPoints(pos);
		}

	}

	private int isForRoom(HomeDoorOrWindow piece) {
		float[][] points = room.getPoints();
		int nbPoints = room.getPointCount();
		float pieceAngle = piece.getAngle();
		for (int i = 0; i < nbPoints; ++i) {
			Vec3 pieceVec = new Vec3(Math.cos(pieceAngle), Math.sin(pieceAngle));
			Vec3 wallVec = new Vec3(points[i][0]
					- points[(i + 1) % nbPoints][0], points[i][1]
					- points[(i + 1) % nbPoints][1]);
			if (Vec3.crossProduct(pieceVec, wallVec).norm() < 0.05 * wallVec
					.norm()) {
				if ((new Vec3(piece.getX() - points[i][0], piece.getY()
						- points[i][1])).norm()
						+ (new Vec3(piece.getX()
								- points[(i + 1) % nbPoints][0], piece.getY()
								- points[(i + 1) % nbPoints][1])).norm() < wallVec
						.norm() + 0.5 * piece.getDepth())
					return i;
			}
		}
		return -1;
	}

	private void printInfo() {
		float[][] points = room.getPoints();
		for (int i = 0; i < room.getPointCount(); ++i) {
			System.out.println("x=" + points[i][0] + "y=" + points[i][1]);
		}
		for (int i = 0; i < room.getPointCount(); ++i) {
			System.out.println(wallAngles[i]);
		}
	}

	// TODO this function can be removed if wall angles are not required in the
	// AI algorithm
	private void findWallAngles() {
		float[][] points = room.getPoints();
		int nbPoints = room.getPointCount();
		wallAngles = new float[nbPoints];
		for (int i = 0; i < nbPoints - 1; ++i) {
			wallAngles[i] = (float) (Math.atan2(points[(i + 1) % nbPoints][1]
					- points[i][1], points[(i + 1) % nbPoints][0]
					- points[i][0]) + Math.PI);
		}
	}

	public float getLowestX() {
		float min=Float.MAX_VALUE;
		float [][]points=room.getPoints();
		for (int i=0;i<room.getPointCount();++i){
			if (points[i][0]<min)
				min=points[i][0];
		}
		return min;
	}

	public float getHighestX() {
		float max=Float.MIN_VALUE;
		float [][]points=room.getPoints();
		for (int i=0;i<room.getPointCount();++i){
			if (points[i][0]>max)
				max=points[i][0];
		}
		return max;
	}

	public float getLowestY() {
		float min=Float.MAX_VALUE;
		float [][]points=room.getPoints();
		for (int i=0;i<room.getPointCount();++i){
			if (points[i][1]<min)
				min=points[i][1];
		}
		return min;
	}

	public float getHighestY() {
		float max=Float.MIN_VALUE;
		float [][]points=room.getPoints();
		for (int i=0;i<room.getPointCount();++i){
			if (points[i][1]>max)
				max=points[i][1];
		}
		return max;
	}
}
