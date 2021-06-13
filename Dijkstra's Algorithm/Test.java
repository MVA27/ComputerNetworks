import java.util.*;

/*
	value : integer value that represents minimum distance
	columnName : represents for which row the node value has been altered
	columnValue : represents column index of the character in integer 
	isFinal : tells that any node in the 'column' below current node is canceled
*/
class Node
{
	int value;
	char columnName;
	int columnValue;
	boolean isFinal = false;
	
	Node(){ //useful in local area t create temp objects
		
	}
	
	Node(int columnNumber){
		this(2147483647,'#',columnNumber,false);	//Default values
	}
	
	Node(int value,char columnName,int columnValue,boolean isFinal){
		this.value = value;
		this.columnName = columnName;
		this.columnValue = columnValue;
		this.isFinal = isFinal;
	}
	
	public String toString(){
		
		if(this.value == 2147483647)
			return "INF";
		
		else{
			return this.value+""+this.columnName;
		}
	}
	
}

class Hello
{
	static Node currentMinimum = null;											  // Points to smallest vlaue object to track columns
	static Node smallest = null;												 // Points to smallest vlaue object to track rows
	static LinkedList<Integer> ignoreColumnList = new LinkedList<Integer>();; 	// List of columns that have to be ignored
	static LinkedList<Node> traceList = new LinkedList<Node>();
	
	static int[][] relation = {{1,1,0,0,0,0,1,0},  //A
								{1,1,1,0,1,0,0,0}, //B
								{0,1,1,1,0,0,0,0}, //C
								{0,0,1,1,0,0,0,1}, //D
								{0,1,0,0,1,1,1,0}, //E
								{0,0,1,0,1,1,0,1}, //F
								{1,0,0,0,1,0,1,1}, //G
								{0,0,0,1,0,1,1,1}};//H
								
	static int[][] distance = {{0,2,0,0,0,0,6,0},  //A
								{2,0,7,0,2,0,0,0}, //B
								{0,7,0,3,0,3,0,0}, //C
								{0,0,3,0,0,0,0,2}, //D
								{0,2,0,0,0,2,1,0}, //E
								{0,0,3,0,2,0,0,2}, //F
								{6,0,0,0,1,0,0,4}, //G
								{0,0,0,2,0,2,4,0}};//H
	
	
	public static void printMatrix(Node[][] nodeMatrix){
		
		for(Node[] n : nodeMatrix){
			for(Node eachNode : n){
				System.out.print(eachNode+"\t");
			}
			System.out.println();
		}
		
		System.out.println();
	}
	
	public static void printMatrix(int[][] nodeMatrix){
		
		for(int[] n : nodeMatrix){
			for(int eachNode : n){
				System.out.print(eachNode+"\t");
			}
			System.out.println();
		}
		
		System.out.println();
	}
	
	/*
		Initialize all cells of matrix with object
		Initialize source object with value 0
		For each object note its column value
	*/
	public static void initMatrix(Node[][] nodeMatrix,char sourceNode){
		
		int sourceColumnNumber = sourceNode - 65;
		
		for(int i = 0 ; i<nodeMatrix.length ; i++){
			for(int j = 0 ; j<nodeMatrix[i].length ; j++){
				
				if(j == sourceColumnNumber && i == 0)
					nodeMatrix[i][j] = new Node(0,sourceNode,j,false);
				
				else
					nodeMatrix[i][j] = new Node(j);
				
				System.out.print(nodeMatrix[i][j]+"\t");
			}
			System.out.println();
		}
		
	}
	
	/*
		For a given calculate the minimum value by comparing it with top row value
		sourceRowIndex = currentMinimum.ColumnValue 
		destinationColumnIndex = j
		
	*/
	public static Node min(Node topNode,Node currentNode,int sourceRowIndex,int destinationColumnIndex){
		
		//Find the distance/weight between source(pointed by currentMinimum) and destination (determined based on column value of nodeMatrix i.e j)
		int weight = distance[sourceRowIndex][destinationColumnIndex];
		
		// set the currentNode.columnNumber = sourceRowIndex (currentMinimum.ColumnValue)
		currentNode.columnName =  (char) (65 + sourceRowIndex);
		System.out.println("weight "+weight);
		
		if(weight + currentMinimum.value < topNode.value){
			currentNode.value = weight + smallest.value;
			return currentNode;
		}
		
		else{
			currentNode.value = topNode.value;
			currentNode.columnName = topNode.columnName;
			return currentNode;
		}
	}
	
	/*
		set smaller and currentMinimum pointer which is basicly pointing to the same object
		But smaller is for value comparison when calculating min
		And currentMinimum is used to check columnName as a column vertex because
			smallest value is actually the row vertex
	*/
	public static Node setSmallest(Node[][] nodeMatrix,int i){
		
		//Assume first object is slammest
		Node tempSmallest =  nodeMatrix[i][0];
		
		//Ckeck if the assumption is wrong
		for(int j = 0 ; j < nodeMatrix[i].length ; j++){
			if(nodeMatrix[i][j].value <=  tempSmallest.value){
				tempSmallest = nodeMatrix[i][j]; //if the assumption is wrong, correct it
			}
		}


		//Note down the column number having smallest value in a row
		for(int j = 0 ; j < nodeMatrix[i].length ; j++){
			
			if(nodeMatrix[i][j].value ==  tempSmallest.value){
				currentMinimum = tempSmallest;		 			//Global value
				nodeMatrix[i][j].isFinal = true; 	  			//Final value
				ignoreColumnList.add(j) ;						//Add column no. in ignore list
				break;
			}
		}
			
		return tempSmallest;
	}
	
	//main row wise traversal
	public static void core(Node[][] nodeMatrix){
		
		for(int i = 1 ; i < nodeMatrix.length ; i++){
			
			
			
			System.out.println("For i = "+i);
			
			//Find Shortest element of previous row and cut the column
			smallest = setSmallest(nodeMatrix,i-1);
			
			System.out.println("small for i = "+smallest);
			
			
			for(int j = 0 ; j < nodeMatrix[i].length ; j++){
				
				if(i == nodeMatrix.length - 1) {	//if true then this is last iteration where there is nothing to compare. Threfore set the top value
					nodeMatrix[i][j].value = nodeMatrix[i-1][j].value;
					nodeMatrix[i][j].columnValue = nodeMatrix[i-1][j].columnValue;
					nodeMatrix[i][j].columnName = nodeMatrix[i-1][j].columnName;
				}
				
				if(ignoreColumnList.contains(j)){	//ignore cloumn
					System.out.println("ignoring column = "+j);
					continue;
				}
				
				try{
					System.out.println("rel "+currentMinimum.columnValue+" "+j);
					
					//If an edge/relation exists find minimum
					if(relation[currentMinimum.columnValue][j] == 1){
						
						//Last to parameter is used to retrive data from distance[][] matrix
						nodeMatrix[i][j] = min(nodeMatrix[i-1][j],nodeMatrix[i][j],currentMinimum.columnValue,j);
					}
					
					//else update it with top column values
					else{
						nodeMatrix[i][j].value = nodeMatrix[i-1][j].value;
						nodeMatrix[i][j].columnValue = nodeMatrix[i-1][j].columnValue;
						nodeMatrix[i][j].columnName = nodeMatrix[i-1][j].columnName;
					}
					
				}
				catch(Exception e){
					continue;
				}
				
			}
			
			//smallest = setSmallest(nodeMatrix,i-1);
			//printMatrix(nodeMatrix);
			System.out.println();
		}
		System.out.println("Smallest = "+smallest);
		System.out.println("Ignore List = "+ignoreColumnList);

		printMatrix(nodeMatrix);
	}
	
	//set isFinal to true
	public static void finalFilter(Node[][] nodeMatrix){
		
		for(int k = 1 ; k<=nodeMatrix.length ; k++ ){	//Perform each node
			
			Node temp = new Node();	
			for(int i = 0 ; i<nodeMatrix.length ; i++ )
			{
				for(int j = 0 ; j<nodeMatrix[i].length ; j++ )
				{
					if(j == 0){	//Assume first node to be minimum ie. 0th row
						temp = nodeMatrix[j][i];
					}
					
					
					if(nodeMatrix[j][i].value <= temp.value){ // If any other node is less then that make temp to point to that node
						temp = nodeMatrix[j][i];
					}
					
				}
				
				//Finally where ever temp is pointing, make that node final i.e there is no node below that in the table
				temp.isFinal = true;
			}
		}
		
	}
	
	//trace all the nodes with isFinal=true attributes
	public static void trace(Node[][] nodeMatrix,String input){
		
		input = input.toUpperCase();
		
		//Index values required to perform traversal 
		int src = input.charAt(0);
		int dest = input.charAt(1);
		int tempDest = input.charAt(1);	//Just to check if the row wise traversel is of 'dest' column (helps to determine cost)
		
		//Add the destination already and add weight/cost 
		String path = ""+(char)dest;
		int cost = 0;
		
		//input will be like "AB" take each character and find its corresponding index value
		src = src - 65;
		dest = dest - 65;
		tempDest = dest;
		
		//System.out.println(src);
		//System.out.println(dest);
		
		//Go to each column and find nodes having isFinal=true
		//Do this until src=dest and stop
		OUTER:while(true)								// For all row traversal
		{
			
			for(int i = 0 ; i<nodeMatrix.length ; i++){	// Fach row wise iteration
				
				if(nodeMatrix[i][dest].isFinal)
				{
					Node node = nodeMatrix[i][dest];	//Point to required node
					path = path+node.columnName;		//Add columnName to path
					
					if(dest == tempDest){				//If this is not done, cost value will be calculated again and again 
						cost = cost + node.value;		//Note the value of the node having attribute isFinal=true (that gives cost)
					}
					
					dest = node.columnName - 65;		//That columnName's column index number is the next destination for next iteration
					
					if(dest == src)
						break OUTER;
				}
			}
		}
		
		//Tracing starts from reverse. therefore reverse the path String
		for(int i = path.length()-1 ; i>=0 ; i--){
			
			System.out.print(path.charAt(i));
			if(i != 0) 
				System.out.print("->");
		}
		
		System.out.println("\tcost = "+cost);
	}
	
	public static void main(String[] args){
		
		
		int length = 8;
		
		
		
		/*
			1 extra row added which is reserved for initial values
			1 extra column added which is unwanted and exists to make the matrix square which then 
				avoids Runtime exception in finalFilter() method {where i and j are swapped}
		*/
		Node[][] nodeMatrix = new Node[length+1][length+1];
		
		
		
		printMatrix(relation);
		
		printMatrix(distance);
		
		
		
		/*
			Initialize the matrix
				- by setting Source node value 0
				- and all other values as INF (i.e infinity)
			
			At the same time, Other attributs like columnName might be set
		*/
		initMatrix(nodeMatrix,'A');
		
		/*
			Perform core functionality
				- Traverse through every row starting from index 1
				- call min() method 
					-> which calculates minimum 
					-> adds cost value
					-> compares it with top node value
					
				- checks if relation/edge exists :- if it doesnt exist, sets top node walue
		*/
		core(nodeMatrix);
		
		/*
			After core part is done this method identifies the minimum value in a 'column'
			and sets its isFinal value to be true 
		*/
		finalFilter(nodeMatrix);
		
		/*
			Up till here the matrix is ready
			Last part is traversing the matrix and identify all the nodes with isFinal=true 
		*/
		trace(nodeMatrix,"AB");
		trace(nodeMatrix,"AC");
		trace(nodeMatrix,"AD");
		trace(nodeMatrix,"AE");
		trace(nodeMatrix,"AF");
		trace(nodeMatrix,"AG");
		trace(nodeMatrix,"AH");
		
	}
}

