package it.algos.albergo.arrivipartenze;

import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.J2PanelPrinter;
import com.wildcrest.j2printerworks.J2TextPrinter;
import com.wildcrest.j2printerworks.PageEject;
import com.wildcrest.j2printerworks.VerticalGap;
import it.algos.albergo.arrivipartenze.riepilogo.RiepilogoNew;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoLogica;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.stampa.Printer;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class StampaMovimenti {


    /**
     * Stampa il report degli arrivi per il periodo indicato.
     * <p/>
     * Recupera la lista dei periodi compresi nelle date indicate <br>
     * Suddivide i periodi in un pacchetto di prenotazioni (ognuna coi propri periodi) <br>
     *
     * @param dataInizio degli arrivi da stampare
     * @param dataFine degli arrivi da stampare
     *
     * @return true se riuscito
     */
    public static boolean stampaArrivi(Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        J2FlowPrinter flowPrinter = null;
        boolean riuscito = false;
        boolean continua;
        Printer printer;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(dataInizio, dataFine));

            if (continua) {
                flowPrinter = creaPrinterArrivi(dataInizio, dataFine);
                continua = (flowPrinter != null);
            }// fine del blocco if

            /* crea il Printer completo */
            if (continua) {
                printer = new Printer();
                printer.addPageable(flowPrinter);
                printer.print();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Crea un printer per la stampa degli arrivi per il periodo indicato.
     * <p/>
     * Recupera la lista dei periodi compresi nelle date indicate <br>
     * Suddivide i periodi in un pacchetto di prenotazioni (ognuna coi propri periodi) <br>
     *
     * @param dataInizio degli arrivi da stampare
     * @param dataFine degli arrivi da stampare
     *
     * @return il printer creato
     */
    private static J2FlowPrinter creaPrinterArrivi(Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        J2FlowPrinter flowPrinter = null;
        boolean continua;
        int[] lista = null;
        ArrayList<PrenotazionePeriodiWrap> prenotazioni = null;
        ArrayList<ArrivoPartenzaWrap> gruppo = null;
        ArrivoPartenzaWrap wrapStampa;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(dataInizio, dataFine));

            if (continua) {
                lista = PeriodoLogica.getArrivi(dataInizio, dataFine);
                continua = (lista != null) && (lista.length > 0);
            }// fine del blocco if

            if (continua) {
                prenotazioni = getPrenotazioni(lista);
                continua = (prenotazioni != null) && (prenotazioni.size() > 0);
            }// fine del blocco if

            if (continua) {
                gruppo = new ArrayList<ArrivoPartenzaWrap>();

                for (PrenotazionePeriodiWrap wrap : prenotazioni) {
                    wrapStampa = new ArrivoWrap(wrap);
                    gruppo.add(wrapStampa);
                } // fine del ciclo for-each

                continua = (gruppo.size() > 0);
            }// fine del blocco if

            /* crea il Printer completo */
            if (continua) {
                flowPrinter = new J2FlowPrinter();
                flowPrinter.addFlowable(creaTitoloArrivi(dataInizio, dataFine));
                flowPrinter.addFlowable(new VerticalGap());

                for (ArrivoPartenzaWrap arrivo : gruppo) {
                    flowPrinter.addFlowable(arrivo);
                    flowPrinter.addFlowable(new VerticalGap());
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return flowPrinter;
    }


    /**
     * Stampa il report delle partenze per il periodo indicato.
     * <p/>
     * Recupera la lista dei periodi compresi nelle date indicate <br>
     * Suddivide i periodi in un pacchetto di prenotazioni (ognuna coi propri periodi) <br>
     *
     * @param dataInizio delle partenze da stampare
     * @param dataFine delle partenze da stampare
     *
     * @return true se riuscito
     */
    public static boolean stampaPartenze(Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua;
        Printer printer;
        J2FlowPrinter flowPrinter = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(dataInizio, dataFine));

            if (continua) {
                flowPrinter = creaPrinterPartenze(dataInizio, dataFine);
                continua = (flowPrinter != null);
            }// fine del blocco if

            /* crea il Printer completo */
            if (continua) {

                printer = new Printer();
                printer.addPageable(flowPrinter);
                printer.print();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Crea un printer per le partenze per il periodo indicato.
     * <p/>
     * Recupera la lista dei periodi compresi nelle date indicate <br>
     * Suddivide i periodi in un pacchetto di prenotazioni (ognuna coi propri periodi) <br>
     *
     * @param dataInizio delle partenze da stampare
     * @param dataFine delle partenze da stampare
     *
     * @return il printer creato
     */
    private static J2FlowPrinter creaPrinterPartenze(Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        J2FlowPrinter flowPrinter = null;
        boolean continua;
        int[] lista = null;
        ArrayList<PrenotazionePeriodiWrap> prenotazioni = null;
        ArrayList<ArrivoPartenzaWrap> gruppo = null;
        ArrivoPartenzaWrap wrapStampa;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(dataInizio, dataFine));

            if (continua) {
                lista = PeriodoModulo.getPartenze(dataInizio, dataFine);
                continua = (lista != null) && (lista.length > 0);
            }// fine del blocco if

            if (continua) {
                prenotazioni = getPrenotazioni(lista);
                continua = (prenotazioni != null) && (prenotazioni.size() > 0);
            }// fine del blocco if

            if (continua) {
                gruppo = new ArrayList<ArrivoPartenzaWrap>();

                for (PrenotazionePeriodiWrap wrap : prenotazioni) {
                    wrapStampa = new PartenzaWrap(wrap);
                    gruppo.add(wrapStampa);
                } // fine del ciclo for-each

                continua = (gruppo.size() > 0);
            }// fine del blocco if

            /* crea il Printer completo */
            if (continua) {
                flowPrinter = new J2FlowPrinter();
                flowPrinter.addFlowable(creaTitoloPartenze(dataInizio, dataFine));
                flowPrinter.addFlowable(new VerticalGap());

                for (ArrivoPartenzaWrap arrivo : gruppo) {
                    flowPrinter.addFlowable(arrivo);
                    flowPrinter.addFlowable(new VerticalGap());
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return flowPrinter;
    }


    /**
     * Crea un printer per i cambi per il periodo indicato.
     * <p/>
     *
     * @param dataInizio data iniziale (compresa)
     * @param dataFine data finale (compresa)
     *
     * @return il printer creato
     */
    private static J2FlowPrinter creaPrinterCambi(Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        J2FlowPrinter flowPrinter = null;
        boolean continua;
        int[] cambiUscita = null;
        ArrayList<PrenotazionePeriodiWrap> prenotazioni = null;
        ArrayList<ArrivoPartenzaWrap> gruppo = null;
        ArrivoPartenzaWrap wrapStampa;
        Filtro filtro;
        Modulo modPeriodo;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(dataInizio, dataFine));

            /* recupera l'elenco dei periodi con cambio in uscita */
            if (continua) {
                filtro = PeriodoModulo.getFiltroCambiUscita(dataInizio, dataFine);
                modPeriodo = PeriodoModulo.get();
                cambiUscita = modPeriodo.query().valoriChiave(filtro);
            }// fine del blocco if

            /* recupera l'elenco univoco delle relative prenotazioni */
            if (continua) {
                prenotazioni = getPrenotazioni(cambiUscita);
                continua = (prenotazioni != null) && (prenotazioni.size() > 0);
            }// fine del blocco if

            /* crea un wrapper per ogni prenotazione contenente i periodi che cambiano
             * aggiunge i wrapper a un gruppo */
            if (continua) {
                gruppo = new ArrayList<ArrivoPartenzaWrap>();
                for (PrenotazionePeriodiWrap wrap : prenotazioni) {
                    wrapStampa = new CambioWrap(wrap);
                    gruppo.add(wrapStampa);
                } // fine del ciclo for-each
                continua = (gruppo.size() > 0);
            }// fine del blocco if

            /* crea il Printer completo */
            if (continua) {
                flowPrinter = new J2FlowPrinter();
                flowPrinter.addFlowable(creaTitoloCambi(dataInizio, dataFine));
                flowPrinter.addFlowable(new VerticalGap());

                for (ArrivoPartenzaWrap cambio : gruppo) {
                    flowPrinter.addFlowable(cambio);
                    flowPrinter.addFlowable(new VerticalGap());
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return flowPrinter;
    }


    /**
     * Stampa il prospetto finale dei totali.
     * <p/>
     * Recupera i presenti in albergo il giorno precedente alla dataIOnizio <br>
     * Recupera gli arrivi previsti nel periodo <br>
     * Recupera le partenze previste nel periodo <br>
     * Recupera i presenti di oggi in albergo <br>
     * <p/>
     * @param data1 data iniziale
     * @param data2 data finale
     * @return il printer del riepilogo
     */
    private static J2PanelPrinter getProspetto(Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */
        J2PanelPrinter panPrinter = null;
        boolean continua;
        RiepilogoNew riepilogo;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(data1, data2));

            if (continua) {

                riepilogo=new RiepilogoNew();
                riepilogo.aggiorna(data1,data2);

                /* crea il Printer completo */
                panPrinter = new J2PanelPrinter();
                panPrinter.setPanel(riepilogo.getPanFisso());

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return panPrinter;
    }


    /**
     * Titolo e spiegazione all'inizio della stampa degli arrivi.
     *
     * @param dataInizio degli arrivi da stampare
     * @param dataFine degli arrivi da stampare
     * @return il printer per il titolo
     */
    private static J2TextPrinter creaTitoloArrivi(Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        J2TextPrinter titoli = null;
        String testo;

        try { // prova ad eseguire il codice
            testo = "Arrivi del giorno " + Lib.Data.getStringa(dataInizio);
            titoli = new J2TextPrinter(testo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return titoli;
    }


    /**
     * Titolo e spiegazione all'inizio della stampa degli arrivi.
     *
     * @param dataInizio degli arrivi da stampare
     * @param dataFine degli arrivi da stampare
     * @return il printer
     */
    private static J2TextPrinter creaTitoloPartenze(Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        J2TextPrinter titoli = null;
        String testo;

        try { // prova ad eseguire il codice
            testo = "Partenze del giorno " + Lib.Data.getStringa(dataInizio);
            titoli = new J2TextPrinter(testo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return titoli;
    }


    /**
     * Titolo e spiegazione all'inizio della stampa dei cambi.
     *
     * @param dataInizio data iniziale (compresa)
     * @param dataFine data finale (compresa)
     * @return il printer del titolo
     */
    private static J2TextPrinter creaTitoloCambi(Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        J2TextPrinter titoli = null;
        String testo;

        try { // prova ad eseguire il codice
            testo = "Cambi del giorno " + Lib.Data.getStringa(dataInizio);
            titoli = new J2TextPrinter(testo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return titoli;
    }



    /**
     * Suddivide i periodi in un pacchetto di prenotazioni (ognuna coi propri periodi).
     * <p/>
     * Per ogni periodo recupero il link alla prenotazione <br>
     * Uso una mappa di appoggio <br>
     * <p/>
     * Costruisco una lista unica di codici <br>
     *
     * @param lista dei periodi da elaborare
     *
     * @return pacchetto di prenotazioni (ognuna coi propri periodi)
     */
    private static ArrayList<PrenotazionePeriodiWrap> getPrenotazioni(int[] lista) {
        /* variabili e costanti locali di lavoro */
        ArrayList<PrenotazionePeriodiWrap> prenotazioni = null;
        boolean continua;
        Modulo modPeriodo = null;
        int link;
        LinkedHashMap<Integer, PrenotazionePeriodiWrap> mappa = null;
        PrenotazionePeriodiWrap prenotazione;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.length > 0);

            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = (modPeriodo != null);
            }// fine del blocco if

            if (continua) {
                mappa = new LinkedHashMap<Integer, PrenotazionePeriodiWrap>();

                for (int cod : lista) {
                    link = modPeriodo.query().valoreInt(Periodo.Cam.prenotazione.get(), cod);

                    if (mappa.containsKey(link)) {
                        prenotazione = mappa.get(link);
                    } else {
                        prenotazione = new PrenotazionePeriodiWrap(link);
                        mappa.put(link, prenotazione);
                    }// fine del blocco if-else
                    prenotazione.add(cod);
                } // fine del ciclo for-each

                continua = (mappa.size() > 0);
            }// fine del blocco if

            if (continua) {
                prenotazioni = new ArrayList<PrenotazionePeriodiWrap>();

                for (Map.Entry map : mappa.entrySet()) {
                    prenotazioni.add((PrenotazionePeriodiWrap)map.getValue());
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return prenotazioni;
    }


//    /**
//     * Costruisce un oggetto di tipo stampabile.
//     * <p/>
//     *
//     * @param wrap con prenotazione e periodi interessati
//     *
//     * @return oggetto stampabile
//     */
//    private static ArrivoPartenzaWrap creaArrivo(PrenotazionePeriodiWrap wrap) {
//        /* variabili e costanti locali di lavoro */
//        ArrivoPartenzaWrap arrivo = null;
//        boolean continua;
//
//        try { // prova ad eseguire il codice
//            /* controllo di congruità */
//            continua = (wrap != null);
//
//            if (continua) {
//                arrivo = new ArrivoWrap(wrap);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return arrivo;
//    }


//    /**
//     * Costruisce un oggetto di tipo stampabile.
//     * <p/>
//     *
//     * @param wrap con prenotazione e periodi interessati
//     *
//     * @return oggetto stampabile
//     */
//    private static ArrivoPartenzaWrap creaPartenza(PrenotazionePeriodiWrap wrap) {
//        /* variabili e costanti locali di lavoro */
//        ArrivoPartenzaWrap arrivo = null;
//        boolean continua;
//
//        try { // prova ad eseguire il codice
//            /* controllo di congruità */
//            continua = (wrap != null);
//
//            if (continua) {
//                arrivo = new PartenzaWrap(wrap);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return arrivo;
//    }


//    /**
//     * Costruisce un oggetto di tipo stampabile.
//     * <p/>
//     *
//     * @param wrap con prenotazione e periodi interessati
//     *
//     * @return oggetto stampabile
//     */
//    private static ArrivoPartenzaWrap creaCambio(PrenotazionePeriodiWrap wrap) {
//        /* variabili e costanti locali di lavoro */
//        ArrivoPartenzaWrap arrivo = null;
//        boolean continua;
//
//        try { // prova ad eseguire il codice
//            /* controllo di congruità */
//            continua = (wrap != null);
//
//            if (continua) {
//                arrivo = new CambioWrap(wrap);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return arrivo;
//    }



    /**
     * Stampa il prospetto di Arrivi, Partenze e Cambi.
     * <p/>
     * @param dataInizio data di inizio (compresa)
     * @param dataFine data di fine (compresa)
     */
    public static void stampaCompleta(Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        int giorni;
        Date data;
        Printer printer;
        J2FlowPrinter flowPrinter;
        J2PanelPrinter panPrinter;
        String titoloRiepilogo;

        try {    // prova ad eseguire il codice
            giorni = Lib.Data.diff(dataFine, dataInizio);

            /* crea e regola il Printer generale */
            printer = new Printer();

            /* una stampata per giorno */
            for (int k = 0; k < giorni + 1; k++) {
                data = Lib.Data.add(dataInizio, k);
                flowPrinter = stampaCompleta(data);
                printer.addPageable(flowPrinter);
            } // fine del ciclo for

            panPrinter = getProspetto(dataInizio, dataFine);
            if (panPrinter != null) {
                flowPrinter = new J2FlowPrinter();
                if (dataInizio.equals(dataFine)) {
                    titoloRiepilogo = "Prospetto riepilogativo del giorno ";
                    titoloRiepilogo += Lib.Data.getDataBreve(dataInizio);
                } else {
                    titoloRiepilogo = "Prospetto riepilogativo del periodo ";
                    titoloRiepilogo += Lib.Data.getDataBreve(dataInizio);
                    titoloRiepilogo += "/";
                    titoloRiepilogo += Lib.Data.getDataBreve(dataFine);
                } // fine del blocco if-else
                flowPrinter.addFlowable(new J2TextPrinter(titoloRiepilogo));
                flowPrinter.addFlowable(panPrinter);
                printer.addPageable(flowPrinter);
            } // fine del blocco if

            /* stampa il printer */
            printer.showPrintPreviewDialog();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Stampa il prospetto di Arrivi, Partenze e Cambi per un giorno.
     * <p/>
     * @param data di riferimento
     * @return il printer da stampare
     */
    private static J2FlowPrinter stampaCompleta(Date data) {
        /* variabili e costanti locali di lavoro */
        J2FlowPrinter flowPrinter = null;
        J2FlowPrinter comp;

        try {    // prova ad eseguire il codice

            flowPrinter = new J2FlowPrinter();
            comp = creaPrinterArrivi(data, data);
            if (comp != null) {
                flowPrinter.addFlowable(comp);
            } // fine del blocco if

            comp = creaPrinterPartenze(data, data);
            if (comp != null) {
                flowPrinter.addFlowable(new PageEject());
                flowPrinter.addFlowable(comp);
            } // fine del blocco if

            comp = creaPrinterCambi(data, data);
            if (comp != null) {
                flowPrinter.addFlowable(new PageEject());
                flowPrinter.addFlowable(comp);
            } // fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return flowPrinter;
    }

}
