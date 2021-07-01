/**
 * Title:        RigaDettaglio.java
 * Package:      it.algos.albergo.ristorante.menu.stampa.cucina
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 7 novembre 2003 alle 13.19
 */

package it.algos.albergo.ristorante.menu.stampa.cucina;

import it.algos.base.errore.Errore;

import java.util.ArrayList;


/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Definire il modello dati di una riga della stampa dettagliata
 * del menu per la cucina<br>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  7 novembre 2003 ore 13.19
 */
public final class RigaDettaglio extends Object {

    public static final int TIPO_DA_MENU = 1;   // identifica una riga di piatto da menu

    public static final String STRINGA_TIPO_DA_MENU = "Piatti da menu"; // testo per la stampa

    public static final int TIPO_EXTRA = 2; // identifica una riga di piatto extra

    public static final String STRINGA_TIPO_EXTRA = "Piatti extra"; // testo per la stampa

    public static final int TIPO_VARIANTE = 3; // identifica una riga di variante

    public static final String STRINGA_TIPO_VARIANTE = "Modifiche"; // testo per la stampa

    /**
     * Codice della riga menu piatto (RMP) corrispondente
     * (solo per righe base e variante, le righe Extra non lo hanno)
     */
    private int codiceRMP = 0;

    /**
     * codice del piatto extra (solo righe extra)
     */
    private int codiceExtraPiatto = 0;

    /**
     * codice del contorno extra (solo righe extra)
     */
    private int codiceExtraContorno = 0;

    /**
     * codice della eventuale modifica
     */
    private int codiceModifica = 0;

    /**
     * codice della categoria del piatto principale
     */
    private int codiceCategoria = 0;

    /**
     * il tipo di riga (piatto da menu, piatto extra, variante)
     */
    private int tipoRiga = 0;

    /**
     * il testo della riga
     */
    private String testoRiga = null;

    /**
     * lista delle comande ordinate per ogni tavolo
     * contiene oggetti ComandaTavolo
     * (vedi la classe interna per la struttura)
     */
    private ArrayList comande = new ArrayList();

    /**
     * flag per indicare se si tratta di una riga relativa a un piatto Extra
     */
    private boolean extra = false;


    /**
     * Costruttore base <br>
     */
    public RigaDettaglio() {
        /** rimanda al costruttore della superclasse */
        super();

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
    }
    /* fine del metodo inizia */


    /**
     * Ritorna la descrizione corrispondente a un tipo
     * (menu, extra, variante)
     *
     * @param codiceTipo il codice del Tipo
     *
     * @return la descrizione corrispondente al codiceTipo
     */
    public static String getDescrizioneTipo(int codiceTipo) {
        /** variabili e costanti locali di lavoro */
        String unaDescrizione = "";

        /** selezione */
        switch (codiceTipo) {
            case TIPO_DA_MENU:
                unaDescrizione = STRINGA_TIPO_DA_MENU;
                break;
            case TIPO_EXTRA:
                unaDescrizione = STRINGA_TIPO_EXTRA;
                break;
            case TIPO_VARIANTE:
                unaDescrizione = STRINGA_TIPO_VARIANTE;
                break;
            default:
                break;
        } /* fine del blocco switch */

        /** valore di ritorno */
        return unaDescrizione;
    }


    /**
     * Inizializza l'elenco delle quantita' per la riga
     * crea un elenco di quantita' (oggetti ComandaTavolo)
     * inizialmente vuoti
     *
     * @param quantiTavoli il numero dei tavoli apparecchiati del menu
     */
    public void creaElencoQuantitaVuoto(int quantiTavoli) {
        /** pulisce la lista per sicurezza */
        this.getComande().clear();
        /** ciclo for */
        for (int k = 0; k < quantiTavoli; k++) {
            this.getComande().add(new ComandaTavolo());
        } /* fine del blocco for */
    } /* fine del metodo */


    /**
     * Incrementa di una unita' la quantita' ordinata per un dato tavolo.<br>
     *
     * @param indiceTavolo l'indice del tavolo per il quale incrementare
     * la quantita'
     */
    public void incrementaQuantitaTavolo(int indiceTavolo) {
        /** variabili e costanti locali di lavoro */
        ComandaTavolo unaComanda = null;

        try {    // prova ad eseguire il codice
            /* recupera l'oggetto ComandaTavolo corrispondente all'indice*/
            unaComanda = (ComandaTavolo)comande.get(indiceTavolo);
            /* incrementa la quantita' della comanda di 1 unita' */
            unaComanda.setQuantita(unaComanda.getQuantita() + 1);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Incrementa di una unita' la quantita' ordinata fuori orario
     * per un dato tavolo.<br>
     *
     * @param indiceTavolo l'indice del tavolo per il quale
     * incrementare la quantita' fuori orario
     */
    public void incrementaQuantitaFOTavolo(int indiceTavolo) {
        /** variabili e costanti locali di lavoro */
        ComandaTavolo unaComanda = null;

        try {    // prova ad eseguire il codice
            /* recupera l'oggetto ComandaTavolo corrispondente all'indice*/
            unaComanda = (ComandaTavolo)comande.get(indiceTavolo);
            /* incrementa la quantita' della comanda di 1 unita' */
            unaComanda.setQuantitaFO(unaComanda.getQuantitaFO() + 1);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Imposta a vero il flag "Esistono Modifiche" sulla comanda di un dato tavolo.<br>
     *
     * @param indiceTavolo l'indice del tavolo per il quale impostare il flag
     */
    public void setEsistonoModifiche(int indiceTavolo) {
        /** variabili e costanti locali di lavoro */
        ComandaTavolo unaComanda = null;
        try {    // prova ad eseguire il codice
            /* recupera l'oggetto ComandaTavolo corrispondente all'indice*/
            unaComanda = (ComandaTavolo)comande.get(indiceTavolo);
            unaComanda.setModifiche(true);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Imposta a vero il flag "Esistono Modifiche Cucina" sulla
     * comanda di un dato tavolo.<br>
     *
     * @param indiceTavolo l'indice del tavolo per il quale impostare il flag
     */
    public void setEsistonoModificheCucina(int indiceTavolo) {
        /** variabili e costanti locali di lavoro */
        ComandaTavolo unaComanda = null;
        try {    // prova ad eseguire il codice
            /* recupera l'oggetto ComandaTavolo corrispondente all'indice*/
            unaComanda = (ComandaTavolo)comande.get(indiceTavolo);
            unaComanda.setModificheCucina(true);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Determina se esistono modifiche per la comanda di un dato tavolo.<br>
     *
     * @param indiceTavolo l'indice del tavolo per il quale leggere il flag
     *
     * @return true se esistono modifiche comandate dal tavolo specificato
     */
    public boolean isEsistonoModifiche(int indiceTavolo) {
        /** variabili e costanti locali di lavoro */
        ComandaTavolo unaComanda = null;
        boolean modifiche = false;

        try {    // prova ad eseguire il codice
            /* recupera l'oggetto ComandaTavolo corrispondente all'indice*/
            unaComanda = (ComandaTavolo)comande.get(indiceTavolo);
            /* verifica se esistono modifiche */
            modifiche = unaComanda.isModifiche();
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return modifiche;

    } /* fine del metodo */


    /**
     * Determina se esistono modifiche di interesse cucina per
     * la comanda di un dato tavolo.<br>
     *
     * @param indiceTavolo l'indice del tavolo per il quale leggere il flag
     *
     * @return true se esistono modifiche di interesse cucina comandate
     *         dal tavolo specificato
     */
    public boolean isEsistonoModificheCucina(int indiceTavolo) {
        /** variabili e costanti locali di lavoro */
        ComandaTavolo unaComanda = null;
        boolean modifiche = false;

        try {    // prova ad eseguire il codice
            /* recupera l'oggetto ComandaTavolo corrispondente all'indice*/
            unaComanda = (ComandaTavolo)comande.get(indiceTavolo);
            /* verifica se esistono modifiche */
            modifiche = unaComanda.isModificheCucina();
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return modifiche;

    } /* fine del metodo */


    /**
     * Determina se esistono modifiche di interesse della cucina
     * su una qualsiasi comanda per questa riga.<br>
     *
     * @return true se esistono modifiche che interessano
     *         la cucina su qualsiasi comanda di questa riga
     */
    public boolean isEsistonoModificheCucina() {
        /** variabili e costanti locali di lavoro */
        boolean modifiche = false;
        ArrayList listaComande = null;
        ComandaTavolo unaComanda = null;

        try {    // prova ad eseguire il codice
            listaComande = this.getComande();
            for (int k = 0; k < listaComande.size(); k++) {
                unaComanda = (ComandaTavolo)listaComande.get(k);
                if (unaComanda.isModificheCucina()) {
                    modifiche = true;
                    break;
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return modifiche;

    } /* fine del metodo */


    /**
     * Determina se esistono modifiche di interesse della cucina
     * su una qualsiasi comanda con quantita' Fuori Orario
     * per questa riga.<br>
     *
     * @return true se esistono modifiche che interessano
     *         la cucina su qualsiasi comanda FO di questa riga
     */
    public boolean isEsistonoModificheFOCucina() {
        /** variabili e costanti locali di lavoro */
        boolean modifiche = false;
        ArrayList listaComande = null;
        ComandaTavolo unaComanda = null;

        try {    // prova ad eseguire il codice
            listaComande = this.getComande();
            for (int k = 0; k < listaComande.size(); k++) {
                unaComanda = (ComandaTavolo)listaComande.get(k);
                if (unaComanda.getQuantitaFO() != 0) {
                    if (unaComanda.isModificheCucina()) {
                        modifiche = true;
                        break;
                    }// fine del blocco if
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return modifiche;

    } /* fine del metodo */


    /**
     * Ritorna la quantita' totale di ordini per questa riga
     *
     * @return la quantita' totale ordinata per questa riga
     */
    public int getQuantitaTotale() {
        /** variabili e costanti locali di lavoro */
        ComandaTavolo unaComandaTavolo = null;
        int totale = 0;
        int quantita = 0;
        ArrayList listaComande = this.getComande();

        /** ciclo for per sommare le quantita' delle comande */
        for (int k = 0; k < listaComande.size(); k++) {
            unaComandaTavolo = (ComandaTavolo)listaComande.get(k);
            quantita = unaComandaTavolo.getQuantita();
            totale = totale + quantita;
        } /* fine del blocco for */

        /** valore di ritorno */
        return totale;
    } /* fine del metodo */


    /**
     * Ritorna la quantita' totale di ordini fuori orario per questa riga
     *
     * @return la quantita' totale fuori orario per questa riga
     */
    public int getQuantitaTotaleFuoriOrario() {
        /** variabili e costanti locali di lavoro */
        ComandaTavolo unaComandaTavolo = null;
        int totale = 0;
        int quantita = 0;
        ArrayList listaComande = this.getComande();

        /** ciclo for per sommare le quantita' fuori orario delle comande */
        for (int k = 0; k < listaComande.size(); k++) {
            unaComandaTavolo = (ComandaTavolo)listaComande.get(k);
            quantita = unaComandaTavolo.getQuantitaFO();
            totale = totale + quantita;
        } /* fine del blocco for */

        /** valore di ritorno */
        return totale;
    } /* fine del metodo */


    /**
     * Ritorna la quantita' ordinata per questa riga per un dato tavolo.<br>
     *
     * @param indiceTavolo l'indice del tavolo per il quale tornare la qta
     *
     * @return la quantita' totale ordinata dal tavolo per questa riga
     */
    public int getQuantitaTavolo(int indiceTavolo) {
        /** variabili e costanti locali di lavoro */
        ComandaTavolo unaComanda = null;
        ArrayList listaComande = null;
        int quantita = 0;

        try {    // prova ad eseguire il codice
            /* recupera la lista delle comande (oggetti ComandaTavolo)*/
            listaComande = this.getComande();
            /* recupera il singolo oggetto ComandaTavolo corrispondente all'indice*/
            unaComanda = (ComandaTavolo)listaComande.get(indiceTavolo);
            /* recupera la quantita' della comanda */
            quantita = unaComanda.getQuantita();
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quantita;

    } /* fine del metodo */


    /**
     * Ritorna la quantita' ordinata per questa riga per un dato tavolo.<br>
     *
     * @param indiceTavolo l'indice del tavolo per il quale tornare la qta
     *
     * @return la quantita' totale ordinata dal tavolo per questa riga
     */
    public int getQuantitaTavoloFO(int indiceTavolo) {
        /** variabili e costanti locali di lavoro */
        ComandaTavolo unaComanda = null;
        ArrayList listaComande = null;
        int quantita = 0;

        try {    // prova ad eseguire il codice
            /* recupera la lista delle comande (oggetti ComandaTavolo)*/
            listaComande = this.getComande();
            /* recupera il singolo oggetto ComandaTavolo corrispondente all'indice*/
            unaComanda = (ComandaTavolo)listaComande.get(indiceTavolo);
            /* recupera la quantita' della comanda */
            quantita = unaComanda.getQuantitaFO();
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quantita;

    } /* fine del metodo */


    /**
     * Setter for property codiceRigaMenu.
     *
     * @param codiceRMP New value of property codiceRigaMenu.
     */
    public void setCodiceRMP(int codiceRMP) {
        this.codiceRMP = codiceRMP;
    }


    /**
     * Setter for property codiceExtraPiatto.
     *
     * @param codiceExtraPiatto New value of property codiceExtraPiatto.
     */
    public void setCodiceExtraPiatto(int codiceExtraPiatto) {
        this.codiceExtraPiatto = codiceExtraPiatto;
    }


    /**
     * Setter for property codiceExtraContorno.
     *
     * @param codiceExtraContorno
     */
    public void setCodiceExtraContorno(int codiceExtraContorno) {
        this.codiceExtraContorno = codiceExtraContorno;
    }


    /**
     * Setter for property codiceModifica.
     *
     * @param codiceModifica
     */
    public void setCodiceModifica(int codiceModifica) {
        this.codiceModifica = codiceModifica;
    }


    /**
     * Setter for property codiceCategoria.
     *
     * @param codiceCategoria New value of property codiceCategoria.
     */
    public void setCodiceCategoria(int codiceCategoria) {
        this.codiceCategoria = codiceCategoria;
    }


    /**
     * Setter for property tipoRiga.
     *
     * @param tipoRiga New value of property tipoRiga.
     */
    public void setTipoRiga(int tipoRiga) {
        this.tipoRiga = tipoRiga;
    }


    /**
     * Setter for property testoRiga.
     *
     * @param testoRiga New value of property testoRiga.
     */
    public void setTestoRiga(String testoRiga) {
        this.testoRiga = testoRiga;
    }


    /**
     * Setter for property quantita.
     *
     * @param comande New value of property quantita.
     */
    public void setComande(ArrayList comande) {
        this.comande = comande;
    }


    /**
     * Setter for property extra.
     *
     * @param extra New value of property extra.
     */
    public void setExtra(boolean extra) {
        this.extra = extra;
    }


    /**
     * Getter for property codiceRigaMenu.
     *
     * @return il codice della riga di menu corrispondente a questa riga
     */
    public int getCodiceRMP() {
        return this.codiceRMP;
    }


    /**
     * Getter for property codiceExtraPiatto.
     *
     * @return il codice piatto extra della riga
     */
    public int getCodiceExtraPiatto() {
        return this.codiceExtraPiatto;
    }


    /**
     * Getter for property codiceExtraContorno.
     *
     * @return il codice contorno extra della riga
     */
    public int getCodiceExtraContorno() {
        return this.codiceExtraContorno;
    }


    /**
     * Getter for property codiceModifica.
     *
     * @return il codice della eventuale modifica della riga
     */
    public int getCodiceModifica() {
        return codiceModifica;
    }


    /**
     * Getter for property codiceCategoria.
     *
     * @return il codice categoria della riga
     */
    public int getCodiceCategoria() {
        return this.codiceCategoria;
    }


    /**
     * Getter for property tipoRiga.
     *
     * @return Value of property tipoRiga.
     */
    public int getTipoRiga() {
        return this.tipoRiga;
    }


    /**
     * Getter for property testoRiga.
     *
     * @return Value of property testoRiga.
     */
    public String getTestoRiga() {
        return this.testoRiga;
    }


    /**
     * Getter for property quantita.
     *
     * @return Value of property quantita.
     */
    public ArrayList getComande() {
        return this.comande;
    }


    /**
     * Getter for property extra.
     *
     * @return Value of property extra.
     */
    public boolean isExtra() {
        return this.extra;
    }


    /**
     * Struttura dati interna per la comanda di un singolo tavolo
     * per questa riga.<br>
     * Mantiene i seguenti dati:
     * - quantita' totale ordinata (compresa qta fuori orario)
     * - quantita' ordinata fuori orario
     * - flag per indicare la presenza di modifiche nella comanda del tavolo
     */
    private class ComandaTavolo extends Object {

        /**
         * la quantita' totale ordinata
         */
        private int quantita = 0;

        /**
         * la quantita' fuori orario ordinata
         */
        private int quantitaFO = 0;

        /**
         * flag - se esistono modifiche di qualsiasi tipo
         */
        private boolean modifiche = false;

        /**
         * flag - se esistono modifiche di interesse cucina
         */
        private boolean modificheCucina = false;


        /* costruttore di base senza parametri
* rimanda al costruttore completo usando valori di default */
        public ComandaTavolo() {
            this(0, 0, false, false);
        }


        /* costruttore completo */
        public ComandaTavolo(int quantita,
                             int quantitaFO,
                             boolean modifiche,
                             boolean modificheCucina) {
            this.setQuantita(quantita);
            this.setQuantitaFO(quantitaFO);
            this.setModifiche(modifiche);
            this.setModificheCucina(modificheCucina);
        }


        public int getQuantita() {
            return quantita;
        }


        public void setQuantita(int quantita) {
            this.quantita = quantita;
        }


        public int getQuantitaFO() {
            return quantitaFO;
        }


        public void setQuantitaFO(int quantitaFO) {
            this.quantitaFO = quantitaFO;
        }


        public boolean isModifiche() {
            return modifiche;
        }


        public void setModifiche(boolean modifiche) {
            this.modifiche = modifiche;
        }


        public boolean isModificheCucina() {
            return modificheCucina;
        }


        public void setModificheCucina(boolean modificheCucina) {
            this.modificheCucina = modificheCucina;
        }

    } // fine della classe interna

}// fine della classe