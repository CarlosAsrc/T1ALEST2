import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
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
	private static Map<Integer, ArrayList> mines = new HashMap();
	private static ArrayList<FreeRectangle> rectangles = new ArrayList<>();

	public static void main(String [] args) {
		loadData();
		scanGround();
		menu();
	}

	public static void menu() {
		FreeRectangle r = largestFreeRectangle();
		System.out.println(r.getArea());
		System.out.println(r.getPt1x() + ", "+r.getPt1y()+" | "+r.getPt2x()+", "+r.getPt2y());
	}

	public static void loadData() {
		Path path = Paths.get("res/teste.txt");
		try(Scanner reader = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))) {

			String head [] = reader.nextLine().split(" ");
			h = Integer.parseInt(head[0]);
			w = Integer.parseInt(head[1]);
			m = Integer.parseInt(head[2]);

			while(reader.hasNextLine()) {
				String pos[] = reader.nextLine().split(" ");
				int x = Integer.parseInt(pos[0]);
				int y = Integer.parseInt(pos[1]);


				if(!mines.containsKey(x)) {
					ArrayList<Integer> minesInLineX = new ArrayList();
					minesInLineX.add(y);
					mines.put(x, minesInLineX);
				} else {
					mines.get(x).add(y);
				}
			}
		}
		catch(IOException e) {
			System.out.println("Arquivo não encontrado!");
		}
	}

	public static void scanGround() {
		String newArea;
		for(pt1x = 1; pt1x <= h; pt1x++) {
			for(pt1y = 1; pt1y <= w; pt1y++) {
				if(!isMined(pt1x, pt1y)) {
					pt2x = pt1x;
					pt2y = pt1y;
					while((newArea = checkNewArea()).equals("free")) {
						pt2x++;
						pt2y++;
					}
					switch (newArea) {
						case "both":
							rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
							break;
						case "line":
							while(checkNextColumn()) {
								pt2y++;
							}
							rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
							break;
						case "column":
							while(checkNextLine()) {
								pt2x++;
							}
							rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
							break;

						case "intersection":
							int aux = pt2y;
							while(checkNextColumn()) {
								pt2y++;
							}
							rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));

							pt2y = aux;

							aux = pt2x;
							while(checkNextLine()) {
								pt2x++;
							}
							rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
							break;
					}
				}
			}
		}
	}

	public static String checkNewArea() {
		if(isMined(pt2x+1, pt2y+1)) {
			return "intersection";
		}
		boolean columnFree = checkNextColumn();
		boolean lineFree = checkNextLine();

		if(!columnFree && !lineFree) {return "both";}
		if(!columnFree && lineFree) {return "column";}
		if(columnFree && !lineFree) {return "line";}
		return "free";
	}

	public static boolean checkNextLine() {
		if(pt2x+1 > h) {
			return false;
		}
		for(int y = pt2y; y >= pt1y; y--) {
			if(isMined(pt2x+1, y)) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkNextColumn() {
		if(pt2y+1 > w) {
			return false;
		}
		for(int x = pt2x; x >= pt1x; x--) {
			if(isMined(x, pt2y+1)) {
				return false;
			}
		}
		return true;
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

	public static boolean isMined(int x, int y) {
		if(mines.containsKey(x)){
			if(mines.get(x).contains(y)){
				return true;
			}
		}
		return false;
	}
}
