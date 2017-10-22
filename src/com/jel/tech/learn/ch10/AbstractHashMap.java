package com.jel.tech.learn.ch10;

import java.util.ArrayList;
import java.util.Random;

import com.jel.tech.learn.ch09.Entry;

/**
 * hash map的抽象基类
 * 支持Multiply-Add-and- Divide (MAD) formula计算hash值,
 * and support for automatically resizing the underlying
 * hash table when the load factor reaches a certain threshold.
 * @author jelex.xu
 * @date 2017年10月22日
 * @param <K>
 * @param <V>
 */
public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {
	//元素个数
	protected int n = 0;
	//hash table大小
	protected int capacity;
	/*
	 * MAD用来计算hash值
	 */
	private int prime; //prime factor
	private long scale, shift; //the shift and scaling factors

	public AbstractHashMap(int cap, int prime) {
		capacity = cap;
		this.prime = prime;
		Random rand = new Random();
		//scale作为一个乘数因子，不能为0，所以采用这种方式
		scale = rand.nextInt(prime-1) + 1;
		//作为加数的一个部分，可以为0
		shift = rand.nextInt(prime);
		//创建table
		createTable();
	}
	/*
	 * 为简单起见，table大小使用2^k+1(2的k次方+1)，虽然它不一定是一个质数。
	 */
	public AbstractHashMap(int cap) {
		this(cap, 109345121);
	}
	public AbstractHashMap() {
		this(17);
	}

	@Override
	public int size() {
		return n;
	}
	@Override
	public V get(K key) {
		return bucketGet(hashValue(key), key);
	}
	/*
	 * MAD算法
	 */
	private int hashValue(K key) {
		return (int) ((Math.abs(key.hashCode()*scale + shift) % prime) % capacity);
	}
	@Override
	public V put(K key, V value) {
		V answer = bucketPut(hashValue(key), key, value);
		// keep load factor <= 0.5
		if(n > capacity/2) {
			//或者找一个相近的质数
			resize(2*capacity-1);
		}
		return answer;
	}
	/*
	 * 扩容:因为影响MAD算法的重新计算，所以不能像ArrayList那样直接分配
	 * 一个更大的容器，然后把元素复制过去，这里算是更新操作
	 */
	private void resize(int newCap) {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>(this.n);
		for(Entry<K, V> e : entrySet()) {
			buffer.add(e);
		}
		//重新改变大小
		this.capacity = newCap;
		//
		createTable();
		this.n = 0; //在put元素时会重新计算该值
		for(Entry<K, V> e : buffer) {
			put(e.getKey(), e.getValue());
		}
	}
	@Override
	public V remove(K key) {
		return bucketRemove(hashValue(key), key);
	}
	/*
	 * 抽象方法，让子类去实现
	 */
	protected abstract void createTable();
	protected abstract V bucketGet(int hashValue, K key);
	protected abstract V bucketPut(int hashValue, K key, V value);
	protected abstract V bucketRemove(int hashValue, K key);
}
