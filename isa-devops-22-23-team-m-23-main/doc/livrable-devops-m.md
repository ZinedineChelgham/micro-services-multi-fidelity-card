# Processus DevOps

## Les outils de notre processus DevOps

Dans le cadre de notre processus DevOps, nous avons mis en place deux outils essentiels : Jenkins et Artifactory.

-   Jenkins est un outil qui facilite le processus de développement logiciel en automatisant les tâches de construction, de test et de déploiement d'applications. Cela permet aux développeurs de déployer rapidement les modifications de code dans des environnements de test et de production, et d'intégrer facilement Jenkins avec d'autres outils de développement.

-   Artifactory est un outil de gestion de dépôts d'artefacts qui permet de stocker et de gérer les différentes versions des artefacts générés pendant le processus de développement.


En utilisant Jenkins et Artifactory ensemble, on a pu travailler de manière plus efficace et automatisée. Jenkins gère l'intégration continue (CI), tandis qu'Artifactory gère la gestion des artefacts (CD), nous permettant de collaborer plus efficacement.



De plus, nous avons également publié les artefacts générés par Artifactory sur un hub Docker, ce qui nous a permis de distribuer facilement les packages de notre application et de les utiliser avec d'autres outils de conteneurisation. Cette intégration de Docker a rendu le processus de déploiement de notre application plus simple et plus rapide, tout en garantissant une cohérence entre les environnements de développement, de test et de production.

## Notre pipeline jenkins

[Jenkinsfile](../Jenkinsfile)

![](https://lh3.googleusercontent.com/xTvqRb3Y4FMFnD5xIQcFAJXPfHfV7No_EEVkrk6yA2s87PiA_gF4Qk23atbbFOJvBUEv3W7i2TzUze-z5xImdTS0O7Yv_xzfHbHYyIPp6a-1PVP_8PhE4t45kBpCBh7ydDqOpotjIvhEbcCxhy_yU6k)

CI / CD

-   **(CI/CD)** Init permet de vérifier à la fois la version des outils requis (JDK 19 et Maven 3.6.9 pour utiliser HTTP sur Artifactory).

-   **(CD)** Init Docker permet de vérifier que Docker est correctement installé et de se connecter pour prévoir la publication sur le hub Docker.

-   **(CI)** Check backend permet de compiler le backend et de vérifier que tous les tests passent.

-   **(CI)** Check cli permet de compiler le CLI.

-   **(CD)** Deploy snapshot permet de déployer une version snapshot sur Artifactory en utilisant la commande "deploy" de Maven.

-   **(CD)** Deploy release permet de déployer une version stable sur Artifactory en utilisant la commande "deploy" de Maven.

-   **(CD)** Download Artifacts permet de télécharger la dernière version stable sur Artifactory à l'aide d'un script.

-   **(CD)** Create Docker Images permet d'exécuter tous les Dockerfiles, de créer les images grâce aux jars téléchargés dans l'étape précédente et de les publier sur le hub Docker.


### Axes d’amélioration

-   Utiliser une nomenclature claire pour les tâches pour améliorer la lisibilité et la maintenabilité du code.


-   Adapter les tests en fonction du nom de la branche pour optimiser le temps de test : si la branche a pour nom "feature*", tester seulement le backend ; si la branche a pour nom "dev*", tester le backend et le cli; si la branche a pour nom "cli*", tester seulement le cli. De la même manière, exécuter uniquement les tests unitaires et/ou d’intégration dépendamment du nom de la branche.


-   Effectuer un déploiement des applications "parking" et "bank".

-   Envisager la création d'un agent de déploiement pour fonctionner sous Docker plutôt que d'utiliser les outils intégrés à Jenkins.

-   Construire un docker compose pour réaliser des tests end to end avec nos images montées.

- Mettre en place des outils supplémentaires comme Sonarqube pour assurer la robustesse, qualité de notre code avec différents métriques.


## Les images Docker

Notre projet comporte 4 Dockerfile, chacun d'entre eux étant utilisé pour construire une image Docker contenant une partie spécifique de l'application. Nous avons veillé à ce que ces Dockerfile soient conçus de manière à ce que les applications puissent fonctionner correctement lorsqu'elles sont exécutées dans des conteneurs.



Nous avons opté pour des Dockerfile relativement simples pour la plupart de nos applications, mais le dockerfile pour l'application "Bank" a été copié en grande partie de celui fourni par "SimpleTCFS". Bien que ce dockerfile soit plus complexe que les autres, nous avons pris le temps de bien le comprendre et de nous assurer qu'il fonctionne correctement.

* [backend/Dockerfile](../cli/Dockerfile)
* [cli/Dockerfile](../cli/Dockerfile)
* [bank/Dockerfile](../bank/Dockerfile)
* [parking/Dockerfile](../parking/Dockerfile)

## La conteneurisation

Le docker-compose est un élément clé de notre processus de DevOps, car il nous permet de garantir la disponibilité et la fiabilité de nos outils, tout en facilitant leur déploiement et leur maintenance.

### Conteneurisation des outils du processus DevOps

Dans le cadre de notre processus de DevOps, il est important de pouvoir déployer rapidement nos outils en cas de crash ou de besoin de les redéployer sur un autre environnement. Pour répondre à ce besoin, nous avons mis en place un [docker-compose](../devops/docker-compose.yaml).


Nous avons installé Jenkins, Artifactory et Postgres. Postgres est ici, utilisé comme base de données par Artifactory. Ces outils sont essentiels à notre processus de développement et de déploiement. Dans le cas d’une réinstallation, il nous suffira de simplement remettre les tools de Jenkins et tout refonctionne (et aussi reconnecter le repo git).

L'interface web de Jenkins est accessible sur le port 8001 de la VM, et celle d'Artifactory sur le port 8004.

#### Credentials Jenkins :
- admin:f60c6c1ace134f609c8b152cf75b8f67

#### Credentials Artifactory :
- admin:LAr5gRezYmANSMq$mM6tY@4#QzHH4Et!bJs6dXKf

De plus, nous avons prévu un [script](../devops/smee-script.sh) présent sur la VM permettant de déployer l'outil smee exploitant les webhooks de github à travers l'utilisation d'un screen.


### Conteneurisation de notre application


[Voici le docker compose permettant de déployer notre application.](../docker-compose.yaml) Les images sont basées sur les dernières versions publiées sur le docker hub. Une meilleure possibilité aurait été d'avoir un véritable système de versioning afin d'avoir d'autant plus la mainmise sur le bon fonctionnement et la compatibilité de notre application.


## Utilisation de Artifactory

Artifactory a été utilisé dans le cadre de notre processus DevOps pour stocker et gérer les différentes versions des artefacts générés pendant le développement logiciel. Nous avons limité l'utilisation de Artifactory aux jar du backend et du cli afin d'optimiser l'utilisation de l'outil et de ne pas surcharger notre espace de stockage. Nous avons envisagé d'ajouter un zip contenant la banque et le parking, mais nous avons constaté qu'ils ne sont pas des exécutables et ne peuvent donc pas être déployés. Nous avons donc jugé que l'ajout de ces fichiers dans Artifactory n'aurait pas été pertinent et aurait surchargé inutilement l'espace de stockage.
