/*
 * hashmap.c
 * 
 */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "hash.h"

struct key_val_pair 
{
	key k;
	val v;
};

struct hashmap 
{
	key_val_pair* map;
	uint32_t size;
	uint32_t capacity;
	uint64_t (*hash_fn)(key);
	bool (*eq_fn)(key, key);
#ifdef HMAP_DESTRUCTORS
	void (*del_fn)(val);
#endif
};

hashmap* hmap_creat(uint64_t (*hash_fn)(key),
				bool (*eq_fn)(key, key)
			#ifdef HMAP_DESTRUCTORS
				, void (*del_fn)(val)
			#endif
				) 
{
					
	hashmap* hmap = (hashmap*) calloc(sizeof(hashmap), 1);
	hmap->map = (key_val_pair*) calloc(sizeof(key_val_pair), HMAP_PRESET_SIZE);
	hmap->size = 0;
	hmap->capacity = HMAP_PRESET_SIZE;
	hmap->hash_fn = hash_fn;
	hmap->eq_fn = eq_fn;
	
#ifdef HMAP_DESTRUCTORS
	hmap->del_fn = del_fn;
#endif

	return hmap;
}

hashmap* hmap_creat2(uint64_t (*hash_fn)(key),
				bool (*eq_fn)(key, key)
			#ifdef HMAP_DESTRUCTORS
				, void (*del_fn)(val)
			#endif
				, uint32_t set_size
				) 
{
	hashmap* hmap = (hashmap*) calloc(sizeof(hashmap), 1);
	hmap->map = (key_val_pair*) calloc(sizeof(key_val_pair), set_size);
	hmap->size = 0;
	hmap->capacity = set_size;
	hmap->hash_fn = hash_fn;
	hmap->eq_fn = eq_fn;
	
#ifdef HMAP_DESTRUCTORS
	hmap->del_fn = del_fn;
#endif

	return hmap;
}

void free_hmap(hashmap* hmap) 
{
#ifdef HMAP_DESTRUCTORS
	static uint32_t it;
	for(it = 0; it < hmap->size; ++it) 
	{
		if(hmap->map[it].v != NULL) 
		{
			hmap->del_fn(hmap->map[it].v);
		}
	}
#endif

	free(hmap->map);
	free(hmap);
}

static void __oa_hmap_put(key_val_pair* map, uint32_t size, 
                          uint64_t (*hash_fn)(key),
                          key in, val out) 
{
	static uint64_t hash;
	hash = hash_fn(in) % size;

	while(map[hash].v != NULL) 
	{
		hash = (hash + 1) % size;
	}
	
	map[hash].k = in;
	map[hash].v = out;
}

bool __hmap_put(hashmap* hmap, key in, val out) 
{
	if(((float)hmap->size) / hmap->capacity > 0.70) 
	{
		key_val_pair* temp = (key_val_pair*) malloc(hmap->capacity * HMAP_GROWTH_RATE);
		
		if(temp != NULL) 
			hmap->capacity *= HMAP_GROWTH_RATE;
		else 
			return false;
		
		static uint32_t it;
		for(it = 0; it < hmap->capacity; ++it) 
		{
			if(hmap->map[it].v != NULL) 
				__oa_hmap_put(temp, hmap->capacity, hmap->hash_fn, in, out);
		}
		
		free(hmap->map);
		hmap->map = temp;
	}
	
	__oa_hmap_put(hmap->map, hmap->capacity, hmap->hash_fn, in, out);
	hmap->size += 1;
	
	return true;
}

val __hmap_get(hashmap* hmap, key in) 
{
	static uint64_t hash;
	hash = hmap->hash_fn(in) % hmap->capacity;
	
	while(hmap->map[hash].v != NULL) 
	{
		if(hmap->eq_fn(in, hmap->map[hash].k)) 
		{
			return hmap->map[hash].v;
		}

		hash = (hash + 1) % hmap->capacity;
	}

	return NULL;
}

//64 bit Mix Functions
uint64_t int_hash_fn(key in)
{
	static uint64_t key_hash;
	key_hash = *((uint64_t*) in);
	
	key_hash = (~key_hash) + (key_hash << 21); // key = (key << 21) - key - 1; 
	key_hash = key_hash ^ (key_hash >> 24); 
	key_hash = (key_hash + (key_hash << 3)) + (key_hash << 8); // key * 265 
	key_hash = key_hash ^ (key_hash >> 14); 
	key_hash = (key_hash + (key_hash << 2)) + (key_hash << 4); // key * 21 
	key_hash = key_hash ^ (key_hash >> 28); 
	key_hash = key_hash + (key_hash << 31); 
	return key_hash; 
}

bool int_eq_fn(key a, key b) 
{
	return *((int*) a) == *((int*) b) ? true : false;
}

void int_del_fn(val q) 
{
	free(q);
};

// Dan Bernstein's string hash function (djb2)
uint32_t str_hash_fn(key in) 
{
	static uint32_t hash;
	int c;
	hash = 5381;
	
	while((c = *(unsigned char*)in++))
	{
		hash = ((hash << 5) + hash) + c;
	}
	
	return hash;
}

bool str_eq_fn(key a, key b) 
{
	return (strcmp((char*) a, (char*) b) == 0) ? true : false;
}

void str_del_fn(val q) 
{
	free(q);
};