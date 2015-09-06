#include <iostream>
#include "queue.h"
using namespace std;

int main()
{
	Queue *list = new Queue();
	char judge = 'y';

	while(judge == 'y')
	{
		cout << " 1 Let a number enter queue          \n"; 
		cout <<	" 2 Let a number out of queue         \n";
		cout <<	" 3 getting the top element           \n";
		cout << " 4 judging queue is full or empty    \n"; 
		cout <<	" 5 clear the queue                 \n\n";
		
		int select;
		cin >> select;
	
		switch(select)
		{
			case 1:
			{
				cout << " Please enter a number ";
				int number;
				cin >> number;
	
				list->enQueue(number);

				cout << "\n Do you want to continue ? y/n ";
				cin >> judge;
				cout << endl;
				break;
			}

			case 2:
			{
				cout << " the queue' head elememt " << list->getFront() << " has been deleted ";
				list->deQueue();

				cout << "\n Do you want to contiue ? y/n ";
				cin >> judge;
				cout << endl;
				break;
			}

			case 3:
			{
				if(list->isEmpty())
					cout << "queue doesn't have elements \n" << endl;

				else
				{
					cout << " the queue's top elememt is " << list->getFront();

					cout << "\n Do you want to contiue ? y/n ";
					cin >> judge;
					cout << endl;
				}
				break;
			}

			case 4:
			{
				if(list->isEmpty())
					cout << "the queue is empty";

				if(list->isFull())
					cout << "the queue is full";

				if(!list->isEmpty() && !list->isFull())
					cout << "the queue could save elements";

				cout << "\n Do you want to contiue ? y/n ";
				cin >> judge;
				cout << endl;
				break;
			}

			case 5:
			{
				while(!list->isEmpty())
				{
					cout << list->getFront() << " ";
					list->deQueue();
				}

				cout << endl << "the queue has been cleared";

				cout << "\n Do you want to contiue ? y/n ";
				cin >> judge;
				cout << endl;
				break;
			}
		}
	}

	return 0;
}