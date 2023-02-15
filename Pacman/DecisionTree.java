package DT;

public class DecisionTree 
{
	class BinTree{
		public boolean condition;
		public int id;
		public String eval;
		public BinTree trueBranch;
		public BinTree falseBranch;
		
		public BinTree(int newId, boolean condition, String eval) {
			id = newId;
			this.condition = condition;
		}
	}
	
	BinTree root;
	public String result;
	
	public void SetRoot(int newId, boolean condition, String eval)
	{
		root = new BinTree(newId, condition, eval);
	}
	
	public void AddTrueNode(int existingNodeId, int newNodeId, boolean condition, String eval) {
		if(root == null) {
			return;
		}
		if(ParseTreeAndAddTrueNode(root, existingNodeId, newNodeId, condition, eval)) {
			System.out.print("added node " + newNodeId + "onto true branch of node " + existingNodeId);
		}
		else {
			System.out.print("Node " + existingNodeId + " not found!");
		}
	}
	
	private boolean ParseTreeAndAddTrueNode(BinTree currentNode, int existingNodeId, int newNodeId, boolean condition, String eval) {
		if(currentNode.id == existingNodeId) {
			if(currentNode.trueBranch == null) {
				currentNode.trueBranch = new BinTree(newNodeId, condition, eval);
			}
			else {
				System.out.print("WARNING: replacing " + "(id = " + currentNode.trueBranch.id + ") linked to true-branch of " + existingNodeId);
			}
			return true;
		}
		else {
			if(currentNode.trueBranch != null) {
				return ParseTreeAndAddTrueNode(currentNode.falseBranch, existingNodeId, newNodeId, condition, eval);
			}
			else {
				return false;
			}
		}
	}
	
	public void AddFalseNode(int existingNodeId, int newNodeId, boolean condition, String eval) {
		if(root == null) {
			System.out.print("ERROR: No DT!");
			return;
		}
		if(ParseTreeAndAddFalseNode(root, existingNodeId, newNodeId, condition, eval)) {
			System.out.print("added node " + newNodeId + "onto false branch of node " + existingNodeId);
		}
		else {
			System.out.print("Node " + existingNodeId + " not found!");
		}
	}
	
	private boolean ParseTreeAndAddFalseNode(BinTree currentNode, int existingNodeId, int newNodeId, boolean condition, String eval) {
		if(currentNode.id == existingNodeId) {
			if(currentNode.falseBranch == null) {
				currentNode.falseBranch = new BinTree(newNodeId, condition, eval);
			}
			else {
				System.out.print("WARNING: replacing " + "(id = " + currentNode.trueBranch.id + ") linked to false-branch of " + existingNodeId);
				currentNode.falseBranch = new BinTree(newNodeId, condition, eval);
			}
			return true;
		}
		else {
			if(currentNode.trueBranch != null) {
				if(ParseTreeAndAddFalseNode(currentNode.trueBranch, existingNodeId, newNodeId, condition, eval)) {
					return true;
				}
				else {
					if(currentNode.falseBranch != null) {
						return ParseTreeAndAddFalseNode(currentNode.trueBranch, existingNodeId, newNodeId, condition, eval);
					}
					else return false;
				}
			}
			else {
				return false;
			}
		}
	}
	
	private void Evaluate(BinTree currentNode) {
		System.out.print(currentNode.eval);
		
		if(currentNode.condition) ParseTree(currentNode.trueBranch);
		else {
			if(!currentNode.condition) ParseTree(currentNode.falseBranch);
		}
	}
	
	void ParseTree(BinTree currentNode) {
		if(currentNode.trueBranch == null) {
			if(currentNode.falseBranch == null) {
				result = currentNode.eval;
			}
			return;
		}
		if(currentNode.falseBranch == null) {
			return;
		}
		Evaluate(currentNode);
	}
	
	public void ParseTree() {
		ParseTree(root);
	}
}
