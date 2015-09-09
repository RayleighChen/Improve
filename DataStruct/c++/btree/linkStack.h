/*��ջ�Ķ��弰ʵ��*/


//����ջ
template <class T>
class linkStack
{
    public:
      linkStack();    //���캯��

      bool empty();    //�пպ���

      void push(const T &x);    //��ջ����

      T pop();    //��ջ����

      T top();    //����ջ��Ԫ��

      ~linkStack();    //��������

    private:
      //����ڵ�
      struct node
      {
          T data;    //����б��������

          node *next;    //���ָ��

          node():next(NULL){};    //���캯�����޲���

          node(const T &x,node *n = NULL):data(x), next(n){};    //���캯����������

          ~node(){};    //��������
      };

      node *top_p;    //ջ��ָ��
};

//���캯��
template <class T>
linkStack<T>::linkStack()
{
    top_p = NULL;
}

//�пպ���
template <class T>
bool linkStack<T>::empty()
{
    return top_p == NULL;
}

//��ջ����
template <class T>
void linkStack<T>::push(const T &x)
{
    node *add = new node(x, top_p);

    top_p = add;
}

//��ջ����
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

//����ջ��Ԫ��
template <class T>
T linkStack<T>::top()
{
    if(empty()) 
		throw 1;
    
	return top_p->data;
}

//��������
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