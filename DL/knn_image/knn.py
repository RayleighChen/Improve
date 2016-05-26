#-*- coding:utf-8 -*-
import read_batch

class NearestNeighbor:
  def __init__(self):
    pass

  def train(self, X, y):

    self.Xtr = X
    self.ytr = y

  def predict(self, X):
    num_test = X.shape[0]
    Ypred = np.zeros(num_test, dtype = self.ytr.dtype)

    for i in xrange(num_test):
      distances = np.sum(np.abs(self.Xtr - X[i,:]), axis = 1)
      min_index = np.argmin(distances) # ȡ���ͼƬ���±�
      Ypred[i] = self.ytr[min_index] # ��¼��label

    return Ypred

nn = NearestNeighbor() # ��ʼ��һ������ڶ���
nn.train(Xtr_rows, Y_train) # ѵ��...��ʵ���Ƕ�ȡѵ����
Yte_predict = nn.predict(Xte_rows) # Ԥ��

print 'accuracy: %f' % ( np.mean(Yte_predict == Y_test) )