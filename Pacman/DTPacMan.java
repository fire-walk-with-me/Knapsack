package DT;

import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.controllers.Controller;
import pacman.game.Game;
import java.util.ArrayList;
import java.util.List;


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
		
		TraverseTree();
		
		return move;
	}
	
	
	void GenerateTree()
	{
		activeTree = ID3Algorithm.GenerateTreeWithID3();
	}
	
	
	public void TraverseTree()
	{
		activeTree.ParseTree();
		
		if(activeTree.result == "up") {setMOVE(MOVE.UP);}
		else if(activeTree.result == "down") {setMOVE(MOVE.DOWN);}
		else if (activeTree.result == "left") {setMOVE(MOVE.LEFT);}
		else if(activeTree.result == "right") {setMOVE(MOVE.RIGHT);}
		else if(activeTree.result == null) {setMOVE(MOVE.NEUTRAL);}
	}
		
	
	public Game getGame() {
		return game;
	}
	
	
	public void setMOVE(MOVE move){
		this.move = move;
	}
}
