/**
 * Title:        CDElencoInterno.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Valbonesi
 * @version 1.0  /
 * Creato:       il 7 novembre 2003 alle 11.59
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.elemento.ESeparatore;
import it.algos.base.campo.elemento.EVuoto;
import it.algos.base.elenco.Elenco;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.matrice.MatriceDoppia;
import it.algos.base.tavola.renderer.RendererElenco;

import java.util.ArrayList;

/**
 * Classe concreta per implementare un oggetto da <code>CDElenco</code> <br>
 * <p/>
 * Questa classe concreta e' responsabile di: <br>
 * A - Gestire i dati per i gruppi di oggetti <br>
 * B - La lista valori e' interna; viene regolata con un metodo <i>accessor</i>
 * dal <i>cliente</i> che usa il <code>Campo</code>, prima della sua
 * inizializzazione <br>
 * E - Mantiene un flag per la <i>tipologia</i> di memorizzazione: <il>
 * <li>selezione singola - registra direttamente la posizione
 * (codice per i valori esterni - posizione per i valori interni); <br>
 * <li>selezione multipla - registra un numero che corrisponde ad una serie
 * di valori booleani (ha senso solo per i gruppi di check box) </il> <br>
 *
 * @author Guido Andrea Ceresa e Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  7 novembre 2003 ore 11.59
 */
public final class CDElencoInterno extends CDElenco {

    /**
     * array dei valori alla creazione (di solito stringa)
     */
    protected ArrayList valoriIniziali = null;

    /**
     * array dei valori di una eventuale legenda da mostrare a fianco del popup
     */
    protected ArrayList<String> valoriLegenda = null;

    /**
     * lista degli elementi interni del combo
     */
    private Campo.ElementiCombo[] valoriCombo = null;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDElencoInterno() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDElencoInterno(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        this.setElencoInterno(true);

        /* regola il renderer dei dati nella lista */
        this.setRenderer(new RendererElenco(this.getCampoParente()));
    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializza() {

        /* invoca il metodo sovrascritto della superclasse */
        super.inizializza();

        try {    // prova ad eseguire il codice
            /* Crea un Elenco, con la Matrice doppia */
            super.creaElenco();

            this.getCampoParente().getCampoVideo().setUsaNuovo(false);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Recupera il valore di elenco correntemente selezionato.
     * <p/>
     *
     * @return il valore corrente
     */
    public Object getValoreElenco() {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        Elenco elenco;

        try { // prova ad eseguire il codice
            elenco = this.getElenco();
            if (elenco != null) {
                valore = elenco.getValoreSelezionato();
//                valore = elenco.getValore((Integer) this.getMemoria());
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Assegna il valore memoria del campo in base a un valore di elenco.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param valore da cercare nell'elenco
     */
    public void setValoreDaElenco(Object valore) {
        /* variabili e costanti locali di lavoro */
        Elenco elenco = null;
        int pos;
        int posizione = 0;
        ArrayList valori;

        try { // prova ad eseguire il codice
            elenco = this.getElenco();
            if (elenco != null) {
                valori = elenco.getListaValori();
                if (valori != null) {

                    pos = 1;
                    for (Object unValore : valori) {
                        if (unValore.equals(valore)) {
                            posizione = pos;
                            break;
                        }// fine del blocco if
                        pos++;
                    }

                    if (posizione != 0) {
                        this.getCampoParente().setValore(posizione);
                    }// fine del blocco if

                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



    /**
     * Assegna il valore memoria del campo in base a un valore di elenco.
     * <p/>
     * todo alex - Nuova versione del 05-2009
     * todo - da verificare e unificare col precedente!
     *
     * @param valore da cercare nell'elenco
     */
    public void setValoreDaElencoNew(Object valore) {
        /* variabili e costanti locali di lavoro */
        Elenco elenco;
        int pos;
        int posizione = 0;
        ArrayList valori;

        try { // prova ad eseguire il codice

            elenco = this.getElenco();
            if (elenco != null) {
                valori = elenco.getListaValori();
                if (valori != null) {

                    pos = 1;
                    for (Object unValore : valori) {
                        if (unValore.equals(valore)) {
                            posizione = pos;
                            break;
                        }// fine del blocco if

                        /* se è un elemento speciale non lo conta */
                        boolean speciale = ((unValore instanceof EVuoto) || (unValore instanceof ESeparatore));
                        if (!speciale) {
                            pos++;
                        }// fine del blocco if
                    }

                    if (posizione != 0) {
                        this.getCampoParente().setValore(posizione);
                    }// fine del blocco if

                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



    /**
     * recupera la lista di valori <br>
     * riempie la matrice doppia con i valori <br>
     */
    protected MatriceDoppia regolaValoriMatrice(MatriceDoppia unaMatriceDoppia) {
        /* variabili e costanti locali di lavoro */
        ArrayList listaValori;

        try {    // prova ad eseguire il codice

            /* recupera la lista di valori */
            listaValori = this.getValoriInterni();

            /* inserisce i valori nella matrice doppia */
            if (listaValori != null) {
                unaMatriceDoppia.setValori(listaValori);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unaMatriceDoppia;

    } /* fine del metodo */


    /**
     * Regola i valori di default della matrice.<br>
     * Se è stata passata una lista di ElementiCombo la usa
     */
    protected void regolaDefaultMatrice(MatriceDoppia unaMatriceDoppia) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Campo.ElementiCombo[] elementi;
        int dim;
        Object[] codici;
        Object[] valori;

        try { // prova ad eseguire il codice
            super.regolaDefaultMatrice(unaMatriceDoppia);
            elementi = this.getValoriCombo();
            continua = (elementi != null && elementi.length > 0);

            if (continua) {
                dim = elementi.length;
                codici = new Object[dim];
                valori = new Object[dim];

                for (int k = 0; k < elementi.length; k++) {
                    codici[k] = elementi[k].getCodice();
                    valori[k] = elementi[k];
                } // fine del ciclo for

                unaMatriceDoppia.setCodici(codici);
                unaMatriceDoppia.setValori(valori);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    public String getLegenda(int pos) {
        /* variabili e costanti locali di lavoro */
        String legenda = "";
        ArrayList<String> lista;

        try { // prova ad eseguire il codice
            lista = this.getValoriLegenda();
            if (lista != null) {
                if ((pos > -1) && (pos < lista.size())) {
                    legenda = lista.get(pos);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return legenda;
    }


    /**
     * lista dei valori alla creazione
     */
    public void setValoriInterni(ArrayList valoriIniziali) {
        this.valoriIniziali = valoriIniziali;
    } /* fine del metodo setter */


    /**
     * lista dei valori alla creazione
     */
    public void setValoriInterni(Campo.ElementiCombo[] valori) {
        this.setValoriCombo(valori);
        this.creaElenco();
    } /* fine del metodo setter */


    /**
     * elenco dei valori alla creazione (di solito stringa)
     */
    public void setValoriInterni(String unaStringaValori) {
        this.setValoriInterni(Lib.Array.creaLista(unaStringaValori));
    } /* fine del metodo setter */


    /**
     * array dei valori alla creazione
     */
    public void setValoriInterni(String[] unaStringaValori) {
        this.setValoriInterni(Lib.Testo.getStringaVirgola(unaStringaValori));
    } /* fine del metodo setter */


    /**
     * array dei valori alla creazione
     */
    public ArrayList getValoriInterni() {
        return this.valoriIniziali;
    } /* fine del metodo getter */


    /**
     * Assegna la lista dei valori della legenda.
     * <p/>
     *
     * @param listaValori la lista dei valori interni
     */
    public void setValoriLegenda(ArrayList<String> listaValori) {
        this.valoriLegenda = listaValori;
    }


    /**
     * Assegna i valori della legenda.
     * <p/>
     *
     * @param valori stringa di valori della legenda, separati da virgola
     */
    public void setValoriLegenda(String valori) {
        this.setValoriLegenda(Lib.Array.creaLista(valori));
    }


    public ArrayList<String> getValoriLegenda() {
        return valoriLegenda;
    }


    private Campo.ElementiCombo[] getValoriCombo() {
        return valoriCombo;
    }


    private void setValoriCombo(Campo.ElementiCombo[] valoriCombo) {
        this.valoriCombo = valoriCombo;
    }
}// fine della classe