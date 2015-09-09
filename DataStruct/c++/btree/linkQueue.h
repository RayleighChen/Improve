/*��������*/

template <class T>
class linkQueue
{
    public:
      linkQueue();    //���캯��

      bool empty();    //�пպ���
	  
      void enQueue(const T &);    //���Ӻ���

      T deQueue();    //���Ӻ���

      T getHead();    //���ض�ͷԪ��

      ~linkQueue();    //��������

    private:
      //������
      struct node
      {
          T data;    //����б��������

          node *next;    //���ָ��

          node():next(NULL){};    //���캯�����޲���

          node(const T &x,node *n = NULL):data(x), next(n){};    //���캯����������

          ~node(){};
      };

      node *head;    //��ͷָ��

      node *tail;    //��βָ��
};

//���캯��
template <class T>
linkQueue<T>::linkQueue()
{
    head = tail = NULL;
}

//�пպ���
template <class T>
bool linkQueue<T>::empty()
{
    return head == NULL;
}

//���Ӻ���
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

//���Ӻ���
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

//���ض�ͷԪ��
template <class T>
T linkQueue<T>::getHead()
{
    if(empty()) 
		throw 1;
    
	return head->data;
}

//��������
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