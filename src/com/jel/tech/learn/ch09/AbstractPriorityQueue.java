package com.jel.tech.learn.ch09;

import java.util.Comparator;

public abstract class AbstractPriorityQueue<K, V> implements PriorityQueue<K, V> {

	protected static class PQEntry<K, V> implements Entry<K, V> {

		private K k;
		private V v;

		public PQEntry(K k, V v) {
			this.k = k;
			this.v = v;
		}

		@Override
		public K getKey() {
			return k;
		}

		@Override
		public V getValue() {
			return v;
		}

		public void setK(K k) {
			this.k = k;
		}

		public void setV(V v) {
			this.v = v;
		}

	}

	private Comparator<K> comp;

	public AbstractPriorityQueue(Comparator<K> comp) {
		this.comp = comp;
	}

	public AbstractPriorityQueue() {
		this(new DefaultComparator<K>());
	}

	/*
	 * 通过它们的Key比较大小
	 */
	protected int compare(Entry<K, V> entry1, Entry<K, V> entry2) {
		return comp.compare(entry1.getKey(), entry2.getKey());
	}

	/*
	 * 通过离散数学中的 Reflexive property来验证Key的合法性
	 */
	protected boolean checkKey(K key) throws IllegalArgumentException {
		try {
			return comp.compare(key, key) == 0;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Incompatible key");
		}
	}

	/*
	 * 这条没什么好说的！size()方法由具体子类实现
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
}
