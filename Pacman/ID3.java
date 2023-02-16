package DT;

import java.util.ArrayList;
import java.util.List;

import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ID3 
{
int numTraningsets;
int numAttributes;
String lable;
List<SampleData> sampleList = new ArrayList<SampleData>();
DecisionTree decisionTree;


ID3(List<SampleData> data)
{
  sampleList = data;
  
  GenerateTreeWithID3();
}

public DecisionTree GenerateTreeWithID3()
{
	decisionTree = new DecisionTree();
	
	//
	//generate tree with ID3 and add nodes to it;
	//
	
	return decisionTree;
	}



boolean isSingleLable() 
{
	String classification = lable;
	
	/*for (SampleData sample : sampleList) {
		if(classification.compareTo(sample.lable) != 0) return false;
	}*/
	
	return true;
}



}
