# -*- coding: utf-8 -*-
'''
Created on 2014��9��30��

@author: Rayleigh
'''

def sort(data):
    length = len(data);
    for i in range(length - 1):
        for j in range(length - 1 - i):
            if (data[j] > data[j + 1]):
                tmp = data[j];
                data[j] = data[j + 1];
                data[j + 1] = tmp;

def chen(data):
    for i in range(len(data)):
        print data[i];


if __name__ == '__main__':
    data = [4, 2, 1, 5, 12, 99];
    chen(data);
    sort(data);
    chen(data);
    