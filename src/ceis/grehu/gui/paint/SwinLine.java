package ceis.grehu.gui.paint;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class SwinLine {
	private ArrayList<String> arrayNameSwingLine;
	
	private ArrayList<Integer> arrayWidths;
	
	private int id = -1;

	public SwinLine(){
		arrayNameSwingLine = new ArrayList<String>();
		arrayWidths = new ArrayList<Integer>();
		Random random = new Random();
		id = random.hashCode();
	}
	
	public ArrayList<String> getArrayNameSwingLine() {
		return arrayNameSwingLine;
	}

	public void setArrayNameSwingLine(ArrayList<String> arrayNameSwingLine) {
		this.arrayNameSwingLine = arrayNameSwingLine;
	}

	public ArrayList<Integer> getArrayWidths() {
		return arrayWidths;
	}

	public void setArrayWidths(ArrayList<Integer> arrayWidths) {
		this.arrayWidths = arrayWidths;
	}
	
	public int getSumOfWidths(int pos){
		int sum = 0;
		for (int i = 0; i <= pos; i++) {
			sum += getArrayWidths().get(i);
		}
		return sum;
	}
	
	public int posOfSwinLineSelected(Point pointInicial){
		int pos = -1;
		for (int i = 0; i < getArrayWidths().size(); i++) {
			int sum = getSumOfWidths(i);
			if(pointInicial.x == sum)
				pos = i;
		}
		return pos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
