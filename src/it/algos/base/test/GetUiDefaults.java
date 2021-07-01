/**
 * Title:     GetUiDefaults
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-giu-2006
 */
package it.algos.base.test;


import javax.swing.*;
import java.util.Enumeration;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 20-giu-2006 ore 13.45.24
 */
public final class GetUiDefaults extends Object {

    public static void main(String[] args) {
//      System.out.println("Default L&F:");
//      System.out.println("  " + UIManager.getLookAndFeel().getName());
//
//      UIManager.LookAndFeelInfo[] inst = UIManager.getInstalledLookAndFeels();
//      System.out.println("Installed L&Fs: ");
//      for (int i=0;i<inst.length;i++) {
//        System.out.println("  " + inst[i].getName());
//      }
//
//      LookAndFeel[] aux = UIManager.getAuxiliaryLookAndFeels();
//      System.out.println("Auxiliary L&Fs: ");
//      if (aux != null) {
//        for (int i=0;i<aux.length;i++) {
//          System.out.println("  " + aux[i].getName());
//        }
//      }
//      else {System.out.println("  <NONE>");}
//
//      System.out.println("Cross-Platform:");
//      System.out.println("  " + UIManager.getCrossPlatformLookAndFeelClassName());
//
//      System.out.println("System:");
//      System.out.println("  " + UIManager.getSystemLookAndFeelClassName());

//      System.exit(0);

        UIDefaults uiDefaults = UIManager.getDefaults();
        Enumeration enumerazione = uiDefaults.keys();
        while (enumerazione.hasMoreElements()) {
            Object key = enumerazione.nextElement();
            Object val = uiDefaults.get(key);
            System.out.println("[" +
                    key.toString() +
                    "]:[" +
                    (null != val ? val.toString() : "(null)") +
                    "]");
        }

    }
}// fine della classe
