package DT;
import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;
import dataRecording.DataTuple.DiscreteTag;
import pacman.game.Game;
import pacman.game.Constants.MOVE;
//import BehaviourTree.GameInformation;
import DT.SampleData;

public class Filter {
	
	DiscreteTag mazeIndex; //Very_low (???), Very_High (???)
	DiscreteTag currentLevel; //Very_low (Start Level is 0), Very_High (Last Level)
	DiscreteTag pacmanPosition; //Very_low (???), Very_High (???)
	DiscreteTag numOfPillsLeft; //Very_low (Few pills left), Very_High (Many pills left)
	DiscreteTag numOfPowerPillsLeft; //Very_low (Few pp left), Very_High (Many pp left)
	DiscreteTag isGhostEdible; //Very_Low (0.0 -> notEdible), Very_High (1.0 -> edible)
	DiscreteTag ghostDistance; //Very_low (ghost close), Very_High (ghost far away)
	
	//GameInformation information;
	MOVE myMove;
	
	public Filter (){
		//information = new GameInformation();
	}
	
	public void Filter_Data() {
		DataTuple[] mySaveData = DataSaverLoader.LoadPacManData();
		SampleData[] mySamples = new SampleData[mySaveData.length];
		for(int i = 0; i < mySaveData.length; i++)
		{
			mySamples[i] = new SampleData(mySaveData[i]);
		}		
		
	}
	
	
	public MOVE getMove(Game game, long timeDue) 
	{
		
		//Place your game logic here to play the game as Ms Pac-Man

		//information.Update(game, null);
		DataTuple data = new DataTuple(game, MOVE.NEUTRAL); //Neutral because we don't know what move we want to make yet
		SampleData discreteData = new SampleData(data);
		
		//Use discreteData to traverse the tree and check each current data parameter
		
		return myMove;
		
	}

	
	

}
