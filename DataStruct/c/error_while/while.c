//Error while 
//Q: why the result(count) is not equal to a * b


#include <stdio.h>
int main()
{
	int n, a, b, c;
	int count;
	scanf("%d", &n);
	while(n--) {
		scanf("%d%d", &a, &b);
		count = 0;
		while(a--) {
			while(b--) {
				count++;
			}
		}
		printf("%d\n", count);
	}
	
	return 0;
}