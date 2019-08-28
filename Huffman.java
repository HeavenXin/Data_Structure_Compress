package Compress;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;
/**
 * 
 * @author Huffman
 *	此方法准确的定义了一个霍夫曼算法的单词查找树
 */
public class Huffman {
	private static int R = 256;// ASCII字母表

	private static class Node implements Comparable<Node> {
		private char ch;// 表示字符
		private int freq;
		private final Node left, right;

		Node(char ch, int freq, Node left, Node right) {
			this.ch = ch;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

		public int compareTo(Node o) {
			return this.freq - o.freq;
		}
	}

	private static Node buileTrie(int[] freq) {// 构造树的方法
		MinPQ<Node> pq = new MinPQ<Node>();
		for (char c = 0; c < R; c++) {// 遍历字母表
			if (freq[c] > 0) {// 对于存在的
				pq.insert(new Node(c, freq[c], null, null));// 创建结点
			}
		}
		while (pq.size() > 1) {
			Node x = pq.delMin();
			Node y = pq.delMin();
			Node parent = new Node('\0', x.freq + y.freq, x, y);
			pq.insert(parent);// 添加父节点
		}
		return pq.delMin();// 返回根节点
	}

	private static void buildCode(String[] st, Node x, String s) {
		if (x.isLeaf()) {// 如果为树叶结点
			st[x.ch] = s;
			return;
		}
		buildCode(st, x.left, s + '0');
		buildCode(st, x.right, s + '1');
	}

	public static void compress() {// 获取输入
		String s = BinaryStdIn.readString();
		char[] input = s.toCharArray();// 获取输入字符串的char数组
		// 统计频率
		int[] freq = new int[R];
		for (int i = 0; i < input.length; i++) {
			freq[input[i]]++;
		}
		Node root = buileTrie(freq);// 构建根节点
		String[] st = new String[R];// 构造编译表
		buildCode(st, root, "");
		writeTrie(root);//写入
		BinaryStdOut.write(input.length);//写入总长度
		for (int i = 0; i < input.length; i++) {
			String code = st[input[i]];
			for (int j = 0; j < code.length(); j++) {//按照数组简单输出
				if (code.charAt(j) == '1') {
					BinaryStdOut.write(true);
				}else {
					BinaryStdOut.write(false);
				}
			}
		}
		BinaryStdOut.close();
	}

	private static void writeTrie(Node x) {//写入输出流
		if (x.isLeaf()) {
			BinaryStdOut.write(true);
			BinaryStdOut.write(x.ch);// 写入字符串
			return;
		}
		BinaryStdOut.write(false);//不是继续
		writeTrie(x.left);//按照先左后右的顺序
		writeTrie(x.right);
	
	}

}