package DT;

import java.util.ArrayList;
import java.util.List;
import dataRecording.DataTuple.DiscreteTag;
import pacman.game.Constants.MOVE;

public class DecisionTree 
{
	public class Node{
		public int id;
		public int dataIndex;
		public DiscreteTag condition;
		public Node parent;
		public List<Node> children = new ArrayList<Node>();
		public List<Integer> attrbutesChecked = new ArrayList<Integer>();
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
	public List<Node> tree = new ArrayList<Node>();
	
	public void SetRoot(int newId, DiscreteTag condition, int dataIndex)
	{
		root = new Node(newId, condition, dataIndex);
		tree.add(root);
	}
	
	public Node CreateNode(int newId, DiscreteTag Condition, int dataIndex, Node parent) {
		Node node = new Node(newId, Condition, dataIndex);
		if(!tree.contains(node)) tree.add(node);
		
		SetParent(parent, node);
		
		if(node.parent.attrbutesChecked.size() > 0) {
			for(int i = 0; i < node.parent.attrbutesChecked.size(); i++) {
			node.attrbutesChecked.add(parent.attrbutesChecked.get(i));
			}
		}
		
		node.attrbutesChecked.add(parent.dataIndex);
			
		return node;
	}
	
	public void SetParent(Node parent, Node child) {
		child.parent = parent;
		if(!parent.children.contains(child)) parent.children.add(child);
	}
	
	public void AddChild(Node child, Node parent) {
		if(!parent.children.contains(child)) parent.children.add(child);
		if(child.parent != parent) child.parent = parent;
	}
	
	public int GetParentIndex(Node child) {
		return child.parent.dataIndex;
	}
	
	public int GetParentIndex(int index) {
		return tree.get(index).parent.dataIndex;
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
