package it.algos.albergo.clientealbergo;

import it.algos.albergo.clientealbergo.tabelle.autorita.Autorita;
import it.algos.albergo.clientealbergo.tabelle.autorita.AutoritaModulo;
import it.algos.albergo.clientealbergo.tabelle.parente.Parentela;
import it.algos.albergo.clientealbergo.tabelle.parente.ParentelaModulo;
import it.algos.albergo.clientealbergo.tabelle.tipodocumento.TipoDocumento;
import it.algos.albergo.clientealbergo.tabelle.tipodocumento.TipoDocumentoModulo;
import it.algos.albergo.tabelle.lingua.Lingua;
import it.algos.albergo.tabelle.lingua.LinguaModulo;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modello.Modello;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.wrapper.CampoValore;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.AnagraficaModulo;
import it.algos.gestione.anagrafica.tabelle.titolo.Titolo;
import it.algos.gestione.anagrafica.tabelle.titolo.TitoloModulo;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.IndirizzoModulo;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;
import it.algos.gestione.indirizzo.tabelle.via.Via;
import it.algos.gestione.indirizzo.tabelle.via.ViaModulo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Classe astratta per i moduli di creazione dei singoli files. </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-ott-2007 ore 19.21.46
 */
public class ImportClientiDialogo extends DialogoAnnullaConferma {

    /**
     * Mappa per selezionare il capogruppo.
     * Costruttore senza parametri.
     * <p/>
     */
    private LinkedHashMap<Integer, Integer> mappaGruppi;

    /**
     * Mappa della codifica nazioni - tra la vecchi e quella attuale.
     */
    private static LinkedHashMap<String, String> nazioni;

    /**
     * Mappa della codifica lingue - sigla (old) e descrizione (new).
     */
    private static LinkedHashMap<String, String> lingue;

    private ArrayList<String> colonne = null;


    /**
     * Costruttore senza parametri.
     * <p/>
     */
    public ImportClientiDialogo() {
        this(null);
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore base con parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public ImportClientiDialogo(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            this.setTitolo("Importazione iniziale dei dati");
            this.setMessaggio("Questa operazione richiede alcuni minuti\nVuoi proseguire?");

            /* mappa dei capigruppo */
            this.setMappaGruppi(new LinkedHashMap<Integer, Integer>());

            this.creaMappaNazioni();
            this.creaMappaLingue();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Esegue l'importazione dei dati.
     * </p>
     */
    public void esegueImport() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        List<List<String>> lista;
        int quanti = 0;

        try { // prova ad eseguire il codice
            lista = Lib.Imp.importCSV();
            continua = (lista != null);

            if (continua) {
                quanti = lista.size();
                continua = (quanti > 1); // ci deve essere la riga titoli
            }// fine del blocco if


            if (continua) {

                this.elaboraTitoli(lista.get(0));

                System.out.println("Start import anagrafiche: " + (quanti - 1) + " records");
                for (int k = 1; k < lista.size(); k++) {
                    this.elaboraRiga(lista.get(k));
                    System.out.println(k + "/" + (quanti - 1));   // esclusa riga titoli
                } // fine del ciclo for

            }// fine del blocco if

            if (continua) {
                this.getModulo().caricaTutti();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Estrae i nomi delle colonne dalla prima riga.
     * </p>
     *
     * @param rigaTitoli - lista delle colonne della prima riga
     */
    private void elaboraTitoli(List<String> rigaTitoli) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> listaColonne;
        String tagSep = ",";

        try { // prova ad eseguire il codice

            /* spazzola i titoli per estrarre il solo nome
             * contengono anche il tipo di dati e la lunghezza) */
            listaColonne = new ArrayList<String>();
            for (String col : rigaTitoli) {
                if (col.contains(tagSep)) {
                    col = Lib.Testo.prima(col, tagSep);
                    listaColonne.add(col);
                }// fine del blocco if
            } // fine del ciclo for-each

            this.setColonne(listaColonne);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Elaborazione del singolo record.
     * </p>
     *
     * @param riga - lista delle colonne della riga corrente
     */
    private void elaboraRiga(List<String> riga) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<String> listaColonne = null;
        String[] campi = null;
        String nome;
        String nomeCampo;
        String valore;
        Object valoreCampo;
        CampoValore cv;
        ArrayList<CampoValore> listaCampi;
        int numColonne;
        char car;
        String stringCar = "";
        String valCampo;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (riga != null);

            /* rimuove i soft wrap */
            if (continua) {
                car = (char)236;
                stringCar = Character.toString(car) + "\n";
            }// fine del blocco if

            if (continua) {
                listaColonne = this.getColonne();
                continua = (listaColonne != null);
            }// fine del blocco if

            if (continua) {
                numColonne = listaColonne.size();
                campi = new String[numColonne];
                for (int k = 0; k < numColonne; k++) {
                    valCampo = riga.get(k);
                    valCampo = valCampo.replaceAll(stringCar, "");
                    campi[k] = valCampo;
                } // fine del ciclo for
            }// fine del blocco if

            /* pulisce tutti ivalori, in modo che non rimangano valori della riga precedente */
            if (continua) {
                Colonna.reset();
            }// fine del blocco if

            if (continua) {
                listaCampi = new ArrayList<CampoValore>();
                for (Colonna colonna : Colonna.values()) {
                    if (!colonna.isFisico()) {
                        continue;
                    }// fine del blocco if

                    for (int k = 0; k < campi.length - 1; k++) {
                        nome = listaColonne.get(k);
                        if (nome.equals(colonna.getTitoloCsv())) {
                            valore = campi[k];
                            valore = Lib.Testo.levaVirgolette(valore);
                            colonna.setValore(valore);
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for
                } // fine del ciclo for-each

                /* eventuali campi non-fisici (calcolati) */
                Colonna.calcAll();

                /* campi da registrare sul database */
                for (Colonna colonna : Colonna.values()) {
                    if (!colonna.isDb()) {
                        continue;
                    }// fine del blocco if

                    nomeCampo = colonna.getNomeCampo();
                    valoreCampo = colonna.getValore();
                    cv = new CampoValore(nomeCampo, valoreCampo);
                    listaCampi.add(cv);
                } // fine del ciclo for-each

                /* registra */
                this.registraRecord(Colonna.nome.getValore().toString(),
                        Colonna.cognome.getValore().toString(),
                        listaCampi);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Controlla l'esistenza di un record con lo stesso nome e cognome.
     * </p>
     *
     * @param nome della persona
     * @param cognome della persona
     */
    private boolean isRecordMancante(String nome, String cognome) {
        /* variabili e costanti locali di lavoro */
        boolean mancante = false;
        boolean continua;
        Modulo mod = null;
        Filtro filtro = null;
        Filtro filtroNome;
        Filtro filtroCognome;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(nome, cognome));

            if (continua) {
                mod = AnagraficaModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                filtro = new Filtro();
                filtroNome = FiltroFactory.crea(Anagrafica.Cam.nome.get(), nome);
                filtroCognome = FiltroFactory.crea(Anagrafica.Cam.cognome.get(), cognome);
                filtro.add(filtroNome);
                filtro.add(filtroCognome);
            }// fine del blocco if

            /* controlla */
            if (continua) {
                mancante = (mod.query().contaRecords(filtro) == 0);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return mancante;
    }// fine del metodo


    /**
     * Codice di un record con nome e cognome.
     * </p>
     *
     * @param nome della persona
     * @param cognome della persona
     */
    private int getRecord(String nome, String cognome) {
        /* variabili e costanti locali di lavoro */
        int codRecord = 0;
        boolean continua;
        Modulo mod = null;
        Filtro filtro = null;
        Filtro filtroNome;
        Filtro filtroCognome;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(nome, cognome));

            if (continua) {
                mod = AnagraficaModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                filtro = new Filtro();
                filtroNome = FiltroFactory.crea(Anagrafica.Cam.nome.get(), nome);
                filtroCognome = FiltroFactory.crea(Anagrafica.Cam.cognome.get(), cognome);
                filtro.add(filtroNome);
                filtro.add(filtroCognome);
            }// fine del blocco if

            /* recupera */
            if (continua) {
                codRecord = mod.query().valoreChiave(filtro);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codRecord;
    }// fine del metodo


    /**
     * Registra il singolo record, solo se non esiste già.
     * </p>
     * Controlla nome e cognome <br>
     * Regola il gruppo di appartenenza <br>
     *
     * @param nome della persona
     * @param cognome della persona
     * @param listaCampi nomi e valori dei campi per il nuovo record
     */
    private void registraRecord(String nome, String cognome, ArrayList<CampoValore> listaCampi) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo mod = null;
        int codRecord = 0;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(nome, cognome));

            if (continua) {
                mod = ClienteAlbergoModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                continua = (listaCampi != null && listaCampi.size() > 0);
            }// fine del blocco if

            /* registra */
            if (continua) {
                if (this.isRecordMancante(nome, cognome)) {
                    codRecord = mod.query().nuovoRecord(listaCampi);
                } else {
                    codRecord = this.getRecord(nome, cognome);
                    mod.query().registraRecordValori(codRecord, listaCampi);
                }// fine del blocco if-else
                continua = (codRecord > 0);
            }// fine del blocco if

            if (continua) {
                this.regolaCapoGruppo(mod, codRecord);
                this.regolaIndirizzo(codRecord);
            }// fine del blocco if

            if (continua) {
                this.registraDate(codRecord);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Forza la registrazione delle date di creazione e modifica della vecchia anagrafica.
     * </p>
     */
    private void registraDate(int codRecord) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo mod;
        Date dataCreazione = null;
        Date dataModifica = null;
        Modello modello = null;
        boolean isTrigger;
        boolean isUsaPrec;
        Timestamp tempoCreazione;
        Timestamp tempoModifica;

        try { // prova ad eseguire il codice
            mod = ClienteAlbergoModulo.get();
            continua = (mod != null);

            if (continua) {
                dataCreazione = Libreria.getDate(Colonna.creazioneAna.getValore());
                dataModifica = Libreria.getDate(Colonna.modificaAna.getValore());
                continua = (Lib.Data.isValida(dataCreazione) || Lib.Data.isValida(dataModifica));
            }// fine del blocco if

            if (continua) {
                modello = mod.getModello();
                continua = (modello != null);
            }// fine del blocco if

            if (continua) {
                isTrigger = modello.isTriggerModificaAttivo();
                isUsaPrec = modello.isTriggerModificaUsaPrecedenti();
                modello.setTriggerModificaAttivo(false, false);

                if (Lib.Data.isValida(dataCreazione)) {
                    tempoCreazione = new Timestamp(dataCreazione.getTime());
                    mod.query().registra(codRecord,
                            ModelloAlgos.NOME_CAMPO_DATA_CREAZIONE,
                            tempoCreazione);
                }// fine del blocco if

                if (Lib.Data.isValida(dataModifica)) {
                    tempoModifica = new Timestamp(dataModifica.getTime());
                    mod.query().registra(codRecord,
                            ModelloAlgos.NOME_CAMPO_DATA_MODIFICA,
                            tempoModifica);
                }// fine del blocco if

                modello.setTriggerModificaAttivo(isTrigger, isUsaPrec);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Regola il valore del codice del capogruppo.
     * </p>
     *
     * @param codRecord codice del record appena registrato
     */
    private void regolaCapoGruppo(Modulo mod, int codRecord) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int codCapoGruppo = 0;
        int codImport = 0;
        LinkedHashMap<Integer, Integer> mappa = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codRecord));

            if (continua) {
                codImport = Libreria.getInt(Colonna.capo.getValore());
                continua = (codImport > 0);
            }// fine del blocco if

            if (continua) {
                mappa = this.getMappaGruppi();
                continua = (mappa != null);
            }// fine del blocco if

            if (continua) {
                if (mappa.containsKey(codImport)) {
                    codCapoGruppo = mappa.get(codImport);
                } else {
                    mappa.put(codImport, codRecord);
                    codCapoGruppo = codRecord;
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                mod.query().registra(codRecord, ClienteAlbergo.Cam.linkCapo.get(), codCapoGruppo);
                if (codCapoGruppo == codRecord) {
                    mod.query().registra(codRecord, Modello.NOME_CAMPO_PREFERITO, true);
                } else {
                    mod.query().registra(codRecord, Modello.NOME_CAMPO_PREFERITO, false);
                }// fine del blocco if-else
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Regola il valore di legame del campo-link parentela.
     * </p>
     * Controlla se esiste già il valore nella tavola collegata <br>
     * Se non esiste, lo crea <br>
     *
     * @param valore di testo proveniente dall'import
     */
    private static int regolaParente(Object valore) {
        /* variabili e costanti locali di lavoro */
        int codLink = 0;
        boolean continua;
        Modulo mod = null;
        boolean esiste;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(valore));

            if (continua) {
                mod = ParentelaModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                esiste = mod.query().isEsisteRecord(Parentela.Cam.sigla.get(), valore);
                if (esiste) {
                    codLink = mod.query().valoreChiave(Parentela.Cam.sigla.get(), valore);
                } else {
                    codLink = mod.query().nuovoRecord(Parentela.Cam.sigla.get(), valore);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codLink;
    }// fine del metodo


    /**
     * Regola il valore di legame del campo-link titolo.
     * </p>
     * Controlla se esiste già il valore nella tavola collegata <br>
     * Se non esiste, lo crea <br>
     *
     * @param valore di testo proveniente dall'import
     */
    private static int regolaTitolo(Object valore) {
        /* variabili e costanti locali di lavoro */
        int codLink = 0;
        boolean continua;
        Modulo mod = null;
        boolean esiste;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(valore));

            if (continua) {
                mod = TitoloModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                esiste = mod.query().isEsisteRecord(Titolo.Cam.sigla.get(), valore);
                if (esiste) {
                    codLink = mod.query().valoreChiave(Titolo.Cam.sigla.get(), valore);
                } else {
                    codLink = mod.query().nuovoRecord(Titolo.Cam.sigla.get(), valore);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codLink;
    }// fine del metodo


    /**
     * Regola il valore di legame del campo-link tipo di documento.
     * </p>
     * Controlla se esiste già il valore nella tavola collegata <br>
     * Se non esiste, lo crea <br>
     *
     * @param valore di testo proveniente dall'import
     */
    private static int regolaTipoDocumento(Object valore) {
        /* variabili e costanti locali di lavoro */
        int codLink = 0;
        boolean continua;
        Modulo mod = null;
        boolean esiste;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(valore));

            if (continua) {
                mod = TipoDocumentoModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                esiste = mod.query().isEsisteRecord(TipoDocumento.Cam.sigla.get(), valore);
                if (esiste) {
                    codLink = mod.query().valoreChiave(TipoDocumento.Cam.sigla.get(), valore);
                } else {
                    codLink = mod.query().nuovoRecord(TipoDocumento.Cam.sigla.get(), valore);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codLink;
    }// fine del metodo


    /**
     * Regola il valore di legame del campo-link autorita.
     * </p>
     * Controlla se esiste già il valore nella tavola collegata <br>
     * Se non esiste, lo crea <br>
     *
     * @param valore di testo proveniente dall'import
     */
    private static int regolaAutorita(Object valore) {
        /* variabili e costanti locali di lavoro */
        int codLink = 0;
        boolean continua;
        Modulo mod = null;
        boolean esiste;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(valore));

            if (continua) {
                mod = AutoritaModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                esiste = mod.query().isEsisteRecord(Autorita.Cam.sigla.get(), valore);
                if (esiste) {
                    codLink = mod.query().valoreChiave(Autorita.Cam.sigla.get(), valore);
                } else {
                    codLink = mod.query().nuovoRecord(Autorita.Cam.sigla.get(), valore);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codLink;
    }// fine del metodo


    /**
     * Controlla l'esistenza di un record di indirizzi con lo stesso valore.
     * </p>
     * Confronto sul campo indirizzo <br>
     *
     * @param codRecord codice del record di anagrafica appena registrato
     * @param indirizzo campo del primo indirizzo
     */
    private boolean isIndirizzoMancante(int codRecord, String indirizzo) {
        /* variabili e costanti locali di lavoro */
        boolean mancante = false;
        boolean continua;
        Modulo modInd = null;
        Modulo modAna = null;
        Filtro filtro = null;
        int linkAnagrafica;
        int codIndirizzo = 0;
        int mioCapogruppo;
        int capoIndirizzo;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(indirizzo));

            if (continua) {
                modInd = IndirizzoModulo.get();
                continua = (modInd != null);
            }// fine del blocco if

            if (continua) {
                modAna = this.getModulo();
                continua = (modAna != null);
            }// fine del blocco if

            if (continua) {
                filtro = FiltroFactory.crea(Indirizzo.Cam.indirizzo.get(), indirizzo);
            }// fine del blocco if

            /* controlla */
            if (continua) {
                codIndirizzo = modInd.query().valoreChiave(filtro);
                mancante = (codIndirizzo == 0);
            }// fine del blocco if

            if (continua) {
                if (!mancante) {
                    linkAnagrafica = modInd.query().valoreInt(Indirizzo.Cam.anagrafica.get(),
                            codIndirizzo);
                    if (linkAnagrafica > 0) {
                        mioCapogruppo = modAna.query().valoreInt(ClienteAlbergo.Cam.linkCapo.get(),
                                codRecord);
                        capoIndirizzo = modAna.query().valoreInt(ClienteAlbergo.Cam.linkCapo.get(),
                                linkAnagrafica);

                        mancante = (mioCapogruppo != capoIndirizzo);
                    }// fine del blocco if

                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return mancante;
    }// fine del metodo


    /**
     * Regola i valori dei campi dell'indirizzo.
     * </p>
     *
     * @param codRecord codice del record di anagrafica appena registrato
     */
    private void regolaIndirizzo(int codRecord) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo mod = null;
        int cod = 0;
        int codImport = 0;
        int via;
        String indirizzo = "";
        String numero;
        int codCitta = 0;
        String cap = "";
        int tipo;
        int provincia;
        int nazione;


        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codRecord));

            if (continua) {
                mod = IndirizzoModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                indirizzo = Colonna.indirizzo.getValore().toString();
                continua = (Lib.Testo.isValida(indirizzo));
            }// fine del blocco if

            if (continua) {
                continua = this.isIndirizzoMancante(codRecord, indirizzo);
            }// fine del blocco if

            if (continua) {
                codCitta = this.getCodCitta(Colonna.localita);
                cap = Colonna.cap.getValore().toString();
            }// fine del blocco if

            if (continua) {
                cod = mod.query().nuovoRecord(Indirizzo.Cam.indirizzo.get(), indirizzo);
                mod.query().registra(cod, Indirizzo.Cam.citta.get(), codCitta);
                mod.query().registra(cod, Indirizzo.Cam.cap.get(), cap);
                mod.query().registra(cod, Indirizzo.Cam.anagrafica.get(), codRecord);
                AnagraficaModulo.get().query().registra(codRecord,
                        ClienteAlbergo.Cam.indirizzoInterno.get(),
                        cod);
            }// fine del blocco if

            /* specificazione aggiuntiva */
            if (continua) {
//                this.regolaVia(mod, cod, indirizzo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Regola il tipo (via) di indirizzo.
     * </p>
     * Estrae la prima parola dell'indirizzo, supponendo che sia una tipologia da codificare <br>
     *
     * @param modInd modulo indirizzi
     * @param codRecord codice del record di indirizzi appena registrato
     * @param indirizzo valore del campo indirizzi da elaborare ulteriormente
     */
    private void regolaVia(Modulo modInd, int codRecord, String indirizzo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modVia = null;
        int codLink = 0;
        String prima = "";
        String tag = " ";
        boolean esiste;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(codRecord, indirizzo));

            if (continua) {
                modVia = ViaModulo.get();
                continua = (modVia != null);
            }// fine del blocco if

            if (continua) {
                prima = Lib.Testo.prima(indirizzo, tag);
                continua = (Lib.Testo.isValida(prima));
            }// fine del blocco if

            if (continua) {
                indirizzo = indirizzo.substring(prima.length()).trim();
                continua = (Lib.Testo.isValida(indirizzo));
            }// fine del blocco if

            if (continua) {
                esiste = modVia.query().isEsisteRecord(Via.Cam.sigla.get(), prima);
                if (esiste) {
                    codLink = modVia.query().valoreChiave(Via.Cam.sigla.get(), prima);
                } else {
                    codLink = modVia.query().nuovoRecord(Via.Cam.sigla.get(), prima.toLowerCase());
                }// fine del blocco if-else
                continua = (codLink > 0);
            }// fine del blocco if

            if (continua) {
                modInd.query().registra(codRecord, Indirizzo.Cam.via.get(), codLink);
                modInd.query().registra(codRecord, Indirizzo.Cam.indirizzo.get(), indirizzo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Codice della città.
     * </p>
     * Se manca, la crea <br>
     */
    private static int getCodCitta(Colonna colonna) {
        /* variabili e costanti locali di lavoro */
        int codCitta = 0;
        boolean continua;
        Modulo mod;
        String citta = "";

        try { // prova ad eseguire il codice
            mod = CittaModulo.get();
            continua = (mod != null);

            if (continua) {
                citta = colonna.getValore().toString();
                continua = (Lib.Testo.isValida(citta));
            }// fine del blocco if

            if (continua) {
                codCitta = mod.query().valoreChiave(Citta.Cam.citta.get(), citta);
            }// fine del blocco if

            if (continua) {
                if (codCitta == 0) {
                    codCitta = creaCitta(colonna);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codCitta;
    }// fine del metodo


    /**
     * Crea una città mancante.
     * Se è la citta di residenza, conosco cap, provincia, regione e stato <br>
     * Se è la città di nascita, conosco stato e provincia <br>
     */
    private static int creaCitta(Colonna colonna) {
        /* variabili e costanti locali di lavoro */
        int codCitta = 0;
        boolean continua;
        String citta = "";
        Modulo modCitta;
        Modulo modNaz = null;
        Modulo modPro = null;
        String nazOld = "";
        String nazNew;
        LinkedHashMap<String, String> nazioni = null;
        int codNaz = 0;
        String cap = "";
        String siglaPro = "";
        String siglaReg = "";
        int codPro;
        int codNazDellaProv;
        String note;

        try { // prova ad eseguire il codice
            modCitta = CittaModulo.get();
            continua = (modCitta != null);

            if (continua) {
                citta = colonna.getValore().toString();
                continua = (Lib.Testo.isValida(citta));
            }// fine del blocco if

            if (continua) {
                modNaz = NazioneModulo.get();
                continua = (modNaz != null);
            }// fine del blocco if

            /* crea comunque la città */
            if (continua) {
                codCitta = modCitta.query().nuovoRecord(Citta.Cam.citta.get(), citta);
                continua = (codCitta > 0);
            }// fine del blocco if

            /* mappa di conversione sigla elio con sigle ISO degli stati */
            if (continua) {
                nazioni = getNazioni();
                continua = (nazioni != null);
            }// fine del blocco if

            /* aggiunge il link alla nazione */
            if (continua) {
                if (colonna == Colonna.localita) {
                    nazOld = Colonna.nazione.getValore().toString();
                }// fine del blocco if
                if (colonna == Colonna.luogoNato) {
                    nazOld = Colonna.nazioneNato.getValore().toString();
                }// fine del blocco if

                if (Lib.Testo.isValida(nazOld)) {
                    nazNew = nazioni.get(nazOld);
                    if (Lib.Testo.isValida(nazNew)) {
                        codNaz = modNaz.query().valoreChiave(Nazione.Cam.sigla2.get(), nazNew);
                        if (codNaz > 0) {
                            modCitta.query().registra(codCitta,
                                    Citta.Cam.linkNazione.get(),
                                    codNaz);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* aggiunge il cap */
            if (continua) {
                if (colonna == Colonna.localita) {
                    cap = Colonna.cap.getValore().toString();
                }// fine del blocco if

                if (Lib.Testo.isValida(cap)) {
                    modCitta.query().registra(codCitta, Citta.Cam.cap.get(), cap);
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                modPro = ProvinciaModulo.get();
                continua = (modPro != null);
            }// fine del blocco if

            /* aggiunge la sigla provincia */
            if (continua) {
                if (colonna == Colonna.localita) {
                    siglaPro = Colonna.provincia.getValore().toString();
                }// fine del blocco if

                if (Lib.Testo.isValida(siglaPro)) {
                    codPro = modPro.query().valoreChiave(Provincia.Cam.sigla.get(), siglaPro);
                    if (codPro == 0) {
                        modCitta.query().registra(codCitta,
                                ModelloAlgos.NOME_CAMPO_NOTE,
                                "Codice provincia = " + siglaPro);
                    } else {
                        codNazDellaProv = modPro.query().valoreInt(Provincia.Cam.linkNazione.get(),
                                codPro);

                        if (codNazDellaProv == codNaz) {
                            modCitta.query().registra(codCitta,
                                    Citta.Cam.linkProvincia.get(),
                                    codPro);
                        } else {
                            modCitta.query().registra(codCitta,
                                    ModelloAlgos.NOME_CAMPO_NOTE,
                                    "Codice provincia = " + siglaPro);
                        }// fine del blocco if-else
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine del blocco if

            /* aggiunge la nota sulla regione */
            if (continua) {
                if (colonna == Colonna.localita) {
                    siglaReg = Colonna.regione.getValore().toString();
                }// fine del blocco if

                if (Lib.Testo.isValida(siglaReg)) {
                    note = modCitta.query().valoreStringa(ModelloAlgos.NOME_CAMPO_NOTE, codCitta);
                    if (Lib.Testo.isValida(note)) {
                        note += "\n";
                    }// fine del blocco if
                    note += "Codice regione = " + siglaReg;
                    modCitta.query().registra(codCitta, ModelloAlgos.NOME_CAMPO_NOTE, note);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codCitta;
    }// fine del metodo


    /**
     * Costruisce la data dalla stringa.
     * </p>
     * Confronto sul campo indirizzo <br>
     */
    private static Date regolaData(Object ogg) {
        /* variabili e costanti locali di lavoro */
        Date data = Lib.Data.getVuota();
        String dataStringa;
        boolean continua;
        String tagOld = "/";
        String tagNew = "-";
        String anno;
        String[] parti = null;
        String dataNew;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(ogg));

            if (continua) {
                dataStringa = ogg.toString();
                dataStringa = dataStringa.replaceAll(tagOld, tagNew);
                parti = dataStringa.split(tagNew);
                continua = (parti != null && parti.length == 3);
            }// fine del blocco if

            if (continua) {
                anno = parti[2];
                if (anno.length() == 2) {
                    if (anno.startsWith("0")) {
                        anno = "20" + anno;
                    } else {
                        anno = "19" + anno;
                    }// fine del blocco if-else
                }// fine del blocco if
                dataNew = parti[0] + tagNew + parti[1] + tagNew + anno;
                data = Libreria.getDate(dataNew);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }// fine del metodo


    /**
     * Costruisce il booleano dalla stringa.
     * </p>
     */
    private static boolean regolaBool(Object ogg) {
        /* variabili e costanti locali di lavoro */
        boolean flag = false;
        String stringa;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(ogg));

            if (continua) {
                stringa = ogg.toString();

                if (stringa.equals("VERO")) {
                    flag = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return flag;
    }// fine del metodo


    /**
     * Regola la città di nascita.
     * </p>
     */
    private static int regolaNascita(Object ogg) {
        /* variabili e costanti locali di lavoro */
        int codCitta = 0;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(ogg));

            if (continua) {
                codCitta = getCodCitta(Colonna.luogoNato);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codCitta;
    }// fine del metodo


    /**
     * Regola il valore di legame del campo-link lingua.
     * </p>
     * Controlla se esiste già il valore nella tavola collegata <br>
     * Se non esiste, lo crea <br>
     *
     * @param valore di testo proveniente dall'import
     */
    private static int regolaLingua(Object valore) {
        /* variabili e costanti locali di lavoro */
        int codLink = 0;
        boolean continua;
        Modulo mod = null;
        boolean esiste;
        String valDesc;
        LinkedHashMap<String, String> mappa = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(valore));

            if (continua) {
                mod = LinguaModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                mappa = getLingue();
                continua = (mappa != null && mappa.size() > 0);
            }// fine del blocco if

            if (continua) {
                esiste = mod.query().isEsisteRecord(Lingua.Cam.sigla.get(), valore);
                if (esiste) {
                    codLink = mod.query().valoreChiave(Lingua.Cam.sigla.get(), valore);
                } else {
                    codLink = mod.query().nuovoRecord(Lingua.Cam.sigla.get(), valore);
                    valDesc = mappa.get(valore);
                    mod.query().registra(codLink, Lingua.Cam.descrizione.get(), valDesc);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codLink;
    }// fine del metodo


    /**
     * Crea la mappa delle nazioni.
     * </p>
     */
    private void creaMappaNazioni() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, String> mappa;

        try { // prova ad eseguire il codice
            mappa = new LinkedHashMap<String, String>();

            mappa.put("A", "AT");
            mappa.put("ALB", "AL");
            mappa.put("ARG", "AR");
            mappa.put("AU", "AT");
            mappa.put("AUS", "AU");
            mappa.put("B", "BE");
            mappa.put("BELG", "BE");
            mappa.put("BRAS", "BR");
            mappa.put("CAN", "CA");
            mappa.put("CDN", "CA");
            mappa.put("CECO", "CZ");
            mappa.put("CH", "CH");
            mappa.put("CILE", "CL");
            mappa.put("D", "DE");
            mappa.put("DK", "DK");
            mappa.put("E", "ES");
            mappa.put("EC", "EC");
            mappa.put("EIRE", "IE");
            mappa.put("ESTO", "EE");
            mappa.put("F", "FR");
            mappa.put("GB", "GB");
            mappa.put("H", "HU");
            mappa.put("HR", "HR");
            mappa.put("I", "IT");
            mappa.put("IL", "IL");
            mappa.put("IND", "IN");
            mappa.put("L", "LU");
            mappa.put("LIEC", "LI");
            mappa.put("LITU", "LT");
            mappa.put("MC", "MC");
            mappa.put("NL", "NL");
            mappa.put("POL", "PL");
            mappa.put("PORT", "PT");
            mappa.put("RDOM", "DO");
            mappa.put("ROM", "RO");
            mappa.put("RSM", "SM");
            mappa.put("RUS", "RU");
            mappa.put("S", "SE");
            mappa.put("USA", "US");

            setNazioni(mappa);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Crea la mappa delle nazioni.
     * </p>
     */
    private void creaMappaLingue() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, String> mappa;

        try { // prova ad eseguire il codice
            mappa = new LinkedHashMap<String, String>();

            mappa.put("D", "tedesco");
            mappa.put("E", "inglese");
            mappa.put("F", "francese");
            mappa.put("I", "italiano");
            mappa.put("S", "spagnolo");

            setLingue(mappa);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo


    private LinkedHashMap<Integer, Integer> getMappaGruppi() {
        return mappaGruppi;
    }


    private void setMappaGruppi(LinkedHashMap<Integer, Integer> mappaGruppi) {
        this.mappaGruppi = mappaGruppi;
    }


    private static LinkedHashMap<String, String> getNazioni() {
        return nazioni;
    }


    private static void setNazioni(LinkedHashMap<String, String> nazioni) {
        ImportClientiDialogo.nazioni = nazioni;
    }


    private static LinkedHashMap<String, String> getLingue() {
        return lingue;
    }


    private static void setLingue(LinkedHashMap<String, String> lingue) {
        ImportClientiDialogo.lingue = lingue;
    }


    private ArrayList<String> getColonne() {
        return colonne;
    }


    private void setColonne(ArrayList<String> colonne) {
        this.colonne = colonne;
    }


    /**
     * Classe interna Enumerazione.
     */
    private enum Colonna {

        capo("GRUPPO", ClienteAlbergo.Cam.linkCapo.get(), true, true) {
            protected void calc() {
                this.setValore(Libreria.getInt(this.getValore()));
            }
        },
        parente("PARENTELA", ClienteAlbergo.Cam.parentela.get(), true, true) {
            protected void calc() {
                this.setValore(regolaParente(this.getValore()));
            }
        },
        titolo("TITOLO", Anagrafica.Cam.titolo.get(), true, true) {
            protected void calc() {
                this.setValore(regolaTitolo(this.getValore()));
            }
        },
        nome("NOME", Anagrafica.Cam.nome.get(), true, true),
        cognome("COGNOME", Anagrafica.Cam.cognome.get(), true, true),
        soggetto("", Anagrafica.Cam.soggetto.get(), false, true) {
            protected void calc() {
                this.setValore(Colonna.cognome.getValore() + " " + Colonna.nome.getValore());
            }
        },
        sesso("SESSO", Anagrafica.Cam.sesso.get(), true, true) {
            protected void calc() {
                Object val = this.getValore();

                if (Lib.Testo.isValida(val)) {
                    if (val.equals("F")) {
                        val = 2;
                    } else {
                        val = 1;
                    }// fine del blocco if-else
                }// fine del blocco if
                this.setValore(val);
            }
        },
        luogoNato("LUOGONASCI", ClienteAlbergo.Cam.luogoNato.get(), true, true) {
            protected void calc() {
                this.setValore(regolaNascita(this.getValore()));
            }
        },
        dataNato("DATANASCI", ClienteAlbergo.Cam.dataNato.get(), true, true) {
            protected void calc() {
                this.setValore(regolaData(this.getValore()));
            }
        },
        nazioneNato("NAZ_NASCI", "", true, false),
        provinciaNato("PROV_NASCI", "", true, false),

        indirizzo("INDIRIZZO", Indirizzo.Cam.indirizzo.get(), true, false),
        cap("CAP", Indirizzo.Cam.cap.get(), true, false),
        localita("LOCALITA", Indirizzo.Cam.citta.get(), true, false),
        provincia("PROVINCIA", Citta.Cam.linkProvincia.get(), true, false),
        nazione("NAZIONE", Citta.Cam.linkNazione.get(), true, false),
        regione("REGIONE", "", true, false),
        telefono("TELEFONO", Anagrafica.Cam.telefono.get(), true, true),
        cellulare("", Anagrafica.Cam.cellulare.get(), false, false),
        fax("TELEFAX", Anagrafica.Cam.fax.get(), true, true),

        dataDoc("RILDOC", ClienteAlbergo.Cam.dataDoc.get(), true, true) {
            protected void calc() {
                this.setValore(regolaData(this.getValore()));
            }
        },
        codiceDoc("CODDOC", ClienteAlbergo.Cam.tipoDoc.get(), true, true) {
            protected void calc() {
                this.setValore(regolaTipoDocumento(this.getValore()));
            }
        },
        autoritaDoc("AUTRILASCI", ClienteAlbergo.Cam.autoritaDoc.get(), true, true) {
            protected void calc() {
                this.setValore(regolaAutorita(this.getValore()));
            }
        },
        numDoc("NUMDOC", ClienteAlbergo.Cam.numDoc.get(), true, true),
        scadenzaDoc("SCADDOC", ClienteAlbergo.Cam.scadenzaDoc.get(), true, true) {
            protected void calc() {
                this.setValore(regolaData(this.getValore()));
            }
        },
        creazioneAna("DATA_REGIS", "", true, false) {
            protected void calc() {
                this.setValore(regolaData(this.getValore()));
            }
        },
        modificaAna("ULT_MOD", "", true, false) {
            protected void calc() {
                this.setValore(regolaData(this.getValore()));
            }
        },
        lingua("LINGUA", ClienteAlbergo.Cam.lingua.get(), true, true) {
            protected void calc() {
                this.setValore(regolaLingua(this.getValore()));
            }
        },
        posta("CORRISPOND", ClienteAlbergo.Cam.checkPosta.get(), true, true) {
            protected void calc() {
                this.setValore(regolaBool(this.getValore()));
            }
        },
        famiglia("FAMIGLIA", ClienteAlbergo.Cam.checkFamiglia.get(), true, true) {
            protected void calc() {
                this.setValore(regolaBool(this.getValore()));
            }
        },
        evidenza("EVIDENZ", ClienteAlbergo.Cam.checkEvidenza.get(), true, true) {
            protected void calc() {
                this.setValore(regolaBool(this.getValore()));
            }
        },
        note("NOTE", ModelloAlgos.NOME_CAMPO_NOTE, true, true),
        notePers("PERSONALE", ClienteAlbergo.Cam.notePersonali.get(), true, true),;


        /**
         * titolo tabella import
         */
        private String titoloCsv;

        /**
         * nome del campo
         */
        private String nomeCampo;

        /**
         * flag
         */
        private boolean fisico;

        /**
         * flag
         */
        private boolean db;

        /**
         * valore corrente
         */
        private Object valore;


        /**
         * Costruttore completo con parametri.
         *
         * @param titoloCsv - titolo tabella import
         * @param nomeCampo - nome del campo
         * @param fisico - flag
         * @param db - flag
         */
        Colonna(String titoloCsv, String nomeCampo, boolean fisico, boolean db) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitoloCsv(titoloCsv);
                this.setNomeCampo(nomeCampo);
                this.setFisico(fisico);
                this.setDb(db);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static void reset() {
            try { // prova ad eseguire il codice
                for (Colonna col : Colonna.values()) {
//                    col.setValore(null);
                } // fine del ciclo for-each
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static void calcAll() {
            try { // prova ad eseguire il codice
                for (Colonna col : Colonna.values()) {
                    col.calc();
                } // fine del ciclo for-each
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        protected void calc() {
        }


        public String getTitoloCsv() {
            return titoloCsv;
        }


        private void setTitoloCsv(String titoloCsv) {
            this.titoloCsv = titoloCsv;
        }


        public String getNomeCampo() {
            return nomeCampo;
        }


        private void setNomeCampo(String nomeCampo) {
            this.nomeCampo = nomeCampo;
        }


        public boolean isFisico() {
            return fisico;
        }


        private void setFisico(boolean fisico) {
            this.fisico = fisico;
        }


        public boolean isDb() {
            return db;
        }


        private void setDb(boolean db) {
            this.db = db;
        }


        public Object getValore() {
            return valore;
        }


        public void setValore(Object valore) {
            this.valore = valore;
        }
    }// fine della classe


}// fine della classe
