package DT;
import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;
import dataRecording.DataTuple.DiscreteTag;
import pacman.game.Constants.MOVE;
import java.util.ArrayList;
import java.util.List;

public class SampleData {
	MOVE outcome;
	//DiscreteTag mazeIndex; //Very_low (???), Very_High (???)
	//DiscreteTag currentLevel; //Very_low (Start Level is 0), Very_High (Last Level)
	DiscreteTag pacmanPosition; //Very_low (???), Very_High (???)
	DiscreteTag numOfPillsLeft; //Very_low (Few pills left), Very_High (Many pills left)
	DiscreteTag numOfPowerPillsLeft; //Very_low (Few pp left), Very_High (Many pp left)
	DiscreteTag isBlinkyEdible; //Very_Low (0.0 -> notEdible), Very_High (1.0 -> edible)
	DiscreteTag isInkyEdible;
	DiscreteTag isPinkyEdible;
	DiscreteTag isSueEdible;
	DiscreteTag blinkyDist; //Very_low (ghost close), Very_High (ghost far away)
	DiscreteTag inkyDist;
	DiscreteTag pinkyDist;
	DiscreteTag sueDist;

	//Might not use these below
	DiscreteTag distanceToPowerPill;
	DiscreteTag distanceToClosestGhost; //Very_low (ghost close), Very_High (ghost far away)
	
	DiscreteTag distanceToPellet;	
	DiscreteTag ghostVoulnerability;
	
	public List<DiscreteTag> dataList = new ArrayList<DiscreteTag>();
	
	public SampleData(DataTuple mySample) {

		outcome = mySample.DirectionChosen;
		//mazeIndex = mySample.discretizePosition(mySample.mazeIndex); 
		//currentLevel = DiscreteTag.DiscretizeDouble(mySample.normalizeLevel(mySample.currentLevel));
		pacmanPosition = mySample.discretizePosition(mySample.pacmanPosition);
		//this.pacmanLivesLeft;
		numOfPillsLeft = mySample.discretizeNumberOfPills(mySample.numOfPillsLeft);
		numOfPowerPillsLeft = mySample.discretizeNumberOfPowerPills(mySample.numOfPowerPillsLeft);
		isBlinkyEdible = mySample.discretizeVulnerability(mySample.isBlinkyEdible);
		isInkyEdible = mySample.discretizeVulnerability(mySample.isInkyEdible);
		isPinkyEdible = mySample.discretizeVulnerability(mySample.isPinkyEdible);
		isSueEdible = mySample.discretizeVulnerability(mySample.isSueEdible);
		blinkyDist = mySample.discretizeDistance(mySample.blinkyDist);
		inkyDist = mySample.discretizeDistance(mySample.inkyDist);
		pinkyDist = mySample.discretizeDistance(mySample.pinkyDist);
		sueDist = mySample.discretizeDistance(mySample.sueDist);
		//this.blinkyDir; //Might not care what direction the ghost are moving just the relative distance
		/*this.inkyDir;
		this.pinkyDir;
		this.sueDir;*/
		//this.numberOfNodesInLevel;
		//this.numberOfTotalPillsInLevel;
		//this.numberOfTotalPowerPillsInLevel;
		
		//dataList.add(mazeIndex);
		//dataList.add(currentLevel);
		dataList.add(pacmanPosition);
		dataList.add(numOfPillsLeft);
		dataList.add(numOfPowerPillsLeft);
		dataList.add(isBlinkyEdible);
		dataList.add(isInkyEdible);
		dataList.add(isPinkyEdible);
		dataList.add(isSueEdible);
		dataList.add(blinkyDist);
		dataList.add(inkyDist);
		dataList.add(pinkyDist);
		dataList.add(sueDist);
		
		for(int i = 7; i < dataList.size(); i++) { 
			if(dataList.get(i) == DiscreteTag.NONE) {
				dataList.set(i, DiscreteTag.MEDIUM);
			}
		}
		
		/*int[] distanceToGhosts = new int[] {mySample.blinkyDist, mySample.inkyDist, mySample.pinkyDist, mySample.sueDist};
		boolean[] ghostEdible = new boolean[] {mySample.isBlinkyEdible, mySample.isInkyEdible, mySample.isPinkyEdible, mySample.isSueEdible};
		
		distanceToClosestGhost = mySample.discretizeDistance(TagDistanceToGhost(distanceToGhosts));*/
		//ghostVoulnerability = mySample.discretizeVulnerability(TagAreGhostsEdible(ghostEdible));

	}
	
	public MOVE GetMoveOutcome() {
		return outcome;
	}
	
	/**
	 * Return the closest distance out of the four ghosts. Supposes that the maximum possible
	 * distance is 150.
	 * 
	 * @param distanceToGhosts
	 *            Array with the distance to each ghost
	 * @return Closest ghost distance out of the four
	 */
	public int TagDistanceToGhost(int[] distanceToGhosts) {
		int closestGhostDistance = 150; //Max distance in maze from DataTuple class
		
		for(int i = 0; i < distanceToGhosts.length; i++)
		{
			if(distanceToGhosts[i] <= -1) {
				continue;
			}
			else if(distanceToGhosts[i] < closestGhostDistance) {
				closestGhostDistance = distanceToGhosts[i];
			}
		}
		if(closestGhostDistance >= 150) {
			closestGhostDistance = - 1;
		}
		return closestGhostDistance;
	}
	
	public boolean TagAreGhostsEdible(boolean[] ghostEdible) { //Can check how many ghosts are edible for better results
		for(int i = 0; i < ghostEdible.length; i++)
		{
			if(ghostEdible[i]) {
				return true;
			}
		}

		return false;
	}
	

}
