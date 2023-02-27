package DT;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

import dataRecording.DataTuple.DiscreteTag;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ID3 
{
int numTraningsets;
int numAttributes;
int numLables;
String lable; //These are our Discrete tags in DataTuple
double entropyOfSystem = 0.0;

List<SampleData> sampleList = new ArrayList<SampleData>();
List<Double> informationGain = new ArrayList<Double>();
DecisionTree decisionTree;


ID3(List<SampleData> data)
{
  sampleList = data;
  numTraningsets = data.size();
  numAttributes = data.get(0).dataList.size();
       
  CalculateEntropyOfTheSystem();
  
  for(int i = 0; i < numAttributes; i++) {
	  informationGain.add(InformationGain(i));
  }
  
  GenerateTreeWithID3();
}

public void CalculateEntropyOfTheSystem() {	//List<Integer> applicableRows
	
  double nrOfMoveLeft = 0.0;
  double nrOfMoveRight = 0.0;
  double nrOfMoveUp = 0.0;
  double nrOfMoveDown = 0.0;
  
  for(SampleData sample : sampleList) { //Need to replace this with the rows that relate to the parent node
	  //if()
	  if(sample.GetMoveOutcome() == MOVE.DOWN) {
		  nrOfMoveDown++;
	  }
	  else if(sample.GetMoveOutcome() == MOVE.UP) {
		  nrOfMoveUp++;
	  }
	  else if(sample.GetMoveOutcome() == MOVE.LEFT) {
		  nrOfMoveLeft++;
	  }
	  else if(sample.GetMoveOutcome() == MOVE.RIGHT) {
		  nrOfMoveRight++;
	  }

  }
  
  entropyOfSystem = Entropy(nrOfMoveUp, nrOfMoveDown, nrOfMoveLeft, nrOfMoveRight);	  	  
}


public DecisionTree GenerateTreeWithID3()
{
	decisionTree = new DecisionTree();
	
	//generate tree with ID3 and add nodes to it;
	
	return decisionTree;
}

public double InformationGain(int columnIndex) { 
		  
  double[] listTagsHigh = new double[] {0.0, 0.0, 0.0, 0.0};
  double[] listTagsMedium = new double[] {0.0, 0.0, 0.0, 0.0};
  double[] listTagsLow = new double[] {0.0, 0.0, 0.0, 0.0};
  	  	  
  double nrOfHighTags = 0.0;
  double nrOfMediumTags = 0.0;
  double nrOfLowTags = 0.0;
  
  for(int j = 0; j < numTraningsets; j++) { //Row iterator
	  if(sampleList.get(columnIndex).dataList.get(j) == DiscreteTag.HIGH) {
		  CheckMoveType(listTagsHigh, j);
		  nrOfHighTags += 1;
	  }
	  else if(sampleList.get(columnIndex).dataList.get(j) == DiscreteTag.MEDIUM) {
		  CheckMoveType(listTagsMedium, j);
		  nrOfMediumTags += 1;
	  }
	  else if(sampleList.get(columnIndex).dataList.get(j) == DiscreteTag.LOW) {
		  CheckMoveType(listTagsLow, j);
		  nrOfLowTags += 1;
	  }

  }
	  
	  
  double entropyHigh = Entropy(listTagsHigh[0], listTagsHigh[1], listTagsHigh[2], listTagsHigh[3]);
  double entropyMedium = Entropy(listTagsMedium[0], listTagsMedium[1], listTagsMedium[2], listTagsMedium[3]);
  double entropyLow = Entropy(listTagsLow[0], listTagsLow[1], listTagsLow[2], listTagsLow[3]);
	  
  double totalTags = nrOfHighTags + nrOfMediumTags + nrOfLowTags;
   
  return entropyOfSystem - (nrOfHighTags / totalTags) * entropyHigh - (nrOfMediumTags / totalTags) * entropyMedium - (nrOfLowTags / totalTags) * entropyLow;
}

public void CheckMoveType(double[] listTags, int sampleRowIndex) {
	if(sampleList.get(sampleRowIndex).GetMoveOutcome() == MOVE.UP) {
		  listTags[0] += 1;
	  }
	  else if(sampleList.get(sampleRowIndex).GetMoveOutcome() == MOVE.DOWN) {
		  listTags[1] += 1;
	  }
	  else if(sampleList.get(sampleRowIndex).GetMoveOutcome() == MOVE.LEFT) {
		  listTags[2] += 1;
	  }
	  else if(sampleList.get(sampleRowIndex).GetMoveOutcome() == MOVE.RIGHT) {
		  listTags[3] += 1;
	  }
}

public double Entropy(double up, double down, double left, double right) {
	double total = up + down + left + right;
	return -(up / total) * log2(up /total) - (down / total) * log2(down / total) - (left / total) * log2(left / total) - (right / total) * log2(right / total); 
}

public double log2(double N)
{

    double result = (double)(Math.log(N) / Math.log(2));

    return result;
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

