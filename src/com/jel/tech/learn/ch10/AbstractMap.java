package com.jel.tech.learn.ch10;

import java.util.Iterator;

import com.jel.tech.learn.ch09.Entry;

/**
 * map 抽象基类
 * by the way,为什么时隔一个礼拜才能继续学习，因为
 * 过去一周每天加班，让2个礼拜完成的活1个礼拜完成，f**k,
 * 晚上下班回来没有精力再学习了，今天周六，go on learning~
 * @author jelex.xu
 * @date 2017年10月21日
 * @param <K>
 * @param <V>
 */
public abstract class AbstractMap<K, V> implements Map<K, V> {

	protected static class MapEntry<K, V> implements Entry<K, V> {

		private K k;
		private V v;

		public MapEntry(K k, V v) {
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

		protected void setKey(K k) {
			this.k = k;
		}
		protected V setValue(V value) {
			V old = this.v;
			this.v = value;
			return old;
		}
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	private class KeyIterator implements Iterator<K> {
		//“寄生”在entrySet()实现类上
		private Iterator<Entry<K, V>> entries = entrySet().iterator();
		@Override
		public boolean hasNext() {
			return entries.hasNext();
		}
		@Override
		public K next() {
			return entries.next().getKey();
		}
		@Override
		public void remove() {
			//不支持移除key操作
			throw new UnsupportedOperationException();
		}
	}
	private class KeyIterable implements Iterable<K> {
		@Override
		public Iterator<K> iterator() {
			return new KeyIterator();
		}

	}
	@Override
	public Iterable<K> keySet() {
		return new KeyIterable();
	}
	/*
	 * 整个套路和之前的章节是一样嘀！
	 */
	private class ValueIterator implements Iterator<V> {
		private Iterator<Entry<K, V>> entries = entrySet().iterator();
		@Override
		public boolean hasNext() {return entries.hasNext();}
		@Override
		public V next() {return entries.next().getValue();}
		@Override
		public void remove() {throw new UnsupportedOperationException();}
	}
	private class ValueIterable implements Iterable<V> {
		@Override
		public Iterator<V> iterator() {
			return new ValueIterator();
		}
	}
	@Override
	public Iterable<V> values() {
		return new ValueIterable();
	}

}
