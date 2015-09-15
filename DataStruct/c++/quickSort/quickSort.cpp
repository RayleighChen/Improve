#include <iostream>
#include <conio.h>

using namespace std;
int arrs[] = { 23, 65, 12, 3, 8, 76, 345, 90, 21, 75, 34, 61 };
int arrLen = sizeof(arrs) / sizeof(arrs[0]);

void quickSort(int * arrs, int left, int right){
    int oldLeft = left;
    int oldRight = right;
    bool flag = true;
    int baseArr = arrs[oldLeft]; 
    while(left < right)
	{
        while(left < right && arrs[right] >= baseArr)
		{
            right--;
            flag = false;
        }
        arrs[left] = arrs[right];
        while(left < right && arrs[left] <= baseArr)
		{
            left++;
            flag = false;
        }
        arrs[right] = arrs[left];
    }
    arrs[left] = baseArr;
    if (!flag)
	{
        quickSort(arrs, oldLeft, left-1);
        quickSort(arrs, left+1, oldRight);
    }
}

int main()
{
    quickSort(arrs, 0, arrLen - 1);
    for (int i = 0; i < arrLen; i++)
        cout << arrs[i] << endl;
    getch();
    return 0;
}