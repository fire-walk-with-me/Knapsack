package DT;

import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.controllers.Controller;
import pacman.game.Game;
import java.util.ArrayList;
import java.util.List;

import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;


public class DTPacMan extends Controller<MOVE>
{
	Game game;
	MOVE move;
	Boolean firstRun = true;
	List<SampleData> data = new ArrayList<SampleData>();
	ID3 ID3Algorithm;
	DecisionTree activeTree;
	
	public DTPacMan() {
		super();
	}
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		//state.Update(game, this);
		
		if(firstRun) {
			ID3Algorithm = new ID3(data);
			GenerateTree();
			firstRun = false;
		}
		
		DataTuple data = new DataTuple(game, MOVE.NEUTRAL); //Neutral because we don't know what move we want to make yet
		SampleData discreteData = new SampleData(data);
		
		TraverseTree(discreteData);
		
		return move;
	}
	
	
	void GenerateTree()
	{
		activeTree = ID3Algorithm.GenerateTreeWithID3();
	}
	
	
	public void TraverseTree(SampleData discreteData)
	{
		activeTree.ParseTree(discreteData);
		
		if(true) {setMOVE(MOVE.UP);}
		else if(true) {setMOVE(MOVE.DOWN);}
		else if (true) {setMOVE(MOVE.LEFT);}
		else if(true) {setMOVE(MOVE.RIGHT);}
		else if(true) {setMOVE(MOVE.NEUTRAL);}
	}
		
	
	public void Filter_Data() {
		DataTuple[] mySaveData = DataSaverLoader.LoadPacManData();
		SampleData[] mySamples = new SampleData[mySaveData.length];
		for(int i = 0; i < mySaveData.length; i++)
		{
			mySamples[i] = new SampleData(mySaveData[i]);
		}		
		
	}
	
	public Game getGame() {
		return game;
	}
	
	
	public void setMOVE(MOVE move){
		this.move = move;
	}
}
