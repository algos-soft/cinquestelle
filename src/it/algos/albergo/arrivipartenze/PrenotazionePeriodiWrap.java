package it.algos.albergo.arrivipartenze;

import it.algos.base.errore.Errore;

import java.util.ArrayList;

/**
 * Wrapper di una prenotazione in arrivo.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 26-giu-2008
 */
public final class PrenotazionePeriodiWrap {

    /**
     * link al codice del record di prenotazione
     */
    private int prenotazione;

    /**
     * matrice dei link ai codici di periodo
     */
    private ArrayList<Integer> periodi;


    /**
     * Costruttore con parametri.
     *
     * @param prenotazione - link al codice del record di prenotazione
     */
    public PrenotazionePeriodiWrap(int prenotazione) {
        this(prenotazione, new ArrayList<Integer>());
    }// fine del metodo costruttore


    /**
     * Costruttore completo con parametri.
     *
     * @param prenotazione - link al codice del record di prenotazione
     * @param periodi - matrice dei link ai codici di periodo
     */
    public PrenotazionePeriodiWrap(int prenotazione, ArrayList<Integer> periodi) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setPrenotazione(prenotazione);
        this.setPeriodi(periodi);
    }// fine del metodo costruttore completo


    public void add(int periodo) {
        ArrayList<Integer> periodi;

        try { // prova ad eseguire il codice
            periodi = this.getPeriodi();

            if (periodi != null) {
                if (!periodi.contains(periodo)) {
                    periodi.add(periodo);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public int getPrenotazione() {
        return prenotazione;
    }


    public void setPrenotazione(int prenotazione) {
        this.prenotazione = prenotazione;
    }


    public ArrayList<Integer> getPeriodi() {
        return periodi;
    }


    public void setPeriodi(ArrayList<Integer> periodi) {
        this.periodi = periodi;
    }
}
