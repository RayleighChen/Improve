#include <stdio.h>
#include "hash.h"

void main()
{
	hashmap *hMap;
	hMap = hmap_creat(int_hash_fn, int_eq_fn, int_del_fn);

	if(!hmap_put(hMap, (void*)"test", (void*)"value"))
		return;
	printf( "%s\n", (char*)hmap_get(hMap, (void*)"test") );
	free_hmap(hMap);
	hMap = NULL;
}