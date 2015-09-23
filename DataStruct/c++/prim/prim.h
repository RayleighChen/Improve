#include<iostream>  
#include<string>  
#include<queue>  
using namespace std;  
  
const int MAX_VEX_NUM=20;  
bool visited[20];   
  
class MGraph;  
  
class CS_Tree;  
//�����  
class CSnode  
{  
    string data;  
    CSnode *firstchild;  
    CSnode *nextsibling;  
    friend class CS_Tree;  
    friend class MGraph;  
};  
  
//���ඨ��  
class CS_Tree  
{  
public:  
    void PreRoot_Traverse(CSnode *T) //�ȸ�����  
    {  
        if(T)  
        {  
            cout<<T->data<<"  ";  
            PreRoot_Traverse(T->firstchild);  
            PreRoot_Traverse(T->nextsibling);  
        }  
    }  
  
    void PostRoot_Traverse(CSnode *T) //�������  
    {  
        if(T)  
        {  
            PostRoot_Traverse(T->firstchild);  
            cout<<T->data<<"  ";  
            PostRoot_Traverse(T->nextsibling);  
        }  
    }  
  
    void LevelOrder_Traverse(CSnode *T) //��α���  
    {  
        queue<CSnode *> q;  
        CSnode *t;  
        q.push(T);  
        do  
        {  
            t=q.front();  
            do  
            {  
                cout<<t->data<<"  ";  
                if(t->firstchild)  
                    q.push(t->firstchild);  
                t=t->nextsibling;  
            }while(t);  
            q.pop();  
        }while(!q.empty());  
    }  
};  
  
  
class ArcNode  
{  
public:  
    int adj;  
    bool search;//��Ϊ������С��������ʱ����Ƿ񱻷��ʹ��ı�־  
    friend class MGraph;  
};  
  
class temp //Ϊ��������  
{  
public:  
    int i;  
    int j;  
    int weight;  
    friend class MGraph;  
};  
  
class closedge //Ϊ��������  
{  
public:  
    string adjvex;  
    int weight;  
    friend class MGraph;  
};  
  
class MGraph  
{  
private:  
    string vexs[MAX_VEX_NUM];//��������  
    ArcNode arcs[MAX_VEX_NUM][MAX_VEX_NUM];//�ڽӾ���  
    int vexnum;//������  
    int arcnum;//����  
public:  
    void Create_MG()  
    {  
        int i,j,k;  
        cout<<"����ͼ�Ķ������ͱ�����";  
        cin>>vexnum>>arcnum;  
        cout<<"��������������ƣ�";  
        for(i=0;i<vexnum;i++)  
            cin>>vexs[i];  
          
        for(i=0;i<vexnum;i++)  
            for(int j=0;j<vexnum;j++)  
            {  
                arcs[i][j].adj=-1;  
                arcs[i][j].search=false;  
            }  
        //�����ǳ�ʼ���ڽӾ���  
          
        for(k=0;k<arcnum;k++)  
        {  
            cout<<"����ÿ���߶�Ӧ�����������Լ��ñߵ�Ȩֵ��";  
            string v1,v2;  
            int w;  
            cin>>v1>>v2>>w;  
            i=Locate_Vex(v1);  
            j=Locate_Vex(v2);             
          
            while(i<0|| i>vexnum-1 || j<0 || j>vexnum-1)  
            {  
                cout<<"���λ���������,��������: ";  
                cin>>v1>>v2>>w;  
                i=Locate_Vex(v1);  
                j=Locate_Vex(v2);     
            }  
              
            arcs[i][j].adj=w;  
            arcs[j][i]=arcs[i][j]; //�öԳƱ�  
        }  
        cout<<"ͼ�������"<<endl;  
    }  
      
    int Locate_Vex(string x)  //����ȷ�������ڶ��������е�λ��  
    {  
        for(int k=0;vexs[k]!=x;k++);  
        return k;  
    }  
  
    void DFS(int v)  
    {  
        visited[v]=true;  
        cout<<vexs[v]<<"  ";  
          
        for(int j=0;j<vexnum;j++)  
            if(arcs[v][j].adj!=-1 && !visited[j])  
                DFS(j);  
    }  
  
    //������ȱ���ͼ  
    void DFS_Traverse()  
    {  
        //visited����������Ϊ�Ƿ��ѷ��ʵı�־  
        for(int i=0;i<vexnum;i++)  
            visited[i]=false;  
      
        for(int v=0;v<vexnum;v++)  
            if(!visited[v])  
                DFS(v);  
    }  
  
    //������ȱ���  
    void BFS_Traverse()  
    {  
        queue<int> q;  
        int u,w,v;  
        for(v=0;v<vexnum;v++)  
            visited[v]=false;  
          
        for(v=0;v<vexnum;v++)  
        {  
            if(!visited[v])  
            {  
                visited[v]=true;  
                cout<<vexs[v]<<"  ";  
                q.push(v);  
              
                while(!q.empty())  
                {  
                    u=q.front();  
                    q.pop();  
                      
                    for(w=0;w<vexnum;w++)  
                    {  
                        if(arcs[u][w].adj!=-1 && !visited[w])  
                        {  
                            visited[w]=true;  
                            cout<<vexs[w]<<"  ";  
                            q.push(w);  
                        }  
                    }  
                }  
            }  
        }  
    }  
  
    CSnode *Prim_Min_Pre(int v,closedge c[])  
    {  
        string *u=new string[20];//U�б����Ѿ���Ϊ�����Ķ���  
        int num=0;  
        int low;  
        for(int l=0;l<vexnum;l++) //��Ϊ�����Ƿ��Ѿ���U�еı�־  
            visited[l]=false;  
        u[num]=vexs[v];  
        visited[v]=true;  
        num++;  
          
        for(int vex=1;vex<vexnum;vex++)  
        {  
            temp min;  
            low=99999;  
            /*-------------------------------- 
            /�����forѭ�����ҳ���U�����ж��� 
            /���ڵķ�U�еĶ����б�Ȩֵ��С��һ�� 
            /--------------------------------*/  
            for(int j=0;j<num;j++)  
            {  
                int i=Locate_Vex(u[j]);  
                for(int k=0;k<vexnum;k++)  
                {  
                    if(arcs[i][k].adj!=-1 && arcs[i][k].adj<low && !arcs[i][k].search &&!visited[k])  
                    {  
                          
                        low=arcs[i][k].adj;  
                        min.i=i;  
                        min.j=k;  
                        min.weight=low;  
                    }  
                }  
            }  
            c[min.j].adjvex=vexs[min.i];  
            c[min.j].weight=min.weight;  
            u[num]=vexs[min.j];  
            num++;  
            visited[min.j]=true;  
            arcs[min.i][min.j].search=arcs[min.j][min.i].search=true; //���Ѿ���Ϊ���ıߵı߱�־Ϊ�ѷ���  
            if(num==vexnum)  
                break ;  
        }  
        CSnode *T=NULL;  
        Prim_TREE(v,T,c);//��c�����е�Ԫ��ת��Ϊ��  
        return T;  
  
    }  
  
    void Prim_TREE(int v,CSnode *&T,closedge c[])  
    {  
        CSnode *r=NULL,*p=NULL,*q=NULL;  
        bool first;  
        queue<CSnode *> qu;  
        bool connected[20];//��Ϊ�ö����Ƿ��Ѿ����������еı�־  
        for(int i=0;i<vexnum;i++)  
            connected[i]=false;  
  
        qu.push(T);  
  
        //�����ǽ������̣�����˼·��ͼ�Ĺ����������������һ����  
        while(!qu.empty())  
        {  
            r=qu.front();  
            qu.pop();  
              
            if(!r && !T)//��һ�ν���ʱ��TΪ��  
            {  
                T=new CSnode;  
                T->data=vexs[v];  
                T->firstchild=T->nextsibling=NULL;  
                r=T;  
            }  
  
            first=true;  
            for(int i=1;i<vexnum;i++)  
            {  
                if(!connected[i] && c[i].adjvex==r->data)  
                {  
                    connected[i]=true;  
                    p=new CSnode;  
                    p->data=vexs[i];  
                    p->firstchild=p->nextsibling=NULL;  
                    qu.push(p);  
                    if(first)  
                    {  
                        r->firstchild=p;  
                        first=false;  
                    }  
                    else  
                        q->nextsibling=p;  
                    q=p;  
                }  
            }  
        }  
    }  
}; 