package DT;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

import DT.DecisionTree.Node;
import dataRecording.DataTuple.DiscreteTag;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ID3 {
	int numTraningsets;
	int numAttributes;
	int numOfAttributesNotChecked;
	int numLables;
	String lable; // These are our Discrete tags in DataTuple
	double entropyOfSystem = 0.0;

	List<SampleData> sampleList = new ArrayList<SampleData>();
	List<Double> informationGain = new ArrayList<Double>();
	DecisionTree decisionTree;

	ID3(List<SampleData> data) {
		sampleList = data;
		numTraningsets = data.size();
		numAttributes = data.get(0).dataList.size();
		numOfAttributesNotChecked = numAttributes;
	}

	public DecisionTree GenerateTreeWithID3() {
		decisionTree = new DecisionTree();

		List<Integer> attributeIndexChecked = new ArrayList<Integer>();
		int attributeIndex = GetAttributeWithHighestIG(attributeIndexChecked, sampleList);

		decisionTree.SetRoot(0, null, attributeIndex);
		generateTree(decisionTree.root, sampleList);

		// generate tree with ID3 and add nodes to it;

		return decisionTree;
	}

	private void generateTree(Node currentNode, List<SampleData> subData) {
		if (currentNode.move != null) {
			return;
		}

		List<SampleData> subsetData = new ArrayList<SampleData>();

		for (int i = 1; i < DiscreteTag.values().length - 2; i++) { // Start at 1 and remove -1 to not include Very_high/low
																	// tags

			for (int j = 0; j < subData.size(); j++) {
				if (subData.get(j).dataList.get(currentNode.dataIndex) == DiscreteTag.values()[i]) {
					subsetData.add(subData.get(j)); // Add sample row that has the right tag
				}
			}
			
			if(subsetData.size() <= 0) {
				Node child = decisionTree.CreateNode(i, DiscreteTag.values()[i], -1, currentNode);
				child.SetMove(GetMostCommonMove(subData));
				continue; //Borde kolla här att subset data har något i sig och inte är tom för isf bör vi inte skapa en nod
			}
						
			int attributeIndex = GetAttributeWithHighestIG(currentNode.attrbutesChecked, subsetData);
			Node child = decisionTree.CreateNode(i, DiscreteTag.values()[i], attributeIndex, currentNode);

			if (isSingleLable(subsetData)) {
				child.SetMove(subsetData.get(0).GetMoveOutcome());
				continue; 
			} else if(attributeIndex == -1){
				child.SetMove(GetMostCommonMove(subsetData));
				continue;
			}
				else {
				generateTree(child, subsetData);
			}
			subsetData.clear();
		}
	}
	
	public MOVE GetMostCommonMove(List<SampleData> sampleDatas) {
		int[] moveFrequency = new int[] { 0, 0, 0, 0 };
		for(SampleData data : sampleDatas) {
			if (data.GetMoveOutcome() == MOVE.UP) {
				moveFrequency[0] += 1;
			} else if (data.GetMoveOutcome() == MOVE.DOWN) {
				moveFrequency[1] += 1;
			} else if (data.GetMoveOutcome() == MOVE.LEFT) {
				moveFrequency[2] += 1;
			} else if (data.GetMoveOutcome() == MOVE.RIGHT) {
				moveFrequency[3] += 1;
			}
		}
		
		int highestMoveFequency = 0;
		int highestMoveIndex = -1;
		for(int i = 0; i < moveFrequency.length; i++) {
			if(moveFrequency[i] > highestMoveFequency) {
				highestMoveIndex = i;
				highestMoveFequency = moveFrequency[i];
			}
		}
		
		if (highestMoveIndex == 0) {
			return MOVE.UP;
		} else if (highestMoveIndex == 1) {
			return MOVE.DOWN;
		} else if (highestMoveIndex == 2) {
			return MOVE.RIGHT;
		} else if (highestMoveIndex == 3) {
			return MOVE.LEFT;
		}
		
		return MOVE.NEUTRAL;
		
	}

	public int GetAttributeWithHighestIG(List<Integer> attributeIndexChecked, List<SampleData> subsetData) {
		CalculateEntropyOfTheSystem(subsetData); // Calculate the entropy of the subset data set
		double highestIG = 0.0;
		int attributeIndex = -1; //0 är default för oss här så om inget har ett IG värde som är > 0 så blir det 0 för vårt index
		informationGain.clear();

		for (int i = 0; i < numAttributes; i++) {
			boolean checkCleared = true;

			if (attributeIndexChecked.size() > 0) {
				for (int j = 0; j < attributeIndexChecked.size(); j++) {
					if (i == attributeIndexChecked.get(j)) { // should never enter here for the root
						informationGain.add(-1.0);
						checkCleared = false;
						break;
					}
				}
			}
			if (checkCleared) {
				informationGain.add(InformationGain(i, subsetData)); // Adds for each attribute that is not checked before the parent
			}
		}
		
		

		for (int i = 0; i < informationGain.size(); i++) { // Finds the attribute with highest IG for the subset
			if (informationGain.get(i) > highestIG) {
				highestIG = informationGain.get(i);
				attributeIndex = i;
				
			}
		}
		if(attributeIndex == -1) {
			//System.out.print("All 0 or -1?");
		}

		return attributeIndex;
	}

	public void CalculateEntropyOfTheSystem(List<SampleData> applicableRows) {

		double[] nrOfMoveDir = new double[] { 0.0, 0.0, 0.0, 0.0 }; //Up, down, left, right

		for (SampleData sample : applicableRows) { // Checks the move outcome of the data set
			if (sample.GetMoveOutcome() == MOVE.UP) {
				nrOfMoveDir[0] = nrOfMoveDir[0] + 1;
			} else if (sample.GetMoveOutcome() == MOVE.DOWN) {
				nrOfMoveDir[1] = nrOfMoveDir[1] + 1;
			} else if (sample.GetMoveOutcome() == MOVE.LEFT) {
				nrOfMoveDir[2] = nrOfMoveDir[2] + 1;
			} else if (sample.GetMoveOutcome() == MOVE.RIGHT) {
				nrOfMoveDir[3] = nrOfMoveDir[3] + 1;
			}

		}

		entropyOfSystem = Entropy(nrOfMoveDir);
	}

	public double InformationGain(int columnIndex, List<SampleData> applicableRows) {

		//double[] listTagsNone = new double[] { 0.0, 0.0, 0.0, 0.0 };
		double[] listTagsHigh = new double[] { 0.0, 0.0, 0.0, 0.0 }; // Up, down, left, right
		double[] listTagsMedium = new double[] { 0.0, 0.0, 0.0, 0.0 };
		double[] listTagsLow = new double[] { 0.0, 0.0, 0.0, 0.0 };

		//double nrOfNoneTags = 0.0;
		double nrOfHighTags = 0.0;
		double nrOfMediumTags = 0.0;
		double nrOfLowTags = 0.0;

		for (int j = 0; j < applicableRows.size(); j++) { // Row iterator
			if (applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.HIGH
					|| applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.VERY_HIGH) {
				CheckMoveType(listTagsHigh, j, applicableRows);
				nrOfHighTags += 1;
			} else if (applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.MEDIUM) {
				CheckMoveType(listTagsMedium, j, applicableRows);
				nrOfMediumTags += 1;
			} else if (applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.LOW
					|| applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.VERY_LOW) {
				CheckMoveType(listTagsLow, j, applicableRows);
				nrOfLowTags += 1;
			}/*else if(applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.NONE) {
				CheckMoveType(listTagsNone, j, applicableRows);
				nrOfNoneTags += 1;
			}*/
		}

		double entropyHigh = Entropy(listTagsHigh);
		double entropyMedium = Entropy(listTagsMedium);
		double entropyLow = Entropy(listTagsLow);
		//double entropyNone = Entropy(listTagsNone);

		double totalTags = nrOfHighTags + nrOfMediumTags + nrOfLowTags; // + nrOfNoneTags;

		return entropyOfSystem - (nrOfHighTags / totalTags) * entropyHigh - (nrOfMediumTags / totalTags) * entropyMedium
				- (nrOfLowTags / totalTags) * entropyLow; /* - (nrOfNoneTags / totalTags) * entropyNone;*/
	}
	
	public double InformationGain2(int columnIndex, List<SampleData> applicableRows) {

		double[] listTagsNone = new double[] { 0.0, 0.0, 0.0, 0.0 };
		double[] listTagsVeryHigh = new double[] { 0.0, 0.0, 0.0, 0.0 };
		double[] listTagsHigh = new double[] { 0.0, 0.0, 0.0, 0.0 }; // Up, down, left, right
		double[] listTagsMedium = new double[] { 0.0, 0.0, 0.0, 0.0 };
		double[] listTagsLow = new double[] { 0.0, 0.0, 0.0, 0.0 };
		double[] listTagsVeryLow = new double[] { 0.0, 0.0, 0.0, 0.0 };

		double nrOfNoneTags = 0.0;
		double nrOfVeryHighTags = 0.0;
		double nrOfHighTags = 0.0;
		double nrOfMediumTags = 0.0;
		double nrOfLowTags = 0.0;
		double nrOfVeryLowTags = 0.0;

		for (int j = 0; j < applicableRows.size(); j++) { // Row iterator
			if (applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.HIGH) {
				CheckMoveType(listTagsHigh, j, applicableRows);
				nrOfHighTags += 1;
			}else if(applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.VERY_HIGH) {
				CheckMoveType(listTagsVeryHigh, j, applicableRows);	
				nrOfVeryHighTags += 1;
			} else if (applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.MEDIUM) {
				CheckMoveType(listTagsMedium, j, applicableRows);
				nrOfMediumTags += 1;
			} else if (applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.LOW) {
				CheckMoveType(listTagsLow, j, applicableRows);
				nrOfLowTags += 1;
			}
			else if(applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.VERY_LOW) {
				CheckMoveType(listTagsVeryLow, j, applicableRows);
				nrOfVeryLowTags += 1;
			}
			else if(applicableRows.get(j).dataList.get(columnIndex) == DiscreteTag.NONE) {
				CheckMoveType(listTagsNone, j, applicableRows);
				nrOfNoneTags += 1;
			}
		}

		double entropyNone = Entropy(listTagsNone);
		double entropyVeryHigh = Entropy(listTagsVeryHigh);
		double entropyHigh = Entropy(listTagsHigh);
		double entropyMedium = Entropy(listTagsMedium);
		double entropyLow = Entropy(listTagsLow);
		double entropyVeryLow = Entropy(listTagsVeryLow);

		double totalTags = nrOfHighTags + nrOfMediumTags + nrOfLowTags + nrOfNoneTags + nrOfVeryHighTags + nrOfVeryLowTags;

		return entropyOfSystem - (nrOfHighTags / totalTags) * entropyHigh - (nrOfMediumTags / totalTags) * entropyMedium
				- (nrOfLowTags / totalTags) * entropyLow - (nrOfNoneTags / totalTags) * entropyNone 
				- (nrOfVeryLowTags / totalTags) * entropyVeryLow - (nrOfVeryHighTags / totalTags) * entropyVeryHigh;
	}

	public void CheckMoveType(double[] listTags, int sampleRowIndex, List<SampleData> applicableRows) {
		if (applicableRows.get(sampleRowIndex).GetMoveOutcome() == MOVE.UP) {
			listTags[0] += 1;
		} else if (applicableRows.get(sampleRowIndex).GetMoveOutcome() == MOVE.DOWN) {
			listTags[1] += 1;
		} else if (applicableRows.get(sampleRowIndex).GetMoveOutcome() == MOVE.LEFT) {
			listTags[2] += 1;
		} else if (applicableRows.get(sampleRowIndex).GetMoveOutcome() == MOVE.RIGHT) {
			listTags[3] += 1;
		}
	}

	public double Entropy(double[] frequency) {
		double total = 0.0;
		for(Double f : frequency) {
			total += f;
		}
		
		double entropy = 0.0;
		for(Double p: frequency) {
			if(p > 0.0) {
				entropy += -(p / total) * log2(p / total);
			}
		}
		
		if(!Double.isNaN(entropy)) return entropy;
		
		return 0.0;
	}

	public double log2(double N) {

		double result = (double) (Math.log(N) / Math.log(2));

		return result;
	}

	boolean isSingleLable(List<SampleData> subsetData) // Check if all the data rows have the same outcome
	{
		if (subsetData == null || subsetData.size() < 1)
			return false;

		MOVE classification = subsetData.get(0).GetMoveOutcome(); // Any row will do here to compare

		for (SampleData sample : subsetData) {
			if (classification != sample.GetMoveOutcome())
				return false;
		}

		return true; // if true then we can say that all rows have the same move outcome and we don't
						// need to continue building the tree
	}
}

/*
 * else if(i == 1 && subData.get(j).dataList.get(currentNode.dataIndex) ==
 * DiscreteTag.VERY_LOW) { subsetData.add(subData.get(j)); } else if(i == 3 &&
 * subData.get(j).dataList.get(currentNode.dataIndex) == DiscreteTag.VERY_HIGH)
 * { subsetData.add(subData.get(j)); }
 */
