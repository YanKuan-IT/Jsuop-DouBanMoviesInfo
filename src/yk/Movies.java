package yk;

public class Movies {
	private int id;
	private String name;
	private String types;
	private String release_date;
	private double score;
	private String url;
	private String is_playable;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getRelease_date() {
		return release_date;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Movies() {
		super();
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getIs_playable() {
		return is_playable;
	}
	public void setIs_playable(String is_playable) {
		this.is_playable = is_playable;
	}
	@Override
	public String toString() {
		return "<tr><td>"+id+"</td><td><a href='"+url+"'>" + name + "</a></td><td>" + is_playable + "</td><td>" + types + "</td><td>" + release_date + "</td><td>" + score + "</td></tr>";
	}
	
	
	
}
