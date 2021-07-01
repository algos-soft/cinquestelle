/*
 * Test.java
 *
 * Created on 20 dicembre 2003, 11.45
 */

package it.algos.base.filtroAlb;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.util.Date;

/**
 * @author albi
 */
public class Test extends javax.swing.JFrame {

    /**
     * Creates a new instance of Test
     */
    public Test() {
        super();
        inizializza();
    }


    public static void main(String args[]) {
        new Test();
    }


    private void inizializza() {
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        cp.add(new JLabel("Codice fiscale"));
        cp.add(FiltriIngressoFactory.creaFiltroCodiceFiscale());
        cp.add(new JLabel("Et\u00e0"));
        cp.add(FiltriIngressoFactory.creaFiltroDecimali());
        cp.add(new JLabel("Data"));
        cp.add(FiltriIngressoFactory.creaFiltroData());
        cp.add(new JLabel("Filtri intelligenti"));
        cp.add(new JLabel("Data di nascita (gg-mm-aaaa)"));
        //cp.add(FiltriIngressoFactory.creaFiltro(FiltroIngresso.NUMERI+FiltroIngresso.TRATTINO));
        JTextComponent tc = new JTextField();//FiltriIngressoFactory.creaFiltroData();
        tc.addFocusListener(new FocusAdapter() {
            private FiltroUscita filtro = null;

            private String valoreInserito = null;


            {
                filtro = FiltriUscitaFactory.creaFiltroData();
                filtro.setValoreMassimo(new Date());
            }


            public void focusLost(FocusEvent e) {
                JTextComponent src = (JTextComponent)e.getSource();
                try {
                    valoreInserito = src.getText();
                    Object value = filtro.parseObject(valoreInserito);
                    src.setText(filtro.format(value));
                } catch (ParseException pe) {
                    System.out.println("non parsa " + src.getText());
                }
            }


            public void focusGained(FocusEvent e) {
                ((JTextComponent)e.getSource()).setText(valoreInserito);
            }
        });

        cp.add(tc);


        this.setTitle("Test dei Filtri");

        this.setDefaultCloseOperation(Test.EXIT_ON_CLOSE);

        this.pack();
        this.setVisible(true);

    }
}
