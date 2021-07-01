package it.algos.albergo.arrivipartenze;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.conto.WrapConto;
import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebitofisso.AddebitoFisso;
import it.algos.albergo.conto.addebitofisso.AddebitoFissoModulo;
import it.algos.albergo.pensioni.WrapAddebiti;
import it.algos.albergo.pensioni.WrapAddebitiConto;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.stampeobbligatorie.StampeObbLogica;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.IntBool;
import it.algos.base.wrapper.SetValori;
import it.algos.gestione.anagrafica.Anagrafica;

import java.util.ArrayList;
import java.util.Date;

public class ArriviPartenzeLogica {

    /**
     * Conferma un arrivo.
     * <p/>
     *
     * @param codPeriodo codice del periodo da confermare
     *
     * @return true se riuscito
     */
    public static boolean confermaArrivo(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        ConfermaArrivoDialogo dialogo = null;
        int codPrenotazione;
        boolean confermata;
        Modulo modPeriodo;
        int codCamera;
        Date dataArrivo;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codPeriodo));

            /**
             * controlla che la prenotazione relativa sia confermata
             */
            if (continua) {
                codPrenotazione = PeriodoModulo.getCodPrenotazione(codPeriodo);
                confermata = PrenotazioneModulo.isConfermata(codPrenotazione);
                if (!confermata) {
                    new MessaggioAvviso(
                            "La prenotazione non è confermata\nImpossibile effettuare l'arrivo.");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /**
             * controlla che non ci siano arrivi precedenti
             * ancora da confermare ed eventualmente chiede
             * conferma
             */
            if (continua) {
                continua = ArriviPartenzeLogica.checkArrivi(codPeriodo);
            }// fine del blocco if

            /**
             * controlla che la camera di destinazione sia libera
             */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                codCamera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);
                dataArrivo = modPeriodo.query().valoreData(Periodo.Cam.arrivoPrevisto.get(),
                        codPeriodo);
                if (!isCameraLibera(codCamera)) {
                    continua = checkInPartenza(dataArrivo, codCamera);
                }// fine del blocco if
            }// fine del blocco if

            /* presenta il dialogo di conferma arrivo */
            if (continua) {
                dialogo = new ConfermaArrivoDialogo(codPeriodo);
                dialogo.avvia();
                continua = dialogo.isConfermato();
            }// fine del blocco if

            if (continua) {
                continua = ArriviPartenzeLogica.esegueArrivo(dialogo);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Controlla se una camera è in partenza nella data indicata.
     * Se è in partenza chiede di confermare e se confermato ritorna true
     * Se non è in partenza presenta un messaggio e ritorna false.
     * <p/>
     *
     * @param data data da controllare
     * @param codCamera codice della camera
     *
     * @return false se non è in partenza nella data indicata
     *         true se è in partenza e l'utente ha confermato il messaggio
     */
    private static boolean checkInPartenza(Date data, int codCamera) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        String stringaNomi;
        String testo;
        MessaggioDialogo messaggio;
        Modulo modCamera;
        String nomeCamera;

        try { // prova ad eseguire il codice

            stringaNomi = getOccupantiCamera(codCamera);
            modCamera = CameraModulo.get();
            nomeCamera = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCamera);


            if (isDiventaLibera(data, codCamera)) {

                testo = "La camera " + nomeCamera + " è ancora occupata da " + stringaNomi;
                testo += " ma dovrebbe liberarsi oggi.";
                testo += "\nVuoi continuare ugualmente?";
                messaggio = new MessaggioDialogo(testo);
                continua = messaggio.isConfermato();

            } else {

                testo = "Impossibile confermare il movimento.\n";
                testo += "La camera " + nomeCamera + " è occupata da:\n";
                testo += stringaNomi;
                new MessaggioAvviso(testo);
                continua = false;
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Controlla se la camera indicata è destinata a liberarsi
     * nella data indicata.
     * <p/>
     * E' destinata a liberarsi se per la data indicata è prevista
     * una partenza o un cambio in uscita
     * todo ancora da implementare il riconoscimento del cambio in uscita!
     *
     * @param data data da controllare
     * @param codCamera codice della camera
     *
     * @return true se è destinata a liberarsi nella data indicata
     */
    private static boolean isDiventaLibera(Date data, int codCamera) {
        /* variabili e costanti locali di lavoro */
        boolean diventaLibera = false;
        boolean continua;
        Modulo modPeriodo;
        Filtro filtro;
        Filtro filtroData;
        Filtro filtroCamera;
        Filtro filtroAperte;
        Filtro filtroPartenze;
        Filtro filtroCambiUscita;
        Filtro filtroUscite;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(data, codCamera));


            if (continua) {

                modPeriodo = PeriodoModulo.get();

                /**
                 * costruisce un filtro per selezionare le partenze non ancora
                 * confermate per la camera e la data
                 */
                filtroPartenze = PeriodoModulo.getFiltroPartenze(data);

                /**
                 * costruisce un filtro per selezionare i cambi in uscita non ancora
                 * confermati per la camera e la data
                 */
                filtroCambiUscita = PeriodoModulo.getFiltroCambiUscita(data);

                /**
                 * combina i due filtri precedenti con OR
                 */
                filtroUscite = new Filtro();
                filtroUscite.add(filtroPartenze);
                filtroUscite.add(Filtro.Op.OR, filtroCambiUscita);

                /**
                 * costruisce il filtro completo
                 */
                filtroCamera = FiltroFactory.crea(Periodo.Cam.camera.get(), codCamera);
                filtroAperte = FiltroFactory.creaFalso(Periodo.Cam.partito.get());
                filtro = new Filtro();
                filtro.add(filtroCamera);
                filtro.add(filtroAperte);
                filtro.add(filtroUscite);

                /* controlla il database */
                diventaLibera = modPeriodo.query().isEsisteRecord(filtro);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return diventaLibera;
    }


    /**
     * Movimentazione manuale di un arivo.
     * <p/>
     *
     * @return true se effettuata
     */
    public static boolean arrivoManuale() {
        /* variabili e costanti locali di lavoro */
        boolean confermato = false;
        boolean continua;
        ArrivoManualeDialogo dialogo;

        try { // prova ad eseguire il codice

            /* presenta il dialogo di conferma arrivo */
            dialogo = new ArrivoManualeDialogo();
            dialogo.avvia();
            continua = dialogo.isConfermato();

            if (continua) {
                continua = ArriviPartenzeLogica.esegueArrivoManuale(dialogo);
            }// fine del blocco if

            /* conferma (all'utente) delle operazioni effettuate */
            if (continua) {
                new MessaggioAvviso("Arrivo confermato.");
            }// fine del blocco if

            confermato = continua;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Conferma una partenza.
     * <p/>
     *
     * @param codPeriodo codice del periodo da confermare
     *
     * @return true se riuscito
     */
    public static boolean confermaPartenza(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        ConfermaPartenzaDialogo dialogo = null;
        Modulo modPeriodo;
        int codCamera;
        int[] clienti;
        boolean arrivato;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codPeriodo));

            /**
             * controlla che il periodo risulti arrivato
             */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                arrivato = modPeriodo.query().valoreBool(Periodo.Cam.arrivato.get(), codPeriodo);
                if (!arrivato) {
                    new MessaggioAvviso("Il cliente non è ancora arrivato!");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /**
             * controlla che ci siano persone presenti nella camera
             */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                codCamera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);
                clienti = PresenzaModulo.getClientiPresenti(codCamera);
                if (clienti.length == 0) {
                    new MessaggioAvviso("In questa camera non ci sono clienti presenti!");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

//            /**
//             * controlla che non ci siano partenze precedenti
//             * ancora da confermare ed eventualmente chiede
//             * conferma
//             */
//            if (continua) {
//                continua = ArriviPartenzeLogica.checkPartenze(codPeriodo);
//            }// fine del blocco if

            /* presenta il dialogo di conferma partenza */
            if (continua) {
                dialogo = new ConfermaPartenzaDialogo(codPeriodo);
                dialogo.avvia();
                continua = dialogo.isConfermato();
            }// fine del blocco if


            if (continua) {
                continua = ArriviPartenzeLogica.eseguePartenza(dialogo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Movimentazione manuale di una partenza.
     * <p/>
     *
     * @return true se riuscito
     */
    public static boolean partenzaManuale() {
        /* variabili e costanti locali di lavoro */
        boolean confermato = false;
        boolean continua;
        PartenzaManualeDialogo dialogo;


        try { // prova ad eseguire il codice

            /* presenta il dialogo di conferma arrivo */
            dialogo = new PartenzaManualeDialogo();
            dialogo.avvia();
            continua = dialogo.isConfermato();

            if (continua) {
                continua = ArriviPartenzeLogica.eseguePartenzaManuale(dialogo);
            }// fine del blocco if

            /* conferma (all'utente) delle operazioni effettuate */
            if (continua) {
                new MessaggioAvviso("Partenza confermata\nConto da controllare");
            }// fine del blocco if

            confermato = continua;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Conferma un cambio.
     * <p/>
     *
     * @param codPeriodo codice del periodo da confermare (il primo)
     *
     * @return true se riuscito
     */
    public static boolean confermaCambio(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        ConfermaCambioDialogo dialogo = null;
        int codPrenotazione;
        boolean confermata;
        boolean arrivato;
        Modulo modPeriodo;
        int codCamera;
        Date dataCambio;
        int codPeriodo2;


        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codPeriodo));

            /**
             * controlla che la prenotazione relativa sia confermata
             */
            if (continua) {
                codPrenotazione = PeriodoModulo.getCodPrenotazione(codPeriodo);
                confermata = PrenotazioneModulo.isConfermata(codPrenotazione);
                if (!confermata) {
                    new MessaggioAvviso(
                            "La prenotazione non è confermata\nImpossibile effettuare il cambio.");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /**
             * controlla che il periodo iniziale risulti arrivato
             */
            if (continua) {
                arrivato = PeriodoModulo.get().query().valoreBool(Periodo.Cam.arrivato.get(),
                        codPeriodo);
                if (!arrivato) {
                    new MessaggioAvviso("Questo periodo non è ancora arrivato.");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /**
             * controlla che non ci siano cambi precedenti
             * ancora da confermare ed eventualmente chiede
             * conferma
             */
            if (continua) {
                continua = ArriviPartenzeLogica.checkCambi(codPeriodo);
            }// fine del blocco if

            /**
             * controlla che la camera di destinazione sia libera
             */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                codPeriodo2 = modPeriodo.query().valoreInt(Periodo.Cam.linkDestinazione.get(),
                        codPeriodo);
                codCamera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo2);
                dataCambio = modPeriodo.query().valoreData(Periodo.Cam.partenzaPrevista.get(),
                        codPeriodo);
                if (!isCameraLibera(codCamera)) {
                    continua = checkInPartenza(dataCambio, codCamera);
                }// fine del blocco if
            }// fine del blocco if

            /* presenta il dialogo di conferma cambio */
            if (continua) {
                dialogo = new ConfermaCambioDialogo(codPeriodo);
                dialogo.avvia();
                continua = dialogo.isConfermato();
            }// fine del blocco if

            /* esegue le operazioni */
            if (continua) {
                continua = ArriviPartenzeLogica.esegueCambio(dialogo);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Controlla se una camera è libera.
     * <p/>
     *
     * @param codCamera codice della camera da controllare
     *
     * @return true se la camera è libera
     */
    private static boolean isCameraLibera(int codCamera) {
        /* variabili e costanti locali di lavoro */
        boolean libera = false;
        int[] presenze;

        try { // prova ad eseguire il codice
            presenze = PresenzaModulo.getPresenzeAperte(codCamera);
            libera = (presenze.length == 0);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return libera;
    }


    /**
     * Ritorna una stringa con gli occupanti di una camera.
     * <p/>
     *
     * @param codCamera codice della camera da controllare
     *
     * @return la stringa con i nomi degli occupanti
     */
    private static String getOccupantiCamera(int codCamera) {
        /* variabili e costanti locali di lavoro */
        String stringaNomi = "";
        Modulo modPresenza;
        Modulo modCliente;
        int[] presenze;
        int codCli;
        String nomeCli;

        try { // prova ad eseguire il codice

            modPresenza = PresenzaModulo.get();
            modCliente = ClienteAlbergoModulo.get();
            presenze = PresenzaModulo.getPresenzeAperte(codCamera);
            for (int cod : presenze) {
                codCli = modPresenza.query().valoreInt(Presenza.Cam.cliente.get(), cod);
                nomeCli = modCliente.query().valoreStringa(Anagrafica.Cam.soggetto.get(), codCli);
                if (Lib.Testo.isValida(stringaNomi)) {
                    stringaNomi += ", ";
                }// fine del blocco if
                stringaNomi += nomeCli;
            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringaNomi;
    }


    /**
     * Movimentazione manuale di un cambio.
     * <p/>
     *
     * @return true se riuscito
     */
    public static boolean cambioManuale() {
        /* variabili e costanti locali di lavoro */
        boolean confermato = false;
        boolean continua = true;
        CambioManualeDialogo dialogo = null;


        try { // prova ad eseguire il codice

//            new MessaggioAvviso("Funzione non ancora disponibile.");
//            continua = false;

            /* presenta il dialogo di impostazione cambio manuale */
            if (continua) {
                dialogo = new CambioManualeDialogo();
                dialogo.avvia();
                continua = dialogo.isConfermato();
            }// fine del blocco if

            if (continua) {
                continua = ArriviPartenzeLogica.esegueCambioManuale(dialogo);
            }// fine del blocco if

            /* conferma (all'utente) delle operazioni effettuate */
            if (continua) {
                new MessaggioAvviso("Cambio effettuato.\nConto da controllare");
            }// fine del blocco if

            confermato = continua;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Annullamento di un arrivo.
     * <p/>
     *
     * @param codPeriodo codice del periodo per il quale annullare l'arrivo
     *
     * @return true se l'operazione è stata effettuata
     */
    public static boolean annullaArrivo(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Modulo modPeriodo = null;
        Modulo modPresenza = null;
        boolean flag;
        int codCamera;
        int[] presenze = null;
        String testo;
        AnnullaArrivoDialogo dialogo = null;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codPeriodo));

            /* recupero il modulo Periodi */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = (modPeriodo != null);
            }// fine del blocco if

            /* recupero il modulo Presenze */
            if (continua) {
                modPresenza = PresenzaModulo.get();
                continua = (modPresenza != null);
            }// fine del blocco if

            /* controllo che il periodo risulti arrivato */
            if (continua) {
                flag = modPeriodo.query().valoreBool(Periodo.Cam.arrivato.get(), codPeriodo);
                if (!flag) {
                    new MessaggioAvviso("Il periodo cod." + codPeriodo + " non risulta arrivato.");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* controllo che il periodo non risulti già partito */
            if (continua) {
                flag = modPeriodo.query().valoreBool(Periodo.Cam.partito.get(), codPeriodo);
                if (flag) {
                    new MessaggioAvviso("Il periodo cod." + codPeriodo + " risulta già partito.");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if


            /**
             * se ci sono delle presenze aperte nella camera, controllo
             * per ognuna che si possano ancora de-movimentare i registri
             * alla data di arrivo del cliente
             */
            if (continua) {
                codCamera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);
                presenze = PresenzaModulo.getPresenzeAperte(codCamera);
                for (int cod : presenze) {
                    Date dataArrivo = modPresenza.query().valoreData(Presenza.Cam.entrata.get(), cod);
                    int codAzienda = modPresenza.query().valoreInt(Presenza.Cam.azienda.get(), cod);
                    boolean movimentabile = StampeObbLogica.isArrivoRegistrabile(dataArrivo, codAzienda);
                    if (!movimentabile) {
                        testo = ArriviPartenzeLogica.getMessaggioArriviNonConfermabili(codAzienda);
                        new MessaggioAvviso(testo);
                        continua = false;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

            /* presenta il dialogo di annullamento arrivo */
            if (continua) {
                dialogo = new AnnullaArrivoDialogo(codPeriodo);
                dialogo.avvia();
                continua = dialogo.isConfermato();
            }// fine del blocco if

            /* esegue l'annullamento arrivo */
            if (continua) {
                continua = ArriviPartenzeLogica.esegueAnnullaArrivo(dialogo);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Annullamento di una partenza.
     * <p/>
     *
     * @param codPeriodo codice del periodo per il quale annullare la partenza
     *
     * @return true se l'operazione è stata effettuata
     */
    public static boolean annullaPartenza(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Modulo modPeriodo = null;
        Modulo modPresenza;
        boolean flag;
        int codCamera;
        int[] presenze;
        AnnullaPartenzaDialogo dialogo = null;
        String nomi;
        String testo;
        int quantiConti = 0;
        int quantePres = 0;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codPeriodo));

            /* recupero il modulo Periodi */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = (modPeriodo != null);
            }// fine del blocco if

            /* recupero il modulo Presenze */
            if (continua) {
                modPresenza = PresenzaModulo.get();
                continua = (modPresenza != null);
            }// fine del blocco if

            /* controllo che il periodo risulti arrivato */
            if (continua) {
                flag = modPeriodo.query().valoreBool(Periodo.Cam.arrivato.get(), codPeriodo);
                if (!flag) {
                    new MessaggioAvviso("Il periodo cod." + codPeriodo + " non risulta arrivato.");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* controllo che il periodo risulti partito */
            if (continua) {
                flag = modPeriodo.query().valoreBool(Periodo.Cam.partito.get(), codPeriodo);
                if (!flag) {
                    new MessaggioAvviso("Il periodo cod." + codPeriodo + " non risulta partito.");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* controllo che la camera sia ancora libera */
            if (continua) {
                codCamera = modPeriodo.query().valoreInt(Periodo.Cam.camera.get(), codPeriodo);
                presenze = PresenzaModulo.getPresenzeAperte(codCamera);
                if (presenze.length > 0) {
                    nomi = getOccupantiCamera(codCamera);
                    new MessaggioAvviso(
                            "Impossibile annullare la partenza.\nLa camera risulta già occupata da " +
                                    nomi);
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if


            /* presenta il dialogo di annullamento partenza */
            if (continua) {
                dialogo = new AnnullaPartenzaDialogo(codPeriodo);
                dialogo.avvia();
                continua = dialogo.isConfermato();
            }// fine del blocco if

            /* memorizza alcuni valori per il messaggio di conferma */
            if (continua) {
                quantePres = dialogo.getPresenze().length;
                quantiConti = dialogo.getContiChiusi().length;
            }// fine del blocco if

            /* esegue l'annullamento partenza */
            if (continua) {
                continua = ArriviPartenzeLogica.esegueAnnullaPartenza(dialogo);
            }// fine del blocco if

            /* messaggio operazione eseguita */
            if (continua) {
                testo = "La partenza è stata annullata.";
                if (quantePres > 0) {
                    testo += "\nSono state ripristinate " + quantePres + " presenze.";
                }// fine del blocco if
                if (quantiConti > 0) {
                    testo += "\nSono stati riaperti " + quantiConti + " conti.";
                }// fine del blocco if

                new MessaggioAvviso(testo);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Annullamento di un cambio.
     * <p/>
     *
     * @param codPeriodo codice del periodo per il quale annullare il cambio
     *
     * @return true se l'operazione è stata effettuata
     */
    public static boolean annullaCambio(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        try { // prova ad eseguire il codice
            new MessaggioAvviso("Funzione non ancora disponibile.");
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * controlla che non ci siano arrivi precedenti
     * ancora da confermare ed eventualmente chiede
     * conferma.
     * <p/>
     *
     * @param codPeriodo codice del periodo da controllare
     *
     * @return true se riuscito
     */
    private static boolean checkArrivi(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        boolean esistonoPrecedenti;
        Date dataArrivo;
        MessaggioDialogo messaggio;
        Modulo mod = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codPeriodo));

            if (continua) {
                mod = PeriodoModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            /**
             * controlla che non ci siano arrivi precedenti
             * ancora da confermare ed eventualmente chiede
             * conferma
             */
            if (continua) {
                dataArrivo = mod.query().valoreData(Periodo.Cam.arrivoPrevisto.get(), codPeriodo);
                esistonoPrecedenti = ArriviPartenzeLogica.isEsistonoArriviPrecedenti(dataArrivo);
                if (esistonoPrecedenti) {
                    Lib.Sist.beep();
                    messaggio = new MessaggioDialogo(
                            "Attenzione!\nCi sono degli arrivi precedenti ancora da confermare.\nVuoi continuare ugualmente?");
                    if (!messaggio.isConfermato()) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Controlla se esistono arrivi non ancora confermati
     * precedenti a una certa data.
     * <p/>
     *
     * @param data da controllare
     *
     * @return true se esistono
     */
    public static boolean isEsistonoArriviPrecedenti(Date data) {
        /* variabili e costanti locali di lavoro */
        boolean esistono = false;
        boolean continua;
        Date dataTest;
        Filtro filtro;
        Filtro filtroNonArrivati;
        int quanti;
        Modulo mod = null;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(data));

            if (continua) {
                mod = PeriodoModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                dataTest = Lib.Data.add(data, -1);
                filtro = PeriodoModulo.getFiltroArrivi(null, dataTest);
                filtroNonArrivati = FiltroFactory.creaFalso(Periodo.Cam.arrivato);
                filtro.add(filtroNonArrivati);
                quanti = mod.query().contaRecords(filtro);
                if (quanti > 0) {
                    esistono = true;
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esistono;
    }


    /**
     * controlla che non ci siano partenze precedenti
     * ancora da confermare ed eventualmente chiede
     * conferma.
     * <p/>
     *
     * @param codPeriodo codice del periodo da controllare
     *
     * @return true se riuscito
     */
    public static boolean checkPartenze(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Date dataPartenza;
        Date giornoPrecedente;
        Filtro filtro;
        Filtro filtroNonPartiti;
        int quanti;
        MessaggioDialogo messaggio;
        Modulo mod = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codPeriodo));

            if (continua) {
                mod = PeriodoModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            /**
             * controlla che non ci siano arrivi precedenti
             * ancora da confermare ed eventualmente chiede
             * conferma
             */
            if (continua) {
                dataPartenza = mod.query().valoreData(Periodo.Cam.partenzaPrevista.get(),
                        codPeriodo);
                giornoPrecedente = Lib.Data.add(dataPartenza, -1);
                filtro = PeriodoModulo.getFiltroPartenze(null, giornoPrecedente);
                filtroNonPartiti = FiltroFactory.creaFalso(Periodo.Cam.partito);
                filtro.add(filtroNonPartiti);
                quanti = mod.query().contaRecords(filtro);
                if (quanti > 0) {
                    Lib.Sist.beep();
                    messaggio = new MessaggioDialogo(
                            "Attenzione!\nCi sono delle partenze precedenti ancora da confermare.\nVuoi continuare?");
                    if (!messaggio.isConfermato()) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * controlla che non ci siano cambi precedenti
     * ancora da confermare ed eventualmente chiede
     * conferma.
     * <p/>
     *
     * @param codPeriodo codice del periodo da controllare
     *
     * @return true se riuscito
     */
    private static boolean checkCambi(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Date dataCambio;
        boolean esistonoPrecedenti;
        MessaggioDialogo messaggio;
        Modulo mod = null;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codPeriodo));

            if (continua) {
                mod = PeriodoModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                dataCambio = mod.query().valoreData(Periodo.Cam.partenzaPrevista.get(), codPeriodo);
                esistonoPrecedenti = ArriviPartenzeLogica.isEsistonoCambiPrecedenti(dataCambio);
                if (esistonoPrecedenti) {
                    Lib.Sist.beep();
                    messaggio = new MessaggioDialogo(
                            "Attenzione!\nCi sono dei cambi precedenti ancora da confermare.\nVuoi continuare ugualmente?");
                    if (!messaggio.isConfermato()) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Controlla se esistono cambi non ancora confermati
     * precedenti a una certa data.
     * <p/>
     *
     * @param data da controllare
     *
     * @return true se esistono
     */
    private static boolean isEsistonoCambiPrecedenti(Date data) {
        /* variabili e costanti locali di lavoro */
        boolean esistono = false;
        boolean continua;
        Date dataTest;
        Filtro filtro;
        int quanti;
        Modulo mod = null;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(data));

            if (continua) {
                mod = PeriodoModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                dataTest = Lib.Data.add(data, -1);

                /* filtro vuoto */
                filtro = new Filtro();

                /* filtro cambi validi in uscita fino alla data specificata */
                filtro.add(PeriodoModulo.getFiltroCambiUscita(null, dataTest));

                /* filtro periodo aperto (IN = true, OUT = false) */
                filtro.add(PeriodoModulo.getFiltroAperti());

                /* controllo */
                quanti = mod.query().contaRecords(filtro);
                if (quanti > 0) {
                    esistono = true;
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esistono;
    }


    /**
     * Esegue le operazioni all'arrivo, dopo che sono state confermate.
     * <p/>
     *
     * @param dialogo contenente i dati dell'arrivo
     *
     * @return true se riuscito
     */
    public static boolean esegueArrivo(ConfermaArrivoDialogo dialogo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Date data = null;
        int codCamera;
        int codConto = 0;
        int arrivoCon = 0;
        int codPensione;
        int codPasto;
        int codAzienda;
        int[] presenzeVecchie;
        ArrayList<IntBool> listaArrivi = null;
        Modulo mod;
        Connessione conn = null;
        boolean transactionStarted = false;
        ArrayList<WrapConto> wrapConti;
        int codPrenotazione;
        int codPeriodo;

        try { // prova ad eseguire il codice

            /* recupera il codice del periodo confermato */
            codPeriodo = dialogo.getCodPeriodo();
            continua = (codPeriodo > 0);

            /* recupera l'elenco delle persone arrivate e il relativo flag bambino */
            if (continua) {
                listaArrivi = dialogo.getClientiInArrivo();
                continua = (listaArrivi != null && listaArrivi.size() > 0);
            }// fine del blocco if

            /* recupera la connessione */
            if (continua) {
                conn = Progetto.getConnessione();
                continua = (conn != null);
            }// fine del blocco if

            /* apre una transazione */
            if (continua) {
                conn.startTransaction();
                transactionStarted = true;
            }// fine del blocco if

            /* crea o modifica i conti */
            if (continua) {
                wrapConti = dialogo.getWrapConti();
                for (WrapConto wc : wrapConti) {
                    codConto = syncConto(wc, true, conn);
                } // fine del ciclo for-each
            }// fine del blocco if

            /* se la camera è occupata aggiunge un flag alle presenze in partenza */
            if (continua) {
                data = dialogo.getDataArrivo();
                codCamera = dialogo.getCodCamera();
                if (isDiventaLibera(data, codCamera)) {
                    presenzeVecchie = PresenzaModulo.getPresenzeAperte(codCamera);
                    mod = PresenzaModulo.get();
                    for (int cod : presenzeVecchie) {
                        mod.query().registra(cod, Presenza.Cam.provvisoria.get(), true, conn);
                    } // fine del ciclo for-each
                }// fine del blocco if
            }// fine del blocco if

            /* crea le presenze */
            if (continua) {
                data = dialogo.getDataArrivo();
                arrivoCon = dialogo.getArrivoCon();
                codCamera = dialogo.getCodCamera();
                codPensione = dialogo.getCodPensione();
                codPasto = dialogo.getCodPasto();
                codAzienda = dialogo.getCodAzienda();
                continua = creaPresenze(data,
                        codCamera,
                        listaArrivi,
                        codConto,
                        arrivoCon,
                        codPensione,
                        codPasto,
                        codPeriodo,
                        codAzienda,
                        conn);
            }// fine del blocco if

            /* accende eventualmente il flag caparra accreditata nella prenotazione */
            if (continua) {
                if (dialogo.isCaparraUtilizzata()) {
                    mod = PrenotazioneModulo.get();
                    if (mod != null) {
                        codPrenotazione = dialogo.getCodPrenotazione();
                        continua = mod.query().registra(codPrenotazione,
                                Prenotazione.Cam.caparraAccreditata.get(),
                                true,
                                conn);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /**
             * aggiorna i dati del periodo in base ai dati reali di arrivo
             * e accende il flag arrivato nel periodo
             */
            if (continua) {
                mod = PeriodoModulo.get();
                if (mod != null) {
                    SetValori sv = new SetValori(mod);
                    sv.add(Periodo.Cam.arrivato, true);
                    sv.add(Periodo.Cam.arrivoEffettivo, data);
                    sv.add(Periodo.Cam.arrivoCon, dialogo.getArrivoCon());
                    sv.add(Periodo.Cam.trattamento, dialogo.getCodPensione());
                    sv.add(Periodo.Cam.pasto, dialogo.getCodPasto());
                    sv.add(Periodo.Cam.adulti, dialogo.getAdultiSelezionati().size());
                    sv.add(Periodo.Cam.bambini, dialogo.getBambiniSelezionati().size());
                    continua = mod.query().registraRecordValori(codPeriodo, sv.getListaValori(), conn);
                }// fine del blocco if
            }// fine del blocco if


            /* conclude la transazione */
            if (transactionStarted) {
                if (continua) {
                    conn.commit();
                } else {
                    conn.rollback();
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Esegue le operazioni all'arrivo, dopo che sono state confermate.
     * <p/>
     *
     * @param dialogo contenente i dati dell'arrivo
     *
     * @return true se riuscito
     */
    public static boolean esegueArrivoManuale(ArrivoManualeDialogo dialogo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Date dataArrivo = null;
        int codCamera = 0;
        int codCliente = 0;
        boolean bambino = false;
        int codConto = 0;
        int arrivoCon = 0;
        int codPeriodo = 0;
        int codPensione = 0;
        int codPasto = 0;
        int codAzienda = 0;
        WrapConto wrapConto;
        Connessione conn = null;
        boolean transactionStarted = false;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (dialogo != null);

            if (continua) {
                dataArrivo = dialogo.getDataMovimento();
                continua = (dataArrivo != null);
            }// fine del blocco if

            if (continua) {
                codCliente = dialogo.getCodCliente();
                continua = (codCliente > 0);
            }// fine del blocco if

            if (continua) {
                bambino = dialogo.isBambino();
            }// fine del blocco if

            if (continua) {
                codCamera = dialogo.getCodCamera();
                continua = (codCamera > 0);
            }// fine del blocco if

            /* tipo di arrivo */
            if (continua) {
                arrivoCon = dialogo.getArrivoCon();
                continua = (arrivoCon > 0);
            }// fine del blocco if

            /* pensione */
            if (continua) {
                codPensione = dialogo.getCodPensione();
                continua = (codPensione > 0);
            }// fine del blocco if

            /* pasto (facoltativo) */
            if (continua) {
                codPasto = dialogo.getCodPasto();
            }// fine del blocco if

            /* periodo */
            if (continua) {
                codPeriodo = dialogo.getCodPeriodoDestinazione();
                continua = (codPeriodo > 0);
            }// fine del blocco if

            /* azienda */
            if (continua) {
                codAzienda = dialogo.getCodAzienda();
                continua = (codAzienda > 0);
            }// fine del blocco if

            /* recupera la connessione */
            if (continua) {
                conn = Progetto.getConnessione();
                continua = (conn != null);
            }// fine del blocco if

            /* apre una transazione */
            if (continua) {
                conn.startTransaction();
                transactionStarted = true;
            }// fine del blocco if

            /* crea o modifica il conto */
            if (continua) {
                wrapConto = dialogo.getWrapConto();
                codConto = syncConto(wrapConto, false, conn);
                continua = (codConto > 0);
            }// fine del blocco if

            /* crea le presenze */
            if (continua) {
                continua = creaPresenze(dataArrivo,
                        codCamera,
                        codCliente,
                        bambino,
                        codConto,
                        arrivoCon,
                        codPensione,
                        codPasto,
                        codPeriodo,
                        codAzienda,
                        conn);
            }// fine del blocco if

            
            /**
             * modifica il periodo destinazione aggiungendo
             * la persona arrivata (adulto o bambino)
             */
            if (continua) {
                continua = addRemovePersonaPeriodo(codPeriodo, bambino, Operazione.aggiunta, conn);
            }// fine del blocco if


            /* conclude la transazione */
            if (transactionStarted) {
                if (continua) {
                    conn.commit();
                } else {
                    conn.rollback();
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Esegue le operazioni di partenza manuale.
     * <p/>
     *
     * @param dialogo contenente i dati della partenza
     *
     * @return true se riuscito
     */
    private static boolean eseguePartenzaManuale(PartenzaManualeDialogo dialogo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Date data = null;
        int codPresenza = 0;
        Connessione conn = null;
        boolean transactionStarted = false;

        try {    // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (dialogo != null);

            if (continua) {
                data = dialogo.getDataMovimento();
                continua = (data != null);
            }// fine del blocco if

            if (continua) {
                codPresenza = dialogo.getCodPresenzaSelezionata();
                continua = (codPresenza > 0);
            }// fine del blocco if

            /* recupera la connessione */
            if (continua) {
                conn = Progetto.getConnessione();
                continua = (conn != null);
            }// fine del blocco if

            /* apre una transazione */
            if (continua) {
                conn.startTransaction();
                transactionStarted = true;
            }// fine del blocco if

            /**
             * modifica il periodo provenienza sottraendo
             * la persona partita (adulto o bambino)
             */
            if (continua) {
                Modulo modPresenza = PresenzaModulo.get();
                boolean bambino = modPresenza.query().valoreBool(Presenza.Cam.bambino.get(), codPresenza, conn);
                int codPeriodo = modPresenza.query().valoreInt(Presenza.Cam.periodo.get(),codPresenza, conn);
                continua = addRemovePersonaPeriodo(codPeriodo, bambino, Operazione.sottrazione, conn);
            }// fine del blocco if


            /* chiude la presenza */
            if (continua) {
                continua = chiudiPresenza(codPresenza, data, conn);
            }// fine del blocco if


            /* conclude la transazione */
            if (transactionStarted) {
                if (continua) {
                    conn.commit();
                } else {
                    conn.rollback();
                }// fine del blocco if-else
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Esegue le operazioni alla partenza, dopo che sono state confermate.
     * <p/>
     *
     * @param dialogo contenente i dati della partenza
     *
     * @return true se riuscito
     */
    public static boolean eseguePartenza(ConfermaPartenzaDialogo dialogo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        int codPeriodo = 0;
        Date data = null;
        Modulo modPeriodo = null;
        Modulo modPresenze = null;
        Connessione conn = null;
        boolean transactionStarted = false;
        Filtro filtro;
        int[] presenze;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (dialogo != null);

            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = (modPeriodo != null);
            }// fine del blocco if

            if (continua) {
                modPresenze = PresenzaModulo.get();
                continua = (modPresenze != null);
            }// fine del blocco if

            if (continua) {
                codPeriodo = dialogo.getCodPeriodo();
                continua = (codPeriodo > 0);
            }// fine del blocco if

            /* recupera la connessione */
            if (continua) {
                conn = Progetto.getConnessione();
                continua = (conn != null);
            }// fine del blocco if

            /* apre una transazione */
            if (continua) {
                conn.startTransaction();
                transactionStarted = true;
            }// fine del blocco if

            /* accende il flag partito nel periodo */
            if (continua) {
                continua = modPeriodo.query().registra(codPeriodo,
                        Periodo.Cam.partito.get(),
                        true,
                        conn);
            }// fine del blocco if

            /* registra la data di partenza effettiva */
            if (continua) {
                data = dialogo.getDataPartenza();
                continua = modPeriodo.query().registra(codPeriodo,
                        Periodo.Cam.partenzaEffettiva.get(),
                        data,
                        conn);
            }// fine del blocco if

            /* presenze */
            if (continua) {
                filtro = dialogo.getFiltroPresenze();
                presenze = modPresenze.query().valoriChiave(filtro);

                for (int cod : presenze) {
                    continua = chiudiPresenza(cod, data, conn);
                    if (!continua) {
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

            /* conclude la transazione */
            if (transactionStarted) {
                if (continua) {
                    conn.commit();
                } else {
                    conn.rollback();
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Esegue le operazioni di annullamento partenza, dopo che sono state confermate.
     * <p/>
     *
     * @param dialogo contenente i dati di annullamento della partenza
     *
     * @return true se riuscito
     */
    private static boolean esegueAnnullaPartenza(AnnullaPartenzaDialogo dialogo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        int codPeriodo = 0;
        Modulo modPeriodo = null;
        Modulo modPresenze;
        Modulo modConto = null;
        Connessione conn = null;
        boolean transactionStarted = false;
        int[] presenze = new int[0];
        int[] conti = new int[0];
        SetValori sv;
        ArrayList<CampoValore> listaCV;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (dialogo != null);

            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = (modPeriodo != null);
            }// fine del blocco if

            if (continua) {
                modPresenze = PresenzaModulo.get();
                continua = (modPresenze != null);
            }// fine del blocco if

            if (continua) {
                modConto = ContoModulo.get();
                continua = (modConto != null);
            }// fine del blocco if

            /* recupera il codice del periodo */
            if (continua) {
                codPeriodo = dialogo.getCodPeriodo();
                continua = (codPeriodo != 0);
            }// fine del blocco if

            /* recupera i codici delle presenze e dei conti chiusi */
            if (continua) {
                presenze = dialogo.getPresenze();
                conti = dialogo.getContiChiusi();
            }// fine del blocco if

            /* recupera la connessione */
            if (continua) {
                conn = Progetto.getConnessione();
                continua = (conn != null);
            }// fine del blocco if

            /* apre una transazione */
            if (continua) {
                conn.startTransaction();
                transactionStarted = true;
            }// fine del blocco if

            /* spegne il flag partito nel periodo */
            if (continua) {
                continua = modPeriodo.query().registra(codPeriodo,
                        Periodo.Cam.partito.get(),
                        false,
                        conn);
            }// fine del blocco if

            /* annulla la data di partenza effettiva nel periodo */
            if (continua) {
                continua = modPeriodo.query().registra(codPeriodo,
                        Periodo.Cam.partenzaEffettiva.get(),
                        Lib.Data.getVuota(),
                        conn);
            }// fine del blocco if

            /* riapre le presenze */
            if (continua) {
                for (int cod : presenze) {
                    continua = riapriPresenza(cod, conn);
                    if (!continua) {
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

            /* riapre eventuali conti chiusi */
            if (continua) {
                for (int cod : conti) {
                    sv = new SetValori(modConto);
                    sv.add(Conto.Cam.chiuso, false);
                    sv.add(Conto.Cam.dataChiusura, Lib.Data.getVuota());
                    listaCV = sv.getListaValori();
                    continua = modConto.query().registraRecordValori(cod, listaCV, conn);
                    if (!continua) {
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

            /* conclude la transazione */
            if (transactionStarted) {
                if (continua) {
                    conn.commit();
                } else {
                    conn.rollback();
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Esegue le operazioni al cambio, dopo che sono state confermate.
     * <p/>
     *
     * @param dialogo contenente i dati del cambio
     *
     * @return true se riuscito
     */
    public static boolean esegueCambio(ConfermaCambioDialogo dialogo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        int codCameraProvenienza = 0;
        int codCameraDestinazione = 0;
        int codContoUpdate=0;
        Connessione conn = null;
        boolean transactionStarted = false;
        int codPrimoPeriodo = 0;
        int codSecondoPeriodo = 0;
        Date dataCambio;
        Filtro filtro;
        int[] presenze = null;
        Modulo mod;

        try { // prova ad eseguire il codice

            /* recupera la data del cambio */
            dataCambio = dialogo.getDataCambio();
            continua = Lib.Data.isValida(dataCambio);

            /* recupera i codici dei periodi  */
            if (continua) {
                codPrimoPeriodo = dialogo.getCodPrimoPeriodo();
                codSecondoPeriodo = dialogo.getCodSecondoPeriodo();
                continua = (codPrimoPeriodo > 0 && codSecondoPeriodo > 0);
            }// fine del blocco if

            /* recupera le camere provenienza e destinazione */
            if (continua) {
                codCameraProvenienza = dialogo.getCameraProvenienza();
                codCameraDestinazione = dialogo.getCameraDestinazione();
                continua = (codCameraProvenienza > 0 && codCameraDestinazione > 0);
            }// fine del blocco if

            /* recupera il conto al quale aggiungere gli addebiti */
            if (continua) {
                codContoUpdate = dialogo.getCodContoUpdate();
            }// fine del blocco if

            /* recupera la connessione */
            if (continua) {
                conn = Progetto.getConnessione();
                continua = (conn != null);
            }// fine del blocco if

            /* apre una transazione */
            if (continua) {
                conn.startTransaction();
                transactionStarted = true;
            }// fine del blocco if

            /* modifica i due periodi */
            if (continua) {
                continua = cambiaPeriodi(codPrimoPeriodo, codSecondoPeriodo, dataCambio, conn);
            }// fine del blocco if

            /**
             * se ci sono delle presenze aperte nella camera destinazione
             * le contrassegna con flag provvisorio
             */
            if (continua) {
                mod = PresenzaModulo.get();
                presenze = PresenzaModulo.getPresenzeAperte(codCameraDestinazione);
                for (int cod : presenze) {
                    continua = mod.query().registra(cod,
                            Presenza.Cam.provvisoria.get(),
                            true,
                            conn);
                    if (!continua) {
                        break;
                    }// fine del blocco if
                }// fine del blocco for
            }// fine del blocco if

            /* modifica le presenze che cambiano camera */
            if (continua) {
                filtro = dialogo.getFiltroPresenze();
                mod = PresenzaModulo.get();
                presenze = mod.query().valoriChiave(filtro, conn);
                continua = cambiaPresenze(presenze,
                        codSecondoPeriodo,
                        codCameraDestinazione,
                        dataCambio,
                        conn);
            }// fine del blocco if


            /* trasferisce il piano addebiti previsti del periodo destinazione sul conto destinazione */
            if (continua) {
                if (codContoUpdate>0) {
                    continua = trasferisciAddebiti(codSecondoPeriodo, codContoUpdate, conn);
                }// fine del blocco if
            }// fine del blocco if

            /* aggiorna l'intervallo di validità del conto */
            if (continua) {
                if (codContoUpdate>0) {
                    continua = ContoModulo.updateValidita(codContoUpdate, conn);
                }// fine del blocco if
            }// fine del blocco if

            /* modifica i conti (sigla e riferimento alla camera) */
            if (continua) {
                continua = cambiaConti(presenze, codCameraProvenienza, codCameraDestinazione, conn);
            }// fine del blocco if

            /* conclude la transazione */
            if (transactionStarted) {
                if (continua) {
                    conn.commit();
                } else {
                    conn.rollback();
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Esegue le operazioni di cambio manuale.
     * <p/>
     *
     * @param dialogo contenente i dati del cambio
     *
     * @return true se riuscito
     */
    private static boolean esegueCambioManuale(CambioManualeDialogo dialogo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Date dataCambio = null;
        int codPresenza = 0;
        int codCameraDestinazione = 0;
        int codConto = 0;
        int codPeriodoOrig=0;
        int codPeriodoDest=0;
        boolean bambino=false;
        boolean unicaPres=false;
        boolean finePeriodo=false;
        Connessione conn = null;
        boolean transactionStarted = false;
        int codPresNuova = 0;
        Modulo mod;
        Query q;

        try {    // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (dialogo != null);

            if (continua) {
                dataCambio = dialogo.getDataMovimento();
                continua = (dataCambio != null);
            }// fine del blocco if

            if (continua) {
                codPresenza = dialogo.getCodPresenzaSelezionata();
                continua = (codPresenza > 0);
            }// fine del blocco if

            if (continua) {
                codCameraDestinazione = dialogo.getCodCamera();
                continua = (codCameraDestinazione > 0);
            }// fine del blocco if

            if (continua) {
                codConto = dialogo.getCodConto();
                continua = (codConto > 0);
            }// fine del blocco if

            if (continua) {
                codPeriodoOrig = dialogo.getCodPeriodoOrigine();
                continua = (codPeriodoOrig > 0);
            }// fine del blocco if

            if (continua) {
                codPeriodoDest = dialogo.getCodPeriodoDestinazione();
                continua = (codPeriodoDest > 0);
            }// fine del blocco if

            if (continua) {
                bambino = dialogo.isBambino();
                unicaPres = dialogo.isUnicaPresenza();
                finePeriodo = dialogo.isFinePeriodo();
            }// fine del blocco if

            /* recupera la connessione */
            if (continua) {
                conn = Progetto.getConnessione();
                continua = (conn != null);
            }// fine del blocco if

            /* apre una transazione */
            if (continua) {
                conn.startTransaction();
                transactionStarted = true;
            }// fine del blocco if

            /* Chiude la presenza originale con chiusura per cambio
             * Apre la nuova presenza con entrata per cambio */
            if (continua) {
                codPresNuova = chiudiPresenzaCambio(codPresenza,
                        dataCambio,
                        codCameraDestinazione,
                        codPeriodoDest,
                        conn);
                if (codPresNuova == 0) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* assegna il conto di competenza alla nuova presenza creata */
            if (continua) {
                mod = PresenzaModulo.get();
                continua = mod.query().registra(codPresNuova,
                        Presenza.Cam.conto.get(),
                        codConto,
                        conn);
            }// fine del blocco if

            /**
             * Se il periodo di destinazione non è arrivato, lo marca ora come arrivato.
             * Questo succede quando si ha uno sdoppiamento da una camera verso due camere.
             * Delle due camere destinazione, una si movimenta con un cambio
             * normale, la seconda è soltanto impegnata e ha n. di persone = 0.
             * Dopo aver effettuato il primo cambio automatico, si effettua un
             * cambio manuale delle persone coinvolte verso la seconda camera destinazione.
             * In questo caso, dopo l'operazione il periodo va marcato come arrivato perché
             * non avremo altri arrivi e la camera è stata regolarmente occupata.
             */
            if (continua) {
                continua = PeriodoModulo.get().query().registra(codPeriodoDest,Periodo.Cam.arrivato.get(), true, conn);
            }// fine del blocco if

            /**
             * Se il giorno del cambio coincide con la fine del periodo e la camera rimane vuota
             * chiude il periodo di origine tramite uscita con cambio.
             * Serve per cambio manuale da due camere a una.
             * La prima cambia in automatico.
             * La seconda cambia in manuale l'ultimo giorno e rimane vuota.
             * Il periodo viene chiuso automaticamente con cambio e linkato al periodo destinazione
             */
            if (continua) {
                if (unicaPres) {
                    if (finePeriodo) {
                        Modulo modPeriodo = PeriodoModulo.get();
                        SetValori sv = new SetValori(modPeriodo);
                        sv.add(Periodo.Cam.partito,true);
                        sv.add(Periodo.Cam.partenzaEffettiva, dataCambio);
                        sv.add(Periodo.Cam.causalePartenza,Periodo.CausaleAP.cambio.getCodice());
                        sv.add(Periodo.Cam.linkDestinazione,codPeriodoDest);
                        continua = modPeriodo.query().registraRecordValori(codPeriodoOrig, sv.getListaValori(), conn);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


            /**
             * Dopo il cambio, se il periodo di origine si prolunga oltre la data del cambio,
             * sottrae una persona dal periodo di origine perché la persona è stata
             * spostata su un altro periodo.
             */
            if (continua) {
                Modulo modPeriodo = PeriodoModulo.get();
                Date dataFine = modPeriodo.query().valoreData(Periodo.Cam.partenzaPrevista.get(),codPeriodoOrig, conn);
                if (Lib.Data.isPosteriore(dataCambio, dataFine)) {
                    continua = addRemovePersonaPeriodo(codPeriodoOrig, bambino, Operazione.sottrazione, conn);
                }// fine del blocco if
            }// fine del blocco if


            /**
             * Dopo il cambio, si verificano le presenze aperte legate al periodo destinazione
             * e si aggiorna il numero di adulti e bambini nel periodo destinazione
             */
            if (continua) {

                // recupera totale adulti e bambini
                Modulo modPresenza = PresenzaModulo.get();
                Filtro filtro = new Filtro();
                filtro.add(FiltroFactory.crea(Presenza.Cam.periodo.get(), codPeriodoDest));
                filtro.add(FiltroFactory.crea(Presenza.Cam.chiuso.get(), false));
                ArrayList lista = modPresenza.query().valoriCampo(Presenza.Cam.bambino.get(),filtro,conn);
                int totA=0,totB=0;
                for(Object obj: lista){
                    boolean bam=Libreria.getBool(obj);
                    if (bam) {
                        totB++;
                    } else {
                        totA++;
                    }// fine del blocco if-else
                }

                // aggiorna il periodo
                Modulo modPeriodo = PeriodoModulo.get();
                SetValori sv = new SetValori(modPeriodo);
                sv.add(Periodo.Cam.adulti,totA);
                sv.add(Periodo.Cam.bambini,totB);
                continua = modPeriodo.query().registraRecordValori(codPeriodoDest, sv.getListaValori(), conn);

            }// fine del blocco if


            /* conclude la transazione */
            if (transactionStarted) {
                if (continua) {
                    conn.commit();
                } else {
                    conn.rollback();
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Esegue le operazioni di annullamento arrivo, dopo che sono state confermate.
     * <p/>
     *
     * @param dialogo dialogo di preparazione annullamento arrivo
     *
     * @return true se riuscito
     */
    private static boolean esegueAnnullaArrivo(AnnullaArrivoDialogo dialogo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Modulo modPeriodo;
        Modulo modPresenza;
        Modulo modConto;
        Modulo modPrenotazione;
        int codCamera = 0;
        int[] codPresenze = null;
        int[] codConti = null;
        double totAddebiti;
        ArrayList<Integer> lista;
        int codPeriodo = 0;
        int codPrenotazione;
        Connessione conn = null;
        boolean transactionStarted = false;

        try { // prova ad eseguire il codice

            /* recupera il codice camera */
            if (continua) {
                codCamera = dialogo.getCodCamera();
                continua = (codCamera != 0);
            }// fine del blocco if

            /* recupera l'elenco delle presenze da annullare */
            if (continua) {
                codPresenze = dialogo.getPresenze();
                continua = (codPresenze != null);
            }// fine del blocco if

            /* recupera l'elenco dei conti aperti corrispondenti */
            if (continua) {
                codConti = PresenzaModulo.getContiAperti(codPresenze);
                continua = (codConti != null);
            }// fine del blocco if

            /* rimuove dall'elenco i conti che hanno degli addebiti
             * questi conti non verranno cancellati */
            if (continua) {
                lista = new ArrayList<Integer>();
                for (int cod : codConti) {
                    totAddebiti = ContoModulo.getTotAddebiti(cod, null, Progetto.getConnessione());
                    if (totAddebiti == 0) {
                        lista.add(cod);
                    }// fine del blocco if
                } // fine del blocco for
                codConti = Lib.Array.creaIntArray(lista);
            }// fine del blocco if

            /* recupera la connessione */
            if (continua) {
                conn = Progetto.getConnessione();
                continua = (conn != null);
            }// fine del blocco if

            /* apre una transazione */
            if (continua) {
                conn.startTransaction();
                transactionStarted = true;
            }// fine del blocco if

            /* cancella le presenze */
            if (continua) {
                modPresenza = PresenzaModulo.get();
                for (int cod : codPresenze) {
                    continua = modPresenza.query().eliminaRecord(cod, conn);
                    if (!continua) {
                        break;
                    }// fine del blocco if
                }// fine del blocco for
            }// fine del blocco if

            /* spegne il flag provvisorio a tutti quelli eventualmente rimasti nella camera  */
            if (continua) {
                codPresenze = PresenzaModulo.getPresenzeProvvisorie(codCamera);
                modPresenza = PresenzaModulo.get();
                for (int cod : codPresenze) {
                    modPresenza.query().registra(cod, Presenza.Cam.provvisoria.get(), false, conn);
                }// fine del blocco for
            }// fine del blocco if

            /* cancella i conti che non hanno addebiti */
            if (continua) {
                modConto = ContoModulo.get();
                for (int cod : codConti) {
                    continua = modConto.query().eliminaRecord(cod, conn);
                    if (!continua) {
                        break;
                    }// fine del blocco if
                }// fine del blocco for
            }// fine del blocco if

            /* spegne sempre il flag caparra accreditata nella prenotazione */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                codPeriodo = dialogo.getCodPeriodo();
                codPrenotazione = modPeriodo.query().valoreInt(Periodo.Cam.prenotazione.get(),
                        codPeriodo,
                        conn);
                if (codPrenotazione > 0) {
                    modPrenotazione = PrenotazioneModulo.get();
                    continua = modPrenotazione.query().registra(codPrenotazione,
                            Prenotazione.Cam.caparraAccreditata.get(),
                            false,
                            conn);
                }// fine del blocco if
            }// fine del blocco if

            /* spegne il flag arrivato nel periodo */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = modPeriodo.query().registra(codPeriodo,
                        Periodo.Cam.arrivato.get(),
                        false,
                        conn);
            }// fine del blocco if

            /* cancella la data di arrivo effettiva */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = modPeriodo.query().registra(codPeriodo,
                        Periodo.Cam.arrivoEffettivo.get(),
                        Lib.Data.getVuota(),
                        conn);
            }// fine del blocco if

            /* conclude la transazione */
            if (transactionStarted) {
                if (continua) {
                    conn.commit();
                } else {
                    conn.rollback();
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }



    /**
     * Aggiunge o sottrae una singola persona da/a un periodo.
     * <p/>
     * @param codPeriodo al quale aggiungere o sottrarre la persona
     * @param bambino false se adulto true se bambino
     * @param op operazione da eseguire (aggiunta/sottrazione)
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    private static boolean addRemovePersonaPeriodo(
            int codPeriodo, boolean bambino, Operazione op, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Campi campo, altroCampo;
        Modulo modPeriodo = PeriodoModulo.get();
        int quanti;

        try { // prova ad eseguire il codice

            /* recupera il campo da movimentare */
            if (bambino) {
                campo = Periodo.Cam.bambini;
            } else {
                campo = Periodo.Cam.adulti;
            }// fine del blocco if-else

            /* separa i due casi */
            switch (op) {

                case aggiunta:
                    quanti = modPeriodo.query().valoreInt(campo.get(), codPeriodo, conn);
                    riuscito = modPeriodo.query().registra(codPeriodo, campo.get(), quanti + 1, conn);
                    break;

                case sottrazione:

                    /**
                     * Sottrae la persona dal campo opportuno (adulto o bambino).
                     *
                     * Se non trova persone da sottrarre nel campo richiesto, le sottrae
                     * dall'altro campo, se non le trova neamche lì non fa nulla.
                     *
                     * (All'arrivo la persona prevista come adulto potrebbe essere
                     * stato registrato come bambino o viceversa... il periodo
                     * viene sincronizzato all'arrivo e dovrebbe corrispondere, questa
                     * è una sicurezza aggiuntiva).
                     */

                    /* recupera il campo alternativo */
                    if (bambino) {
                        altroCampo = Periodo.Cam.adulti;
                    } else {
                        altroCampo = Periodo.Cam.bambini;
                    }// fine del blocco if-else

                    Campi campoScrivere = campo;
                    quanti = modPeriodo.query().valoreInt(campo.get(), codPeriodo, conn);
                    if (quanti == 0) {
                        quanti = modPeriodo.query().valoreInt(altroCampo.get(), codPeriodo, conn);
                        campoScrivere = altroCampo;
                    }// fine del blocco if

                    /* esegue solo se il numero nel periodo è > 0 */
                    if (quanti > 0) {
                        riuscito = modPeriodo.query()
                                .registra(codPeriodo, campoScrivere.get(), quanti - 1, conn);
                    }// fine del blocco if

                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }



    /**
     * Esegue la modifica dei due periodi legati con un cambio.
     * <p/>
     * chiude il primo periodo (out)
     * apre il secondo periodo (in)
     *
     * @param codPrimoPeriodo codice del primo periodo del cambio
     * @param codSecondoPeriodo codice del secondo periodo del cambio
     * @param dataCambio la data del cambio
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    private static boolean cambiaPeriodi(int codPrimoPeriodo,
                                         int codSecondoPeriodo,
                                         Date dataCambio,
                                         Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Modulo modPeriodo = null;
        ArrayList<CampoValore> listaCV;
        CampoValore cv;
        SetValori sv;

        try {    // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codPrimoPeriodo, codSecondoPeriodo, dataCambio));

            /* recupera il modulo Periodi */
            if (continua) {
                modPeriodo = PeriodoModulo.get();
                continua = (modPeriodo != null);
            }// fine del blocco if

            /* chiude il primo periodo */
            if (continua) {
                sv = new SetValori(modPeriodo);
                sv.add(Periodo.Cam.partito, true);
                sv.add(Periodo.Cam.partenzaEffettiva, dataCambio);
                listaCV = sv.getListaValori();
                continua = modPeriodo.query().registraRecordValori(codPrimoPeriodo, listaCV, conn);
            }// fine del blocco if

            /* apre il secondo periodo */
            if (continua) {
                sv = new SetValori(modPeriodo);
                sv.add(Periodo.Cam.arrivato, true);
                sv.add(Periodo.Cam.arrivoEffettivo, dataCambio);
                listaCV = sv.getListaValori();
                continua = modPeriodo.query().registraRecordValori(codSecondoPeriodo,
                        listaCV,
                        conn);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Esegue la modifica delle presenze in conseguenza di un cambio.
     * <p/>
     * Chiude e riapre le presenze duplicando e modificando i record
     *
     * @param presenze codici dei record di Presenza da cambiare
     * @param codSecondoPeriodo codice del secondo periodo del cambio
     * @param codCameraDestinazione codice della camera destinazione
     * @param dataCambio la data del cambio
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    private static boolean cambiaPresenze(int[] presenze,
                                          int codSecondoPeriodo,
                                          int codCameraDestinazione,
                                          Date dataCambio,
                                          Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        int codPresNuova;

        try {    // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codSecondoPeriodo, codCameraDestinazione, dataCambio));

            /* spazzola i presenti e per ognuno invoca il metodo delegato */
            if (continua) {
                for (int cod : presenze) {
                    codPresNuova = chiudiPresenzaCambio(cod,
                            dataCambio,
                            codCameraDestinazione,
                            codSecondoPeriodo,
                            conn);
                    if (codPresNuova == 0) {
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Trasferisce il piano addebiti di un periodo su un conto.
     * <p/>
     * Modifica il riferimento alla camera e la sigla del conto
     * su tutti i conti puntati dalle presenze che cambiano
     * (ma solo quelli che puntavano alla camera di provenienza,
     * se il conto puntava a una camera diversa non viene modificato)
     *
     * @param codPeriodo codice del periodo
     * @param codConto codice del conto
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    private static boolean trasferisciAddebiti(int codPeriodo, int codConto, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;

        try { // prova ad eseguire il codice


            /* recupera i dati degli addebiti da leggere */
            Modulo modAddSource = AddebitoPeriodoModulo.get();
            Query query = new QuerySelezione(modAddSource);
            query.addCampo(Addebito.Cam.listino);
            query.addCampo(Addebito.Cam.quantita);
            query.addCampo(Addebito.Cam.prezzo);
            query.addCampo(AddebitoFisso.Cam.dataInizioValidita);
            query.addCampo(AddebitoFisso.Cam.dataFineValidita);
            query.addCampo(Addebito.Cam.note);
            query.addCampo(Addebito.Cam.codRigaListino);

            Filtro filtro = FiltroFactory.crea(AddebitoPeriodo.Cam.periodo.get(), codPeriodo);
            Ordine ordine = AddebitoPeriodoModulo.getOrdineAddebiti();
            query.setFiltro(filtro);
            query.setOrdine(ordine);

            Dati dati = modAddSource.query().querySelezione(query, conn);

            /* spazzola gli addebiti e li copia nel conto */
            Modulo modAddDest = AddebitoFissoModulo.get();
            for (int k = 0; k < dati.getRowCount(); k++) {

                SetValori sv = new SetValori(modAddDest);

                sv.add(Addebito.Cam.conto, codConto);
                sv.add(Addebito.Cam.listino, dati.getIntAt(k,Addebito.Cam.listino.get()));
                sv.add(Addebito.Cam.quantita, dati.getIntAt(k,Addebito.Cam.quantita.get()));
                sv.add(Addebito.Cam.prezzo, dati.getDoubleAt(k,Addebito.Cam.prezzo.get()));
                sv.add(AddebitoFisso.Cam.dataInizioValidita, dati.getDataAt(k,AddebitoFisso.Cam.dataInizioValidita.get()));
                sv.add(AddebitoFisso.Cam.dataFineValidita, dati.getDataAt(k,AddebitoFisso.Cam.dataFineValidita.get()));
                sv.add(Addebito.Cam.note, dati.getStringAt(k,Addebito.Cam.note.get()));
                sv.add(Addebito.Cam.codRigaListino, dati.getIntAt(k,Addebito.Cam.codRigaListino.get()));

                int codRec = modAddDest.query().nuovoRecord(sv, conn);

                // controllo se riuscito
                if (codRec<=0) {
                    k=dati.getRowCount()+1;  // forza uscita dal loop
                    continua=false;
                }// fine del blocco if

            } // fine del ciclo for

            dati.close();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }




    /**
     * Esegue le modifiche ai conti in conseguenza di un cambio.
     * <p/>
     * Modifica il riferimento alla camera e la sigla del conto
     * su tutti i conti puntati dalle presenze che cambiano
     * (ma solo quelli che puntavano alla camera di provenienza,
     * se il conto puntava a una camera diversa non viene modificato)
     *
     * @param presenze elenco delle presenze che cambiano
     * @param codCameraProvenienza codice della camera di provenienza
     * @param codCameraDestinazione codice della camera di destinazione
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    private static boolean cambiaConti(int[] presenze,
                                       int codCameraProvenienza,
                                       int codCameraDestinazione,
                                       Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        ArrayList<Integer> listaConti = null;
        ArrayList<Integer> listaContiDef = null;
        Modulo modConto = null;
        Modulo modPresenza = null;
        int codConto;
        int codCamera;

        try {    // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codCameraDestinazione));

            /* recupera il modulo Conti */
            if (continua) {
                modConto = ContoModulo.get();
                continua = (modConto != null);
            }// fine del blocco if

            /* recupera il modulo Presenze */
            if (continua) {
                modPresenza = PresenzaModulo.get();
                continua = (modPresenza != null);
            }// fine del blocco if

            /* recupera l'elenco univoco dei conti relativi alle presenze che cambiano */
            if (continua) {
                listaConti = new ArrayList<Integer>();
                for (int codPres : presenze) {
                    codConto = modPresenza.query().valoreInt(Presenza.Cam.conto.get(),
                            codPres,
                            conn);
                    if (codConto > 0) {
                        if (!listaConti.contains(codConto)) {
                            listaConti.add(codConto);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco for
            }// fine del blocco if

            /* rimuove dalla lista tutti i conti che non puntano alla camera di provenienza */
            if (continua) {
                listaContiDef = new ArrayList<Integer>();
                for (int cod : listaConti) {
                    codCamera = modConto.query().valoreInt(Conto.Cam.camera.get(), cod);
                    if (codCamera == codCameraProvenienza) {
                        listaContiDef.add(cod);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

            /* cambia la camera nei conti */
            if (continua) {
                for (int cod : listaContiDef) {
                    continua = modConto.query().registra(cod,
                            Conto.Cam.camera.get(),
                            codCameraDestinazione,
                            conn);
                    if (!continua) {
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Chiude una presenza per cambio
     * <p/>
     * Duplica la presenza
     * Chiude la presenza originale con chiusura per cambio
     * Apre la nuova presenza con entrata per cambio
     *
     * @param codice della presenza
     * @param dataCambio data di cambio
     * @param cameraDestinazione la camera di destinazione
     * @param codSecondoPeriodo codice del periodo destinazione (facoltativo)
     * @param conn connessione da utilizzare
     *
     * @return il codice della presenza riaperta dopo il cambio, 0 se non riuscito
     */
    private static int chiudiPresenzaCambio(int codice,
                                            Date dataCambio,
                                            int cameraDestinazione,
                                            int codSecondoPeriodo,
                                            Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codPresenzaNuova = 0;
        boolean continua;
        Modulo modPresenza = null;
        int codDuplicato = 0;
        SetValori sv;
        ArrayList<CampoValore> listaCV;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codice, dataCambio, cameraDestinazione));

            if (continua) {
                modPresenza = PresenzaModulo.get();
                continua = (modPresenza != null);
            }// fine del blocco if

            /* duplica la presenza originale */
            if (continua) {
                codDuplicato = modPresenza.query().duplicaRecord(codice, false, conn);
                continua = (codDuplicato > 0);
            }// fine del blocco if

            /* chiude la presenza originale come per partenza */
            if (continua) {
                continua = chiudiPresenza(codice, dataCambio, conn);
            }// fine del blocco if

            /**
             * Aggiunge le informazioni di chiusura per cambio
             * alla presenza chiusa
             */
            if (continua) {
                continua = modPresenza.query().registra(codice,
                        Presenza.Cam.cambioUscita.get(),
                        true,
                        conn);
            }// fine del blocco if

            /**
             * Modifica la presenza riaperta aggiungendo le informazioni
             * di riapertura per cambio
             */
            if (continua) {
                sv = new SetValori(modPresenza);
                sv.add(Presenza.Cam.entrata, dataCambio);
                sv.add(Presenza.Cam.cambioEntrata, true);
                sv.add(Presenza.Cam.camera, cameraDestinazione);
                sv.add(Presenza.Cam.provvisoria, false);

                /* solo se specificato, altrimenti lascia l'originale duplicato */
                if (codSecondoPeriodo != 0) {
                    sv.add(Presenza.Cam.periodo, codSecondoPeriodo);
                }// fine del blocco if

                listaCV = sv.getListaValori();
                continua = modPresenza.query().registraRecordValori(codDuplicato, listaCV, conn);
            }// fine del blocco if

            /* se riuscito regola il valore di ritorno */
            if (continua) {
                codPresenzaNuova = codDuplicato;
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codPresenzaNuova;
    }


    /**
     * Chiude una presenza per partenza
     * <p/>
     *
     * @param codice della presenza
     * @param partenza data di partenza
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    private static boolean chiudiPresenza(int codice, Date partenza, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Modulo mod = null;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codice, partenza));

            if (continua) {
                mod = PresenzaModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                continua = mod.query().registra(codice, Presenza.Cam.chiuso.get(), true, conn);
            }// fine del blocco if

            if (continua) {
                continua = mod.query().registra(codice, Presenza.Cam.uscita.get(), partenza, conn);
            }// fine del blocco if

            if (continua) {
                continua = mod.query().registra(codice,
                        Presenza.Cam.provvisoria.get(),
                        false,
                        conn);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Riapre una presenza esistente a causa di annullamento partenza.
     * <p/>
     *
     * @param codice della presenza
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    private static boolean riapriPresenza(int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Modulo mod = null;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codice));

            if (continua) {
                mod = PresenzaModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                continua = mod.query().registra(codice, Presenza.Cam.chiuso.get(), false, conn);
            }// fine del blocco if

            if (continua) {
                continua = mod.query().registra(codice,
                        Presenza.Cam.uscita.get(),
                        Lib.Data.getVuota(),
                        conn);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Crea un nuovo conto o aggiunge/sostituisce gli addebiti previsti a un conto esistente.
     * <p/>
     * - Se il codice del conto contenuto nel wrapper è = 0, crea un nuovo conto
     * - Se è diverso da 0, modifica il conto esistente
     * <p/>
     * Solo in caso di modifica conto esistente:
     * - se aggiungi è true, aggiunge gli addebiti a quelli esistenti (ed effettua la eventuale riduzione)
     * - se aggiungi è false, sostituisce gli addebiti esistenti
     *
     * @param wrapConto con le informazioni sul conto
     * @param aggiungi true per aggiungere gli addebiti, false per sostituirli
     * (significativo solo in caso di modifica conto esistente)
     * @param conn connessione da utilizzare
     *
     * @return il codice del conto creato o modificato, 0 se non riuscito
     */
    private static int syncConto(WrapConto wrapConto, boolean aggiungi, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codConto = 0;
        boolean continua;
        ContoModulo modConto;
        WrapAddebiti wrapAddebiti;
        WrapAddebiti wrapAddebitiNew;

        try {    // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (wrapConto != null) && (conn != null);

            /* crea o modifica il conto */
            if (continua) {
                codConto = wrapConto.getCodConto();
                modConto = ContoModulo.get();


                if (codConto == 0) {  // crea un nuovo conto
                    codConto = modConto.nuovoConto(wrapConto, conn);
                } else { // aggiunge gli addebiti a un conto esistente

                    if (aggiungi) { // aggiunge agli addebiti esistenti
                        wrapAddebiti = new WrapAddebitiConto(codConto,
                                conn);  // addebiti del conto esistente
                        wrapAddebitiNew = wrapConto.getWrapAddebiti();   // addebiti da aggiungere
                        wrapAddebiti.add(wrapAddebitiNew);     // aggiunge
                        wrapAddebiti.riduci();   // riduce elementi uguali
                        continua = wrapAddebiti.write(codConto, conn);   // scrive il wrapper
                    } else {  // sostituisce gli addebiti
                        wrapAddebitiNew = wrapConto.getWrapAddebiti();   // addebiti da scrivere
                        continua = wrapAddebitiNew.write(codConto, conn);   // scrive il wrapper
                    }// fine del blocco if-else

                    /* se non riuscito forza il ritorno a zero */
                    if (!continua) {
                        codConto = 0;
                    }// fine del blocco if

                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codConto;
    }


    /**
     * Crea le presenze.
     * <p/>
     *
     * @param data di inizio della presenza
     * @param codCamera codice della camera
     * @param listaArrivi lista dei clienti arrivati (codici cliente con flag bambino)
     * @param codConto codice del conto
     * @param arrivoCon codice dell' arrivo con
     * @param codPensione codice del tipo di pensione
     * @param codPasto codice del tipo di pasto se a mezza pensione
     * @param codPeriodo codice del periodo che origina le presenze
     * @param codAzienda codice dell'azienda
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    private static boolean creaPresenze(Date data,
                                        int codCamera,
                                        ArrayList<IntBool> listaArrivi,
                                        int codConto,
                                        int arrivoCon,
                                        int codPensione,
                                        int codPasto,
                                        int codPeriodo,
                                        int codAzienda,
                                        Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua;
        Modulo mod = null;
        int codPresenza;
        SetValori sv;
        ArrayList<CampoValore> listaCV;
//        IntBool wrapper;

        try { // prova ad eseguire il codice
            continua =
                    (data != null &&
                            codCamera > 0 &&
                            listaArrivi != null &&
                            listaArrivi.size() > 0);

            if (continua) {
                mod = PresenzaModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                riuscito = true;
                for (IntBool wrapper : listaArrivi) {
                    sv = new SetValori(mod);
                    sv.add(Presenza.Cam.arrivo.get(), data);
                    sv.add(Presenza.Cam.entrata.get(), data);
                    sv.add(Presenza.Cam.camera.get(), codCamera);
                    sv.add(Presenza.Cam.cliente.get(), wrapper.getInt());
                    sv.add(Presenza.Cam.bambino.get(), wrapper.getBool());
                    sv.add(Presenza.Cam.conto.get(), codConto);
                    sv.add(Presenza.Cam.arrivoCon.get(), arrivoCon);
                    sv.add(Presenza.Cam.pensione.get(), codPensione);
                    sv.add(Presenza.Cam.pasto.get(), codPasto);
                    sv.add(Presenza.Cam.periodo.get(), codPeriodo);
                    sv.add(Presenza.Cam.azienda.get(), codAzienda);
                    listaCV = sv.getListaValori();
                    codPresenza = mod.query().nuovoRecord(listaCV, conn);
                    if (codPresenza <= 0) {
                        riuscito = false;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Crea le presenze.
     * <p/>
     *
     * @param data di inizio della presenza
     * @param codCamera codice della camera
     * @param codCliente codice cliente
     * @param bambino true se bambino
     * @param codConto codice del conto
     * @param arrivoCon codice dell'arrivo con
     * @param codPensione codice del tipo di pensione
     * @param codPasto codice del tipo di pasto se a mezza pensione
     * @param codPeriodo codice del periodo che origina le presenze
     * @param codAzienda codice dell'azienda
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    private static boolean creaPresenze(Date data,
                                        int codCamera,
                                        int codCliente,
                                        boolean bambino,
                                        int codConto,
                                        int arrivoCon,
                                        int codPensione,
                                        int codPasto,
                                        int codPeriodo,
                                        int codAzienda,
                                        Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua;
        ArrayList<IntBool> listaArrivi;
        IntBool wrapper;


        try { // prova ad eseguire il codice
            continua = (data != null && codCamera > 0 && codCliente > 0);

            if (continua) {
                listaArrivi = new ArrayList<IntBool>();
                wrapper = new IntBool(codCliente, bambino);
                listaArrivi.add(wrapper);
                riuscito = creaPresenze(data,
                        codCamera,
                        listaArrivi,
                        codConto,
                        arrivoCon,
                        codPensione,
                        codPasto,
                        codPeriodo,
                        codAzienda,
                        conn);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Ritorna un messaggio che avvisa che non è possibile confermare
     * gli arrivi per una data azienda perché alcune stampe obbligatorie sono già state effettuate.
     * <p/>
     *
     * @param codAzienda codice della azienda
     *
     * @return testo del messaggio
     */
    public static String getMessaggioArriviNonConfermabili(int codAzienda) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        Date ultimaPS;
        Date ultimaNotif;
        Date ultimaISTAT;
        Date dataMax;
        Date dataMin;

        try { // prova ad eseguire il codice
            ultimaPS = StampeObbLogica.getDataUltimaStampaChiusa(TestaStampe.TipoRegistro.ps, codAzienda);
            ultimaNotif = StampeObbLogica.getDataUltimaStampaChiusa(TestaStampe.TipoRegistro.notifica, codAzienda);
            ultimaISTAT = StampeObbLogica.getDataUltimaStampaChiusa(TestaStampe.TipoRegistro.istat, codAzienda);
            dataMax = Lib.Data.getMax(ultimaPS, ultimaNotif, ultimaISTAT);
            dataMin = Lib.Data.add(dataMax, 1);
            testo = "Non è possibile confermare/annullare arrivi in data anteriore al ";
            testo += Lib.Data.getStringa(dataMin) + "\n";
            testo += "perché alcune stampe obbligatorie sono già state effettuate.\n";
            testo += "Per movimentare l'arrivo occorre prima annullare le stampe obbligatorie già effettuate.";
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Ritorna un messaggio che avvisa che non è possibile confermare
     * le partenze per una data azienda perché la stampa ISTAT è già stata effettuata.
     * <p/>
     *
     * @param codAzienda codice della azienda
     *
     * @return testo del messaggio
     */
    public static String getMessaggioPartenzeNonConfermabili(int codAzienda) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        Date ultimaISTAT;
        Date dataMin;

        try { // prova ad eseguire il codice
            ultimaISTAT = StampeObbLogica.getDataUltimaStampaChiusa(TestaStampe.TipoRegistro.istat, codAzienda);
            dataMin = Lib.Data.add(ultimaISTAT, 1);
            testo = "Non è possibile confermare/annullare partenze in data anteriore al ";
            testo += Lib.Data.getStringa(dataMin) + "\n";
            testo += "perché le stampe ISTAT sono già state effettuate.\n";
            testo += "Per movimentare la partenza occorre prima annullare le stampe ISTAT già effettuate.";
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }

    /**
     * Codifica del tipo di operazione effettuabile su un Periodo.
     * <p/>
     */
    private enum Operazione {
        aggiunta,
        sottrazione;
    }



}
