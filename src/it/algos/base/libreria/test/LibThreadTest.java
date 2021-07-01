package it.algos.base.libreria.test;

import it.algos.base.libreria.LibThread;
import junit.framework.TestCase;

import javax.swing.*;
import java.lang.reflect.Method;

public class LibThreadTest extends TestCase {

    LibThread libThread;


    public void testTimeOut() throws Exception {
        Method metodo;
        libThread = new LibThread();

        long prima;
        long dopo;
        long delta;

        prima = System.currentTimeMillis();

        metodo = this.getClass().getMethod("prova", null);
        libThread.timeOut(metodo, 5000);

        dopo = System.currentTimeMillis();

        delta = dopo - prima;

        if (delta != 1500) {
            fail("tempo sbagliato");
        }// fine del blocco if
    }


    public static void prova() {
//        System.out.println("qui");
        JDialog dialogo;
        dialogo = new JDialog();
        dialogo.add(new JLabel("Esecuzione!"));
        dialogo.setVisible(true);

//        new MessaggioAvviso("Sto andando!!!!");
    }


}