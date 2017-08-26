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
	private static Map<Integer, ArrayList> mines = new HashMap();
	private static ArrayList<FreeRectangle> rectangles = new ArrayList<>();

	public static void main(String [] args) {
		loadData();
	}

	public static void loadData() {
		Path path = Paths.get("res/caso010");
		try(Scanner reader = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))) {

			String head [] = reader.nextLine().split(" ");
			w = Integer.parseInt(head[0]);
			h = Integer.parseInt(head[1]);
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

//		for(Integer x: minas.keySet()) {
//			ArrayList minasNaLinhaX = minas.get(x);
//			for(Object y: minasNaLinhaX) {
//				System.out.println(x+", "+y);
//			}
//		}
	}


	public static void scanGround() {

		for(int x = 1; x < h; x++) {
			for(int y = 1; y < w; y++) {

			}
		}

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
