#language: fr
Fonctionnalité: Enregistrement d'un utilisateur

  Scénario: Enregistrement réussi
    Étant donné que l'utilisateur fournit son nom, adresse email et informations de paiement valides
    Quand il demande à créer un compte
    Alors un compte est créé avec succès
    Et l'utilisateur est connecté à son compte

  Scénario: Enregistrement échoué - adresse email déjà utilisée
    Étant donné que l'utilisateur fournit une adresse email qui a déjà été utilisée pour créer un compte
    Quand il demande à créer un compte
    Alors un message d'erreur est affiché indiquant que l'adresse email est déjà utilisée
    Et le compte n'est pas créé

  Scénario: Enregistrement échoué - informations de paiement non valides
    Étant donné que l'utilisateur fournit des informations de paiement non valides
    Quand il demande à créer un compte
    Alors un message d'erreur est affiché indiquant que les informations de paiement sont incorrectes
    Et le compte n'est pas créé