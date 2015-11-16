#include <iostream>
using namespace std;
 
int Partition(int *L, int low, int high)
{
    int pivotkey = L[low];
    int i = low, j = high + 1;
 
    while(i < j)
    {
        while (L[++i] < pivotkey && i <= high);
        while (L[--j] > pivotkey);
 
        if (i < j)
		{
            int temp = L[i];
            L[i] = L[j];
            L[j] = temp;
        }
    }
 
    L[low] = L[j];
    L[j] = pivotkey;
 
    return j;
 }
 
int NSort(int *L, int low, int high, int k)
{
    int mid;
 
    if(low <= high)
    {
        mid = Partition(L, low, high);
        if(high - mid + 1 == k 
			return L[mid];
        else if(high - mid + 1 < k)
			return NSort(L, low, mid - 1, k);
        else return NSort(L, mid + 1, high, k);
    }
     return 0;
 }
 
int main()
{
    int L[10] = {11, 49, 53, 86, 22, 27, 98, 88, 91, 100};
 
    for(int i = 1; i <= 10; i++)
	{
        int ith = NSort(L, 0, 9, i);
        printf("%d\n", ith);
    }
    return 0;
 }
