/*链栈的定义及实现*/


//定义栈
template <class T>
class linkStack
{
    public:
      linkStack();    //构造函数

      bool empty();    //判空函数

      void push(const T &x);    //进栈函数

      T pop();    //出栈函数

      T top();    //返回栈顶元素

      ~linkStack();    //析构函数

    private:
      //定义节点
      struct node
      {
          T data;    //结点中保存的数据

          node *next;    //后继指针

          node():next(NULL){};    //构造函数，无参数

          node(const T &x,node *n = NULL):data(x), next(n){};    //构造函数，带参数

          ~node(){};    //析构函数
      };

      node *top_p;    //栈顶指针
};

//构造函数
template <class T>
linkStack<T>::linkStack()
{
    top_p = NULL;
}

//判空函数
template <class T>
bool linkStack<T>::empty()
{
    return top_p == NULL;
}

//进栈函数
template <class T>
void linkStack<T>::push(const T &x)
{
    node *add = new node(x, top_p);

    top_p = add;
}

//出栈函数
template <class T>
T linkStack<T>::pop()
{
    if(empty())
		throw 1;

    T d = top_p->data;
    
	node *temp = top_p;
    
	top_p = top_p->next;
    
	delete temp;
    
	return d;
}

//返回栈顶元素
template <class T>
T linkStack<T>::top()
{
    if(empty()) 
		throw 1;
    
	return top_p->data;
}

//析构函数
template <class T>
linkStack<T>::~linkStack()
{
    while(top_p)
    {
        node *temp = top_p;
        
		top_p = top_p->next;
        
		delete temp;
    }
}