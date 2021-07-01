/**
 * Title:     AlberoCalcolo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      6-dic-2006
 */
package it.algos.base.calcresolver;

import it.algos.base.albero.Albero;
import it.algos.base.albero.AlberoModello;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.logica.CLCalcolato;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.errore.Errore;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Albero che mantiene la struttura dei campi calcolati di un Modulo.
 * </p>
 * I campi calcolati vengono aggiunti all'albero durante la
 * inizializzazione del Modello Dati.<br>
 * Al termine l'albero viene risolto.<br>
 * L'albero viene usato per aggiungere automaticamente i campi
 * calcolati alle query di inserimento e modifica record.<br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 06-dic-2006 ore 14.24.21
 */
public final class AlberoCalcolo extends Albero {

    /**
     * modello di riferimento
     */
    private Modello modello;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param modello dati di riferimento
     */
    public AlberoCalcolo(Modello modello) {
        /* rimanda al costruttore della superclasse */
        super(null);

        try { // prova ad eseguire il codice

            this.setModello(modello);

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
        AlberoCalcolo.RendererNodo renderer;
        AlberoModello datiAlbero = null;
        AlberoNodo nodoRoot;

        try {    // prova ad eseguire il codice

            /* crea il modello dell'albero */
            datiAlbero = new AlberoModello();
            this.setModelloAlbero(datiAlbero);

            /* crea il nodo root */
            nodoRoot = new AlberoNodo();

            /* aggiunge il nodo root */
            datiAlbero.addNodo(nodoRoot, null);

            /* Assegna un renderer personalizzato per
             * disegnare le celle dell'albero. */
            renderer = new AlberoCalcolo.RendererNodo();
            this.setCellRenderer(renderer);

            /* rende invisibile il nodo root */
            this.getTree().setRootVisible(false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Inizializza l'albero
     * <p/>
     * Risolve i nodi
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        String titolo;
        Modello modello;
        Modulo modulo;

//        super.inizializza();

        try { // prova ad eseguire il codice

            /* regola il voce della finestra */
            titolo = "Albero Calcolo ";
            modello = this.getModello();
            if (modello != null) {
                modulo = modello.getModulo();
                if (modulo != null) {
                    titolo += modulo.getNomeChiave();
                }// fine del blocco if
            }// fine del blocco if
            this.setTitoloFinestra(titolo);

            /* risolve l'albero */
            this.risolvi();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge un campo calcolato all'albero.
     * <p/>
     *
     * @param campo calcolato da aggiungere
     */
    public void addCampoCalcolato(Campo campo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> nomiOsservati;
        Campo unCampo;
        NodoCampo nodoPadre = null;
        NodoCampo nodoFiglio = null;
        CampoLogica cl = null;
        CLCalcolato clc = null;
        boolean continua;
        boolean risoltoPadre;
        boolean risoltoFiglio;

        try {    // prova ad eseguire il codice

            /* controlla che non sia nullo */
            continua = campo != null;

            /* se non è calcolato non fa nulla */
            if (continua) {
                continua = campo.isCalcolato();
            }// fine del blocco if

            /* recupera il campo logica e controlla che sia di tipo CLCalcolato */
            if (continua) {
                cl = campo.getCampoLogica();
                continua = cl instanceof CLCalcolato;
            }// fine del blocco if

            /* recupera il campo logica calcolato */
            if (continua) {
                clc = (CLCalcolato)cl;
            }// fine del blocco if

            /* aggiunge il campo all'albero */
            if (continua) {

                /* crea il nodo padre e lo attacca a root */
                nodoPadre = new NodoCampo(campo);
                this.addNodo(nodoPadre, this.getNodoRoot());

                /* attacca tutti i campi osservati sotto al nodo padre */
                nomiOsservati = clc.getCampiOsservati();
                risoltoPadre = true;
                for (String nome : nomiOsservati) {
                    unCampo = this.getModello().getCampo(nome);
                    risoltoFiglio = !unCampo.isCalcolato();
                    nodoFiglio = new NodoCampo(unCampo);
                    nodoFiglio.setRisolto(risoltoFiglio);
                    this.addNodo(nodoFiglio, nodoPadre);

                    /* al primo figlio non risolto, spegne il flag risoltoPadre */
                    if (!risoltoFiglio) {
                        risoltoPadre = false;
                    }// fine del blocco if
                }

                /* se tutti i figli sono risolti, il nodo padre è risolto
                 * se c'è anche un solo figlio non risolto, il padre non è risolto */
                nodoPadre.setRisolto(risoltoPadre);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Risolve l'albero.
     * <p/>
     * Risolve ciclicamente tutti i nodi non risolti.<br>
     * Dopo la risoluzione di un nodo, ricomincia da capo perché la struttura
     * dell'albero potrebbe essere cambiata.<br>
     * Continua fino a quando ci sono nodi non risolti.
     */
    private void risolvi() {
        /* variabili e costanti locali di lavoro */
        NodoCampo nodo;
        boolean fine = false;
        int i = 0;

        try {    // prova ad eseguire il codice

            /* ciclo fino a quando trova nodi di primo livello non risolti */
            while (!fine) {

                /* trova il primo nodo non risolto di primo livello */
                nodo = this.getPrimoNodoNonRisoltoLivello1();
                if (nodo != null) {
                    this.risolviNodo(nodo);
                } else {
                    fine = true;
                }// fine del blocco if-else

                /* controllo anti-loop */
                i++;
                if (i > 1000) {
                    throw new Exception("Risoluzione dell'albero Calcolo in loop.\n" +
                            "Modulo: " +
                            this.getModello().getModulo().getNomeChiave() +
                            "\n" +
                            "Probabile dipendenza circolare.\n" +
                            "Controllare le dipendenze dei campi calcolati.");
                }// fine del blocco if

            }

            /* al termine esegue il reload del modello se no l'albero
             * non è graficamente aggiornato */
            this.getModelloAlbero().reload();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Risolve un nodo.
     * <p/>
     * Risolve tutti i figli
     * Marca il nodo come risolto
     *
     * @param nodo da risolvere
     */
    private void risolviNodo(NodoCampo nodo) {
        /* variabili e costanti locali di lavoro */
        NodoCampo unNodo;
        Enumeration<TreeNode> figli;

        try {    // prova ad eseguire il codice

            figli = nodo.children();
            while (figli.hasMoreElements()) {
                unNodo = (NodoCampo) figli.nextElement();
                if (!unNodo.isRisolto()) {
                    this.sostituisciNodo(unNodo);
                }// fine del blocco if
            }

            /* segna il nodo come risolto */
            nodo.setRisolto(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il primo nodo non risolto di primo livello (sotto a root).
     * <p/>
     *
     * @return il primo nodo non risolto di primo livello,
     *         null se tutti i nodi di primo livello sono risolti
     */
    private NodoCampo getPrimoNodoNonRisoltoLivello1() {
        /* variabili e costanti locali di lavoro */
        AlberoNodo nodoRoot = null;
        NodoCampo nodo = null;
        Enumeration<TreeNode> num;

        try {    // prova ad eseguire il codice
            nodoRoot = this.getNodoRoot();
            num = nodoRoot.children();
            while (num.hasMoreElements()) {
                NodoCampo unNodo = (NodoCampo) num.nextElement();
                if (!unNodo.isRisolto()) {
                    nodo = unNodo;
                    break;
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nodo;
    }


    /**
     * Ritorna il primo nodo risolto contenente un dato campo.
     * <p/>
     * Cerca in tutto l'albero
     *
     * @param campo da cercare
     *
     * @return il primo nodo risolto contenente il campo, null se non trovato
     */
    private NodoCampo getPrimoNodoRisolto(Campo campo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<NodoCampo> lista = null;
        NodoCampo nodo = null;

        try {    // prova ad eseguire il codice

            /* tutti i nodi contenenti il campo */
            lista = this.getNodi(campo);

            /* prende il primo che è risolto */
            for (NodoCampo unNodo : lista) {
                if (unNodo.isRisolto()) {
                    nodo = unNodo;
                    break;
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nodo;
    }


    /**
     * Sostituisce il nodo non risolto con un corrispondente
     * nodo risolto recuperato dall'albero.
     * <p/>
     * Se il nodo risolto si trova al primo livello, lo stacca
     * dall'albero e lo attacca al posto del nodo da sostituire.<br>
     * Se il nodo risolto non si trova al primo livello, ne crea un clone
     * e lo attacca al posto del nodo da sostituire.<br>
     *
     * @param nodo da sostituire
     */
    private void sostituisciNodo(NodoCampo nodo) {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        NodoCampo unNodoRisolto;
        NodoCampo unNodoParente;
        int indice;

        try {    // prova ad eseguire il codice
            campo = nodo.getCampo();
            unNodoRisolto = this.getPrimoNodoRisolto(campo);

            if (unNodoRisolto != null) {

                if (unNodoRisolto.getLevel() == 1) {

                    /* rimuove il nodo risolto da root */
                    unNodoRisolto.removeFromParent();

                } else {

                    /* clona il nodo */
                    unNodoRisolto = (NodoCampo)unNodoRisolto.clonaNodo();

                }// fine del blocco if-else

                /* recupera il parente del nodo da sostitituire */
                unNodoParente = (NodoCampo)nodo.getParent();

                /* rimuove il nodo da sostituire */
                indice = unNodoParente.getIndex(nodo);
                nodo.removeFromParent();

                /* aggiunge il nodo risolto al posto del nodo da sostituire */
                unNodoParente.insert(unNodoRisolto, indice);

            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna l'elenco dei nodi contenenti un dato campo.
     * <p/>
     *
     * @param campo da cercare
     *
     * @return la lista dei nodi contenenti il campo
     */
    private ArrayList<NodoCampo> getNodi(Campo campo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<NodoCampo> lista = null;
        ArrayList<NodoCampo> listaOut = null;

        try {    // prova ad eseguire il codice
            listaOut = new ArrayList<NodoCampo>();
            lista = this.getNodiCampo(AlberoModello.PREORDER);
            for (NodoCampo nodo : lista) {
                if (nodo.contieneCampo(campo)) {
                    listaOut.add(nodo);
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Ritorna tutti i nodi dell'albero escluso il
     * nodo root nell'ordine richiesto
     * <p/>
     *
     * @param metodo il metodo di attraversamento dell'albero.
     * (PREORDER, POSTORDER o BREADTH_FIRST)
     *
     * @return tutti i nodi dell'albero escluso il nodo root
     *
     * @see AlberoModello
     */
    private ArrayList<NodoCampo> getNodiCampo(int metodo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<NodoCampo> nodi = null;
        ArrayList<AlberoNodo> nodiAlbero;
        NodoCampo nodo;

        try { // prova ad eseguire il codice
            nodi = new ArrayList<NodoCampo>();
            nodiAlbero = this.getNodi(metodo);
            for (AlberoNodo alberoNodo : nodiAlbero) {
                if (!alberoNodo.isRoot()) {
                    if (alberoNodo instanceof NodoCampo) {
                        nodo = (NodoCampo)alberoNodo;
                        nodi.add(nodo);
                    }// fine del blocco if
                }// fine del blocco if
            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nodi;
    } // fine del metodo


    /**
     * Dato un elenco di campi, ritorna l'elenco univoco
     * di tutti i campi dipendenti.
     * <p/>
     *
     * @param campi dei quali determinare le dipendenze
     *
     * @return l'elenco dei campi dipendenti
     */
    public ArrayList<Campo> getCampiDipendenti(ArrayList<Campo> campi) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campiDip = null;
        ArrayList<Campo> listaCampi = null;

        try {    // prova ad eseguire il codice

            campiDip = new ArrayList<Campo>();
            for (Campo campo : campi) {
                listaCampi = this.getCampiDipendenti(campo);
                for (Campo c : listaCampi) {
                    if (!campiDip.contains(c)) {
                        campiDip.add(c);
                    }// fine del blocco if
                }
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campiDip;
    }


    /**
     * Dato un campo ritorna l'elenco
     * di tutti i campi calcolati da esso dipendenti.
     * <p/>
     *
     * @param campo del quale determinare le dipendenze
     *
     * @return l'elenco dei campi calcolati dipendenti
     */
    public ArrayList<Campo> getCampiDipendenti(Campo campo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campiDip = null;
        ArrayList<NodoCampo> nodiCampo = null;
        TreeNode[] arrayNodi;
        AlberoNodo unNodo;
        NodoCampo unNodoCampo;
        Campo unCampo;

        try {    // prova ad eseguire il codice

            campiDip = new ArrayList<Campo>();
            nodiCampo = this.getNodi(campo);
            for (NodoCampo nodo : nodiCampo) {
                arrayNodi = this.getModelloAlbero().getPathToRoot(nodo);
                for (int k = 0; k < arrayNodi.length; k++) {
                    unNodo = (AlberoNodo)arrayNodi[k];

                    /* non considera il primo elemento (root) e
                     * l'ultimo (il campo stesso) */
                    if (!unNodo.isRoot()) {
                        if (!unNodo.equals(nodo)) {
                            unNodoCampo = (NodoCampo)unNodo;
                            unCampo = unNodoCampo.getCampo();
                            if (!campiDip.contains(unCampo)) {
                                campiDip.add(unCampo);
                            }// fine del blocco if
                        }// fine del blocco if

                    }// fine del blocco if
                } // fine del ciclo for
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campiDip;
    }


    /**
     * Dato un elenco di campi, ritorna l'elenco univoco
     * di tutti i campi osservati.
     * <p/>
     *
     * @param campi dei quali determinare i campi osservati
     *
     * @return l'elenco univoco dei campi osservati
     */
    public ArrayList<Campo> getCampiOsservati(ArrayList<Campo> campi) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campiOss = null;
        ArrayList<Campo> listaCampi = null;

        try {    // prova ad eseguire il codice

            campiOss = new ArrayList<Campo>();
            for (Campo campo : campi) {
                listaCampi = this.getCampiOsservati(campo);
                for (Campo c : listaCampi) {
                    if (!campiOss.contains(c)) {
                        campiOss.add(c);
                    }// fine del blocco if
                }
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campiOss;
    }


    /**
     * Dato un campo ritorna l'elenco
     * di tutti i campi da esso osservati.
     * <p/>
     *
     * @param campo del quale determinare i campi osservati
     *
     * @return l'elenco dei campi osservati
     */
    private ArrayList<Campo> getCampiOsservati(Campo campo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campiOss = null;
        ArrayList<NodoCampo> nodiCampo = null;
        Enumeration<TreeNode> figli;
        Campo unCampo;

        try {    // prova ad eseguire il codice

            campiOss = new ArrayList<Campo>();
            nodiCampo = this.getNodi(campo);
            for (NodoCampo nodo : nodiCampo) {
                figli = nodo.children();
                while (figli.hasMoreElements()) {
                    TreeNode node = figli.nextElement();
                    if(node instanceof NodoCampo){
                        NodoCampo nodoCampo=(NodoCampo)node;
                        unCampo = nodoCampo.getCampo();
                        if (!campiOss.contains(unCampo)) {
                            campiOss.add(unCampo);
                        }
                    }else{
                        System.out.println(node + " non è istanza di NodoCampo");
                    }
                }
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campiOss;
    }


    /**
     * Recupera tutti i campi calcolati in postorder
     * <p/>
     *
     * @return l'elenco dei campi calcolati in postorder
     */
    public ArrayList<Campo> getCampiCalcolatiPostorder() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campiCalc = null;
        ArrayList<NodoCampo> nodiCampo = null;
        Campo campo;

        try {    // prova ad eseguire il codice

            campiCalc = new ArrayList<Campo>();
            nodiCampo = this.getNodiCampo(AlberoModello.POSTORDER);
            for (NodoCampo nodo : nodiCampo) {
                if (nodo.isCalcolato()) {
                    campo = nodo.getCampo();
                    if (!campiCalc.contains(campo)) {
                        campiCalc.add(campo);
                    }// fine del blocco if
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campiCalc;
    }


    private Modello getModello() {
        return this.modello;
    }


    private void setModello(Modello modello) {
        this.modello = modello;
    }


    /**
     * Nodo dell'albero.
     * </p>
     */
    private final class NodoCampo extends AlberoNodo {

        /**
         * campo contenuto nel nodo
         */
        private Campo campo;

        /**
         * flag - indica se il campo è calcolato
         */
        private boolean calcolato;

        /**
         * flag - indica se il nodo è risolto
         * il nodo è risolto se tutti i figli sono risolti
         */
        private boolean risolto;


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param campo da inserire nel nodo
         */
        public NodoCampo(Campo campo) {

            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.setCampo(campo);

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
            boolean calcolato;

            try { // prova ad eseguire il codice

                /* regola il flag calcolato */
                calcolato = this.getCampo().isCalcolato();
                this.setCalcolato(calcolato);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        private Campo getCampo() {
            return campo;
        }


        private void setCampo(Campo campo) {
            this.campo = campo;
        }


        private boolean isCalcolato() {
            return calcolato;
        }


        private void setCalcolato(boolean calcolato) {
            this.calcolato = calcolato;
        }


        private boolean isRisolto() {
            return risolto;
        }


        private void setRisolto(boolean risolto) {
            this.risolto = risolto;
        }


        /**
         * Controlla se questo nodo contiene un dato campo.
         * <p/>
         *
         * @param campo campo da controllare
         *
         * @return true se questo nodo contiene il campo
         */
        public boolean contieneCampo(Campo campo) {
            /* variabili e costanti locali di lavoro */
            boolean contiene = false;

            try {    // prova ad eseguire il codice
                if (this.getCampo().equals(campo)) {
                    contiene = true;
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return contiene;
        }


        public String toString() {
            /* variabili e costanti locali di lavoro */
            String stringa = "";

            try { // prova ad eseguire il codice
                stringa = this.getCampo().toString();
                stringa += ", ";
                if (this.isCalcolato()) {
                    stringa += "calc";
                } else {
                    stringa += "noncalc";
                }// fine del blocco if-else

                stringa += ", ";
                if (this.isRisolto()) {
                    stringa += "R";
                }// fine del blocco if-else


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return stringa;
        }

    } // fine della classe 'interna'


    /**
     * Renderer specifico per i nodi dell'albero
     */
    private class RendererNodo extends DefaultTreeCellRenderer {

        /**
         * Intercetto il metodo per ottenere informazioni
         * sul nodo e regolare il testo del renderer
         */
        public Component getTreeCellRendererComponent(JTree tree,
                                                      Object value,
                                                      boolean sel,
                                                      boolean expanded,
                                                      boolean leaf,
                                                      int row,
                                                      boolean hasFocus) {

            /* variabili e costanti locali di lavoro */
            Component comp;
            NodoCampo nodo = null;
            Campo campo;
            String testo = "";
            Color colore = Color.BLACK;

            comp = super.getTreeCellRendererComponent(tree,
                    value,
                    sel,
                    expanded,
                    leaf,
                    row,
                    hasFocus);

            if (value != null) {
                if (value instanceof NodoCampo) {
                    nodo = (NodoCampo)value;
                    campo = nodo.getCampo();
                    testo = campo.toString();
                    if (nodo.isRisolto()) {
                        testo += " (R)";
                    }// fine del blocco if

                    /* regolo il colore */
                    if (nodo.isCalcolato()) {
                        colore = Color.RED;
                    }// fine del blocco if

                }// fine del blocco if
            }// fine del blocco if

            /* regolo il testo per il renderer */
            this.setText(testo);
            this.setForeground(colore);

            /* valore di ritorno */
            return comp;

        }

    }


}// fine della classe
