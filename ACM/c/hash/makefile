test : main.o hash.o
	gcc -o test main.o hash.o
main.o : main.c hash.h
	gcc -c main.c
hash.o : hash.c hash.h
	gcc -c hash.c
	
clean:
	rm main.o hash.o
