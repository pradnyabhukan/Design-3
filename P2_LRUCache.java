/*
 TC: 
 insertAtHead : O(1)
 get : O(1)
 put : O(1)
 SC: O(capacity)
 */
class LRUCache {
    class Node {
        int key;
        int val;
        Node next;
        Node prev;
        Node(int key, int value) {
            this.key = key;
            this.val = value;
            next = null;
            prev = null;
        }
    }
    int capacity;
    Node head;
    Node tail;
    HashMap<Integer, Node> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.head = new Node(-1,-1);
        this.tail = new Node(-1,-1);
        head.next = tail;
        tail.prev = head;
        this.map = new HashMap<>();
    }

    private void insertAtHead(int key, int val) { 
        Node newNode;
        if(!map.containsKey(key)) {
            newNode = new Node(key, val);
        } else {
            newNode = map.get(key);
            newNode.val = val;
            newNode.prev.next = newNode.next;
            newNode.next.prev = newNode.prev;
        }
        Node temp = head.next;
        head.next = newNode;
        temp.prev = newNode;
        newNode.next = temp;
        newNode.prev = head;
        map.put(key, newNode);
    }
    
    public int get(int key) { 
        if(!map.containsKey(key)) {
            return -1;
        }
        insertAtHead(key, map.get(key).val);
        return map.get(key).val;
    }

    public void put(int key, int value) { 
        Node newNode = new Node(key, value);
        if(map.containsKey(key)) {
            insertAtHead(key, value);
        }
        else if(capacity > 0) {
            insertAtHead(key, value);
            capacity--;
        } else {
            if(!map.containsKey(key)) {
                Node remove = tail.prev;
                remove.prev.next = tail;
                tail.prev = remove.prev;

                remove.next = null;
                remove.prev = null;
                map.remove(remove.key);
            }
            insertAtHead(key, value);
            
        }
    }
}