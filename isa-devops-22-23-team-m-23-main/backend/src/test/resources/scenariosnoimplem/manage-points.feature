#language: fr
Fonctionnalité: Gérer les points

  Scénario: Consultation du solde de points
    Étant donné que l'utilisateur est connecté à son compte
    Quand il demande à consulter son solde de points
    Alors le solde de points de l'utilisateur s'affiche

  Scénario: Utilisation des points pour débloquer des avantages
    Étant donné que l'utilisateur est connecté à son compte
    Et il a suffisamment de points pour débloquer un avantage
    Quand il demande à utiliser ses points pour débloquer un avantage
    Alors les points sont déduits du solde de l'utilisateur
    Et l'avantage est débloqué

  Scénario: Échec de l'utilisation des points - solde insuffisant
    Étant donné que l'utilisateur est connecté à son compte
    Et il n'a pas suffisamment de points pour débloquer un avantage
    Quand il demande à utiliser ses points pour débloquer un avantage
    Alors un message d'erreur est affiché indiquant que le solde de points est insuffisant
    Et l'avantage n'est pas débloqué

  Scénario: Échec de l'utilisation des points - non connecté
    Étant donné que l'utilisateur n'est pas connecté à son compte
    Quand il demande à utiliser ses points pour débloquer un avantage
    Alors un message d'erreur est affiché indiquant que l'utilisateur doit être connecté pour utiliser ses points
    Et l'avantage n'est pas débloqué