/**
 * Title:     PagamentoModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      9-feb-2004
 */
package it.algos.gestione.tabelle.tipopagamento;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.wrapper.CampoValore;

import java.util.ArrayList;
import java.util.Date;

/**
 * PagamentoModulo - Contenitore dei riferimenti agli oggetti del package.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li>  </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 9-feb-2004 ore 7.56.53
 */
public final class TipoPagamentoModulo extends ModuloBase {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = TipoPagamento.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = TipoPagamento.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = TipoPagamento.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public TipoPagamentoModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public TipoPagamentoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

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
        /* selezione del modello (obbligatorio) */
        super.setModello(new TipoPagamentoModello());

        /* regola il voce della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il voce di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(true);
    }// fine del metodo inizia


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
        Navigatore nav;

        try { // prova ad eseguire il codice
            nav = this.getNavigatoreDefault();

            /* modifica i bottoni della toolbar della lista */
            nav.setUsaFrecceSpostaOrdineLista(true);
            nav.addSchedaCorrente(new TipoPagamentoScheda(this));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna la data di scadenza in funzione di un codice di pagamento
     * e di una data iniziale.
     * <p/>
     *
     * @param codPaga codice di pagamento
     * @param dataIniziale la data iniziale
     * @param conn la connessione da utilizzare per le query
     *
     * @return la data di scadenza
     */
    public Date getDataScadenza(int codPaga, Date dataIniziale, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Date dataScad = null;
        int giorni;
        boolean fm;

        try {    // prova ad eseguire il codice

            giorni = (Integer)this.query().valoreCampo(TipoPagamento.Cam.scadGiorni.get(),
                    codPaga,
                    conn);
            fm = (Boolean)this.query().valoreCampo(TipoPagamento.Cam.fineMese.get(), codPaga, conn);
            dataScad = Lib.Data.add(dataIniziale, giorni);
            if (fm) {
                dataScad = Lib.Data.getFineMese(dataScad);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dataScad;
    }


    /**
     * Ritorna la data di scadenza in funzione di un codice di pagamento
     * e di una data iniziale.
     * <p/>
     * Utilizza la connessione del modulo per le query
     *
     * @param codPaga codice di pagamento
     * @param dataIniziale la data iniziale
     *
     * @return la data di scadenza
     */
    public Date getDataScadenza(int codPaga, Date dataIniziale) {
        return this.getDataScadenza(codPaga, dataIniziale, this.getConnessione());
    }


    /**
     * Prima istallazione.
     * <p/>
     * Metodo invocato da Progetto.lancia() <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * <p/>
     * Esegue il metodo iniziale di istallazione per ogni modulo <br>
     */
    @Override
    public void setup() {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoValore> valori;
        String sigla;
        String descrizione;
        int giorni;
        boolean fineMese;
        boolean banca;
        int qualeBanca;
        CampoValore cvSigla;
        CampoValore cvDesc;
        CampoValore cvGiorni;
        CampoValore cvMese;
        CampoValore cvBanca;
        CampoValore cvQualeBanca;
        int cod;

        try { // prova ad eseguire il codice
            valori = new ArrayList<CampoValore>();

            cvSigla = new CampoValore(TipoPagamento.Cam.sigla.get(), "");
            cvDesc = new CampoValore(TipoPagamento.Cam.descrizione.get(), "");
            cvGiorni = new CampoValore(TipoPagamento.Cam.scadGiorni.get(), 0);
            cvMese = new CampoValore(TipoPagamento.Cam.fineMese.get(), false);
            cvBanca = new CampoValore(TipoPagamento.Cam.usaBanca.get(), false);
            cvQualeBanca = new CampoValore(TipoPagamento.Cam.qualeBanca.get(), 0);

            valori.add(cvSigla);
            valori.add(cvDesc);
            valori.add(cvGiorni);
            valori.add(cvMese);
            valori.add(cvBanca);
            valori.add(cvQualeBanca);

            /* traverso tutta la collezione */
            for (TipoPagamento.Setup pag : TipoPagamento.Setup.values()) {
                sigla = pag.getSigla();
                descrizione = pag.getDescrizione();
                giorni = pag.getGiorni();
                fineMese = pag.isFineMese();
                banca = pag.isBanca();
                qualeBanca = pag.getQualeBanca();

                cvSigla.setValore(sigla);
                cvDesc.setValore(descrizione);
                cvGiorni.setValore(giorni);
                cvMese.setValore(fineMese);
                cvBanca.setValore(banca);
                cvQualeBanca.setValore(qualeBanca);

                this.query().nuovoRecord(valori);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static TipoPagamentoModulo get() {
        return (TipoPagamentoModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new TipoPagamentoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

}// fine della classe
