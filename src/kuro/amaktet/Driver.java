package kuro.amaktet;

public class Driver {
	public static void main(String[] args){
		final String[] args_copy = args.clone();
		javax.swing.SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					new GameLoader( args_copy);}});}
}