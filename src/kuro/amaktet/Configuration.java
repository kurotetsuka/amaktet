package kuro.amaktet;

// standard library imports
import java.awt.Font;
import java.io.File;
import java.util.Enumeration;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

// ini4j imports
import org.ini4j.Ini;

public class Configuration{
	private File file;
	private Ini ini;
	private Ini.Section meta_section;
	private Ini.Section gui_section;

	// gui config
	private String lookandfeel;
	
	public Configuration(){
		ini = null;
		gui_section = null;
		meta_section = null;
		file = null;

		// initialize other variables
		lookandfeel = null;}

	// open file
	public void open( File file)
			throws java.io.IOException{
		this.file = file;
		Ini ini = new Ini();
		ini.load(file);

		meta_section = ini.get( "Meta");
		// check if this configuration file is enabled or not
		if( meta_section != null){
			String data = meta_section.get( "enabled");
			boolean enabled = Boolean.parseBoolean( data);
			if( enabled){
				// get file sections
				gui_section = ini.get( "Gui");}}}

	// load methods
	public void load(){
		// load config from gui section
		String lookandfeel = gui_section.get("lookandfeel");
		String lookandfeel_custom = gui_section.get("lookandfeel_custom");
		if( lookandfeel != null)
			switch( lookandfeel){
				case "custom":{
					if( lookandfeel_custom != null)
						this.lookandfeel = lookandfeel_custom;
					else
						this.lookandfeel = null;
					break;}
				case "default":
				default:{
					this.lookandfeel = null;
					break;}}}

	public void applyGuiSettings(){
		javax.swing.JFrame.setDefaultLookAndFeelDecorated( true);
		javax.swing.JDialog.setDefaultLookAndFeelDecorated( true);
		try{
			UIManager.setLookAndFeel(
				this.lookandfeel != null ? this.lookandfeel :
					UIManager.getSystemLookAndFeelClassName());}
		catch( Exception exception){
			exception.printStackTrace();}}

	public void multiplyGuiFontSize( double multiplier){
		UIDefaults defaults = UIManager.getDefaults();
		Enumeration keys = defaults.keys();
		// for all keys in ui defaults
		while( keys.hasMoreElements()){
			Object key = keys.nextElement();
			Object value = defaults.get( key);
			// select those values that represent fonts
			if (value instanceof Font) {
				// multiply their sice
				Font font = (Font) value;
				int newSize = (int) Math.round(
					font.getSize() * multiplier);
				// select the appropriate class constructor
				value = value instanceof FontUIResource ?
					new FontUIResource(
						font.getName(), font.getStyle(), newSize) :
					new Font(
						font.getName(), font.getStyle(), newSize);
				// write the key
				defaults.put( key, value);}}}
}
