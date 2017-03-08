import numpy as np

def pca(data, num):
  norm_data = data - data.mean(0)
  eig_val, eig_vec = np.linalg.eig(norm_data.T.dot(norm_data))
  eig_pairs = eig_pairs = [(np.abs(eig_val[i]), eig_vec[:,i]) for i in range(data.shape[1])]
  eig_pairs.sort(reverse=True)
  
  sel_feature = np.array([ sel[1] for sel in eig_pairs[:num]])
  pca_data = norm_data.dot(sel_feature.T)
  print pca_data
  
X = np.array([[-1, 1], [-2, -1], [-3, -2], [1, 1], [2, 1], [3, 10]])
pca(X, 1)
