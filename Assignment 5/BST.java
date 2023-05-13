package assignment5prt5;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
/**
 * BST creates a reference to the root of a binary tree (not a binary search tree),
 * allowing us to traverse the tree to answer questions about familial relationships.
 * @author Dacia Simon
 * @param <Person>
 */
public class BST<Person> {

	private BNode<Person> root; 
	
	public BST(Person element) {
		root = new BNode<>(element);
	}
	
	public BST() {
		
	}

	//add a person to the existing tree
	public void add(Person element) {
		if(root==null) {
			root = new BNode<>(element);
			//System.out.println("root is " + root.element); //not necessary
		}
	}
	
	//given a node, add a person to the existing tree
	public void add(BNode<Person> p) {
		if(root==null) {
			root = p;
			//System.out.println("root is " + root.element); //not necessary
		}	
	}
	
	/**
	 * Gtree dictates when this method is called, but the parent's left node 
	 * we want to add the child node to is established
	 * @param parent
	 * @param child
	 * @return BNode<Person>
	 */
	
	public BNode<Person> addLeft(BNode<Person> parent, BNode<Person> child) { 
		if(parent.equals(child)) {
			//System.out.println(parent.equals(child));
			return null;
		}
		
		parent.left=child; 
		//output.println("  left: "+parent.left.element);	
		
		return child;//for eldest child addition
	}
	/**
	 * Gtree dictates when this method is called, but the parent's left node 
	 * we want to add the child node to is established
	 * @param parent
	 * @param child
	 * @return BNode<Person>
	 */
	public BNode<Person> addRight(BNode<Person> parent, BNode<Person> child) { //YES
		if(parent.equals(child)) {
			//System.out.println(parent.equals(child));
			return null;
		}
		parent.right=child;
		//System.out.println("  right: "+ parent.right.element);	
		
		return child;//for younger children addition
	}
	
	public BNode<Person> getRoot() {
		return root;
	}
	
	//an inorder traversal to print the tree; one of two ways to access printing elements.
	public void print(PrintWriter output) {
		inorder(root, output);
	}
	
	//an inorder traversal to print the tree; one of two ways to access printing elements.
	public void inorder(PrintWriter output) {
		inorder(root, output);
	}
	//an inorder traversal to print the tree;Called from one of the two above methods
	public void inorder(BNode<Person> root, PrintWriter output) {
		
		if(root==null) {
			return;
		}
		
		inorder(root.left, output);
		output.print(root.element + " ");
		inorder(root.right, output);
	}
	//call to a preorder traversal to find eldest child.
	public void preorderEldest(PrintWriter output) {
		preorderEldest(root, output);
	}
	
	//preorder traversal to find and print the eldest child.
	public void preorderEldest(BNode<Person> root, PrintWriter output) {
		
		if(root==null) {
			return;
		}
		
		if(root.left==null)
			output.println(root.element + " no children ");
		else
			output.println(root.element  + "'s eldest child " +root.left.element);
		
		preorderEldest(root.left, output);
		
		preorderEldest(root.right, output);
		
	}
	//call to a post-order traversal to find and print the youngest sibling.
	public void postorderYoungestSibling(PrintWriter output) {
		postorderYoungestSibling(root, output);
	}
	//post-order traversal to find and print the youngest sibling.
	public void postorderYoungestSibling(BNode<Person> root, PrintWriter output) {
		if(root==null) {
			return;	
		}
		
		postorderYoungestSibling(root.left, output);
		
		postorderYoungestSibling(root.right, output);
		 
			BNode<Person> current = root;
			BNode<Person> previous = null;
			
			while(current!=null) {
				previous = current; //previous will be last item before null encountered = youngest child
				current = current.right;
			}
			if(root.getElement()!=previous.element)
				output.println(root.getElement() +"'s youngest sibling "+ "is "+previous.element );
			else
				output.println(root.getElement() +" has no siblings");
	}
	
	//call to a post-order traversal method to find all the parent's sons/children.
	public void postorderAllSons(PrintWriter output) {
		postorderAllSons(root, output);
	}
	
	//post-order traversal method to find all the parent's sons/children.
	public void postorderAllSons(BNode<Person> root, PrintWriter output) {
		if(root==null) {
			return;	
		}
		
		postorderAllSons(root.left, output);
		
		postorderAllSons(root.right,output);
		 
			BNode<Person> current = root;
			
			while(current!=null) {
				
				output.print(root.getElement() + "'s child "+current.element + " ");
				current = current.right; //each node on the right of the tree is a child so we print each time
			}
			output.println();
	}
	//call to a post-order traversal method to find all the parent's sons/children.
	public void preorderAllSons(PrintWriter output) {
		preorderAllSons(root,output);
	}
	//post-order traversal method to find all the parent's sons/children.
	public void preorderAllSons(BNode<Person> root, PrintWriter output) {
		if(root==null) {
			return;	
		}
		BNode<Person> current = root;
		BNode<Person> previous = null;
		int i=0;
		
		while(current!=null) {
				previous = root;
				if(i==0) {
					//previous=current; or
					current = current.left;
					if(current==null) {
						output.print(root.getElement() + " 0 children");
						break;
					}
					i++;
					output.print(root.getElement() + "'s child(ren) ");
				}
				//System.out.print(root.getElement() + "'s child "+current.element + " ");
				output.print(current.element + " ");
				current = current.right;
			}
			output.println();
			
		preorderAllSons(root.left, output);
		
		preorderAllSons(root.right, output);
		 
			
	}
	
	//unused; kept for future practice
	/*
	 * public BNode<Person> inorderFindIterative(BNode<Person> root, Person person,
	 * PrintWriter output) { //nlr
	 * 
	 * 
	 * if(root==null) { return null; }
	 * 
	 * String p = person+ "";
	 * 
	 * 
	 * 
	 * BNode<Person> curr = root; Stack<BNode<Person>> stack = new Stack<>();
	 * 
	 * 
	 * while(!stack.isEmpty()||curr!=null) {
	 * 
	 * while(curr!=null) { //lnr? //System.out.println("curr start"+ curr.element);
	 * stack.push(curr); curr=curr.left; //System.out.println("curr end"+
	 * curr.element); }
	 * 
	 * curr = stack.pop();
	 * 
	 * String rt = curr.element + "";
	 * 
	 * output.println("node currently at " + rt);
	 * 
	 * output.println("searching for " + p);
	 * 
	 * if(rt.equals(p)) { output.println("here"); //marker point return curr; }
	 * 
	 * //stack.push(curr.right); curr=curr.right;
	 * 
	 * } return null; }
	 */
	
	//binary tree nodes differs from BNode in that there are left/right people in them
	public static class BNode<Person> {
		private Person element; 
		private BNode<Person> left;
		private BNode<Person> right; 
		
		public BNode(Person element) {
			this.element = element;
		}
		

		public Person getElement() {
			return element;
		}
		
	
	}
	
}

