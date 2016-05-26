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
      min_index = np.argmin(distances) # 取最近图片的下标
      Ypred[i] = self.ytr[min_index] # 记录下label

    return Ypred

nn = NearestNeighbor() # 初始化一个最近邻对象
nn.train(Xtr_rows, Y_train) # 训练...其实就是读取训练集
Yte_predict = nn.predict(Xte_rows) # 预测

print 'accuracy: %f' % ( np.mean(Yte_predict == Y_test) )