# ProjetJavaCite

## Description
Il s'agit d'un projet effectée lors le session 2 de science de données ( programmation avancée avec java ) permettant la gestion de réservations avec inscription et connexion d’utilisateurs.  
L’application vérifie les identifiants dans une base de données et redirige vers l’interface de réservation.

## Objectif / Contexte
L’objectif est de faciliter la gestion des réservations et le suivi des utilisateurs dans un contexte pratique.  
Ce projet permet de montrer comment créer une application Java avec interface graphique et interaction avec une base de données.

## Données
- Source : Base de données locale ou serveur MySQL  
- Taille : Variable selon les utilisateurs et réservations enregistrés  
- Variables principales :  
  - Utilisateur : `id`, `nom`, `email`, `mot_de_passe`  
  - Réservation : `id_reservation`, `utilisateur_id`, `date`, `heure`, `statut`  

## Méthodologie / Analyse
1. Création de l’interface utilisateur avec JavaFX et Scene Builder  
2. Connexion à la base de données pour gérer les inscriptions et authentifications  
3. Validation des données utilisateurs et gestion des erreurs  
4. Implémentation du module de réservation et suivi des actions  
5. Tests fonctionnels et vérification des flux utilisateur
6. Génération de fichier pdf

## Résultats clés
- Gestion complète des inscriptions et connexions sécurisées  
- Interface graphique intuitive pour la réservation  
- Stockage et récupération des données utilisateurs et réservations depuis la base de données  
- Application prête pour une utilisation locale ou déploiement simple

## Technologies utilisées
- Java JDK 24 (ou version utilisée)  
- JavaFX pour l’interface graphique  
- Maven pour la gestion du projet et des dépendances  
- Base de données MySQL   
- Scene Builder pour la création des interfaces

## Auteur
- Participants :
    - Tanoh Cédrick 2741679@collegelacite.ca
    - Sorel Aniel   2738286@collegelacite.ca
