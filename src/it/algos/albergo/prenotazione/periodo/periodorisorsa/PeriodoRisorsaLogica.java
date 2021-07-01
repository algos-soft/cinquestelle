package it.algos.albergo.prenotazione.periodo.periodorisorsa;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.risorse.Risorsa;
import it.algos.albergo.risorse.RisorsaModulo;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsa;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.gestione.anagrafica.Anagrafica;

import java.util.Date;

public class PeriodoRisorsaLogica {

    /**
     * Ritorna il codice dell'eventuale impegno che si sovrappone a un altro.
     * <p/>
     * Se ci fosse più di 1 impegno sovrapposto (in teoria non dovrebbe essere possibile),
     * torna il codice del primo impegno sovrapposto trovato.
     * Se non ci sono impegni sovrapposti, o i dati non sono sufficienti per controllare, ritorna 0.
     *
     * @param dataIni data di inizio impegno
     * @param dataEnd data di fine impegno
     * @param tipoRisorsa codice del tipo di risorsa impegnata
     * @param codRisorsa codice della risorsa
     * @param codEscludi codice dell'eventuale impegno da escludere dalla ricerca
     *
     * @return il codice dell'eventuale primo impegno sovrapposto, 
     * 0 se non ce ne sono (o i dati non sono sufficienti per controllare)
     */
    public static int getCodImpegnoSovrapposto(
            Date dataIni,
            Date dataEnd,
            int tipoRisorsa,
            int codRisorsa,
            int codEscludi) {
        /* variabili e costanti locali di lavoro */
        int codSovrapposto = 0;
        boolean continua;
        Modulo mod;
        Filtro filtro;
        Filtro filtroIni;
        Filtro filtroEnd;
        Filtro filtroTipoRisorsa;
        Filtro filtroRisorsa;
        Filtro filtroDisdette;
        Filtro filtroAzienda;
        Filtro filtroEscludi;
        PrenotazioneModulo moduloPren;
        Campo campo;
        int[] codici = null;

        try {    // prova ad eseguire il codice


            /* controllo congruità date */
            continua = (!Lib.Data.isVuota(dataIni) && !Lib.Data.isVuota(dataEnd));
            
            // controllo congruità tipo risorsa
            continua = (tipoRisorsa!=0);
            
            // controllo congruità id risorsa
            continua = (codRisorsa!=0);



            /* controllo se esistono impegni sovrapposti */
            if (continua) {
            	
                /* modulo da controllare */
                mod = RisorsaPeriodoModulo.get();

                filtro = new Filtro();

                /* crea il filtro data iniziale */
                filtroIni = FiltroFactory.crea(
                        RisorsaPeriodo.Cam.dataInizio.get(),
                        Filtro.Op.MINORE,
                        dataEnd);

                /* crea il filtro data finale */
                filtroEnd = FiltroFactory.crea(
                		RisorsaPeriodo.Cam.dataFine.get(),
                        Filtro.Op.MAGGIORE,
                        dataIni);

                /* crea il filtro tipo risorsa */
                filtroTipoRisorsa = FiltroFactory.crea(RisorsaPeriodo.Cam.tipoRisorsa.get(), tipoRisorsa);

                /* crea il filtro risorsa */
                filtroRisorsa = FiltroFactory.crea(RisorsaPeriodo.Cam.risorsa.get(), codRisorsa);

                /* crea il filtro no disdette */
                campo = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.disdetta.get());
                filtroDisdette = FiltroFactory.crea(campo, false);

                /* recupera il filtro azienda */
                moduloPren = PrenotazioneModulo.get();
                filtroAzienda = moduloPren.getFiltroAzienda();

                filtro.add(filtroIni);
                filtro.add(filtroEnd);
                filtro.add(filtroTipoRisorsa);
                filtro.add(filtroRisorsa);
                filtro.add(filtroDisdette);
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
     * Costruisce un messaggio specifico sull'impegno di una risorsa per un periodo.
     * <p/>
     *
     * @param cod codice del record RisorsaPeriodo da documentare come già occupato
     *
     * @return testo del messaggio
     */
    public static String getMessaggioOccupato(int cod) {
        /* variabili e costanti locali di lavoro */
        String messaggio = "";

        try { // prova ad eseguire il codice
        	
            /* campi da recuperare nella query */
        	Campo campoSiglaTipoRisorsa = TipoRisorsaModulo.get().getCampo(TipoRisorsa.Cam.sigla);
        	Campo campoNumeroRisorsa = RisorsaModulo.get().getCampo(Risorsa.Cam.numero);
        	Campo campoInizio = RisorsaPeriodoModulo.get().getCampo(RisorsaPeriodo.Cam.dataInizio);
        	Campo campoFine = RisorsaPeriodoModulo.get().getCampo(RisorsaPeriodo.Cam.dataFine);
        	Campo campoNomeCliente = ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.soggetto);
        	Campo campoDataPrenotazione = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.dataPrenotazione);
        	Campo campoConfermata = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.confermata);
        	Campo campoOpzione = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.opzione);

            Modulo modRP = RisorsaPeriodoModulo.get();

            Query query = new QuerySelezione(modRP);
            query.addCampo(campoSiglaTipoRisorsa);
            query.addCampo(campoNumeroRisorsa);
            query.addCampo(campoInizio);
            query.addCampo(campoFine);
            query.addCampo(campoNomeCliente);
            query.addCampo(campoDataPrenotazione);
            query.addCampo(campoConfermata);
            query.addCampo(campoOpzione);


            Filtro filtro = FiltroFactory.codice(modRP, cod);
            query.setFiltro(filtro);

            Dati dati = modRP.query().querySelezione(query);

            String siglaTipoRisorsa = dati.getStringAt(campoSiglaTipoRisorsa);
            int numeroRisorsa = dati.getIntAt(campoNumeroRisorsa);
            String dataInizio = Lib.Data.getStringa(dati.getDataAt(campoInizio));
            String dataFine = Lib.Data.getStringa(dati.getDataAt(campoFine));
            String nomeCliente = dati.getStringAt(campoNomeCliente);
            String dataPrenotazione = Lib.Data.getStringa(dati.getDataAt(campoDataPrenotazione));
            boolean conf = dati.getBoolAt(campoConfermata);
            boolean opt = dati.getBoolAt(campoOpzione);

            
            messaggio += "La risorsa";
            messaggio += " "+siglaTipoRisorsa;
            messaggio += " "+numeroRisorsa;
            messaggio += " è già impegnata";
            messaggio += " dal "+dataInizio;
            messaggio += " al "+dataFine;
            messaggio += "\ndalla prenotazione di "+nomeCliente;
            messaggio += " del "+dataPrenotazione;
            if (conf) {
                messaggio += " (confermata)";
			}
            if (opt) {
                messaggio += " (opzione)";
			}

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return messaggio;
    }


}
