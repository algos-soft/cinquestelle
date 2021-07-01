/**
 * Title:     LibImageTest
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      5-mar-2007
 */
package it.algos.base.libreria.test;

import com.wildcrest.j2printerworks.ImagePanel;
import com.wildcrest.j2printerworks.J2ComponentPrinter;
import com.wildcrest.j2printerworks.J2Printer;
import it.algos.base.libreria.Lib;
import junit.framework.TestCase;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LibImageTest extends TestCase {

    public void testCreaDaComp() throws Exception {
        /* variabili e costanti locali di lavoro */
        ImagePanel imgPan;
        Component comp;
        Border bordo;
        JDialog dialogo;
        boolean riuscito = false;

//        /* crea un Component qualsiasi */
//        comp = new JLabel("Immagine di prova");
//        ((JLabel)comp).setHorizontalAlignment(SwingConstants.CENTER);
//        bordo = BorderFactory.createLineBorder(Color.blue, 4);
//        ((JComponent)comp).setBorder(bordo);
//        ((JComponent)comp).setOpaque(false);
//        comp.setBackground(Color.YELLOW);
//        comp.setPreferredSize(new Dimension(300, 200));

        /* crea un Component qualsiasi */
        comp = new JTextArea();
        ((JTextArea)comp).setText("Alessandro Valbonesi\nVia Soderini 27\n20146 Milano");
//        ((JLabel)comp).setHorizontalAlignment(SwingConstants.CENTER);
        bordo = BorderFactory.createLineBorder(Color.blue, 4);
        ((JComponent)comp).setBorder(bordo);
        ((JComponent)comp).setOpaque(true);
        comp.setBackground(Color.YELLOW);
        comp.setPreferredSize(new Dimension(300, 200));

        /* crea un pannello con l'immagine del componente */
        imgPan = Lib.Image.creaPannelloStampa(comp);

        /* mette il pannello in un dialogo e lo mostra */
        dialogo = new JDialog();
        dialogo.setModal(true);
        dialogo.add(imgPan);
        dialogo.pack();
        dialogo.setVisible(true);


        J2ComponentPrinter cp = new J2ComponentPrinter();
        cp.setComponent(imgPan);

        J2Printer printer = new J2Printer();
//        printer.getBodyWidth();
        printer.setPageable(cp);
        printer.showPrintPreviewDialog();


        riuscito = true;
        if (!riuscito) {
            fail("fallito");
        }// fine del blocco if

    }

//    //panel used to draw image on
//    public class ImagePanel extends JPanel {
//
//        //image object
//        private Image img;
//
//
//        public ImagePanel(Image image) throws IOException {
//            //save path
//            this.img = image;
//        }
//
//
//        //override paint method of panel
//        public void paint(Graphics g) {
//            //draw the image
//            if (img != null)
//                g.drawImage(img, 0, 0, this);
//        }
//
//
//        public Dimension getPreferredSize() {
//            int w = img.getWidth(null);
//            int h = img.getHeight(null);
//            return new Dimension(w,h);
//        }
//
//
//    }


}
