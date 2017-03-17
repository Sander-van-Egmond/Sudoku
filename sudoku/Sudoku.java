package sudoku;
import java.util.ArrayList;

public class Sudoku {
	Field roster = new Field();
	
	public static void main(String[] args){
		Sudoku mySudoku = new Sudoku();
		mySudoku.readInit(args[0]);
		mySudoku.printInitial();
		mySudoku.solver();
		mySudoku.printSolved();
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
	
	
	void solver(){
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
		System.out.print(options.size()+": ");
		for (int x : options){
			System.out.print(x);
		}
		System.out.println();
		if (options.size() == 1){
			current.value=options.get(0);
			return;
		}
		System.out.println(current.options.size());
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
		optionsRow.removeAll(current.options);
		optionsColumn.removeAll(current.options);
		optionsBox.removeAll(current.options);
		
		current.printProperties();
		
		/*
		System.out.print(current.options.size());
		System.out.print("options: ");
		for (int x : current.options){
			System.out.print(x);
		}
		System.out.println();
		
		System.out.print("row: ");
		for (Integer row : optionsRow){
			System.out.print(row);
		}
		System.out.println();
		
		System.out.print("column: ");
		for (Integer row : optionsColumn){
			System.out.print(row);
		}
		System.out.println();
		
		System.out.print("box: ");
		for (Integer row : optionsBox){
			System.out.print(row);
		}
		System.out.println();
		*/
		
		for (int option : current.options){
			
			if (!optionsRow.contains(option)||!optionsColumn.contains(option)||!optionsBox.contains(option)){
				current.value = option;
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
	
	public Number(){
		
	}
	
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