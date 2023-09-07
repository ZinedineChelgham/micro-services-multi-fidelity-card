#language: fr
Fonctionnalité: Afficher les avantages

  Scénario: Affichage des avantages commerçants partenaires
    Étant donné que l'utilisateur est connecté à son compte
    Quand il demande à afficher les avantages commerçants partenaires
    Alors la liste des avantages commerçants partenaires disponibles s'affiche

  Scénario: Affichage des avantages institutionnels VFP
    Étant donné que l'utilisateur est connecté à son compte
    Et il est un client VFP
    Quand il demande à afficher les avantages institutionnels
    Alors la liste des avantages institutionnels VFP disponibles s'affiche

  Scénario: Affichage échoué - non connecté
    Étant donné que l'utilisateur n'est pas connecté à son compte
    Quand il demande à afficher les avantages
    Alors un message d'erreur est affiché indiquant que l'utilisateur doit être connecté pour afficher les avantages

  Scénario: Affichage échoué - non client VFP
    Étant donné que l'utilisateur est connecté à son compte
    Et il n'est pas un client VFP
    Quand il demande à afficher les avantages institutionnels
    Alors un message d'erreur est affiché indiquant que l'utilisateur doit être un client VFP pour afficher les avantages institutionnels