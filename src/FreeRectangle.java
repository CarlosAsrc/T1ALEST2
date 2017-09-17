
public class FreeRectangle {
	private int pt1x;
	private int pt1y;
	private int pt2x;
	private int pt2y;
	private int area;

	public FreeRectangle(int pt1x, int pt1y, int pt2x, int pt2y) {
		this.pt1x = pt1x;
		this.pt1y = pt1y;
		this.pt2x = pt2x;
		this.pt2y = pt2y;
		calculateArea();
	}

	public void calculateArea() {
		this.area = (pt2x-pt1x+1) * (pt2y-pt1y+1);
	}

	public int getPt1x() {
		return pt1x;
	}

	public void setPt1x(int pt1x) {
		this.pt1x = pt1x;
	}

	public int getPt1y() {
		return pt1y;
	}

	public void setPt1y(int pt1y) {
		this.pt1y = pt1y;
	}

	public int getPt2x() {
		return pt2x;
	}

	public void setPt2x(int pt2x) {
		this.pt2x = pt2x;
	}

	public int getPt2y() {
		return pt2y;
	}

	public void setPt2y(int pt2y) {
		this.pt2y = pt2y;
	}

	public int getArea() {
		return area;
	}

	@Override
	public String toString() {
		return "Posição do retângulo:\npt1x = " + pt1x + "	pt1y = " + pt1y + "\npt2x = " + pt2x + "	pt2y = " + pt2y + "\nÁrea=" + area;
	}
}
