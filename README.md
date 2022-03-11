# rentmanager
 [4A-MIN] Pascal Monin Rentmanager

 Exerices effectués:

 La DAO Et le service fonctionnent.
J'ai réalisé les fonctions suivantes 
    - findAll
    - findById
    - create
    - update
    - delete
    - count

Pour les réservations, en plus des fonctions précédentes, j'ai réalisé les fonctions suivantes :
    - findByClientId
    - findByVehiculeId

J'ai écrit les servlets de manière à ce que, pour les clients, les véhicules et les reservations, on puisse :
    - Afficher la liste
    - Ajouter un élément
    - Supprimer un élément
    - Modifier un élément
Je n'ai pas implémenté les pages "détails" car je n'ai pas compris à quoi elles devaient servir. Pour la liste des réservations, je n'ai pas réussi afficher les noms des clients plutôt que leur "id".

Je n'ai pas compris comment réaliser les tests de la séance 3.

J'ai implémenté la contrainte de l'âge minimum de 18 ans pour la création et à la mise à jour des utilisateurs.

J'ai rencontré un problème au début du cours lorsqu'on est passé sur le site en host local via MVN : le style ne fonctionnait pas sur les pages du fait d'un problème d'arborescence. J'ai déplacé le dossier "ressources" qui contient tout le style des pages dans le dossier "webapp" pour résoudre le problème. Néanmoins, il a été un obstacle important dans mon travail.

Pour le dépot git de mon travail, je n'ai pas compris quels fichier devaient être ignorés via le .gitignore, donc j'ai tout laissé dans le doute.