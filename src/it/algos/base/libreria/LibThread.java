package it.algos.base.libreria;

import it.algos.base.errore.Errore;

import javax.swing.*;
import java.lang.reflect.Method;

/**
 * Classe astratta con metodi statici. </p> Questa classe astratta: <ul> <li>  </li> <li>  </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 2-mag-2007 ore 17.13.08
 */
public class LibThread {

    /**
     * Metodo statico.
     * <p/>
     */
    public void timeOut(Method metodo, int tempo) {
        /* variabili e costanti locali di lavoro */
        Thread controllo;
        Thread operativo;
        Controllo classeControllo;
        Operazione classeOperazione;

        try { // prova ad eseguire il codice
            classeControllo = new Controllo();
            classeControllo.setTempo(tempo);

            controllo = new Thread(classeControllo);

            classeOperazione = new Operazione();
            classeOperazione.setMetodo(metodo);

            operativo = new Thread(classeOperazione);
            operativo.start();
            controllo.start();
//            int b = 87;                   
//
//            while (b==87) {
//            }

            while (controllo.getState() != Thread.State.TERMINATED) {
                int a = 87;
//                operativo.interrupt();
//                operativo.stop();
            }// fine del blocco while

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Classe 'interna'. </p>
     */
    public final class Controllo implements Runnable {

        long tempo;


        public void run() {

            try { // prova ad eseguire il codice
                JDialog dialogo;
                dialogo = new JDialog();
                dialogo.add(new JLabel("Per favore devi aspettare qualche secondo!"));
                dialogo.setVisible(true);
                Thread.sleep(getTempo());
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public long getTempo() {
            return tempo;
        }


        public void setTempo(long tempo) {
            this.tempo = tempo;
        }
    } // fine della classe 'interna'


    /**
     * Classe 'interna'. </p>
     */
    public final class Operazione implements Runnable {

        Method metodo;


        public void run() {

            try { // prova ad eseguire il codice
                metodo.invoke(null);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public Method getMetodo() {
            return metodo;
        }


        public void setMetodo(Method metodo) {
            this.metodo = metodo;
        }
    } // fine della classe 'interna'

}// fine della classe
