package com.jel.tech.learn.ch03;

/**
 * 一种小游戏，借此来介绍二维数组
 * @author jelex.xu
 * @date 2017年10月1日
 */
public class TicTacToe {

	public static final int X = 1, O = -1, EMPTY = 0;
	//游戏环境面板
	private int[][] board = new int[3][3];
	//当前轮到谁下子，刚开始时X先下
	private int player;

	public TicTacToe() {
		clearBoard();
	}
	/*
	 * 初始化环境面板
	 */
	private void clearBoard() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				board[i][j] = EMPTY;
			}
		}
		player = X;
	}

	/*
	 * 落子
	 * i/j 为落子的横纵坐标
	 */
	public void putMark(int i, int j) throws IllegalArgumentException{
		if(i<0 || i>2 || j<0 || j>2) {
			throw new IllegalArgumentException("(" + i + ", " + j + ")不是有效位置！");
		}
		if(board[i][j] != EMPTY) {
			throw new IllegalArgumentException("(" + i + ", " + j + ")已有棋子落下！");
		}
		board[i][j] = player; //当前棋手的身份就代表了落子的性质
		player = -player; //切换棋手，让对方落子
	}
	/*
	 * 判断mark这个玩家是否赢了！
	 */
	public boolean isWinner(int mark) {

		return (board[0][0] + board[0][1] + board[0][2] == mark * 3 //第一行
				|| board[1][0] + board[1][1] + board[1][2] == mark * 3
				|| board[2][0] + board[2][1] + board[2][2] == mark * 3

				||board[0][0] + board[1][0] + board[2][0] == mark*3 //第1列
				||board[0][1] + board[1][1] + board[2][1] == mark*3 //第2列
				||board[0][2] + board[1][2] + board[2][2] == mark*3 //第3列

				||board[0][2] + board[1][1] + board[0][2] == mark*3 //正斜线
				||board[0][0] + board[1][1] + board[2][2] == mark*3 //反斜线
				);
	}
	/*
	 * 获取赢家角色
	 */
	public int winner() {
		if(isWinner(X)) {
			return X;
		}
		else if(isWinner(O)) {
			return O;
		}
		else {
			return 0;
		}
	}

	/*
	 * 输出游戏面板内容
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				switch(board[i][j]) {
				case X:
					sb.append('X');
					break;
				case O:
					sb.append('O');
					break;
				case EMPTY:
					sb.append(' ');
					break;
				}
				//列分隔符
				if(j < 2) {
					sb.append('|');
				}
			}
			//行分隔
			if(i<2) {
				sb.append("\n-----\n");
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		TicTacToe game = new TicTacToe();
		 /* X moves: */
		 game.putMark(1,1);
		 game.putMark(2,2);
		 game.putMark(0,1);
		 game.putMark(1,2);
		 game.putMark(2,0);

		 /* O moves: */
		 game.putMark(0,2);
		 game.putMark(0,0);
		 game.putMark(2,1);
		 game.putMark(1,0);

		 System.out.println(game);
		 int winningPlayer = game.winner();
		 String[ ] outcome = {"O wins", "Tie", "X wins"}; // rely on ordering
		 System.out.println(outcome[1 + winningPlayer]);
	}
}
