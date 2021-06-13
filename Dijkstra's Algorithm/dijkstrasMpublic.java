import java.util.*;

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

class Test
{
	static Node currentMinimum = null;											  
	static Node smallest = null;												 
	static LinkedList<Integer> ignoreColumnList = new LinkedList<Integer>();; 	
	static LinkedList<Node> traceList = new LinkedList<Node>();
	
	static int[][] relation ;
								
	static int[][] distance ;

	public static void initMatrix(Node[][] nodeMatrix,char sourceNode){
		
		int sourceColumnNumber = sourceNode - 65;
		
		for(int i = 0 ; i<nodeMatrix.length ; i++){
			for(int j = 0 ; j<nodeMatrix[i].length ; j++){
				
				if(j == sourceColumnNumber && i == 0)
					nodeMatrix[i][j] = new Node(0,sourceNode,j,false);
				
				else
					nodeMatrix[i][j] = new Node(j);
			}
		}
		
	}

	public static Node min(Node topNode,Node currentNode,int sourceRowIndex,int destinationColumnIndex){
		
		int weight = distance[sourceRowIndex][destinationColumnIndex];
		
		currentNode.columnName =  (char) (65 + sourceRowIndex);
		
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
	
	public static Node setSmallest(Node[][] nodeMatrix,int i){
		
		Node tempSmallest =  nodeMatrix[i][0];
		
		for(int j = 0 ; j < nodeMatrix[i].length ; j++){
			if(nodeMatrix[i][j].value <=  tempSmallest.value){
				tempSmallest = nodeMatrix[i][j]; 
			}
		}

		for(int j = 0 ; j < nodeMatrix[i].length ; j++){
			
			if(nodeMatrix[i][j].value ==  tempSmallest.value){
				currentMinimum = tempSmallest;		 			
				nodeMatrix[i][j].isFinal = true; 	  			
				ignoreColumnList.add(j) ;						
				break;
			}
		}
			
		return tempSmallest;
	}
	
	//main row wise traversal
	public static void core(Node[][] nodeMatrix){
		
		for(int i = 1 ; i < nodeMatrix.length ; i++){
			
			smallest = setSmallest(nodeMatrix,i-1);
			
			for(int j = 0 ; j < nodeMatrix[i].length ; j++){
				
				if(i == nodeMatrix.length - 1) {
					nodeMatrix[i][j].value = nodeMatrix[i-1][j].value;
					nodeMatrix[i][j].columnValue = nodeMatrix[i-1][j].columnValue;
					nodeMatrix[i][j].columnName = nodeMatrix[i-1][j].columnName;
				}
				
				if(ignoreColumnList.contains(j)){	
					continue;
				}
				
				try{					
					if(relation[currentMinimum.columnValue][j] == 1){
						nodeMatrix[i][j] = min(nodeMatrix[i-1][j],nodeMatrix[i][j],currentMinimum.columnValue,j);
					}
					
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
		}
		
	}
	
	public static void finalFilter(Node[][] nodeMatrix){
		
		for(int k = 1 ; k<=nodeMatrix.length ; k++ ){	
			
			Node temp = new Node();	
			for(int i = 0 ; i<nodeMatrix.length ; i++ )
			{
				for(int j = 0 ; j<nodeMatrix[i].length ; j++ )
				{
					if(j == 0){	
						temp = nodeMatrix[j][i];
					}
					
					
					if(nodeMatrix[j][i].value <= temp.value){ 
						temp = nodeMatrix[j][i];
					}
					
				}
				
				temp.isFinal = true;
			}
		}
		
	}
	
  public static void trace(Node[][] nodeMatrix,String input){
		
		input = input.toUpperCase();
		
		int src = input.charAt(0);
		int dest = input.charAt(1);
		int tempDest = input.charAt(1);	
		
		System.out.print(((char)src)+"\t"+(char)dest+"\t");
		
		String path = ""+(char)dest;
		int cost = 0;
		
		src = src - 65;
		dest = dest - 65;
		tempDest = dest;
		
		OUTER:while(true)								
		{
			
			for(int i = 0 ; i<nodeMatrix.length ; i++){	
				
				if(nodeMatrix[i][dest].isFinal)
				{
					Node node = nodeMatrix[i][dest];	
					path = path+node.columnName;		
					
					if(dest == tempDest){				
						cost = cost + node.value;		
					}
					
					dest = node.columnName - 65;		
					
					if(dest == src)
						break OUTER;
				}
			}
		}
		
		System.out.print(cost+"\t");
		
		for(int i = path.length()-1 ; i>=0 ; i--){
			
			System.out.print(path.charAt(i));
			if(i != 0) 
				System.out.print("->");
		}
		System.out.println();
	}
	
	public static void main(String[] args){
		
		int length = getLength();	
		
		char[] nodeArray = new char[length];
		
		displayTotalNodesCreated(length,nodeArray);
		
		relation = new int[length][length];
		distance = new int[length][length];
		
		for(int i = 0 ; i<relation.length ; i++){
			
			char rowChar = (char) (65 + i);
			System.out.println("Enter the data for node "+rowChar+": ");
			
			for(int j = 0 ; j<relation[i].length ; j++){
				
				char colChar = 65;
				
				if(relation[i][j] == 1)
					continue;
				
				if(i == j){	
					relation[i][j] = 1;
					distance[i][j] = 0;
				}
				
				else{
					System.out.print("Enter '1' if there is an edge from  "+rowChar+"->"+((char)(colChar+j))+" Else enter '0' : ");
					Scanner s = new Scanner(System.in);
					int input = s.nextInt();
					System.out.println();
					
					if(input == 1){
						
						relation[i][j] = 1;
						relation[j][i] = 1;	
						
						System.out.print("Enter the cost to go from "+rowChar+"->"+((char)(colChar+j))+" : ");
						s = new Scanner(System.in);
						input = s.nextInt();
						distance[i][j] = input;
						distance[j][i] = input;
						
						System.out.println();
					}
				}
				
			}
		}
		
		Node[][] nodeMatrix = new Node[length+1][length+1];
		
		initMatrix(nodeMatrix,sourceChar);
		
		core(nodeMatrix);
		
		finalFilter(nodeMatrix);

		System.out.println("SRC\tDEST\tCOST\tPATH");
		for(int i = 0 ; i < nodeArray.length ; i++){
			
			if(sourceChar != nodeArray[i]){
				trace(nodeMatrix,sourceChar+""+nodeArray[i]);
			}
			
		}
	}

	public static int getLength(){
		Scanner s = new Scanner(System.in);
		System.out.print("Enter Number of Nodes : ");
		int length = s.nextInt();
		System.out.println();
		return length;
	}
	
	public static char getSourceChar(){
		Scanner s = new Scanner(System.in);
		System.out.print("Enter The Source Node : ");
		char ch = s.next().charAt(0);
		System.out.println();
		return ch;
	}

	public static void displayTotalNodesCreated(int length,char[] nodeArray){
		char character = 65 ;	//UNICODE of A
		System.out.println("Nodes created are : ");
		for(int i=0; i<length ; i++){
			nodeArray[i] = (char)(character+i);
			System.out.print(nodeArray[i]+"\t");
		}
		System.out.println();
	}
}

