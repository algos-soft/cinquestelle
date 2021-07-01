/**
 * Title:     Monitorabile
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-giu-2006
 */
package it.algos.base.progressbar;

import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Operazione monitorabile da una ProgressBar.
 * </p>
 * Esegue in un Thread separato
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-giu-2006 ore 0.59.12
 */
public abstract class OperazioneMonitorabile implements Runnable {

    private ProgressBar bar;

    private String messaggio;

    private boolean breakAbilitato;

    private int periodo;

    private Timer timer;

    private Thread threadOperazione;

    private boolean interrompi;


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param pb progress bar che deve monitorare l'operazione
     */
    public OperazioneMonitorabile(ProgressBar pb) {
        /* rimanda al costruttore della superclasse */
        this(pb, "");
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param pb progress bar che deve monitorare l'operazione
     * @param messaggio da visualizzare nella ProgressBar
     */
    public OperazioneMonitorabile(ProgressBar pb, String messaggio) {
        /* rimanda al costruttore della superclasse */
        this(pb, messaggio, false);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param pb progress bar che deve monitorare l'operazione
     * @param messaggio da visualizzare nella ProgressBar
     * @param breakAbilitato per abilitare la possibilita' di interruzione
     */
    public OperazioneMonitorabile(ProgressBar pb, String messaggio, boolean breakAbilitato) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setBar(pb);
        this.setMessaggio(messaggio);
        this.setBreakAbilitato(breakAbilitato);

        try { // prova ad eseguire il codice

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JButton bottone;
        ActionListener[] azioni;
        ArrayList<Object> listaAzioni;
        AzioneBreak azioneBreak;

        try { // prova ad eseguire il codice

            /* tempo in millisecondi di aggiornamento dello stato */
            this.setPeriodo(100);

            /* crea un timer che interroga periodicamente l'operazione */
            this.setTimer(new Timer(this.getPeriodo(), new ListenerOp()));

            /* regola il messaggio nella ProgressBar */
            this.getProgressBar().setString(this.getMessaggio());

            /* regola l'abilitazione del break */
            this.getProgressBar().setBreakAbilitato(this.isBreakAbilitato());

            /*
             * rimuove tutti i listener di tipo AzioneBreak
             * aggiunge un listener AzioneBreak al bottone
             * break della status bar
             */
            bottone = this.getProgressBar().getBottoneBreak();
            azioni = bottone.getActionListeners();
            listaAzioni = Lib.Array.creaLista(azioni);
            for (Object oggetto : listaAzioni) {
                if (oggetto instanceof AzioneBreak) {
                    azioneBreak = (AzioneBreak)oggetto;
                    bottone.removeActionListener(azioneBreak);
                }// fine del blocco if
            }
            bottone.addActionListener(new AzioneBreak());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Regola le variabili del pacchetto in funzione dello stato corrente <br>
     */
    public void avvia() {

        try { // prova ad eseguire il codice

            this.getProgressBar().setMaximum(this.getMax());

            /* avvia il timer */
            this.getTimer().start();

            /* avvia l'operazione (esegue in un thread proprio) */
            this.startNewThread();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Ritorna il valore di fondo scala per la ProgressBar.
     * <p/>
     */
    public abstract int getMax();


    /**
     * Ritorna il valore corrente dell'avanzamento.
     * <p/>
     * Invocato periodicamente dalla ProgressBar
     */
    public abstract int getCurrent();


    /**
     * Ritorna lo stato di completamento.
     * <p/>
     * Viene acceso quanto l'elaborazione e' terminata o quando
     * viene interrotta
     *
     * @return true se l'elaborazione e' terminata
     */
    public boolean isDone() {
        /* variabili e costanti locali di lavoro */
        boolean done = false;

        if (this.isInterrompi()) {
            done = true;
        } else {
            if (this.getCurrent() >= this.getMax()) {
                done = true;
            }// fine del blocco if
        }// fine del blocco if-else

        /* valore di ritorno */
        return done;
    }


    /**
     * Esegue l'operazione.
     * <p/>
     * Da qui parte l'esecuzione del codice specifico.
     */
    public abstract void start();


    /**
     * Metodo eseguito da Java per all'avvio di un thread.
     * <p/>
     * Si puo' lanciare direttamente dall'esterno
     * se si vuole eseguire nel thread corrente.
     */
    public void run() {
        /* variabili e costanti locali di lavoro */
        ProgressBar pb;
        boolean pbVisibileIniziale = false;
        boolean pbRegolata = false;

        try {    // prova ad eseguire il codice

            /*
             * se esiste la ProgressBar controlla se e' visibile
             * e poi la rende comunque visibile
             */
            pb = this.getProgressBar();
            if (pb != null) {
                pbVisibileIniziale = pb.isVisible();
                pb.setVisible(true);
                pbRegolata = true;
            }// fine del blocco if

            /*
             * chiama il metodo start()
             * (sovrascritto nelle classi specifiche)
             */
            this.start();

            /* se la progress bar era invisibile la rimette a posto come prima */
            if (pbRegolata) {
                pb.setVisible(pbVisibileIniziale);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Lancia un nuovo thread con questa classe.
     * <p/>
     * Java esegue il metodo run()
     */
    private void startNewThread() {
        Thread thread = null;

        try {    // prova ad eseguire il codice

            /* se non c'e' un thread attivo
             * crea, registra e avvia il nuovo thread */
            if (this.getThreadOperazione() == null) {
                thread = new Thread(this);
                this.setThreadOperazione(thread);
                thread.start(); // Java invoca il metodo run()
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Metodo invocato quando si preme sul bottone Break
     * della ProgressBar
     * <p/>
     * Chiede conferma
     * Interrompe l'operazione
     */
    private void richiestaBreak() {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo;

        try {    // prova ad eseguire il codice

            dialogo = DialogoFactory.annullaConferma();
            dialogo.setTitolo("Attenzione");
            dialogo.setMessaggio("Confermi l'interruzione della operazione?");
            dialogo.avvia();

            if (dialogo.isConfermato()) {
                this.setInterrompi(true);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    public ProgressBar getProgressBar() {
        return bar;
    }


    private void setBar(ProgressBar bar) {
        this.bar = bar;
    }


    private String getMessaggio() {
        return messaggio;
    }


    protected void setMessaggio(String messaggio) {
        /* variabili e costanti locali di lavoro */
        ProgressBar pb;

        try { // prova ad eseguire il codice

            this.messaggio = messaggio;

            /* se esiste gia' al ProgressBar la cambia al volo */
            pb = this.getProgressBar();
            if (pb != null) {
                pb.setString(messaggio);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private boolean isBreakAbilitato() {
        return breakAbilitato;
    }


    protected void setBreakAbilitato(boolean breakAbilitato) {
        /* variabili e costanti locali di lavoro */
        ProgressBar pb;

        try { // prova ad eseguire il codice

            this.breakAbilitato = breakAbilitato;

            /* regola la ProgressBar*/
            pb = this.getProgressBar();
            if (pb != null) {
                pb.setBreakAbilitato(breakAbilitato);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    public int getPeriodo() {
        return periodo;
    }


    private void setPeriodo(int periodo) {
        this.periodo = periodo;
    }


    private Timer getTimer() {
        return timer;
    }


    private void setTimer(Timer timer) {
        this.timer = timer;
    }


    private Thread getThreadOperazione() {
        return threadOperazione;
    }


    private void setThreadOperazione(Thread threadOperazione) {
        this.threadOperazione = threadOperazione;
    }


    protected boolean isInterrompi() {
        return interrompi;
    }


    private void setInterrompi(boolean interrompi) {
        this.interrompi = interrompi;
    }


    /**
     * Listener invocato periodicamente dal timer.
     */
    private class ListenerOp implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            /* variabili e costanti locali di lavoro */
            int corrente;

            /* recupera il valore di avanzamento corrente */
            corrente = getCurrent();

            /* regola la progress bar */
            getProgressBar().setValue(corrente);

            /* controlla se ha terminato
             * in tal caso ferma il timer */
            if (isDone()) {
                Lib.Sist.beep();
                getTimer().stop();
                getProgressBar().setValue(0);
                getProgressBar().setString("Terminato");
            }
        }

    }

    /**
     * Listener per il bottone break della StatusBar. </p>
     */
    public final class AzioneBreak implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            richiestaBreak();
        }
    } // fine della classe 'interna'


}
