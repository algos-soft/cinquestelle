package it.algos.base.componente;

import it.algos.base.libreria.Lib;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VerticalLabelTest
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Main");
		frame.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				Window win = e.getWindow();
				win.setVisible(false);
				win.dispose();
				System.exit(0);
			}
		} );

		frame.getContentPane().setLayout( new FlowLayout() );

		ImageIcon icon = Lib.Risorse.getIconaBase("Magic24");

		JLabel l = new JLabel( "Rotated anti-clockwise", icon, SwingConstants.LEFT );
		l.setUI( new VerticalLabelUI(false) );
		l.setBorder( new EtchedBorder() );
		frame.getContentPane().add( l );

		l = new JLabel( "Rotated Clockwise", icon, SwingConstants.LEFT );
//		l.setHorizontalTextPosition( SwingConstants.LEFT );
		l.setUI( new VerticalLabelUI(true) );
		l.setBorder( new EtchedBorder() );
		frame.getContentPane().add( l );

        VerticalLabel v1 = new VerticalLabel(true);
        v1.setText("prova uno");
        frame.getContentPane().add(v1);

        VerticalLabel v2 = new VerticalLabel("Prova di VerticalLabel", icon, SwingConstants.LEFT, true);
        frame.getContentPane().add( v2 );


		frame.getContentPane().add( new JButton( "Button" ) );
		frame.pack();
		frame.show();


	}
}