/*定义链队*/

template <class T>
class linkQueue
{
    public:
      linkQueue();    //构造函数

      bool empty();    //判空函数
	  
      void enQueue(const T &);    //进队函数

      T deQueue();    //出队函数

      T getHead();    //返回对头元素

      ~linkQueue();    //析构函数

    private:
      //定义结点
      struct node
      {
          T data;    //结点中保存的数据

          node *next;    //后继指针

          node():next(NULL){};    //构造函数，无参数

          node(const T &x,node *n = NULL):data(x), next(n){};    //构造函数，带参数

          ~node(){};
      };

      node *head;    //队头指针

      node *tail;    //队尾指针
};

//构造函数
template <class T>
linkQueue<T>::linkQueue()
{
    head = tail = NULL;
}

//判空函数
template <class T>
bool linkQueue<T>::empty()
{
    return head == NULL;
}

//进队函数
template <class T>
void linkQueue<T>::enQueue(const T &x)
{
    node *add = new node(x);

    if(head)
    {
        tail->next = add;

        tail = add;
    }

    else head = tail = add;
}

//出队函数
template <class T>
T linkQueue<T>::deQueue()
{
    if(empty()) 
		throw 1;

    T d = head->data;
    
	node *temp = head;
    
	head = head->next;
    
	delete temp;
    
	return d;
}

//返回对头元素
template <class T>
T linkQueue<T>::getHead()
{
    if(empty()) 
		throw 1;
    
	return head->data;
}

//析构函数
template <class T>
linkQueue<T>::~linkQueue()
{
    while(head != NULL)
    {
        node *temp = head;
        
		head = head->next;
        
		delete temp;
    }
}