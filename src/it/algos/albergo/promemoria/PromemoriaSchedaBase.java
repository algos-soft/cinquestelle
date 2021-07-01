package it.algos.albergo.promemoria;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaBase;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.JLabel;
import java.util.Date;

/**
 * Scheda specifiche del pacchetto Promemoria
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @version 1.0 / 14-mar-2009
 */
public abstract class PromemoriaSchedaBase extends SchedaBase implements Promemoria {

    protected Pannello placeHolder;


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento per la scheda
     */
    public PromemoriaSchedaBase(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setPlaceHolder(PannelloFactory.orizzontale(this));
    }// fine del metodo inizia


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param campo che ha generato l'evento
     */
    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Campo campoOra;
        Campo campoData;
        Date scadenza;
        Date visione;
        int ora;

        try { // prova ad eseguire il codice

            /* esegue solo all'uscita del campo data di scadenza */
            if (this.isCampo(campo, Promemoria.Cam.dataScadenza)) {

                campoOra = this.getCampo(Promemoria.Cam.oraVisione);
                campoData = this.getCampo(Promemoria.Cam.dataVisione);

                ora = this.getInt(Promemoria.Cam.oraScadenza.get());
                scadenza = this.getData(Promemoria.Cam.dataScadenza.get());

                /* se l'orario previsto è mezzanotte, scala di un giorno
                 * altrimenti di un ora */
                if (ora == MEZZANOTTE) {
                    visione = Lib.Data.add(scadenza, -1);
                    campoOra.setValore(MEZZANOTTE);
                    campoData.setValore(visione);
                } else {
                    campoOra.setValore(ora - 3600);
                    campoData.setValore(scadenza);
                }// fine del blocco if-else


            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.scheda.SchedaBase#add
     */
    @Override
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pag;
        Pannello panAvviso;
        Pannello panDate;
        Pannello panRif;
        int gapEse = 122; //per allineare il bordo destro
        int gapDate = 76; //per allineare il bordo destro
        int gapRif = 20; //per allineare il bordo destro
        String legenda;

        try {    // prova ad eseguire il codice
            /* crea una pagina vuota col titolo */
            pag = super.addPagina("generale");

            /* pannello superiore per l'avviso */
            panAvviso = PannelloFactory.orizzontale(this);
            panAvviso.setAllineamento(Layout.ALLINEA_BASSO);
            panAvviso.setGapFisso(gapEse);

            /* aggiunge i campi al pannello */
            panAvviso.add(Cam.linkTipo);
            panAvviso.add(this.getPlaceHolder());
            pag.add(panAvviso);
            pag.add(Cam.testo);

            /* pannello centrale per le date di avviso e visualizzazione */
            panDate = PannelloFactory.orizzontale(this);
            panDate.creaBordo("date");
            panDate.setGapFisso(gapDate);
            legenda = "scadenza del promemoria";
            panDate.add(getPanDate("esecuzione", legenda, Cam.oraScadenza, Cam.dataScadenza));
            legenda = "segnalazione del promemoria";
            panDate.add(getPanDate("avviso", legenda, Cam.oraVisione, Cam.dataVisione));

            /* pannello inferiore per gli eventuali riferimenti e la conferma */
            panRif = PannelloFactory.orizzontale(this);
            panRif.creaBordo("riferimenti");
            panDate.setGapFisso(gapRif);

            /* aggiunge i campi al pannello */
            panRif.add(Cam.rifCamera);
            panRif.add(Cam.rifCliente);

            /* aggiunge i pannelli alla pagina */
            pag.add(panDate);
            pag.add(panRif);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo creaPagine


    /* pannello centrale per le date di avviso e visualizzazione */
    private Pannello getPanDate(String etichetta, String legenda, Campi sin, Campi dex) {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Pannello panSotto;
        int gapA = 2;
        int gapB = 2;
        JLabel label;
        JLabel labelLeg;

        try { // prova ad eseguire il codice
            pan = PannelloFactory.verticale(this);
            pan.setGapFisso(gapA);
            panSotto = PannelloFactory.orizzontale(this);
            panSotto.setGapFisso(gapB);

            label = new JLabel(etichetta);
            TestoAlgos.setEtichetta(label);
            pan.add(label);
            panSotto.add(sin);
            panSotto.add(dex);

            pan.add(panSotto);

            labelLeg = new JLabel(legenda);
            TestoAlgos.setLegenda(labelLeg);
            pan.add(labelLeg);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    protected Pannello getPlaceHolder() {
        return placeHolder;
    }


    protected void setPlaceHolder(Pannello placeHolder) {
        this.placeHolder = placeHolder;
    }
}// fine della classe}