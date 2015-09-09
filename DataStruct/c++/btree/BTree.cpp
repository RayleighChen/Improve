#include <iostream>
#include "tree.h"
using namespace std;

int main()
{	
	BT<char> cl;

	cl.createTree('0');

	cl.midOrder();

	cl.preOrder();

	cl.postOrder();

	cout << cl.width();

	BT<char> cet;

	BT<char> j('j');

	BT<char> k;

	k.makeTree('k', j, cet);
	
	BT<char> i;
	
	i.makeTree('i', cet, k);
	
	BT<char> g('g');
	
	BT<char> h;

	h.makeTree('h', g, i);

	BT<char> c('c');

	BT<char> d;

	d.makeTree('d', c, cet);

	BT<char> a('a');

	BT<char> b;

	b.makeTree('b', a, d);

	BT<char> f;

	f.makeTree('f', cet, h);

	BT<char> e;

	e.makeTree('e', b, f);

	e.preOrder();

	e.midOrder();

	e.postOrder();
	return 0;
}



