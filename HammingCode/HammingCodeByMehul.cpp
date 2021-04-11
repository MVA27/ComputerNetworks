//Hamming Code based on Even Parity
#include<iostream>
#include<math.h>	 // For pow()
#include<cstdlib>	// For abs()
using namespace std;

void display(int arr[],int size)
{
	for(int i = 0 ; i< size ; i++)
	{
		cout<<arr[i]<<"\t";
	}
	cout<<endl;
}

void sender()
{
	//=============PHASE 1=================
	//Take Size of Data Array
	int sizeofData;
	cout<<"Enter The Size Of Data : ";
	cin>>sizeofData;
	cout<<endl;

	//Enter The Data
	int dataArray[sizeofData];
	cout<<"Enter The "<<sizeofData<<" bits Of Data : ";
	for(int i = 0 ; i< sizeofData ; i++)
	{
		cin>>dataArray[i];
	}
	cout<<endl;

	//Display The Data
	cout<<"Data From User: ";
	display(dataArray,sizeofData); 
	
	//Calculate Number of redundant bits to be appended
	int redundantBits;
	for(int r = 0 ;  ; r++)	//Infinite Loop
	{
		if(pow(2,r) >= sizeofData + r + 1)
		{
			redundantBits = r;
			break;
		}
	}

	//=============PHASE 2=================
	//We have size of data + number of redundant bits which is == Final size of code word
	//Therefore create a new array of size sizeofData(n) + redundantBits(r)
	int finalSize = sizeofData + redundantBits;
	int codeword[finalSize];
	for(int i = 0 ; i< finalSize ; i++)	//Initialize with 0
	{
		codeword[i] = 0;
	}

	//Insert the parity bits at power of 2 position (in this case, parity bits will be denoted by -ve integers)
	for(int i = 0 ; i<redundantBits ; i++)	//Infinite Loop
	{
		int pIndex = pow(2,i);
		codeword[pIndex-1] = -1 * (pIndex);	//pIndex-1 as array index starts from 0
	}
	
	
	cout<<"Code word Only With Redundant Bits : "<<endl;
	display(codeword,finalSize);
	
	
	//Now where every there is no negative sign put the user data bits
	int pointer = 0; //pointer to dataArray to extract every bit one by one
	for(int i = 0 ; i< finalSize ; i++)	
	{
		if(codeword[i] >= 0) //Means bit is +ve and is not a parity bit. Therefore insert user bit
		{
			codeword[i] = dataArray[pointer];
			pointer++;
		}
	}
	cout<<endl;

	cout<<"Code word With Redundant Bits and Data bits : "<<endl;
	display(codeword,finalSize);
	
	
	//=============PHASE 3=================
	/* 	
		1. Select alternative bits 
		2. Count no. of 1s
		3. manuplate ith bit in codeword[]
	*/
	for(int i = 0 ; i< finalSize ; i++)	//Traverse Each Bit One by One
	{
		int executeTill,numberOfOnes = 0;
		if(codeword[i] < 0)	//If The Bit is -ve That Means its Redundant bit and its mod/abs value gives how many bits to consider and skip
		{
			executeTill =  abs(codeword[i]);
			cout<<endl<<"-ve value found : "<<codeword[i]<<endl;
		}
		
		else	//If its not -ve that means its data bit and skip the iteration
			continue;
		
		int pointer = i; //var pointer points to -ve bit in codeword[]
		int counter = 0; //var counter to increment uptil var executeTill
		
		while(pointer < finalSize)
		{
			//start from current bit and consider next bits upto executeTill and till pointer is not pointing outside codeword[]
			for(pointer ; counter < executeTill && pointer < finalSize ; pointer++) 
			{
				
				cout<<endl<<"conside : "<<codeword[pointer]<<"at index = "<<pointer+1<<"\t";
				
				if(codeword[pointer] == 1){
					numberOfOnes++;
				}
				
				
				counter++;
			}		
			counter = 0;
			cout<<endl;
			
			//start from current bit and skip next bits upto executeTill and till pointer is not pointing outside codeword[]
			for(pointer ; counter < executeTill && pointer < finalSize ; pointer++)
			{
				
				//cout<<endl<<"skip : "<<codeword[pointer]<<"at index = "<<pointer+1<<"\t";
				counter++;
			}
			counter = 0;	
			cout<<endl;
		}		
	
		if(numberOfOnes % 2 == 0)//Even number of 1s exists. Therefore put 0 to keep it Even
		{
			codeword[i] = 0;
		}
		
		else	//Odd number of 1s exists. Therefore put 1 to make it Even
		{
			codeword[i] = 1;
		}
	}

	//=============PHASE 4=================
	cout<<"Final Code word That Will be sent to Receiver : "<<endl;
	display(codeword,finalSize);
}


void receiver()
{
	// PHASE 1 :: Take Size of Codeword
	int sizeofCodeword;
	cout<<"Enter The Size Of Code word : ";
	cin>>sizeofCodeword;
	cout<<endl;

	//Enter The Code word
	int codeword[sizeofCodeword];
	cout<<"Enter The "<<sizeofCodeword<<" bits Of Data : ";
	for(int i = 0 ; i< sizeofCodeword ; i++)
	{
		cin>>codeword[i];
	}
	cout<<endl;
	
	display(codeword,sizeofCodeword);
	
	
	// PHASE 2 :: Detect Error ie Locate the redundant/parity bits and check its parity
	int pIndex = 0,pointer = 0;
	bool isEven = false,isOdd = false; //Will determine if a redundent bit is Even of Odd parity
	int numberOfParityBits = 0,parityArray[100];
	for(int i = 0 ; i<sizeofCodeword ; i++)	//Infinite Loop
	{
		pIndex = pow(2,i);	//Determine power of 2 value (which gives executeTill value)
		pointer = pIndex-1;	//Determine actual index  (which gives starting index value for loop)
		
		
		if(pIndex > sizeofCodeword)	//If power of 2 is > size of whole codeword then break
		{
			break;
		}

		numberOfParityBits++; //Will count number of parity bits in codeword[]

		int executeTill = pIndex,numberOfOnes = 0;
		int counter = 0; //var counter to increment uptil var executeTill
		
		
		while(pointer < sizeofCodeword)
		{
			//start from current bit and consider next bits upto executeTill and till pointer is not pointing outside codeword[]
			for(pointer ; counter < executeTill && pointer < sizeofCodeword ; pointer++) 
			{
				
				//cout<<endl<<"conside : "<<codeword[pointer]<<"at index = "<<pointer+1<<"\t";
				if(codeword[pointer] == 1)
				{
					numberOfOnes++;
				}
	
				counter++;
			}		
			counter = 0;
			cout<<endl;

			//start from current bit and skip next bits upto executeTill and till pointer is not pointing outside codeword[]
			for(pointer ; counter < executeTill && pointer < sizeofCodeword ; pointer++)
			{
				counter++;
			}
			counter = 0;	
			cout<<endl;
		}
		
		if(numberOfOnes % 2 == 0)//Even number of 1s exists. (Therefore put 0 to keep it Even Theoritically)
		{
			isEven = true;	//Currenty has no purpose. But will be helpful if program is changed to support Odd parity
			parityArray[numberOfParityBits-1] = 0;
			cout<<"EVEN"<<endl;
		}
		
		else	//Odd number of 1s exists. (Therefore put 1 to make it Even Theoritically)
		{
			isOdd = true;	//A bit contains odd number of 1s, Therefore make a note of it
			parityArray[numberOfParityBits-1] = pIndex;
			cout<<"ODD"<<endl;
		}
	
		
		//Check if even one redundant bit has Odd parity
		if(isOdd == true)
		{
			//Current bit contains Odd number of 1s. Therefore our Assumption is wrong and msg contains ERROR
			cout<<"**ERROR DETECTED**"<<endl;
		}
		
		else{
			cout<<"Message is ERROR FREE"<<endl;
		}
	}


	//PHASE 3 :: Correct Error
	cout<<endl<<"No. of P Bits = "<<numberOfParityBits<<endl;
	display(parityArray,numberOfParityBits);
	
	cout<<endl<<"codeword before correction"<<endl;
	display(codeword,sizeofCodeword);
	
	int errorBit = 0;
	for(int i = 0 ; i<numberOfParityBits ; i++)
	{
		errorBit = errorBit + parityArray[i];
	}
	
	if(codeword[errorBit-1])
	{
		codeword[errorBit-1] = 0;
	}
	
	else{
		codeword[errorBit-1] = 1;
	}
	
	cout<<endl<<"codeword After correction"<<endl;
	display(codeword,sizeofCodeword);
	
}


int main()
{

	//sender();
	receiver();

	return 0;
}
