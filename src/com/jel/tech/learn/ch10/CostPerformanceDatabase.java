package com.jel.tech.learn.ch10;

import com.jel.tech.learn.ch09.Entry;

/**
 * 使用SortedTableMap数据结构的一个场景：
 *  汽车（价格，性能）=（price, performance）,
 *  找出价格不大于某一个值，性能最好的那些车信息
 * @author jelex.xu
 * @date 2017年10月29日
 */
public class CostPerformanceDatabase {

	private SortedTableMap<Integer, Integer> map = new SortedTableMap<>();

	public CostPerformanceDatabase() {
	}

	public Entry<Integer, Integer> best(int c) {
		return map.floorEntry(c);
	}

	public void add(int c, int p) {
		Entry<Integer, Integer> other = map.floorEntry(c);
		/*
		 * 新加进来车的参数不如已经存在的最优值呢！
		 */
		if(other !=null && p<=other.getValue()) {
			return;
		}
		//竟然碰到一辆更优的车,保存到db中
		map.put(c, p);
		//把之前的非最优数据清除掉
		other = map.higherEntry(c);
		//价格比c要高，但是性能还比不上p,要你何用！
		while(other != null && other.getValue()<=p) {
			map.remove(other.getKey());
			other = map.higherEntry(c);
		}
	}
}
