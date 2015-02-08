package engine;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;
import org.jgap.impl.IntegerGene;


import ModelBlock.ModelBlock;
import ModelBlock.ModelImporter;

import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.Room;
import com.eteks.sweethome3d.model.UserPreferences;

public class Engine01 {
	public static final String MODEL_FILENAME = "DefaultModelName.xml";
	private UserPreferences userPreferences;
	private Home home;
	private ModelBlock modelBlock;
	private RoomEngine room1;

	public Engine01(UserPreferences userPreferences, Home home) {
		super();
		this.userPreferences = userPreferences;
		this.home = home;
		ModelImporter modelImporter = new ModelImporter(this.userPreferences,
				this.home);
		modelBlock = modelImporter.importModelBlock(MODEL_FILENAME);
		modelBlock.setVisibility(false);
	
		try {
			room1 = new RoomEngine(home, 0);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		startEngine();
	}

	public void startEngine() {
		System.out.print(home.getRooms().size() + "\n");
		Configuration.reset();
		Configuration conf;
		conf = new DefaultConfiguration();
		Gene[] sampleGenes = new Gene[3];
		try {
			sampleGenes[0] = new DoubleGene(conf, room1.getLowestX(),
					room1.getHighestX());// positionX in room
			sampleGenes[1] = new DoubleGene(conf, room1.getLowestY(),
					room1.getHighestY());// positionY in room
			sampleGenes[2] = new IntegerGene(conf, 0, 35);// rotation angles
			IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
			FitnessFunction fitnessFunction = new FitnessMaximizationBlockPlacement(
					modelBlock, room1);
			conf.setFitnessFunction(fitnessFunction);
			conf.setSampleChromosome(sampleChromosome);
			conf.setPopulationSize(60);
			Genotype population;
			population = Genotype.randomInitialGenotype(conf);

			for (int i = 0; i < 200; ++i) {
				population.evolve();
			}
			IChromosome bestSolution = population.getFittestChromosome();
			float x = ((Double) bestSolution.getGene(0).getAllele())
					.floatValue();
			float y = ((Double) bestSolution.getGene(1).getAllele())
					.floatValue();
			float angle = (float) (((Integer) bestSolution.getGene(2)
					.getAllele()) * 2 * Math.PI / 36);

			System.out.println("\nBestChromosomeAlleles");
			for (int i = 0; i < sampleGenes.length; ++i)
				System.out.println("Gene #" + i + " "
						+ bestSolution.getGene(i).getAllele());
			modelBlock.setX(x);
			modelBlock.setY(y);
			modelBlock.setAngle(angle);
			modelBlock.setVisibility(true);

		} catch (InvalidConfigurationException e) {
			// TODO show wrong configuration
			e.printStackTrace();
		}
	}

}
