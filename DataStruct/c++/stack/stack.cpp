#include <iostream>
#include "stack.h"
using namespace std;

int main()
{
	Stack<int> *list = new Stack<int>();
	char judge = 'y';

	while(judge == 'y')
	{
		cout << " 1 push a number into stack          \n"; 
		cout <<	" 2 pop a number into stack           \n";
		cout <<	" 3 getting the top element           \n";
		cout << " 4 judging stack is full or empty    \n"; 
		cout <<	" 5 clear the stack                 \n\n";
		
		int select;
		cin >> select;
	
		switch(select)
		{
			case 1:
			{
				cout << " Please enter a number ";
				int number;
				cin >> number;
	
				list->push(number);

				cout << "\n Do you want to continue ? y/n ";
				cin >> judge;
				cout << endl;
				break;
			}

			case 2:
			{
				cout << " the stack's top elememt " << list->getTop() << " has been deleted ";
				list->pop();

				cout << "\n Do you want to contiue ? y/n ";
				cin >> judge;
				cout << endl;
				break;
			}

			case 3:
			{
				if(list->isEmpty())
					cout << "stack doesn't have elements \n" << endl;
				else
				{
					cout << " the stack's top elememt is " << list->getTop();

					cout << "\n Do you want to contiue ? y/n ";
					cin >> judge;
					cout << endl;
				}
				break;
			}

			case 4:
			{
				if(list->isEmpty())
					cout << "the stack is empty";

				if(list->isFull())
					cout << "the stack is full";

				if(!list->isEmpty() && !list->isFull())
					cout << "the stack could save elements";

				cout << "\n Do you want to contiue ? y/n ";
				cin >> judge;
				cout << endl;
				break;
			}

			case 5:
			{
				while(!list->isEmpty())
				{
					cout << list->getTop() << " ";
					list->pop();
				}

				cout << endl << "the stack has been cleared";
				list->clear();

				cout << "\n Do you want to contiue ? y/n ";
				cin >> judge;
				cout << endl;
				break;
			}
		}
	}

	return 0;
}