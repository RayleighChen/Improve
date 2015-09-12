# -*- coding: utf-8 -*-
'''
Created on 2014年9月30日
@author: Rayleigh
'''
import numpy as np
import pylab as pl
b=1              #偏置
a=0.3            #学习率
x=np.array([[b,1,3],[b,2,3],[b,1,8],[b,2,15],[b,3,7],[b,4,29],[b,4,8],[b,4,20]])   #训练数据
d=np.array([1,1,-1,-1,1,-1,1,-1])                                      #训练数据类别
w=np.array([b,0,0])                                               #初始w
def sgn(v):                                 
    if v>=0:
        return 1
    else:
        return -1
def comy(myw,myx):
    return sgn(np.dot(myw.T,myx))
def neww(oldw,myd,myx,a):
    return oldw+a*(myd-comy(oldw,myx))*myx

for ii in range(5):                                #迭代次数
    i=0
    for xn in x:
        w=neww(w,d[i],xn,a)
        i+=1
    print w

myx=x[:,1]                                    #绘制训练数据
myy=x[:,2]
pl.subplot(111)
x_max=np.max(myx)+15
x_min=np.min(myx)-5
y_max=np.max(myy)+50
y_min=np.min(myy)-5
pl.xlabel(u"x")
pl.xlim(x_min,x_max)
pl.ylabel(u"y")
pl.ylim(y_min,y_max)
for i in range(0,len(d)):
    if d[i]==1:
        pl.plot(myx[i],myy[i],'r*')
    else:
        pl.plot(myx[i],myy[i],'ro')
#绘制测试点
test=np.array([b,9,19])
if comy(w,test)>0:
    pl.plot(test[1],test[2],'b*')
else:
    pl.plot(test[1],test[2],'bo')
test=np.array([b,9,64])
if comy(w,test)>0:
    pl.plot(test[1],test[2],'b*')
else:
    pl.plot(test[1],test[2],'bo')
test=np.array([b,9,16])
if comy(w,test)>0:
    pl.plot(test[1],test[2],'b*')
else:
    pl.plot(test[1],test[2],'bo')
test=np.array([b,9,60])
if comy(w,test)>0:
    pl.plot(test[1],test[2],'b*')
else:
    pl.plot(test[1],test[2],'bo')
#绘制分类线
testx=np.array(range(0,20))
testy=testx*2+1.68
pl.plot(testx,testy,'g--')
pl.show()   
for xn in x:
    print "%d  %d => %d" %(xn[1],xn[2],comy(w,xn))

# from cmath import sqrt
# class  Point_2d(object):
#     def __init__(self):
#         self.name = "2d point"
# 
#     def setx(self, x):
#     # write your code here
#         self.x = x;
#         
#     def sety(self,y):
#     # write your code here
#         self.y = y;
#         
#     def distance(self, point_a, point_b):
#     # write your code here
#         return sqrt((point_a.x - point_b.x) ** 2 + (point_a.y - point_b.y) ** 2);
# 
# class Point_3d(Point_2d):
#     def __init__(self):
#         self.name = "3d point"
# 
#     def setz(self, z):
#     # write your code here    
#         self.z = z;
#         
#     def distance(self, point_a, point_b):
#     # write your code here
#         return sqrt((point_a.x - point_b.x) ** 2 + (point_a.y - point_b.y) ** 2 + (point_a.z - point_b.z) ** 2);
#  
# a = Point_2d()
# a.setx(0)
# a.sety(4)
# b = Point_2d()
# b.setx(3)
# b.sety(0)
# print a.distance(a, b) 
# 
# c = Point_3d()
# c.setx(0)
# c.sety(0)
# c.setz(0)
# d = Point_3d()
# d.setx(0)
# d.sety(1)
# d.setz(0)
# print c.distance(c, d) 