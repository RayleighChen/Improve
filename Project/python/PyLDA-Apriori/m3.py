# -*- coding: utf-8 -*-
import sys

#���ɺ�ѡ��C1
#return���ֵ�key=item;value=item���ֵĴ���
def getC1(srcdata): 
    c1 = {} 
    for transaction in srcdata: 
        for item in transaction: 
            key = frozenset(set([item])) #frozenset�ſ�����Ϊ�ֵ��key
            #����item
            if key in c1: 
                c1[key] = c1[key] + 1 
            else: 
                c1[key] = 1 
    return c1 

#return�� ������С֧�ֶȵĺ�ѡ��
def getL(c, supct): 
    # ɾ��С����С֧�ֶȵ�item
    for key in [item for item in c if c[item] < supct]: 
        del c[key]
    #if c != {}:
	    #print c		
    return c 

#������һ��L������ѡ��C 
#ɨ��Դ���ݣ�����item
def getnextcandi(preL, srcdata): 
    c = {} 
    for key1 in preL: 
        for key2 in preL: 
            if key1 != key2: 
                # preL �� preL ���ɵѿ����� 
                key = key1.union(key2) 
                c[key] = 0 
    #����item 
    for i in srcdata: 
        for item in c: 
            if item.issubset(i): 
                c[item] = c[item] + 1			
    return c 

# Apriori �㷨 
def Apriori(filename, supct): 
    #��ȡ�����ļ�
    #�ļ���ʽ:һ��һ������,һ������ĸ���Ԫ����Tab(\t)�ָ�
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
