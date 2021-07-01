/**
 * Title:        CDBooleano.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 23 ottobre 2003 alle 11.46
 */
package it.algos.base.campo.dati;

import java.util.regex.Pattern;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.tipodati.tipoarchivio.TABooleano;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TMBooleano;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TVBooleano;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.query.ordine.Ordine;

/**
 * Classe concreta per implementare un oggetto da CDBase;
 * <p/>
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati <br>
 */
public final class CDBooleano extends CDBase {

    private static final TipoArchivio TIPO_ARCHIVIO = TABooleano.getIstanza();

    private static final TipoMemoria TIPO_MEMORIA = TMBooleano.getIstanza();

    private static final TipoVideo TIPO_VIDEO = TVBooleano.getIstanza();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDBooleano() {
        /* rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDBooleano(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente, TIPO_ARCHIVIO, TIPO_MEMORIA, TIPO_VIDEO);

        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Ordine ordine;
        Ordine.Elemento elemento;

        /* assegna l'icona specifica per il tipo di campo */
        this.setIcona(ICONA_CAMPO_BOOL);

        /* assegna editor nullo così la JTable usa il default che è un check box */
        this.setEditor(null);

        /* regola il renderer per la lista */
//        this.setRenderer(new RendererBooleanoRadio(unCampoParente));

        /* primo ordine standard specifico per i booleani */
        ordine = this.getCampoParente().getCampoLista().getOrdinePrivato();
        if (ordine != null) {
            elemento = ordine.getElemento(0);
            if (elemento != null) {
                elemento.setOperatore(Operatore.DISCENDENTE);
            }// fine del blocco if
        }// fine del blocco if

//        /* assegna un editor di tipo check box */
//        JCheckBox cb = new JCheckBox();
//        cb.setHorizontalAlignment(SwingConstants.CENTER);
//        DefaultCellEditor editor = new DefaultCellEditor(cb);
//        this.setEditor(editor);

    } /* fine del metodo inizia */

//    protected void assegnaCellEditor() {
//        /* variabili e costanti locali di lavoro */
//        JCheckBox cb;
//        DefaultCellEditor editor;
//
//        try { // prova ad eseguire il codice
//
//            /* assegna un editor di tipo check box */
//            cb = new JCheckBox();
//            cb.setHorizontalAlignment(SwingConstants.CENTER);
//            editor = new FieldEditor(cb);
//            this.setEditor(editor);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//    }


    /**
     * Ritorna il valore a livello di memoria
     * per il campo editato nella cella.
     * <p/>
     *
     * @param stringaIn il valore in uscita dall'editor in formato stringa
     *
     * @return il valore convertito a livello memoria
     */
    protected Object getValoreEditor(String stringaIn) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;

        try {    // prova ad eseguire il codice
            valore = Libreria.getBool(stringaIn);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna true se il campo e' booleano.
     *
     * @return true se booleano
     */
    public boolean isBooleano() {
        return true;
    }


    /**
     * Controllo di validit� del valore video.
     * <p/>
     * Controlla che il valore sia compatibile col tipo di dati del Campo <br>
     *
     * @param valoreVideo oggetto da controllare
     *
     * @return true se il valore � compatibile
     */
    public boolean isVideoValido(Object valoreVideo) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;

        try { // prova ad eseguire il codice

            /* controlla il tipo */
            if (valoreVideo instanceof Boolean) {
                valido = true;
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Converte un valore di memoria.
     * <p/>
     * Trasforma in un valore accettabile per questo tipo di campo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param valore in ingresso
     *
     * @return valore accettabile convertito
     *         false se non accettabile
     */
    public Object convertiMemoria(Object valore) {
        /* variabili e costanti locali di lavoro */
        Boolean valoreCovertito = false;
        boolean continua;
        String testo;

        try { // prova ad eseguire il codice

            continua = (valore != null);

            if (continua) {
                if (valore instanceof Boolean) {
                    valoreCovertito = (Boolean)valore;
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                if ((valore instanceof String) || (valore instanceof Number)) {
                    testo = valore.toString();

                    if (Pattern.matches("^[Tt1Vv].*", testo)) {
                        valoreCovertito = true;
                    } else {
                        valoreCovertito = false;
                    }// fine del blocco if-else

                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valoreCovertito;
    }


}// fine della classe