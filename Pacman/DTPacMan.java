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
	Boolean firstTick = true;
	List<SampleData> data = new ArrayList<SampleData>();
	ID3 ID3Algorithm;
	DecisionTree activeTree;
	
	public DTPacMan() {
		super();
	}
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		//state.Update(game, this);
		
		if(firstTick) {
			Filter_Data();
			ID3Algorithm = new ID3(data);
			GenerateTree();
			firstTick = false;
		}
		
		DataTuple gameData = new DataTuple(game, MOVE.NEUTRAL); //Neutral because we don't know what move we want to make yet
		SampleData discreteData = new SampleData(gameData);
		
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
		
		if(activeTree.result == MOVE.UP) {setMOVE(MOVE.UP);}
		else if(activeTree.result == MOVE.DOWN) {setMOVE(MOVE.DOWN);}
		else if (activeTree.result == MOVE.LEFT) {setMOVE(MOVE.LEFT);}
		else if(activeTree.result == MOVE.RIGHT) {setMOVE(MOVE.RIGHT);}
		else if(activeTree.result == MOVE.NEUTRAL) {setMOVE(MOVE.NEUTRAL);}
	}
		
	
	public void Filter_Data() {
		DataTuple[] mySaveData = DataSaverLoader.LoadPacManData();
		for(int i = 0; i < mySaveData.length; i++)
		{
		   SampleData s = new SampleData(mySaveData[i]);
		   data.add(s);
		}		
	}
	
	public Game getGame() {
		return game;
	}
	
	
	public void setMOVE(MOVE move){
		this.move = move;
	}
}
