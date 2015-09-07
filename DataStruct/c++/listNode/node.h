#ifndef _ListNode_
#define _ListNode_

template<class T> class LinkedList;
template<class T>
class ListNode
{
	friend class LinkedList<T>;
	
public:
	ListNode(ListNode<T> *ptrlink = NULL)
	{
		link = ptrlink;
	}

	ListNode(const T& item, ListNode<T> *ptrlink = NULL)
	{
		data = item;

		link = ptrlink;
	}

	~ListNode() {}

	ListNode<T> *getNode(const T& item, ListNode<T> *next = NULL)
	{
		link->data = item;

		link->link = next;

		return link;
	}

	ListNode<T> *getlink() {return link;}

	T getData() {return data;}

	void setLink(ListNode<T> *next) {link = next;}

	void setData(T value) {data = value;}

private:
	ListNode<T> *link;
	T data;

};


#endif