# Beleg PZR2 (94)
Checkboxen gemäß Bearbeitung befüllen und _kursiv_ gesetzten Text durch entsprechende Angaben ersetzten.
Bei keiner Angabe wird nur Entwurf, Tests (ohne Testabdeckung Rest), Fehlerfreiheit und Basisfunktionalität bewertet.
Die Zahl in der Klammer sind die jeweiligen Punkte für die Bewertung.
Die empfohlenen Realisierungen sind **fett** gesetzt und ergeben 50 Punkte.
Ergänzende Anmerkungen bitte immer _kursiv_ setzen. Andere Änderungen sind nicht zulässig.

## Vorrausetzungen für das Bestehen
- [X] Quellen angegeben
- [X] zip Archiv mit dem Projekt im root
- [X] IntelliJ-Projekt (kein Gradle, Maven o.ä.)
- [X] keine weiteren Bibliotheken außer JUnit5, Mockito und JavaFX (und deren Abhängigkeiten)
- [X] keine Umlaute, Sonderzeichen, etc. in Datei- und Pfadnamen
- [X] Trennung zwischen Test- und Produktiv-Code
- [X] kompilierbar
- [X] geforderte main-Methoden nur im default package des module belegProg3
- [X] keine vorgetäuschte Funktionalität (inkl. leere Tests)

## Entwurf (10)
- [X] **Benennung** (2)
- [X] **Zuständigkeit** (2)
- [X] **Paketierung** (2)
- [X] Schichtenaufteilung (via modules) (2)
- [X] Architekturdiagramm (1)
- [X] keine Duplikate (1)

## Tests (34)
- [X] **Testqualität** (9)
- [X] **Testabdeckung GL (inkl. Abhängigkeiten)** (9) _Abdeckung in Prozent angeben_
- [X] Testabdeckung Rest (6)
  - [x] Einfügen von Produzent*innen über das CLI _Tests angeben_
  - [x] Anzeigen von Produzent*innen über das CLI _Tests angeben_
  - [x] ein Beobachter _Tests angeben_
  - [ ] deterministische Funktionalität der Simulationen _Tests angeben_
  - [X] Speichern via JOS oder JBP _Tests angeben_
  - [X] Laden via JOS oder JBP _Tests angeben_
- [X] **mindestens 5 Unittests, die Mockito verwenden** (5)
- [X] mindestens 4 Spy- / Verhaltens-Tests (4)
- [X] **keine unbeabsichtigt fehlschlagenden Test** (1)

## Fehlerfreiheit (10)
- [X] **Kapselung** (5)
- [X] **keine Ablauffehler** (5)

## Basisfunktionalität (10)
- [X] **CRUD** (2)
- [X] **CLI** (2)
  * Syntax gemäß Anforderungen
- [X] **Simulation** (2)
  * ohne race conditions, ohne sleep
- [X] **I/O** (2)
- [ ] **Net** (2)

## Funktionalität (20)
- [X] vollständige GL (2)
- [X] threadsichere GL (1)
- [X] vollständiges CLI (2)
- [X] alternatives CLI (1)
  * _angeben welche Funktionalität im alternativen CLI deaktiviert_
- [X] ausdifferenziertes event-System mit mindestens 3 events (2)
- [X] observer (2)
- [X] angemessene Aufzählungstypen (2)
- [X] Simulation 2 (1)
- [ ] Simulation 3 (1)
- [ ] sowohl JBP als auch JOS (2)
- [ ] sowohl TCP als auch UDP (2)
- [ ] Server unterstützt konkurierende Clients für TCP oder UDP (2)

## zusätzliche Anforderungen GUI (5)
- [ ] Basis-GUI (CRUD) (1)
- [ ] skalierbare GUI (1)
- [ ] vollständige GUI (1)
- [ ] Sortierung in der GUI (1)
- [ ] data binding verwendet (1)

## zusätzliche Anforderungen (5)
gemeinsame Produzentenverwaltung: _ja/nein_
- [ ] Unterstützung mehrerer Instanzen (2)
- [ ] Ansteuerung per CLI (2)
- [ ] An- und Abschalten zur Laufzeit (1)

