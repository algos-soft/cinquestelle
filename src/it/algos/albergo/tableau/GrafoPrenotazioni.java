/**
 * Title:     GrafoPrenotazioni
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-feb-2009
 */
package it.algos.albergo.tableau;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.OnEditingFinished;
import it.algos.base.wrapper.DueInt;

import org.jgraph.JGraph;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.GraphSelectionModel;
import org.jgraph.graph.VertexView;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Grafo delle prenotazioni.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-feb-2009 ore 9.11.17
 */
class GrafoPrenotazioni extends JGraph {

    /* Colore della griglia per i giorni infrasettimanali */

    static final Color COLORE_GRIGLIA_INFRASET = Color.lightGray;  // grigio

    /* Colore della griglia per il weekend */

    static final Color COLORE_GRIGLIA_WEEKEND = new Color(169, 77, 78);  // rosso

    /* Colore della griglia per il primo del mese */

    static final Color COLORE_GRIGLIA_MESE = Color.darkGray;  // blu?

    /* Colore della griglia per le camere (orizzontale) */

    static final Color COLORE_GRIGLIA_CAMERE = Color.lightGray;  // grigio

    /* Colore della griglia quando evidenziata */

    static final Color COLORE_GRIGLIA_HIGHLIGHT = new Color(37, 181, 252); 

    /* contenitore di riferimento */

    private PanGrafi panGrafi;

    /* lista delle celle di griglia */

    private ArrayList<CellGriglia> celleGriglia;

    /* mappa delle celle di periodo (chiave periodo, cella) */

    private HashMap<Integer, CellPeriodoIF> mappaPeriodi;

    /* croce di highlight corrente */

    private HighlightCross highlightCross = null;

    /* cella che evidenzia il giorno di oggi */

    private GraphCell cellaOggi;

    /**
     * Costruttore completo.
     * <p/>
     *
     * @param tbComp contenitore di riferimento
     */
    public GrafoPrenotazioni(PanGrafi tbComp) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setPanGrafi(tbComp);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo

    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            /**
             * sostituisce il delegato alla UI con uno specializzato
             * va fatto prima di ogni altra cosa se no fa casino
             */
            this.setUI(new GPGraphUI(this));
            
//            setBackground(Color.yellow);
//            setOpaque(true);

            GraphModel model = new DefaultGraphModel();
            GraphLayoutCache view = new GraphLayoutCache(model, new PeriodoViewFactory());
            this.setModel(model);
            this.setGraphLayoutCache(view);

            /* regolazioni generali */
            this.setGridEnabled(true); // allinea alla griglia
            this.setGridVisible(false); // griglia non visibile
            this.setGridMode(JGraph.LINE_GRID_MODE);   // griglia a linee
            this.setGridSize(Tableau.GRID_SIZE);  // dim. griglia
            this.setMinimumMove(10);  // numero minimo di pixel prima di iniziare un movimento
//            this.setTolerance(0);
            this.setHandleSize(4);
            this.setHandleColor(Color.yellow);

            this.setEditable(false);  // no editing dei componenti
            this.setAntiAliased(true);
            this.setAutoResizeGraph(false);  // no resizing se sposto celle fuori
            this.setDropEnabled(false); // no drop da componenti esterni
            this.setMoveBeyondGraphBounds(false);  // no spostare celle oltre i confini
            this.setMarqueeHandler(null);  // no marquee (la selezione di più elementi trascinando un rettangolo)
            this.getSelectionModel()
                    .setSelectionMode(GraphSelectionModel.SINGLE_GRAPH_SELECTION); // seleziona solo una cella per volta

            this.setBorder(BorderFactory.createLineBorder(Color.gray));

            /**
             * registra il grafo presso il tooltip manager di sistema
             * se non fai questo i tooltips non si vedono
             */
            ToolTipManager.sharedInstance().registerComponent(this);

            /**
             * registra un listener per le modifiche alle celle
             */
            this.getModel().addGraphModelListener(new AzGrafoModificato());

            /* crea la lista delle celle di griglia */
            this.setCelleGriglia(new ArrayList<CellGriglia>());

            /* crea la mappa delle celle di periodo */
            this.setMappaPeriodi(new HashMap<Integer, CellPeriodoIF>());

            /* carica i dati e popola il grafo secondo le impostazioni correnti */
            this.reload();
            
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia

    /**
     * Crea la griglia visibile.
     * <p/>
     * Elimina la griglia corrente e crea una nuova griglia
     * in base al periodo corrente
     */
    private void creaGriglia() {
        /* variabili e costanti locali di lavoro */
        ArrayList<CellGriglia> celle = new ArrayList<CellGriglia>();
        CellGriglia cell;
        GraphCell cellOggi = null;
        GraphLayoutCache cache = this.getGraphLayoutCache();

        try {    // prova ad eseguire il codice

            /* elimina la griglia corrente */
            cache.remove(this.getCelleGriglia().toArray());

            /* crea le righe orizzontali */
            int nCamere = this.getPanGrafi().getNumRisorse();
            for (int k = 0; k < nCamere; k++) {
                cell = this.creaCellaGriglia(k, SwingConstants.HORIZONTAL, TipoLinea.camera);
                celle.add(cell);
            } // fine del ciclo for

            /* crea le righe verticali */
            int nGiorni = this.getPanGrafi().getNumGiorni();
            Date dataStart = this.getPanGrafi().getData1();
            Date dataCurr = AlbergoLib.getDataProgramma();
            if (dataCurr == null) {
                dataCurr = Lib.Data.getCorrente();
            }// fine del blocco if

            int gset = Lib.Data.getNumeroGiornoSettimana(dataStart);
            for (int k = 0; k < nGiorni; k++) {

                /* eventuale segnale della data di oggi */
                Date data = Lib.Data.add(dataStart, k);
                if (data.equals(dataCurr)) {
                    cell = this.creaCellaOggi(k);
                    this.setCellaOggi(cell);    // memorizza
                    celle.add(cell);
                    cellOggi = cell;
                }// fine del blocco if

                /* calcola il numero del giorno del mese
                * relativamente al giorno successivo */
                int gmes = Lib.Data.getNumeroGiornoInMese(data);

                /* determina il tipo di linea da usare in funzione del giorno */
                TipoLinea tipoLinea;
                if (gmes == 1) {
                    tipoLinea = TipoLinea.mese;
                } else {
                    if ((gset == Calendar.SATURDAY) || (gset == Calendar.SUNDAY) || (gset
                            == Calendar.MONDAY)) {
                        tipoLinea = TipoLinea.weekend;
                    } else {
                        tipoLinea = TipoLinea.feriale;
                    }// fine del blocco if-else
                }// fine del blocco if-else

                /* crea la cella */
                cell = this.creaCellaGriglia(k, SwingConstants.VERTICAL, tipoLinea);
                celle.add(cell);
                gset++;
                if (gset > 7) {
                    gset = 1;
                }// fine del blocco if

            } // fine del ciclo for

            /* registra l'elenco delle nuove celle di griglia */
            this.setCelleGriglia(celle);

            /* le inserisce tutte insieme */
            cache.insert(celle.toArray());

            /**
             * manda in fondo tutte le celle orizzontali
             */
            ArrayList<CellGriglia> listOrizz = new ArrayList<CellGriglia>();
            for (CellGriglia cella : celle) {
                if (cella.isOrizzontale()) {
                    listOrizz.add(cella);
                }// fine del blocco if
            }
            Object[] arrOrizz = new Object[listOrizz.size()];
            for (int k = 0; k < listOrizz.size(); k++) {
                arrOrizz[k] = listOrizz.get(k);
            } // fine del ciclo for
            this.getGraphLayoutCache().toBack(arrOrizz);

            /* manda in fondo il segnalatore della data di oggi, se esiste*/
            if (cellOggi != null) {
                Object[] celleoggi = new Object[1];
                celleoggi[0] = cellOggi;
                this.getGraphLayoutCache().toBack(celleoggi);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Crea una riga di griglia per il grafo corrente.
     * <p/>
     *
     * @param numero di riga o colonna (0 per la prima)
     * @param orientamento SwingConstants.HORIZONTAL o SwingConstants.VERTICAL
     * @param tipoLinea tipo di linea da creare (enum SepLine)
     * (solo per linea verticale, passare null se linea orizzontale)
     *
     * @return la cella creata
     */
    private CellGriglia creaCellaGriglia(int numero, int orientamento, TipoLinea tipoLinea) {
        /* variabili e costanti locali di lavoro */
        CellGriglia cell = new CellGriglia(orientamento, numero, tipoLinea);
        int x, y, w, h;
        AttributeMap map;

        try {    // prova ad eseguire il codice

            int nCamere = this.getPanGrafi().getNumRisorse();
            int nGiorni = this.getPanGrafi().getNumGiorni();

            if (orientamento == SwingConstants.HORIZONTAL) {
                x = 0;
                y = numero * Tableau.V_MODULE;
                w = nGiorni * Tableau.H_MODULE;
                h = tipoLinea.getSpessore();
            } else {
                x = numero * Tableau.H_MODULE;
                y = 0;
                w = tipoLinea.getSpessore();
                h = nCamere * Tableau.V_MODULE;
            }// fine del blocco if-else

            map = cell.getAttributes();
            GraphConstants.setBounds(map, new Rectangle2D.Double(x, y, w, h));
            GraphConstants.setOpaque(map, true);
            GraphConstants.setSelectable(map, false);
            GraphConstants.setLineWidth(map, 1);
            GraphConstants.setBackground(map, tipoLinea.getColore());

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cell;
    }

    /**
     * Crea una cella verticale per segnalare il giorno corrente.
     * <p/>
     *
     * @param numero di riga o colonna (0 per la prima)
     *
     * @return la cella creata
     */
    private CellGriglia creaCellaOggi(int numero) {
        /* variabili e costanti locali di lavoro */
        CellGriglia cell = new CellGriglia(SwingConstants.VERTICAL, numero, null);
        int x, y, w, h;
        AttributeMap map;

        try {    // prova ad eseguire il codice

            int nCamere = this.getPanGrafi().getNumRisorse();

            x = numero * Tableau.H_MODULE;
            y = 0;
            w = Tableau.H_MODULE;
            h = nCamere * Tableau.V_MODULE;

            map = cell.getAttributes();
            GraphConstants.setBounds(map, new Rectangle2D.Double(x, y, w, h));
            GraphConstants.setBackground(map, new Color(247, 222, 224));
            GraphConstants.setOpaque(map, true);
            GraphConstants.setSelectable(map, false);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cell;
    }

    /**
     * Evidenzia la colonna e la riga a una data posizione di cella.
     * <p/>
     * Colora le celle della griglia di fondo
     *
     * @param row la riga, 0 per la prima
     * @param col la colonna, 0 per la prima
     */
    private void highlightGridAt(int row, int col) {
        /* variabili e costanti locali di lavoro */
        HighlightCross cross;
        int oldRow = -1;
        int oldCol = -1;

        try { // prova ad eseguire il codice

            /* elimina la croce corrente se esiste */
            cross = this.getHighlightCross();
            if (cross != null) {
                oldRow = cross.getRow();
                oldCol = cross.getCol();
                this.removeHighlight();
            }// fine del blocco if

            /**
             * controlla se ho cliccato su un punto  diverso dal precedente
             * se è lo stesso punto non ricreo la croce
             */
            if ((row != oldRow) || (col != oldCol)) {

                /* crea una nuova croce */
                cross = new HighlightCross(row, col);
                this.setHighlightCross(cross);

                /* recupera l'array delle celle della croce */
                Object[] oggetti = cross.getObjectArray();

                /* aggiunge la croce al grafo */
                this.getGraphLayoutCache().insert(oggetti);

                /* manda le celle in fondo */
                this.getGraphLayoutCache().toBack(oggetti);

                /* manda in fondo la cella del giorno di oggi */
                Object[] celleoggi = new Object[1];
                celleoggi[0] = this.getCellaOggi();
                this.getGraphLayoutCache().toBack(celleoggi);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }

    /**
     * Elimina la croce di highlight.
     * <p/>
     */
    void removeHighlight() {
        /* variabili e costanti locali di lavoro */
        HighlightCross cross;

        try {    // prova ad eseguire il codice
            cross = this.getHighlightCross();
            if (cross != null) {
                cross.remove();
                this.setHighlightCross(null);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Mouse premuto nel grafo.
     * <p/>
     * Evidenzia riga e colonna nella griglia
     *
     * @param x le coordinate di schermo (da convertire)
     * @param y le coordinate di schermo (da convertire)
     */
    public void mousePressed(int x, int y) {

        try {    // prova ad eseguire il codice

            Point2D p1 = new Point2D.Double(x, y);
            Point2D p2 = fromScreen(p1);
            double newX = p2.getX();
            double newY = p2.getY();
            double col = newX / Tableau.H_MODULE;
            double row = newY / Tableau.V_MODULE;
            this.highlightGridAt((int)row, (int)col);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }

    /**
     * Ricarica il grafo dei periodi con le impostazioni correnti.
     * <p/>
     */
    public void reload() {

        /* svuota grafo */
    	GraphLayoutCache cache = this.getGraphLayoutCache();
        Object[] cells = cache.getCells(true,true,true,true);
        cache.remove(cells);

        /* crea la griglia visibile */
        this.creaGriglia();

        /* popola il grafo */
        this.popola();
        
    }

    /**
     * Popola il grafo con i dati dei periodi.
     * <p/>
     */
    private void popola() {
        /* variabili e costanti locali di lavoro */
        HashMap<Integer, CellPeriodoIF> mappaCelle;
        ArrayList<CellPeriodoIF> listaCelle;

        try {    // prova ad eseguire il codice

            /* recupera la mappa di celle periodo corrente */
            mappaCelle = this.getMappaPeriodi();

            /* chiede al Tableau di fornire le nuove celle di periodo da inserire */
            listaCelle = this.getPanGrafi().getTableau().getCellePeriodo();

            /* regola la posizione delle celle nel grafo */
            for (CellPeriodoIF cella : listaCelle) {
                this.regolaCella(cella);
            }

            /* inserisce le nuove celle */
            this.getGraphLayoutCache().insert(listaCelle.toArray());

            /* aggiorna la mappa delle celle correnti */
            mappaCelle.clear();
            for (CellPeriodoIF cella : listaCelle) {
                mappaCelle.put(cella.getIdSorgente(), cella);
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Inserisce una cella di periodo nel grafo.
     * <p/>
     * - regola la posizione della cella
     * - aggiunge la cella al grafo
     * - aggiunge la cella alla mappa
     *
     * @param cella la cella da aggiungere
     */
    private void insertCell(CellPeriodoIF cella) {
        try {    // prova ad eseguire il codice
            this.regolaCella(cella);
            this.getGraphLayoutCache().insert(cella);
            this.getMappaPeriodi().put(cella.getIdSorgente(), cella);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Rimuove una cella di periodo dal grafo.
     * <p/>
     * - rimuove la cella dal grafo
     * - rimuove la cella dalla mappa
     *
     * @param cella la cella da aggiungere
     */
    private void removeCell(CellPeriodoIF cella) {
        try {    // prova ad eseguire il codice
            Object[] celle = new Object[1];
            celle[0] = cella;
            this.getGraphLayoutCache().remove(celle);
            this.getMappaPeriodi().remove(cella.getIdSorgente());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Regola la posizione nel grafo di una cella.
     * <p/>
     *
     * @param cella della quale regolare la posizione
     */
    private void regolaCella(CellPeriodoIF cella) {
        /* variabili e costanti locali di lavoro */
        boolean selectable;

        try {    // prova ad eseguire il codice

            PanGrafi tbComp = this.getPanGrafi();
            int idRisorsa = cella.getUO().getIdRisorsa();
            Date dataInizio = cella.getUO().getDate(UserObjectPeriodo.KEY_DATAINIZIO);
            Date dataFine = cella.getUO().getDate(UserObjectPeriodo.KEY_DATAFINE);

            /* posiziona la cella nel grafo */
            DueInt pos = tbComp.getPosPeriodo(idRisorsa, dataInizio);
            int riga = pos.x;
            int colonna = pos.y;

            int x = (colonna + 1) * Tableau.H_MODULE;
            x = x - (Tableau.H_MODULE / 2);   //a metà cella

            int marg = Tableau.V_MODULE - Tableau.H_CELLE_PERIODO;
            int half = marg / 2;

            int y = riga * Tableau.V_MODULE + half;
            int hCell = Tableau.H_CELLE_PERIODO;

            int giorni = Lib.Data.diff(dataFine, dataInizio);
            int wCell = giorni * Tableau.H_MODULE;

            /* determina se è selezionabile */
//            boolean partito = cella.getUO().isPartito();
//            selectable = !partito;
            selectable = true;  // modificato alex 06/2009 - tutte selectable

            Map map = cella.getAttributes();
            GraphConstants.setBounds(map, new Rectangle2D.Double(x, y, wCell, hCell));
            GraphConstants.setSelectable(map, selectable);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }

    /**
     * Ritorna la PreferredSize del grafo.
     * <p/>
     * Tiene conto del fattore di scala.
     * La dimensione del grafo determina l'attivazione delle scroll bars
     * nello scroll pane che lo contiene.
     * Le barre dei giorni e delle camere, non essendo contenute nel viewport
     * ma nei rowHeader/columnHeader, non concorrono all'attivazione
     * delle scroll bars.
     */
    public Dimension getPreferredSize() {
        /* variabili e costanti locali di lavoro */
        Dimension dim = null;
        int currW, currH;

        try { // prova ad eseguire il codice

            // l'altezza della barra risorse
        	currH = getPanGrafi().getBarRisorse().getPreferredSize().height;
            // la larghezza dalla barra tempo
            currW = getPanGrafi().getBarTempo().getPreferredSize().width;
            
            dim = new Dimension(currW, currH);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dim;
    }

    /**
     * E' stata eseguita una modifica (spostamento o ridimensionamento di una cella).
     * <p/>
     * Il mouse è già stato rilasciato.
     *
     * @param change oggetto descrittore della modifica
     */
    private void grafoModificato(GraphModelEvent.GraphModelChange change) {

        /* variabili e costanti locali di lavoro */
        boolean continua;
        CellPeriodoIF cell = null;
        AttributeMap oldMap;
        AttributeMap newMap = null;
        LineaPeriodo lineaAnte;
        LineaPeriodo lineaPost = null;
        LineaPeriodo lineaSnap = null;
        boolean spostata = false;
        boolean estremo = false;
        boolean rollback = false;
        Rectangle2D bounds;

        try {    // prova ad eseguire il codice

            /* recupera la mappa attributi precedente la modifica */
            oldMap = this.getOldAttributes(change);
            continua = (oldMap != null);

            /* recupera la nuova mappa attributi della cella */
            if (continua) {
                newMap = this.getNewAttributes(change);
                continua = (newMap != null);
            }// fine del blocco if

            /**
             * determina se la cella è stata ridimensionata o spostata
             * se è stata ridimensionata determina anche qual'è l'estremità
             * che è stata ridimensionata
             */
            if (continua) {
                lineaAnte = TableauLogica.getLineaCella(GraphConstants.getBounds(oldMap));
                lineaPost = TableauLogica.getLineaCella(GraphConstants.getBounds(newMap));
                estremo = false;
                spostata = TableauLogica.isSpostata(lineaAnte, lineaPost);
                if (!spostata) {
                    estremo = TableauLogica.qualeEstremoRidimensionato(lineaAnte, lineaPost);
                }// fine del blocco if
            }// fine del blocco if

            /* effettua uno snap al modulo del grafo */
            if (continua) {
                lineaSnap = TableauLogica.snapModuloGrafo(lineaPost, spostata, estremo);
            }// fine del blocco if

            /**
             * verifica che non si sovrapponga ad altri periodi
             * se si sovrappone accende il rollback
             */
            if (continua) {
                bounds = lineaSnap.getRectangle();
                CellPeriodoIF[] celle = TableauLogica.getCelleIntersecate(this, bounds);
                CellPeriodoIF cellMod = getCellaModificata(change);
                for (CellPeriodoIF cella : celle) {
                    if (!cella.equals(cellMod)) {
                        rollback = true;
                        break;
                    }// fine del blocco if
                }
            }// fine del blocco if

            /**
             * verifica che la larghezza sia di almeno 1 giorno
             * se no accende il rollback
             */
            if (continua) {
                if (!rollback) {
                    int giorni = lineaSnap.getQuantiGiorni();
                    if (giorni < 1) {
                        rollback = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* todo se ha dei cambi verifica che sia compatibile con i periodi attaccati */
            if (continua) {
                if (!rollback) {
//                    codice;
                }// fine del blocco if
            }// fine del blocco if

            /* posiziona definitivamente la cella o la rimette al posto di partenza */
            if (continua) {
                if (!rollback) {
                    bounds = TableauLogica.getBoundsCella(lineaSnap);
                } else {
                    bounds = GraphConstants.getBounds(oldMap);
                }// fine del blocco if-else

                cell = getCellaModificata(change);
                Map nested = new Hashtable();
                Map attributeMap1 = new Hashtable();
                GraphConstants.setBounds(attributeMap1, bounds);
                nested.put(cell, attributeMap1);
                this.getGraphLayoutCache().edit(nested, null, null, null);

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Recupera la cella modificata.
     * <p/>
     * (Si può modificare 1 sola cella per volta)
     *
     * @param change l'oggetto change da esaminare
     *
     * @return la cella modificata
     */
    private CellPeriodoIF getCellaModificata(GraphModelEvent.GraphModelChange change) {
        /* variabili e costanti locali di lavoro */
    	CellPeriodoIF cell = null;

        try {    // prova ad eseguire il codice
            Object[] changed = change.getChanged();
            if (changed != null) {
                if (changed.length == 1) {
                    Object ogg = changed[0];
                    if (ogg instanceof CellPeriodoIF) {
                        cell = (CellPeriodoIF)ogg;
                    }
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cell;
    }

    /**
     * Ritorna la cella correntemente selezionata.
     * <p/>
     * (Si può selezionare 1 sola cella per volta)
     *
     * @return la cella correntemente selezionata, null se nessuna selezionata
     */
    public CellPeriodoIF getCellaSelezionata() {
        /* variabili e costanti locali di lavoro */
    	CellPeriodoIF cell = null;

        try {    // prova ad eseguire il codice
            Object obj = this.getSelectionCell();
            if (obj != null) {
                if (obj instanceof CellPeriodoIF) {
                    cell = (CellPeriodoIF)obj;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cell;
    }

    /**
     * Recupera gli attributi della cella precedenti la modifica.
     * <p/>
     * Rappresenta una mappa di undo
     *
     * @param change l'oggetto change da esaminare
     *
     * @return gli attributi precedenti la modifica
     */
    private AttributeMap getOldAttributes(GraphModelEvent.GraphModelChange change) {
        /* variabili e costanti locali di lavoro */
        AttributeMap oldMap = null;
        //GraphCell cell;
        Object obj;

        try {    // prova ad eseguire il codice
            Map mappa = change.getAttributes();
            CellPeriodoIF cell = this.getCellaModificata(change);
            if (cell!=null) {
                GraphCell gCell = cell.getGraphCell();
                if (gCell != null) {
                    obj = mappa.get(gCell);
                    if (obj != null) {
                        if (obj instanceof AttributeMap) {
                            oldMap = (AttributeMap)obj;
                        }
                    }// fine del blocco if
                }// fine del blocco if
			}
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return oldMap;
    }

    /**
     * Recupera gli attributi della cella posteriori la modifica.
     * <p/>
     *
     * @param change l'oggetto change da esaminare
     *
     * @return gli attributi posteriori la modifica
     */
    private AttributeMap getNewAttributes(GraphModelEvent.GraphModelChange change) {
        /* variabili e costanti locali di lavoro */
        AttributeMap oldMap = null;
        //GraphCell cell;
        Object obj;

        try {    // prova ad eseguire il codice
            Map mappa = change.getPreviousAttributes();
            CellPeriodoIF cell = this.getCellaModificata(change);
            if (cell!=null) {
            	GraphCell gCell = cell.getGraphCell();
                if (gCell != null) {
                    obj = mappa.get(gCell);
                    if (obj != null) {
                        if (obj instanceof AttributeMap) {
                            oldMap = (AttributeMap)obj;
                        }
                    }// fine del blocco if
                }// fine del blocco if
			}
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return oldMap;
    }

    /**
     * Recupera il punto estremo centrale sinistro
     * o destro di una cella di periodo.
     * <p/>
     *
     * @param map la mappa attribuiti della cella
     * @param start false per l'estremo sinistro, true per l'estremo destro
     */
    private Point getEstremoCella(AttributeMap map, boolean start) {
        /* variabili e costanti locali di lavoro */
        Point punto = null;
        int xe;

        try {    // prova ad eseguire il codice

            Rectangle2D bounds = GraphConstants.getBounds(map);
            int x1 = (int)bounds.getMinX();
            int y1 = (int)bounds.getMinY();
            int x2 = (int)bounds.getMaxX();
            int y2 = (int)bounds.getMaxY();

            if (!start) {  //sinistro
                xe = x1;
            } else {
                xe = x2;  //destro
            }// fine del blocco if-else
            int ye = y1 + ((y2 - y1) / 2);

            punto = new Point(xe, ye);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return punto;
    }

    /**
     * Apre per modifica la prenotazione relativa a una cella.
     * <p/>
     *
     * @param cella cella interessata
     *
     * @return true se la prenotazione è stata modificata
     */
    public boolean apriPrenotazione(CellPeriodoIF cella) {
        /* variabili e costanti locali di lavoro */
        boolean registrato = false;

        /* recupera il cod prenotazione */
        int codPeriodo = cella.getCodicePeriodo();
        int codPrenotazione = PeriodoModulo.get().query().valoreInt(Periodo.Cam.prenotazione.get(), codPeriodo);
        
        if (codPrenotazione>0) {
			
            /* edita la prenotazione */
        	Tableau tableau = this.getPanGrafi().getTableau();
        	tableau.apriPrenotazione(codPrenotazione, new EditingFinishedListener());


		}

        /* valore di ritorno */
        return registrato;
    }
    
    private class EditingFinishedListener implements OnEditingFinished{

		@Override
		public void onEditingFinished(Modulo modulo, int id, boolean confirmed) {
			updateAfterEdit(id, confirmed);
		}
    	
    }
    
    /**
     *  ricarica tutte le celle che possono essere state modificate 
     * dall'utente lavorando nella prenotazione 
     */
    private void updateAfterEdit(int codPrenotazione, boolean registrato){
        if (registrato) {
        	
        	int[] idRecordSorgenti = this.getPanGrafi().getTableau().getIdRecordSorgenti(codPrenotazione);
            if (idRecordSorgenti != null) {

                for (int id : idRecordSorgenti) {

                    /* rimuove */
                	CellPeriodoIF unaCella = this.getMappaPeriodi().get(id);
                    if (unaCella != null) {
                        this.removeCell(unaCella);
                    }// fine del blocco if

                    /* aggiunge */
                    int idTipoRisorsa = this.getPanGrafi().getTableau().getTipoRisorseSelezionato().getId();
                    unaCella = this.getPanGrafi().getTableau().getCellaPeriodo(idTipoRisorsa, id);
                    this.insertCell(unaCella);

                }
            }// fine del blocco if
        }// fine del blocco if

    }

    public PanGrafi getPanGrafi() {
        return panGrafi;
    }

    private void setPanGrafi(PanGrafi panGrafi) {
        this.panGrafi = panGrafi;
    }

    private ArrayList<CellGriglia> getCelleGriglia() {
        return celleGriglia;
    }

    private void setCelleGriglia(ArrayList<CellGriglia> celleGriglia) {
        this.celleGriglia = celleGriglia;
    }

    private HashMap<Integer, CellPeriodoIF> getMappaPeriodi() {
        return mappaPeriodi;
    }

    private void setMappaPeriodi(HashMap<Integer, CellPeriodoIF> mappaPeriodi) {
        this.mappaPeriodi = mappaPeriodi;
    }

    private HighlightCross getHighlightCross() {
        return highlightCross;
    }

    private void setHighlightCross(HighlightCross cross) {
        this.highlightCross = cross;
    }

    private GraphCell getCellaOggi() {
        return cellaOggi;
    }

    private void setCellaOggi(GraphCell cellaOggi) {
        this.cellaOggi = cellaOggi;
    }

    /**
     * Tipi di linea disponibili la griglia.
     * </p>
     */
    enum TipoLinea {

        feriale(COLORE_GRIGLIA_INFRASET, 1),
        weekend(COLORE_GRIGLIA_WEEKEND, 1),
        mese(COLORE_GRIGLIA_MESE, 2),
        camera(COLORE_GRIGLIA_CAMERE, 1);

        private Color colore;   // colore della riga

        private int spessore; // spessore della riga

        TipoLinea(Color colore, int spessore) {
            this.colore = colore;
            this.spessore = spessore;
        }

        public Color getColore() {
            return colore;
        }

        public int getSpessore() {
            return spessore;
        }

    }

    /**
     * ViewFactory per le celle di tipo Periodo.
     * </p>
     */
    private class PeriodoViewFactory extends DefaultCellViewFactory {

        protected VertexView createVertexView(Object cell) {
            /* variabili e costanti locali di lavoro */
            VertexView view = null;

            if (cell instanceof CellPeriodoIF) {
                view = new CellViewPeriodo(cell);
            }// fine del blocco if

            if (cell instanceof CellNuovo) {
                view = new CellViewNuovo(cell);
            }// fine del blocco if

            if (view == null) {
                view = new CellViewPeriodo(cell); //default
            }// fine del blocco if

            /* valore di ritorno */
            return view;
        }

    } // fine della classe CameraViewFactory

    /**
     * Listener delle modifiche alle celle del grafo
     * </p>
     */
    private class AzGrafoModificato implements GraphModelListener {

        public void graphChanged(GraphModelEvent event) {

            grafoModificato(event.getChange());
        }

    } // fine della classe 'interna'

    /**
     * Croce di evidenza del punto selezionato
     * </p>
     */
    private class HighlightCross {

        private int row, col; // cella di centro della croce

        private GraphCell braccioVert; // cella per il braccio verticale

        private GraphCell braccioOrizz; // cella per il braccio orizzontale

        /**
         * Costruisce una croce alle coordinate fornite.
         * <br>
         *
         * @param row la riga del grafo (0 per la prima)
         * @param col la colonna del grafo (0 per la prima)
         */
        public HighlightCross(int row, int col) {
            /* rimanda al costruttore della superclasse */
            super();

            this.row = row;
            this.col = col;
            this.inizia();

        }// fine del metodo costruttore semplice

        /**
         * Metodo chiamato direttamente dal costruttore
         */
        private void inizia() {
            this.braccioVert = this.creaBraccio(true);
            this.braccioOrizz = this.creaBraccio(false);
        }

        /**
         * Crea un braccio della croce.
         * <p/>
         *
         * @param vert true per verticale false per orizzontale
         *
         * @return il braccio creato
         */
        private GraphCell creaBraccio(boolean vert) {
            /* variabili e costanti locali di lavoro */
            GraphCell braccio = new DefaultGraphCell();
            int x, y, w, h;
            AttributeMap map;
            int quanti;

            try {    // prova ad eseguire il codice

                if (vert) {

                    quanti = getPanGrafi().getNumRisorse();

                    x = col * Tableau.H_MODULE;
                    y = 0;
                    w = Tableau.H_MODULE;
                    h = quanti * Tableau.V_MODULE;

                } else {

                    quanti = getPanGrafi().getNumGiorni();

                    x = 0;
                    y = row * Tableau.H_MODULE;
                    w = quanti * Tableau.V_MODULE;
                    h = Tableau.H_MODULE;

                }// fine del blocco if-else

                map = braccio.getAttributes();
                GraphConstants.setBounds(map, new Rectangle2D.Double(x, y, w, h));
                GraphConstants.setBackground(map, COLORE_GRIGLIA_HIGHLIGHT);
                GraphConstants.setOpaque(map, true);
                GraphConstants.setSelectable(map, false);

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return braccio;

        }

        /* rimuove dal grafo la croce corrente */

        public void remove() {
            GraphLayoutCache cache = getGraphLayoutCache();
            if (cache != null) {
                cache.remove(this.getObjectArray());
            }// fine del blocco if
        }

        /* ritorna un object array con le due celle */

        public Object[] getObjectArray() {
            Object[] objArr = new Object[2];
            objArr[0] = braccioVert;
            objArr[1] = braccioOrizz;
            return objArr;
        }

        /**
         * Ritorna la riga (posizione del braccio orizzontale)
         * <p/>
         *
         * @return la riga
         */
        public int getRow() {
            return row;
        }

        /**
         * Ritorna la colonna (posizione del braccio verticale)
         * <p/>
         *
         * @return la colonna
         */
        public int getCol() {
            return col;
        }

    } // fine della classe 'interna'

}// fine della classe