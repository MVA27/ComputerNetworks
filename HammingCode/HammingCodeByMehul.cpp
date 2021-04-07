#include<iostream>
#include<math.h>
using namespace std;

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
	cout<<"Data : ";
	for(int i = 0 ; i< sizeofData ; i++)
	{
		cout<<dataArray[i]<<"\t";
	}
	cout<<endl;

	
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
	
	
	cout<<"Code word : ";
	for(int i = 0 ; i< finalSize ; i++)	
	{
		cout<<codeword[i]<<"\t";
	}
	cout<<endl;
	
	
	//Now where every there is no negative sign put the user data bits
	int pointer = 0; //pointer to dataArray to extract every bit one by one
	for(int i = 0 ; i< finalSize ; i++)	
	{
		if(codeword[i] >= 0) //Means bit is +ve and is not a parity bit. Therefore inser user bit
		{
			codeword[i] = dataArray[pointer];
			pointer++;
		}
	}
	cout<<endl;

	cout<<"Code word : ";
	for(int i = 0 ; i< finalSize ; i++)	
	{
		cout<<codeword[i]<<"\t";
	}
	cout<<endl;
	
	
	//=============PHASE 3=================
}

int main()
{

	sender();

	return 0;
}
