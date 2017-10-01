package com.jel.tech.learn.ch03;
/**
 * 存储高分者的数据模型
 * @author jelex.xu
 * @date 2017年9月29日
 */
public class ScoreBoard {
	//记录条数
	private int numEntries = 0;
	//高分模型数组
	private GameEntry[] board;

	public ScoreBoard(int capacity) {
		this.board = new GameEntry[capacity];
	}
	/*
	 * 新插入一条记录：
	 * 不是所有的entry都有资格入选的（当board满了的时候，你的分数还不高已有entry高！没资格）
	 * 并且插入后entry是按降序排列的，要不怎么说是记录前几名的高分者呢！
	 */
	public void add(GameEntry entry) {
		int newScore = entry.getScore();
		//还没填充满时，或者你分数比目前最后一个人要高
		//我好奇哦，第2个条件不会出现数组越界，或者空指针之类的吗？
		//其实，结合第1个条件就不会了，因为出现空指针就是刚开始时候，此时第一个条件就过滤了
		//不会有机会到达第2个条件的，这么easy的东西，我怎么还这样？真是。
		if(numEntries<board.length || newScore>board[numEntries-1].getScore()) {
			//考虑要不要增加记录条数
			if(numEntries < board.length) {
				numEntries++;
			}
			/*
			 * 从倒数第2条记录开始往后移动一个位置，因为倒数第1条数据已经比newScore要低了
			 * 它注定要成为炮灰！被挤掉！
			 */
			int j = numEntries - 1; //最后一条记录的下标位置
			/*
			 * 游标从倒数第2个元素位置往前移动，把所有小于newScore
			 * 的分数记录往后移动
			 */
			while(j > 0 && board[j-1].getScore() < newScore) {
				board[j] = board[j-1];
				j--;
			}
			/*
			 * 此时j-1位置的分数比newScore要高！或者j等于0了，
			 * 不管怎样，j的位置就属于newScore的entry了！
			 */
			board[j] = entry;
		}
	}
	/*
	 * 有人cheat,所以要删掉TA的成绩！并通报批评！
	 */
	public GameEntry remove(int i) throws IndexOutOfBoundsException {
		if(i < 0 || i >= numEntries) {
			throw new IndexOutOfBoundsException("无效位置：" + i);
		}
		GameEntry ret = board[i]; //准备通报批评的对象！
		/*
		 * 把cheat之后的分数往前挪动一个位置
		 */
		for(int j=i; j<numEntries-1; j++) {
			board[j] = board[j+1];
		}
		board[numEntries-1] = null; // help GC
		numEntries--; // 更新总记录数量
		return ret;
	}

	@Override
	public String toString() {
		//用char效率高点哟~
		StringBuilder sb = new StringBuilder('[');
		for(int i=0; i<numEntries; i++) {
			if(i > 0) {
				sb.append(", ");
			}
			sb.append(board[i]);
		}
		sb.append(']');
		return sb.toString();
	}

	public static void main(String[] args) {
		/*
		 * 为什么要记录前5名？
		 * 一是为了尊重作者，二是因为我毕业时候是全班第5名
		 */
		ScoreBoard board = new ScoreBoard(5);
		String [] names = {"我", "汤一T", "李Q", "李雪Q", "邱海Q"};
		int [] scores = {574, 577, 584, 591, 579};

		for(int i=0; i<names.length; i++) {
			GameEntry e = new GameEntry(names[i], scores[i]);
			board.add(e);
		}
		System.out.println(board);

		GameEntry entry = board.remove(2);
		System.out.println(entry);
		System.out.println(board);
		entry = board.remove(2);
		System.out.println(entry);
		System.out.println(board);
	}
}
