#include <iostream>
#include "linkStack.h"
#include "linkQueue.h"

using namespace std;

//����ڵ�
template<class T>
struct node
{
    T data;    //����б��������

    node *left;    //���������
    node *right;    //�����Ҷ���

    node():left(NULL), right(NULL){}    //���캯�����޲���

    //���캯����������
    node(const T &x, node *l = NULL, node *r = NULL):data(x), left(l), right(r){}

    ~node(){}    //��������
};

//���������
template<class T>
class BT
{
	public:
      BT();    //����ն�����

      BT(const T &x);    //����������ֵΪx�Ľ��Ϊ�����Ķ�����

      BT(const node<T> *p);    //������pΪ�����Ķ�����

      node<T> *getRoot();    //���ظ�����ַ

      //����һ��������ֵΪx�Ľ��Ϊ����㣬��ltΪ��������rtΪ�������Ķ�����

      void makeTree(const T &x, BT &lt, BT &rt);

      void delLeft();    //ɾ��������

      void delRight();    //ɾ��������

      bool empty() const;    //�ж��Ƿ�Ϊ����

      void clear();    //ɾ��������

      int size() const;    //��������Ĺ�ģ

      int height() const;    //��������ĸ߶�

	  int width() const; //��������Ŀ��

      int leafCount() const;    //�������Ҷ�ӽ�����

      void preOrder() const;    //ǰ�����������

      void midOrder() const;    //�������������

      void postOrder() const;    //��������������

      void levelOrder() const;    //��α���������

      bool isCompBT() const;    //�ж��Ƿ�Ϊ��ȫ������

      void createTree(T flag);    //����������������flag��ʾΪ��

      void copyTree(BT &tree);    //���ƶ�����

      ~BT();    //��������

	private:
      node<T> *root;    //�������ĸ����

      void clear(node<T> *t);    //ɾ����tΪ�����Ķ�����

      int size(node<T> *t) const;    //����tΪ�����Ķ������Ĺ�ģ

      int height(node<T> *t) const;    //����tΪ�����Ķ������ĸ߶�

      void width(node<T> *t, int *count, int i = 0) const; //����tΪ���ڵ�Ķ������Ŀ��
	  
	  //������tΪ�����Ķ�����Ϊ��rΪ�������¶�����
      void copyTree(node<T> *&r, node<T> *t);    
};

/*��������ʵ��*/

//����ն�����
template <class T>
BT<T>::BT()
{
    root = NULL;
}

//����������ֵΪx�Ľ��Ϊ�����Ķ�����
template <class T>
BT<T>::BT(const T &x)
{
    root = new node<T>(x);
}

//������pΪ�����Ķ�����
template <class T>
BT<T>::BT(const node<T> *p)
{
    root = p;
}

//���ظ�����ַ
template <class T>
node<T> *BT<T>::getRoot()
{
    return root;
}

//����һ��������ֵΪx�Ľ��Ϊ����㣬��ltΪ��������rtΪ�������Ķ�����
template <class T>
void BT<T>::makeTree(const T &x, BT &lt, BT &rt)
{
    root = new node<T>(x, lt.root, rt.root);

    lt.root = NULL;

    rt.root = NULL;
}

//ɾ��������
template <class T>
void BT<T>::delLeft()
{
    if(root && root->left)
    {
        BT temp = root->left;

        root->left = NULL;

        temp.clear();
    }
}

//ɾ��������
template <class T>
void BT<T>::delRight()
{
    if(root && root->right)
    {
        BT temp = root->right;

        root->right = NULL;

        temp.clear();
    }
}

//�ж��Ƿ�Ϊ����
template <class T>
bool BT<T>::empty() const
{
    return root == NULL;
}

//ɾ��������
template <class T>
void BT<T>::clear()
{
    if(root)
    {
        clear(root);

        root = NULL;
    }
}

//ɾ����tΪ�����Ķ�����
template <class T>
void BT<T>::clear(node<T> *t)
{
    if(t)
    {
        if(t->left) 
			clear(t->left);

        if(t->right)
			clear(t->right);

        delete t;
    }
}

//��������Ĺ�ģ
template <class T>
int BT<T>::size() const
{
    return size(root);
}

//����tΪ�����Ķ������Ĺ�ģ
template <class T>
int BT<T>::size(node<T> *t) const
{
    if(t) 
		return 1 + size(t->left) + size(t->right);

    return 0;
}

//��������ĸ߶�
template <class T>
int BT<T>::height() const
{
    return height(root);
}

//����tΪ�����Ķ������ĸ߶�
template <class T>
int BT<T>::height(node<T> *t) const
{
    if(t)
    {
        int lt = height(t->left), rt = height(t->right);

        return 1 + (lt > rt ? lt : rt);
    }

    return -1;
}


//��������Ŀ��
template <class T>
int BT<T>::width() const
{
	int dep = height() + 1;

	int *count = new int[dep];

	for(int i = 0; i < dep; i++)
		count[i] = 0;

	width(root, count, 0);

	int max = 0;

	for(int i = 0; i < dep; i++)
		if(max < count[i])
			max = count[i];

    return max;
}

//����tΪ�����Ķ������Ŀ��
template <class T>
void BT<T>::width(node<T> *t, int *count, int i = 0) const
{
	if(t)
	{
		count[i]++;

		width(t->left, count, i + 1);

		width(t->right, count, i + 1);
	}
}

/*
//��������Ŀ��
template <class T>
int BT<T>::width() const
{
    return width(root);
}

//����tΪ�����Ķ������Ŀ��
template <class T>
int BT<T>::width(node<T> *t) const
{
	linkQueue<node<T> *> queue;
	
	int flag = 0, count = 0;
	
	node<T> *p, *rear;
	
	if(t)
	{
		queue.enQueue(t);
		
		flag = 1;
		
		p = t;
	}
	
	while(p != queue.getHead())
	{
		t = queue.getHead();
		
		if(t->left)
		{
			queue.enQueue(t->left);
			
			count++;
			
			rear = t->left;
		}
		
		if(t->right)
		{
			queue.enQueue(t->right);
			
			count++;
			
			rear = t->right;
		}
		
		if(queue.getHead() == p)
		{
			if(flag < count)
				flag = count;
			
			count = 0;
			
			p = rear;
		}
	}
	
	return flag;



/*	int static n[10];

	int static i = 1;

	int static max = 0;

    if(t)
    {
		if(i == 1)
		{
			n[i]++;

			i++;

			if(t->left)
				n[i]++;

			if(t->right)
				n[i]++;
		}

		else
		{
			i++;

			if(t->left)
				n[i]++;

			if(t->right)
				n[i]++;
		}  

		if(max < n[i])
			max = n[i];

		width(t->left);

		i--;

		width(t->right);
    }

    return max; */
//}


//�������Ҷ�ӽ�����
template <class T>
int BT<T>::leafCount() const
{
    /*�ݹ�ʵ�֣�
    if(!t) 
		return 0;

    if(!t->left && !t->right) 
		return 1;

    return leafCount(t->left) + leafCount(t->right);
    */

    //�ǵݹ�ʵ�֣�
    int count = 0;

    linkStack<node<T> *> stack;

    node<T> *t = root;

    if(t)
    {
        stack.push(t);

        while(!stack.empty())
        {
            node<T> *temp = stack.pop();

            if(!temp->left && !temp->right) 
				++count;

            if(temp->right) 
				stack.push(temp->right);

            if(temp->left)
				stack.push(temp->left);
        }
    }
    return count;
}

//ǰ�����������
template <class T>
void BT<T>::preOrder() const
{
    /*�ݹ�ʵ�֣�
    if(t)
    {
        cout << t->data << ' ';

        preOrder(t->left);

        preOrder(t->right);
    }
    */

    //�ǵݹ�ʵ�֣�
    if(root)
    {
        cout << "\npreOrder��";

        linkStack<node<T> *> stack;

        node<T> *t = root;

        if(t)
        {
            stack.push(t);

            while(!stack.empty())
            {
                node<T> *temp = stack.pop();

                cout << temp->data << ' ';

                if(temp->right)
					stack.push(temp->right);

                if(temp->left) 
					stack.push(temp->left);
            }
        }
    }
}

//�������������
template <class T>
void BT<T>::midOrder() const
{
    /*�ݹ�ʵ�֣�
    if(t)
    {
        midOrder(t->left);

        cout << t->data<<' ';

        midOrder(t->right);
    }
    */

    //�ǵݹ�ʵ�֣�
    if(root)
    {
        cout << "\nmidOrder��";

        linkStack<node<T> *> stack;

        node<T> *t = root;

        if(t)
        {
            while(t)
            {
                stack.push(t);

                t = t->left;
            }

            while(!stack.empty())
            {
                node<T> *temp = stack.pop();

                cout << temp->data << ' ';

                if(temp->right)
                {
                    stack.push(temp->right);

                    node<T> *temp1 = temp->right;

                    while(temp1->left)
                    {
                        stack.push(temp1->left);

                        temp1 = temp1->left;
                    }
                }
            }
        }
    }
}

//��������������
template <class T>
void BT<T>::postOrder() const
{
    /*�ݹ�ʵ�֣�
    if(t)
    {
        postOrder(t->left);

        postOrder(t->right);

        cout << t->data <<' ';
    }
    */

    //�ǵݹ�ʵ�֣�
    if(root)
    {
        cout << "\npostOrder��";

        linkStack<node<T> *> stack;

        node<T> *t = root;

        do
        {
            while(t)
            {
                stack.push(t);

                t = t->left;
            }

            node<T> *temp = NULL;

            bool flag = 1;

            while(!stack.empty() && flag)
            {
                t = stack.top();

                if(t->right == temp)
                {
                    cout << t->data << ' ';

                    stack.pop();

                    temp = t;
                }

                else
                {
                    t = t->right;
                    
					flag = 0;
                }
            }

        }while(!stack.empty());
    }
}

//��α���������
template <class T>
void BT<T>::levelOrder() const
{
    if(root)
    {
        cout << "\nlevelOrder��";
        
		linkQueue<node<T> *> queue;
        
		node<T> *t = root;
        
		queue.enQueue(t);
        
		while(!queue.empty())
        {
            t = queue.deQueue();
            
			cout << t->data << ' ';
            
			if(t->left)
				queue.enQueue(t->left);
            
			if(t->right) 
				queue.enQueue(t->right);
        }
    }
}

//�ж��Ƿ�Ϊ��ȫ������
template <class T>
bool BT<T>::isCompBT() const
{
    if(!root) 
		return 1;
    
	bool judge = 1;
    
	bool flag = 0;
    
	linkQueue<node<T> *> queue;
    
	queue.enQueue(root);
    
	while(!queue.empty())
    {
        node<T> *temp = queue.getHead();
        
		if(flag)
        {
            if(temp->left || temp->right)
            {
                judge = 0;
                break;
            }
        }

        else
        {
            if(temp->left && temp->right)
            {
                queue.enQueue(temp->left);
                
				queue.enQueue(temp->right);
            }

            else if(!temp->left && temp->right)
            {
                judge = 0;
                break;
            }

            else
            {
                if(temp->left) 
					queue.enQueue(temp->left);
                
				flag = 1;
            }
        }
        queue.deQueue();
    }
    
	while(!queue.empty())
		queue.deQueue();
    
	return judge;
}

//����������������flag��ʾΪ��
template <class T>
void BT<T>::createTree(T flag)
{
    linkQueue<node<T> *> queue;

    node<T> *temp;

    T x, ldata, rdata;

    //������������flag��ʾ��
    cout << "\nenter the root��";
    
	cin >> x;
    
	if(x == flag) 
		root = NULL;
    
	else
    {
        root = new node<T>(x);
        
		queue.enQueue(root);
        
		while(!queue.empty())
        {
            temp = queue.deQueue();
            
			cout << "\nenter " << temp->data << "'s children ��" << flag << " means NULL����";
            
			cin >> ldata >> rdata;
            
			if(ldata != flag)
				queue.enQueue(temp->left = new node<T>(ldata));
            
			if(rdata != flag)
				queue.enQueue(temp->right = new node<T>(rdata));
        }
    }
    cout << "\ncompleted��\n\n";
}

//���ƶ�����
template <class T>
void BT<T>::copyTree(BT &tree)
{
    copyTree(root, tree.root);
}

//������tΪ�����Ķ�����Ϊ��rΪ�������¶�����
template <class T>
void BT<T>::copyTree(node<T> *&r,node<T> *t)
{
    if(t)
    {
        r = new node<T>(t->data);
        
		copyTree(r->left, t->left);
        
		copyTree(r->right, t->right);
    }

    else r = NULL;
}

//��������
template <class T>
BT<T>::~BT()
{
    clear();
}
