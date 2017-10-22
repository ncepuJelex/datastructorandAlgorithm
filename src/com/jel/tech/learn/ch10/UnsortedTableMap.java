package com.jel.tech.learn.ch10;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.jel.tech.learn.ch09.Entry;

/**
 * 简单的无序、基于ArrayList实现的map,
 * 在最坏情况下，需要O(n)时间，因为涉及到遍历列表查询key,
 * 移除操作时，如果移除的不是最后一个元素，把最后一个元素
 * 复制到要移除的位置（反正是无序的），而不是把要移除元素后面的
 * 元素整体往前移动一个位置，那效率不敢直视！
 * @author jelex.xu
 * @date 2017年10月21日
 * @param <K>
 * @param <V>
 */
public class UnsortedTableMap<K, V> extends AbstractMap<K, V> {

	private ArrayList<MapEntry<K, V>> table = new ArrayList<>();

	public UnsortedTableMap() {
	}

	@Override
	public int size() {
		return table.size();
	}
	/*
	 * 辅助查找方法
	 */
	private int findIndex(K key) {
		for(int j=0, n=table.size(); j<n; j++) {
			if(table.get(j).getKey().equals(key)) {
				return j;
			}
		}
		return -1;
	}
	@Override
	public V get(K key) {
		int index = this.findIndex(key);
		if(index == -1) return null;
		return table.get(index).getValue();
	}

	@Override
	public V put(K key, V value) {
		int index = this.findIndex(key);
		if(index == -1) {
			table.add(new MapEntry<K, V>(key, value));
			return null;
		} else {
			//怪不得MapEntry中的setValue要返回一个值，原来这里就用到了！
			return table.get(index).setValue(value);
		}
	}

	@Override
	public V remove(K key) {
		int j = this.findIndex(key);
		if(j == -1) return null;
		int n = table.size();
		V answer = table.get(j).getValue();
		//移除的不是最后一个元素,把最后一个元素复制到将要被移除的元素位置
		if(j != n-1) {
			table.set(j, table.get(n-1));
		}
		//值已经移动到j位置了，最后一个元素可以移除了！
		table.remove(n-1);
		return answer;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		return new EntryIterable();
	}
	private class EntryIterator implements Iterator<Entry<K, V>> {
		//列表中遍历到的当前index位置
		private int j = 0;
		@Override
		public boolean hasNext() {
			return j<table.size();
		}
		@Override
		public Entry<K, V> next() {
			if(j == table.size()) {
				throw new NoSuchElementException();
			}
			return table.get(j++);
		}
		@Override
		public void remove() {
			//不支持在iterator遍历时的删除操作
			throw new UnsupportedOperationException();
		}
	}
	private class EntryIterable implements Iterable<Entry<K, V>> {
		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new EntryIterator();
		}
	}

	public static void main(String[] args) {
		UnsortedTableMap<Integer, String> m = new UnsortedTableMap<>();
		m.put(101, "张三");
		m.put(102, "李四");
		m.put(103, "王五");
		m.put(7, "Cristiano Ronaldo");
		m.put(10, "Messi");
		Iterator<Entry<Integer, String>> iterator = m.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Integer, String> next = iterator.next();
			System.out.println(next.getKey() + "=>" + next.getValue());
		}
		String string = m.get(7);
		System.out.println(string);
		String remove = m.remove(101);
		System.out.println(remove);
		/*
		 *running result:
		 	101=>张三
			102=>李四
			103=>王五
			7=>Cristiano Ronaldo
			10=>Messi
			Cristiano Ronaldo
			张三
		 */
	}
}
