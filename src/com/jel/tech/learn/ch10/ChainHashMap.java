package com.jel.tech.learn.ch10;

import java.util.ArrayList;
import java.util.Iterator;

import com.jel.tech.learn.ch09.Entry;

/**
 * 第一个hash map实现，借助了之前的UnsortedTableMap,允许冲突，因为
 * 每个位置对应一个“桶”——一个map,hashValue或者key一致的元素都存放在该桶里，
 * （因为不同值的key的hashValue可能相同，就那么 % 一下，结果肯定有不同key值对应相同的hashValue,
 * 当然存放相同 key下的元素，自然就相当于覆盖了，否则就形成了链条）
 * 所以形成了链条_chain hash map
 *
 * @param <K>
 * @param <V>
 * @author jelex.xu
 * @date 2017年10月22日
 */
public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {

    private UnsortedTableMap<K, V>[] tables;

    public ChainHashMap(int cap, int prime) {
        super(cap, prime);
    }

    public ChainHashMap(int cap) {
        super(cap);
    }

    public ChainHashMap() {
        super();
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        for (int h = 0; h < capacity; h++) {
            //需要判空，因为没有元素时默认是null，节省空间嘛！
            if (tables[h] != null) {
                for (Entry<K, V> e : tables[h].entrySet()) {
                    buffer.add(e);
                }
            }
        }
        return buffer;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void createTable() {
        tables = (UnsortedTableMap<K, V>[]) new UnsortedTableMap[this.capacity];
    }

    @Override
    protected V bucketGet(int hashValue, K key) {
        UnsortedTableMap<K, V> bucket = tables[hashValue];
        if (bucket != null) {
            return bucket.get(key);
        }
        return null;
    }

    @Override
    protected V bucketPut(int hashValue, K key, V value) {
        UnsortedTableMap<K, V> bucket = tables[hashValue];
        //之前还没有元素存放到该位置下的桶里，new一个出来,这也验证了遍历时候要判空
        if (bucket == null) {
            bucket = tables[hashValue] = new UnsortedTableMap<>();
        }
        //记录 存放元素之前时 桶里元素个数
        int oldSize = bucket.size();
        //新元素插入（也可能是更新）进去
        V answer = bucket.put(key, value);
        //更新元素个数(更新的话，元素个数不变)
        this.n += (bucket.size() - oldSize);
        return answer;
    }

    @Override
    protected V bucketRemove(int hashValue, K key) {
        UnsortedTableMap<K, V> bucket = tables[hashValue];
        //没有该key对应的元素
        if (bucket == null) return null;
        int oldSize = bucket.size();
        //删除元素
        V answer = bucket.remove(key);
        //更新元素个数
        this.n += (oldSize - bucket.size());
        return answer;
    }

    public static void main(String[] args) {
        ChainHashMap<Integer, String> m = new ChainHashMap<>();
        m.put(101, "张三");
        m.put(102, "李四");
        m.put(103, "王五");
        m.put(7, "Cristiano Ronaldo");
        m.put(10, "Messi");
        Iterator<Entry<Integer, String>> iterator = m.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Integer, String> next = iterator.next();
            System.out.println(next.getKey() + "=>" + next.getValue());
        }
        String string = m.get(7);
        System.out.println(string);
        String remove = m.remove(101);
        System.out.println(remove);
		/*
		 * running result:
		  	103=>王五
			10=>Messi
			101=>张三
			7=>Cristiano Ronaldo
			102=>李四
			Cristiano Ronaldo
			张三
		 */
    }
}
