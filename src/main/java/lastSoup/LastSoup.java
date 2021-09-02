package lastSoup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LastSoup {

	private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0 ";
	private static final String OUTPUT_FILE_PATH = System.getProperty("user.dir") + "\\output\\";
	private static String filename = "output.txt";

	public static void main(String[] args) throws IOException {
		String urlString = "";
		Scanner scn = new Scanner(System.in);
		char option = '1';
		
		if (args.length < 1 || args[1] == null) {
			System.out.println("Please enter URL:");
			
			// get user input
			urlString = scn.nextLine();
		} else {
			// use first argument from command line
			urlString = args[1];
		}
		
		if (args.length < 2 || args[2] == null) {
			System.out.println("1) albums & artists\n2) songs & artists");
			
			// get user input
			option = scn.next().charAt(0);
		} 
		
		

		final Document doc = Jsoup.connect(urlString).userAgent(USER_AGENT).get();

		scn.close();
		
		switch(option) {
		case '1':
			// get arraylist of songs and artists
			ArrayList<String> albumsAndArtists = getAlbumsAndArtists(doc);
			writeResultsToFile(albumsAndArtists, true);
			break;
		
		case '2':
			ArrayList<String> songsAndArtists = getSongsAndArtists(doc);
			writeResultsToFile(songsAndArtists, false);
			break;
		}
		
	}

	public static boolean checkOutputPath() {
		Path testablePath = Paths.get(System.getProperty("user.dir") + "/output/");
		if ( Files.exists(testablePath) ) {
			return true;
		} else {
			boolean outputDirCreationStatus = new File("output").mkdir();
			if (outputDirCreationStatus == true) {
				System.out.println("output directory successfuly created.");
				return true;
			}
			else {
				System.out.println("Unable to create output directory, perhaps one already exists?");
				return false;
			}
		}
	}
	
	// taken from googleconsumer
	public static void writeResultsToFile(ArrayList<String> results, boolean isAlbumsAndArtists) throws FileNotFoundException, UnsupportedEncodingException {
		
		
		if (!checkOutputPath()) {
			System.out.println("failed to create or find output path");
		}
		
		if (!isAlbumsAndArtists) {
			filename = "songs_and_artists_" + filename;
		}
		
		PrintWriter write = new PrintWriter(OUTPUT_FILE_PATH + filename, "UTF-8");
		for (String s : results)
		{
			write.println(s);
			System.out.println("wrote: " + s);
		}
		write.close();
		System.out.println("\ndone.");
	}

	private static ArrayList<String> getAlbumsAndArtists(Document doc) {
		ArrayList<String> songsAndArtists = new ArrayList<String>();
		
		Elements trs = doc.select("tr");
		for(Element tr : trs) {
			Elements tds = tr.select("td");
			
			String result = "";
			
			if( tds.size() > 4 ) {
				Element sec = tds.get(4);// get name of artist
				result = sec.text();
			}
			
			if(tds.size() > 3) {
				Element sec = tds.get(3); // get album name
				result += " - " + sec.text();
			}
			
			if (result != "")
				songsAndArtists.add(result);
		}
		
		return songsAndArtists;
	}
	
	public static ArrayList<String> getSongsAndArtists(final Document doc) {
		ArrayList<String> songsAndArtists = new ArrayList<String>();
		
		Elements trs = doc.select("tr");
		for(Element tr : trs) {
			Elements tds = tr.select("td");
			
			String result = "";
			
			if(tds.size() > 5) {
				Element sec = tds.get(5);// get name
				result = sec.text();
			}
			
			if(tds.size() > 4) {
				Element sec = tds.get(4);// get song title
				result += " - " + sec.text();
			}
			
			if (result != "")
				songsAndArtists.add(result);
		}
		
		return songsAndArtists;
	}
}
