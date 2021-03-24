#include<iostream>
using namespace std;

void modulo2(int dataArray[],int keyArray[],int absoluteSize,int sizeofKey)
{
    int pointer = sizeofKey;
    int N = sizeofKey;

    cout<<pointer<<endl;

    //Create 2 arrays to perform XOR
    int top[N],bottom[N],answer[N];

    //In top[] load first N data bits
    for(int i = 0 ; i<sizeofKey ; i++)
    {
        top[i] = dataArray[i];
    }

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

            //Finally add next bit in last bit of top[]
            top[sizeofKey] = pointer;
            pointer++;

        }

        if(top[0] == 0)
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

            //Finally add next bit in last bit of top[]
            top[sizeofKey] = pointer;
            pointer++;
        }



        //Printing Top
        cout<<"JUST PRINTING"<<endl;
        for(int i = 0 ; i<sizeofKey ; i++)
        {
            cout<<top[i]<<"\t";
        }
        cout<<endl;



        //Terminating condition
        if(answer[1] == 1 && answer[2] == 0 && answer[34] == 1)
        {
            cout<<"ENDING with "<<pointer<<endl;
            for(int i = 1 ; i<sizeofKey ; i++)
            {
                cout<<answer[i]<<"\t";
            }
            cout<<endl;
            break;
        }
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


    //Perform Division
    modulo2(dataArray,keyArray,absoluteSize,sizeofKey);



    //OUTPUT
    cout<<endl;
    cout<<"Key : "<<endl;
    for(int i = 0 ; i<sizeofKey ; i++)
    {
        cout<<keyArray[i]<<"\t";
    }

    cout<<endl;
    cout<<endl;

    cout<<"Data : "<<endl;
    for(int i = 0 ; i<absoluteSize ; i++)
    {
        cout<<dataArray[i]<<"\t";
    }


}

int main()
{

    sender();


    return 0;
}
