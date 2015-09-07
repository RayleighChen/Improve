#ifndef _STACK_
#define _STACK_

class Stack
{
public:
	Stack(int capacity = 10)
	{
		maxSize = capacity;
		theArray[capacity - 1];
		top = -1;
	}

	bool isEmpty()
	{
		return top == -1;
	}

	bool isFull()
	{
		return top == maxSize - 1;
	}

	void clear()
	{
		top = -1;
	}

	void push(int x)
	{
		if(isFull())
			cout << "the stack is full" << endl;

		else
			theArray[++top] = x;
	}

	void pop()
	{
		if(isEmpty())
			cout << "the stack is empty" << endl;

		else
			theArray[top--];
	}

	int getTop()
	{
		if(isEmpty())
			cout << "error";

		else
			return theArray[top];
	}

private:
	int maxSize;
	int top;
	int theArray[100];
};

#endif