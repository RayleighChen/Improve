#-*- coding:utf-8 -*-

import os
import cPickle
import sys
import numpy as np

def load_CIFAR_batch(filename):
    with open(filename, 'r') as f:
        print f
        datadict=cPickle.load(f)

        X=datadict['data']
        Y=datadict['labels']

        X=X.reshape(10000, 3, 32, 32).transpose(0,2,3,1).astype("float")
        Y=np.array(Y)

        return X, Y


def load_CIFAR10(ROOT):

    xs=[]
    ys=[]

    for b in range(1,6):
        f=os.path.join(ROOT, "data_batch_%d" % (b, ))
        X, Y=load_CIFAR_batch(f)
        xs.append(X)
        ys.append(Y)

    X_train=np.concatenate(xs)
    Y_train=np.concatenate(ys)

    del X, Y

    X_test, Y_test=load_CIFAR_batch(os.path.join(ROOT, "test_batch"))

    return X_train, Y_train, X_test, Y_test

X_train, Y_train, X_test, Y_test = load_CIFAR10('../cifar10/') 

Xtr_rows = X_train.reshape(X_train.shape[0], 32 * 32 * 3) # Xtr_rows : 50000 x 3072
Xte_rows = X_test.reshape(X_test.shape[0], 32 * 32 * 3) # Xte_rows : 10000 x 3072