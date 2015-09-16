#include <iostream>
using namespace std;
 
void BubbleSort(int *list, int len)
{
    int i, j, temp;
    for(i = 0; i < len; i++)
        for(j = 0; j < len - i; j++)
        {
            if(list[j] > list[j + 1])
            {
                temp = list[j];
                list[j] = list[j + 1];
                list[j + 1] = temp;
            }
        }
}
 
int main ()
{
    int list[10];
    int n = 10, m = 0;
    
	cout << "Input ten number:";
    for(int i = 0; i < 10; i++)
        cin >> list[i];
    
	cout << endl;
    BubbleSort(list, n);
    for(int i = 0; i < 10; i++)
        cout << list[i] << " ";
    cout << endl;
	return 0;
}