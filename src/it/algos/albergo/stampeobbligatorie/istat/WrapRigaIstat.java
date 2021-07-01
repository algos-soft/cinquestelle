package it.algos.albergo.stampeobbligatorie.istat;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.util.ArrayList;

/**
 * Wrapper per incapsulare una singola riga di dati del modulo ISTAT
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 02-ott-2008 ore 16:52
 */
public final class WrapRigaIstat {

    /**
     * Tipo di riga: Italiano, Straniero, Non specificato (da Enum)
     */
    private ISTAT.TipoRiga tipoRiga;

    /**
     * Codice di residenza
     * - Codice della provincia di residenza per Italia
     * - Codice della nazione di residenza per Estero
     */
    private int codResidenza;


    /**
     * Elenco dei codici dei clienti arrivati
     */
    private ArrayList<Integer> codArrivati;

    /**
     * Elenco dei codici dei clienti partiti
     */
    private ArrayList<Integer> codPartiti;




    /**
     * Costruttore completo con parametri. <br>
     *
     * @param tipoRiga Tipo di riga: Italiano, Straniero, Non specificato (da Enum)
     * @param codResidenza Codice di residenza (nazione per stranieri, provincia per italiani)
     */
    public WrapRigaIstat(ISTAT.TipoRiga tipoRiga, int codResidenza) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setTipoRiga(tipoRiga);
        this.setCodResidenza(codResidenza);

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
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> lista;

        try { // prova ad eseguire il codice

            /* crea le liste interne degli arrivati e partiti */
            lista = new ArrayList<Integer>();
            this.setCodArrivati(lista);
            lista = new ArrayList<Integer>();
            this.setCodPartiti(lista);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un cliente in arrivo o in partenza.
     * <p/>
     * @param codCliente codice del cliente da aggiungere
     * @param arrivo true per aggiungerlo in arrivo, false per aggiungerlo in partenza
     */
    public void addCliente(int codCliente, boolean arrivo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> lista;

        try {    // prova ad eseguire il codice

            if (arrivo) {
                lista = this.getCodArrivati();
            } else {
                lista = this.getCodPartiti();
            }// fine del blocco if-else
            lista.add(codCliente);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna una stringa con i codici dei clienti arrivati separati da virgola.
     * <p/>
     * @return la stringa dei clienti arrivati
     */
    public String getStringaArrivati() {
        return this.getStringaClienti(true);
    }


    /**
     * Ritorna una stringa con i codici dei clienti arrivati separati da virgola.
     * <p/>
     * @return la stringa dei clienti arrivati
     */
    public String getStringaPartiti() {
        return this.getStringaClienti(false);
    }

    /**
     * Ritorna una stringa con i codici dei clienti separati da virgola.
     * <p/>
     * @param arrivati per gli arrivati, false per i partiti
     * @return la stringa dei clienti
     */
    private String getStringaClienti(boolean arrivati) {
        /* variabili e costanti locali di lavoro */
        String stringaClienti  = "";
        ArrayList<Integer> listaClienti;
        String[] arrayClienti;
        int codCliente;
        String strCodice;

        try {    // prova ad eseguire il codice

            if (arrivati) {
                listaClienti = this.getCodArrivati();
            } else {
                listaClienti = this.getCodPartiti();
            }// fine del blocco if-else

            arrayClienti = new String[listaClienti.size()];
            for (int k = 0; k < listaClienti.size(); k++) {
                codCliente = listaClienti.get(k);
                strCodice = Lib.Testo.getStringa(codCliente);
                arrayClienti[k]=strCodice;
            } // fine del ciclo for

            stringaClienti = Lib.Testo.concat(",",arrayClienti);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringaClienti;
    }







    ISTAT.TipoRiga getTipoRiga() {
        return tipoRiga;
    }


    private void setTipoRiga(ISTAT.TipoRiga tipoRiga) {
        this.tipoRiga = tipoRiga;
    }


    int getCodResidenza() {
        return codResidenza;
    }


    private void setCodResidenza(int codResidenza) {
        this.codResidenza = codResidenza;
    }


    private ArrayList<Integer> getCodArrivati() {
        return codArrivati;
    }


    private void setCodArrivati(ArrayList<Integer> codArrivati) {
        this.codArrivati = codArrivati;
    }


    private ArrayList<Integer> getCodPartiti() {
        return codPartiti;
    }


    private void setCodPartiti(ArrayList<Integer> codPartiti) {
        this.codPartiti = codPartiti;
    }


    public int getNumArrivati() {
        return this.getCodArrivati().size();
    }


    public int getNumPartiti() {
        return this.getCodPartiti().size();
    }


}// fine della classe