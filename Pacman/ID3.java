package DT;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

import DT.DecisionTree.Node;
import dataRecording.DataTuple.DiscreteTag;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ID3 
{
int numTraningsets;
int numAttributes;
int numOfAttributesNotChecked;
int numLables;
String lable; //These are our Discrete tags in DataTuple
double entropyOfSystem = 0.0;

List<SampleData> sampleList = new ArrayList<SampleData>();
List<Double> informationGain = new ArrayList<Double>();
List<SampleData> subsetData = new ArrayList<SampleData>();
DecisionTree decisionTree;


ID3(List<SampleData> data)
{
  sampleList = data;
  numTraningsets = data.size();
  numAttributes = data.get(0).dataList.size();
  numOfAttributesNotChecked = numAttributes;    
}

public DecisionTree GenerateTreeWithID3()
{
	decisionTree = new DecisionTree();
	
	List<Integer> attributeIndexChecked = new ArrayList<Integer>();
	int attributeIndex = GetAttributeWithHighestIG(attributeIndexChecked, sampleList);
	
	decisionTree.SetRoot(0, null, attributeIndex);
	generateTree(decisionTree.root); 
	
	//generate tree with ID3 and add nodes to it;
	
	return decisionTree;
}

private void generateTree(Node currentNode)
{
    if (currentNode.move != null)
    {
        return;
    }
    
    for(int i = 1; i < DiscreteTag.values().length - 2; i++){ //Start at 0 and remove -2 to include Very_high/low tags
    subsetData.clear();
  	  for(int j = 0; j < sampleList.size(); j++) {
  		  if(sampleList.get(j).dataList.get(currentNode.dataIndex) == DiscreteTag.values()[i]) {
  			  subsetData.add(sampleList.get(j)); //Add sample row that has the right tag
  		  }
  	  }
  	  int attributeIndex = GetAttributeWithHighestIG(currentNode.attrbutesChecked, subsetData);
    	Node n = decisionTree.CreateNode(i, DiscreteTag.values()[i], attributeIndex, currentNode);
    	if(isSingleLable(subsetData)) {
    		n.SetMove(subsetData.get(0).GetMoveOutcome());
    		return; //set move to return by the node here by giving it subsetData.get(0).GetMoveOutcome()
    	}
    }
    
    if(subsetData.size() == 1)
    
    for(Node child : currentNode.children)
    {
        generateTree(child);
    }
}

public int GetAttributeWithHighestIG(List<Integer> attributeIndexChecked, List<SampleData> subsetData) {
	CalculateEntropyOfTheSystem(subsetData); //Calculate the entropy of the subset data set
	double highestIG = 0.0;
	int attributeIndex = 0;
	informationGain.clear();
	
	  for(int i = 0; i < numAttributes; i++) { 
		  boolean checkCleared = true;
		  
		  if(attributeIndexChecked.size() > 0) {
			  for(int j = 0; j < attributeIndexChecked.size(); j++) { 
				  if(i == attributeIndexChecked.get(j)) { //should never enter here for the root
					  informationGain.add(-1.0);
					  checkCleared = false;
					  break;
				  	}
			  }  
		  }
		  if(checkCleared) {
			  informationGain.add(InformationGain(i, subsetData)); // Adds for each attribute that is not checked before the parent
		  }
	  }
	  
	  for(int i = 0; i < informationGain.size(); i++) { //Finds the attribute with highest IG for the subset
		  if(informationGain.get(i) > highestIG) {
			  highestIG = informationGain.get(i);
			  attributeIndex = i;
			  
		  }
	  }
	  
	  return attributeIndex;
}

public void CalculateEntropyOfTheSystem(List<SampleData> applicableRows) {	
	
	  double nrOfMoveLeft = 0.0;
	  double nrOfMoveRight = 0.0;
	  double nrOfMoveUp = 0.0;
	  double nrOfMoveDown = 0.0;
	  
	  for(SampleData sample : applicableRows) { //Checks the move outcome of the data set
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

public double InformationGain(int columnIndex, List<SampleData> applicableRows) { 
		  
  double[] listTagsHigh = new double[] {0.0, 0.0, 0.0, 0.0}; //Up, down, left, right
  double[] listTagsMedium = new double[] {0.0, 0.0, 0.0, 0.0};
  double[] listTagsLow = new double[] {0.0, 0.0, 0.0, 0.0};
  	  	  
  double nrOfHighTags = 0.0;
  double nrOfMediumTags = 0.0;
  double nrOfLowTags = 0.0;
  
  for(int j = 0; j < applicableRows.size(); j++) { //Row iterator
	  if(applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.HIGH || applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.VERY_HIGH) {
		  CheckMoveType(listTagsHigh, j, applicableRows);
		  nrOfHighTags += 1;
	  }
	  else if(applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.MEDIUM) {
		  CheckMoveType(listTagsMedium, j, applicableRows);
		  nrOfMediumTags += 1;
	  }
	  else if(applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.LOW || applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.VERY_LOW) {
		  CheckMoveType(listTagsLow, j, applicableRows);
		  nrOfLowTags += 1;
	  }
  }
	  
	  
  double entropyHigh = Entropy(listTagsHigh[0], listTagsHigh[1], listTagsHigh[2], listTagsHigh[3]);
  double entropyMedium = Entropy(listTagsMedium[0], listTagsMedium[1], listTagsMedium[2], listTagsMedium[3]);
  double entropyLow = Entropy(listTagsLow[0], listTagsLow[1], listTagsLow[2], listTagsLow[3]);
	  
  double totalTags = nrOfHighTags + nrOfMediumTags + nrOfLowTags;
   
  return entropyOfSystem - (nrOfHighTags / totalTags) * entropyHigh - (nrOfMediumTags / totalTags) * entropyMedium - (nrOfLowTags / totalTags) * entropyLow;
}

public void CheckMoveType(double[] listTags, int sampleRowIndex, List<SampleData> applicableRows) {
	if(applicableRows.get(sampleRowIndex).GetMoveOutcome() == MOVE.UP) {
		  listTags[0] += 1;
	  }
	  else if(applicableRows.get(sampleRowIndex).GetMoveOutcome() == MOVE.DOWN) {
		  listTags[1] += 1;
	  }
	  else if(applicableRows.get(sampleRowIndex).GetMoveOutcome() == MOVE.LEFT) {
		  listTags[2] += 1;
	  }
	  else if(applicableRows.get(sampleRowIndex).GetMoveOutcome() == MOVE.RIGHT) {
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

boolean isSingleLable(List<SampleData> subsetData) //Check if all the data rows have the same outcome
{
	if(subsetData == null || subsetData.size() < 1) return false;
	
	MOVE classification = subsetData.get(0).GetMoveOutcome(); //Any row will do here to compare
	
	for (SampleData sample : subsetData) {
		if(classification != sample.GetMoveOutcome()) return false;
	}
	
	return true; //if true then we can say that all rows have the same move outcome and we don't need to continue building the tree
}



}

