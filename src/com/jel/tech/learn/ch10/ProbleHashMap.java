package com.jel.tech.learn.ch10;

import java.util.ArrayList;
import java.util.Iterator;

import com.jel.tech.learn.ch09.Entry;

/**
 * 线性查找，该种方式的map不需要维护一个ArrayList,
 * 因为通过查找能保证每个元素对应一个坑，不会有重复问题（冲突）
 * @author jelex.xu
 * @date 2017年10月22日
 * @param <K>
 * @param <V>
 */
public class ProbleHashMap<K, V> extends AbstractHashMap<K, V> {

	//只需要该类型数组保存数据，而不需要像ChainHashMap那样，把map嵌套在arraylist中
	private MapEntry<K,V>[] table;
	//表示要移除的元素标记
	private MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null);

	public ProbleHashMap(int cap, int prime) {
		super(cap, prime);
	}
	public ProbleHashMap(int cap) {
		super(cap);
	}
	public ProbleHashMap() {super(); }

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>();
		for(int h=0; h<this.capacity; h++) {
			//过滤为null的和有待删除标识的元素
			if(!isAvailable(h)) {
				buffer.add(table[h]);
			}
		}
		return buffer;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createTable() {
		table = new MapEntry[this.capacity];
	}

	/*
	 * 查找槽点:
	 * 	h : 经过hashValue()算法计算出来的hashValue,
	 *  k : key值，
	 *  Returns index with key k, or −(a+1) such that k could be added at index a.
	 */
	private int findSlot(int h, K k) {
		int available = -1;
		int j = h;
		do {
			//如果可用（同时也意味着没找到，其它地方也不用找了，没有）
			if(isAvailable(j)) {
				//第一次循环进来！
				if(available == -1) {
					available = j;
				}
				//如果该位置元素为空（因为可用状态有2种状态还有一种是DEFUNCT）
				if(table[j] == null) {
					//跳出循环，相当于查找失败了，因为没找到k（h）对应下的元素
					break;
				}
			}
			//table中有key为k的MapEntry元素，找到了！
			else if(table[j].getKey().equals(k)){
				return j;
			}
			//没找到，让索引位置前移，接着循环找！
			j = (j+1)%this.capacity;
		} while( j != h); //对table循环遍历一次
		/*
		 * 到这步了就表示没找到，要么是因为break出来，table本来就没有key对应的元素
		 * 存在，要么是因为整个table中没有可用的空槽，并且里面每个元素的key竟然
		 * 都不和我们条件给出的key相等！
		 * 	1.如果是break出来的，那么返回的位置（其中的available）表示找到的第一个元素为null的位置;
		 *  2.如果是遍历一遍还没找到位置，流程才到这的，说明table中有好多DEFUNCT!
		 *    此时，我们返回的位置（其中的available）表示最后一次找到的DEFUNCT位置，
		 * 不管怎样，available位置都是可用的！
		 */
		//我们采用这种方式（有正负之分），以便利用它的状态搞事件
		return -(available+1);
	}

	/*
	 * 判断位置j上是否可用——现在该位置上为空或者有删除状态标记
	 */
	private boolean isAvailable(int j) {
		return table[j] == null || table[j] == DEFUNCT;
	}

	@Override
	protected V bucketGet(int hashValue, K key) {
		int j = this.findSlot(hashValue, key);
		//没找到！
		if(j < 0) {
			return null;
		}
		return table[j].getValue();
	}

	/**
	 * 返回该key之前对应的值，新插入时为null,更新时为之前key对应的value
	 */
	@Override
	protected V bucketPut(int hashValue, K key, V value) {
		int j = this.findSlot(hashValue, key);
		//table中已有对应key的元素，此时put是更新操作
		if(j > 0) {
			return table[j].setValue(value);
		}
		//新插入元素,注意，之前返回的值是j=-(available+1),
		//那么 -(j+1) = -j-1 = (available+1)-1=available
		table[-(j+1)] = new MapEntry<>(key, value);
		//更新元素个数
		this.n++;
		return null;
	}

	@Override
	protected V bucketRemove(int hashValue, K key) {
		int j = this.findSlot(hashValue, key);
		//没找到
		if(j < 0) return null;
		V answer = table[j].getValue();
		//标记为移除标识
		table[j] = DEFUNCT;
		//更新元素个数
		this.n--;
		return answer;
	}
	public static void main(String[] args) {
		ProbleHashMap<Integer, String> m = new ProbleHashMap<>();
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
		 * running result:
		 	10=>Messi
			103=>王五
			7=>Cristiano Ronaldo
			102=>李四
			101=>张三
			Cristiano Ronaldo
			张三
		 */
	}
}
