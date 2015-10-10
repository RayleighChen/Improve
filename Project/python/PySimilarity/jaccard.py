# -*- coding: utf-8 -*-
from __future__ import division
def jaccard(s1,s2):
    intersection = []
    union = []
    s1_list = []
    s2_list = []
    for i in s1:
        s1_list.append(i)
    for i in s2:
        s2_list.append(i)
    for word_1 in s1_list:
        if(word_1 in s2_list):
            if word_1 not in intersection:
                
                intersection.append(word_1)
    ls = s1_list + s2_list
    for i in ls:
        if i not in union:
            union.append(i)
    

    num_1 = len(intersection)
    num_2 = len(union)

    jaccard = num_1/num_2
    print "交集:"
    print intersection
    print "并集:"
    print union
    print "相似度为："
    print jaccard

s1 = "abcdefabcde"
s2 = "abcdeiabcde"
jaccard(s1,s2)