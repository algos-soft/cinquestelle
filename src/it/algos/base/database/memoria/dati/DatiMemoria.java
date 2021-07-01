/**
 * Title:     DatiMemoria
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-ott-2004
 */
package it.algos.base.database.memoria.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.dati.DatiBase;
import it.algos.base.database.dati.InfoColonna;
import it.algos.base.database.memoria.tipodati.TipoDatiMemBase;
import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.errore.Errore;
import it.algos.base.matrice.MatriceBase;
import it.algos.base.modulo.Modulo;

import java.util.ArrayList;

/**
 * Contenitore di dati in memoria.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-ott-2004 ore 16.08.14
 */
public final class DatiMemoria extends DatiBase {

    private MatriceBase matrice;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public DatiMemoria() {
        /* rimanda al costruttore della superclasse */
        this((Dati)null);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param dati per riempire la matrice
     */
    public DatiMemoria(Dati dati) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia(dati);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo.
     * <p/>
     * Costruisce un oggetto dati in memoria
     * rappresentante un elenco di campi
     * Il numero di colonne è pari al numero dei campi
     * Il numero di righe è zero
     *
     * @param campi rappresentati dalle colonne
     */
    public DatiMemoria(ArrayList<Campo> campi) {
        /* rimanda al costruttore della superclasse */
        this(campi, 0);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo.
     * <p/>
     * Costruisce un oggetto dati in memoria
     * rappresentante un elenco di campi.
     * Il numero di colonne è pari al numero dei campi
     * Il numero di righe va specificato
     *
     * @param campi rappresentati dalle colonne
     * @param righe numero iniziale delle righe di dati
     */
    public DatiMemoria(ArrayList<Campo> campi, int righe) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            MatriceBase matrice;
            int colonne;

            this.regolaInfoColonne(campi);

            colonne = campi.size();
            matrice = new MatriceBase(righe, colonne);
            this.setMatrice(matrice);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo.
     * <p/>
     * Costruisce un oggetto dati in memoria
     *
     * @param righe numero iniziale delle righe di dati
     * @param colonne numero iniziale delle colonne di dati
     */
    public DatiMemoria(int righe, int colonne) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice

            MatriceBase matrice;
            matrice = new MatriceBase(righe, colonne);
            this.setMatrice(matrice);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @param dati per riempire la matrice
     *
     * @throws Exception unaEccezione
     */
    private void inizia(Dati dati) throws Exception {
        this.setMatrice(new MatriceBase(0, 0));
        if (dati != null) {
            this.regolaMatrice(dati);
        }// fine del blocco if
    }// fine del metodo inizia


    /**
     * Regola le informazioni sulle colonne in base ai campi.
     * <p/>
     * Crea un oggetto InfoColonna per ogni campo e lo aggiunge alla collezione
     * Aggiunge una entry alla mappa campi
     * Identifica la posizione della eventuale colonna chiave
     * @param campi lista dei campi
     */
    private void regolaInfoColonne(ArrayList<Campo> campi) {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        InfoColonna ic = null;
        TipoDati td = null;
        Class classe;
        String chiave = null;
        Modulo modulo;

        try {    // prova ad eseguire il codice


            for (int k = 0; k < campi.size(); k++) {

                campo = campi.get(k);

                /* crea un tipo di dati per il campo */
                td = new TipoDatiMemBase();
                classe = campo.getCampoDati().getTipoMemoria().getClasse();
                td.setClasseBl(classe);

                /* crea un oggetto di informazioni sulla colonna,
                 * lo regola e lo aggiunge alla lista */
                ic = new InfoColonna();
                ic.setPosizione(k);
                ic.setCampo(campo);
                ic.setTipoDati(td);

                this.getInfoColonne().add(ic);

                /* recupera la chiave del campo */
                chiave = campo.getChiaveCampo();

                /* aggiunge la entry per la mappa campi */
                this.getMappaCampi().put(chiave, ic);

                /* se il campo e' il campo chiave, regola la posizione
                 * della colonna chiave */
                modulo = campo.getModulo();
                if (modulo != null) {
                    if (campo.equals(modulo.getCampoChiave())) {
                        this.setColonnaChiavi(k);
                    }// fine del blocco if
                }// fine del blocco if


            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regolazione della matrice.
     *
     * @param dati per riempire la matrice
     */
    public void regolaMatrice(Dati dati) {
        /* variabili e costanti locali di lavoro */
        int righe;
        int colonne;
        MatriceBase mat;
        Object valore;

        try { // prova ad eseguire il codice

            righe = dati.getRowCount();
            colonne = dati.getColumnCount();

            mat = new MatriceBase(righe, colonne);
            this.setMatrice(mat);

            for (int k = 0; k < righe; k++) {
                for (int j = 0; j < colonne; j++) {

                    valore = dati.getValueAt(k, j);

                    mat.setValueAt(k, j, valore);
                } // fine del ciclo for
            } // fine del ciclo for


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il valore Memoria di una cella.
     * <p/>
     * Se la cella rappresenta il valore di un campo, ritorna il valore
     * memoria per il tipo di campo<br>
     * Se la cella e' il risultato di una funzione, ritorna il valore
     * fornito dal database.<br>
     *
     * @param riga indice di riga della cella richiesta (0 per la prima)
     * @param colonna indice di colonna della cella richiesta (0 per la prima)
     *
     * @return il valore Memoria dell'oggetto nella cella specificata
     */
    public Object getValueAt(int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        boolean continua;
        MatriceBase mat;

        try { // prova ad eseguire il codice
            mat = this.getMatrice();
            continua = mat != null;

            if (continua) {
                continua = riga < this.getRowCount();
            }// fine del blocco if

            if (continua) {
                continua = colonna < this.getColumnCount();
            }// fine del blocco if

            if (continua) {
                valore = mat.getValueAt(riga, colonna);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Assegna un valore a una cella.
     * <p/>
     * Se gli indici sono fuori dalla matrice non fa nulla.
     *
     * @param valore da assegnare
     * @param riga l'indice della riga, 0 per la prima
     * @param colonna l'indice della colonna, 0 per la prima
     */
    public void setValueAt(Object valore, int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        MatriceBase matrice;

        try { // prova ad eseguire il codice
            matrice = this.getMatrice();
            if ((riga >= 0) && (riga < this.getRowCount())) {
                if ((colonna >= 0) && (colonna <= this.getColumnCount())) {
                    matrice.setValueAt(riga, colonna, valore);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il numero di righe contenute nei dati
     * <p/>
     *
     * @return il numero di righe nei dati
     */
    public int getRowCount() {
        /* variabili e costanti locali di lavoro */
        int righe = 0;
        MatriceBase mat;

        try { // prova ad eseguire il codice
            mat = this.getMatrice();

            if (mat != null) {
                righe = mat.getNumeroRighe();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return righe;

    } /* fine del metodo */


    /**
     * Ritorna il numero di colonne dei Dati.
     * <p/>
     *
     * @return il numero di colonne dei dati
     */
    public int getColumnCount() {
        /* variabili e costanti locali di lavoro */
        int colonne = 0;
        MatriceBase mat;

        try { // prova ad eseguire il codice
            mat = this.getMatrice();

            if (mat != null) {
                colonne = mat.getNumeroColonne();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return colonne;

    }


    /**
     * Aggiunge una riga ai dati.
     * <p/>
     *
     * @return l'indice della riga aggiunta, -1 se non aggiunta
     */
    public int addRiga() {
        /* variabili e costanti locali di lavoro */
        int indice = -1;
        MatriceBase matrice;
        int quanteColonne;
        Object[] oggetti;

        try {    // prova ad eseguire il codice
            matrice = this.getMatrice();
            quanteColonne = matrice.getNumeroColonne();
            oggetti = new Object[quanteColonne];
            matrice.addRiga(oggetti);
            indice = matrice.getNumeroRighe() - 1;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return indice;
    }


    /**
     * Aggiunge una colonna ai dati.
     * <p/>
     *
     * @return l'indice della colonna aggiunta, -1 se non aggiunta
     */
    public int addColonna() {
        /* variabili e costanti locali di lavoro */
        int indice = -1;
        MatriceBase matrice;
        int quanteRighe;
        Object[] oggetti;

        try {    // prova ad eseguire il codice
            matrice = this.getMatrice();
            quanteRighe = matrice.getNumeroRighe();
            oggetti = new Object[quanteRighe];
            matrice.addColonna(oggetti);
            indice = matrice.getNumeroColonne() - 1;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return indice;
    }


    /**
     * Cancella tutte le righe.
     * <p/>
     */
    public void removeRighe() {
        this.getMatrice().removeRighe();
    }
    


    private MatriceBase getMatrice() {
        return matrice;
    }


    public void setMatrice(MatriceBase matrice) {
        this.matrice = matrice;
    }
}// fine della classe
