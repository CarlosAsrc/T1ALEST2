import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

	private static int w;
	private static int h;
	private static int m;
	private static int pt1x;
	private static int pt1y;
	private static int pt2x;
	private static int pt2y;
	private int area;

	private static List<Mine> dataMines = new ArrayList<>();
	private static ArrayList<Mine> mines = new ArrayList<>();
	private static ArrayList<FreeRectangle> rectangles = new ArrayList<>();

	public static void main(String [] args) {
		loadData();
		Collections.sort(dataMines);
		defineMines();
		menu();
	}

	public static void menu() {
//		FreeRectangle r = largestFreeRectangle();
		listElements(dataMines);
		System.out.println("\n\n\n-----------------------------\n\n\n");
		listElements(mines);
	}

	public static void listElements(List<Mine> dataMines) {
		for(Mine m: dataMines) {
			System.out.println(m.toString());
		}
	}


	public static void loadData() {
		Path path = Paths.get("res/caso100");
		try(Scanner reader = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))) {

			String head [] = reader.nextLine().split(" ");
			h = Integer.parseInt(head[0]);
			w = Integer.parseInt(head[1]);
			m = Integer.parseInt(head[2]);

			while(reader.hasNextLine()) {
				String pos[] = reader.nextLine().split(" ");
				int x = Integer.parseInt(pos[0]);
				int y = Integer.parseInt(pos[1]);



				dataMines.add(new Mine(x, y));
			}
		}
		catch(IOException e) {
			System.out.println("Arquivo não encontrado!");
		}
	}


	public static void defineMines() {
		for(Mine m: dataMines) {
			int x = m.getX();
			int y = m.getY();
			long emptySpacesLeft;
			long emptySpacesRight;
			if(!mines.isEmpty()) {
				emptySpacesLeft = ( ((x - mines.get((mines.size()-1)).getX()) - 1) * w) + (y-1) + (w - mines.get((mines.size()-1)).getY());
				mines.get(mines.size()-1).setEmptySpacesRight(emptySpacesLeft);
			} else {
				emptySpacesLeft = ((x-1) * w) + (y-1);
			}
			emptySpacesRight = ((h-x) * w) + (w - y);
			mines.add(new Mine(x, y, emptySpacesLeft, emptySpacesRight));
		}
	}

	public static FreeRectangle largestFreeRectangle() {
		int largestArea = 0;
		int area;
		FreeRectangle result = null;
		for(FreeRectangle r: rectangles) {
			area = r.getArea();
			if(area > largestArea) {
				largestArea = area;
				result = r;
			}
		}
		return result;
	}


//	public static String checkNewArea() {
//		if(isMined(pt2x+1, pt2y+1)) {
//			return "intersection";
//		}
//		boolean columnFree = checkNextColumn();
//		boolean lineFree = checkNextLine();
//
//		if(!columnFree && !lineFree) {return "both";}
//		if(!columnFree && lineFree) {return "column";}
//		if(columnFree && !lineFree) {return "line";}
//		return "free";
//	}
//
//	public static boolean checkNextLine() {
//		if(pt2x+1 > h) {
//			return false;
//		}
//		for(int y = pt2y; y >= pt1y; y--) {
//			if(isMined(pt2x+1, y)) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	public static boolean checkNextColumn() {
//		if(pt2y+1 > w) {
//			return false;
//		}
//		for(int x = pt2x; x >= pt1x; x--) {
//			if(isMined(x, pt2y+1)) {
//				return false;
//			}
//		}
//		return true;
//	}



//	public static boolean isMined(int x, int y) {
//		if(mines.containsKey(x)){
//			if(mines.get(x).contains(y)){
//				return true;
//			}
//		}
//		return false;
//	}
}
