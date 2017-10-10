package com.jel.tech.learn.ch09;

import java.util.Comparator;

import com.jel.tech.learn.ch07.LinkedPositionList;
import com.jel.tech.learn.ch07.Position;
import com.jel.tech.learn.ch07.PositionList;

/**
 * 有序（key以非降序排列）优先级队列
 * @author jelex.xu
 * @date 2017年10月10日
 * @param <K>
 * @param <V>
 */
public class SortedPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

	private PositionList<Entry<K, V>> list = new LinkedPositionList<>();

	public SortedPriorityQueue() {
		super();
	}
	public SortedPriorityQueue(Comparator<K> comp) {
		super(comp);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public Entry<K, V> insert(K k, V v) throws IllegalArgumentException {
		/*
		 * 校验
		 */
		checkKey(k);
		Entry<K, V> entry = new PQEntry<>(k, v);
		/*
		 * starts at the end of the list, walking backward until
		 * the new key is smaller than that of an existing entry;
		 * in the worst case, it progresses until reaching the front of the list
		 */
		Position<Entry<K, V>> walk = list.last();
		while(walk != null && compare(entry, walk.getElement()) < 0) {
			walk = list.before(walk);
		}
		//entry有最小的key
		if(walk == null) {
			list.addFirst(entry);
		} else {
			list.addAfter(walk, entry);
		}
		return entry;
	}

	@Override
	public Entry<K, V> min() {
		if(isEmpty()) {
			return null;
		}
		return list.first().getElement();
	}

	@Override
	public Entry<K, V> removeMin() {
		if(isEmpty()) {
			return null;
		}
		return list.remove(list.first());
	}

}
