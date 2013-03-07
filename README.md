[![Build Status](https://secure.travis-ci.org/bekkopen/NoCommons.png)](http://travis-ci.org/bekkopen/NoCommons) 


NoCommons-biblioteket er en samling hjelpeklasser for manipulering, generering og validering av data spesifikt for Norge og norske innbyggere.

> **Summary in english**: The NoCommons library is a collection of helper classes for manipulation, generation and validation of data specific to Norway and Norwegian citizens.

Maven Site: [http://bekkopen.github.com/NoCommons/](http://bekkopen.github.com/NoCommons/)

# Features
* Personnummer
  * validering
  * generering (nyttig for testing)
* Organisasjonsnummer
  * [http://www.brreg.no/samordning/organisasjonsnummer.html](http://www.brreg.no/samordning/organisasjonsnummer.html)
  * validering
  * generering (nyttig for testing)
* Postnummer og poststed
  * Finn poststed basert på postnummer og omvendt
  * Finn poststed basert på starten av postnummer og omvendt
  * Basere seg på lister fra posten: [http://epab.posten.no/Norsk/Nedlasting/NedlastingMeny.htm](http://epab.posten.no/Norsk/Nedlasting/NedlastingMeny.htm])
* Telefonnummer
  * Gyldig nummer (mange er reserverte)
  * "Korrekte" skrivemåter
  * Diverse logikk rundt nummerserier - finn leverandør, fylke etc
 [http://www.npt.no/portal/page?_pageid=121,47706,121_47719&_dad=web&_schema=PORTAL](http://www.npt.no/portal/page?_pageid=121,47706,121_47719&_dad=web&_schema=PORTAL])
* Fylkesnummer
  * Finn fylkesnummer for fylke
  * Finn fylke for fylkesnummer
  * Validere fylke og / eller fylkesnummer
* Kommunenummer
  * Finn kommunenummer for kommune
  * Finn kommune for kommunenummer
  * Validere kommune og / eller kommunenummer
* Registreringsnummer
  * Validering av regnummer
  * Finn trafikkstasjon for bilnummer
  * Finn lenke på nettet til infoside
* Prior eggnummer
  * Validering av eggnummer
  * Finn bondegård basert på eggnummer (WS / screenscraping)
  * Finn pakkeri basert på eggnummer
  * Validering av eggnummer
* Bankkontonummer
  * Validering av kontonummer (modulus 11-sjekk)
  * Generering (nyttig for testing)
* Norsk språk o.l.
  * Konvertering av norske bokstaver i en tekst til de engelske erstatningene aa, ae og oe
  * motsatt konvertering, feks ved bruk av et ["memento-objekt"](http://en.wikipedia.org/wiki/Memento_pattern)
  * Generering av typisk Norske fiktive personnavn - nyttig for testing? (feks blande fra en liste av fornavn og etternavn herfra: [http://www.ssb.no/emner/00/navn/index.html](http://www.ssb.no/emner/00/navn/index.html))
* Norsk datofunksjonalitet
  * Sjekk om dato er helligdag
  * Sjekk om dato er arbeidsdag (dvs. ikke helligdag eller helg)
  * Legg til x antall arbeidsdager til en gitt dato
  * Liste ut alle helligdager
