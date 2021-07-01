/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.pannello;

import it.algos.base.azione.adapter.AzAdapterListSelection;
import it.algos.base.bottone.BottoneAzione;
import it.algos.base.costante.CostanteColore;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.ArrayList;

/**
 * Pannello con liste di stringhe.
 * </p>
 * Questa classe: <ul>
 * <li> Ogni lista � divisa verticalmente in tre:
 * area titolo, area contenuto, area bottoni comando </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public class PannelloLista extends PannelloFlusso {

    /**
     * sfondo delle liste
     */
    static final Color SFONDO = CostanteColore.SFONDO_CAMPO_EDIT;

    /**
     * numero di righe visibili nelle liste (default)
     */
    static final int NUMERO_RIGHE_VISIBILI = 15;

    /**
     * riferimento al proprietario di questa lista
     */
    private PannelloListaDoppia panDoppio;

    /**
     * lista sinistra o destra
     */
    private PannelloListaDoppia.Lista sinistraDestra;

    /**
     * pannello titoli generale
     */
    private PannelloFlusso panTitoli;

    /**
     * label del titolo
     */
    private JLabel etichetta;

    /**
     * lista
     */
    private JList lista;

    /**
     * pannello comandi
     */
    private PannelloFlusso panComandi;

    /**
     * flag uso del bottone taglia
     */
    private boolean usaTaglia;

    /**
     * flag uso del bottone deseleziona
     */
    private boolean usaDeselezione;

    /**
     * flag uso dei bottoni ordinamento
     */
    private boolean usaOrdinamento;

    /**
     * bottone per deselezionare tutti gli elementi
     */
    private BottoneAzione botDeseleziona;

    /**
     * bottone per cancellare gli elementi selezionati dalla lista destra
     */
    private BottoneAzione botTaglia;

    /**
     * bottone freccia su
     */
    private BottoneAzione bottoneSu;

    /**
     * bottone freccia giu
     */
    private BottoneAzione bottoneGiu;

    /**
     * utilizzo della lista scorrevole
     */
    private boolean usaScorrevoleLista;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public PannelloLista() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param sinistraDestra costante per differenziare le due liste
     */
    public PannelloLista(PannelloListaDoppia.Lista sinistraDestra) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setSinistraDestra(sinistraDestra);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            /* regolazioni generali del pannello */
            this.regolaPannello();

            /* regola la sezione titolo */
            this.regolaSezioneTitolo();

            /* regola la sezione contenuto */
            this.regolaSezioneContenuto();

            /* regola la sezione bottoni di comando */
            this.regolaSezioneBottoni();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alle impostazioni
     * correnti delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Pannello panBot;

        try { // prova ad eseguire il codice

//            /* invoca il metodo sovrascritto della superclasse */
//            super.inizializza();

            /* Aggiunge gli elementi grafici al pannello */
            this.disegnaPannello();

            /* recupera il pannello dei bottoni comando */
            panBot = this.getPanComandi();

            /* inserisce il bottone deselezione nel pannello */
            if (this.isUsaDeselezione()) {
                if (this.getBotDeseleziona() != null) {
                    panBot.add(this.getBotDeseleziona());
                }// fine del blocco if
            }// fine del blocco if

            /* inserisce il bottone taglia nel pannello */
            if (this.isUsaTaglia()) {
                if (this.getBotTaglia() != null) {
                    panBot.add(this.getBotTaglia());
                }// fine del blocco if
            }// fine del blocco if

            /* inserisce i bottoni spostamento nel pannello */
            if (this.isUsaOrdinamento()) {
                if (this.getBottoneSu() != null) {
                    panBot.add(this.getBottoneSu());
                }// fine del blocco if
                if (this.getBottoneGiu() != null) {
                    panBot.add(this.getBottoneGiu());
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola il pannello.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     */
    private void regolaPannello() {
        try { // prova ad eseguire il codice
            this.setBackground(SFONDO);
            this.setBackground(Color.YELLOW);
            this.setOpaque(false);

            this.setUsaGapFisso(true);
            this.setGapPreferito(10);
            this.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            this.setUsaScorrevoleLista(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge gli elementi grafici al pannello.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     */
    private void disegnaPannello() {
        /* variabili e costanti locali di lavoro */
        JScrollPane scorrevole;
        int cod = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;

        try { // prova ad eseguire il codice

            /* aggiunge la sezione al pannello generale */
            this.add(this.getPanTitoli());

            /* aggiunge la lista, con scorrevole, al pannello generale */
            if (this.isUsaScorrevoleLista()) {
                scorrevole = new JScrollPane(this.getLista());
                scorrevole.setVerticalScrollBarPolicy(cod);
                this.add(scorrevole);
            } else {
                this.add(this.getLista());
            }// fine del blocco if-else

            /* aggiunge la sezione al pannello generale */
            this.add(this.getPanComandi());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la sezione titolo.
     * <p/>
     * Crea gli oggetti <br>
     * Inserisce la sezione nel pannello <br>
     */
    private void regolaSezioneTitolo() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso panTit;
        JLabel etichetta = null;

        try { // prova ad eseguire il codice

            /* crea un pannello per i titoli */
            panTit = new PannelloFlusso();
            this.setPanTitoli(panTit);

            /* crea una etichetta da inserire nel pannello */
            etichetta = new JLabel();
            this.setEtichetta(etichetta);

            /* aggiunge l'etichetta al pannello titoli */
            panTit.add(this.getEtichetta());

            /* assegna un titolo di default */
            this.setTitolo("Lista");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la sezione contenuto.
     * <p/>
     * Crea gli oggetti <br>
     * Inserisce la sezione nel pannello <br>
     */
    private void regolaSezioneContenuto() {
        /* variabili e costanti locali di lavoro */
        JList lista;

        try { // prova ad eseguire il codice

            /* regola gli oggetti */
            this.setLista(new JList());
            lista = this.getLista();
            lista.setBackground(SFONDO);

            lista.addListSelectionListener(new SincronizzaLista());

            /* regola l'altezza della parte visibile della lista
             * in numero di righe */
            this.setNumeroRigheVisibili(NUMERO_RIGHE_VISIBILI);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la sezione bottoni di comando.
     * <p/>
     * Crea il pannello <br>
     * Crea i due bottoni di scorrimento, senza aggiungerli al pannello <br>
     * Crea il bottone taglia, senza aggiungerlo al pannello <br>
     * Inserisce la sezione nel pannello <br>
     */
    private void regolaSezioneBottoni() {
        /* variabili e costanti locali di lavoro */
        int orientamento;
        BottoneAzione botDes;
        BottoneAzione botTaglia;
        BottoneAzione botSu;
        BottoneAzione botGiu;
        String iconaDes = "CaricaTutti24";
        String iconaTaglia = "Cut24";
        String iconaSu = "listaspostasu24";
        String iconaGiu = "listaspostagiu24";
        String metodoDes = "deselezionaTutti";
        String metodoTaglia = "taglia";
        String metodoSu = "rigaSu";
        String metodoGiu = "rigaGiu";
        String toolTipDes = "Deseleziona tutti gli elementi";
        String toolTaglia = "Rimuove dalla lista gli elementi selezionati";
        String toolTipSu = "Sposta in alto l'elemento selezionato";
        String toolTipGiu = "Sposta in basso l'elemento selezionato";


        try { // prova ad eseguire il codice

            /* crea e regola il pannello bottoni */
            orientamento = Layout.ORIENTAMENTO_ORIZZONTALE;
            this.setPanComandi(new PannelloFlusso(orientamento));

            /* crea il bottone deseleziona */
            botDes = this.getBottone(metodoDes, iconaDes, toolTipDes);
            this.setBotDeseleziona(botDes);

            /* crea il bottone taglia */
            botTaglia = this.getBottone(metodoTaglia, iconaTaglia, toolTaglia);
            this.setBotTaglia(botTaglia);

            /* crea il bottone su */
            botSu = this.getBottone(metodoSu, iconaSu, toolTipSu);
            this.setBottoneSu(botSu);

            /* crea il bottone giu */
            botGiu = this.getBottone(metodoGiu, iconaGiu, toolTipGiu);
            this.setBottoneGiu(botGiu);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un bottone.
     * <p/>
     */
    public void addBottone(JButton bottone) {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso panBot;

        try { // prova ad eseguire il codice

            panBot = this.getPanComandi();
            panBot.add(bottone);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Spostamento di una riga in una lista.
     * <p/>
     *
     * @param tipoSpostamento spostamento in su od in giu
     */
    private void spostaRiga(Spostamento tipoSpostamento) {
        /* variabili e costanti locali di lavoro */
        int[] tot;
        ArrayList<String> lista;
        int pos;
        String riga;

        try { // prova ad eseguire il codice
            /* controlla quante righe sono selezionate*/
            tot = this.getLista().getSelectedIndices();

            /* prosegue solo con una riga selezionata */
            if (tot.length == 1) {
                /* recupera la lista attuale */
                lista = this.getValori();

                /* posizione nella lista dell'elemento da spostare */
                pos = this.getLista().getSelectedIndex();

                /* elemento da spostare */
                riga = this.getLista().getSelectedValue().toString();

                /* eliminazione temporanea */
                lista.remove(pos);

                /*  */
                switch (tipoSpostamento) {
                    case SOPRA:
                        lista.add(pos - 1, riga);
                        break;
                    case SOTTO:
                        lista.add(pos + 1, riga);
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

                /* regola i valori della lista */
                this.setValori(lista);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Deseleziona tutti gli elementi della lista.
     * <p/>
     * Metodo invocato dall'evento del bottone <br>
     */
    public void deselezionaTutti() {
        /* variabili e costanti locali di lavoro */
        JList lista;

        try { // prova ad eseguire il codice
            /* recupera la lista */
            lista = this.getLista();

            /* pulisce la selezione*/
            lista.clearSelection();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Rimuove dalla lista di destra gli elementi selezionati.
     * <p/>
     * Metodo invocato dall'evento del bottone <br>
     * Recupera i valori selezionati <br>
     */
    public void taglia() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> valoriListaDestra;
        ArrayList<String> valoriSelezionati;

        try { // prova ad eseguire il codice

            /* recupera i valori selezionati della lista */
            valoriSelezionati = this.getSelezionati();

            /* recupera tutti i valori della lista */
            valoriListaDestra = this.getValori();

            /* invoca il metodo delegato della classe */
            Lib.Array.rimuoveValori(valoriSelezionati, valoriListaDestra);

            /* aggiorna la lista */
            this.setValori(valoriListaDestra);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone riga su.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Regola il tipo di spostamento <br>
     * Invoca il metodo delegato per invertire i valori <br>
     */
    public void rigaSu() {
        /* invoca il metodo delegato della classe */
        this.spostaRiga(Spostamento.SOPRA);
    }


    /**
     * Bottone riga giu.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Regola il tipo di spostamento <br>
     * Invoca il metodo delegato per invertire i valori <br>
     */
    public void rigaGiu() {
        /* invoca il metodo delegato della classe */
        this.spostaRiga(Spostamento.SOTTO);
    }


    /**
     * Sincronizza la lista.
     * <p/>
     * Metodo invocato da una modifica della selezione della lista <br>
     * Invoca il metodo della classe delegata <br>
     */
    public void sincronizzaLista() {
        /* variabili e costanti locali di lavoro */
        PannelloListaDoppia panDoppio;
        PannelloListaDoppia.Lista sinistraDestra;

        try { // prova ad eseguire il codice
            /* recupera il pannello doppio */
            panDoppio = this.getPanDoppio();

            /* recupera la costante identificativa di questa lista */
            sinistraDestra = this.getSinistraDestra();

            /* invoca il meto delegato, passandogli il parametro  */
            panDoppio.sincronizzaLista(sinistraDestra);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce lo stato della selezione.
     * <p/>
     */
    public boolean isSelectionEmpty() {
        /* variabili e costanti locali di lavoro */
        boolean vuota = true;

        try {    // prova ad eseguire il codice
            vuota = this.getLista().isSelectionEmpty();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return vuota;
    }


    /**
     * Regola il modello dati.
     */
    public void setValori(ArrayList<String> valori) {
        this.getLista().setListData(valori.toArray());
    }


    /**
     * Restituisce un array di valori della lista completa.
     */
    public ArrayList<String> getValori() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> valori = null;
        ListModel modello;
        int tot;
        String riga;

        try { // prova ad eseguire il codice
            /* crea l'oggetto */
            valori = new ArrayList<String>();

            /* recupera il modello dei dati */
            modello = lista.getModel();

            /* dimensione della lista */
            tot = modello.getSize();

            /* spazzola tutta la lista */
            for (int k = 0; k < tot; k++) {
                riga = modello.getElementAt(k).toString();
                valori.add(riga);
            } // fine del ciclo for
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valori;
    }


    /**
     * Restituisce un array di valori selezionati della lista.
     */
    ArrayList<String> getSelezionati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> valori = null;
        Object[] valoriSelezionati;
        String riga;

        try { // prova ad eseguire il codice
            /* crea l'oggetto */
            valori = new ArrayList<String>();

            /* recupera la matrice dei valori selezionati */
            valoriSelezionati = lista.getSelectedValues();

            /* spazzola tutta la lista */
            for (Object oggetto : valoriSelezionati) {
                riga = (String)oggetto;
                valori.add(riga);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valori;
    }


    private PannelloListaDoppia getPanDoppio() {
        return panDoppio;
    }


    public void setPanDoppio(PannelloListaDoppia panDoppio) {
        this.panDoppio = panDoppio;
    }


    private PannelloFlusso getPanTitoli() {
        return panTitoli;
    }


    private void setPanTitoli(PannelloFlusso panTitoli) {
        this.panTitoli = panTitoli;
    }


    private JLabel getEtichetta() {
        return etichetta;
    }


    private void setEtichetta(JLabel etichetta) {
        this.etichetta = etichetta;
    }


    public void setTitolo(String titolo) {
        this.getEtichetta().setText(titolo);
        this.getPanTitoli().bloccaDim();
    }


    /**
     * Regola il numero di righe visibili della lista
     * <p/>
     * (determina l'altezza del pannello)
     *
     * @param numRighe il numero di righe visibili
     */
    public void setNumeroRigheVisibili(int numRighe) {
        /* variabili e costanti locali di lavoro */
        JList lista = null;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                lista.setVisibleRowCount(numRighe);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public JList getLista() {
        return lista;
    }


    private void setLista(JList lista) {
        this.lista = lista;
    }


    private PannelloFlusso getPanComandi() {
        return panComandi;
    }


    private void setPanComandi(PannelloFlusso panComandi) {
        this.panComandi = panComandi;
    }


    private boolean isUsaTaglia() {
        return usaTaglia;
    }


    public void setUsaTaglia(boolean usaTaglia) {
        this.usaTaglia = usaTaglia;
    }


    private boolean isUsaDeselezione() {
        return usaDeselezione;
    }


    public void setUsaDeselezione(boolean usaDeselezione) {
        this.usaDeselezione = usaDeselezione;
    }


    private boolean isUsaOrdinamento() {
        return usaOrdinamento;
    }


    public void setUsaOrdinamento(boolean usaOrdinamento) {
        this.usaOrdinamento = usaOrdinamento;
    }


    public BottoneAzione getBottoneSu() {
        return bottoneSu;
    }


    private void setBottoneSu(BottoneAzione bottoneSu) {
        this.bottoneSu = bottoneSu;
    }


    public BottoneAzione getBottoneGiu() {
        return bottoneGiu;
    }


    private void setBottoneGiu(BottoneAzione bottoneGiu) {
        this.bottoneGiu = bottoneGiu;
    }


    public PannelloListaDoppia.Lista getSinistraDestra() {
        return sinistraDestra;
    }


    public void setSinistraDestra(PannelloListaDoppia.Lista sinistraDestra) {
        this.sinistraDestra = sinistraDestra;
    }


    public BottoneAzione getBotDeseleziona() {
        return botDeseleziona;
    }


    private void setBotDeseleziona(BottoneAzione botDeseleziona) {
        this.botDeseleziona = botDeseleziona;
    }


    public BottoneAzione getBotTaglia() {
        return botTaglia;
    }


    private void setBotTaglia(BottoneAzione botTaglia) {
        this.botTaglia = botTaglia;
    }


    private boolean isUsaScorrevoleLista() {
        return usaScorrevoleLista;
    }


    public void setUsaScorrevoleLista(boolean usaScorrevoleLista) {
        this.usaScorrevoleLista = usaScorrevoleLista;
    }


    /**
     * Enumerazione.
     * <p/>
     * Costanti per identificare i due bottoni di spostamento <br>
     */
    private enum Spostamento {

        SOPRA,
        SOTTO
    }// fine della classe interna


    /**
     * Inner class per gestire la sincronizzazione della lista.
     */
    private class SincronizzaLista extends AzAdapterListSelection {

        /**
         * valueChanged, da ListSelectionListener.
         * <p/>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void valueChanged(ListSelectionEvent unEvento) {
            sincronizzaLista();
        }

    } // fine della classe interna

} // fine della classe
