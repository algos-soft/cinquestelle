package it.algos.albergo.odg;

import com.wildcrest.j2printerworks.J2Printer;

import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.compoaccessori.WrapCompoAccessorio;
import it.algos.albergo.camera.zona.ZonaModulo;
import it.algos.albergo.odg.odgaccessori.OdgAccModulo;
import it.algos.albergo.odg.odgriga.OdgRiga;
import it.algos.albergo.odg.odgriga.OdgRigaModulo;
import it.algos.albergo.odg.odgrisorse.OdgRisorseStampa;
import it.algos.albergo.odg.odgtesta.Odg;
import it.algos.albergo.odg.odgtesta.OdgModulo;
import it.algos.albergo.odg.odgzona.OdgZona;
import it.algos.albergo.odg.odgzona.OdgZonaModulo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.stampa.Printer;
import it.algos.base.wrapper.SetValori;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;

/**
 * Logica del package ODG
 */
public class OdgLogica {

    /**
     * Genera tutti gli ODG per il periodo specificato.
     * <p/>
     *
     * @param d1 data di inizio
     * @param d2 data di fine
     *
     * @return l'elenco dei codici degli ODG esistenti o creati
     */
    static int[] generaOdgs(Date d1, Date d2) {
        /* variabili e costanti locali di lavoro */
        int[] arrayCod = new int[0];
        ArrayList<Integer> listaCod = new ArrayList<Integer>();
        int codOdg;

        try {    // prova ad eseguire il codice

            Date[] date = Lib.Data.getDateComprese(d1, d2);
            for (Date data : date) {
                codOdg = generaOdg(data);
                listaCod.add(codOdg);
            }

            /* converte la lista in array */
            arrayCod = Lib.Array.toIntArray(listaCod);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return arrayCod;


    }


    /**
     * Genera il singolo ODG per il giorno specificato.
     * <p/>
     *
     * @param data di riferimento
     *
     * @return il codice dell'Odg esistente o creato
     */
    static int generaOdg(Date data) {
        /* variabili e costanti locali di lavoro */
        int codOdg = 0;

        try {    // prova ad eseguire il codice

            codOdg = chkOdg(data);    // si assicura che esista, se no lo crea
            checkRigheZona(codOdg);       // crea le zone mancanti

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codOdg;

    }


    /**
     * Controlla se esiste l'ODG per il giorno specificato,
     * se non esiste lo crea.
     * <p/>
     *
     * @param data da controllare
     *
     * @return il codice dell'Odg esistente o creato
     */
    static int chkOdg(Date data) {
        /* variabili e costanti locali di lavoro */
        int codOdg = 0;

        try { // prova ad eseguire il codice

            Modulo modOdg = OdgModulo.get();
            Filtro filtro = FiltroFactory.crea(Odg.Cam.data.get(), data);
            int[] chiavi = modOdg.query().valoriChiave(filtro);
            if (chiavi.length > 0) {
                codOdg = chiavi[0];
            } else {      // nessuno, lo crea ora
                SetValori sv = new SetValori(modOdg);
                sv.add(Odg.Cam.data, data);
                sv.add(Odg.Cam.azienda, AlbergoModulo.getCodAzienda());     // crea il nuovo record per l'azienda attiva
                codOdg = modOdg.query().nuovoRecord(sv);
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codOdg;
    }


    /**
     * Genera le righe di zona per un ODG.
     * <p/>
     * Vengono generate solo le righe di zona non già esistenti
     *
     * @param codOdg codice dell'ODG
     */
    private static void checkRigheZona(int codOdg) {

        try {    // prova ad eseguire il codice

            int[] codiciZona = ZonaModulo.get().query().valoriChiave();
            for (int codZona : codiciZona) {
                Filtro filtro = new Filtro();
                filtro.add(FiltroFactory.crea(OdgZona.Cam.odg.get(), codOdg));
                filtro.add(FiltroFactory.crea(OdgZona.Cam.zona.get(), codZona));
                int codRigaZona = OdgZonaModulo.get().query().valoreChiave(filtro);
                if (codRigaZona <= 0) {
                    codRigaZona = generaRigheZona(codOdg, codZona);
                }// fine del blocco if

                /* genera le righe di ODG relative alla zona */
                generaRigheOdg(codRigaZona);

            }

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Genera una riga di zona per un ODG.
     * <p/>
     *
     * @param codOdg codice dell'ODG
     * @param codZona codice della zona di riferimento
     *
     * @return il codice del record creato
     */
    private static int generaRigheZona(int codOdg, int codZona) {
        /* variabili e costanti locali di lavoro */
        int codRec = 0;

        try {    // prova ad eseguire il codice

            Modulo mod = OdgZonaModulo.get();
            SetValori sv = new SetValori(mod);
            sv.add(OdgZona.Cam.odg, codOdg);
            sv.add(OdgZona.Cam.zona, codZona);
            codRec = mod.query().nuovoRecord(sv);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codRec;

    }


    /**
     * Genera le righe di ODG per una data riga di zona.
     * <p/>
     * Cancella tutte le righe esistenti generate da Periodo
     * Ricrea le righe in base alle Prenotazioni
     *
     * @param codRigaZona codice della riga di zona
     */
    private static void generaRigheOdg(int codRigaZona) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;

        try {    // prova ad eseguire il codice

            /* cancella le righe ODG relative alla riga di Zona */
            eliminaRigheOdg(codRigaZona);

            /* recupera il codice della zona */
            int codZona = OdgZonaModulo.get()
                    .query()
                    .valoreInt(OdgZona.Cam.zona.get(), codRigaZona);

            /* recupera l'elenco camere della zona in esame */
            filtro = FiltroFactory.crea(Camera.Cam.linkZona.get(), codZona);
            Ordine ordine = new Ordine(CameraModulo.get().getCampo(Camera.Cam.camera.get()));
            int[] codiciCamera = CameraModulo.get().query().valoriChiave(filtro, ordine);

            /* spazzola e per ogni camera crea la riga */
            for (int codCamera : codiciCamera) {
                generaRigaOdg(codRigaZona, codCamera);
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Cancella le righe di ODG per una data riga di zona.
     * <p/>
     * Cancella tutte le righe esistenti
     *
     * @param codRigaZona codice della riga di zona
     */
    private static void eliminaRigheOdg(int codRigaZona) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        Modulo modOdgRiga = OdgRigaModulo.get();

        try {    // prova ad eseguire il codice

            /* cancella le righe ODG relative alla riga di Zona */
            filtro = new Filtro();
            filtro.add(FiltroFactory.crea(OdgRiga.Cam.zona.get(), codRigaZona));
            modOdgRiga.query().eliminaRecords(filtro);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Genera la riga di ODG per una data riga di zona e camera.
     * <p/>
     *
     * @param codRigaZona codice della riga di zona
     * @param codCamera codice della camera
     */
    private static void generaRigaOdg(int codRigaZona, int codCamera) {
        /* variabili e costanti locali di lavoro */
        Modulo modRigaOdg = OdgRigaModulo.get();
        WrapInfoRigaOdg info;

        try { // prova ad eseguire il codice

            /* todo solo per debug */
            String stringa = CameraModulo.get()
                    .query()
                    .valoreStringa(Camera.Cam.camera.get(), codCamera);

            /* recupera il pacchetto informativo */
            info = new WrapInfoRigaOdg(codRigaZona, codCamera);

            /* crea il record */
            SetValori sv = new SetValori(modRigaOdg);
            sv.add(OdgRiga.Cam.zona.get(), codRigaZona);
            sv.add(OdgRiga.Cam.camera.get(), codCamera);

            sv.add(OdgRiga.Cam.periodo1.get(), info.getCodPeriodo1());
            sv.add(OdgRiga.Cam.periodo2.get(), info.getCodPeriodo2());
            sv.add(OdgRiga.Cam.fermata.get(), info.isFermata());
            sv.add(OdgRiga.Cam.arrivo.get(), info.isArrivo());
            sv.add(OdgRiga.Cam.partenza.get(), info.isPartenza());
            sv.add(OdgRiga.Cam.cambio.get(), info.isCambio());
            sv.add(OdgRiga.Cam.cambioDa.get(), info.getCodCamProvenienza());
            sv.add(OdgRiga.Cam.cambioA.get(), info.getCodCamDestinazione());
            sv.add(OdgRiga.Cam.parteDomani.get(), info.isParteDomani());
            sv.add(OdgRiga.Cam.cambiaDomani.get(), info.isCambiaDomani());
            sv.add(OdgRiga.Cam.chiudere.get(), info.isChiudere());
            sv.add(OdgRiga.Cam.dafare.get(), info.isDaFare());
            sv.add(OdgRiga.Cam.compoprecedente.get(), info.getSiglaCompoPrecedente());
            sv.add(OdgRiga.Cam.composizione.get(), info.getCodComposizione());
            sv.add(OdgRiga.Cam.note.get(), info.getNote());

            int codRiga = modRigaOdg.query().nuovoRecord(sv);
            creaAccessori(codRiga, info.getListaAccessori());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea gli accessori per una riga di Odg.
     * <p/>
     *
     * @param codRiga codice della riga
     * @param lista lista degli accessori con quantità
     */
    public static void creaAccessori(int codRiga, ArrayList<WrapCompoAccessorio> lista) {
        /* variabili e costanti locali di lavoro */
        Modulo modOdgAcc = OdgAccModulo.get();

        try {    // prova ad eseguire il codice
            for (WrapCompoAccessorio wrapper : lista) {
                SetValori sv = new SetValori(modOdgAcc);
                sv.add(OdgAccModulo.Cam.rigaOdg.get(), codRiga);
                sv.add(OdgAccModulo.Cam.accessorio.get(), wrapper.getCodAccessorio());
                sv.add(OdgAccModulo.Cam.quantita.get(), wrapper.getQtaAccessorio());
                modOdgAcc.query().nuovoRecord(sv);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Stampa un Odg.
     * <p/>
     *
     * @param codOdg il codice dell'Odg da stampare
     */
    public static boolean stampaOdg(int codOdg) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Modulo modOdgZona = OdgZonaModulo.get();

        try {    // prova ad eseguire il codice

            /**
             * recupera i codici delle righe di Zona ordinati per sequenza di Zona
             */
            Filtro filtro = FiltroFactory.crea(OdgZona.Cam.odg.get(), codOdg);
            Ordine ordine = new Ordine(ZonaModulo.get().getCampoOrdine());
            int[] codiciRZ = modOdgZona.query().valoriChiave(filtro, ordine);
            riuscito = stampaZoneOdg(codiciRZ);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }
    
    /**
     * Stampa un Odg di Risorse
     * <p/>
     *
     * @param data la data
     * @param codTipoRisorse l'id del tipo di risorse da stampare
     * @param note le eventuali note di stampa
     */
    public static boolean stampaOdgRisorse(Date data, int codTipoRisorse, String note) {
        boolean riuscito = true;
        boolean continua = true;
        String titolo="";

        /**
         * recupera la data dell'Odg e crea una stringa per il titolo
         */
        if (continua) {
            String nomeGiorno = Lib.Data.getGiorno(data);
            nomeGiorno = Lib.Testo.primaMaiuscola(nomeGiorno);
            String strData = nomeGiorno + " " + Lib.Data.getDataEstesa(data);
            titolo = "Ordine del Giorno di " + strData;
        }// fine del blocco if

        /**
         * crea lo stampabile ed esegue la stampa
         */
        if (continua) {
            Printer printer = new Printer();
            printer.setOrientation(J2Printer.PORTRAIT);
            printer.setCenterHeader(titolo);
            printer.setScale(0.58);	// è l'unico modo per non avere i bordi spessi
        	OdgRisorseStampa stampa = new OdgRisorseStampa(codTipoRisorse, data, note, printer);
            printer.addPageable(stampa);
            printer.showPrintPreviewDialog();
        }// fine del blocco if

        return riuscito;
    }



    /**
     * Stampa un elenco di Zone di Odg.
     * <p/>
     * Le zone devono appartenere allo stesso Odg!
     * @param codiciRigheZona l'elenco dei codici delle Zone di Odg da stampare
     * @return true se riuscito
     */
    public static boolean stampaZoneOdg(int[] codiciRigheZona) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        int codOdg=0;
        Filtro filtro;
        ArrayList lista;
        int[] codiciZona=null;
        String titolo="";
        Modulo modOdgZona = OdgZonaModulo.get();

        try {    // prova ad eseguire il codice

            /**
             * Recupera la lista dei codici di Odg relativi alle righe
             */
            filtro = FiltroFactory.elenco(modOdgZona, codiciRigheZona);
            lista = modOdgZona.query().valoriCampo(OdgZona.Cam.odg.get(), filtro);
            int[] codiciOdg = Lib.Array.toIntArray(lista);
            continua = (codiciOdg.length > 0);

            /**
             * Controlla che i codici ODG siano tutti uguali
             */
            if (continua) {
                codOdg = codiciOdg[0];
                for (int cod : codiciOdg) {
                    if (cod != codOdg) {
                        new MessaggioAvviso(
                                "Errore: non si possono stampare insieme righe di ODG diversi.");
                        continua = false;
                        break;
                    }// fine del blocco if
                }
            }// fine del blocco if

            /**
             * recupera la data dell'Odg e crea una stringa per il titolo
             */
            if (continua) {
                Date data = OdgModulo.get().query().valoreData(Odg.Cam.data.get(), codOdg);
                String nomeGiorno = Lib.Data.getGiorno(data);
                nomeGiorno = Lib.Testo.primaMaiuscola(nomeGiorno);
                String strData = nomeGiorno + " " + Lib.Data.getDataEstesa(data);
                titolo = "Ordine del Giorno di " + strData;
            }// fine del blocco if

            /**
             * Recupera la lista dei codici di Zona relativi alle righe
             */
            if (continua) {
                filtro = FiltroFactory.elenco(modOdgZona, codiciRigheZona);
                lista = modOdgZona.query().valoriCampo(OdgZona.Cam.zona.get(), filtro);
                codiciZona = Lib.Array.toIntArray(lista);
                continua = (codiciZona.length > 0);
            }// fine del blocco if

            /**
             * crea lo stampabile ed esegue la stampa
             */
            if (continua) {
                OdgStampa stampa = new OdgStampa(codOdg, codiciZona);
                Printer printer = new Printer();
                printer.setOrientation(J2Printer.LANDSCAPE);
                printer.setCenterHeader(titolo);
                printer.addPageable(stampa);
                printer.setScale(0.58);
                printer.showPrintPreviewDialog();
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Ritorna il codice della riga di zona dato il codice della riga di Odg.
     * <p/>
     *
     * @param codRigaOdg codice della riga di odg
     *
     * @return codice della riga di zona
     */
    private static int getCodRigaZona(int codRigaOdg) {
        return OdgRigaModulo.get().query().valoreInt(OdgRiga.Cam.zona.get(), codRigaOdg);
    }


    /**
     * Ritorna il codice dell'Odg dato il codice della riga di Zona.
     * <p/>
     *
     * @param codRigaZona codice della riga di Zona
     *
     * @return codice della riga di Odg
     */
    private static int getCodOdg(int codRigaZona) {
        return OdgZonaModulo.get().query().valoreInt(OdgZona.Cam.odg.get(), codRigaZona);
    }


}