public class Mine implements Comparable<Mine>{

    private int x;
    private int y;
	private long emptySpacesLeft;
    private long emptySpacesRight;


    public Mine(int x, int y, long emptySpacesLeft, long emptySpacesRight) {
        this.x = x;
        this.y = y;
        this.emptySpacesLeft = emptySpacesLeft;
        this.emptySpacesRight = emptySpacesRight;
    }

    public Mine(int x, int y) {
        this.x = x;
        this.y = y;
    }

	public long getEmptySpacesLeft() {
		return emptySpacesLeft;
	}

	public void setEmptySpacesLeft(long emptySpacesLeft) {
		this.emptySpacesLeft = emptySpacesLeft;
	}

	public long getEmptySpacesRight() {
		return emptySpacesRight;
	}

	public void setEmptySpacesRight(long emptySpacesRight) {
		this.emptySpacesRight = emptySpacesRight;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int compareTo(Mine another) {

		int resultado = this.getX()- another.getX();
	    if (resultado == 0) {
	        resultado = this.getY() - another.getY();
	    }

	    return resultado;
//		if(another.getX() != getX()) {
//			return Integer.compare(another.getX(), getX());
//		}
//		return Integer.compare(another.getY(), getY());
	}

	@Override
	public String toString() {
		return "Mine [x=" + x + ", y=" + y + ", emptySpacesLeft=" + emptySpacesLeft + ", emptySpacesRight="
				+ emptySpacesRight + "]";
	}






}
