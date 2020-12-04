package com.jel.tech.learn.ch03;

/**
 * 记录前几名的姓名和分数
 *
 * @author jelex.xu
 * @date 2017年9月29日
 */
public class GameEntry {

    private String name;
    private int score;

    public GameEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "[" + name + ", " + score + "]";
    }


}
