# -*- coding: utf-8 -*-
import sys

#生成候选集C1
#return：字典key=item;value=item出现的次数
def getC1(srcdata): 
    c1 = {} 
    for transaction in srcdata: 
        for item in transaction: 
            key = frozenset(set([item])) #frozenset才可以作为字典的key
            #计数item
            if key in c1: 
                c1[key] = c1[key] + 1 
            else: 
                c1[key] = 1 
    return c1 

#return： 满足最小支持度的候选集
def getL(c, supct): 
    # 删除小于最小支持度的item
    for key in [item for item in c if c[item] < supct]: 
        del c[key]
    #if c != {}:
	    #print c		
    return c 

#根据上一个L产生候选集C 
#扫描源数据，计数item
def getnextcandi(preL, srcdata): 
    c = {} 
    for key1 in preL: 
        for key2 in preL: 
            if key1 != key2: 
                # preL 和 preL 生成笛卡尔积 
                key = key1.union(key2) 
                c[key] = 0 
    #计数item 
    for i in srcdata: 
        for item in c: 
            if item.issubset(i): 
                c[item] = c[item] + 1			
    return c 

# Apriori 算法 
def Apriori(filename, supct): 
    #读取数据文件
    #文件格式:一行一个事务,一个事务的各个元素以Tab(\t)分隔
    srcdata = [line.strip("\n").split(" ") for line in file(filename)]
    c = getC1(srcdata)	
	
    L = getL(c, supct) 
    c = getnextcandi(L, srcdata) 
    return c

if __name__ == "__main__":
    if len(sys.argv) == 3:
        #Usage:   apri.py filename surpport
        items = Apriori(sys.argv[1], int(sys.argv[2]))
        for key in [item for item in items if items[item] < int(sys.argv[2])]: 
            del items[key]
        ap = {} 
        for itor in items:
            #print items[itor]
            #print itor
            strword = ''
            for word in itor:
                strword += word + " "
            ap[strword.strip(' ')] = items[itor]
        linelst = sorted(ap.items(), lambda x, y: cmp(x[1], y[1]), reverse=True)
        for i in range(len(linelst)):
		    print "#" + str(linelst[i][1]) + " " + linelst[i][0]
        #for (k, v) in ap.items():
            #print "#" + str(v) + " " + k
    else:
        #for example
        print "err args"
