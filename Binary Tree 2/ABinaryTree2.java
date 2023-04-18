package binarytrees;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * 
 * Creates a binary tree that stores any element (generic type).
 * @param <E>
 * @author Dacia Simon
 */
public class ABinaryTree2 <E extends Comparable<E>>{

	/**
	 * Node pointer used to point to descendant nodes. 
	 * @param <E>
	 */
	private class Node<E>{
		private E element; 
		private Node<E> left; 
		private Node<E> right;
		
		public Node(E e) {
			element=e;
		}
	}
	
	private Node<E> root;
	private int size;
	private int even; //num of even integers
	private int odd; //num of odd integers
	
	
	public ABinaryTree2() {
	}
	
	/**inserts the root element or calls overloaded insert */
	public Node<E> insert(E e) {
		
		if(e instanceof Integer) {
			if((Integer)e%2==0)
				even++;
			else {
				odd++;
			}
		}
		/**if root is null, insert the element at root */
		if(root==null) {
			root= new Node<>(e);
			size++;
			return root;
			
		}
		size++;
		return insert(root,e);
		
		
		
	}
	
	/**recursively insert the given element extending from the root*/
	private Node<E> insert(Node<E> r, E e) {
		
		if(r==null) {
			return new Node<>(e);
		}
			
		/**checks to see if the item being inserted is less than/equal to 
		 * or greater than the given node to place the new node*/
		
		else if(r.element.compareTo(e)>=0) { // not a BST: > not >=
			r.left = insert(r.left, e);
		}
		else if(r.element.compareTo(e)<0) {
			r.right = insert(r.right, e);
		}
		return r;                            // not a BST: else{ error message return;}
		
	}
	
	public int count(){
		return(inOrder().size());
	}
	
	public int getEven(){
		return even;
	}
	
	public int getOdd(){
		return odd;
	}
	
	public int numEven() {
		ArrayList<E> list = new ArrayList<>();
		evenNum(root,list);
		return list.size();
	}
	
	public void evenNum(Node<E> r, ArrayList<E> list) {//using an inorder implementation
		if(r==null) {
			return;
		}
		
		inOrder(r.left,list);
		if((int)r.element %2==0)
			list.add(r.element);
		inOrder(r.right,list);
	}
	
	public ArrayList<E> inOrder() {
		if(root==null) {
			System.out.println("No elements to organize");
			return null;
		}
		
		ArrayList<E> inOrderArray =  new ArrayList<>();
		inOrder(root, inOrderArray);
		return inOrderArray;
	}
	
	public void inOrder(Node<E> r, ArrayList<E> list){ //lnr
		if(r==null) {
			return;
		}
		
		inOrder(r.left,list);
		list.add(r.element);
		inOrder(r.right,list);
		
	}
	
	public ArrayList<E> preOrder() {
		if(root==null) {
			System.out.println("No elements to organize");
			return null;
		}
		
		ArrayList<E> preOrderArray =  new ArrayList<>();
		preOrder(root, preOrderArray);
		return preOrderArray;
	}
	
	public void preOrder(Node<E> r, ArrayList<E> list){ //nlr
		if(r==null) {
			return;
		}
		list.add(r.element);
		preOrder(r.left,list);
		preOrder(r.right,list);
		
	}
	
	public ArrayList<E> postOrder() {
		if(root==null) {
			System.out.println("No elements to organize");
			return null;
		}
		
		ArrayList<E> postOrderArray =  new ArrayList<>();
		postOrder(root, postOrderArray);
		return postOrderArray;
	}
	
	public void postOrder(Node<E> r, ArrayList<E> list){ //lrn
		if(r==null) {
			return;
		}
		postOrder(r.left,list);
		postOrder(r.right,list);
		list.add(r.element);
		
		
	}
	
	/**get an arraylist of numbers as they were originally entered???*/
	//public ArrayList<E> originalList(){
		
		
	//}
	
	
	/** iterative insertion of element e*/
	public void insertIterative(E e) {
		if(root==null) {
			root=new Node<>(e);
			return;
		}
		
		Node<E> curr = root;
		Node<E> parent = root;
		
		while(curr!=null) {
			
			if(curr.element.compareTo(e)>=0) { //not a BST implementation, see previous insert
				parent=curr;
				curr = curr.left;
			}
			else if(curr.element.compareTo(e)<0){
				parent=curr;
				curr=curr.right;
			}
		}
		
		curr = new Node<>(e);
		
		if(curr.element.compareTo(parent.element)>=0) { //not a BST implementation, see previous insert
			parent.right=curr;
		}
		else if(curr.element.compareTo(parent.element)<0){
			parent.left=curr;
		}
	
	}
				

	public E getRoot() {
		if(root==null) {
			System.out.println("no first node");
			return null;
			
		}
		return root.element;
	}
	
	public E getLeft() {
		return root.left.element;
	}
	
	/**calls the recursive method to find the element*/
	public boolean search(E e) {
		return search(root,e);
	}
	
	/** a recursive call to find an element;*/
	private boolean search(Node<E> r, E e) {
		if(r==null) {
			return false;
		}
		
		if(r.element.compareTo(e)>0) {
			return search(r.left,e);
		}
		else if(r.element.compareTo(e)<0){
			return search(r.right,e);
		}
		else {
			return true;
		}
	}
	
	/** an iterative call to search*/
	private boolean searchIterative(E e) {
		
		Node<E> curr = root;
		
		while(curr!=null) {
			if(curr.element.compareTo(e)>0) {
				curr=curr.left;
			}
			else if(curr.element.compareTo(e)<0) {
				curr=curr.right;
			}
			else{
				return true;
			}
		}
		return false;
	}
	
	/**recursive call to search and return the node where element is found*/
	private Node<E> searchIterNode(E e) {
		
		Node<E> curr = root;
		Node<E> parent = root;
		while(curr!=null) {
			if(curr.element.compareTo(e)>0) {
				parent =curr;
				curr=curr.left;
			}
			else if(curr.element.compareTo(e)<0) {
				parent=curr;
				curr=curr.right;
			}
			else{
				return parent;
			}
		}
		return null;
	}
	
	/**calls the recursive deletion*/
	public boolean delete(E e){
		if(searchIterNode(e)==null) {
			System.out.println(e + " cannot be found in the binary tree");
			return false;
		}
		else {
			return delete(searchIterNode(e),e); //will always be true because we confirmed we found it
		}
	}
	
	public boolean delete(Node<E> parent, E e){
		//first case: root is the element to be removed
		if(parent==root) {  //does parent point to the root?
			return deleteRoot();
		}
		
		boolean left = parent.element.compareTo(e)>0; //if parent.elem > e; left will be true; e to be removed is to the left
		Node<E> curr = parent; 
		curr=curr.left; //child class we're looking to remove
		
		//second case: leaf is element to be removed (has no children)
		/** a leaf has no right or left children; call to delete the leaf containing the element*/
		if(curr.left ==null && curr.right==null) {
			return leafDeletion(parent, e, left);
		}
		
		//third case: element whose node has two children is to be removed
		/** remove the node without removing the two children*/
		else if(curr.left !=null && curr.right!=null) {
			return leafParentDouble(parent, e, left); //left is true or false to indicate which side of the parent the child (removing) is.
		}
		
		//fourth case: element whose node has one child is to be removed
		/** remove the node without removing the remaining child*/
		else if(curr.left !=null && curr.right==null || curr.left ==null && curr.right!=null) {
			Node<E> currKid;
			
			if(curr.left !=null && curr.right==null) { //the left node is not null and contains the child we want to keep
				currKid = curr.left; //a pointer to the child we want to keep that's a descendant of the child we want to remove.
			}		
			else {
				currKid = curr.right;
			}
			return leafParentSingle(parent, e,left,currKid);
		}
		
		return true;
	}
	
	public boolean deleteRoot() {
		System.out.println("did nothing");
		return true;
	}
	
	/** we determined the element to be removed is a leaf*/
	
	public boolean leafDeletion(Node<E> parent, E e, boolean left) {
		
		if(left) {
			parent.left=null;
		}
		else {
			parent.right=null;
		}
		
		return true; //node removed containing element.
	}
	
	/** removal of the child we don't want, while keeping it's only offspring*/
	public boolean leafParentSingle(Node<E> parent, E e, boolean left, Node<E> keptKid) {
		
		/*if the child we want to remove is on the left, the parent's left node now points to its child 
		 * --the child we want to keep.
		 */
		if(left) {
			parent.left=keptKid;
		}
		else {
			parent.right=keptKid;
		}
		
		return true;
	}
	
	
	public boolean leafParentDouble(Node<E> parent, E e, boolean left) {
		Node<E> removeChild;
		Node<E> leftChild; //points to removeChild's left child.
		
		if(left) {
			removeChild = parent.left;
		}
		else {
			removeChild = parent.right;
		}
		
		leftChild =removeChild.left;//start of the removing child's left node
		
		Node<E> curr = leftChild;
		
		while(curr!=null) {
			curr=curr.right; //go down the child's left node until it's null;
		}
		
		curr.right= removeChild.right; //take all values at the end of the removing child and add it to the right
		
		if(left) { //if the removing child is on the left, then the parent's left node points to it along with its new nodes
			parent.left = leftChild;
		}
		else {
			parent.right = leftChild;
		}
		
		
		return true;
	}

	/**
	 * main program
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner input=null;
		Queue<Integer> queueNumbers;
		Stack<ABinaryTree2<Integer>> stackTrees;
		
		try {
		/**file contains numbers to be used to create binary trees.*/
			input = new Scanner(new File("numbers.txt")); 			
		}
		catch (Exception e){
			 System.out.println("File not found");
		}
		
		
		/**store all numbers in the file in the queue*/
		
		queueNumbers = new LinkedList<>();
		
		while(input.hasNextInt()) {
			queueNumbers.add(input.nextInt());
		}
		
		//System.out.println("THIS IS THE LIST " + queueNumbers); //DELETE
		
		
		/**create binary trees for each set of numbers*/
		stackTrees = new Stack<>();
		
		ABinaryTree2<Integer> firstThree= new ABinaryTree2<>(); //gets first 3 sets of numbers to add to tree
		ABinaryTree2<Integer> lastThree= new ABinaryTree2<>(); //gets last 3 sets of numbers to add to tree
		
		for(int i=0;i<6;i++) {
			stackTrees.push(new ABinaryTree2<>());
			createBinaryTree(stackTrees,queueNumbers,firstThree, lastThree);
		}
		/**add the last two trees to the stack */
		stackTrees.push(firstThree);
		stackTrees.push(lastThree);
		
		/**add numbers from sets 1, 2, and 3 to a binary tree*/
		
		
		/**display binary tree*/
		System.out.println("TREES CREATED");
		for(int i=0;i<stackTrees.size();i++) {
			System.out.println("\nTree " + (i+1));
			System.out.println("Preorder: "+stackTrees.get(i).preOrder());
			System.out.println("Inorder: "+stackTrees.get(i).inOrder());
			System.out.println("Postorder: "+stackTrees.get(i).postOrder()+"\n");
			System.out.println("total even: "+stackTrees.get(i).getEven()+" total odd: "+stackTrees.get(i).getOdd() );
			System.out.println(stackTrees.get(i).count()+ " total nodes in tree" );
			System.out.println("------------------------------------------------" );
			
			
		}
		
		
		
		
	}
	
	public static void createBinaryTree(Stack<ABinaryTree2<Integer>> stackTrees, Queue<Integer> queueNumbers,
										ABinaryTree2<Integer> firstThree, ABinaryTree2<Integer> lastThree) {
		
		ABinaryTree2<Integer> tree = stackTrees.pop();
		
		Integer inQueue=queueNumbers.remove();
		while(inQueue!=-999) {
			
			try {
				tree.insert(inQueue);
				
				
				/** arranges the group of 3 sets of numbers into their respective binary trees*/ 
				if (stackTrees.size()<3)
					firstThree.insert(inQueue);
				
				else 
					lastThree.insert(inQueue);
				
				
				inQueue = queueNumbers.remove();
			}
			catch(InputMismatchException e){
				System.out.println("Null encountered; Cannot be resolved to inQueue");
				//TEST IF NULL ENCOUNTERED DOES THE WHILE LOOP KEEP RUNNING or EXIT WHILE??
			}
			
		}
		
		stackTrees.push(tree);
		
	}		

}


















