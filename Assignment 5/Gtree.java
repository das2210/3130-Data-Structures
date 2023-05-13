package assignment5prt5;

import java.util.*;

import assignment5prt5.BST.BNode;

import java.io.*;

/**
 * Program takes in data containing a person's name and the number of children they have.
 * That data is converted into a general tree. The general tree is then converted to a binary 
 * tree to answer questions about each node of the tree
 * @author Dacia Simon
 * @param <Person>
 */
public class Gtree<Person> {

	//root node points to a node that references a person
	private Node<Person> root=null;
	
	public Gtree(Node<Person> root) {
		this.root = root;
	}
	
	//returns root
	public Node<Person> getRoot(){
		return root;
	}
	
	//prints the general tree using a queue to process data 
	public void printGenTree() {
    	
        if (root == null) {
            System.out.println("The tree is empty.");
            return;
        }

        Queue<Node<Person>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
        	Node<Person> node = queue.poll(); //remove the node at the front of queue
        	
            	//System.out.println(node.getElement() + " ");// unnecessary
        	
        
            ArrayList<Node<Person>> children = node.getChildren();//get children of node
            
            for (int i=0;i<children.size();i++) { //iterate through the children of the node
                queue.offer(children.get(i));//add child to the queue
                
            }
         }
       }
    
	//creates a binary tree using a queue
	 public BST<Person> binaryTree() { 
		
	    if (root == null) {
	        System.out.println("The tree is empty.");
	        return null;
	    }
	    
	    BST<Person> btree = new BST();
	    //add contents of the gen tree to an array of BNodes (for access left / right instances)
	    Queue<BNode<Person>> queueB = new LinkedList<>();
	    queueB.offer(new BNode(root.getElement()));
	    
	   //add contents of the gen tree to an array of nodes (for access to the original siblings beyond the node)
	   //done because connected familymembers won't be copied in the bnode; will be lost otherwise.
	    Queue<Node<Person>> queueA = new LinkedList<>();
	    queueA.offer(root);
	    
	
	    while (!queueB.isEmpty()) {
	    
	    	BNode<Person> node = queueB.poll(); //remove the node at the front of queue
	    	Node<Person> n = queueA.poll();
	  
	    	
	    	if(btree.getRoot()==null) {
	          	 btree.add(node); //should only execute once to instantiate the root
	    	}
	        	
	        ArrayList<Node<Person>> children = n.getChildren();//get children of node
	        //System.out.println(node.getElement().getName() +" has " + children.size() + " child(ren)");
			// if(children.size()==0) System.out.println();
			
	        Queue<BNode<Person>> eldest = new LinkedList<>();
	        BNode<Person> eldestNode=null;
	        for (int i=0;i<children.size();i++) { //iterate through the children of the node
	        	
	        	//1st: bnode parent; 2nd:child //
	        	
	        	if(i==0) {
	        		//btree.addLeft(node, person);
	        		//System.out.println("Node " + node.getElement());
	        		if(node.getElement().getName().equals(children.get(i).getElement().getName())){
	        			System.out.println("same");
	        			break;
	        		}
	        		eldestNode = btree.addLeft(node, new BNode(children.get(i).getElement()));
	        		//System.out.println("ELDEST IS "+eldestNode.getElement());
	        		//System.out.println("ELDEST CHILD " + children.get(i).getElement());
	        		eldest.offer(eldestNode);
	        	}
	        	
	        	else {
	        		//System.out.println("Node " + eldestNode.getElement());
	        		//btree.addLeft(node, person);
	        		if(node.getElement().getName().equals(children.get(i).getElement().getName())){
	        			System.out.println("same >0");
	        			break;
	        		}
	        		
	        		eldestNode=btree.addRight(eldest.poll(), new BNode(children.get(i).getElement()));
	        		//System.out.println("BABYCHILDREN " + children.get(i).getElement());
	        		eldest.offer(eldestNode);
	        		
	        	}
	        		
	        	
	        	//queueB.offer(new BNode(children.get(i).getElement())); semi-worked
	        	//queueA.offer(children.get(i)); semi-worked 
	        	queueB.offer(eldestNode);
	        	queueA.offer(children.get(i));

	        }
	     }
	 
	    return btree;
	   }
	
	/**
	 * Nodes contain the element and references to a parent node and an
	 * arraylist to be able to create the general tree
	 * @author Dacia Simon
	 *
	 * @param <Person>
	 */
	public static class Node<Person>{
		private Person element;
		private Node<Person> parent;
		private ArrayList<Node<Person>> children;
		
		public Node(Person element) {
			this.element = element;
			children = new ArrayList<>();
		}
		
		
		public Node<Person> getParent() {
			return parent;
		}
		public Person getElement() {
			return element;
		}
		
		public void addChild(Node<Person> person) {
			person.setparent(this);
			children.add(person);
			
		}
		
		public void setparent(Node<Person> parent) {
			this.parent=parent;
		}
		
		public ArrayList<Node<Person>> getChildren(){
			return children;
		}
		
	}
	
	
	 
	/**
	 * Each row of data in the imported file refers to info about a person,
	 * so we create a person class to store that data
	 * @author Dacia Simon
	 *
	 */		
	public static class Person { // static

		private String name;
		private int numChild;

		public Person(String name, int numChild) {
			this.name = name;
			this.numChild = numChild;
		}

		//get the num of children this person has
		public int getnumChild() {
			return numChild;
		}

		//get this person's name
		public String getName() {
			return name;
		}

		public String toString() {
			return "[" + name + ", " + numChild + "]"; // remove \n
		}
		
		//comparing people based on name(String)
		public boolean equals(Person p) {
			if(this.getName().equals(p.getName())) {
				return true;
			}
			return false;
		}
		
	}
	

	public static void main(String[] args) {
		
		genToBinary("default_Family.txt","output_Family1.txt");
		
		genToBinary("default_Family2.txt","output_Family2.txt");
		
		genToBinary("default_Family3.txt","output_Family3.txt");
		
		System.out.println("Program complete");
	}

	/**
	 * General to binary conversion
	 * @param inputA inputfile to read
	 * @param outputB output file to print to
	 */
	public static void genToBinary(String inputA, String outputB) {
			List<Person> list1 = new ArrayList<>();
			
			try {
				Scanner input = new Scanner(new File(inputA));
				PrintWriter output = new PrintWriter(new File(outputB));
				
				//add the line read in to the list as an instancee of a person
				while (input.hasNext()) {
					String name = input.next();
					Integer numChild = Integer.parseInt(input.next());
					list1.add(new Person(name, numChild));
				}
				output.println("ORIGINAL DATA: \n\n" + list1);
				output.println("\n");

				int i = 0;
				List<Person> listsub = new ArrayList<>();// holds initial list of each parent-sibling
				List<List<Person>> listfin = new ArrayList<>();// holds final list of all parent-siblings
				int index = 0;// must initialize

				while (i < list1.size()) {
					
					int numChild = list1.get(i).getnumChild();
					int c = 0;
					if (numChild == 0) { //if no kids add to final list immediately; index unchanged
						listfin.add(Arrays.asList(list1.get(i)));					
					}

					else if (numChild > 0) {

						listsub.add(list1.get(i));

						while (c < numChild) { // find the child in the arraylist

							listsub.add(list1.get(index + 1));
							
							index += 1;
							c++;
						}

						listfin.add(listsub); //add the sublist of items to the final list

						listsub = new ArrayList<>(); //clear listsub
					}

					else {
						System.out.println("negative num; invalid");
						System.exit(0);// terminated well
					}

					i++;

				}
				output.println("GENERAL TREE \n(parent-children groupings represented in an arraylist) " + "\n\n" + listfin+"\n");
				
				
				//create a tree based on the arraylist that tells us the parent (first index) and their children, if any)
				
				
				//initialize the root 
				
				Gtree<Person> tree1 = new Gtree(new Node(listfin.get(0).get(0)));
				
				//initialize the root's kids - general tree
				
				for(int j=1;j<listfin.get(0).size();j++) {
					tree1.getRoot().addChild(new Node(listfin.get(0).get(j)));
				}
				
				
				//add additional children based on root/roots children - general tree
				for(int a=1; a<listfin.size();a++) {
					//find the parent that may be assigned children
					Node<Person> parent = find(tree1.getRoot(), listfin.get(a).get(0));
					for(int z=1; z<listfin.get(a).size();z++) {
						parent.addChild(new Node(listfin.get(a).get(z)));
					}
				}
				
		
				
				//create a binary tree based on the general tree
				
				BST<Person> tree2Binary = tree1.binaryTree();
				
				
				output.println("\nTHE BINARY TREE INORDER:\n");
				tree2Binary.print(output);
				
				
				
				output.println("\n\nELDEST CHILD OF GIVEN PARENT:\n");
				tree2Binary.preorderEldest(output);
				
				output.println("\n\nYOUNGEST SIBLING AMONG SIBLINGS:\n");
				tree2Binary.postorderYoungestSibling(output);
				
				output.println("\n\nALL CHILDREN OF GIVEN PARENT\n");
				tree2Binary.preorderAllSons(output);
				
				
			input.close();
			output.close();
			}

			catch (IOException e) {
				System.out.println("File not found");
			}
			
		
		}

	//locate the node in the existing tree and return once found	
	public static Node<Person> find(Node<Person> rt, Person person) {
		if(rt.getElement().equals(person)) {
			return rt;
		}
		else {
			for(int i=0;i<rt.getChildren().size();i++) {
				Node<Person> found = find(rt.getChildren().get(i), person);
				
				if(found!=null) {
					return found;
				}
			}
		}
		return null;
	}
	
}
