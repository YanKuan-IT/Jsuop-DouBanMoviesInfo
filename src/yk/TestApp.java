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
 * ���Ƚ����ݱ��浽txt�ļ��У�Ȼ���ļ���Ϊhtml���ͣ�ֱ����������� 
 *
 */
public class TestApp extends Thread{
	
	public static HashMap<String, String> urlandnames = new HashMap<String, String>();
	private static	File f = new File("f://t.txt");
	
	public static void main(String[] args)  {
		FileWriter fw = null;
		try {
			//����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
			fw = new FileWriter(f,true);
			PrintWriter pw = new PrintWriter(fw);
			//��html�Ĵ��뱣�浽txt�ļ���
			pw.print("<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'><html><head><title>�����Ӱ����</title>"
					+ "<style type='text/css'>table{width:80%;border:1px solid blue;border-collapse: collapse;margin: 30px auto;}td{border: 1px solid red;height: 30px;text-align: center;}tr:NTH-CHILD(odd) {background: #e6e6e6;}tr:NTH-CHILD(even) {background: #99ccff;}</style>"
					+ "</head>"
					+ "<body background='gray'>"
					);
			pw.flush();
			pw.close();
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("�������ݵ�t.txt�ļ�ʱ������");
		}
		
		// ���а�ҳ��
		String url = "http://movie.douban.com/chart";
		// ��ȡ���������������Ӻͷ�������
		GetMoviesName getMoviesName = new GetMoviesName(url);
		try {
			// ����������Ӻͷ�������
			urlandnames = getMoviesName.getAllKinds();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("��ȡurlandname���ִ��󣡣�");
		}
		
		//��ȡ���е�key
		Set<String> keySet = urlandnames.keySet();	
		//����keyֵ
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			// ��ȡ��keyֵ
			String next = iterator.next();
			try {
				//����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
				fw = new FileWriter(f,true);
				PrintWriter pw = new PrintWriter(fw);
				//����keyֵ����ȡvalueֵ�������������ݴ浽txt�ļ���
				pw.print("<table align='center'><tr><th>���</th><th>��Ӱ����</th><th>�Ƿ�������߹ۿ�</th><th>����</th><th>����ʱ��</th><th>��������</th></tr>");
				pw.print("<tr background='white'><td colspan='7'>"+urlandnames.get(next)+"</td></tr>\r\n");
				pw.flush();
				pw.close();
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("�������ݵ�t.txt�ļ�ʱ������");
			}
			
			// ����ĳһ���������ӣ���ȡ�ж�Ӧ�ĵ�Ӱ����
			RunnableOfMoviename runnale = new RunnableOfMoviename(next);
			runnale.run();
		}
		
		try {
			//����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
			fw = new FileWriter(f,true);
			PrintWriter pw = new PrintWriter(fw);
			// ����html���뵽txt�ļ�
			pw.print("</table></body></html>");
			pw.flush();
			pw.close();
			fw.close();
			
			//�޸��ļ�����ʹtxt�ļ� ��� html�ļ���ֱ��ʹ���������
			f.renameTo(new File("f://"+System.currentTimeMillis()+".html"));
			
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("�������ݵ�t.txt�ļ�ʱ������");
		}
	}
	
	
	// �ֶ������ȡ��������
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