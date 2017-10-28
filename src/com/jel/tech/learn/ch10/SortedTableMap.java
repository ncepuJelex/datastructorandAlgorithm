package com.jel.tech.learn.ch10;

import java.util.ArrayList;
import java.util.Comparator;

import com.jel.tech.learn.ch09.Entry;

/**
 * 第一个sorted map实现
 * 底层使用array list实现是为了能使用二叉树查找
 * @author jelex.xu
 * @date 2017年10月28日
 * @param <K>
 * @param <V>
 */
public class SortedTableMap<K, V> extends AbstractSortedMap<K, V> {
	//存储数据结构
	private ArrayList<MapEntry<K, V>> table = new ArrayList<>();

	public SortedTableMap() {
		super();
	}
	public SortedTableMap(Comparator<K> comp) {
		super(comp);
	}

	/*
	 * 查找方法，供内部调用，返回结果为：
	 * 	1.找到key时，返回key所在索引;
	 *  2.没找到时，返回key可插入的位置
	 */
	private int findIndex(K key, int low, int high) {
		/*
		 * 出现这种情况说明没找到相应key,此时分3种情况：
		 *  1.key很小时，此时low=0, high=-1,返回的位置为high+1=0;
		 *  2.key很大时，此时low=high+1,返回的位置为high+1为table的大小，即最后的元素的下一个位置;
		 *  3.key大小在table中的key之间时……
		 */
		if(high < low) return high+1;

		int mid = (low+high) >> 1;
		//比较key的大小
		int comp = compare(key, table.get(mid));
		//相等
		if(comp == 0) {
			return mid;
		} else if(comp < 0) { //key 小于mid上的key
			return findIndex(key, low, mid-1); //这里是引起high=-1的原因
		} else {
			return findIndex(key, mid+1, high); //这里是引起2.的原因
		}
	}

	private int findIndex(K key) {
		return findIndex(key, 0, table.size()-1);
	}

	private Entry<K,V> safeEntry(int j) {
		if(j<0 || j>=table.size()) {
			return null;
		}
		return table.get(j);
	}

	@Override
	public Entry<K, V> firstEntry() {
		return safeEntry(0);
	}

	@Override
	public Entry<K, V> lastEntry() {
		return safeEntry(table.size()-1);
	}

	@Override
	public Entry<K, V> ceilingEntry(K k) throws IllegalArgumentException {
		return safeEntry(findIndex(k));
	}

	@Override
	public Entry<K, V> floorEntry(K k) throws IllegalArgumentException {
		int j = findIndex(k);
		//如果没匹配上，此时j要修正为k所要插入位置的前一个位置
		if(j==size() || !k.equals(table.get(j).getKey())) {
			j--;
		}
		return safeEntry(j);
	}

	@Override
	public Entry<K, V> lowerEntry(K k) throws IllegalArgumentException {
		return safeEntry(findIndex(k)-1);
	}

	@Override
	public Entry<K, V> higherEntry(K k) throws IllegalArgumentException {
		int j = findIndex(k);
		//匹配上了，j要加1，如果没匹配上，j本身就表示后一个位置，就不用加1了
		if(j<size() && k.equals(table.get(j))) {
			j++;
		}
		return safeEntry(j);
	}

	private Iterable<Entry<K,V>> snapshot(int startIndex ,K stop) {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>();
		int j = startIndex;
		//stop==null:用于entrySet()遍历所有；
		//compare(..)条件用于subMap()功能；
		while(j<table.size() && (stop==null || compare(stop, table.get(j))>0)) {
			buffer.add(table.get(j++));
		}
		return buffer;
	}

	@Override
	public Iterable<Entry<K, V>> subMap(K from, K to) throws IllegalArgumentException {
		return snapshot(findIndex(from), to);
	}

	@Override
	public int size() {
		return table.size();
	}

	@Override
	public V get(K key) {
		//查找key对应的元素
		int j = findIndex(key);
		//没查找到，返回null
		//前一个条件表示key太大了，找到table最后都没匹配到，
		//后一个条件表示key太小（此时j=0） 或者key在table中元素的key大小之间！
		if(j==size() || compare(key, table.get(j)) != 0) {
			return null;
		}
		return table.get(j).getValue();
	}

	@Override
	public V put(K key, V value) {
		int j = findIndex(key);
		//匹配到了key，更改操作，返回之前的元素值
		if(j<size() && compare(key, table.get(j)) == 0) {
			return table.get(j).setValue(value);
		}
		/*
		 * 没匹配到，插入新元素
		 */
		table.add(j, new MapEntry<K, V>(key, value));
		return null;
	}

	@Override
	public V remove(K key) {
		int j = findIndex(key);
		//没匹配到
		if(j==size() || compare(key, table.get(j)) != 0) {
			return null;
		}
		return table.remove(j).getValue();
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		return snapshot(0, null);
	}

}
