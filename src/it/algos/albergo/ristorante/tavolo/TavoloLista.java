/**
 * Title:     tavoloLista
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-mag-2005
 */
package it.algos.albergo.ristorante.tavolo;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.lista.ListaBase;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;

import javax.swing.JPanel;
import java.awt.Component;

/**
 * Lista specifica per il modulo Tavolo.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-mag-2005 ore 11.28.57
 */
public final class TavoloLista extends ListaBase {

    /* il modulo Tavolo */
    private static Modulo moduloTavolo = null;

    /* il campo Occupato del modulo Tavolo */
    private static Campo campoOccupato = null;

    /* il campo MezzaPensione del modulo Tavolo */
    private static Campo campoMezza = null;

    /* pannello vuoto */
    private static JPanel panVuoto = null;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public TavoloLista() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* non avendo una Vista specifica usa quella di default */
        this.setNomeVista("");

        panVuoto = new JPanel();

    }// fine del metodo inizia


    /**
     * Inizializzazione della Lista.
     * <p/>
     */
    public void inizializza() {

        super.inizializza();

        try {    // prova ad eseguire il codice

            /* regola alcune variabili di lavoro comuni */
            moduloTavolo = Progetto.getModulo(Tavolo.NOME_MODULO);
            campoOccupato = moduloTavolo.getCampo(Tavolo.Cam.occupato);
            campoMezza = moduloTavolo.getCampo(Tavolo.Cam.mezzapensione);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Intercetta prepareRenderer di JTable.
     * <p/>
     * Prepara il renderer per una cella prima che venga disegnato.
     * Sovrascritto dalle sottoclassi. <br>
     * Permette alla sottoclasse di modificare il componente
     * da visualizzare nella cella. <br>
     *
     * @param comp il componente da visualizzare della cella
     * @param riga l'indice della riga da disegnare (0 per la prima)
     * @param colonna l'indice della colonna da disegnare (0 per la prima)
     *
     * @return il componente da visualizzare nella cella
     */
    public Component prepareRenderer(Component comp, int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        Component compOut = null;
        Campo campo = null;
        int colonnaOccupato = 0;
        boolean occupato = false;
        int colonnaMezza = 0;
        boolean mezza = false;
        String nomeCampo = "";

        try { // prova ad eseguire il codice

            /* inizialmente il componente in uscita e' quello in entrata */
            compOut = comp;

            /* determina se il tavolo e' occupato */
            colonnaOccupato = this.getColonna(campoOccupato);
            occupato = this.getBoolAt(riga, colonnaOccupato);

            /* determina se il tavolo e' a mezza pensione */
            colonnaMezza = this.getColonna(campoMezza);
            mezza = this.getBoolAt(riga, colonnaMezza);

            /* recupera il nome del campo da visualizzare */
            campo = this.getCampo(colonna);
            nomeCampo = campo.getNomeInterno();

            /* se e' uno dei campi dipendenti dal flag occupato,
             * e il tavolo non e' occupato, visualizza un JPanel vuoto
             * al posto del campo */
            if (nomeCampo.equals(Tavolo.Cam.nomecliente.get()) ||
                    nomeCampo.equals(Tavolo.Cam.mezzapensione.get()) ||
                    nomeCampo.equals(Tavolo.Cam.pasto.get())) {

                if (!occupato) {
                    panVuoto.setBackground(comp.getBackground());
                    panVuoto.setForeground(comp.getForeground());
                    panVuoto.setPreferredSize(comp.getPreferredSize());
                    compOut = panVuoto;
                }// fine del blocco if

            }// fine del blocco if

            /* se e' uno dei campi dipendenti dal flag Mezza Pensione,
       * e il tavolo non e' a Mezza Pensione, visualizza un JPanel vuoto
       * al posto del campo */
            if (nomeCampo.equals(Tavolo.Cam.pasto.get())) {

                if (!mezza) {
                    panVuoto.setBackground(comp.getBackground());
                    panVuoto.setForeground(comp.getForeground());
                    panVuoto.setPreferredSize(comp.getPreferredSize());
                    compOut = panVuoto;
                }// fine del blocco if

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return compOut;
    }

}// fine della classe
