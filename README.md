# TP du FIC Testing

_Edition 2023 - Avec Quarkus et Angular_

## 1. Présentation

### 1.1. Contexte

L'application qui va être utilisée pour se former à la réalisation de tests automatisés, est une implémentation très simplifiée d'un site web marchand, une marketplace électronique.
Vous allez vous focaliser sur la fonctionnalité d'ajout / retrait de produits dans un panier d'achat avec calcul du prix total du panier.

### 1.2. Processus

![Processus métier](doc/processus_metier.png)

L'application se compose de 2 écrans pour l'instant:

- le premier écran permet de lister les produits disponibles sur le site marchand, d'en consulter les informations et de les rajouter au panier. L'ajout au panier se fait via une icône bleue "Ajout au panier" localisée dans la dernière colonne à droite du tableau. Dès qu'au moins un produit est ajouté au panier, alors un encart Panier apparaît en haut à droite et permet d'obtenir le coût total du panier ainsi que le nombre d'articles ajouté. En cliquant sur cet encart panier, vous pouvez accéder à votre panier d'achat.

![Ecran 1](doc/1_liste_articles.png)

- le deuxième écran est le panier d'achat. Il permet de visualiser la liste des articles ajoutés au panier, d'ajouter ou supprimer des articles ainsi que d'ajouter un éventuel code de réduction. Vous pouvez utiliser par exemple l'un de ces codes de réduction: DIXPOURCENT (-10%), ONVIDETOUT (-50%). Veuillez noter que la fonctionnalité de finalisation de l'achat n'est pas incluse dans le périmètre de l'exercice.

![Ecran 1](doc/2_panier.PNG)

### 1.3. Architecture

L'architecture de l'application est celle d'une application web moderne classique en 3 tiers. Elle se compose d'un front-end Angular qui est une Single Page Application chargée dans le navigateur de l'utilisateur. Cette application web Angular accès à un back-end Quarkus. Quarkus est un framework Java permettant d'implémenter des services web très performants au temps de démarrage très rapide. Enfin la base de données est implémentée grâce à H2 qui est une base de données chargée uniquement en mémoire au sein de la Java Virtual Machine du back-end.

![Architecture](doc/architecture.png)

### 1.4. Entités mise en oeuvre

Ce projet met en oeuvre 4 entités métier:

- l'entité **Product** (produit) qui matérialise un produit achetable
- l'entité **Discount Code** (code de réduction) avec le champ discount qui est le pourcentage de réduction applicable au panier
- l'entité **Basket** (panier d'achats) qui matérialise un panier d'achat et contient le champ "totalPrice" qui est calculé à partir des produits ajoutés au panier
- l'entité **ProductInBasket** (produit dans le panier) qui contient la liste des produits ajoutés au panier et leur quantité

## 2. Préparation

### 2.1. Démarrage de votre GitPod

GitPod est un environnement de développement en ligne qui permet d'obtenir un environnement de développement à la volée implémenté grâce à Docker et un IDE Visual Studio Code en ligne. C'est l'environnement que nous utiliserons pour ce TP.

Pour démarrer votre GitPod, vous pouvez cliquer sur le lien ci-dessous:
https://gitpod.io/#/https://github.com/juliencognet/FicTesting2023Quarkus

Il vous faudra créer un compte sur Github pour utiliser GitPod. Vous disposerez de 50h d'utilisation gratuite par mois.

Une fois le conteneur créé, laissez GitPod initialiser l'environnement.

### 2.2. Lancement de l'application

Dans l'onglet Terminal, créez un nouveau terminal de type bash, puis lancez ./mvnw

Quand l'application aura démarrée, vous verrez dans le terminal

` 2023-02-21 13:47:37,043 INFO [io.quarkus] (Quarkus Main Thread) fic-tests-2023 1.0.0-SNAPSHOT on JVM (powered by Quarkus 1.12.1.Final) started in 5.415s. Listening on: http://localhost:8080`

Dans un nouveau terminal, tapez :
`gp url 8080` pour récupérer l'url à appeler pour accéder à votre application. Elle devrait se présenter sous la forme https://8080-juliencogne-fictesting2-2rs0l0ld3ak.ws-eu87.gitpod.io/

L'application démarre en mode liveReload. C'est-à-dire que tous les changements apportés sur le back-end java sont répercutés quasi immédiatement sur l'application.

### 2.3. Lancement des tests unitaires

A gauche de votre IDE, la dernière icone se présente sous la forme d'un bécher de laboratoire. C'est la section testing. Elle vous permet d'exécuter tout ou partie des tests de l'application. Les tests de l'application sont dans `src/test/java`.

## 3. Exercices

### 3.1. Tests exploratoires

Naviguez dans l'application, essayez toutes les fonctionnalités et procédez à des tests exploratoires.

> Quelles anomalies avez-vous détectée (2 ont été identifiées par les formateurs) ?
>
>    <details>
>    <summary>Solution</summary>
>    En cas d'ajout du même produit dans le panier, un doublon se crée au lieu d'incrémenter la quantité de cette référence.
>    Le calcul du panier ne prend pas en compte les bons de réduction qui sont ajoutés sur le panier.
>    </details>

## Remarques pour les éventuels mainteneurs du TP

This application was generated using JHipster 6.10.5 and JHipster Quarkus 1.1.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.10.5](https://www.jhipster.tech/documentation-archive/v6.10.5).

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

    npm install

We use npm scripts and [Webpack][] as our build system.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    npm start

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

### PWA Support

JHipster ships with PWA (Progressive Web App) support, and it's turned off by default. One of the main components of a PWA is a service worker.

The service worker initialization code is commented out by default. To enable it, uncomment the following code in `src/main/webapp/index.html`:

```html
<script>
  if ('serviceWorker' in navigator) {
    navigator.serviceWorker.register('./service-worker.js').then(function () {
      console.log('Service Worker Registered');
    });
  }
</script>
```

Note: [Workbox](https://developers.google.com/web/tools/workbox/) powers JHipster's service worker. It dynamically generates the `service-worker.js` file.

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

    npm install --save --save-exact leaflet

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

    npm install --save-dev --save-exact @types/leaflet

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Edit [src/main/webapp/app/vendor.ts](src/main/webapp/app/vendor.ts) file:

```
import 'leaflet/dist/leaflet.js';
```

Edit [src/main/webapp/content/scss/vendor.scss](src/main/webapp/content/scss/vendor.scss) file:

```
@import '~leaflet/dist/leaflet.css';
```

Note: There are still a few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Using Angular CLI

You can also use [Angular CLI][] to generate some custom client code.

For example, the following command:

    ng generate component my-component

will generate few files:

    create src/main/webapp/app/my-component/my-component.component.html
    create src/main/webapp/app/my-component/my-component.component.ts
    update src/main/webapp/app/app.module.ts

## Building for production

### Packaging as thin jar

To build the final jar and optimize the FIC_Tests_2023 application for production, run:

```
./mvnw -Pprod clean package
```

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/quarkus-app/*.jar

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

### Packaging as native executable

_Targeting your Operation System_
In order to build a native image locally, your need to have [GraalVM](https://www.graalvm.org/) installed and `GRAALVM_HOME` defined.
You can use the `native` profile as follow to build native executable.

```
./mvnw package -Pnative
```

Keep in mind that the generated native executable is dependent on your Operating System.

_Targeting a container environment_
If you plan to run your application in a container, run:

```
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

It will use a Docker container with GraalVM installed and produce an 64 bit Linux executable.

## Testing

To launch your application's tests, run:

    ./mvnw verify

### Client tests

Unit tests are run by [Jest][] and written with [Jasmine][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

    npm test

For more information, refer to the [Running tests page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

    docker-compose -f src/main/docker/postgresql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/postgresql.yml down

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 6.10.5 archive]: https://www.jhipster.tech/documentation-archive/v6.10.5
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v6.10.5/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v6.10.5/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v6.10.5/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v6.10.5/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v6.10.5/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v6.10.5/setting-up-ci/
[node.js]: https://nodejs.org/
[yarn]: https://yarnpkg.org/
[webpack]: https://webpack.github.io/
[angular cli]: https://cli.angular.io/
[browsersync]: https://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[jasmine]: https://jasmine.github.io/2.0/introduction.html
[protractor]: https://angular.github.io/protractor/
[leaflet]: https://leafletjs.com/
[definitelytyped]: https://definitelytyped.org/
