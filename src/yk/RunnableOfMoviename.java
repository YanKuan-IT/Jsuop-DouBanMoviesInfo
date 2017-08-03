package yk;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RunnableOfMoviename {
	Movies movie;
	private String url = null;
	private String nameurl = null;
	
	public RunnableOfMoviename(String url) {
		this.url = url;
	}
	
	public void run(){
		
		String[] tempurl = url.split("&");
		String finalurl = "http://movie.douban.com/j/chart/top_list_count?"+tempurl[1]+"&"+tempurl[2];
		// finalurl ---------http://movie.douban.com/j/chart/top_list_count?type=18&interval_id=100:90
		String document = null;
		try {
			//��ȡ�����ӰƬ������total�������߹ۿ�����playable_count
			document = Jsoup.connect(finalurl).timeout(10000).ignoreContentType(true).execute().body();	
			// document------{"playable_count":18,"total":32,"unwatched_count":32}�����߹ۿ�18������32����δ�ۿ�32��
		} catch (IOException e) {
			e.printStackTrace();
		}

		//json������
		JsonParser parser = new JsonParser();
		//��ȡjson����
		JsonObject jsonObject = (JsonObject) parser.parse(document);
		//��json����תΪint������
		int movienum = jsonObject.get("total").getAsInt();
		System.out.println(movienum);
		nameurl = "http://movie.douban.com/j/chart/top_list?"+tempurl[1]+"&"+tempurl[2]+"&action=&start=0&limit="+movienum;
		// nameurl-------------http://movie.douban.com/j/chart/top_list?type=18&interval_id=100:90&action=&start=0&limit=32
		FileWriter fw = null;
		try {
			//����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
			File f = new File("f://t.txt");
			fw = new FileWriter(f,true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print("<tr background='white'><td colspan='7'>������"+movienum+"</td></tr>\r\n");
			pw.flush();
			pw.close();
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("�������ݵ�t.txt�ļ�ʱ������");
		}
		String doc = null;
		try {
			//��ȡ����������ӰƬ����Ϣ
			doc = Jsoup.connect(nameurl).timeout(10000).ignoreContentType(true).execute().body();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//��json��һ���������������JsonElement����
		JsonElement element = null;
		try {
			//ͨ��JsonParser������԰�json��ʽ���ַ���������һ��JsonElement����
			element = parser.parse(doc);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		JsonArray jsonArray = null;
		if(element.isJsonArray()){
			//JsonElement���������һ������Ļ�ת����jsonArray
			jsonArray = element.getAsJsonArray();
		}
		
		int id=0;
		//����json�Ķ�������
		Iterator it = jsonArray.iterator();
		while (it.hasNext()) {
			JsonObject e = (JsonObject)it.next();
			movie = new Movies();
			//��Ӱ����
			String name = e.get("title").getAsString();
			//��������
			double score = e.get("score").getAsDouble();
			//����ʱ��
			String release_date = e.get("release_date").getAsString();
			//����
			JsonArray jsonArray2 = e.get("types").getAsJsonArray();
			String types = jsonArray2.toString();
			//���ӵ�ַ
			String movieUrl = e.get("url").getAsString();
			//�Ƿ�������߲���
			String is_playable = e.get("is_playable").getAsString();
			//���
			id++;
			
			//�����ݴ���movie��
			movie.setId(id);
			movie.setName(name);
			movie.setTypes(types);
			movie.setRelease_date(release_date);
			movie.setScore(score);
			movie.setUrl(movieUrl);
			if(is_playable.equals("false")){
				is_playable = "";
			}else {
				is_playable = "��";
			}
			movie.setIs_playable(is_playable);
			
			//�ڿ��ư������������
			System.out.println(movie);
			
			//����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
			try {
				File f = new File("f://t.txt");
				fw = new FileWriter(f,true);
				PrintWriter pw = new PrintWriter(fw);
				pw.print(movie.toString()+"\r\n");
				pw.flush();
				pw.close();
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("�������ݵ�t.txt�ļ�ʱ������");
			}
		}
	}
}
