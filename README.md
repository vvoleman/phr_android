![Static Analysis](https://github.com/vvoleman/phr_android/actions/workflows/detekt.yml/badge.svg)
![JUnit](https://github.com/vvoleman/phr_android/actions/workflows/junit.yml/badge.svg)

# Mobilní aplikace pro osobní evidenci zdravotní péče

- Autor: [Vojtěch Voleman](https://github.com/vvoleman)
- Vedoucí práce: [Ing. Jana Vitvarová, Ph.D.](https://www.fm.tul.cz/personal/jana.vitvarova)

# Zadání práce

| Název tématu          | Aplikace pro osobní evidenci zdravotní péče |
|-----------------------|---------------------------------------------|
| Název tématu anglicky | Personal healthcare app                     |

## Zásady pro vypracování

1. Proveďte rešerši existujících aplikací pro evidenci osobní zdravotní péče.
2. Analyzujte potřeby pacientů při vybraných akutních i chronických zdravotních problémech.
3. Analyzujte využitelná veřejně dostupná zdravotnická data, jako například informace o lécích, či
   kódové označení nemocí. Dále zdroje dat, které má pacient k dispozici, jako například přehledy
   zdravotní péče od zdravotní pojišťovny nebo papírové lékařské zprávy.
4. Vytvořte funkční specifikaci aplikace dle zjištěných potřeb.
5. Navrhněte technické řešení a vyberte platformu s důrazem na bezpečné nakládání s citlivými daty.
6. Vytvořte interaktivní prototyp uživatelského rozhraní.
7. Implementujte a testujte prototyp aplikace procesem CI/CD.
8. Proveďte uživatelské testování a výsledky vyhodnoťte.

### Literatura

1. ECKEL, Bruce a Svetlana ISAKOVA. Atomic Kotlin. Mindview, 2021. ISBN 978-0-9818725-5-1.
2. MARTIN, Robert C. Clean Code: A Handbook of Agile Software Craftsmanship. Pearson, 2008. ISBN
   0132350882.
3. MKN-10 klasifikace [online]. [cit. 2022-10-12]. Dostupné z: https://mkn10.uzis.cz

## O projektu

Tato mobilní aplikace byla vypracována jako součást bakalářské práce na FM TUL. Aplikace má za cíl
ulehčit uživateli evidenci svého zdravotní stavu a své zdravotní péče. Data o léčivých přípravcích,
diagnózách a lékařských zařízeních aplikace čerpá z [API serveru](https://github.com/vvoleman/phr),
který byl v rámci této práce také vypracován. Uživatelské rozhraní bylo navrženo v nástroji Figma,
odkaz na prototyp je k
dispozici [zde](https://www.figma.com/design/DAySWnTruVGtQRTTjM7kh4/PHR?node-id=102%3A40&t=xTlttjfjksAryQjZ-1).
Finální verze bakalářské práce je k dispozici v souboru [bp_vvoleman_final.pdf](bp_vvoleman_final.pdf).

### Cílové skupiny

#### Běžný uživatel

- je systematický, chodí spíše jen na preventivní prohlídky
- chce mít pořádek v lékařských zprávách
- chce všechny kontakty na využívané lékaře na jednom místě
- chce přehled o termínech, "kalendář"

#### Uživatel se zdravotní indispozicí

- běžné funkce
- potřebuje si udržovat, kdy má brát jaké léky
- rád by měl po ruce příbalové letáky
- musí si zaznamenávat některé zdravotní hodnoty (tlak, tep...)

#### Rodinný příslušník/opatrovník

- potřebuje si udržovat pořádek v lék. zprávách svého "pacienta"
- chce znát termíny návstěv
- musí pravidelně více pacientům podávat rozdílné léky

### Funkce

Aplikace rozděluje své funkce do několika modulů, které se týkají různých aspektů zdravotní péče.

#### Lékařské zprávy

V tomto modulu si uživatel zaznamenává lékařské zprávy obdržené při návštěvě lékařského zařízení.
Při přidání uživatel vyfotí zprávu a aplikace se pokusí z fotky extrahovat některá data (datum
návštěvy, diagnóza).
Uživatel může následně doplnit chybějící informace a zprávu uložit. Uživatel si může zprávy
prohlížet,
upravovat, exportovat do PDF a mazat.

#### Léky

V tomto modulu si uživatel eviduje léky, které užívá a aplikace mu připomíná jejich užití. Při
přidání
léku nejprve vyhledá lék v databázi léků a následně si vybere jeho dávkování. V přehledu uživatel
vidí,
které léky má dnes ještě užít. Po kliknutí na lék si může zobrazit jeho detaily či otevřít
příbalový leták.

#### Měření

V tomto modulu si uživatel eviduje svá měření (např. krevní tlak, hmotnost, teplota). Uživatel si
nejprve
definuje jaké položky mají v rámci jednoho měření a posléže si nastaví upozornění na měření.

#### Plány návštěv

V tomto modulu si uživatel eviduje své budoucí návštěvy lékařských zařízení. Při vytváření může
určit,
kterého problémy či lékaře se návštěva týká. Aplikace uživatele upozorní na blížící se návštěvu.

#### Ostatní

Mimo tyto hlavní moduly aplikace obsahuje i funkce, které jsou součástí modulu Obecné. Patří sem

1. správa pacientů - pacienti v aplikaci fungují jako profily, všechny záznamy se vážou k nějakému
   pacientovi,
2. správa kategorií problémů - de facto obálku, do které si uživatel může seskupit svá data a ve
   které
   vidí detail nějakého problému (zlomenná noha atd.) a
3. správa lékařských pracovníků - uživatel si může přidat kontakt na pracovníky, kteří se o něj
   starají.
   U pracovníka vybírá zařízení z Národního registru poskytovatelů zdravotních služeb.

## Technologie

Aplikace je vyvíjena v jazyce Kotlin a je stavěna dle systémové architektury Clean Architecture.
Pro konkrétní implementaci bylo čerpáno z knihy *Clean Architecture for Android: Implement
Expert-led
Design Patterns to Build Scalable, Maintainable, and Testable Android Apps (English Edition)*
(Eran Boundjnah, 2022, ISBN 9355510497). Požadovaná verze Androidu je 8.0 (API 26), verze Gradle je
8.0 a AGP 8.1.1. Verze Kotlinu je 1.8.0

Pro ukládání dat v aplikaci je využita
knihovna [Room](https://developer.android.com/training/data-storage/room),
pro komunikaci s API serverem je využita knihovna [Retrofit](https://square.github.io/retrofit/).
Pro převod fotografie na text (OCR) je využit
nástroj [Google ML Kit](https://developers.google.com/ml-kit).

## Instalace

Aplikaci lze stáhnout z [Releases](https://github.com/vvoleman/phr_android/releases/latest) v tomto
repozitáři. Pro instalaci je třeba povolit instalaci aplikací z neznámých zdrojů.

Pro vlastní sestavení aplikace je třeba mít nainstalovaný Android Studio a SDK pro Android 13 (API
level 33).

## Prvotní spuštění

Aplikace neobsahuje žádný "onboarding" proces, uživateli je k dispozici výchozí pacient s názvem "
Výchozí". Prvotní obrazovkou aplikace jsou "Lékařské zprávy".
