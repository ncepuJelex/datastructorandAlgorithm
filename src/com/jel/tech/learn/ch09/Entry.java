package com.jel.tech.learn.ch09;

/**
 * For priority queues,
 * we use composition to pair a key k and a value v as a single object.
 * Priority Queue，从你开始！
 *
 * @param <K>
 * @param <V>
 * @author jelex.xu
 * @date 2017年10月9日
 */
public interface Entry<K, V> {

    K getKey();

    V getValue();
}
