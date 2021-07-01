/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3 feb 2007
 */

package it.algos.gestione.fattura.fatt;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.AnagraficaModulo;
import it.algos.gestione.anagrafica.WrapDatiFatturazione;
import it.algos.gestione.fattura.FattBase;
import it.algos.gestione.fattura.FattBaseModulo;
import it.algos.gestione.fattura.FattBasePref;
import it.algos.gestione.fattura.riga.RigaFatturaModulo;
import it.algos.gestione.tabelle.iva.Iva;
import it.algos.gestione.tabelle.iva.IvaModulo;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamentoModulo;

/**
 * Fatt - Contenitore dei riferimenti agli oggetti del package.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li> Regola il riferimento al Modello specifico (obbligatorio) </li>
 * <li> Regola i titoli di Menu e Finestra del Navigatore </li>
 * <li> Regola eventualmente alcuni aspetti specifici del Navigatore </li>
 * <li> Crea altri eventuali <strong>Moduli</strong> indispensabili per il
 * funzionamento di questo modulo </li>
 * <li> Rende visibili nel Menu gli altri moduli </li>
 * <li> Regola eventuali funzionalit&agrave; specifiche del Navigatore </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3 feb 2007
 */
public final class FattModulo extends FattBaseModulo {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = "FatturaNormale";

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = "Fatture";

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = "Fatture";

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = "Fatture";


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public FattModulo() {
        /* regola la variabile di istanza con la costante */
        super();

        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public FattModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice
            /* regola il voce della finestra del navigatore */
            super.setTitoloFinestra(TITOLO_FINESTRA);

            /* regola il voce di questo modulo nei menu di altri moduli */
            super.setTitoloMenu(TITOLO_MENU);

            /* tipo di documento in uso */
            this.setTipoDocumento(FattBase.TipoDoc.fattura);

            /* selezione del modello (obbligatorio) */
            super.setModello(new FattModello());

            Progetto.setCatUpdate("fatture");

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
        /* variabili e costanti locali di lavoro */
        RigaFatturaModulo modulo;

        try { // prova ad eseguire il codice

            super.creaModuli();

            modulo = (RigaFatturaModulo)super.creaModulo(new RigaFatturaModulo());
            modulo.setNomeModuloPadre(this.getNomeChiave());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Ritorna il codice di pagamento per la fattura
     * relativamente a un cliente.
     * <p/>
     * Se il cliente ha un codice di pagamento specifico, lo ritorna.
     * Altrimenti, ritorna il codice di pagamento di default dalle
     * preferenze di fattura.
     *
     * @param codCliente il codice cliente
     * @param conn la connessione da utilizzare per le query
     *
     * @return il codice di pagamento
     */
    public int getCodPagamentoCliente(int codCliente, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codPaga = 0;
        Modulo modCliente;


        try {    // prova ad eseguire il codice
            modCliente = Progetto.getModulo(Anagrafica.NOME_MODULO);
            codPaga = modCliente.query().valoreInt(Anagrafica.Cam.pagamento.get(),
                    codCliente,
                    conn);
            if (codPaga == 0) {
                codPaga = TipoPagamentoModulo.get().getRecordPreferito();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPaga;
    }


    /**
     * Ritorna il codice di pagamento per la fattura
     * relativamente a un cliente.
     * <p/>
     * Se il cliente ha un codice di pagamento specifico, lo ritorna.
     * Altrimenti, ritorna il codice di pagamento di default dalle
     * preferenze di fattura.
     * Utilizza la connessione del modulo
     *
     * @param codCliente il codice cliente
     *
     * @return il codice di pagamento
     */
    public int getCodPagamentoCliente(int codCliente) {
        return this.getCodPagamentoCliente(codCliente, this.getConnessione());
    }


    /**
     * Ritorna il codice iva per la fattura
     * relativamente a un cliente.
     * <p/>
     * Se il cliente ha un codice iva specifico, lo ritorna.
     * Altrimenti, ritorna il codice iva di default dalle
     * preferenze di fattura.
     *
     * @param codCliente il codice cliente
     * @param conn la connessione da utilizzare per le query
     *
     * @return il codice iva
     */
    public int getCodIvaCliente(int codCliente, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codIva = 0;
        Modulo modCliente;


        try {    // prova ad eseguire il codice
            modCliente = Progetto.getModulo(Anagrafica.NOME_MODULO);
            codIva = modCliente.query().valoreInt(Anagrafica.Cam.iva.get(), codCliente, conn);
            if (codIva == 0) {
                codIva = IvaModulo.get().getRecordPreferito();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codIva;
    }


    /**
     * Ritorna il codice iva per la fattura
     * relativamente a un cliente.
     * <p/>
     * Se il cliente ha un codice iva specifico, lo ritorna.
     * Altrimenti, ritorna il codice iva di default dalle
     * preferenze di fattura.
     * Utilizza la connessione del modulo
     *
     * @param codCliente il codice cliente
     *
     * @return il codice iva
     */
    public int getCodIvaCliente(int codCliente) {
        return this.getCodIvaCliente(codCliente, this.getConnessione());
    }


    /**
     * Ritorna i dati di fatturazione specifici di un cliente.
     * <p/>
     * Recupera il wrapper da Anagrafica e lo integra con i dati
     * da Preferenze fattura dove necessario.
     *
     * @param codice dell'anagrafica
     *
     * @return pacchetto wrapper con i dati di fatturazione
     */
    public WrapDatiFatturazione getDatiFatturazione(int codice) {
        /* variabili e costanti locali di lavoro */
        WrapDatiFatturazione wrapper = null;
        AnagraficaModulo modAnag;
        int intero;
        double doppio;
        boolean flag;
        Anagrafica.ValoriOpzione opzDefault;
        Anagrafica.ValoriOpzione opzione;

        try { // prova ad eseguire il codice

            modAnag = AnagraficaModulo.get();
            opzDefault = Anagrafica.ValoriOpzione.getDefault();
            wrapper = modAnag.getDatiFatturazione(codice);

            /* regola codice pagamento se mancante */
            if (wrapper.getCodPag() == 0) {
                intero = TipoPagamentoModulo.get().getRecordPreferito();
                wrapper.setCodPag(intero);
            }// fine del blocco if

            /* regola codice iva se mancante */
            if (wrapper.getCodIva() == 0) {
                intero = IvaModulo.get().getRecordPreferito();
                wrapper.setCodIva(intero);
            }// fine del blocco if

            /* se in anagrafica l'opzione applica rivalsa è nulla o "default"
             * attribuisce il flag applica rivalsa da preferenze fattura */
            if (FattBasePref.Fatt.applicaRivalsa.is()) {
                opzione = wrapper.getApplicaRivalsa();
                if ((opzione == null) || (opzione == opzDefault)) {
                    opzione = Anagrafica.ValoriOpzione.si;
                }// fine del blocco if
            } else {
                opzione = Anagrafica.ValoriOpzione.no;
            }// fine del blocco if-else
            wrapper.setApplicaRivalsa(opzione);

            /* se in anagrafica l'opzione applica r.a. è nulla o "default"
        * attribuisce il flag applica r.a. e la percentuale r.a.
        * da preferenze fattura */
            if (FattBasePref.Fatt.applicaRa.is()) {
                opzione = wrapper.getApplicaRA();
                if ((opzione == null) || (opzione == opzDefault)) {
                    opzione = Anagrafica.ValoriOpzione.si;
                    intero = FattBasePref.Fatt.percRa.intero();
                    doppio = Libreria.getDouble(intero) / 100;
                    wrapper.setPercRA(Lib.Mat.arrotonda(doppio, 2));
                }// fine del blocco if
            } else {
                opzione = Anagrafica.ValoriOpzione.no;
            }// fine del blocco if-else
            wrapper.setApplicaRA(opzione);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return wrapper;
    }


    /**
     * Ritorna la percentuale IVA di default per le fatture.
     * <p/>
     *
     * @return la percentuale IVA di default
     */
    public double getPercIvaDefault() {
        /* variabili e costanti locali di lavoro */
        double perc = 0;
        int codRec;
        Modulo modIva;

        try {    // prova ad eseguire il codice

            modIva = IvaModulo.get();
            codRec = modIva.getRecordPreferito();
            if (modIva.query().isEsisteRecord(codRec)) {
                perc = modIva.query().valoreDouble(Iva.Cam.valore.get(), codRec);
            } else {
                perc = 0.2; // se mancasse il default
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return perc;
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static FattModulo get() {
        return (FattModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new FattModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
