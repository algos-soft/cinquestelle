package it.algos.albergo.stampeobbligatorie.notifica;

import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.PageEject;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.clientealbergo.tabelle.parente.ParentelaModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.stampeobbligatorie.PannelloObbligatorie;
import it.algos.albergo.stampeobbligatorie.PrinterObblig;
import it.algos.albergo.stampeobbligatorie.StampeObbLogica;
import it.algos.albergo.stampeobbligatorie.WrapRisposta;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.TavolaModello;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.tavola.Tavola;
import it.algos.base.wrapper.SetValori;

import javax.swing.JTable;
import java.util.ArrayList;
import java.util.Date;

public final class NotificaLogica extends StampeObbLogica implements Notifica {

    /**
     * Costruttore completo con parametri.
     * <br>
     *
     * @param pan pannello di riferimento
     */
    public NotificaLogica(PannelloObbligatorie pan) {
        super(pan);
    }// fine del metodo costruttore completo


//    /**
//     * Crea le righe per un record di testa
//     * e completa il record di testa con i dati delle righe
//     * <p/>
//     * In questo caso, crea le schede di notifica.
//     *
//     * @param codTesta codice del record di testa
//     *
//     * @return true se riuscito
//     */
//    protected boolean creaRigheOld(int codTesta) {
//        /* variabili e costanti locali di lavoro */
//        boolean continua = false;
//        Date giorno;
//        Filtro filtro = null;
//        Modulo modTesta = null;
//        Modulo modNotifica = null;
//        Modulo modPresenze = null;
//        ArrayList valori;
//        ArrayList<Integer> codClienti = null;
//        int codCliente;
//        int codAzienda;
//        int codCapo;
//        int codRecord;
//        LinkedHashMap<Integer, ArrayList<Integer>> mappa = null;
//        ArrayList<Integer> membri;
//        int numGruppi = 0;
//        int numPersone;
//        String persone;
//        boolean capoGruppoArrivato;
//        boolean esisteRiga;
//        String nomeProvvisorio;
//        int codIntestatario;
//
//        try { // prova ad eseguire il codice
//
//            /* recupera il giorno */
//            giorno = this.getGiornoTesta(codTesta);
//            continua = (Lib.Data.isValida(giorno));
//
//            /* recupera il filtro per le presenze arrivate nel giorno per l'azienda attiva */
//            if (continua) {
//                codAzienda = AlbergoModulo.getCodAzienda();
//                filtro = PresenzaModulo.getFiltroPresenzeArrivate(giorno, codAzienda);
//            }// fine del blocco if
//
//            /* recupera i codici dei corrispondenti clienti */
//            if (continua) {
//                modPresenze = PresenzaModulo.get();
//                continua = (modPresenze != null);
//            }// fine del blocco if
//
//            if (continua) {
//                modTesta = this.getModTesta();
//                continua = (modTesta != null);
//            } // fine del blocco if
//
//            if (continua) {
//                modNotifica = this.getModRighe();
//                continua = (modNotifica != null);
//            } // fine del blocco if
//
//            if (continua) {
//                valori = modPresenze.query().valoriCampo(Presenza.Cam.cliente.get(), filtro);
//                codClienti = new ArrayList<Integer>();
//                for (Object ogg : valori) {
//                    codCliente = Libreria.getInt(ogg);
//                    codClienti.add(codCliente);
//                } // fine del ciclo for
//                continua = (codClienti != null && codClienti.size() > 0);
//            } // fine del blocco if
//
//            /* spazzola i clienti e per ogni capogruppo crea un elemento della mappa */
//            if (continua) {
//                mappa = new LinkedHashMap<Integer, ArrayList<Integer>>();
//                for (int cod : codClienti) {
//                    if (ClienteAlbergoModulo.isCapogruppo(cod)) {
//                        if (!mappa.containsKey(cod)) {
//                            mappa.put(cod, new ArrayList<Integer>());
//                        } // fine del blocco if
//                    } else {
//                        codCapo = ClienteAlbergoModulo.getCodCapogruppo(cod);
//
//                        if (mappa.containsKey(codCapo)) {
//                            membri = mappa.get(codCapo);
//                            membri.add(cod);
//                        } else {
//                            membri = new ArrayList<Integer>();
//                            membri.add(cod);
//                            mappa.put(codCapo, membri);
//                        } // fine del blocco if-else
//                    } // fine del blocco if-else
//                } // fine del ciclo for-each
//                continua = (mappa != null && mappa.size() > 0);
//            } // fine del blocco if
//
//            /* traverso tutta la collezione (per le righe) */
//            if (continua) {
//                numGruppi = mappa.size();
//                for (Map.Entry map : mappa.entrySet()) {
//                    codCapo = (Integer)map.getKey();
//                    nomeProvvisorio =
//                            ClienteAlbergoModulo.get().query().valoreStringa(Anagrafica.Cam.soggetto.get(), codCapo);
//                    nomeProvvisorio = nomeProvvisorio + "";
//                    membri = (ArrayList<Integer>)map.getValue();
//                    capoGruppoArrivato = codClienti.contains(codCapo);
//                    if (capoGruppoArrivato) {
//                        membri.add(codCapo);
//                    } // fine del blocco if
//                    numPersone = membri.size();
//                    persone = Lib.Testo.getStringaIntVirgola(membri);
//
//                    /* Selezione dell'intestatario della scheda di notifica */
//                    codIntestatario = this.getIntestatario(capoGruppoArrivato, membri, codCapo);
//
//                    /* righe */
//                    filtro = FiltroFactory.crea(Notifica.Cam.linkTesta.get(), codTesta);
//                    filtro.add(FiltroFactory.crea(Notifica.Cam.linkCliente.get(), codCapo));
//                    esisteRiga = modNotifica.query().isEsisteRecord(filtro);
//                    if (!esisteRiga) {
//                        codRecord = modNotifica.query().nuovoRecord();
//                        modNotifica.query().registra(codRecord, Notifica.Cam.linkTesta.get(), codTesta);
//                        modNotifica.query().registra(codRecord, Notifica.Cam.linkCliente.get(), codIntestatario);
//                        modNotifica.query().registra(codRecord, Notifica.Cam.numPersone.get(), numPersone);
//                        modNotifica.query().registra(codRecord, Notifica.Cam.codPersone.get(), persone);
//                    } // fine del blocco if
//                } // fine del ciclo for-each
//            } // fine del blocco if
//
//            /* completa il record di testa coi dati delle righe */
//            if (continua) {
//                numPersone = codClienti.size();
//                modTesta.query().registra(codTesta, TestaStampe.Cam.gruppi.get(), numGruppi);
//                modTesta.query().registra(codTesta, TestaStampe.Cam.numArrivati.get(), numPersone);
//            } // fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return continua;
//    }


    /**
     * Crea le righe per un record di testa
     * e completa il record di testa con i dati delle righe
     * <p/>
     * In questo caso, crea le schede di notifica.
     *
     * @param codTesta codice del record di testa
     *
     * @return true se riuscito
     */
    protected boolean creaRighe(int codTesta) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Date giorno;
        Filtro filtro = null;
        Ordine ordine;
        Campo campo;
        Modulo modTesta;
        Modulo modPresenze;
        Modulo modCamera;
        int[] presenze=new int[0];
        int codCliente;
        ArrayList<Integer> membri;
        int numGruppi = 0;
        int numPersone;
        int currCamera;
        int codCamera;
        int idPeriodo=0;

        try { // prova ad eseguire il codice


            /* dati di uso comune */
            modPresenze = PresenzaModulo.get();
            modTesta = this.getModTesta();
            modCamera = CameraModulo.get();


            /* recupera il giorno degli arrivi */
            giorno = this.getGiornoTesta(codTesta);
            continua = (Lib.Data.isValida(giorno));

            /* recupera il filtro per le presenze arrivate nel giorno per l'azienda attiva */
            if (continua) {
                filtro = PresenzaModulo.getFiltroPresenzeArrivate(giorno);
            }// fine del blocco if

            /* recupera le presenze arrivate in ordine di camera */
            if (continua) {
                campo = modCamera.getCampo(Camera.Cam.camera);
                ordine = new Ordine(campo);
                presenze = modPresenze.query().valoriChiave(filtro, ordine);
            }// fine del blocco if

            /**
             * spazzola le presenze
             * accumula i codici cliente finché non cambia camera
             * quando cambia camera o la lista è finita crea il record di Notifica
             * con tutti i clienti della stessa camera
             */
            if (continua) {
                currCamera = -1;
                membri = new ArrayList<Integer>();
                for(int codPres : presenze){

                    codCamera = modPresenze.query().valoreInt(Presenza.Cam.camera.get(), codPres);
                    codCliente = modPresenze.query().valoreInt(Presenza.Cam.cliente.get(), codPres);
                    
                    if (codCamera!=currCamera) {

                        // registra la lista corrente se piena
                        if (membri.size()>0) {
                            this.creaRiga(codTesta, currCamera, idPeriodo, membri);
                            numGruppi++;
                        }// fine del blocco if

                        // svuota la lista
                        membri.clear();

                        // aggiorna la camera corrente
                        currCamera = codCamera;

                    }// fine del blocco if-else

                    // il periodo è uguale per tutti gli arrivati nella camera (teoricamente)
                    // quindi lo leggo tutte le volte ma nell'ambito della camera rimane uguale
                    // ed è unico per tutta la scheda di notifica
                    idPeriodo = modPresenze.query().valoreInt(Presenza.Cam.periodo.get(), codPres);

                    membri.add(codCliente);

                }

                // alla fine registra la lista corrente se piena
                if (membri.size()>0) {
                    this.creaRiga(codTesta, currCamera, idPeriodo, membri);
                    numGruppi++;
                }// fine del blocco if

            }// fine del blocco if



            /* completa il record di testa coi dati delle righe */
            if (continua) {
                numPersone = presenze.length;
                modTesta.query().registra(codTesta, TestaStampe.Cam.gruppi.get(), numGruppi);
                modTesta.query().registra(codTesta, TestaStampe.Cam.numArrivati.get(), numPersone);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Crea una riga ISTAT con il gruppo di clienti fornito.
     * <p/>
     * @param codTesta codice del record di testa
     * @param codCamera codice della camera
     * @param idPeriodo id del periodo di riferimento
     * @param clienti elenco dei clienti
     * Elegge una persona del gruppo a Capogruppo Scheda con la
     * seguente logica:
     * - se tra le persone c'è un Capogruppo usa quello (il primo se più di uno)
     * - se non ci sono Capigruppo usa il primo in ordine di parentela
     */
    private void creaRiga (int codTesta, int codCamera, int idPeriodo, ArrayList<Integer> clienti) {
        /* variabili e costanti locali di lavoro */
        int codCapo=0;
        String strPersone;
        Modulo modRighe;
        SetValori sv;

        try {    // prova ad eseguire il codice

            /* determina chi deve essere il capogruppo della scheda */
            int[] codici = new int[clienti.size()];
            for (int k = 0; k < clienti.size(); k++) {
                codici[k] = clienti.get(k);
            } // fine del ciclo for
            codCapo = eleggiCapoScheda(codici);

            /* costruisce la stringa con i codici di tutte le persone */
            strPersone = Lib.Testo.getStringaIntVirgola(clienti);

            /* crea il record */
            modRighe = this.getModRighe();
            sv = new SetValori(modRighe);
            sv.add(Notifica.Cam.linkTesta, codTesta);
            sv.add(Notifica.Cam.camera, codCamera);
            sv.add(Notifica.Cam.linkPeriodo, idPeriodo);
            sv.add(Notifica.Cam.linkCliente, codCapo);
            sv.add(Notifica.Cam.numPersone, clienti.size());
            sv.add(Notifica.Cam.codPersone, strPersone);
            modRighe.query().nuovoRecord(sv.getListaValori());

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Determina chi deve essere eletto a Capogruppo della
     * Scheda di Notifica tra un elenco di clienti.
     * <p/>
     * @param clienti elenco dei clienti
     * @return il codice eletto a Capogruppo Scheda
     */
    public static int eleggiCapoScheda (int[] clienti) {
        /* variabili e costanti locali di lavoro */
        int codCapo  = 0;

        try {    // prova ad eseguire il codice

            /* se c'è un Capogruppo usa il primo Capogruppo che trova nella lista */
            for(int codCliente : clienti){
                if (ClienteAlbergoModulo.isCapogruppo(codCliente)) {
                    codCapo = codCliente;
                    break;
                }// fine del blocco if
            }

            /* se non c'è un Capogruppo usa il primo in ordine di parentela */
            if (codCapo==0) {
                codCapo = getPrimoParente(clienti);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codCapo;
}




//    /**
//     * Selezione dell'intestatario della scheda di notifica.
//     * <p/>
//     * Restituisce il codice del capogruppo, se questo è effettivamente arivato;
//     * altrimenti il primo degli arrivati in ordine di categoria/tipo di parentela <br>
//     *
//     * @param capoGruppoArrivato flag
//     * @param membri             persone arrivate
//     * @param codCapo            capogruppo
//     *
//     * @return codice del record di anagrafica dell'intestatario della scheda di notifica
//     *
//     * @see @todo ticket #41
//     */
//    private int getIntestatario(boolean capoGruppoArrivato, ArrayList<Integer> membri, int codCapo) {
//        /* variabili e costanti locali di lavoro */
//        int codIntestatario = 0;
//        boolean continua;
//
//        try {    // prova ad eseguire il codice
//            /* controllo di congruità */
//            continua = (membri != null && membri.size() > 0 && codCapo > 0);
//
//            if (continua) {
//                if (capoGruppoArrivato) {
//                    codIntestatario = codCapo;
//                } else {
//                    if (membri.size() > 0) {
//                        codIntestatario = this.getPrimoParente(membri);
//                        if (codIntestatario == 0) {
//                            codIntestatario = membri.get(0);
//                        }// fine del blocco if
//                    } else {
//                        codIntestatario = codCapo;
//                    }// fine del blocco if-else
//                }// fine del blocco if-else
//            }// fine del blocco if
//        } catch (Exception unErrore) {    // intercetta l'errore
//            new Errore(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return codIntestatario;
//    }


    /**
     * Codice della persona del gruppo col grado più alto di parentela.
     * <p/>
     *
     * @param clienti elenco clienti
     * @return codice del cliente con più alto grado di parentela
     */
    private static int getPrimoParente(int[] clienti) {
        /* variabili e costanti locali di lavoro */
        int codIntestatario = 0;
        boolean continua;
        Modulo modAnagrafica = null;
        Modulo modParentela = null;
        int gradoParentela;
        int codParentela;
        int test = 827; // numero sufficientemente grande

        try {    // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (clienti != null && clienti.length > 0);

            if (continua) {
                modAnagrafica = ClienteAlbergoModulo.get();
                continua = (modAnagrafica != null);
            } // fine del blocco if

            if (continua) {
                modParentela = ParentelaModulo.get();
                continua = (modParentela != null);
            } // fine del blocco if

            if (continua) {
                for (int cod : clienti) {
                    codParentela = modAnagrafica.query().valoreInt(ClienteAlbergo.Cam.parentela.get(), cod);
                    gradoParentela = modParentela.query().valoreInt(modParentela.getCampoOrdine(), codParentela);
                    if (gradoParentela <= test) {
                        codIntestatario = cod;
                        test = gradoParentela;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codIntestatario;
    }


    /**
     * Controlla se una intera stampa è valida.
     * <p/>
     *
     * @param codTesta codice del record di testa
     *
     * @return true se è valida
     */
    private boolean isStampaValida(int codTesta) {
        /* variabili e costanti locali di lavoro */
        boolean valida = true;
        int[] codRighe;

        try {    // prova ad eseguire il codice
            codRighe = this.getCodiciRighe(codTesta, Progetto.getConnessione());
            for (int codRiga : codRighe) {
                valida = isSchedaValida(codRiga);
                if (!valida) {
                    break;
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valida;
    }




    /**
     * Controlla se la scheda relativa a una riga è valida.
     * <p/>
     * Per ogni gruppo di arivi, devono essere valide le info di tutte le persone del gruppo <br>
     * Il capogruppo necessita di nome, cognome, nascita, data, cittadinanza, residenza,
     * documento, rilasciato, data rilascio <br>
     * Gli altri componenti necessitano di nome, cognome, luogo e data di nascita <br>
     *
     * @param codice chiave della riga
     *
     * @return vero se le info delle persone sono sufficienti
     */
    public static boolean isSchedaValida(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean valida = true;
        int codCapo;
        int[] codMembri;
        int codTesta;
        Modulo modNotifica;
        Modulo modTesta;
        Date data;

        try {    // prova ad eseguire il codice

            /* recupera la data della scheda dal record di testa */
            modNotifica = NotificaModulo.get();
            codTesta = modNotifica.query().valoreInt(Notifica.Cam.linkTesta.get(), codice);
            modTesta = TestaStampeModulo.get();
            data = modTesta.query().valoreData(TestaStampe.Cam.data.get(), codTesta);

            /* recupera i codici del capogruppo scheda e dei membri */
            codCapo = getCodCapoGruppo(codice);
            codMembri = getCodMembri(codice);

            /* controlla il capogruppo scheda */
            valida = ClienteAlbergoModulo.isValidoNotifica(codCapo, true, data);

            /* controlla i membri */
            if (valida) {
                for(int cod : codMembri){
                    valida = ClienteAlbergoModulo.isValidoNotifica(cod, false, data);
                    if (!valida) {
                        break;
                    }// fine del blocco if
                }
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valida;
    }


//    /**
//     * Recupera i codici delle persone appartenenti alla scheda di notifica.
//     * <p/>
//     * Codici dei mebri del gruppo <br>
//     *
//     * @param table la jtable
//     * @param riga  da esaminare
//     *
//     * @return codici di tutte le persone del gruppo arrivate
//     */
//    private static int[] getCodMembri(JTable table, int riga) {
//        /* variabili e costanti locali di lavoro */
//        int[] codMembri = null;
//        boolean continua;
//        Dati dati = null;
//        String testo = "";
//
//        try {    // prova ad eseguire il codice
//            continua = (table != null);
//
//            /* recupera le persone (testo) */
//            if (continua) {
//                dati = getDati(table);
//                continua = (dati != null);
//            }// fine del blocco if
//
//            if (continua) {
//                testo = dati.getStringAt(riga, Cam.codPersone.get());
//                continua = (Lib.Testo.isValida(testo));
//            }// fine del blocco if
//
//            /* recupera le persone (codici) */
//            if (continua) {
//                codMembri = getCodMembri(testo);
//            }// fine del blocco if
//
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return codMembri;
//    }


    /**
     * Recupera i codici delle persone indicate nel testo.
     * <p/>
     * Codici dei mebri del gruppo <br>
     *
     * @param testo contenente i codici separati da virgola
     *
     * @return codici di tutte le persone del gruppo arrivate
     */
    private static int[] getCodMembri(String testo) {
        /* variabili e costanti locali di lavoro */
        int[] codMembri = new int[0];
        boolean continua;
        ArrayList<String> codPersone = null;
        ArrayList<Integer> numPersone;
        int cod;
        try {    // prova ad eseguire il codice
            continua = Lib.Testo.isValida(testo);

            /* recupera le persone (codici) */
            if (continua) {
                codPersone = Lib.Array.creaLista(testo);
                continua = (codPersone != null) && (codPersone.size() > 0);
            }// fine del blocco if

            if (continua) {
                numPersone = new ArrayList<Integer>();

                for (String stringa : codPersone) {
                    cod = Libreria.getInt(stringa);
                    if (cod > 0) {
                        numPersone.add(cod);
                    } // fine del blocco if
                } // fine del ciclo for-each

                codMembri = new int[numPersone.size()];
                for (int k = 0; k < codMembri.length; k++) {
                    codMembri[k] = numPersone.get(k);
                } // fine del ciclo for

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codMembri;
    }


    /**
     * Recupera i codici delle persone della riga.
     * <p/>
     *
     * @param codRiga chiave della riga
     *
     * @return codici di tutte le persone del gruppo arrivate compreso il capogruppo
     */
    public static int[] getCodMembri(int codRiga) {
        /* variabili e costanti locali di lavoro */
        int[] codMembri = new int[0];
        boolean continua;
        String testo = "";
        Modulo modNotifica = null;

        try {    // prova ad eseguire il codice
            continua = (codRiga > 0);

            if (continua) {
                modNotifica = NotificaModulo.get();
                continua = (modNotifica != null);
            }// fine del blocco if

            if (continua) {
                testo = modNotifica.query().valoreStringa(Cam.codPersone.get(), codRiga);
                continua = Lib.Testo.isValida(testo);
            } // fine del blocco if

            if (continua) {
                codMembri = getCodMembri(testo);
            } // fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codMembri;
    }


    /**
     * Recupera i codici degli altri membri del gruppo
     * (escluso il capogruppo).
     * <p/>
     *
     * @param codice chiave della riga
     *
     * @return codici di tutte le persone del gruppo arrivate escluso il capogruppo
     */
    public static int[] getCodAltriMembri(int codice) {
        /* variabili e costanti locali di lavoro */
        int[] codAltri = new int[0];
        int[] codTutti;
        ArrayList<Integer> lista = new ArrayList<Integer>();
        Integer unCod;
        int codCapo;
        boolean continua;

        try {    // prova ad eseguire il codice

            continua = (codice > 0);

            if (continua) {

                codTutti = getCodMembri(codice);
                codCapo = getCodCapoGruppo(codice);

                /* spazzola e aggiunge tutti tranne il capo */
                for (int cod : codTutti) {
                    if (cod != codCapo) {
                        lista.add(cod);
                    }// fine del blocco if
                }

                /* crea l'array in uscita */
                codAltri = new int[lista.size()];
                for (int k = 0; k < lista.size(); k++) {
                    unCod = lista.get(k);
                    codAltri[k] = unCod;
                } // fine del ciclo for

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codAltri;
    }


    /**
     * Controlla l'esistenza di altre persone, oltre l'intestatario della scheda.
     *
     * @param codice chiave della riga
     * @return true se esistono
     */
    public static boolean isEsistonoMembri(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean esistono = false;
        boolean continua;
        int[] codTutti = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi());

            if (continua) {
                codTutti = getCodMembri(codice);
                continua = (codTutti != null);
            }// fine del blocco if

            if (continua) {
                esistono = (codTutti.length > 1);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esistono;
    }


//    /**
//     * Recupera il codice del capogruppo.
//     * <p/>
//     *
//     * @param table la jtable
//     * @param riga  da esaminare
//     *
//     * @return codice del capogruppo
//     */
//    private static int getCodCapoGruppo(JTable table, int riga) {
//        /* variabili e costanti locali di lavoro */
//        int codCapoGruppo = 0;
//        boolean continua;
//        Dati dati = null;
//
//        try {    // prova ad eseguire il codice
//            continua = (table != null);
//
//            /* recupera i dati della tavola */
//            if (continua) {
//                dati = getDati(table);
//                continua = (dati != null);
//            }// fine del blocco if
//
//            /* recupera le persone (testo) */
//            if (continua) {
//                codCapoGruppo = dati.getIntAt(riga, Cam.linkCliente.get());
//            }// fine del blocco if
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return codCapoGruppo;
//    }


    /**
     * Recupera il codice del capogruppo.
     * <p/>
     *
     * @param codRiga codice chiave
     *
     * @return codice del capogruppo
     */
    public static int getCodCapoGruppo(int codRiga) {
        /* variabili e costanti locali di lavoro */
        int codCapoGruppo = 0;
        boolean continua;
        Modulo modNotifica = null;

        try {    // prova ad eseguire il codice
            continua = (codRiga > 0);

            if (continua) {
                modNotifica = NotificaModulo.get();
                continua = (modNotifica != null);
            }// fine del blocco if

            if (continua) {
                codCapoGruppo = modNotifica.query().valoreInt(Cam.linkCliente.get(), codRiga);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codCapoGruppo;
    }
    


    /**
     * Recupera i dati della tavola.
     * <p/>
     *
     * @param table la jtable
     *
     * @return codice del capogruppo
     */
    public static Dati getDati(JTable table) {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;
        boolean continua;
        Tavola tavola = null;
        TavolaModello modelloDati = null;

        try {    // prova ad eseguire il codice
            continua = (table != null);

            if (continua) {
                if (table instanceof Tavola) {
                    tavola = (Tavola)table;
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                modelloDati = tavola.getModello();
                continua = (modelloDati != null);
            }// fine del blocco if

            if (continua) {
                dati = modelloDati.getDati();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dati;
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

        try { // prova ad eseguire il codice

            wrapper = super.chkStampabile(codTesta);

            if (!this.isStampaValida(codTesta)) {
                testo = "La documentazione di alcuni clienti non è valida.";
                wrapper.addErrore(false, testo);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return wrapper;
    }


    /**
     * Restituisce i codici dei gruppi arrivati nel giorno.
     * <p/>
     *
     * @param codTesta codice del record di testa
     * @param conn     connessione da utilizzare
     *
     * @return codici di notifiche
     */
    public static int[] getCodici(int codTesta, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int[] codici = null;
        boolean continua;
        Modulo modNotifica;

        try { // prova ad eseguire il codice

            modNotifica = NotificaModulo.get();
            continua = (modNotifica != null);

            if (continua) {
                codici = modNotifica.query().valoriChiave(Notifica.Cam.linkTesta.get(), codTesta, conn);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }


    /**
     * Ritorna il nome del campo linkato al record di testa
     * specifico di questa logica.
     * <p/>
     *
     * @return il nome del campo link
     */
    protected String getNomeCampoLinkTesta() {
        return Notifica.Cam.linkTesta.get();
    }


    /**
     * Restituisce il codice di testa per la data indicata.
     * <p/>
     *
     * @param giorno di arrivo
     *
     * @return codice di testa
     *
     * @deprecated
     */
    public static int getCodTesta(Date giorno) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        boolean continua;
        Modulo modTesta = null;
        Filtro filtro;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(giorno));

            if (continua) {
                modTesta = TestaStampeModulo.get();
                continua = (modTesta != null);
            } // fine del blocco if

            if (continua) {
                filtro = FiltroFactory.crea(
                        TestaStampe.Cam.tipo.get(),
                        TestaStampe.TipoRegistro.notifica.getCodice());
                filtro.add(FiltroFactory.crea(TestaStampe.Cam.data.get(), giorno));
                codice = modTesta.query().valoreChiave(filtro);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Effettua le movimentazioni propedeutiche alla prima stampa.
     * <p/>
     *
     * @param codTesta codice del record di testa
     * @param conn     la connessione da utilizzare
     *
     * @return true se riuscito
     */
    protected boolean movimenta(int codTesta, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;

        try {    // prova ad eseguire il codice

            /* movimenta nella superclasse */
            continua = super.movimenta(codTesta, conn);

            /* qui eventuali movimentazioni specifiche... */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Crea il printer relativo a un giorno di stampa.
     * <p/>
     *
     * @param codTesta    codice del record di testa
     * @param conn        la connessione da utilizzare
     * @param primaStampa true per prima stampa false per ristampa
     *
     * @return il printer creato
     */
    protected PrinterObblig creaPrinter(int codTesta, Connessione conn, boolean primaStampa) {
        /* variabili e costanti locali di lavoro */
        PrinterObblig printer = null;
        boolean continua;
        J2FlowPrinter flowPrinter = null;
        Modulo modNotifica;
        Modulo modTesta;

        try { // prova ad eseguire il codice

            modNotifica = NotificaModulo.get();
            continua = (modNotifica != null);

            if (continua) {
                modTesta = TestaStampeModulo.get();
                continua = (modTesta != null);
            } // fine del blocco if

            /* crea il Printer completo */
            if (continua) {
                printer = new PrinterObblig(primaStampa);
            }// fine del blocco if

            if (continua) {
                flowPrinter = getSchede(codTesta, conn);
                continua = (flowPrinter != null);
            }// fine del blocco if

            /* crea il Printer completo */
            if (continua) {
                printer.addPageable(flowPrinter);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return printer;
    }


    /**
     * Assegna i numeri progressivi alle righe.
     * <p/>
     * Opera a seconda del tipo richiesto (ps, notifica, istat) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param codTesta codice del record di testa per il quale effettuare le regolazioni <br>
     * @param conn     la connessione da utilizzare
     *
     * @return true se riuscito
     */
    protected boolean assegnaProgressivi(int codTesta, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        int next = 0;
        int[] codici = null;
        Modulo modNotifica = null;
        Filtro filtro;
        Ordine ordine;
        Object ogg = null;
        int ultimo = 0;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (codTesta > 0);

            if (continua) {
                modNotifica = this.getModRighe();
                continua = (modNotifica != null);
            } // fine del blocco if

            if (continua) {

                /* recupera l'anno di competenza del documento */
                Modulo modTesta = TestaStampeModulo.get();
                Date data = modTesta.query().valoreData(TestaStampe.Cam.data.get(), codTesta);
                int anno = Lib.Data.getAnno(data);

                filtro = this.getFiltroTestaAnno(anno);
                ordine = new Ordine();
                ordine.add(Cam.progressivo.get());
                ogg = modNotifica.query().valoreUltimoRecord(Cam.progressivo.get(), filtro, ordine, conn);
            } // fine del blocco if

            if (continua) {
                if (ogg != null) {
                    ultimo = Libreria.getInt(ogg);
                } // fine del blocco if
                next = ultimo + 1;

                codici = this.getCodiciRighe(codTesta, conn);
                continua = (codici != null) && (codici.length > 0);
            } // fine del blocco if

            if (continua) {
                for (int cod : codici) {
                    modNotifica.query().registra(cod, Cam.progressivo.get(), next, conn);
                    next++;
                } // fine del ciclo for-each
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Costruisce le schede di notifica degli arrivi di una giornata.
     *
     * @param codTesta codice del record di testa
     * @param conn     connessione da utilizzare
     *
     * @return gli aggetti da stampare
     */
    private static J2FlowPrinter getSchede(int codTesta, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        J2FlowPrinter flowPrinter = null;
        boolean continua;
        int[] codiciGruppi;
        SchedaNotifica scheda;

        try { // prova ad eseguire il codice

            /* recupera i codici di ogni gruppo arivato nel giorno */
            codiciGruppi = getCodici(codTesta, conn);
            continua = (codiciGruppi != null);

            /* crea il Printer completo */
            if (continua) {
                flowPrinter = new J2FlowPrinter();
                for (int cod : codiciGruppi) {
                    scheda = new SchedaNotifica(cod, conn);
                    flowPrinter.addFlowable(scheda);
                    flowPrinter.addFlowable(new PageEject());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return flowPrinter;
    }


    /**
     * Ritorna la parte di base per il nome del file pdf.
     * <p/>
     *
     * @return il nome base del file .pdf
     */
    protected String getBaseFilePdf() {
        return "NOTIF";
    }


    /**
     * Ritorna il codice del tipo di record di testa.
     * <p/>
     */
    @Override
    protected int getCodTipoRecord() {
        return TestaStampe.TipoRegistro.notifica.getCodice();
    }


    /**
     * Ritorna il modulo delle righe.
     * <p/>
     */
    @Override
    protected Modulo getModRighe() {
        return NotificaModulo.get();
    }

}
