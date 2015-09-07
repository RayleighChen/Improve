#ifndef _QUEUE_
#define _QUEUE_

class Queue
{
public:
	Queue(int capacity = 10)
	{
		maxSize = capacity;
		list = new int(maxSize);

		counts = 0;
		front = 0;
		rear = 0;
	}

	bool isEmpty()
	{
		return counts == 0;
	}

	bool isFull()
	{
		return (counts > 0 && front == rear);
	}

	int getFront()
	{
		if(isEmpty())
			cout << "the Queue is empty" << endl;

		else
			return list[front]; 
	}

	void enQueue(int number)
	{
		if(isFull())
			cout << "the Queue is full" << endl;

		else
		{
			list[rear] = number;

			rear = (rear + 1) % maxSize;
			counts++;
		}
	}

	void deQueue()
	{
		if(isEmpty())
			cout << "the Queue is empty" << endl;

		else
		{
			front = (front + 1) % maxSize;
			counts--;
		}
	}

private:
	int maxSize;
	int counts;

	int *list;
	int front;
	int rear;
};

#endif