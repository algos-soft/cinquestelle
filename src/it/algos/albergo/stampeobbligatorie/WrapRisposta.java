package it.algos.albergo.stampeobbligatorie;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.StrBool;

import java.util.ArrayList;

/**
 * Wrapper per incapsulare una risposta del test chkStampabile
 * </p>
 * Contiene un elenco di errori, ognuno composto da un booleano
 * (true per errore critico, false per errore non critico)
 * e un testo di messaggio dell'errore
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 23-set-2008 ore  13:50
 */
public final class WrapRisposta {

    /**
     * Elenco degli errori - Stringa e flag Critico
     */
    private ArrayList<StrBool> errori;


    /**
     * Costruttore completo senza parametri.<br>
     */
    public WrapRisposta() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            this.setErrori(new ArrayList<StrBool>());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un errore all'elenco degli errori.
     * <p/>
     * @param critico true per errore critico
     * @param messaggio il testo dell'errore
     */
    public void addErrore(boolean critico, String messaggio) {
        /* variabili e costanti locali di lavoro */
        ArrayList<StrBool> errori;
        StrBool errore;

        try {    // prova ad eseguire il codice
            errore = new StrBool(messaggio, critico);
            errori = this.getErrori();
            errori.add(errore);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla se il wrapper contiene degli errori.
     * <p/>
     * @return true se contiene almeno un errore
     */
    public boolean isErrore () {
        return (this.getErrori().size()>0);
    }

    /**
     * Controlla se il wrapper contiene degli errori critici.
     * <p/>
     * @return true se contiene almeno un errore critico
     */
    public boolean isErroreCritico () {
        /* variabili e costanti locali di lavoro */
        boolean critico = false;
        ArrayList<StrBool> errori;

        try { // prova ad eseguire il codice
            errori = this.getErrori();
            for(StrBool errore : errori){
                if (errore.isBooleano()) {
                    critico = true;
                    break;
                }// fine del blocco if
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return critico;
    }


    /**
     * Ritorna un messaggio di errore completo (somma il testo di tutti i messaggi).
     * <p/>
     * @return il messaggio di errore
     */
    public String getMessaggio() {
        /* variabili e costanti locali di lavoro */
        String messaggio="";
        ArrayList<StrBool> errori;
        String testo;

        try {    // prova ad eseguire il codice
            errori = this.getErrori();
            for(StrBool errore : errori){
                testo = errore.getStringa();
                if (Lib.Testo.isValida(messaggio)) {
                    messaggio+="\n";
                }// fine del blocco if
                messaggio+="- "+testo;
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return messaggio;
    }





    private ArrayList<StrBool> getErrori() {
        return errori;
    }


    private void setErrori(ArrayList<StrBool> errori) {
        this.errori = errori;
    }
}// fine della classe