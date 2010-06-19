/*
 * Created on 2005-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader;

import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import java.text.*;
import java.io.*;

import org.w3c.dom.*;

import com.iil.util.xml.*;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ArticleStore {

	public static ArticleStore getInstance() {
		if (instance == null) instance = new ArticleStore();
		return instance;
	}
	
	private ArticleStore(){
	}
	
	public void connect() throws SQLException, IOException{

		Properties props = new Properties();
        FileInputStream in = new FileInputStream("database.properties");
        props.load(in);
        in.close();
        
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) {
            System.setProperty("jdbc.drivers", drivers);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

		conn = DriverManager.getConnection(url, username, password);
		stmt = conn.createStatement();
		queryNewsSourcePS = conn.prepareStatement(getQueryNewsSourceSql());
		queryArticlePS = conn.prepareStatement(getQueryArticleSql());
		insertArticlePS = conn.prepareStatement(getInsertArticleSql());
		updateArticleCopyNumPS = conn.prepareStatement(getUpdateArticleCopyNumSql());
		updateArticleClusterPS = conn.prepareStatement(getUpdateArticleClusterSql());
		updateArticlePS = conn.prepareStatement(getUpdateArticleSql());
	}
	
	public int getMaxItemId() throws SQLException{
		ResultSet rs = stmt.executeQuery("select max(id) from Article where 1");
		int maxId = -1;
		if (rs.next()) {
			maxId = rs.getInt(1);
			rs.close();
			System.out.println ("max job id: " + maxId);            
		}
		return maxId;        
	}
	
	public synchronized JRSS getJRSS(int id) throws SQLException {
		JRSS jrss = new JRSS();
		
		queryNewsSourcePS.setInt(1, id);
		ResultSet rs = queryNewsSourcePS.executeQuery();
		if (rs.next()) {
			jrss.id = rs.getInt("id");
			jrss.title = rs.getString("title");
			jrss.LatestNewsPubDate = rs.getDate("latestNewsPubDate");
			jrss.language = rs.getString("language");
			jrss.creator = rs.getString("creator");
		} else throw new SQLException("new such news source!");
		rs.close();
		
		queryArticlePS.setInt(1, id);
		ResultSet rs2 = queryArticlePS.executeQuery();
		jrss.items.addAll(getItems(rs2));
		return jrss;
	}
	
	public synchronized List<NewsSource> getAllNewsSources() throws SQLException {
		String sql = "select id, type, uri, title, tags, latestNewsPubDate, creator, language from NewsSource where 1";
		ResultSet rs = stmt.executeQuery(sql);
		return getNewsSourcesFromResultSet(rs);		
	}
	
	public synchronized List<QueryTask> getAllRSSQueryTask() throws SQLException {
		String sql = "select type, title, condition, outfile from QueryTask where 1";
		ResultSet rs = stmt.executeQuery(sql);
		return getRSSQueryFromResultSet(rs);		
	}
	
	public synchronized List<QueryTask> getRSSQueryFromResultSet(ResultSet rs) throws SQLException {
		List<QueryTask> tasks = new ArrayList<QueryTask>(); 
		while (rs.next()) {
			QueryTask task = new QueryTask();
			task.type = rs.getString("type");
			task.title = rs.getString("title");
			task.condition = rs.getString("condition");
			task.outfile = rs.getString("outfile");
			tasks.add(task);
		}
		rs.close();
		return tasks;
	}

	public synchronized List<NewsSource> getNewsSourcesFromResultSet(ResultSet rs) throws SQLException {
		List<NewsSource> sources = new ArrayList<NewsSource>(); 
		while (rs.next()) {
			NewsSource ns = new NewsSource();
			ns.id = rs.getInt("id");
			ns.title = rs.getString("title");
			ns.type = rs.getString("type");
			ns.uri = rs.getString("uri");
			ns.language = rs.getString("language");
			ns.creator = rs.getString("creator");
			sources.add(ns);
		}
		rs.close();
		return sources;
	}
	
	public synchronized List<NewsItem> getAllItems() throws SQLException {
		return getAllItemsWhere("1 order by id");
	}
	
	public synchronized List<NewsItem> getAllItemsWhere(String condition) throws SQLException {
		String sql = queryNewsItemHead + condition;
		ResultSet rs = stmt.executeQuery(sql);
		return getItems(rs);
	}

	public synchronized List<NewsItem> getItems(ResultSet rs) throws SQLException {
		List<NewsItem> items = new ArrayList<NewsItem>();
		while (rs.next()) {
			NewsItem item = new NewsItem();
			item.id = rs.getInt("id");
			item.pid = rs.getInt("pid");
			item.author = rs.getString("author");
			item.content = rs.getString("content");
			item.description = rs.getString("description");
			item.imgSource = rs.getString("imgSource");
			item.newsSourceId = rs.getInt("newsSourceId");
			item.pubDate = new java.util.Date(rs.getTimestamp("pubDate").getTime());
			item.quality = rs.getString("quality");
			item.subject = rs.getString("subject");
			item.url = rs.getString("url");
			item.tags = rs.getString("tags");
			item.copynum = rs.getInt("copynum");
			item.title = rs.getString("title");
			item.cluster = rs.getInt("cluster");
			
			items.add(item);
		}
		rs.close();
		return items;
	}
	
	public synchronized void updateNewsItemCopyNum(NewsItem item) throws SQLException {
		updateArticleCopyNumPS.setInt(1, item.copynum);
		updateArticleCopyNumPS.setInt(2, item.id);
		updateArticleCopyNumPS.execute();
	}

	public synchronized void updateNewsItemCluster(NewsItem item) throws SQLException {
		updateArticleClusterPS.setInt(1, item.cluster);
		updateArticleClusterPS.setInt(2, item.id);
		updateArticleClusterPS.execute();
	}

	//pid=?, copynum=?, tags=?, quality=?, cluster=? where id=?
	public synchronized void updateNewsItem(NewsItem item) throws SQLException {
		//System.out.println("" + item.id + " " + item.pid + " " + item.cluster);
		updateArticlePS.setInt(1, item.pid);
		updateArticlePS.setInt(2, item.copynum);
		updateArticlePS.setString(3, item.tags);
		updateArticlePS.setString(4, item.quality);
		updateArticlePS.setInt(5, item.cluster);
		updateArticlePS.setInt(6, item.id);
		updateArticlePS.execute();
	}

	//id, pid, title, imgSource, author, pubDate, url, subject, description, content, copynum, tags, newsSourceId, quality
	public synchronized void insertNewsItem(NewsItem item) throws SQLException {
		insertArticlePS.setInt(1, item.id);
		insertArticlePS.setInt(2, item.pid);
		insertArticlePS.setString(3, item.title);
		insertArticlePS.setString(4, item.imgSource);
		insertArticlePS.setString(5, item.author);
		insertArticlePS.setTimestamp(6, new Timestamp(item.pubDate.getTime()));
		insertArticlePS.setString(7, item.url);
		insertArticlePS.setString(8, item.subject);
		insertArticlePS.setString(9, item.description);
		insertArticlePS.setString(10, item.content);
		insertArticlePS.setInt(11, item.copynum);
		insertArticlePS.setString(12, item.tags);
		insertArticlePS.setInt(13, item.newsSourceId);
		insertArticlePS.setString(14, item.quality);
		insertArticlePS.setInt(15, item.cluster);
		insertArticlePS.execute();
	}
	
	
	private int getId() {
		return id++;
	}

	private String getString(Element article, String nodename) {
		return DOMInfoExtractor.extractString(article, nodename);
	}
	
	private Timestamp getTimestamp(Element article, String datenodename) {
		String datestr = getString(article, datenodename);
		if (datestr == null || datestr.trim().length() == 0) return null;
		Locale locale = Locale.ENGLISH;
		String fs;
		fs = "E, dd MMM yyyy HH:mm:ss Z";
    	SimpleDateFormat format = new SimpleDateFormat(fs, locale);
    	try {
	    	java.util.Date date = (java.util.Date)format.parseObject(datestr);
	    	return new Timestamp(date.getTime());
    	}catch(ParseException e) {
    		e.printStackTrace();
    		return null;
    	}
	}
	
	public synchronized void close() throws SQLException{
		stmt.close();
		conn.close();
		System.out.println("store closed......");
	}

	private String getInsertArticleSql() {
		return "insert into article(id, pid, title, imgSource, author, pubDate, "
			+ "url, subject, description, content, copynum, tags, "
			+ "newsSourceId, quality, cluster) "
			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}
	
	private String getUpdateArticleSql() {
		return "update article set pid=?, copynum=?, tags=?, quality=?, cluster=? where id=?";
	}
	
	private String getUpdateArticleCopyNumSql() {
		return "update article set copynum=? where id = ?";
	}

	private String getUpdateArticleClusterSql() {
		return "update article set cluster=? where id = ?";
	}

	private String getQueryNewsSourceSql() {
		return "select id, type, uri, title, tags, latestNewsPubDate, creator, language from NewsSource where id = ?";
	}
	
	private String getQueryArticleSql() {
		return queryNewsItemHead + "newsSourceId = ?";
	}
	
	private static String queryNewsItemHead = "select id, pid, title, imgSource, author, pubDate, url, subject, description, content, copynum, tags, newsSourceId, quality, cluster "
		+ "from article where ";
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement insertArticlePS;
	private PreparedStatement queryNewsSourcePS;
	private PreparedStatement queryArticlePS;
	private PreparedStatement updateArticleCopyNumPS;
	private PreparedStatement updateArticleClusterPS;
	private PreparedStatement updateArticlePS;
	
	private int id;
	
	private static ArticleStore instance;
	
	public static void main(String[] args) throws Exception{
		ArticleStore store = ArticleStore.getInstance();
		store.connect();
		
		JRSS jrss = store.getJRSS(0);
		System.out.println(jrss);
		
		store.close();
	}
}
