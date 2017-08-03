package yk;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 获取分类的所有相对链接地址 和名称，保存到map中，返回数据
 *
 */
public class GetMoviesName {
	private String url;
	HashMap<String, String> hrefandname = new HashMap<String, String>();
	
	public GetMoviesName(String url) {
		this.url = url;
	}
	
	public HashMap<String, String> getAllKinds() throws IOException{
		
		Document kinds = Jsoup.connect(url)
							.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.78 Safari/537.36")
							.timeout(10000)
							.get();
		Elements elements = kinds.select("#content .types a");
		for(Element element : elements){
			String kindurl = element.attr("href");
			hrefandname.put(kindurl, element.text());
		}
		return hrefandname;
	}
}
