package kuro.amaktet;

// standard library imports
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

// local imports
import kuro.amaktet.gui.*;

public class Gui extends JFrame {
	// Public variables
	public CustomCardLayout cards;
	public Container frame;
	public JPanel panel;
	public JLayeredPane layers;
	// Panels
	public GamePanel gamePanel;
	public TestPanel testPanel;

	// Private variables
	private boolean fullscreen_enabled;

	// Constructors
	public Gui(){
		fullscreen_enabled = false;
		cards = null;
		frame = null;
		panel = null;}

	public void load(){
		// setup
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		this.setTitle( "Game Window");
		//this.setUndecorated( true);
		//this.setExtendedState( JFrame.MAXIMIZED_BOTH);
		
		// get frame
		frame = getContentPane();

		// main panel
		cards =  new CustomCardLayout();
		panel = new JPanel( cards);

		// create panels
		gamePanel = new GamePanel();
		testPanel = new TestPanel();

		// add panels
		panel.add( gamePanel, gamePanel.NAME);
		panel.add( testPanel, testPanel.NAME);

		// add main panel
		frame.add( panel);

		// done, show everything
		// testPanel.switchTo();
		gamePanel.switchTo();
		this.pack();
		this.setLocationRelativeTo( null);}

	// Methods
	public void close(){
		// hide window
		this.setVisible( false);
		// post closing event
		WindowEvent wev = 
			new WindowEvent( this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().
			getSystemEventQueue().postEvent( wev);}

	public void setFullscreen( boolean fullscreen){
		if( fullscreen_enabled == fullscreen) return;
		fullscreen_enabled = fullscreen;
		if( fullscreen){
			// this.setUndecorated( true);
			this.setExtendedState( JFrame.MAXIMIZED_BOTH);}
		else{
			// this.setUndecorated( false);
			this.setExtendedState( JFrame.NORMAL);}}
	public boolean getFullscreen(){
		return fullscreen_enabled;}

	// Subclasses
	protected class CustomCardLayout extends CardLayout {
		@Override
		public Dimension preferredLayoutSize(Container parent) {
			Component current = findCurrentComponent(parent);
			if (current != null) {
				Insets insets = parent.getInsets();
				Dimension pref = current.getPreferredSize();
				pref.width += insets.left + insets.right;
				pref.height += insets.top + insets.bottom;
				return pref;}
			else return super.preferredLayoutSize(parent);}

		public Component findCurrentComponent(Container parent) {
			for (Component comp : parent.getComponents())
				if (comp.isVisible())
					return comp;
			return null;}
	}

	protected class CustomJPanel extends JPanel {
		public String NAME;
		protected CustomJPanel( String NAME){
			this.NAME = NAME;}
		public void switchTo(){
			cards.show( panel, NAME);}
	}

	// Panel subclasses
	protected class GamePanel extends CustomJPanel{
		// public variables
		public Canvas canvas;
		// constructor
		public GamePanel(){
			super( "GamePanel");
			// set up layout
			GridBagLayout layout = new GridBagLayout();
			this.setLayout( layout);
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.weightx = 0;
			constraints.weighty = 0;
			constraints.fill = GridBagConstraints.NONE;
			constraints.insets = new Insets( 0, 0, 0, 0);

			// add canvas
			canvas = new Canvas();
			canvas.setMinimumSize(
				new Dimension( 800, 600));
			canvas.setPreferredSize(
				new Dimension( 1000, 600));
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.weightx = 1;
			constraints.weighty = 1;
			constraints.anchor = GridBagConstraints.CENTER;
			this.add( canvas, constraints);

			/*/ set cursor
			// Transparent 16 x 16 pixel cursor image
			BufferedImage cursorImg = new BufferedImage(
				16, 16, BufferedImage.TYPE_INT_ARGB);
			Cursor blankCursor =
				Toolkit.getDefaultToolkit().createCustomCursor(
					cursorImg, new Point(0, 0), "blank cursor");
			// set the blank cursor to the panel
			this.setCursor( blankCursor);
			this.setCursor( new Cursor( Cursor.DEFAULT_CURSOR));*/
		}
	}
	protected class TestPanel extends CustomJPanel{
		public JButton button;
		// constructor
		public TestPanel(){
			super("Test");
			button = new JButton("Test Button");
			button.setActionCommand( "gui.test.button");
			this.add( button);}
	}
}