package sudoku;
import java.util.ArrayList;

public class Sudoku {
	Field roster = new Field();
	
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		Sudoku mySudoku = new Sudoku();
		mySudoku.readInit(args[0]);
		mySudoku.printInitial();
		mySudoku.solver();
		mySudoku.printSolved();
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Solved in "+totalTime+" miliseconds");

	}
	
	void readInit(String field){
		for (int i=0 ; i<81 ; i++){
			Number current = new Number();
			current.row=i/9;
			current.column=i%9;
			current.box=i/9/3*3+i%9/3;
			current.value=Character.getNumericValue(field.charAt(i));
			
			if (current.value>0){
				current.options.add(current.value);
			}
			roster.addNumber(current);
			

		}
	}
	
	boolean boardComplete(){
		for (int i=0 ; i<81 ; i++){
			Number current = roster.field.get(i);
			if  (current.value == 0){
				return false;
			}	
		}
		return true;
	}
	
	
	void solver(){
		while (!boardComplete()){
			for (int i=0 ; i<81 ; i++){
				Number current = roster.field.get(i);
				if (current.value == 0){
					solveNext(current);
				}
			}
			for (int i=0 ; i<81 ; i++){
				Number current = roster.field.get(i);
				if (current.value == 0){
					solveDetailed(current);	
				}
			}
		}	
	}
	
	void solveNext(Number current){
		ArrayList<Integer> options = new ArrayList<Integer>();
		for (int i=1 ;i<10 ;i++){
			options.add(i);
		}
		for (int i=0 ; i<81 ; i++){
			Number check = roster.field.get(i);
			if (check.row == current.row || check.column == current.column || check.box == current.box){
				if (options.contains(check.value)){
					options.remove(new Integer(check.value));
				}
			}
		}
		current.options=options;
		
		if (options.size() == 1){
			current.value=options.get(0);
			current.options.clear();
			for (int i=0 ; i<81 ; i++){
				Number check = roster.field.get(i);
				if (check.row == current.row && check.options.contains(current.value)){
					check.options.remove(new Integer(current.value));
				}
				if (check.column == current.column  && check.options.contains(current.value)){
					check.options.remove(new Integer(current.value));
				}
				if (check.box == current.box && check.options.contains(current.value)){
					check.options.remove(new Integer(current.value));
				}
			}
			return;
		}
	}
	
	void solveDetailed(Number current){
		ArrayList<Integer> optionsRow = new ArrayList<Integer>();
		ArrayList<Integer> optionsColumn = new ArrayList<Integer>();
		ArrayList<Integer> optionsBox = new ArrayList<Integer>();
		
		for (int i=0 ; i<81 ; i++){
			Number check = roster.field.get(i);
			if (check.row == current.row){
				optionsRow.addAll(check.options);
			}
			if (check.column == current.column){
				optionsColumn.addAll(check.options);
			}
			if (check.box == current.box){
				optionsBox.addAll(check.options);
			}
		}
		
		for (int x : current.options){
			optionsRow.remove(new Integer(x));
			optionsColumn.remove(new Integer(x));
			optionsBox.remove(new Integer(x));
		}
				
		for (int option : current.options){
			
			if (!optionsRow.contains(option)||!optionsColumn.contains(option)||!optionsBox.contains(option)){
				current.value = option;
				current.options.clear();
				for (int i=0 ; i<81 ; i++){
					Number check = roster.field.get(i);
					if (check.row == current.row && check.options.contains(option)){
						check.options.remove(new Integer(option));
					}
					if (check.column == current.column  && check.options.contains(option)){
						check.options.remove(new Integer(option));
					}
					if (check.box == current.box && check.options.contains(option)){
						check.options.remove(new Integer(option));
					}
				}
				return;
			}
		}
	}
	
	void printInitial(){
		System.out.print("Initial State:");
		printRoster();
	}

	
	void printSolved(){
		System.out.print("Solved:");
		printRoster();
	}
	
	void printRoster(){
		for (int i=0 ; i<81 ; i++){
			Number current = roster.field.get(i);
			if (current.column==0){
				System.out.println();
				System.out.println("-------------------");
				System.out.print("|");
			}
			if (current.value==0){
				System.out.print(" ");
			}
			else{
				System.out.print(current.value);
			}
			System.out.print("|");
		}
		System.out.println();
		System.out.println("-------------------");
	}
}


class Number{
	int row;
	int column;
	int box;
	int value = 0;
	ArrayList<Integer> options =  new ArrayList<Integer>();
	
	void printProperties(){
		System.out.println("row: "+row);
		System.out.println("column: "+column);
		System.out.println("box: "+box);
		System.out.println("value: "+value);
	}
}

class Field{
	ArrayList<Number> field = new ArrayList<Number>(81);
	
	void addNumber(Number number){
		field.add(number);
	}
}