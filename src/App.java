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

	private static List<Mine> mines = new ArrayList<>();
	private static ArrayList<FreeRectangle> rectangles = new ArrayList<>();

	public static void main(String [] args) {
		long tempoInicial = System.currentTimeMillis();
		loadData();
		Collections.sort(mines);
		findRectangles();
		//mines.forEach(m -> {System.out.println(m.toString());});
		menu();
		System.out.println("o metodo executou em " + (System.currentTimeMillis() - tempoInicial));
	}

	public static void menu() {
		FreeRectangle r = largestFreeRectangle();
		System.out.println(r.toString());
	}


	public static void loadData() {
		Path path = Paths.get("res/wallset.txt");
		try(Scanner reader = new Scanner(Files.newBufferedReader(path, Charset.forName("utf-8")))) {

			String head [] = reader.nextLine().split(" ");
			h = Integer.parseInt(head[0]);
			w = Integer.parseInt(head[1]);
			m = Integer.parseInt(head[2]);

			while(reader.hasNextLine()) {
				String pos[] = reader.nextLine().split(" ");
				int x = Integer.parseInt(pos[0]);
				int y = Integer.parseInt(pos[1]);
				mines.add(new Mine(x, y));
			}
		}
		catch(IOException e) {
			System.out.println("Arquivo não encontrado!");
		}
	}

	public static void findRectangles() {
		for(Mine base: mines) {
			horizontalRectangleAbove(base);
			horizontalRectangleBelow(base);
			horizontalRectangleLeft(base);
			horizontalRectangleRight(base);

			verticalRectangleAbove(base);
			verticalRectangleBelow(base);
			verticalRectangleLeft(base);
			verticalRectangleRight(base);
		}
	}

	public static void horizontalRectangleAbove(Mine base) {
		//Retângulo horizontal acima:
		if(base.getX() > 1) {
			pt1x = 1;
			pt1y = 1;
			pt2x = base.getX()-1;
			pt2y = w;
			for(Mine m: mines) {
				if(!m.equals(base) && m.getX() < base.getX()) {
					if(m.getY() < base.getY() && m.getY() >= pt1y && m.getX() >= pt1x) { pt1y = m.getY()+1;}
					else
					if(m.getY() > base.getY() && m.getY() <= pt2y && m.getX() >= pt1x) {pt2y = m.getY()-1;}
					else
					if(m.getY() == base.getY() && m.getX() >= pt1x) {pt1x = m.getX()+1;}
				}
			}
			rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
		}
	}

	public static void horizontalRectangleBelow(Mine base) {
		//Retângulo horizontal abaixo:
		if(base.getX() < h) {
			pt1x = base.getX()+1;
			pt1y = 1;
			pt2x = h;
			pt2y = w;
			for(Mine m: mines) {
				if(!m.equals(base) && m.getX() > base.getX()) {
					if(m.getY() < base.getY() && m.getY() >= pt1y  && m.getX() <= pt2x) { pt1y = m.getY()+1;}
					else
					if(m.getY() > base.getY() && m.getY() <= pt2y && m.getX() <= pt2x) {pt2y = m.getY()-1;}
					else
					if(m.getY() == base.getY() && m.getX() <= pt2x) {pt2x = m.getX()-1;}
				}
			}
			rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
		}
	}

	public static void horizontalRectangleLeft(Mine base) {
		//Retângulo horizontal à esquerda:
		if(base.getY() > 1) {
			pt1x = 1;
			pt1y = 1;
			pt2x = h;
			pt2y = base.getY()-1;
			for(Mine m: mines) {
				if(!m.equals(base) && m.getY() < base.getY()) {
					if(m.getX() < base.getX() && m.getX() >= pt1x && m.getY() >= pt1y) { pt1x = m.getX()+1;}
					else
					if(m.getX() > base.getX() && m.getX() <= pt2x && m.getY() >= pt1y) {pt2x = m.getX()-1;}
					else
					if(m.getX() == base.getX() && m.getY() >= pt1y) {pt1y = m.getY()+1;}
				}
			}
			rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
		}
	}

	public static void horizontalRectangleRight(Mine base) {
		//Retângulo horizontal à direita:
		if(base.getY() < w) {
			pt1x = 1;
			pt1y = base.getY()+1;
			pt2x = h;
			pt2y = w;
			for(Mine m: mines) {
				if(!m.equals(base) && m.getY() > base.getY()) {
					if(m.getX() < base.getX() && m.getX() >= pt1x && m.getY() <= pt2y) { pt1x = m.getX()+1;}
					else
					if(m.getX() > base.getX() && m.getX() <= pt2x && m.getY() <= pt2y) {pt2x = m.getX()-1;}
					else
					if(m.getX() == base.getX() && m.getY() <= pt2y) {pt2y = m.getY()-1;}
				}
			}
			rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
		}
	}

	public static void verticalRectangleAbove(Mine base) {
		//Retângulo vertical acima:
		if(base.getX() > 1) {
			pt1x = 1;
			pt1y = 1;
			pt2x = base.getX()-1;
			pt2y = w;
			for(Mine m: mines) {
				if(!m.equals(base) && m.getX() < base.getX()) {
					if(m.getX() != base.getX()-1 && m.getX() >= pt1x) {pt1x = m.getX()+1;}
					else
					if(m.getY() < base.getY() && m.getY() >= pt1y) {pt1y = m.getY()+1;}
					else
					if(m.getY() > base.getY() && m.getY() <= pt2y) {pt2y = m.getY()-1;}
				}
			}
			rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
		}
	}

	//2584

	public static void verticalRectangleBelow(Mine base) {
		//Retângulo vertical abaixo:
		if(base.getX() < h) {
			pt1x = base.getX()+1;
			pt1y = 1;
			pt2x = h;
			pt2y = w;
			for(Mine m: mines) {
				if(!m.equals(base) && m.getX() > base.getX()) {
					if(m.getX() != base.getX()+1 && m.getX() <= pt2x) {pt2x = m.getX()-1;}
					else
					if(m.getY() < base.getY() && m.getY() >= pt1y) {pt1y = m.getY()+1;}
					else
					if(m.getY() > base.getY() && m.getY() <= pt2y) {pt2y = m.getY()-1;}
				}
			}
			rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
		}
	}

	public static void verticalRectangleLeft(Mine base) {
		//Retângulo vertical à esquerda:
		if(base.getY() > 1) {
			pt1x = 1;
			pt1y = 1;
			pt2x = h;
			pt2y = base.getY()-1;
			for(Mine m: mines) {
				if(!m.equals(base) && m.getY() < base.getY()) {
					if(m.getY() != base.getY()-1 && m.getY() >= pt1y) {pt1y = m.getY()+1;}
					else
					if(m.getX() < base.getX() && m.getX() >= pt1x) {pt1x = m.getX()+1;}
					else
					if(m.getX() > base.getX() && m.getX() <= pt2x) {pt2x = m.getX()-1;}
				}
			}
			rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
		}
	}

	public static void verticalRectangleRight(Mine base) {
		//Retângulo vertical à direita:
		if(base.getY() < w) {
			pt1x = 1;
			pt1y = base.getY()+1;
			pt2x = h;
			pt2y = w;
			for(Mine m: mines) {
				if(!m.equals(base) && m.getY() > base.getY()) {
					if(m.getY() != base.getY()+1 && m.getY() <= pt2y) {pt2y = m.getY()-1;}
					else
					if(m.getX() < base.getX() && m.getX() >= pt1x) {pt1x = m.getX()+1;}
					else
					if(m.getX() > base.getX() && m.getX() <= pt2x) {pt2x = m.getX()-1;}
				}
			}
			rectangles.add(new FreeRectangle(pt1x, pt1y, pt2x, pt2y));
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
