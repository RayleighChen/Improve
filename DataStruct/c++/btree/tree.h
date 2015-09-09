#include <iostream>
#include "linkStack.h"
#include "linkQueue.h"

using namespace std;

//定义节点
template<class T>
struct node
{
    T data;    //结点中保存的数据

    node *left;    //结点的左儿子
    node *right;    //结点的右儿子

    node():left(NULL), right(NULL){}    //构造函数，无参数

    //构造函数，带参数
    node(const T &x, node *l = NULL, node *r = NULL):data(x), left(l), right(r){}

    ~node(){}    //析构函数
};

//定义二叉树
template<class T>
class BT
{
	public:
      BT();    //构造空二叉树

      BT(const T &x);    //构造以数据值为x的结点为根结点的二叉树

      BT(const node<T> *p);    //构造以p为根结点的二叉树

      node<T> *getRoot();    //返回根结点地址

      //创建一个以数据值为x的结点为根结点，以lt为左子树、rt为右子树的二叉树

      void makeTree(const T &x, BT &lt, BT &rt);

      void delLeft();    //删除左子树

      void delRight();    //删除右子树

      bool empty() const;    //判断是否为空树

      void clear();    //删除二叉树

      int size() const;    //求二叉树的规模

      int height() const;    //求二叉树的高度

	  int width() const; //求二叉树的宽度

      int leafCount() const;    //求二叉树叶子结点个数

      void preOrder() const;    //前序遍历二叉树

      void midOrder() const;    //中序遍历二叉树

      void postOrder() const;    //后续遍历二叉树

      void levelOrder() const;    //层次遍历二叉树

      bool isCompBT() const;    //判断是否为完全二叉树

      void createTree(T flag);    //创建二叉树，输入flag表示为空

      void copyTree(BT &tree);    //复制二叉树

      ~BT();    //析构函数

	private:
      node<T> *root;    //二叉树的根结点

      void clear(node<T> *t);    //删除以t为根结点的二叉树

      int size(node<T> *t) const;    //求以t为根结点的二叉树的规模

      int height(node<T> *t) const;    //求以t为根结点的二叉树的高度

      void width(node<T> *t, int *count, int i = 0) const; //求以t为根节点的二叉树的宽度
	  
	  //复制以t为根结点的二叉树为以r为根结点的新二叉树
      void copyTree(node<T> *&r, node<T> *t);    
};

/*二叉树的实现*/

//构造空二叉树
template <class T>
BT<T>::BT()
{
    root = NULL;
}

//构造以数据值为x的结点为根结点的二叉树
template <class T>
BT<T>::BT(const T &x)
{
    root = new node<T>(x);
}

//构造以p为根结点的二叉树
template <class T>
BT<T>::BT(const node<T> *p)
{
    root = p;
}

//返回根结点地址
template <class T>
node<T> *BT<T>::getRoot()
{
    return root;
}

//创建一个以数据值为x的结点为根结点，以lt为左子树、rt为右子树的二叉树
template <class T>
void BT<T>::makeTree(const T &x, BT &lt, BT &rt)
{
    root = new node<T>(x, lt.root, rt.root);

    lt.root = NULL;

    rt.root = NULL;
}

//删除左子树
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

//删除右子树
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

//判断是否为空树
template <class T>
bool BT<T>::empty() const
{
    return root == NULL;
}

//删除二叉树
template <class T>
void BT<T>::clear()
{
    if(root)
    {
        clear(root);

        root = NULL;
    }
}

//删除以t为根结点的二叉树
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

//求二叉树的规模
template <class T>
int BT<T>::size() const
{
    return size(root);
}

//求以t为根结点的二叉树的规模
template <class T>
int BT<T>::size(node<T> *t) const
{
    if(t) 
		return 1 + size(t->left) + size(t->right);

    return 0;
}

//求二叉树的高度
template <class T>
int BT<T>::height() const
{
    return height(root);
}

//求以t为根结点的二叉树的高度
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


//求二叉树的宽度
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

//求以t为根结点的二叉树的宽度
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
//求二叉树的宽度
template <class T>
int BT<T>::width() const
{
    return width(root);
}

//求以t为根结点的二叉树的宽度
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


//求二叉树叶子结点个数
template <class T>
int BT<T>::leafCount() const
{
    /*递归实现：
    if(!t) 
		return 0;

    if(!t->left && !t->right) 
		return 1;

    return leafCount(t->left) + leafCount(t->right);
    */

    //非递归实现：
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

//前序遍历二叉树
template <class T>
void BT<T>::preOrder() const
{
    /*递归实现：
    if(t)
    {
        cout << t->data << ' ';

        preOrder(t->left);

        preOrder(t->right);
    }
    */

    //非递归实现：
    if(root)
    {
        cout << "\npreOrder：";

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

//中序遍历二叉树
template <class T>
void BT<T>::midOrder() const
{
    /*递归实现：
    if(t)
    {
        midOrder(t->left);

        cout << t->data<<' ';

        midOrder(t->right);
    }
    */

    //非递归实现：
    if(root)
    {
        cout << "\nmidOrder：";

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

//后续遍历二叉树
template <class T>
void BT<T>::postOrder() const
{
    /*递归实现：
    if(t)
    {
        postOrder(t->left);

        postOrder(t->right);

        cout << t->data <<' ';
    }
    */

    //非递归实现：
    if(root)
    {
        cout << "\npostOrder：";

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

//层次遍历二叉树
template <class T>
void BT<T>::levelOrder() const
{
    if(root)
    {
        cout << "\nlevelOrder：";
        
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

//判断是否为完全二叉树
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

//创建二叉树，输入flag表示为空
template <class T>
void BT<T>::createTree(T flag)
{
    linkQueue<node<T> *> queue;

    node<T> *temp;

    T x, ldata, rdata;

    //创建树，输入flag表示空
    cout << "\nenter the root：";
    
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
            
			cout << "\nenter " << temp->data << "'s children （" << flag << " means NULL）：";
            
			cin >> ldata >> rdata;
            
			if(ldata != flag)
				queue.enQueue(temp->left = new node<T>(ldata));
            
			if(rdata != flag)
				queue.enQueue(temp->right = new node<T>(rdata));
        }
    }
    cout << "\ncompleted！\n\n";
}

//复制二叉树
template <class T>
void BT<T>::copyTree(BT &tree)
{
    copyTree(root, tree.root);
}

//复制以t为根结点的二叉树为以r为根结点的新二叉树
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

//析构函数
template <class T>
BT<T>::~BT()
{
    clear();
}
