# Beleg Vorschau (94)
Checkboxen gemäß Bearbeitung befüllen und _kursiv_ gesetzten Text durch entsprechende Angaben ersetzten.
Bei keiner Angabe wird nur Entwurf, Tests (ohne Testabdeckung Rest), Fehlerfreiheit und Basisfunktionalität bewertet.
Die Zahl in der Klammer sind die jeweiligen Punkte für die Bewertung.
Die empfohlenen Realisierungen sind **fett** gesetzt und ergeben 47 Punkte.
Ergänzende Anmerkungen bitte immer _kursiv_ setzen. Andere Änderungen sind nicht zulässig.

## Vorrausetzungen für das Bestehen
- [ ] Quellen angegeben
- [ ] zip Archiv mit dem Projekt im root
- [ ] IntelliJ-Projekt (kein Gradle, Maven o.ä.)
- [ ] keine weiteren Bibliotheken außer JUnit5, Mockito und JavaFX
- [ ] keine Umlaute, Sonderzeichen, etc. in Datei- und Pfadnamen
- [ ] mindestens sechs modules (zu jeder Basisfunktionalität außer I/O + belegProg3)
- [ ] Trennung zwischen Test- und Produktiv-Code
- [ ] kompilierbar
- [ ] geforderte main-Methoden nur im default package des module belegProg3
- [ ] keine vorgetäuschte Funktionalität

## Entwurf (10)
- [ ] **Benennung** (2)
- [ ] **Zuständigkeit** (2)
- [ ] **Paketierung** (2)
- [ ] Schichtenaufteilung (2)
- [ ] Architekturdiagramm (1)
- [ ] keine Duplikate (1)

## Tests (28)
- [ ] **Testqualität** (7)
- [ ] **Testabdeckung GL (inkl. Abhängigkeiten)** (7) _Abdeckung in Prozent angeben_
- [ ] Testabdeckung Rest (6)
  - [ ] Einfügen von Produzent*innen über das CLI _getestete Klassen angeben_
  - [ ] Anzeigen von Produzent*innen über das CLI _getestete Klassen angeben_
  - [ ] ein Beobachter _getestete Klassen angeben_
  - [ ] deterministische Funktionalität der Simulationen _getestete Klassen angeben_
  - [ ] Speichern via JOS oder JBP _getestete Klassen angeben_
  - [ ] Laden via JOS oder JBP _getestete Klassen angeben_
- [ ] **mindestens 5 Unittests, die Mockito verwenden** (4)
- [ ] mindestens 4 Spy- / Verhaltens-Tests (3)
- [ ] **keine unbeabsichtigt fehlschlagenden Test** (1)

## Fehlerfreiheit (10)
- [ ] **Kapselung** (5)
- [ ] **keine Ablauffehler** (5)

## Basisfunktionalität (10)
- [ ] **CRUD** (2)
- [ ] **CLI** (2)
  * Syntax gemäß Anforderungen
- [ ] **Simulation** (2)
  * ohne race conditions
- [ ] **I/O** (2)
  * in CLI oder GUI integriert
- [ ] **Net** (2)

## Funktionalität (23)
- [ ] vollständige GL (2)
- [ ] threadsichere GL (1)
- [ ] vollständiges CLI (1)
- [ ] alternatives CLI (1)
  * _angeben welche Funktionalität im alternativen CLI deaktiviert_
- [ ] ausdifferenziertes event-System mit mindestens 3 events (2)
- [ ] observer (2)
- [ ] angemessene Aufzählungstypen (2)
- [ ] Simulation 2 (1)
- [ ] Simulation 3 (1)
- [ ] skalierbare GUI (1)
- [ ] vollständige GUI (1)
- [ ] data binding verwendet (1)
- [ ] Änderung der Fachnummer mittels drag&drop (1)
- [ ] Einfügen von Mediadateien via GUI erfolgt nebenläufig (1)
- [ ] sowohl JBP als auch JOS (2)
- [ ] sowohl TCP als auch UDP (1)
- [ ] Server unterstützt konkurierende Clients für TCP oder UDP (2)

## zusätzliche Anforderungen (10)
