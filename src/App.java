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

	private int area;

	private static List<Mine> dataMines = new ArrayList<>();
	private static ArrayList<Mine> mines = new ArrayList<>();
	private static ArrayList<FreeRectangle> rectangles = new ArrayList<>();

	public static void main(String [] args) {
		loadData();
		Collections.sort(dataMines);
		defineMines();
		findRectangles();

		menu();
	}

	public static void menu() {
		FreeRectangle r = largestFreeRectangle();
//		listElements(dataMines);
//		System.out.println("\n\n\n-----------------------------\n\n\n");
//		listElements(mines);
		System.out.println(r.toString());
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


	public static void findRectangles() {
		int pt1x, pt1y, pt2x, pt2y;

		for(Mine base: mines) {

			//Retângulo horizontal à esquerda:
			if(base.getEmptySpacesLeft() > 0) {
				pt1x = 1;
				pt1y = 1;
				pt2x = h;
				pt2y = base.getY()-1;
				for(Mine m: mines) {
					if(!m.equals(base) && m.getY() < base.getY()) {
						if(m.getX() < base.getX()) { pt1x = m.getX()+1;}
						else
						if(m.getX() > base.getX()) {pt2x = m.getX()-1;}
						else
						if(m.getX() == base.getX()) {pt1y = m.getY()+1;}
					}
				}
				rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
			}

			//Retângulo horizontal à direita:
			if(base.getEmptySpacesRight() > 0) {
				pt1x = 1;
				pt1y = base.getY()+1;
				pt2x = h;
				pt2y = w;
				for(Mine m: mines) {
					if(!m.equals(base) && m.getY() > base.getY()) {
						if(m.getX() < base.getX()) { pt1x = m.getX()+1;}
						else
						if(m.getX() > base.getX()) {pt2x = m.getX()-1;}
						else
						if(m.getX() == base.getX()) {pt2y = m.getY()+1;}
					}
				}
				rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
			}

			//Retângulo horizontal acima:
			if(base.getX() != 1) {
				pt1x = 1;
				pt1y = 1;
				pt2x = base.getX()-1;
				pt2y = w;
				for(Mine m: mines) {
					if(!m.equals(base) && m.getX() < base.getX()) {
						if(m.getY() < base.getY()) { pt1y = m.getY()+1;}
						else
						if(m.getY() > base.getY()) {pt2y = m.getY()-1;}
						else
						if(m.getX() == base.getX()) {pt1x = m.getX()+1;}
					}
				}
				rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
			}

			//Retângulo horizontal abaixo:
			if(base.getX() != h) {
				pt1x = base.getX()+1;
				pt1y = 1;
				pt2x = h;
				pt2y = w;
				for(Mine m: mines) {
					if(!m.equals(base) && m.getX() > base.getX()) {
						if(m.getY() < base.getY()) { pt1y = m.getY()+1;}
						else
						if(m.getY() > base.getY()) {pt2y = m.getY()-1;}
						else
						if(m.getX() == base.getX()) {pt1x = m.getX()-1;}
					}
				}
				rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
			}
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
}
