package it.algos.albergo.stampeobbligatorie.istat;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.clientealbergo.indirizzoalbergo.IndirizzoAlbergoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.stampeobbligatorie.PannelloObbligatorie;
import it.algos.albergo.stampeobbligatorie.PrinterObblig;
import it.algos.albergo.stampeobbligatorie.StampeObbLogica;
import it.algos.albergo.stampeobbligatorie.WrapRisposta;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibTesto;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.wrapper.SetValori;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public final class ISTATLogica extends StampeObbLogica implements ISTAT {

    /**
     * Costruttore completo con parametri.
     * <br>
     *
     * @param pan pannello di riferimento
     */
    public ISTATLogica(PannelloObbligatorie pan) {
        super(pan);
    }// fine del metodo costruttore completo




    /**
     * Ritorna la data di fine creazione dei record di testa..
     * <p/>
     * Nel caso ISTAT, è la data dell'arrivo o della partenza più recente
     * @return la data di fine creazione
     */
    protected Date getDataFineCreazione () {
        /* variabili e costanti locali di lavoro */
        Date dataFineCreazione=null;
        Date dataUltArrivo;
        Date dataUltPartenza;

        try { // prova ad eseguire il codice
            dataUltArrivo = this.getDataUltimoArrivo();
            dataUltPartenza = this.getDataUltimaPartenza();
            dataFineCreazione = Lib.Data.getMax(dataUltArrivo, dataUltPartenza);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dataFineCreazione;
    }


    /**
     * Determina se esistono dei movimenti significativi ai fini di questo registro
     * per la data indicata
     * <p/>
     * Per l'ISTAT, esistono se ci sono arrivi o partenze nel giorno.
     * Sovrascritto dalle sottoclassi
     * @param data da controllare
     * @return true se esistono movimenti
     */
    protected boolean isMovimenti(Date data) {
        /* variabili e costanti locali di lavoro */
        boolean esistono = false;
        boolean esistonoArrivi;
        boolean esistonoPartenze;


        try { // prova ad eseguire il codice
            esistonoArrivi = this.isArrivi(data);
            esistonoPartenze = this.isPartenze(data);
            esistono = ((esistonoArrivi) || (esistonoPartenze));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esistono;
    }


    /**
     * Crea il record di testa per un giorno.
     * <p/>
     *
     * @param giorno degli arrivi
     *
     * @return il codice del record creato, 0 se non creato
     */
    protected int creaRecordTesta(Date giorno) {
        /* variabili e costanti locali di lavoro */
        int codTesta = 0;
        boolean continua;
        int progressivo = 0;
        int anno;
        Modulo modTesta;
        SetValori sv;

        try { // prova ad eseguire il codice

            /* crea il record standard nella superclasse */
            codTesta = super.creaRecordTesta(giorno);
            continua = (codTesta > 0);

            /* recupera il prossimo progressivo dell'anno */
            if (continua) {
                anno = Lib.Data.getAnno(giorno);
                progressivo = this.getNextProgressivo(anno);
                continua = (progressivo > 0);
            }// fine del blocco if

            /* scrive il progressivo */
            if (continua) {
                modTesta = this.getModTesta();
                sv = new SetValori(modTesta);
                sv.add(TestaStampe.Cam.progressivo, progressivo);
                modTesta.query().registraRecordValori(codTesta, sv.getListaValori());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codTesta;

    }


    /**
     * Ritorna il prossimo numero progressivo nell'anno.
     * <p/>
     *
     * @param anno di riferimento
     *
     * @return il prossimo numero progressivo nell'anno
     */
    private int getNextProgressivo(int anno) {
        /* variabili e costanti locali di lavoro */
        int nextProg = 0;
        Date inizioAnno;
        Date fineAnno;
        Modulo modTesta;
        Filtro filtroTesta;
        Filtro filtroData1;
        Filtro filtroData2;
        Filtro filtroDate;
        Filtro filtroTot;

        Ordine ordine;

        ArrayList lista;
        Object oggetto;
        int lastProg = 0;

        try {    // prova ad eseguire il codice

            /* crea le date di inizio e fine anno */
            inizioAnno = Lib.Data.getPrimoGennaio(anno);
            fineAnno = Lib.Data.getTrentunoDicembre(anno);

            /* filtro per tenere solo il tipo di record e l'azienda correnti */
            filtroTesta = this.getFiltroTesta();

            /* filtro per tenere solo quelli dell'anno */
            filtroData1 = FiltroFactory.crea(
                    TestaStampe.Cam.data.get(), Filtro.Op.MAGGIORE_UGUALE, inizioAnno);
            filtroData2 = FiltroFactory.crea(TestaStampe.Cam.data.get(), Filtro.Op.MINORE_UGUALE, fineAnno);
            filtroDate = new Filtro();
            filtroDate.add(filtroData1);
            filtroDate.add(filtroData2);

            /* filtro completo */
            filtroTot = new Filtro();
            filtroTot.add(filtroTesta);
            filtroTot.add(filtroDate);

            /* ordine per progressivo discendente (prima il più grande) */
            ordine = new Ordine();
            ordine.add(TestaStampe.Cam.progressivo.get(), Operatore.DISCENDENTE);

            /* recupera la lista */
            modTesta = this.getModTesta();
            lista = modTesta.query().valoriCampo(TestaStampe.Cam.progressivo.get(), filtroTot, ordine);

            /* recupera il primo elemento */
            if (lista.size() > 0) {
                oggetto = lista.get(0);
                lastProg = Libreria.getInt(oggetto);
            }// fine del blocco if

            /* incrementa di 1 */
            nextProg = lastProg + 1;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nextProg;
    }


    /**
     * Crea le righe per un record di testa
     * e completa il record di testa con i dati delle righe
     * <p/>
     *
     * @param codTesta codice del record di testa
     *
     * @return true se riuscito
     */
    protected boolean creaRighe(int codTesta) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Date giorno=null;
        Date giornoPrec;
        Filtro filtroArrivati;
        Filtro filtroPartiti;
        Modulo modIstat;
        int[] codArrivati;
        int[] codPartiti;
        SetValori sv;
        Filtro filtro;
        int arrivati;
        int partiti;
        int presPrec;
        int totArrivi=0;
        int totPartenze=0;
        Modulo modTesta;

        ArrayList<WrapRigaIstat> listaRighe = null;

        try { // prova ad eseguire il codice

            /* recupera i modulo di uso comune */
            modIstat = ISTATModulo.get();
            continua = (modIstat != null);

            /* cancella le righe eventualmente esistenti */
            if (continua) {
                filtro = FiltroFactory.crea(ISTAT.Cam.linkTesta.get(), codTesta);
                continua = modIstat.query().eliminaRecords(filtro);
            }// fine del blocco if

            /* recupera il giorno dal record di testa */
            if (continua) {
                giorno = this.getGiornoTesta(codTesta);
                continua = (Lib.Data.isValida(giorno));
            }// fine del blocco if

            /**
             * recupera gli elenchi delle persone arrivate e partite
             * nel giorno specificato per l'azienda attiva
             * crea una lista di wrapper specifici, un elemento per ogni riga ISTAT da creare
             */
            if (continua) {
                filtroArrivati = PresenzaModulo.getFiltroPresenzeArrivate(giorno);
                filtroPartiti = PresenzaModulo.getFiltroPresenzePartite(giorno);
                codArrivati = this.getCodiciCliente(filtroArrivati);
                codPartiti = this.getCodiciCliente(filtroPartiti);
                listaRighe = this.creaRigheIstat(codArrivati, codPartiti);
            }// fine del blocco if


            /* crea le righe ISTAT */
            if (continua) {
                for (WrapRigaIstat wrapper : listaRighe) {

                    arrivati=wrapper.getNumArrivati();
                    partiti=wrapper.getNumPartiti();
                    totArrivi=totArrivi+arrivati;
                    totPartenze=totPartenze+partiti;

                    sv = new SetValori(modIstat);
                    sv.add(ISTAT.Cam.linkTesta, codTesta);
                    sv.add(ISTAT.Cam.tipoRiga, wrapper.getTipoRiga().getCodice());
                    sv.add(ISTAT.Cam.codResidenza, wrapper.getCodResidenza());
                    sv.add(ISTAT.Cam.numArrivati, arrivati);
                    sv.add(ISTAT.Cam.numPartiti, partiti);
                    sv.add(ISTAT.Cam.codArrivati, wrapper.getStringaArrivati());
                    sv.add(ISTAT.Cam.codPartiti, wrapper.getStringaPartiti());
                    modIstat.query().nuovoRecord(sv.getListaValori());

                }
            }// fine del blocco if

            /* completa il record di testa coi dati delle righe */
            if (continua) {

                giornoPrec = Lib.Data.add(giorno, -1);
                presPrec = PresenzaModulo.getNumPresenze(giornoPrec);

                modTesta = this.getModTesta();
                sv = new SetValori(modTesta);
                sv.add(TestaStampe.Cam.numArrivati, totArrivi);
                sv.add(TestaStampe.Cam.numPartiti, totPartenze);
                sv.add(TestaStampe.Cam.presPrec, presPrec);
                modTesta.query().registraRecordValori(codTesta, sv.getListaValori());
            } // fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Ritorna l'elenco dei codici cliente associati a un gruppo di presenze..
     * <p/>
     *
     * @param filtroPresenze il filtro per isolare le Presenze
     *
     * @return l'elenco dei corrispondenti codici cliente
     */
    private int[] getCodiciCliente(Filtro filtroPresenze) {
        /* variabili e costanti locali di lavoro */
        int[] clienti = new int[0];
        boolean continua;
        Modulo modPresenze;
        int[] chiavi;
        int codPresenza;
        int codCliente;

        try {    // prova ad eseguire il codice

            modPresenze = PresenzaModulo.get();
            continua = (modPresenze != null);
            if (continua) {
                chiavi = modPresenze.query().valoriChiave(filtroPresenze);
                clienti = new int[chiavi.length];
                for (int k = 0; k < chiavi.length; k++) {
                    codPresenza = chiavi[k];
                    codCliente = modPresenze.query().valoreInt(Presenza.Cam.cliente.get(), codPresenza);
                    clienti[k] = codCliente;
                } // fine del ciclo for
            } // fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return clienti;
    }


    /**
     * Dato un elenco di clienti arrivati e uno di partiti, crea un elenco di
     * wrapper di riga Istat.
     * <p/>
     * Suddivide i clienti per residenza
     *
     * @param codArrivati elenco dei codici cliente arrivati
     * @param codPartiti elenco dei codici cliente partiti
     *
     * @return l'elenco delle righe Istat
     */
    private ArrayList<WrapRigaIstat> creaRigheIstat(int[] codArrivati, int[] codPartiti) {
        /* variabili e costanti locali di lavoro */
        ArrayList<WrapRigaIstat> lista = null;
        HashMap<String, WrapRigaIstat> mappaRighe;

        try {    // prova ad eseguire il codice

            /* crea una mappa di righe vuota */
            mappaRighe = new HashMap<String, WrapRigaIstat>();

            /* aggiunge alla mappa gli arrivati */
            for (int cod : codArrivati) {
                this.putCliente(cod, true, mappaRighe);
            }

            /* aggiunge alla mappa i partiti */
            for (int cod : codPartiti) {
                this.putCliente(cod, false, mappaRighe);
            }

            /* crea una lista dalla mappa */
            lista = new ArrayList<WrapRigaIstat>();
            for (WrapRigaIstat riga : mappaRighe.values()) {
                lista.add(riga);
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Aggiunge alla mappa un cliente arrivato o partito.
     * <p/>
     *
     * @param codCliente il codice del cliente
     * @param arrivo true per arrivo, false per partenza
     * @param mappaRighe la mappa alla quale aggiungere il cliente
     */
    private void putCliente(int codCliente, boolean arrivo, HashMap<String, WrapRigaIstat> mappaRighe) {
        /* variabili e costanti locali di lavoro */
        String chiave;
        WrapRigaIstat wrapper;
        int codNazione;
        TipoRiga tipoRecord = null;
        int codResidenza;

        try {    // prova ad eseguire il codice

            /* calcola la chiave in funzione del cliente */
            chiave = this.getChiaveMappa(codCliente);

            /**
             * se esiste, recupera il wrapper dalla mappa
             * se non esiste, lo crea e lo aggiunge alla mappa
             */
            if (mappaRighe.containsKey(chiave)) {

                wrapper = mappaRighe.get(chiave);

            } else {

                codNazione = this.getCodNazioneResidenza(codCliente);
                if (codNazione > 0) {
                    if (this.isItalia(codNazione)) {
                        tipoRecord = TipoRiga.italiano;
                        codResidenza = this.getCodProvinciaResidenza(codCliente);
                    } else {
                        tipoRecord = TipoRiga.straniero;
                        codResidenza = codNazione;
                    }// fine del blocco if-else
                } else {
                    tipoRecord = TipoRiga.nonspecificato;
                    codResidenza = 0;
                }// fine del blocco if-else

                wrapper = new WrapRigaIstat(tipoRecord, codResidenza);
                mappaRighe.put(chiave, wrapper);

            }// fine del blocco if-else

            /* aggiunge il cliente al wrapper */
            wrapper.addCliente(codCliente, arrivo);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Costruisce una chiave di mappa relativamente a un cliente.
     * <p/>
     * La chiave è composta da 2 parti:
     * 1) prima parte: 1 carattere: rappresenta cliente italiano, straniero o non specificato
     * "I" per Italiano, "S" per straniero, "X" per non specificato (vedi Enum TipoRiga)
     * 2) seconda parte: 10 caratteri - rappresenta il codice del luogo di residenza
     * - per gli italiani è il codice della provincia di residenza
     * - per gli stranieri è il codice della nazione di residenza
     * il codice è riempito a sinistra con zeri fino alla lunghezza fissa di 10 caratteri
     *
     * @param codCliente il codice del cliente
     *
     * @return la chiave per la mappa
     */
    private String getChiaveMappa(int codCliente) {
        /* variabili e costanti locali di lavoro */
        String chiaveMappa = "";
        String parteUno;
        String parteDue;
        int codNazione;
        int codResidenza;

        try {    // prova ad eseguire il codice

            /* costruzione prima parte */
            codNazione = this.getCodNazioneResidenza(codCliente);
            if (codNazione > 0) {
                if (this.isItalia(codNazione)) {
                    parteUno = ISTAT.TipoRiga.italiano.getChiaveMappa();   // cliente italiano
                    codResidenza = this.getCodProvinciaResidenza(codCliente);
                } else {
                    parteUno = ISTAT.TipoRiga.straniero.getChiaveMappa();;   // cliente straniero
                    codResidenza = codNazione;
                }// fine del blocco if-else
            } else {
                parteUno = ISTAT.TipoRiga.nonspecificato.getChiaveMappa(); // residenza mancante
                codResidenza = 0;
            }// fine del blocco if-else

            /* costruzione seconda parte */
            parteDue = "" + codResidenza;
            parteDue = Lib.Testo.pad(parteDue, '0', 10, LibTesto.Posizione.inizio);

            /* chiave completa */
            chiaveMappa = parteUno + parteDue;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiaveMappa;
    }


    /**
     * Ritorna il codice della nazione di residenza di un dato cliente.
     * <p/>
     *
     * @param codCliente da controllare
     *
     * @return il codice della nazione di residenza
     */
    private int getCodNazioneResidenza(int codCliente) {
        /* variabili e costanti locali di lavoro */
        int codNazione = 0;
        boolean continua;
        int codIndirizzo;
        int codCitta = 0;
        Modulo modIndirizzo;
        Modulo modCitta;

        try {    // prova ad eseguire il codice

            codIndirizzo = ClienteAlbergoModulo.getCodIndirizzo(codCliente);
            continua = (codIndirizzo > 0);

            if (continua) {
                modIndirizzo = IndirizzoAlbergoModulo.get();
                codCitta = modIndirizzo.query().valoreInt(Indirizzo.Cam.citta.get(), codIndirizzo);
                continua = (codCitta > 0);
            }// fine del blocco if

            if (continua) {
                modCitta = CittaModulo.get();
                codNazione = modCitta.query().valoreInt(Citta.Cam.linkNazione.get(), codCitta);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codNazione;
    }


    /**
     * Ritorna il codice della provincia di residenza di un dato cliente residente in Italia.
     * <p/>
     *
     * @param codCliente da controllare
     *
     * @return il codice della provincia di residenza
     */
    private int getCodProvinciaResidenza(int codCliente) {
        /* variabili e costanti locali di lavoro */
        int codProvincia = 0;
        boolean continua;
        int codIndirizzo;
        int codCitta = 0;
        Modulo modIndirizzo;
        Modulo modCitta;

        try {    // prova ad eseguire il codice

            codIndirizzo = ClienteAlbergoModulo.getCodIndirizzo(codCliente);
            continua = (codIndirizzo > 0);

            if (continua) {
                modIndirizzo = IndirizzoAlbergoModulo.get();
                codCitta = modIndirizzo.query().valoreInt(Indirizzo.Cam.citta.get(), codIndirizzo);
                continua = (codCitta > 0);
            }// fine del blocco if

            if (continua) {
                modCitta = CittaModulo.get();
                codProvincia = modCitta.query().valoreInt(Citta.Cam.linkProvincia.get(), codCitta);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codProvincia;
    }


    /**
     * Controlla se un dato codice Nazione identifica l'Italia.
     * <p/>
     *
     * @param codNazione da controllare
     *
     * @return true se è l'Italia
     */
    private boolean isItalia(int codNazione) {
        /* variabili e costanti locali di lavoro */
        boolean italia = false;
        Modulo modNazione;
        String sigla2;

        try {    // prova ad eseguire il codice
            modNazione = NazioneModulo.get();
            sigla2 = modNazione.query().valoreStringa(Nazione.Cam.sigla2.get(), codNazione);
            italia = (sigla2.equals("IT"));
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return italia;
    }


    /**
     * Effettua le movimentazioni propedeutiche alla prima stampa.
     * <p/>
     *
     * @param codTesta codice del record di testa
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    protected boolean movimenta(int codTesta, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Date giorno;
        int codAzienda;

        try {    // prova ad eseguire il codice

            /* movimenta nella superclasse */
            continua = super.movimenta(codTesta, conn);

//            /* movimentazioni specifiche */
//            if (continua) {
//                giorno = super.getGiornoTesta(codTesta);
//                codAzienda = this.getCodAzienda();
//                continua = PresenzaModulo.registraPS(giorno, codAzienda, conn);
//            }// fine del blocco if
//
//            /**
//             * Ricopia i progressivi assegnati nelle righe
//             */
//            if (continua) {
////                todo da fare
//            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }





    /**
     * Ritorna l'elenco dei codici delle persone arrivate
     * relativamente a una riga ISTAT.
     * <p/>
     * @param codIstat codice della riga ISTAT
     * @return l'elenco dei codici delle persone arrivate
     */
    public static int[] getCodArrivati (int codIstat) {
        return getCodPersone(codIstat, true);
    }

    /**
     * Ritorna l'elenco dei codici delle persone partite
     * relativamente a una riga ISTAT.
     * <p/>
     * @param codIstat codice della riga ISTAT
     * @return l'elenco dei codici delle persone arrivate
     */
    public static int[] getCodPartiti (int codIstat) {
        return getCodPersone(codIstat, false);
    }


    /**
     * Ritorna l'elenco dei codici delle persone arrivate o partite
     * relativamente a una riga ISTAT.
     * <p/>
     * @param codIstat codice della riga ISTAT
     * @param arrivati true per recuperare gli arrivati false per i partiti
     * @return l'elenco dei codici delle persone arrivate o partite
     */
    private static int[] getCodPersone (int codIstat, boolean arrivati) {
        /* variabili e costanti locali di lavoro */
        int[] persone  = new int[0];
        Modulo modIstat;
        String nomeCampo;
        String stringa;
        ArrayList<String> lista;
        int codice;

        try {    // prova ad eseguire il codice

            if (arrivati) {
                nomeCampo=ISTAT.Cam.codArrivati.get();
            } else {
                nomeCampo=ISTAT.Cam.codPartiti.get();
            }// fine del blocco if-else

            modIstat = ISTATModulo.get();
            stringa = modIstat.query().valoreStringa(nomeCampo, codIstat);
            if (Lib.Testo.isValida(stringa)) {
                lista = Libreria.creaListaVirgola(stringa);
                persone = new int[lista.size()];
                for (int k = 0; k < lista.size(); k++) {
                    stringa = lista.get(k);
                    codice = Libreria.getInt(stringa);
                    persone[k]=codice;
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return persone;
    }







    /**
     * Verifica se un record di testa è stampabile.
     * <p/>
     * Chiamato dal bottone <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param codTesta codice del record di testa per il quale effettuare la/le ristampa/e <br>
     *
     * @return un wrapper contenente la risposta e il livello di errore
     */
    @Override
    protected WrapRisposta chkStampabile(int codTesta) {
        /* variabili e costanti locali di lavoro */
        WrapRisposta wrapper = null;
        String testo;
        int[] codRighe;
        boolean valida;

        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            wrapper = super.chkStampabile(codTesta);

            codRighe = this.getCodiciRighe(codTesta, Progetto.getConnessione());
            for (int codRiga : codRighe) {
                valida = isRigaValida(codRiga);
                if (!valida) {
                    testo = "La documentazione di alcuni clienti non è valida.";
                    wrapper.addErrore(false, testo);
                    break;
                }// fine del blocco if
            }


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return wrapper;
    }


    /**
     * Controlla se una riga di ISTAT è valida.
     * <p/>
     *
     * @param codRiga codice della riga da controllare
     *
     * @return vero se le info delle persone sono sufficienti
     */
    private static boolean isRigaValida(int codRiga) {
        /* variabili e costanti locali di lavoro */
        boolean valida = true;
        int[] arrivati;
        int[] partiti;
        ArrayList<Integer> persone;
        Date data;

        try {    // prova ad eseguire il codice

            arrivati = getCodArrivati(codRiga);
            partiti = getCodPartiti(codRiga);
            persone = new ArrayList<Integer>();
            data = getDataTesta(codRiga);
            for (int k = 0; k < arrivati.length; k++) {
                persone.add(arrivati[k]);
            } // fine del ciclo for
            for (int k = 0; k < partiti.length; k++) {
                persone.add(partiti[k]);
            } // fine del ciclo for

            for(int cod:persone){
                valida = ClienteAlbergoModulo.isValidoISTAT(cod, data);
                if (!valida) {
                    break;
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valida;
    }

    /**
     * Ritorna la data del record di testa corrispondente a una data riga.
     * <p/>
     * @param codRiga il codice chiave della riga
     * @return la data del corrispondente record di testa
     */
    public static Date getDataTesta(int codRiga) {
        /* variabili e costanti locali di lavoro */
        Date data  = null;
        Modulo modRighe;
        Modulo modTesta;
        int codTesta;

        try {    // prova ad eseguire il codice

            modRighe = ISTATModulo.get();
            codTesta = modRighe.query().valoreInt(ISTAT.Cam.linkTesta.get(), codRiga);
            modTesta = TestaStampeModulo.get();
            data = modTesta.query().valoreData(TestaStampe.Cam.data.get(), codTesta);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }




    /**
     * Crea il printer relativo a un giorno di stampa.
     * <p/>
     *
     * @param codTesta codice del record di testa
     * @param conn     la connessione da utilizzare
     * @param primaStampa true per prima stampa false per ristampa
     *
     * @return il printer creato
     */
    protected PrinterObblig creaPrinter(int codTesta, Connessione conn, boolean primaStampa) {
        /* variabili e costanti locali di lavoro */
        PrinterObblig printer = null;
        boolean continua;
        Modulo modIstat;
        Modulo modTesta;
        ModuloIstatC59 modulo;

        try { // prova ad eseguire il codice

            modIstat = ISTATModulo.get();
            continua = (modIstat != null);

            if (continua) {
                modTesta = TestaStampeModulo.get();
                continua = (modTesta != null);
            } // fine del blocco if

            /* crea il Printer completo */
            if (continua) {
                printer = new PrinterObblig(primaStampa);
                modulo = new ModuloIstatC59(codTesta);
                printer.addPageable(modulo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return printer;
    }


    /**
     * Ritorna la parte di base per il nome del file pdf.
     * <p/>
     *
     * @return il nome base del file .pdf
     */
    protected String getBaseFilePdf() {
        return "ISTAT";
    }


    /**
     * Ritorna il nome del campo linkato al record di testa
     * specifico di questa logica.
     * <p/>
     *
     * @return il nome del campo link
     */
    protected String getNomeCampoLinkTesta() {
        return ISTAT.Cam.linkTesta.get();
    }


    /**
     * Ritorna il codice del tipo di record di testa.
     * <p/>
     */
    @Override
    protected int getCodTipoRecord() {
        return TestaStampe.TipoRegistro.istat.getCodice();
    }


    /**
     * Ritorna il modulo delle righe.
     * <p/>
     */
    @Override
    protected Modulo getModRighe() {
        return ISTATModulo.get();
    }


}