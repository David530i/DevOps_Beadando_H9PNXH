# DevOps Beadando - Sandor David (H9PNXH)

Projekt neve: DevOpsBeadandoH9PNXH
Package: com.devops.h9pnxh
GitHub repo: https://github.com/David530i/DevOps_Beadando_H9PNXH.git


A projekt egy Java/Spring Boot alapu alkalmazas, ami HTTP-n
elerheto es egy "Hello World!" tartalmu uzenetet ir ki.

A projekt reszei:
- alkalmazas fejlesztes: Java es Spring Boot
- verziokovetes: Git
- build: Maven
- kontenerizalas: Docker
- shell script, amivel elindithato a kontener es 5mp kesleltetessel pedid az alkalmazas indul el automatikusan

# 1. Alkalmazas

Az alkalmazas egy HTTP endpointot biztosit:

-URL: 'http://localhost:8080'
-HTTP metodus: 'GET /'
Valasz: egy szoveg, amely tartalmazza a "Hello World!" uzenetet es a neptunkodot.

# 1.1 Fo komponensek

- HelloWorldApp.java fo alkalmazas file
- HelloWorldHTTP.java controller file
- Name.java kulon class-ba kiszervezett nev megadas metodus
A Name osztalyt a Spring @Component annotacioval kezeli, a controllert pedig konstruktoros dependency injection segitsegevel latja el a megfelelo peldannyal.

# 2 Build
# 2.1 Elofeltetelek
A projekt buildelesehez az alabbi eszkozok szuksegesek:
Java JDK 17 vagy ujabb
Maven 3.x
Git

# 2.2 Projekt klonozasa
git clone https://github.com/David530i/DevOps_Beadando_H9PNXH.git
cd DevOpsBeadandoH9PNXH

# 2.3 Build parancs
A projekt gyokereben:
    mvn clean package

Ez a parancs leforditja a forraskodot, lefuttatja az esetleges teszteket es elkesziti a futtathato JAR file-t a target mappaban.
A build outputja: target/hello-devops-1.0.0.jar

# 2.4 Futtatas JAR-bol
java -jar target/hello-devops-1.0.0.jar

Sikeres inditas utan az alkalmazas a kovetkezo cimen erheto el:
http://localhost:8080/

# 3 Git hasznalata - trunk-based development
# 3.1 Branch struktura
main - a fo ag. Mindig futtathato, stabil allapotot tartalmaz.
feature/update-hello-message - feature ag, amelyen az alkalmazas fejleszetese es refaktoralasa tortent
A trunk-based szemlelet alapjan a main ag a mindig futtathato "trunk". Az uj funkciok/atalakitasok rovid eletu feature brancheken keszulnek.
A valtoztatasok merge-ot kovetoen atkerulnek a main branch-re.


# 3.2 Workflow
1. Main branch-re atteres:
git checkout main

2. Feature branch letrehozasa:
git checkout -b feature/update-hello-message

3. Valtoztatasok elvegzese, esetunkben:
    uzenet modositasa
    nev megadasanak kiszervezese kulon osztalyba
    controller refaktoralasa a dependency injection  hasznalatahoz

4. Commit keszitese
    git status - (file-ok listazasa amiben a valtoztatasok tortentek)
    git add . - az osszes valtoztas hozzaadasa
    git commit -m "<informalis commit uzenet, leirva hogy mit tartalmaz a valtoztatas>"

5. Visszalepes a main branch-re es merge
    git checkout main
    git merge feature/update-hello-message
vagy ha szeretnenk uzenetet irni a merge-hez>
    git merge --no-ff -m "<uzenet>" feature/update-hello-message
6. Push a remote reop-ba
    git push origin main
    git push origin feature/update-hello-message

# 4 Docker
Az alkalmazas Docker kontenerben is futtathato. A projekt gyokereben talalhato egy Dockerfile.

# 4.1 Dockerfile tartalma
1. fázis: build - Maven + JDK
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

2. fázis: futtatás - csak a jar egy runtime image-ben
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/hello-devops-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

Az elso fazisban Maven segítségével elkeszul a JAR.
A masodik fazis egy konnyebb JRE image-ben csak a JAR-t tartalmazza.
A konteneren belul az alkalmazas a 8080-as porton fut.

# 4.2 Docker image buildelese
docker build -t devops-beadando-h9pnxh .
itt: devops-beadando-h9pnxh a Docker image neve

# 4.3 Kontener futtatasa
docker run -p 8080:8080 devops-beadando-h9pnxh
itt: <host port>:<konteneren beluli port>
igy az alkalmazas a host geprol a http://localhost:8080 cimen erheto el

# 5 Shell script az automatikus inditashoz
A script elinditja a Docker kontenert a hatterben majd nehany masodperc multan automatikusan megnyitja az alapertelmezett bongeszot a megfelelo URL-el.

# 5.1 Script letrehozasa
touch start_container_with_browser.sh
chmod +x start_container_with_browser.sh

Tartalma:
# Konténer indítása háttérben
docker run -d -p 8080:8080 --name devops-beadando-h9pnxh-container devops-beadando-h9pnxh
sleep 5
open "http://localhost:8080/"

# 5.2 Shell script futtatasa
./start_container_with_browser.sh

# 5.3 Kontener leallitasa
docker ps                          # futó konténerek listája
docker stop devops-beadando-h9pnxh-container
docker rm devops-beadando-h9pnxh-container

# 6 PowerShell script Windows alatt
A script ugyanarra hivatott mint a fentebbi tarsa csak Windowsos kornyezetben.

# 6.1 Script letrehozasa
A projekt gyokereben letre kell hozni egz file-t:
start_container_with_browser.ps1

Tartalma:
docker run -d -p 8080:8080 --name devops-beadando-h9pnxh-container devops-beadando-h9pnxh
Start-Sleep -Seconds 5
Start-Process "http://localhost:8080/"

# 6.2 Futtatas
PowerShell ablakban  a projekt mappajaban:
.\start_container_with_browser.ps1

# 6.3 Kontener leallitasa Windows alatt
docker stop devops-beadando-h9pnxh-container
docker rm devops-beadando-h9pnxh-container
