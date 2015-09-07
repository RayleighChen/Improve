#ifndef _LinkedList_
#define _LinkedList_

//#include <iostream>
#include "node.h"

template<class T>
class LinkedList
{
public:
	LinkedList();

	~LinkedList();

	bool isEmpty() const {return first == 0;}
	
	int length() const;
	
	bool find(int k, T& x) const;
	
	int search(const T& x) const;
	
	ListNode<T> *First() {return first;}

	LinkedList<T> & insert(int k, const T& x);
	
	LinkedList<T> & Delete(int k, T& x);

	//void output(ostream& out) const;

private:
	ListNode<T> *first;
};

template<class T>
LinkedList<T> ::LinkedList() 
{
	//creat a simple LinkedList, the "finish" is the symbol of end, the value which impossible turn out.
	int finished = 10;
	ListNode<T> *p;
	first = NULL;
	T value;
	cin >> value;

	while(value != finished)
	{
		p = new ListNode<T>(value, first);
		first = p;
		cin >> value;
	}
} 

/* the another one */

/*
template<class T>
LinkedList<T> ::LinkedList()
{
	ListNode<T> *p, *rear;
	T value;
	first = NULL;
	rear = NULL;
	cin >> value;

	while(value != finished)
	{
		p = new ListNode<T>(value, NULL);
		if(first == NULL)	{first = p;}
		else rear->link = p;

		rear = p;
		cin >> value;
	}

	if(rear != NULL)
		rear->link = NULL;
}*/

template<class T>
int LinkedList<T> ::length() const
{
	ListNode<T> *current = first;
	int len = 0;

	while(current)
	{
		len++;
		current = current->link;
	}
	return len;
}


template<class T>
bool LinkedList<T> ::find(int k, T& x) const
{
	if(k < 1) return false;
	ListNode<T> *current = first;
	int index = 1;

	while(index < k && current)
	{
		current = current->link;
		index++;
	}

	if(current)
	{
		x = current->data;
		return true;
	}
	return false;
}

template<class T>
int LinkedList<T> ::search(const T& x) const
{
	ListNode<T> *current = first;
	int index = 1;

	while(current && current->data != x)
	{
		current = current->link;
		index++;
	}

	if(current) return index;
	return 0;
}

template<class T>
LinkedList<T> &LinkedList<T> ::insert(int k, const T& x)
{
	if(K < 0) throw OutOfBounds();
	
	ListNode<T> *P = first;

	for(int index = 1; index < k && p; index++) {p = p->link;}

	if(k > 0 && !p) throw OutOfBound();

	ListNode<T> *S = new ListNode<T>;
	s-data = x;
	
	if(x)
	{
		s->link = p->link;
		p->link = s;
	}
	else
	{
		s-link = first;
		first = s;
	}

	return *this;
}

template<class T>
LinkedList<T> &LinkedList<T> ::Delete(int k, T& x)
{
	if(k < 1 || !first) throw OutOfBound();

	ListNode<T> *p = first;

	if(k == 1)
	{
		first = first->link;
	}
	else
	{
		ListNode<T> *q = first;
		for(int index = 1; index < k - 1 && q; index++)
			q = q->link;

		if(!q || !q ->link) throw OutOfBound();

		p = q->link;
		q->link = p->link;
	}

	x = p->data;
	delete p;
	return *this;
}

#endif