public class Mine implements Comparable<Mine>{

    private int x;
    private int y;

    public Mine(int x, int y) {
        this.x = x;
        this.y = y;
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
	}

	@Override
	public String toString() {
		return "Mine [x=" + x + ", y=" + y + "]";
	}







}
