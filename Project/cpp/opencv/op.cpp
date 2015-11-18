#include <iostream>
#include <fstream>
#include <sys/stat.h>
#include "opencv2/opencv.hpp"
//#include "opencv2/gpu/gpu.hpp"
#include <vector>
#include <dirent.h>
using namespace cv;
using namespace std;

vector<string> files2;
void getFiles( string path, vector<string>& files );
void getFloder( string path, vector<string>& files );
int main(int, char**)
{
	int i;
	vector<string> files;
	vector<string> filesFloder;
	vector<string> Floder;
	//int gpu_num = cv::gpu::getCudaEnabledDeviceCount();
	string path_in = "/media/rayleigh/I/UCF-101";//文件夹路径
	string path_out = "/media/rayleigh/I/ucfdata";//输出路径
	char * filePath = "/media/rayleigh/I/UCF-101";
	getFloder(filePath, files );//files里面存放文件名
	filesFloder = files;
	int size = files.size();
	for(i =0;i<size;i++)
	{
		int pos=-1;
		if((pos= filesFloder[i].find("UCF-101")) !=-1)
			filesFloder[i].replace(pos,7,"ucfdata");
		if((pos = filesFloder[i].find(".avi")) !=-1)
			filesFloder[i].replace(pos,4,"/");
		//cout<<filesFloder[i]<<endl;
		//mkdir(filesFloder[i].c_str(), 0755);
	}
	//getFiles(filePath,Floder);
	Floder = files2;
	for(i =0;i<Floder.size();i++)
	{
		int pos=-1;
		if((pos= files2[i].find("UCF-101")) !=-1)
			Floder[i].replace(pos,7,"ucfdata");
		if((pos = files2[i].find(".avi")) !=-1)
			Floder[i].replace(pos,4,"/");
		mkdir(Floder[i].c_str(), 0755);
		//cout<<Floder[i]<<endl;
	}
	ifstream fin;
	ofstream fout;
	VideoCapture cap;
	int count=0;
	size = files2.size();
	for(i=0; i<size; i++)
	{

		cap.open(files2[i]);
		cout<<"No."<<i<<":"<<files2[i]<<endl;
		long totalFrameNumber = cap.get(CV_CAP_PROP_FRAME_COUNT);
		totalFrameNumber = totalFrameNumber - totalFrameNumber % 16;
		Mat frame, frame_out,frame_old;
		string path_frame=Floder[i];
		for(int j=0;j<totalFrameNumber;j++)//j最大值需调整
		{
			cap>>frame;
			char s[100];
			sprintf(s, "%06d.jpg", j+1);

			if(frame.size().height==0)
			{
				imwrite(path_frame+"/"+s, frame_old);
				continue;
			}
			//count ++;
			resize(frame,frame_out,Size(171,128));
			//cout<<path_frame+s<<endl;
			frame_old = frame_out;
			imwrite(path_frame+"/"+s,frame_out);
		}
	}
	string video_name;//视频名*/
	return 0;
}


void getFloder( string path, vector<string>& files )
{
	struct dirent *ptr;
	DIR *dir;
	dir = opendir(path.c_str());
	//cout << "文件列表: " << endl;
	while ((ptr = readdir(dir)) != NULL) {

		//跳过'.'和'..'两个目录
		if (ptr->d_name[0] == '.')
			continue;
		//cout << ptr->d_name << endl;
		files.push_back(path +"/" + ptr->d_name);
	}

	for (int i = 0; i < files.size(); ++i) {
		//cout << path +"/" +files[i] << endl;
		getFiles(files[i], files2 );
	}

	closedir(dir);
}

void getFiles( string path, vector<string>& files )
{
	struct dirent *ptr;
	DIR *dir;
	dir = opendir(path.c_str());
	//cout << "文件列表: " << endl;
	while ((ptr = readdir(dir)) != NULL) {

		//跳过'.'和'..'两个目录
		if (ptr->d_name[0] == '.')
			continue;
		//cout << ptr->d_name << endl;
		files.push_back(path +"/" + ptr->d_name);
	}

	for (int i = 0; i < files.size(); ++i) {
		//cout << path + files[i] << endl;
	}

	closedir(dir);
}
