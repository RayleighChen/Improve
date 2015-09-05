/*
 * hashmap.h 
 * 
 */

#ifndef HASHMAP_H
#define HASHMAP_H

#define HMAP_PRESET_SIZE 	2 << 23
#define HMAP_GROWTH_RATE 	2

#define HMAP_DESTRUCTORS	// require destructors for value clean-up

#include <stdint.h>
#include <stdbool.h>
 
typedef void* key;
typedef void* val;
typedef int int32;

typedef struct key_val_pair key_val_pair;
typedef struct hashmap hashmap;

// create a hashmap
hashmap* hmap_creat(uint64_t (*hash_fn)(key),
                 bool (*eq_fn)(key, key)
#ifdef HMAP_DESTRUCTORS
                 , void (*del_fn)(val)
#endif
    );

hashmap* hmap_creat2(uint64_t (*hash_fn)(key),
                 bool (*eq_fn)(key, key)
#ifdef HMAP_DESTRUCTORS
                 , void (*del_fn)(val)
#endif
                 ,uint32_t set_size
    );

void free_hmap(hashmap*);

bool __hmap_put(hashmap* hmap, key in, val out);
#define hmap_put(hmap, in, out) __hmap_put(hmap, (key) in, (val) out)

val __hmap_get(hashmap* hmap, key in);
#define hmap_get(hmap, obj) __hmap_get(hmap, (key) obj)

uint64_t int_hash_fn(key);
bool int_eq_fn(key, key);
void int_del_fn(val);

#endif