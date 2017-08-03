package yk;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
/**
 * 首先将数据保存到txt文件中，然后将文件改为html类型，直接用浏览器打开 
 *
 */
public class TestApp extends Thread{
	
	public static HashMap<String, String> urlandnames = new HashMap<String, String>();
	private static	File f = new File("f://t.txt");
	
	public static void main(String[] args)  {
		FileWriter fw = null;
		try {
			//如果文件存在，则追加内容；如果文件不存在，则创建文件
			fw = new FileWriter(f,true);
			PrintWriter pw = new PrintWriter(fw);
			//将html的代码保存到txt文件中
			pw.print("<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'><html><head><title>豆瓣电影整理</title>"
					+ "<style type='text/css'>table{width:80%;border:1px solid blue;border-collapse: collapse;margin: 30px auto;}td{border: 1px solid red;height: 30px;text-align: center;}tr:NTH-CHILD(odd) {background: #e6e6e6;}tr:NTH-CHILD(even) {background: #99ccff;}</style>"
					+ "</head>"
					+ "<body background='gray'>"
					);
			pw.flush();
			pw.close();
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("保存数据到t.txt文件时，出错！");
		}
		
		// 排行榜页面
		String url = "http://movie.douban.com/chart";
		// 获取分类的所有相对链接和分类名称
		GetMoviesName getMoviesName = new GetMoviesName(url);
		try {
			// 所有相对链接和分类名称
			urlandnames = getMoviesName.getAllKinds();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("获取urlandname出现错误！！");
		}
		
		//获取所有的key
		Set<String> keySet = urlandnames.keySet();	
		//迭代key值
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			// 获取到key值
			String next = iterator.next();
			try {
				//如果文件存在，则追加内容；如果文件不存在，则创建文件
				fw = new FileWriter(f,true);
				PrintWriter pw = new PrintWriter(fw);
				//根据key值，获取value值，并将该条数据存到txt文件中
				pw.print("<table align='center'><tr><th>序号</th><th>电影名称</th><th>是否可以在线观看</th><th>类型</th><th>发行时间</th><th>豆瓣评分</th></tr>");
				pw.print("<tr background='white'><td colspan='7'>"+urlandnames.get(next)+"</td></tr>\r\n");
				pw.flush();
				pw.close();
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("保存数据到t.txt文件时，出错！");
			}
			
			// 根据某一个类别的链接，获取行对应的电影数据
			RunnableOfMoviename runnale = new RunnableOfMoviename(next);
			runnale.run();
		}
		
		try {
			//如果文件存在，则追加内容；如果文件不存在，则创建文件
			fw = new FileWriter(f,true);
			PrintWriter pw = new PrintWriter(fw);
			// 保存html代码到txt文件
			pw.print("</table></body></html>");
			pw.flush();
			pw.close();
			fw.close();
			
			//修改文件名，使txt文件 变成 html文件，直接使用浏览器打开
			f.renameTo(new File("f://"+System.currentTimeMillis()+".html"));
			
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("保存数据到t.txt文件时，出错！");
		}
	}
	
	
	// 手动计算获取的总条数
	@Test
	public void testAdd(){
		int[] arr = {32,87,74,23,69,108,245,47,117,154,408,148,165,251,31,87,17,16,309,531,124,35,222,13,8,44,65,155,69};
		int sum=0;
		for(int i=0,length=arr.length; i<length; i++){
			sum += arr[i];
		}
		System.out.println(sum);//3654
	}
	
	
	
	
	
	
	
	
	
	
	
	
}