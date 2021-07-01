/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      13-giu-2006
 */
package it.algos.albergo.conto.addebitofisso;

import it.algos.albergo.listino.Listino;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.costante.CostanteColore;
import it.algos.base.errore.Errore;
import it.algos.base.evento.campo.CampoMemoriaAz;
import it.algos.base.evento.campo.CampoMemoriaEve;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioErrore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.wrapper.WrapListino;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;

/**
 * Pannello di selezione degli addebiti fissi.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 13-giu-2006 ore 10.47.10
 */
public final class AddebitoFissoPannello extends PannelloFlusso {

    private ArrayList<Riga> righe;

    /* numero di persone da considerare per la proposta della quantità */
    private int numPersone;

    private Pannello panRighe;

    /* data di riferimento per i prezzi */
    private Date dataPrezzi;

    /* campo totale prezzi */
    private Campo campoTotale;

    /* etichetta campo totale prezzi */
    private JLabel etiTotale;


    /**
     * Costruttore completo
     * <p/>
     */
    public AddebitoFissoPannello() {
        this(null);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo
     * <p/>
     *
     * @param dataPrezzi data di riferimento per visualizzare i prezzi
     * se omessa non visualizza i prezzi
     */
    public AddebitoFissoPannello(Date dataPrezzi) {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_VERTICALE);

        this.setDataPrezzi(dataPrezzi);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JScrollPane scorrevole;
        Pannello panRighe;
        Campo campoTotale;
        JLabel etiTotale;
        Pannello pan;
        Pannello panTotale;

        try { // prova ad eseguire il codice

            this.setGapPreferito(3);
//            this.setGapMassimo(0);

            /* crea il bordo con titolo */
            this.regolaBordo();

            this.setRighe(new ArrayList<Riga>());

            /* crea il pannello delle righe e lo registra */
            panRighe = PannelloFactory.verticale(null);
            this.setPanRighe(panRighe);
            panRighe.setGapMinimo(0);
            panRighe.setGapPreferito(2);
            panRighe.setGapMassimo(3);

            /* Crea le righe e le aggiunge al pannello delle righe */
            this.creaRighe();

            /* crea uno scorrevole contenente il pannello delle righe */
            scorrevole = new JScrollPane(this.getPanRighe().getPanFisso());
            scorrevole.setOpaque(false);
            scorrevole.getViewport().setOpaque(false);
            scorrevole.setPreferredSize(new Dimension(0, 300));
            scorrevole.setBorder(null);
            scorrevole.setPreferredSize(panRighe.getPreferredSize());

            /* crea il campo totale */
            campoTotale = CampoFactory.valuta("totale");
            this.setCampoTotale(campoTotale);
            campoTotale.setAllineamento(SwingConstants.RIGHT);
            campoTotale.decora().eliminaEtichetta();
            campoTotale.setLarScheda(60);
            campoTotale.avvia();
            campoTotale.setAbilitato(false);

            /* crea l'etichetta per il campo totale */
            etiTotale = new JLabel();
            this.setEtiTotale(etiTotale);

            /* crea il pannello per etichetta e totale */
            pan = PannelloFactory.orizzontale(null);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.setGapMassimo(10);
            pan.add(etiTotale);
            pan.add(campoTotale);

            /* crea il pannello per il totale */
            panTotale = PannelloFactory.verticale(null);
            panTotale.setAllineamento(Layout.ALLINEA_DX);
            panTotale.sbloccaLarMax();
            panTotale.add(pan);

            /* aggiunge lo scorrevole a questo pannello */
            this.add(scorrevole);

            /* aggiunge il pannello totale a questo pannello */
            this.add(panTotale);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    /**
     * Regola il titolo del bordo del pannello.
     * <p/>
     * Assegna un bordo con titolo al pannello
     */
    private void regolaBordo() {
        /* variabili e costanti locali di lavoro */
        Date data;
        String stringa = null;
        String titolo;


        try {    // prova ad eseguire il codice

            data = this.getDataPrezzi();
            if (data != null) {
                stringa = Lib.Data.getStringa(data);
            }// fine del blocco if

            titolo = "addebiti giornalieri di pensione";
            if (Lib.Testo.isValida(stringa)) {
                titolo += " (dal " + stringa + ")";
            }// fine del blocco if

            /* crea un bordo con titolo rosso */
            Border bordo = this.creaBordo(titolo);
            if (bordo instanceof CompoundBorder) {
                CompoundBorder cBordo = (CompoundBorder)bordo;
                Border outBordo = cBordo.getOutsideBorder();
                if (outBordo instanceof TitledBorder) {
                    TitledBorder tBordo = (TitledBorder)outBordo;
                    tBordo.setTitleColor(CostanteColore.ROSSO);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea le righe e le aggiunge al pannello delle righe.
     * </p>
     */
    private void creaRighe() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> lista;
        Riga riga;
        ListinoModulo mod;


        try { // prova ad eseguire il codice

            /* lista delle righe di listino disponibili per addebiti giornalieri */
            mod = ListinoModulo.get();
            lista = mod.getGiornalieri();

            /* traverso tutta la collezione */
            for (int cod : lista) {

                riga = new Riga(cod);
                this.getRighe().add(riga);
                this.getPanRighe().add(riga);

            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Ritorna la lista delle righe con check attivato.
     * <p/>
     *
     * @return la lista delle righe correntemente spuntate
     */
    public ArrayList<Riga> getRigheSelezionate() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Riga> righeSelezionate = null;
        ArrayList<Riga> righe;

        try {    // prova ad eseguire il codice
            righe = this.getRighe();
            righeSelezionate = new ArrayList<Riga>();
            /* traverso tutta la collezione */
            for (Riga riga : righe) {
                if (riga.isSelezionata()) {
                    righeSelezionate.add(riga);
                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return righeSelezionate;
    }


    /**
     * Controlla se e' il listino e' in grado di fornire
     * dati completi e congrui per le righe attualmente
     * selezionate e per il periodo specificato.
     * <p/>
     * Se non e' in grado visualizza un messaggio di errore.
     *
     * @param d1 data inizio periodo
     * @param d2 data fine periodo
     *
     * @return true se il listino e' in grado di fornire i dati.
     */
    public boolean checkListino(Date d1, Date d2) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        ListinoModulo modListino;
        int codListino;
        String descListino;
        ArrayList<WrapListino> lista;
        String errore;
        ArrayList<String> errori;

        try {    // prova ad eseguire il codice

            modListino = ListinoModulo.get();
            errori = new ArrayList<String>();

            /* traverso tutta la collezione delle righe selezionate nel pannello
             * e per ognuna richiedo al listino la lista dei prezzi per il periodo */
            for (Riga riga : this.getRigheSelezionate()) {

                codListino = riga.getCodListino();
                lista = ListinoModulo.getPrezzi(codListino, d1, d2);

                /* se la lista e' nulla il listino ha fallito
                 * aggiungo il messaggio di errore */
                if (lista == null) {
                    continua = false;
                    descListino = modListino.query().valoreStringa(Listino.Cam.descrizione.get(),
                            codListino);
                    errori.add(descListino);
                }// fine del blocco if

            } // fine del ciclo for-each

            /* se fallito visualizza l'errore */
            if (!continua) {

                errore =
                        "Il listino non e' in grado di fornire i prezzi" +
                                "\nper il periodo richiesto!";
                errore += "\n\n";
                errore += "Listini incompleti o errati:";

                for (String stringa : errori) {
                    errore += "\n- " + stringa;
                }

                errore += "\n\n";
                errore += "Controllare nel listino:";
                errore += "\n- che tutto il periodo richiesto sia coperto";
                errore += "\n- che non ci siano periodi sovrapposti";
                errore += "\n";

                new MessaggioErrore(errore);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Aggiorna i totali delle righe del Pannello Servizi
     * <p/>
     */
    private void refreshTotali() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Riga> righe;
        String testo = "";
        Date data;
        JLabel label;

        try {    // prova ad eseguire il codice

            /* rinfresca ogni riga */
            righe = this.getRighe();
            if (righe != null) {
                for (Riga riga : righe) {
                    riga.refreshTotale();
                }
            }// fine del blocco if

            /* rinfresca l'etichetta del totale */
            data = this.getDataPrezzi();
            if (!Lib.Data.isVuota(data)) {
                testo = "Totale del giorno " + Lib.Data.getStringa(data) + ":";
            }// fine del blocco if
            label = this.getEtiTotale();
            if (label != null) {
                label.setText(testo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il totale prezzo delle righe selezionate.
     * <p/>
     *
     * @return il prezzo totale
     */
    private double getTotaleRighe() {
        /* variabili e costanti locali di lavoro */
        double totale = 0d;
        ArrayList<Riga> righe;

        try {    // prova ad eseguire il codice
            /* rinfresca ogni riga */
            righe = this.getRighe();
            if (righe != null) {
                for (Riga riga : righe) {
                    totale += riga.getTotale();
                }
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }


    /**
     * Ritorna il totale prezzo delle righe selezionate.
     * <p/>
     *
     * @return il prezzo totale
     */
    private double regolaTotale() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        double totale = 0d;

        try {    // prova ad eseguire il codice
            campo = this.getCampoTotale();
            if (campo != null) {
                totale = this.getTotaleRighe();
                campo.setValore(totale);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }


    public ArrayList<Riga> getRighe() {
        return righe;
    }


    private void setRighe(ArrayList<Riga> righe) {
        this.righe = righe;
    }


    public int getNumPersone() {
        return numPersone;
    }


    public void setNumPersone(int numPersone) {
        this.numPersone = numPersone;
    }


    private Pannello getPanRighe() {
        return panRighe;
    }


    private void setPanRighe(Pannello panRighe) {
        this.panRighe = panRighe;
    }


    private Date getDataPrezzi() {
        return dataPrezzi;
    }


    public void setDataPrezzi(Date dataPrezzi) {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            this.dataPrezzi = dataPrezzi;
            this.regolaBordo();
            this.refreshTotali();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private Campo getCampoTotale() {
        return campoTotale;
    }


    private void setCampoTotale(Campo campoTotale) {
        this.campoTotale = campoTotale;
    }


    private JLabel getEtiTotale() {
        return etiTotale;
    }


    private void setEtiTotale(JLabel etiTotale) {
        this.etiTotale = etiTotale;
    }


    public void setQuantitaModificabili(boolean quantitaModificabili) {
        ArrayList<Riga> righe;
        Campo campo;

        righe = this.getRighe();
        /* traverso tutta la collezione */
        for (Riga riga : righe) {
            campo = riga.getCampoQuantita();
            campo.setModificabile(quantitaModificabili);
        } // fine del ciclo for-each
    }


    /**
     * Singola riga di impostazione addebito fisso
     * </p>
     */
    public final class Riga extends PannelloFlusso {

        /* codice di listino della riga */
        private int codListino;

        /* campo check */
        private Campo campoCheck;

        /* campo quantita' della riga */
        private Campo campoQuantita;

        /* campo totale della riga */
        private Campo campoTotale;


        /**
         * Costruttore completo con parametri.
         *
         * @param codListino codice del listino
         */
        public Riga(int codListino) {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_ORIZZONTALE);

            /* regola le variabili di istanza coi parametri */
            this.setCodListino(codListino);

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Regolazioni statiche di riferimenti e variabili.
         * </p>
         * Metodo invocato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            this.setAllineamento(Layout.ALLINEA_CENTRO);
            this.setGapMinimo(2);
            this.setGapPreferito(5);
            this.setGapMassimo(10);
            this.setRidimensionaComponenti(false);
            this.getLayoutAlgos().setConsideraComponentiInvisibili(true);
            this.regola();

        }


        /**
         * Costruisce la riga grafica.
         * <p/>
         */
        private void regola() {
            int cod;
            String descrizione;
            Campo campoCheck;
            Campo campoQuantita;
            Campo campoTotale;
            ListinoModulo mod;

            try { // prova ad eseguire il codice

                /* recupera la descrizione del listino */
                cod = this.getCodListino();
                mod = ListinoModulo.get();
                descrizione = mod.query().valoreStringa(Listino.Cam.descrizione.get(), cod);

                /* crea il campo check con la descrizione */
                campoCheck = CampoFactory.checkBox("campoA" + cod);
                this.setCampoCheck(campoCheck);
                campoCheck.setTestoComponente(descrizione);
                campoCheck.setLarScheda(260);
                campoCheck.avvia();

                /* crea il campo quantita' */
                campoQuantita = CampoFactory.intero("campoB" + cod);
                this.setCampoQuantita(campoQuantita);
                campoQuantita.decora().eliminaEtichetta();
                campoQuantita.setLarScheda(20);
                campoQuantita.setVisibile(false);
                campoQuantita.avvia();

                /* crea il campo totale */
                campoTotale = CampoFactory.valuta("totale" + cod);
                this.setCampoTotale(campoTotale);
                campoTotale.setAbilitato(false);
                campoTotale.setAllineamento(SwingConstants.RIGHT);
                campoTotale.decora().eliminaEtichetta();
                campoTotale.setLarScheda(60);
                campoTotale.setVisibile(false);
                campoTotale.avvia();

                /* aggiunge gli ascoltatori ai campi modificabili  */
                campoCheck.addListener(new CampoModificato());
                campoQuantita.addListener(new CampoModificato());

                /* aggiunge graficamente i campi */
                this.add(campoCheck);
                this.add(campoQuantita);
                this.add(campoTotale);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Azione di modifica del valore di un campo
         */
        private class CampoModificato extends CampoMemoriaAz {

            public void campoMemoriaAz(CampoMemoriaEve unEvento) {
                Campo campo;
                campo = unEvento.getCampo();

                if (campo.equals(campoCheck)) {
                    checkModificato();
                }// fine del blocco if

                if (campo.equals(campoQuantita)) {
                    quantitaModificata();
                }// fine del blocco if
            }
        } // fine della classe interna


        /**
         * Campo check modificato.
         * <p/>
         * Se acceso regola la quantita' in base al sottoconto e alla camera
         * Se spento pone la quantita' a zero
         * Regola la visibilita' dei campi
         */
        private void checkModificato() {
            /* variabili e costanti locali di lavoro */
            boolean selezionato;
            int quantita = 0;

            try { // prova ad eseguire il codice

                /* recupera il valore del campo check */
                selezionato = (Boolean)campoCheck.getValore();

                /* regola la quantita' */
                if (selezionato) {
                    quantita = this.getQuantitaProposta();
                }// fine del blocco if-else
                this.setQuantita(quantita);

                /* rende il camp quantita' visibile o invisibile */
                getCampoQuantita().setVisibile(selezionato);

                /* aggiorna il valore e la visibilita' del totale */
                this.refreshTotale();

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Campo quantita' modificato.
         * <p/>
         * Ricalcola il totale
         */
        private void quantitaModificata() {
            try { // prova ad eseguire il codice
                this.refreshTotale();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Recupera la quantita' proposta per la riga.
         * <p/>
         *
         * @return la quantita' proposta
         */
        private int getQuantitaProposta() {
            /* variabili e costanti locali di lavoro */
            int qtaProposta = 0;
            boolean continua = true;
            int codListino;

            try {    // prova ad eseguire il codice

                /* controlla se il prezzo del listino e' a persona */
                if (continua) {
                    codListino = this.getCodListino();
                    continua = Listino.TipoPrezzo.isPerPersona(codListino);
                }// fine del blocco if

                /* recupera il numero di persone */
                if (continua) {
                    qtaProposta = getNumPersone();
                }// fine del blocco if

                /* se la q.ta proposta e' zero la pone automaticamente a 1*/
                if (qtaProposta == 0) {
                    qtaProposta = 1;
                }// fine del blocco if


            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return qtaProposta;
        }


        /**
         * Esegue il refresh del totale della riga
         * <p/>
         * Ricalcola il totale in base alla data di riferimento
         * rende visibile o invisibile il campo totale
         */
        public void refreshTotale() {
            /* variabili e costanti locali di lavoro */
            int quantita;
            double prezzo = 0d;
            double totale = 0d;
            int codListino;
            Date data;
            boolean totVisibile = false;

            try {    // prova ad eseguire il codice

                /* ricalcola il totale */
                codListino = this.getCodListino();
                data = getDataPrezzi(); // dalla classe esterna
                if (!Lib.Data.isVuota(data)) {
                    prezzo = ListinoModulo.getPrezzo(codListino, data);
                }// fine del blocco if
                quantita = this.getQuantita();
                totale = quantita * prezzo;
                campoTotale.setValore(totale);

                /* regola la visibilita' del campo totale */
                /* il campo totale e' visibile solo se la riga e' selezionata
                 * e c'e' una data di riferimento */
                if (this.isSelezionata()) {
                    if (!Lib.Data.isVuota(getDataPrezzi())) {
                        totVisibile = true;
                    }// fine del blocco if
                }// fine del blocco if
                getCampoTotale().setVisibile(totVisibile);

                /* il campo totale non e' mai abilitato */
                getCampoTotale().setAbilitato(false);

                /* invoca il metodo della classe esterna che deve regolare il totale */
                regolaTotale();

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

        }


        public int getCodListino() {
            return codListino;
        }


        private void setCodListino(int codListino) {
            this.codListino = codListino;
        }


        private Campo getCampoCheck() {
            return campoCheck;
        }


        private void setCampoCheck(Campo campoCheck) {
            this.campoCheck = campoCheck;
        }


        public Campo getCampoQuantita() {
            return campoQuantita;
        }


        private void setCampoQuantita(Campo campoQuantita) {
            this.campoQuantita = campoQuantita;
        }


        private Campo getCampoTotale() {
            return campoTotale;
        }


        private void setCampoTotale(Campo campoTotale) {
            this.campoTotale = campoTotale;
        }


        public int getQuantita() {
            return (Integer)this.getCampoQuantita().getValore();
        }


        public void setQuantita(int quantita) {
            this.getCampoQuantita().setValore(quantita);
        }


        public boolean isSelezionata() {
            return (Boolean)this.getCampoCheck().getValore();
        }


        public double getTotale() {
            return (Double)this.getCampoTotale().getValore();
        }

    } // fine della classe 'interna'


}// fine della classe
