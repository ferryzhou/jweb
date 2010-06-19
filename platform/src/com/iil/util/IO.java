package com.iil.util;

import java.io.*;

/**
 * 自定义的io包
 * @author Ferry
 * @version 1.0
 */
public class IO {
	
	/**
     * 从一种编码转换到另一种编码。
	 * @param is
	 * @param inenc
	 * @param outenc
	 * @return
	 * @throws IOException
	 * @see IO
	 */
	public static InputStream inputStreamBridge(InputStream is, String inenc, String outenc) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(is, inenc));
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(buffer, outenc);
		String line;
		while ((line = br.readLine()) != null) {
			osw.write(line);
			//System.out.println (line);
			osw.flush();
		}
		return new ByteArrayInputStream(buffer.toByteArray());
	}
	
	public static void readWriteAll(Reader r, Writer w) throws IOException{
		char[] buffer = new char[1024];
		int num;
		while ((num = r.read(buffer)) != -1) {
			if (num < buffer.length) {
				char[] tmp = new char[num];
				for (int i = 0; i<tmp.length; i++) tmp[i] = buffer[i];
				w.write(tmp);
			}
			else w.write(buffer);
			w.flush();
		}
/*		BufferedReader in = new BufferedReader(r);
		BufferedWriter out = new BufferedWriter(w);
		String line;
		while ((line = in.readLine()) != null) {
			out.write(line);
			out.newLine();
			out.flush();
		}
*/	}
	
	public static void readWriteAll(InputStream in, OutputStream out) throws IOException{
		byte[] buffer = new byte[1024];
		int num;
		while ((num = in.read(buffer)) != -1) {
			if (num < buffer.length) {
				byte[] tmp = new byte[num];
				for (int i = 0; i<tmp.length; i++) tmp[i] = buffer[i];
				out.write(tmp);
			}
			else out.write(buffer);
			out.flush();
		}
	}
	
	
	public static String getContent(String filename) throws IOException{
		return getContent(new FileReader(filename));
/*		BufferedReader br = new BufferedReader(new FileReader(filename));
		String s = getContent(br);
		br.close();
		return s;		
*/	}
	
	public static String getContent(Reader r) throws IOException{
		StringWriter sw = new StringWriter();
		readWriteAll(r, sw);
		return sw.getBuffer().toString();
	}
}