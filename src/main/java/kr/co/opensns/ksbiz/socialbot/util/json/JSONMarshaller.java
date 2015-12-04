package kr.co.opensns.ksbiz.socialbot.util.json;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON Seriazlie를 관장
 * @author jaeho
 *
 */
public class JSONMarshaller {
	public void toJson(Object obj) {
		
	}
	
	public static <T> void writeJson(String path, String filename, Collection<T> collections) throws IOException {
		//Gson gson = new Gson();
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		StringBuffer sBuffer = new StringBuffer();
		for( Iterator<T> iter = collections.iterator(); iter.hasNext(); ) {
			sBuffer.append(gson.toJson(iter.next()));
			sBuffer.append("\n");
		}
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path + "//" + filename), "UTF-8"));
		bufferedWriter.write(sBuffer.toString());
		bufferedWriter.close();
	}
	
	public static void writeJson(String path, String filename, Object object) throws IOException {
		//Gson gson = new Gson();
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		StringBuffer sBuffer = new StringBuffer();
			sBuffer.append(gson.toJson(object));
			sBuffer.append("\n");
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path + "//" + filename), "UTF-8"));
		bufferedWriter.write(sBuffer.toString());
		bufferedWriter.close();
	}
	
	public static void main(String[] args) throws IOException {
		String path = "C://Temp";
		String filename = "test.json";
	}
}
