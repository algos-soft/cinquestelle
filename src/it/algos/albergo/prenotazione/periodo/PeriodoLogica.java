package it.algos.albergo.prenotazione.periodo;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.gestione.anagrafica.Anagrafica;

import java.util.Date;

public class PeriodoLogica {

    /**
     * Ritorna il codice dell'eventuale periodo che si sovrappone a un altro.
     * <p/>
     * Se ci fosse più di 1 periodo sovrapposto (in teoria non dovrebbe essere possibile),
     * torna il codice del primo periodo sovrapposto trovato.
     * Se non ci sono periodi sovrapposti, ritorna 0.
     *
     * @param dataIni data di inizio periodo
     * @param dataEnd data di fine periodo
     * @param codCam codice della camera
     * @param codEscludi codice dell'eventuale periodo da escludere dalla ricerca
     *
     * @return il codice dell'eventuale primo periodo sovrapposto, 0 se non ce ne sono
     */
    public static int getCodPeriodoSovrapposto(
            Date dataIni,
            Date dataEnd,
            int codCam,
            int codEscludi) {
        /* variabili e costanti locali di lavoro */
        int codSovrapposto = 0;
        boolean continua;
        Modulo mod;
        Filtro filtro;
        Filtro filtroIni;
        Filtro filtroEnd;
        Filtro filtroCam;
        Filtro filtroDisdette;
        Filtro filtroChiuse;
        Filtro filtroAzienda;
        Filtro filtroEscludi;
        PrenotazioneModulo moduloPren;
        Campo campo;
        int[] codici = null;

        try {    // prova ad eseguire il codice

            /* modulo utilizzato dalla scheda */
            mod = PeriodoModulo.get();

            /* controllo congruità date */
            continua = (!Lib.Data.isVuota(dataIni) && !Lib.Data.isVuota(dataEnd));

            /* controllo se esistono periodi sovrapposti */
            if (continua) {

                filtro = new Filtro();

                /* crea il filtro data iniziale */
                filtroIni = FiltroFactory.crea(
                        Periodo.Cam.arrivoPrevisto.get(),
                        Filtro.Op.MINORE,
                        dataEnd);

                /* crea il filtro data finale */
                filtroEnd = FiltroFactory.crea(
                        Periodo.Cam.partenzaPrevista.get(),
                        Filtro.Op.MAGGIORE,
                        dataIni);

                /* crea il filtro camera */
                filtroCam = FiltroFactory.crea(Periodo.Cam.camera.get(), codCam);

                /* crea il filtro no disdette */
                campo = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.disdetta.get());
                filtroDisdette = FiltroFactory.crea(campo, false);

//                /* crea il filtro no chiuse */
//                campo = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.chiusa.get());
//                filtroChiuse = FiltroFactory.crea(campo, false);

                /* recupera il filtro azienda */
                moduloPren = PrenotazioneModulo.get();
                filtroAzienda = moduloPren.getFiltroAzienda();

                filtro.add(filtroIni);
                filtro.add(filtroEnd);
                filtro.add(filtroCam);
                filtro.add(filtroDisdette);
//                filtro.add(filtroChiuse);
                filtro.add(filtroAzienda);

                if (codEscludi > 0) {
                    filtroEscludi = FiltroFactory.codice(mod, codEscludi);
                    filtro.add(Filtro.Op.AND_NOT, filtroEscludi);
                }// fine del blocco if

                codici = mod.query().valoriChiave(filtro);
                if (codici.length > 0) {
                    codSovrapposto = codici[0];
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codSovrapposto;
    }


    /**
     * Costruisce un messaggio specifico sull'occupazione di una camera per un periodo.
     * <p/>
     *
     * @param cod codice del periodo da documentare come già occupato
     *
     * @return testo del messaggio
     */
    public static String getMessaggioOccupato(int cod) {
        /* variabili e costanti locali di lavoro */
        String messaggio = "";
        Modulo mod;
        Campo campoInizio;
        Campo campoFine;
        Campo campoCamera;
        Campo campoCliente;
        Campo campoConfermata;
        Campo campoOpzione;
        Query query;
        Filtro filtro;
        Dati dati;

        Date data;

        String nomeCamera;
        String nomeCliente;
        String dataInizio;
        String dataFine;
        boolean confermata;
        boolean opzione;

        try { // prova ad eseguire il codice
            /* recupera il modulo */
            mod = PeriodoModulo.get();
            campoInizio = PeriodoModulo.get().getCampo(Periodo.Cam.arrivoPrevisto.get());
            campoFine = PeriodoModulo.get().getCampo(Periodo.Cam.partenzaPrevista.get());
            campoCamera = CameraModulo.get().getCampo(Camera.Cam.camera.get());
            campoCliente = ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.soggetto.get());
            campoConfermata = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.confermata.get());
            campoOpzione = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.opzione.get());

            query = new QuerySelezione(mod);
            query.addCampo(campoInizio);
            query.addCampo(campoFine);
            query.addCampo(campoCamera);
            query.addCampo(campoCliente);
            query.addCampo(campoConfermata);
            query.addCampo(campoOpzione);

            filtro = FiltroFactory.codice(mod, cod);
            query.setFiltro(filtro);

            dati = mod.query().querySelezione(query);

            data = dati.getDataAt(0, campoInizio);
            dataInizio = Lib.Data.getStringa(data);
            data = dati.getDataAt(0, campoFine);
            dataFine = Lib.Data.getStringa(data);
            nomeCamera = dati.getStringAt(0, campoCamera);
            nomeCliente = dati.getStringAt(0, campoCliente);
            confermata = dati.getBoolAt(0, campoConfermata);
            opzione = dati.getBoolAt(0, campoOpzione);

            messaggio += "dal ";
            messaggio += dataInizio;
            messaggio += " al ";
            messaggio += dataFine;
            messaggio += " la camera ";
            messaggio += nomeCamera;
            messaggio += " è già occupata da ";
            messaggio += nomeCliente;
            if (confermata) {
                messaggio += " (confermata)";
            } else {
                if (opzione) {
                    messaggio += " (opzione)";
                } else {
                    messaggio += " (non confermata)";
                }// fine del blocco if-else
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return messaggio;
    }


    /**
     * Costruisce un filtro per il modulo Periodo che seleziona
     * gli arrivi previsti per un dato periodo.
     * <p/>
     * Si basa su tutte le prenotazioni valide per l'azienda attiva.
     *
     * @param data1 data inizio (compresa, null per cominciare dal primo)
     * @param data2 data fine (compresa, null per arrivare all'ultimo)
     *
     * @return il filtro per gli arrivi
     */
    static Filtro getFiltroArrivi(Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroCambio;

        try {    // prova ad eseguire il codice

            /* filtro vuoto */
            filtro = new Filtro();

            /* filtro per le entrate */
            filtro.add(getFiltroEntrate(data1, data2));

            /* filtro no cambi */
            filtroCambio = FiltroFactory.crea(
                    Periodo.Cam.causaleArrivo.get(),
                    Filtro.Op.DIVERSO,
                    Periodo.CausaleAP.cambio.getCodice());
            filtro.add(filtroCambio);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Costruisce un filtro per il modulo Periodo che seleziona
     * le entrate (per arrivo o cambio) per un dato periodo.
     * <p/>
     * Si basa su tutte le prenotazioni valide.
     *
     * @param data1 data inizio (compresa, null per cominciare dal primo)
     * @param data2 data fine (compresa, null per arrivare all'ultimo)
     *
     * @return il filtro per le entrate
     */
    static Filtro getFiltroEntrate(Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroData;

        try {    // prova ad eseguire il codice

            /* filtro vuoto */
            filtro = new Filtro();

            /* filtro data inizio */
            if (!Lib.Data.isVuota(data1)) {
                filtroData = FiltroFactory.crea(
                        Periodo.Cam.arrivoPrevisto.get(),
                        Filtro.Op.MAGGIORE_UGUALE,
                        data1);
                filtro.add(filtroData);
            }// fine del blocco if

            /* filtro data fine */
            if (!Lib.Data.isVuota(data2)) {
                filtroData = FiltroFactory.crea(
                        Periodo.Cam.arrivoPrevisto.get(),
                        Filtro.Op.MINORE_UGUALE,
                        data2);
                filtro.add(filtroData);
            }// fine del blocco if

            /* filtro prenotazioni valide */
            filtro.add(PrenotazioneModulo.getFiltroValide());


//            /* filtro azienda attiva */
//            int codAzienda = AlbergoModulo.getCodAzienda();
//            if (codAzienda != 0) {
//                Modulo modPren = PrenotazioneModulo.get();
//                Campo campo = modPren.getCampo(Prenotazione.Cam.azienda);
//                Filtro filtroAzienda = FiltroFactory.crea(campo, codAzienda);
//                filtro.add(filtroAzienda);
//            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }



    /**
     * Costruisce un ordine per la visualizzazione degli arrivi
     * <p/>
     *
     * @return l'ordine per la visualizzazione degli arrivi
     *         (per data arrivo previsto e cliente)
     */
    static Ordine getOrdineArrivi() {
        /* variabili e costanti locali di lavoro */
        Ordine ordine = null;

        try { // prova ad eseguire il codice
            ordine = new Ordine();
            ordine.add(PeriodoModulo.get().getCampo(Periodo.Cam.arrivoPrevisto));
            ordine.add(ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.soggetto));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ordine;
    }


    /**
     * Costruisce un ordine per la visualizzazione delle partenze
     * <p/>
     *
     * @return l'ordine per la visualizzazione delle partenze
     *         (per data partenza prevista e cliente)
     */
    static Ordine getOrdinePartenze() {
        /* variabili e costanti locali di lavoro */
        Ordine ordine = null;

        try { // prova ad eseguire il codice
            ordine = new Ordine();
            ordine.add(PeriodoModulo.get().getCampo(Periodo.Cam.partenzaPrevista));
            ordine.add(ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.soggetto));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ordine;
    }


    /**
     * Restituisce gli arrivi previsti per un dato periodo.
     * <p/>
     *
     * @param data1 data inizio (compresa, null per cominciare dal primo)
     * @param data2 data fine (compresa, null per arrivare all'ultimo)
     *
     * @return gli arrivi previsti nel periodo
     */
    public static int[] getArrivi(Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */
        int[] arrivi = null;
        boolean continua;
        Filtro filtro = null;
        Ordine ordine = null;
        Modulo modPeriodo = null;
        Modulo modCliente = null;

        try {    // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(data1, data2));

            /* moduli */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                modCliente = ClienteAlbergoModulo.get();
            }// fine del blocco if

            /* filtro arrivi */
            if (continua) {
                filtro = getFiltroArrivi(data1, data2);
            }// fine del blocco if

            /* ordine arrivi */
            if (continua) {
                ordine = new Ordine();
                ordine.add(modPeriodo.getCampo(Periodo.Cam.arrivoPrevisto));
                ordine.add(modCliente.getCampo(Anagrafica.Cam.soggetto));
            }// fine del blocco if

            if (continua) {
                arrivi = modPeriodo.query().valoriChiave(filtro, ordine);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return arrivi;
    }


    /**
     * Numnero di persone previste in arrivo nel periodo.
     * <p/>
     *
     * @param data1 data inizio (compresa, null per cominciare dal primo)
     * @param data2 data fine (compresa, null per arrivare all'ultimo)
     *
     * @return numero di persone previste in arrivo nel periodo
     */
    public static int getPersoneArrivo(Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */
        int numeroPersone = 0;
        boolean continua;
        int[] periodi = null;
        Modulo mod = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(data1, data2));

            if (continua) {
                periodi = getArrivi(data1, data2);
                continua = (periodi != null) && (periodi.length > 0);
            } // fine del blocco if

            if (continua) {
                mod = PeriodoModulo.get();
                continua = (mod != null);
            } // fine del blocco if

            if (continua) {
                for (int cod : periodi) {
                    numeroPersone += mod.query().valoreInt(Periodo.Cam.persone.get(), cod);
                } // fine del ciclo for-each
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numeroPersone;
    }


    /**
     * Costruisce un filtro per il modulo Periodo che seleziona
     * le partenze per un dato intervallo di tempo.
     * <p/>
     * Si basa su tutte le prenotazioni valide per l'azienda attiva.
     *
     * @param data1 data inizio (null per cominciare dalla prima)
     * @param data2 data fine (null per arrivare all'ultima)
     *
     * @return il filtro per le partenze
     */
    static Filtro getFiltroPartenze(Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroCambio;

        try {    // prova ad eseguire il codice

            /* filtro vuoto */
            filtro = new Filtro();

            /* tutte le uscite del periodo */
            filtro.add(getFiltroUscite(data1, data2));

            /* filtro no cambi uscita */
            filtroCambio = FiltroFactory.crea(
                    Periodo.Cam.causalePartenza.get(),
                    Filtro.Op.DIVERSO,
                    Periodo.CausaleAP.cambio.getCodice());
            filtro.add(filtroCambio);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Costruisce un filtro per il modulo Periodo che seleziona
     * le uscite (per partenza o cambio) per un dato intervallo di tempo.
     * <p/>
     * Si basa su tutte le prenotazioni valide per l'azienda attiva.
     *
     * @param data1 data inizio (null per cominciare dalla prima)
     * @param data2 data fine (null per arrivare all'ultima)
     *
     * @return il filtro per le uscite
     */
    static Filtro getFiltroUscite(Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroData;

        try {    // prova ad eseguire il codice

            /* filtro vuoto */
            filtro = new Filtro();

            /* filtro prenotazioni valide */
            filtro.add(PrenotazioneModulo.getFiltroValide());

            /* filtro data inizio */
            if (!Lib.Data.isVuota(data1)) {
                filtroData = FiltroFactory.crea(
                        Periodo.Cam.partenzaPrevista.get(),
                        Filtro.Op.MAGGIORE_UGUALE,
                        data1);
                filtro.add(filtroData);
            }// fine del blocco if

            /* filtro data fine */
            if (!Lib.Data.isVuota(data2)) {
                filtroData = FiltroFactory.crea(
                        Periodo.Cam.partenzaPrevista.get(),
                        Filtro.Op.MINORE_UGUALE,
                        data2);
                filtro.add(filtroData);
            }// fine del blocco if

//            /* filtro azienda attiva */
//            int codAzienda = AlbergoModulo.getCodAzienda();
//            if (codAzienda != 0) {
//                Campo campo = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.azienda);
//                Filtro filtroAzienda = FiltroFactory.crea(campo, codAzienda);
//                filtro.add(filtroAzienda);
//            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }



    /**
     * Restituisce le partenze previste per un dato periodo.
     * <p/>
     *
     * @param data1 data inizio (compresa, null per cominciare dal primo)
     * @param data2 data fine (compresa, null per arrivare all'ultimo)
     *
     * @return le partenze previsti nel periodo
     */
    static int[] getPartenze(Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */
        int[] arrivi = null;
        boolean continua;
        Filtro filtro = null;
        Modulo mod = null;

        try {    // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(data1, data2));

            /* filtro arrivi */
            if (continua) {
                filtro = getFiltroPartenze(data1, data2);
            }// fine del blocco if

            if (continua) {
                mod = PeriodoModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                arrivi = mod.query().valoriChiave(filtro);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return arrivi;
    }


    /**
     * Numnero di persone previste in partenza nel periodo.
     * <p/>
     *
     * @param data1 data inizio (compresa, null per cominciare dal primo)
     * @param data2 data fine (compresa, null per arrivare all'ultimo)
     *
     * @return numero di persone previste in partenza nel periodo
     */
    static int getPersonePartenza(Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */
        int numeroPersone = 0;
        boolean continua;
        int[] periodi = null;
        Modulo mod = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(data1, data2));

            if (continua) {
                periodi = getPartenze(data1, data2);
                continua = (periodi != null) && (periodi.length > 0);
            } // fine del blocco if

            if (continua) {
                mod = PeriodoModulo.get();
                continua = (mod != null);
            } // fine del blocco if

            if (continua) {
                for (int cod : periodi) {
                    numeroPersone += mod.query().valoreInt(Periodo.Cam.persone.get(), cod);
                } // fine del ciclo for-each
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numeroPersone;
    }




}
