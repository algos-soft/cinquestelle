package it.algos.base.finestra;

import it.algos.base.componente.bottone.Bottone;
import it.algos.base.dialogo.DialogoConferma;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.progetto.Progetto;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;

/**
 * Dialogo generico di informazioni sul programma in esecuzione.
 * </p>
 * Può essere sovrascritto, per inserire il nome del programma ed eventuale icona <br>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 17-mag-2008 ore  08:53
 */
public class AboutDialogo extends DialogoConferma {

    /**
     * titolo finestra default
     */
    private static final String TITOLO_FINESTRA = "About";

    /**
     * testo bottone
     */
    private static final String TESTO_BOTTONE = "Continua";

    /**
     * testo fisso per la società
     */
    private static final String NOME_SOCIETA = "Algos®";

    /**
     * testo Copyright
     */
    private static final String COPYRIGHT = "Copyright ©2008,  All Rights Reserved";

    /**
     * eventuale diversa società
     */
    private String societa;

    /**
     * nome specifico del programma
     */
    private String programma;

    /**
     * versione del programma
     */
    private String versione;

    /**
     * eventuale icona specifica del programma da mostrare
     */
    private ImageIcon icona;


    /**
     * Costruttore completo senza parametri.
     */
    public AboutDialogo() {
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
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            /* valori di default */
            this.setSocieta(NOME_SOCIETA);
            this.setProgramma(Progetto.getNomePrimoModulo());
            this.setVersione("");
            this.setIcona(null);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        super.inizializza();
        this.regolaBottone();
    } /* fine del metodo */


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    @Override public void avvia() {

        /* titolo della finestra */
        this.regolaTitolo();

        /* scritte in alto */
        this.regolaContenuti();

        super.avvia();
    }// fine del metodo avvia


    /**
     * Titolo della finestra del dialogo.
     * <p/>
     * controlla se è stato previsto un titolo per la finestra
     * altrimenti usa il default
     */
    private void regolaTitolo() {
        /* variabili e costanti locali di lavoro */
        String titolo;

        try { // prova ad eseguire il codice

            titolo = this.getTitolo();
            if (Lib.Testo.isVuota(titolo)) {
                this.setTitolo(TITOLO_FINESTRA);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Scritta del bottone di uscita/conferma.
     * <p/>
     */
    private void regolaBottone() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Bottone bottone;

        try { // prova ad eseguire il codice

            bottone = super.getBottoneConferma();
            continua = (bottone != null);

            if (continua) {
                bottone.getBottone().setText(TESTO_BOTTONE);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Riempie i contenuti del dialogo.
     */
    private void regolaContenuti() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;

        try { // prova ad eseguire il codice
            /* prima pagina */
            pagina = super.getPrimaPagina();

            pagina.aggiungeComponenti(this.getSopra());
            pagina.aggiungeComponenti(this.getSotto());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Pannello società e copyright.
     * <p/>
     * A destra <br>
     *
     * @return pannello costruito
     */
    private Pannello getDestra() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        String programma;
        String tagVer = "Versione ";
        String versione;

        try { // prova ad eseguire il codice
            /* istanza di ritorno */
            pan = PannelloFactory.verticale(this);

            /* recupera il nome */
            programma = this.getProgramma();

            /* eventuale versione del programma */
            versione = this.getVersione();

            /* scritte società e copy */
            pan.add(new JLabel(programma));
            if (Lib.Testo.isValida(versione)) {
                pan.add(new JLabel(tagVer + versione));
            }// fine del blocco if

            pan.add(new JLabel(COPYRIGHT));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Pannello icona (eventuale), società e copyright.
     * <p/>
     * In alto <br>
     *
     * @return pannello costruito
     */
    private Pannello getSopra() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        ImageIcon icona;

        try { // prova ad eseguire il codice
            /* istanza di ritorno */
            pan = PannelloFactory.orizzontale(this);

            /* eventuale icona specifica del programma */
            icona = this.getIcona();

            if (icona != null) {
                pan.add(new JLabel(icona));
                pan.add(this.getDestra());
            } else {
                pan = this.getDestra();
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Pannello con informazioni sul programma e gli autori.
     * <p/>
     * In basso <br>
     *
     * @return pannello costruito
     */
    private Pannello getSotto() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        String testo;
        String societa;
        String programma;
        String aCapo = "\n";
        String and = " & ";
        JTextArea testoArea;

        try { // prova ad eseguire il codice
            /* istanza di ritorno */
            pan = PannelloFactory.orizzontale(this);

            /* recupera il nome */
            societa = this.getSocieta();

            /* eventuale nome specifico del programma */
            programma = this.getProgramma();

            if (Lib.Testo.isValida(programma)) {
                testo = programma;
                testo += " è stato realizzato da:";
            } else {
                testo = "Questo progetto è stato realizzato da:";
            }// fine del blocco if-else
            testo += aCapo;
            testo += "Alessandro Valbonesi";
            testo += and;
            testo += "Guido Andrea Ceresa";
            testo += aCapo;
            testo += "per conto della ";
            testo += societa;
            testoArea = new JTextArea(testo);
            TestoAlgos.setArea(testoArea);
            testoArea.setOpaque(false);

            /* compone */
            pan.add(testoArea);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    private String getSocieta() {
        return societa;
    }


    protected void setSocieta(String societa) {
        this.societa = societa;
    }


    private String getProgramma() {
        return programma;
    }


    public void setProgramma(String programma) {
        this.programma = programma;
    }


    private String getVersione() {
        return versione;
    }


    public void setVersione(String versione) {
        this.versione = versione;
    }


    private ImageIcon getIcona() {
        return icona;
    }


    public void setIcona(ImageIcon icona) {
        this.icona = icona;
    }
}// fine della classe