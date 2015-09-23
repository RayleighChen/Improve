#include<iostream>  
#include<string>  
#include<queue>  
using namespace std;  
  
const int MAX_VEX_NUM=20;  
bool visited[20];   
  
class MGraph;  
  
class CS_Tree;  
//树结点  
class CSnode  
{  
    string data;  
    CSnode *firstchild;  
    CSnode *nextsibling;  
    friend class CS_Tree;  
    friend class MGraph;  
};  
  
//树类定义  
class CS_Tree  
{  
public:  
    void PreRoot_Traverse(CSnode *T) //先根遍历  
    {  
        if(T)  
        {  
            cout<<T->data<<"  ";  
            PreRoot_Traverse(T->firstchild);  
            PreRoot_Traverse(T->nextsibling);  
        }  
    }  
  
    void PostRoot_Traverse(CSnode *T) //后根遍历  
    {  
        if(T)  
        {  
            PostRoot_Traverse(T->firstchild);  
            cout<<T->data<<"  ";  
            PostRoot_Traverse(T->nextsibling);  
        }  
    }  
  
    void LevelOrder_Traverse(CSnode *T) //层次遍历  
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
    bool search;//作为建立最小生成树的时结点是否被访问过的标志  
    friend class MGraph;  
};  
  
class temp //为建树所用  
{  
public:  
    int i;  
    int j;  
    int weight;  
    friend class MGraph;  
};  
  
class closedge //为建树所用  
{  
public:  
    string adjvex;  
    int weight;  
    friend class MGraph;  
};  
  
class MGraph  
{  
private:  
    string vexs[MAX_VEX_NUM];//顶点数组  
    ArcNode arcs[MAX_VEX_NUM][MAX_VEX_NUM];//邻接矩阵  
    int vexnum;//顶点数  
    int arcnum;//边数  
public:  
    void Create_MG()  
    {  
        int i,j,k;  
        cout<<"输入图的顶点数和边数：";  
        cin>>vexnum>>arcnum;  
        cout<<"输入各个顶点的民称：";  
        for(i=0;i<vexnum;i++)  
            cin>>vexs[i];  
          
        for(i=0;i<vexnum;i++)  
            for(int j=0;j<vexnum;j++)  
            {  
                arcs[i][j].adj=-1;  
                arcs[i][j].search=false;  
            }  
        //上面是初始化邻接矩阵  
          
        for(k=0;k<arcnum;k++)  
        {  
            cout<<"输入每条边对应的两个顶点以及该边的权值：";  
            string v1,v2;  
            int w;  
            cin>>v1>>v2>>w;  
            i=Locate_Vex(v1);  
            j=Locate_Vex(v2);             
          
            while(i<0|| i>vexnum-1 || j<0 || j>vexnum-1)  
            {  
                cout<<"结点位置输入错误,重新输入: ";  
                cin>>v1>>v2>>w;  
                i=Locate_Vex(v1);  
                j=Locate_Vex(v2);     
            }  
              
            arcs[i][j].adj=w;  
            arcs[j][i]=arcs[i][j]; //置对称边  
        }  
        cout<<"图构造完成"<<endl;  
    }  
      
    int Locate_Vex(string x)  //用于确定顶点在顶点数组中的位置  
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
  
    //深度优先遍历图  
    void DFS_Traverse()  
    {  
        //visited数组用来作为是否已访问的标志  
        for(int i=0;i<vexnum;i++)  
            visited[i]=false;  
      
        for(int v=0;v<vexnum;v++)  
            if(!visited[v])  
                DFS(v);  
    }  
  
    //广度优先遍历  
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
        string *u=new string[20];//U中保存已经作为树结点的顶点  
        int num=0;  
        int low;  
        for(int l=0;l<vexnum;l++) //作为顶点是否已经在U中的标志  
            visited[l]=false;  
        u[num]=vexs[v];  
        visited[v]=true;  
        num++;  
          
        for(int vex=1;vex<vexnum;vex++)  
        {  
            temp min;  
            low=99999;  
            /*-------------------------------- 
            /下面的for循环是找出和U中所有顶点 
            /相邻的非U中的顶点中边权值最小的一条 
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
            arcs[min.i][min.j].search=arcs[min.j][min.i].search=true; //将已经作为树的边的边标志为已访问  
            if(num==vexnum)  
                break ;  
        }  
        CSnode *T=NULL;  
        Prim_TREE(v,T,c);//将c数组中的元素转换为树  
        return T;  
  
    }  
  
    void Prim_TREE(int v,CSnode *&T,closedge c[])  
    {  
        CSnode *r=NULL,*p=NULL,*q=NULL;  
        bool first;  
        queue<CSnode *> qu;  
        bool connected[20];//作为该顶点是否已经连接在树中的标志  
        for(int i=0;i<vexnum;i++)  
            connected[i]=false;  
  
        qu.push(T);  
  
        //下面是建树过程，总体思路和图的广度优先生成树的是一样的  
        while(!qu.empty())  
        {  
            r=qu.front();  
            qu.pop();  
              
            if(!r && !T)//第一次进来时，T为空  
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