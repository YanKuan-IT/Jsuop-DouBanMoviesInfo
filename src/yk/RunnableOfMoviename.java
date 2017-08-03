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
			//获取该类别影片的数量total、可在线观看数量playable_count
			document = Jsoup.connect(finalurl).timeout(10000).ignoreContentType(true).execute().body();	
			// document------{"playable_count":18,"total":32,"unwatched_count":32}可在线观看18部，共32部，未观看32部
		} catch (IOException e) {
			e.printStackTrace();
		}

		//json解析器
		JsonParser parser = new JsonParser();
		//获取json对象
		JsonObject jsonObject = (JsonObject) parser.parse(document);
		//将json数据转为int型数据
		int movienum = jsonObject.get("total").getAsInt();
		System.out.println(movienum);
		nameurl = "http://movie.douban.com/j/chart/top_list?"+tempurl[1]+"&"+tempurl[2]+"&action=&start=0&limit="+movienum;
		// nameurl-------------http://movie.douban.com/j/chart/top_list?type=18&interval_id=100:90&action=&start=0&limit=32
		FileWriter fw = null;
		try {
			//如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f = new File("f://t.txt");
			fw = new FileWriter(f,true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print("<tr background='white'><td colspan='7'>数量："+movienum+"</td></tr>\r\n");
			pw.flush();
			pw.close();
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("保存数据到t.txt文件时，出错！");
		}
		String doc = null;
		try {
			//获取该类别的所有影片的信息
			doc = Jsoup.connect(nameurl).timeout(10000).ignoreContentType(true).execute().body();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//将json的一个对象数组解析成JsonElement对象
		JsonElement element = null;
		try {
			//通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
			element = parser.parse(doc);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		JsonArray jsonArray = null;
		if(element.isJsonArray()){
			//JsonElement对象如果是一个数组的话转化成jsonArray
			jsonArray = element.getAsJsonArray();
		}
		
		int id=0;
		//遍历json的对象数组
		Iterator it = jsonArray.iterator();
		while (it.hasNext()) {
			JsonObject e = (JsonObject)it.next();
			movie = new Movies();
			//电影名称
			String name = e.get("title").getAsString();
			//豆瓣评分
			double score = e.get("score").getAsDouble();
			//发布时间
			String release_date = e.get("release_date").getAsString();
			//类型
			JsonArray jsonArray2 = e.get("types").getAsJsonArray();
			String types = jsonArray2.toString();
			//链接地址
			String movieUrl = e.get("url").getAsString();
			//是否可以在线播放
			String is_playable = e.get("is_playable").getAsString();
			//序号
			id++;
			
			//将数据存在movie中
			movie.setId(id);
			movie.setName(name);
			movie.setTypes(types);
			movie.setRelease_date(release_date);
			movie.setScore(score);
			movie.setUrl(movieUrl);
			if(is_playable.equals("false")){
				is_playable = "";
			}else {
				is_playable = "是";
			}
			movie.setIs_playable(is_playable);
			
			//在控制板逐条输出数据
			System.out.println(movie);
			
			//如果文件存在，则追加内容；如果文件不存在，则创建文件
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
				System.out.println("保存数据到t.txt文件时，出错！");
			}
		}
	}
}
