package ceis.grehu.gui.splash;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;

public class StatusBar extends JPanel // implements Runnable {
{
	private static final long serialVersionUID = 8324444481676605195L;

	// instance variables
	//protected JLabel _msg = new JLabel();//no borrar

	protected JProgressBar _progress = new JProgressBar();

	// constructor
	public StatusBar() {
		_progress.setMinimum(0);
		_progress.setMaximum(100);
		_progress.setMinimumSize(new Dimension(100, 20));
		_progress.setSize(new Dimension(100, 20));
		//_progress.setIndeterminate(true);//potter
		_progress.setStringPainted(true);//potter
		
		//_progress.setFont(new Font("Dialog", Font.BOLD, 12));//anetrior
		_progress.setFont(new Font("Dialog", Font.BOLD, 10));//mia

		/*_msg.setMinimumSize(new Dimension(300, 20));//no borrar
		_msg.setSize(new Dimension(300, 20));//no borrar
		_msg.setFont(new Font("Dialog", Font.PLAIN, 10));//no borrar
		_msg.setForeground(Color.black);*///no borrar

		setLayout(new BorderLayout());
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		//add(_msg, BorderLayout.CENTER);//no borrar
		add(_progress, BorderLayout.SOUTH); // BorderLayout.EAST
	}

	public void showStatus(String s) {// need
		//_msg.setText(s);//no borrar
		_progress.setString(s);//potter
		paintImmediately(getBounds());
	}

	public void showProgress(int percent) {// need
		_progress.setValue(percent);
	}

	public void incProgress(int delataPercent) {// need
		_progress.setValue(_progress.getValue() + delataPercent);
	}
} /* end class StatusBar */
