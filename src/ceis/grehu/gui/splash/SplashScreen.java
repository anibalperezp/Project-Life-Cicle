package ceis.grehu.gui.splash;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class SplashScreen extends JWindow  
{
	private static final long serialVersionUID = -8473816296923620961L;
	
	protected StatusBar _statusBar = new StatusBar();

//	constructor
	public SplashScreen(String iconName) {
		super();
		ImageIcon splashImage = new ImageIcon(getClass().getResource(iconName)); 
		JLabel splashButton = new JLabel("");//aqui tengo una etiqueta vacia para ponerle icono
		if (splashImage != null) {
			int imgWidth = splashImage.getIconWidth();
			int imgHeight = splashImage.getIconHeight();
			Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(scrSize.width/2 - imgWidth/2,
					scrSize.height/2 - imgHeight/2);
			splashButton.setIcon(splashImage);//aqui le pongo el icono al label
		}

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(splashButton, BorderLayout.CENTER);
		getContentPane().add(_statusBar, BorderLayout.SOUTH);
		pack();
	}

	public StatusBar getStatusBar() {
		return _statusBar; 
	}

	public void setVisible(boolean b) {
		super.setVisible(b);
	}
} /* end class SplashScreen */
