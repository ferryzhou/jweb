package com.iil.util.web;

import java.io.IOException;

import com.enterprisedt.net.ftp.*;

public class Uploader {
	
	public Uploader(String host, int port, String user, String password) throws IOException{
		try {
			this.host = host;
			this.user = user;
			ftp = new FTPClient(host, port);
			ftp.login(user, password);
			ftp.setConnectMode(FTPConnectMode.ACTIVE);
		}catch(Exception e) {
			throw new IOException(e.getMessage());
		}
	}
	
	public String getHost() {
		return this.host;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public void upload(String path, String filename) throws IOException{
		upload(path, filename, true);
	}
	
	public void upload(String dstdir, String srcdir, String filename) throws IOException{
		upload(dstdir, srcdir, filename, true);
	}
	
	public void upload(String path, String filename, boolean ASCII) throws IOException{
		try {
			System.out.println (filename);
			System.out.println (path + filename);
			if (ASCII) {
				ftp.setType(FTPTransferType.ASCII);
			}else {
				ftp.setType(FTPTransferType.BINARY);
			}
			ftp.put(filename, path + filename);
		}catch(Exception e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
	}
	
	public void upload(String dstdir, String srcdir, String filename, boolean ASCII) throws IOException{
		try {
			//System.out.println (filename);
			//System.out.println (path + filename);
			if (ASCII) {
				ftp.setType(FTPTransferType.ASCII);
			}else {
				ftp.setType(FTPTransferType.BINARY);
			}
			ftp.put(srcdir + "/" + filename, dstdir + "/" + filename);
		}catch(Exception e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
	}
	
	public void close() throws IOException{
		try {
			ftp.quit();
		}catch(Exception e) {
			throw new IOException(e.getMessage());
		}
	}
	
	public static void main(String[] args) throws IOException{
    	Uploader up = new Uploader("www.myjavaserver.com", 21, "ferryzhou", "jk9710");
    	up.upload("rss/", "test.txt");
    	
    	up.close();
    }
    
	private FTPClient ftp;
	private String host;
	private String user;
}