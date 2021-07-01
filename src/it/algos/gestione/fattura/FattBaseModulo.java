/**
 * Title:     FatturaModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      9-feb-2004
 */
package it.algos.gestione.fattura;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.documento.numeratore.NumeratoreDoc;
import it.algos.base.documento.numeratore.NumeratoreDocModulo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.semaforo.SemaforoModulo;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.cliente.ClienteModulo;
import it.algos.gestione.tabelle.contibanca.ContiBanca;
import it.algos.gestione.tabelle.iva.Iva;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamento;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamentoModulo;
import it.algos.gestione.tabelle.um.UM;


/**
 * FatturaModulo - Contenitore dei riferimenti agli oggetti del package.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li>  </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 9-feb-2004 ore 6.55.30
 */
public abstract class FattBaseModulo extends ModuloBase {


    /* oggetto delegato alla stampa di una singola fattura */
    private FattBaseStampa stampa;

    /* tipo di documento in uso  */
    private FattBase.TipoDoc tipoDocumento;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public FattBaseModulo() {
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo <br>
     *
     * @param nomeChiave nome interno del modulo
     * @param unNodo nodo dell'albero moduli
     */
    public FattBaseModulo(String nomeChiave, AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(nomeChiave, unNodo);

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
        /* preferenze specifiche di questo modulo */
        new FattBasePref();
        this.setUsaTransazioni(true);
    }// fine del metodo inizia


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe Progetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Inizializza il gestore , prima di tutto (servono i Comandi per
     * inzializzare i Campi) <br>
     * Tenta di inizializzare il modulo <br>
     * Prima inizializza il modello, se e' riuscito
     * inizializza anche gli altri oggetti del modulo <br>
     *
     * @return true se il modulo e' stato inizializzato
     */
    @Override
    public boolean inizializza() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;

        try { // prova ad eseguire il codice

            /* inizializza nella superclasse */
            riuscito = super.inizializza();

            /* crea l'oggetto delegato alla stampa della singola fattura */
            this.setStampa(new FattBaseStampa());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    } // fine del metodo


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            nav = new FattBaseNavigatore(this);
            this.addNavigatoreCorrente(nav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice

            super.creaModulo(new ClienteModulo());
            super.creaModulo(new TipoPagamentoModulo());
//            super.creaModulo(new RigaFatturaModulo());
            super.creaModulo(new NumeratoreDocModulo());
            super.creaModulo(new SemaforoModulo());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * <br>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice
            super.addModuloVisibile(Anagrafica.MODULO_CLIENTE);
            super.addModuloVisibile(TipoPagamento.NOME_MODULO);
            super.addModuloVisibile(ContiBanca.NOME_MODULO);
            super.addModuloVisibile(Iva.NOME_MODULO);
            super.addModuloVisibile(UM.NOME_MODULO);
            super.addModuloVisibile(NumeratoreDoc.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo

//    /**
//     * Sincronizza i totali della fattura dopo la
//     * aggiunta / cancellazione / modifica di una riga.
//     * <p/>
//     * Ricalcola i totali della fattura in base alle righe legate alla fattura <br>
//     * Aggiorna i totali visibili in scheda (dopo aver aggiornato il DB) <br>
//     *
//     * @param codChiave codice della fattura
//     * @param conn connessione da utilizzare
//     */
//    public void syncTotali(int codChiave, Connessione conn) {
//        /* variabili e costanti locali di lavoro */
//        Navigatore nav;
//        FatturaScheda scheda;
//
//
//        try { // prova ad eseguire il codice
//
//            /* aggiorna i totali sul DB */
//            ((FatturaModello)this.getModello()).syncTotali(codChiave, conn);
////            this.query().registraRecord(codChiave, null);
//
//            /* notifica la scheda */
//            nav = this.getNavigatoreCorrente();
//            scheda = (FatturaScheda)nav.getScheda();
//            scheda.aggiornaTotali();
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//    }


    /**
     * Rtirorna il voce di un dato tipo di documento.
     * <p/>
     *
     * @param codTipo codice del tipo nella enum dei tipi
     *
     * @return il voce del tipo di documento
     */
    public static String getTitolo(int codTipo) {
        return FattBase.TipoDoc.getTitolo(codTipo);
    }

//    /**
//     * Crea le preferenze specifiche di questo modulo.
//     * <p/>
//     * Invocato dal metodo prepara() del modulo.<br>
//     * Sovrascritto dai moduli specifici quando devono creare preferenze proprie.<br>
//     */
//    @Override protected void creaPreferenze() {
//        PrefValore pref = new PrefValore();
//        pref.setTema(PrefOld.Gen.TEMA);
//        pref.setSigla("tipoDoc");
//        pref.setDescrizione("Tipologia documento");
//        pref.setNote("Tipologia del documento di default per una nuova fattura");
//        pref.setNoteProg("qui le note del programmatore");
//        pref.setAttiva(true);
//        pref.setTipo(PrefPac.TIPO_COMBO);
//        pref.setValoriCombo("alfa,beta,gamma,pippo");
//        pref.setStandard(FattBase.TipoDoc.fattura.getValore());
//        this.addPreferenza(pref);
//    }


    public FattBaseStampa getStampa() {
        return stampa;
    }


    private void setStampa(FattBaseStampa stampa) {
        this.stampa = stampa;
    }


    public FattBase.TipoDoc getTipoDocumento() {
        return tipoDocumento;
    }


    protected void setTipoDocumento(FattBase.TipoDoc tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

//    /**
//     * Restituisce in runtime l'istanza presente in progetto.
//     * <p/>
//     *
//     * @return modulo presente in Progetto
//     */
//    public static FattBaseModulo get() {
//        return (FattBaseModulo)ModuloBase.get(getNomeModulo());
//    }


}// fine della classe
