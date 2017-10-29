package com.jel.tech.learn.ch10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;

/**
 * 一个key可以有多个value
 * @author jelex.xu
 * @date 2017年10月29日
 * @param <K>
 * @param <V>
 */
public class HashMultiMap<K, V> {
	//底层数据结构
	Map<K,List<V>> map = new HashMap<>();
	//元素总数
	private int total = 0;
	//
	public HashMultiMap() {
	}
	public int size() {
		return total;
	}
	public boolean isEmpty() {
		return total == 0;
	}
	/*
	 * 得到key对应的values
	 */
	public Iterable<V> get(K key) {
		List<V> secondary = map.get(key);
		if(secondary != null) {
			return secondary;
		}
		return new ArrayList<>();
	}
	/*
	 * put操作
	 */
	public void put(K key, V value) {
		List<V> secondary = map.get(key);
		if(secondary == null) {
			secondary = new ArrayList<>();
			//原来不存在key对应的values，要new出来放到map中
			map.put(key, secondary);
		}
		secondary.add(value);
		//更新元素数量
		total++;
	}
	/*
	 * 删除操作
	 */
	public boolean remove(K key, V value) {
		boolean wasRemoved = false;
		List<V> secondary = map.get(key);
		if(secondary != null) {
			wasRemoved = secondary.remove(value);
			//如果列表中存在value，那么一般会删除成功的，返回true
			if(wasRemoved) {
				//更新元素数量
				total--;
				//如果原本列表中就这么一个value,那删除后为空了，需要把key从map中删除
				if(secondary.isEmpty()) {
					map.remove(key);
				}
			}
		}
		return wasRemoved;
	}
	/*
	 * 删除该key对应的所有values
	 */
	public Iterable<V> removeAll(K key) {
		List<V> secondary = map.get(key);
		if(secondary != null) {
			//更新元素数量
			total -= secondary.size();
			//直接从map中把key对就的记录删除
			map.remove(key);
		} else {
			secondary = new ArrayList<>();
		}
		return secondary;
	}
	/*
	 * 遍历
	 */
	public Iterable<Map.Entry<K, V>> entries() {
		List<Map.Entry<K, V>> result = new ArrayList<>();
		for(Map.Entry<K, List<V>> secondary : map.entrySet()) {
			K key = secondary.getKey();
			for(V value : secondary.getValue()) {
				result.add(new AbstractMap.SimpleEntry<K,V>(key, value));
			}
		}
		return result;
	}
}
