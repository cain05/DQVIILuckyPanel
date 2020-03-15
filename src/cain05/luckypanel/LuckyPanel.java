package cain05.luckypanel;

import java.util.prefs.Preferences;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LuckyPanel
{
	private static final String prefKey = "imageSize";
	/**
	 * Entry point into the application
	 * 
	 * @param args
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
        		new LuckyPanelFrame(getImageSize());
            }
        });
	}
	
	private static ImageSize getImageSize()
	{
		ImageSize 	size 		= ImageSize.medium;
		Preferences prefs 		= getPreferences();
		String 		prefSize 	= prefs.get(prefKey, null);
		
		if (prefSize == null)
		{
			setDefaultImageSize(size);
		}
		
		try
		{
			size = ImageSize.valueOf(prefSize);
		}
		catch (Exception e)
		{
			
		}
		
		return size;
	}
	
	public static void setDefaultImageSize(ImageSize size)
	{
		getPreferences().put(prefKey, size.toString());
	}
	
	private static Preferences getPreferences()
	{
		return Preferences.userRoot();
	}
}
