package it.algos.albergo.storico5stelle;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.finestra.Finestra;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.SetValori;
import it.algos.gestione.anagrafica.Anagrafica;

import java.io.File;
import java.util.Date;
import java.util.List;

public class StoricoLogica {

    /**
     * Importa lo storico da un file .csv.
     * <p/>
     *
     * @param inputFile il file da leggere
     * @param nav navigatore sul quale mostrare il progresso dell'operazione nel titolo finestra
     *
     * @return true se eseguito
     */
    public static boolean importaStorico(File inputFile, Navigatore nav) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        java.util.List<java.util.List<String>> lista;
        int i;
        Modulo modStorico;
        SetValori sv;
        int codNuovo;
        int counter = 0;

        try { // prova ad eseguire il codice

            modStorico = StoricoModulo.get();

            lista = Lib.Imp.importCSV(inputFile.getPath());

            /* rimuove il primo elemento (i titoli di colonna) */
            lista.remove(0);

            for (List<String> riga : lista) {

                counter++;

                i = -1;
                i++;
                String str_cognome = riga.get(i);
                i++;
                String str_nome = riga.get(i);
                i++;
                String str_datanascita = riga.get(i);
                i++;
                String str_entrata = riga.get(i);
                i++;
                String str_uscita = riga.get(i);
                i++;
                String str_camera = riga.get(i);
                i++;
                String str_bambino = riga.get(i);
                i++;
                String str_pensione = riga.get(i);
                i++;
                String str_ps = riga.get(i);
                i++;
                String str_codentrata = riga.get(i);
                i++;
                String str_coduscita = riga.get(i);
                i++;
                String str_x = riga.get(i);

                Date datanascita = Lib.Data.getData(str_datanascita);
                Date dataentrata = Lib.Data.getData(str_entrata);
                Date datauscita = Lib.Data.getData(str_uscita);
                boolean bambino = (str_bambino.equals("VERO"));
                int numps = Libreria.getInt(str_ps);
                boolean x = (str_x.equals("VERO"));

                int codCliente = getCodCliente(str_cognome, str_nome, datanascita);
                int codCamera = getCodCamera(str_camera);
                boolean cambioEntrata = (str_codentrata.equals("C"));
                boolean cambioUscita = (str_coduscita.equals("C"));

                int codAzienda;
                if (!x) {
                    codAzienda = AziendaModulo.get().getRecordPreferito();

//                    /**
//                     * se è l'azienda principale e manca il n. di PS (errore!)
//                     * mette un numero di PS fittizio
//                     */
//                    if (numps==0) {
//                        numps=99999;
//                    }// fine del blocco if

                } else {
                    codAzienda = 2;
                }// fine del blocco if-else

                int codPensione = 0;
                if (str_pensione.equals("BB")) {
                    codPensione = Listino.PensioniPeriodo.pernottamento.getCodice();
                }// fine del blocco if
                if (str_pensione.equals("HB")) {
                    codPensione = Listino.PensioniPeriodo.mezzaPensione.getCodice();
                }// fine del blocco if
                if (str_pensione.equals("FB")) {
                    codPensione = Listino.PensioniPeriodo.pensioneCompleta.getCodice();
                }// fine del blocco if

                sv = new SetValori(modStorico);
                sv.add(Storico.Cam.cognome.get(), str_cognome);
                sv.add(Storico.Cam.nome.get(), str_nome);
                sv.add(Storico.Cam.datanascita.get(), datanascita);
                sv.add(Storico.Cam.entrata.get(), dataentrata);
                sv.add(Storico.Cam.uscita.get(), datauscita);
                sv.add(Storico.Cam.stringacamera.get(), str_camera);
                sv.add(Storico.Cam.bambino.get(), bambino);
                sv.add(Storico.Cam.stringapensione.get(), str_pensione);
                sv.add(Storico.Cam.ps.get(), numps);

                /* campi calcolati */
                sv.add(Storico.Cam.cliente.get(), codCliente);
                sv.add(Storico.Cam.camera.get(), codCamera);
                sv.add(Storico.Cam.cambioEntrata.get(), cambioEntrata);
                sv.add(Storico.Cam.cambioUscita.get(), cambioUscita);
                sv.add(Storico.Cam.azienda.get(), codAzienda);
                sv.add(Storico.Cam.pensione.get(), codPensione);

                codNuovo = modStorico.query().nuovoRecord(sv.getListaValori());

                if (codNuovo < 1) {
                    new MessaggioAvviso("Creazione record non riuscita.\nOperazione interrotta.");
                    riuscito = false;
                    break;
                }// fine del blocco if

                /* aggiorna lo stato */
                Finestra fin = nav.getPortaleNavigatore().getFinestra();
                fin.setTitolo("Record importati: " + counter);

            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Recupera il codice cliente in Anagrafica.
     * <p/>
     *
     * @param cognome il cognome
     * @param nome il nome
     * @param dataNascita (solo per risolvere in caso di omonimia)
     *
     * @return il codice del cliente, 0 se non trovato
     */
    private static int getCodCliente(String cognome, String nome, Date dataNascita) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Filtro filtro;
        ClienteAlbergoModulo modCliente;
        int quanti;

        try {    // prova ad eseguire il codice

            modCliente = ClienteAlbergoModulo.get();

            filtro = new Filtro();
            filtro.add(Anagrafica.Cam.cognome.get(), Filtro.Op.UGUALE, cognome);
            filtro.add(Anagrafica.Cam.nome.get(), Filtro.Op.UGUALE, nome);
            filtro.setCaseSensitive(false);

            quanti = modCliente.query().contaRecords(filtro);

            switch (quanti) {
                case 0:    // nessuno
                    break;
                case 1:    // uno
                    codice = modCliente.query().valoreChiave(filtro);
                    break;
                default: // molti

                    filtro.add(ClienteAlbergo.Cam.dataNato.get(), Filtro.Op.UGUALE, dataNascita);
                    quanti = modCliente.query().contaRecords(filtro);
                    if (quanti == 1) {
                        codice = modCliente.query().valoreChiave(filtro);
                    }// fine del blocco if

            } // fine del blocco switch

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Recupera il codice della camera dal nome.
     * <p/>
     *
     * @param nomeCamera il nome della camera
     *
     * @return il codice della camera, 0 se non trovato
     */
    private static int getCodCamera(String nomeCamera) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Filtro filtro;
        Modulo modCamera;
        int quanti;

        try {    // prova ad eseguire il codice

            modCamera = CameraModulo.get();

            filtro = new Filtro();
            filtro.add(Camera.Cam.camera.get(), Filtro.Op.UGUALE, nomeCamera);
            filtro.setCaseSensitive(false);

            quanti = modCamera.query().contaRecords(filtro);
            if (quanti == 1) {
                codice = modCamera.query().valoreChiave(filtro);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Crea le presenze dallo storico.
     * <p/>
     *
     * @param nav navigatore sul quale mostrare il progresso dell'operazione nel titolo finestra
     *
     * @return true se riuscito
     */
    public static boolean creaPresenze(Navigatore nav) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Modulo modStorico;
        Modulo modPresenze;
        int[] chiaviStor;
        SetValori sv;
        Query query;
        Filtro filtro;
        Ordine ordine;
        Dati dati;
        int codNuovo;
        int counter = 0;

        try {    // prova ad eseguire il codice

            modStorico = StoricoModulo.get();
            modPresenze = PresenzaModulo.get();

            /* tutte le chiavi di Storico in ordine di data entrata */
            ordine = new Ordine();
            ordine.add(Storico.Cam.entrata.get());
            chiaviStor = modStorico.query().valoriChiave(ordine);

            /* spazzola e per ognuna crea la presenza */
            for (int codStor : chiaviStor) {

                counter++;

                query = new QuerySelezione(modStorico);
                query.addCampo(Storico.Cam.cliente);
                query.addCampo(Storico.Cam.entrata);
                query.addCampo(Storico.Cam.uscita);
                query.addCampo(Storico.Cam.camera);
                query.addCampo(Storico.Cam.bambino);
                query.addCampo(Storico.Cam.pensione);
                query.addCampo(Storico.Cam.ps);
                query.addCampo(Storico.Cam.cambioEntrata);
                query.addCampo(Storico.Cam.cambioUscita);
                query.addCampo(Storico.Cam.azienda);

                filtro = FiltroFactory.codice(modStorico, codStor);
                query.setFiltro(filtro);

                dati = modStorico.query().querySelezione(query);
                int codCliente = dati.getIntAt(Storico.Cam.cliente.get());
                Date dataEntrata = dati.getDataAt(Storico.Cam.entrata.get());
                Date dataUscita = dati.getDataAt(Storico.Cam.uscita.get());
                int codCamera = dati.getIntAt(Storico.Cam.camera.get());
                boolean bambino = dati.getBoolAt(Storico.Cam.bambino.get());
                int codPensione = dati.getIntAt(Storico.Cam.pensione.get());
//                int ps = dati.getIntAt(Storico.Cam.ps.get());
                boolean cambioEntrata = dati.getBoolAt(Storico.Cam.cambioEntrata.get());
                boolean cambioUscita = dati.getBoolAt(Storico.Cam.cambioUscita.get());
                int azienda = dati.getIntAt(Storico.Cam.azienda.get());
                dati.close();

                sv = new SetValori(modPresenze);
                sv.add(Presenza.Cam.cliente, codCliente);
                sv.add(Presenza.Cam.entrata, dataEntrata);
                sv.add(Presenza.Cam.uscita, dataUscita);
                sv.add(Presenza.Cam.camera, codCamera);
                sv.add(Presenza.Cam.bambino, bambino);
                sv.add(Presenza.Cam.pensione, codPensione);
//                sv.add(Presenza.Cam.ps, ps);
                sv.add(Presenza.Cam.cambioEntrata, cambioEntrata);
                sv.add(Presenza.Cam.cambioUscita, cambioUscita);
                sv.add(Presenza.Cam.azienda, azienda);

                sv.add(Presenza.Cam.chiuso, true);
                sv.add(Presenza.Cam.arrivo, dataEntrata);

                codNuovo = modPresenze.query().nuovoRecord(sv.getListaValori());
                if (codNuovo <= 0) {
                    new MessaggioAvviso("Creazione record di Presenza non riuscita.\nOperazione interrotta.");
                    riuscito = false;
                    break;
                }// fine del blocco if

                /* aggiorna lo stato */
                Finestra fin = nav.getPortaleNavigatore().getFinestra();
                fin.setTitolo("Presenze create: " + counter);

            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


}