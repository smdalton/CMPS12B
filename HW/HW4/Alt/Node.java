

public class Node {
	Node nextNode;
	int priority;
	int val;
		Node(int priority, int val){
			this.priority = priority;
			this.val = val;
			nextNode = null;
		}
		Node(){
			
		}
}
