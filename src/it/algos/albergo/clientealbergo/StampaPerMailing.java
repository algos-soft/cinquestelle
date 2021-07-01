/**
 * Title:     StampaConIndirizzo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      4-set-2009
 */
package it.algos.albergo.clientealbergo;

import com.wildcrest.j2printerworks.J2TablePrinter;
import it.algos.albergo.clientealbergo.indirizzoalbergo.IndirizzoAlbergo;
import it.algos.albergo.clientealbergo.indirizzoalbergo.IndirizzoAlbergoModulo;
import it.algos.albergo.clientealbergo.tabelle.parente.Parentela;
import it.algos.albergo.clientealbergo.tabelle.parente.ParentelaModulo;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.tabelle.lingua.Lingua;
import it.algos.albergo.tabelle.lingua.LinguaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.stampa.Printer;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.tabelle.titolo.Titolo;
import it.algos.gestione.anagrafica.tabelle.titolo.TitoloModulo;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Stampa di una lista di clienti
 * ai fini di mailing
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 4-set-2009 ore 10.25.15
 */
public final class StampaPerMailing extends Printer {

    private Filtro filtro;

    private Ordine ordine;

    private Dati dati;

    private Campo campoChiave = ClienteAlbergoModulo.get().getCampoChiave();
    private Campo campoTitolo = TitoloModulo.get().getCampo(Titolo.Cam.sigla);
    private Campo campoCognome = ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.cognome);
    private Campo campoNome = ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.nome);
    private Campo campoPrivatoSocieta = ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.privatosocieta);
    private Campo campoParentela = ParentelaModulo.get().getCampo(Parentela.Cam.sigla);
    private Campo campoCorrispondenza = ClienteAlbergoModulo.get().getCampo(ClienteAlbergo.Cam.checkPosta);
    private Campo campoEmail = ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.email);
    private Campo campoLingua = LinguaModulo.get().getCampo(Lingua.Cam.sigla);
    private Campo campoTelefono = ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.telefono);
    private Campo campoCellulare = ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.cellulare);

    /* font per il corpo testo */
    private Font fontTesto = FontFactory.creaPrinterFont(8f);


    /**
     * Costruttore base senza parametri.
     * <p/>
     *
     * @param filtro per isolare i record da stampare
     * @param ordine di presentazione dei record
     */
    public StampaPerMailing(Filtro filtro, Ordine ordine) {
        this.setFiltro(filtro);
        this.setOrdine(ordine);
        this.inizia();
    }// fine del metodo costruttore base


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     */
    private void inizia() {
        /* variabili e costanti locali di lavoro */
        Dati dati;
        Modulo modCliente;
        Query query;

        TableColumn col;

        try { // prova ad eseguire il codice

            /* impostazioni generali */
            this.setCenterHeader("Elenco Clienti");
            this.setOrientation(SwingConstants.HORIZONTAL);

            modCliente = ClienteAlbergoModulo.get();

            query = new QuerySelezione(modCliente);
            query.addCampo(campoChiave);
            query.addCampo(campoTitolo);
            query.addCampo(campoNome);
            query.addCampo(campoCognome);
            query.addCampo(campoPrivatoSocieta);
            query.addCampo(campoParentela);
            query.addCampo(campoCorrispondenza);
            query.addCampo(campoEmail);
            query.addCampo(campoLingua);
            query.addCampo(campoTelefono);
            query.addCampo(campoCellulare);

            query.setFiltro(this.getFiltro());
            query.setOrdine(this.getOrdine());

            dati = modCliente.query().querySelezione(query);
            this.setDati(dati);

            /* crea e regola una JTable vuota */
            JTable tavola = new JTable();
            tavola.setBackground(Color.white);
            tavola.setIntercellSpacing(new Dimension(5, 5));
            tavola.setRowHeight(16);
            tavola.setGridColor(Color.BLACK);
            tavola.getTableHeader().setOpaque(false);
            tavola.getTableHeader().setFont(FontFactory.creaPrinterFont(Font.BOLD, 9f));
            tavola.getTableHeader().setForeground(Color.black);
            tavola.getTableHeader().setBackground(Color.white);

            /* assegna un modello fittizio collegato ai dati */
            tavola.setModel(new ModelloTavola());

            /* colonna Titolo */
            col = this.creaColonna("Tit",40,new RendererTitolo());
            tavola.addColumn(col);

            /* colonna Soggetto */
            col = this.creaColonna("Cliente",150,new RendererCliente());
            tavola.addColumn(col);

            /* colonna Parentela */
            col = this.creaColonna("Par",30,new RendererParentela());
            tavola.addColumn(col);

            /* colonna Corrispondenza */
            col = this.creaColonna("Corr",30,new RendererCorrispondenza());
            tavola.addColumn(col);

            /* colonna Email */
            col = this.creaColonna("Email",160,new RendererEmail());
            tavola.addColumn(col);

            /* colonna Telefono */
            col = this.creaColonna("Telefono",100,new RendererTelefono());
            tavola.addColumn(col);

            /* colonna Cellulare */
            col = this.creaColonna("Cellulare",100,new RendererCellulare());
            tavola.addColumn(col);

            /* colonna Lingua */
            col = this.creaColonna("Ling",30,new RendererLingua());
            tavola.addColumn(col);

            /* colonna Indirizzo */
            col = this.creaColonna("Indirizzo",290,new RendererIndirizzo());
            tavola.addColumn(col);

            /* colonna Ultimo Soggiorno */
            col = this.creaColonna("Ult. Sogg.",50,new RendererUltimoSoggiorno());
            tavola.addColumn(col);




            /* crea e regola un TablePrinter */
            J2TablePrinter prn = new J2TablePrinter(tavola);
            prn.setColumnHeaderPrinting(J2TablePrinter.ALL_PAGES);
            prn.setHorizontalAlignment(J2TablePrinter.CENTER);
            prn.setHorizontalPageRule(J2TablePrinter.SHRINK_TO_FIT);
            prn.setOverlap(true);
            prn.setMaximumPaginationGap(1.0);// non spezza mai le righe su due pagine

            /* aggiunge il table printer a questo printer */
            this.addPageable(prn);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea una colonna di stampa.
     * <p/>
     * @param titolo della colonna
     * @param larghezza della colonna
     * @param renderer della colonna
     * @return la colonna creata
     */
    private TableColumn creaColonna (String titolo, int larghezza, RendererColonna renderer) {
        /* variabili e costanti locali di lavoro */
        TableColumn col=null;

        try {    // prova ad eseguire il codice
            col = new TableColumn();
            col.setHeaderValue(titolo);
            col.setMinWidth(larghezza);
            col.setMaxWidth(larghezza);
            col.setCellRenderer(renderer);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return col;
    }





    /**
     * Chiude i dati utilizzati.
     * <p/>
     */
    public void close() {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;

        try {    // prova ad eseguire il codice
            dati = this.getDati();
            if (dati != null) {
                dati.close();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    private Filtro getFiltro() {
        return filtro;
    }


    private void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }


    private Ordine getOrdine() {
        return ordine;
    }


    private void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }


    private Dati getDati() {
        return dati;
    }


    private void setDati(Dati dati) {
        this.dati = dati;
    }


    private abstract class RendererColonna extends JLabel implements TableCellRenderer {

        public RendererColonna() {
            this.setFont(fontTesto);
        }


        public Component getTableCellRendererComponent(
                JTable table, Object color,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            /* variabili e costanti locali di lavoro */
            String testo = this.getTextValue(row);
            this.setText(testo);

            return this;
        }


        /**
         * Sovrascritto dalle sottoclassi.
         * <p/>
         */
        protected String getTextValue(int row) {
            return "";
        }


    }


    private class RendererTitolo extends RendererColonna {
        protected String getTextValue(int row) {
            return dati.getStringAt(row, campoTitolo);
        }
    }

    private class RendererCliente extends RendererColonna {
        protected String getTextValue(int row) {
            /* variabili e costanti locali di lavoro */
            String stringa="";
            int codTipo;
            Anagrafica.Tipi tipo;

            codTipo = dati.getIntAt(row, campoPrivatoSocieta);
            tipo = Anagrafica.Tipi.getTipo(codTipo);

            switch (tipo) {
                case privato:
                    String cognome, nome;
                    cognome = dati.getStringAt(row, campoCognome);
                    nome = dati.getStringAt(row, campoNome);
                    stringa = Lib.Testo.concatSpace(cognome, nome);
                    break;
                case societa:
                    String societa;
                    societa = dati.getStringAt(row, campoCognome);
                    stringa = societa;
                    break;
                default : // caso non definito
                    break;
            } // fine del blocco switch

            /* valore di ritorno */
            return stringa;
        }
    }

    private class RendererParentela extends RendererColonna {
        protected String getTextValue(int row) {
            return dati.getStringAt(row, campoParentela);
        }
    }

    private class RendererCorrispondenza extends RendererColonna {
        protected String getTextValue(int row) {
            String stringa="";
            boolean flag;
            flag = dati.getBoolAt(row, campoCorrispondenza);
            if (flag) {
                stringa="X";
            }// fine del blocco if
            return stringa;
        }
    }

    private class RendererEmail extends RendererColonna {
        protected String getTextValue(int row) {
            return dati.getStringAt(row, campoEmail);
        }
    }

    private class RendererLingua extends RendererColonna {
        protected String getTextValue(int row) {
            return dati.getStringAt(row, campoLingua);
        }
    }


    private class RendererIndirizzo extends RendererColonna {
        protected String getTextValue(int row) {
            String stringa = "";
            int codCli = getDati().getIntAt(row, 0);
            if (codCli > 0) {
                int codIndir = ClienteAlbergoModulo.getCodIndirizzo(codCli);
                EstrattoBase est = IndirizzoAlbergoModulo.get()
                        .getEstratto(IndirizzoAlbergo.Est.indirizzoRiga, codIndir);
                stringa = est.getStringa();
            }// fine del blocco if

            return stringa;
        }
    }

    private class RendererUltimoSoggiorno extends RendererColonna {
        protected String getTextValue(int row) {
            String stringa = "";
            int codCli = getDati().getIntAt(row, 0);
            if (codCli > 0) {
                Date data = PresenzaModulo.getDataUltimoSoggiorno(codCli);
                if (Lib.Data.isValida(data)) {
                    SimpleDateFormat df = Progetto.getDateFormat();
                    stringa = df.format(data);
                }// fine del blocco if
            }// fine del blocco if
            return stringa;
        }
    }

    private class RendererTelefono extends RendererColonna {
        protected String getTextValue(int row) {
            return dati.getStringAt(row, campoTelefono);
        }
    }

    private class RendererCellulare extends RendererColonna {
        protected String getTextValue(int row) {
            return dati.getStringAt(row, campoCellulare);
        }
    }



    /**
     * Modello dati solo per far credere alla tavola di avere
     * tante righe quante ce ne sono nei dati
     */
    private class ModelloTavola extends DefaultTableModel {

        @Override
        public int getRowCount() {
            return dati.getRowCount();
        }


        @Override
        public Object getValueAt(int i, int i1) {
            return "";
        }
    } // fine della classe 'interna'

}// fine della classe
