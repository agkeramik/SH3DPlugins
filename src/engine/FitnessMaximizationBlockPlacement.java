package engine;

import java.util.List;
import java.util.Map;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import ModelBlock.ModelBlock;
import VectorUtils.Vec3;

import com.eteks.sweethome3d.model.DoorOrWindow;
import com.eteks.sweethome3d.model.HomeDoorOrWindow;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;

public class FitnessMaximizationBlockPlacement extends FitnessFunction {

	private static final long serialVersionUID = 1L;
	ModelBlock staticModel;
	RoomEngine roomEngine;

	public FitnessMaximizationBlockPlacement(ModelBlock modelBlock,
			RoomEngine roomEngine) {
		staticModel = modelBlock;
		this.roomEngine = roomEngine;
	}

	

	@Override
	protected double evaluate(IChromosome chromosome) {
		// TODO Auto-generated method stub
		// Do not work in multi-threading environment
		float x = ((Double) chromosome.getGene(0).getAllele()).floatValue();
		float y = ((Double) chromosome.getGene(1).getAllele()).floatValue();
		float angle = (float) (((Integer) chromosome.getGene(2).getAllele())
				* 2*Math.PI / 36);
//		System.out.println("x="+x+" y="+y+" angle="+angle);
		// List<HomePieceOfFurniture>
		// entryPoints=staticModel.getEntryPointsWithShifts(x, y, angle);
		// Map<HomeDoorOrWindow, Integer> doors=roomEngine.getDoors();
		float xCenter = roomEngine.getRoom().getXCenter();
		float yCenter = roomEngine.getRoom().getYCenter();
		return 1.0/(new Vec3(xCenter - x, yCenter - y).norm());
	}

}
