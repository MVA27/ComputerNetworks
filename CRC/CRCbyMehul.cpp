#include<iostream>
using namespace std;

int remainder[100];		//Used to store remainder bits 

void modulo2(int dataArray[],int keyArray[],int absoluteSize,int sizeofKey)
{
    int pointer = sizeofKey;
    int N = sizeofKey;


    //STEP 1 : Create 2 arrays to perform XOR
    int top[N],bottom[N],answer[N];

    //STEP 2 : In top[] load first N data bits
    for(int i = 0 ; i<sizeofKey ; i++)
    {
        top[i] = dataArray[i];
    }

	/*
	STEP 3 : Check first bit of top[] and determine values of bottom[] arrays
		IF fist bit == 1
			- put value of keyArray[] in bottom[]

		ELSE
			- put all zeros
	*/

	//STEP 4 : Perform bit by bit XOR and store the value in answer[] array

	/*
	STEP 5 : Discard MSB of whatever answer you get. i.e IGNORE the value of answer[0]
		i.e Load only answer[1] to answer[N-1] bits back into top[] array
	*/

	//SETP 6 : Add next bit (i.e where pointer is pointing) at LSB of top[] array

	//STEP 7 : Continue in loop until pointer goes out of absoluteSize. if pointer = absoluteSize, that means pointer is pointing out of array

	//STEP 8 : After getting the remainder replace the redundant bits of dataArray to bits present in answer[];

    while(1)
    {

        if(top[0] == 1)
        {
            //If First Bit == 1, load keyArray[] values
            for(int i = 0 ; i<sizeofKey ; i++)
            {
                bottom[i] = keyArray[i];
            }

            //top[] and bottom[] arrays are ready, Now perform XOR and store in answer[]
            for(int i = 0 ; i<sizeofKey ; i++)
            {
                answer[i] = top[i] ^ bottom[i];
            }

            //MSB of answer is IGNORED. THEREFORE DONT CONSIDER 0th bit of answer[]
            //load the answer in top[]
            for(int i = 0 ; i<sizeofKey-1 ; i++) //sizeofKey-1 because last bit will be next bit from data, where my 'pointer' is pointing
            {
                top[i] = answer[i+1]; //IGNORE 0th bit
            }


			if(pointer == absoluteSize)
				break;


            //Finally add next bit in last bit of top[]
			top[sizeofKey-1] = dataArray[pointer];
			pointer++;
        }

        else if(top[0] == 0)
        {
            //If First Bit == 0, load all zeros
            for(int i = 0 ; i<sizeofKey ; i++)
            {
                bottom[i] = 0;
            }

            //top[] and bottom[] arrays are ready, Now perform XOR and store in answer[]
            for(int i = 0 ; i<sizeofKey ; i++)
            {
                answer[i] = top[i] ^ bottom[i];
            }

            //MSB of answer is IGNORED. THEREFORE DONT CONSIDER 0th bit of answer[]
            //load the answer in top[]
            for(int i = 0 ; i<sizeofKey-1 ; i++) //sizeofKey-1 because last bit will be next bit from data, where my 'pointer' is pointing
            {
                top[i] = answer[i+1]; //IGNORE 0th bit
            }


			if(pointer == absoluteSize)
				break;


            //Finally add next bit in last bit of top[]
			top[sizeofKey-1] = dataArray[pointer];
			pointer++;
        }

		else
            {
                cout<<"EXCEPTION : bits in the array are neither 1 nor 0 ";
                return;
            }
    }



	//We have our remainder in answer[] array till here. Fill those values in remainder[] array for sender()/receiver() to use
	for(int i = 1 ; i<sizeofKey ; i++)
	{
		remainder[i-1] = answer[i];
	}
	
	
	
    //==========OUTPUT (In Modulo)===============
	cout<<endl<<"Remainder Generated : In Modulo"<<endl;
	for(int i = 0  ; i<sizeofKey-1 ; i++)
	{
		cout<<remainder[i]<<"\t";
	}
}

void sender()
{
    int sizeofdata,sizeofKey,absoluteSize;

    //STEP 1 : Enter Key
    cout<<"Enter the size of key : ";
    cin>>sizeofKey;
    cout<<endl;

    int keyArray[sizeofKey];
    cout<<"Enter "<<sizeofKey<<" bits of key : ";
    for(int i = 0 ; i<sizeofKey ; i++)
    {
        cin>>keyArray[i];
    }

    cout<<endl;

    //STEP 2 : Enter data
    cout<<"Enter the size of data : ";
    cin>>sizeofdata;
    cout<<endl;

    //STEP 2.1 : Total size = data bits + K-1 redundant bits
    absoluteSize = sizeofdata + (sizeofKey-1);

    int dataArray[absoluteSize];

    //STEP 2.2 : Take non-redundant/data bits bits from user
    cout<<"Enter "<<sizeofdata<<" bits of data : ";
    for(int i = 0 ; i<sizeofdata ; i++)
    {
        cin>>dataArray[i];
    }

    //STEP 2.3 : Append sizeofKey-1 zeros at the end of data
    for(int i = sizeofdata ; i<absoluteSize ; i++)
    {
        dataArray[i] = 0;
    }


    //STEP 3 : Perform Modulo 2 Division and Store remainder in Global Array
    modulo2(dataArray,keyArray,absoluteSize,sizeofKey);


	//Till here we have remainder that needs to be appended at the end of data
	for(int i = sizeofdata,k = 0 ; i<absoluteSize ; i++,k++)
	{
		dataArray[i] = remainder[k];
	}


    //=======================OUTPUT (In Sender)=======================
    cout<<endl<<endl;
	
	cout<<"=== Sender ==="<<endl;
	
    cout<<"Key : ";
    for(int i = 0 ; i<sizeofKey ; i++)
    {
        cout<<keyArray[i]<<"\t";
    }

    cout<<endl<<endl;

    cout<<"Data : ";
    for(int i = 0 ; i<absoluteSize ; i++)
    {
        cout<<dataArray[i]<<"\t";
    }
}

void receiver()
{
	int sizeofKey,sizeofCodeword;
	
	//Take in the Key
	cout<<endl<<"Enter The Size of Key :";
	cin>>sizeofKey;
	
	int keyArray[sizeofKey];
	cout<<endl<<"Enter The Key : ";
	for(int i=0; i<sizeofKey ; i++)
	{
		cin>>keyArray[i];
	}
	
	
	//Take in the Codeword
	cout<<endl<<"Enter The Size of Codeword : ";
	cin>>sizeofCodeword;
	
	int codewordArray[sizeofCodeword];
	cout<<endl<<"Enter The Codeword : ";
	for(int i=0; i<sizeofCodeword ; i++)
	{
		cin>>codewordArray[i];
	}
	
	
	//Perform modulo 2 division
	modulo2(codewordArray,keyArray,sizeofCodeword,sizeofKey);
	
	//if sizeofKey - 1 bits are 0 then the data received if correct 
	bool foundNonZero = false;
	for(int i=0; i<sizeofKey-1 ; i++)
	{
		if(remainder[i] == 1){
			foundNonZero = true;
			break;
		}
	}
	
	if(foundNonZero == true)
		cout<<endl<<"Data Received is invalid"<<endl;
	
	else
		cout<<endl<<"Data Received is valid"<<endl;
}

int main()
{

    sender();
	receiver();

    return 0;
}
