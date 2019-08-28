package Compress;

import String_Search.threeTrie;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.TST;

/**
 * 
 * @author Heaven
 *	LZW压缩算法的基本实现,调用了已经实现好的输入类jar包
 *	实现的想法是一边查询一边添加
 */
public class LZW {
	private static final int R = 256;//输入字符数
	private static final int L = 4096;//12进制的编码总数为pow(2,12)
	private static final int W = 12;
	private static void compress() {
		String input = BinaryStdIn.readString();//获取输入
		TST<Integer>st = new TST<Integer>();
		for (int i = 0; i < R; i++) {
			st.put(""+(char)i, i);//创建基本的符号表
		}
		int code = R+1;//设置下标
		while (input.length()>0) {
			String s = st.longestPrefixOf(input);//找到最匹配的前缀
			BinaryStdOut.write(st.get(s),W);//打印s的编码
			int t = s.length();
			if (t<input.length()&&code<L) {
				st.put(input.substring(0,t+1), code++);//添加进去
			}
			input = input.substring(t);//去掉已经输出的
		}
		BinaryStdOut.write(R,W);//最后写入停止字符
		BinaryStdOut.close();
	}
	/**
	 * 	展开的方法
	 */
	private static void expand() {
		String[] st = new String[L];
		int i;
		for (i = 0; i < R; i++) {
			st[i] = ""+(char)i;
		}
		st[i++] = "";
		int codeword = BinaryStdIn.readInt(W);//按照位数获取一个字符
		String val = st[codeword];
		while (true) {
			BinaryStdOut.write(val);//写出val
			//开始对字符表进行新增工作
			codeword = BinaryStdIn.readInt(W);
			if (codeword == R) {
				break;
			}
			String s = st[codeword];
			if (i==codeword) {//出现特殊情况了,即前瞻字符不存在
				s = val+val.charAt(0);
			}if (i<L) {//如果小于字符表的上限
				st[i++] = val + s.charAt(0);
			}
			val = s;
		}
		BinaryStdOut.close();
	}
}
