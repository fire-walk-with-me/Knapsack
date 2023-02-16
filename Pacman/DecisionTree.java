package DT;

import java.util.ArrayList;
import java.util.List;
import dataRecording.DataTuple.DiscreteTag;
import pacman.game.Constants.MOVE;

public class DecisionTree 
{
	class Node{
		public int id;
		public int dataIndex;
		public DiscreteTag condition;
		public Node parent;
		public List<Node> children = new ArrayList<Node>();
		public MOVE move;
		public double informationGain;
		
		public Node(int newId, DiscreteTag condition, int dataIndex) {
			id = newId;
			this.condition = condition;
			this.dataIndex = dataIndex;
		}
		
		public void SetMove(MOVE move) {
			this.move = move; 
		}
	}
	
	Node root;
	public MOVE result;
	
	public void SetRoot(int newId, DiscreteTag condition, int dataIndex)
	{
		root = new Node(newId, condition, dataIndex);
	}
	
	public void SetParent(Node parent, Node child) {
		child.parent = parent;
		if(!parent.children.contains(child)) parent.children.add(child);
	}
	
	public void AddChild(Node child, Node parent) {
		if(!parent.children.contains(child)) parent.children.add(child);
		if(child.parent != parent) child.parent = parent;
	}
	
	void ParseTree(Node currentNode, SampleData discreteData) 
	{
		System.out.print(currentNode.condition);
		
		if(currentNode.children.isEmpty()) {
				result = currentNode.move;
				return;
			}
		
		for(Node child : currentNode.children) {
			if(discreteData.dataList.get(child.dataIndex) == child.condition) {
				ParseTree(child, discreteData);
			}
		}
		
		System.out.print("Move not found, something wrong with tree?");
		result = MOVE.NEUTRAL;
	}
	
	
	
	public void ParseTree(SampleData discreteData) {
		ParseTree(root, discreteData);
	}
}
