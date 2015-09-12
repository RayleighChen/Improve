# -*- coding: utf-8 -*-
'''
Created on 2014年9月29日

@author: Rayleigh
'''
import numpy as np;
import cv2; 
   
if __name__ == '__main__':
    szl = 200;
    sz2 = 300;
    print u'产生的图像矩阵 （%d*%d）。。 ' % (szl, sz2);
    
    img = np.zeros((szl, sz2, 3), np.uint8);
    pos1 = np.random.randint(200, size = (2000, 1));
    pos2 = np.random.randint(300, size = (2000, 1));
    
    for i in range(2000):
        img[pos1[i], pos2[i], [0]] = np.random.randint(22, 122);
        img[pos1[i], pos2[i], [1]] = np.random.randint(22, 122);
        img[pos1[i], pos2[i], [2]] = np.random.randint(22, 122);
    
    cv2.imshow('preview', img);
    cv2.waitKey();
    cv2.destroyAllWindows(); 