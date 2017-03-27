

public class PriorityQueue {
    //want to insert at the tail
    
    //(42,1) (35,2)
    Node head;
    Node tail;
    Node max;
    int maxPriority;
    int maxVal;
    PriorityQueue(){
        head = null;
        tail = null;
        max = null;
        maxPriority = 0;
    }
    
    void add(int val,int priority) {
        //case empty list
        //if(max == null) System.out.println("max is null");
        if(head == null) {
            head = new Node(priority,val);
            tail = head;
        }else {//case non-empty list
            tail.nextNode = new Node(priority,val);
            tail = tail.nextNode;
        }
        if(priority>maxPriority) {
            maxPriority = priority;
        }
    }
    // need to search the list from the front to the back to find the max value
    
    //once it is searched and we have found the max value from the front to the back, we need
    //to delete
    
    
    void delete(Node nodeToDelete) {
        int i = 0;
        //case where the node to delete is the head:
        if(nodeToDelete == head) {
            if(head.nextNode == null) head = null;
            else head = head.nextNode;
        }else if(nodeToDelete == tail) {
            Node current = head;
            while(current != null) {
                Node afterCurrent = current.nextNode;
                if(afterCurrent == tail) tail = current;
                current = current.nextNode;
            }
        }else {
            Node current = head;
            while(current!=null) {
                if(current.nextNode == nodeToDelete) {
                    current.nextNode = current.nextNode.nextNode;
                    break;
                }
                current = current.nextNode;
            }
        }
    }
    
    int deleteMax() {
        Node current = head;
        int maxPriority = 0;
        int i=0;
        int j=0;
        //establish what the max of the list is
        while(current!=null && i < 100) {
            if (current.priority > maxPriority) maxPriority = current.priority;
            current = current.nextNode;
            i++;
        }
        current = head;
        //search for the first entry that is of maximum priority
        while(current != null&& j < 100) {
            if(current.priority == maxPriority) {
                int returnVal = current.val;
                delete(current);
                return returnVal;
            }
            current = current.nextNode;
            j++;
        }
        return 0;
    }
    
    void traverse() {
        Node current = head;
        int i =0;
        while(current!=null && i<100) {
            System.out.println( current.val + " "+current.priority );
            current = current.nextNode;
            i++;
        }
    }
}
