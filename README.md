# ControleAcces

Le projet simule un système de contrôle d'accès. On peut y trouver des lecteurs, des portes, des badges, des zones et
des plages horaires. Le but est de tester les différentes fonctionnalités du système et de s'assurer de leur bon
fonctionnement.  
On utilise la méthode EATDD (Executable Acceptance Test Driven Design) pour développer le projet. La première étape est
de faire un
test qui échoue. Ensuite, on développe le code pour que le test passe. Enfin, on refactorise le code.

## Installation

SDK 21 recommandé

```bash
git clone git@github.com:Mathias-LEROY-epsi/ControlAccess.git
cd ControleAcces
```

# Explication des fonctionnalités

## 1. Les Badges

On peut bloquer ou débloquer un badge. Un badge bloqué ne peut pas ouvrir les portes.
On peut affecter un badge à une zone. Un badge affecté à une zone peut ouvrir les portes de cette zone.
On peut définir un grade à un badge.Cela permet de définir le niveau de permissions au sein du bâtiment.

## 2. Les Portes

Les portes peuvent être bloquées ou non. Une porte bloquée ne peut pas être ouverte.
Certaines portes ont un accès restreint.
Cela veut dire que seul les personnes possèdant un badge avec le grade correspondant peuvent ouvrir la porte.

## 3. Les Lecteurs

Les lecteurs sont liés à des portes. Ils permettent de lire les badges et d'ouvrir les portes.
Ils vérifient le grade du badge, ainsi que son état (bloqué ou non).
Ils vérifient aussi si le badge peut ouvrir une porte dans une zone, en fonction de son niveau de grade.

## 4. Les Plages horaires

Les plages horaires permettent de définir des heures de début et de fin.
Durant ces heures, les portes s'ouvriront peut importe si on a un badge ou non (non visiteur).
En dehors de cette plage horaire, les portes ont besoin d'un badge pour s'ouvrir.

## 5. Les Jours de la semaine

On peut définir quels jours de la semaine sont bloqués. Par défaut, seul le week-end est bloqué.
Cela signifie que seul les admins et les techniciens peut ouvrir les portes pendant un jour bloqué.

## 6. La maintenance

On peut définir une plage horaire pour la maintenance. Durant cette plage horaire, les portes sont bloquées pour tout le
monde sauf les techniciens.

## 7. Les Grades

Les grades sont affectés aux badges et permettent de définir les droits d'accès dans le bâtiment.
Il y a quatre grades : utilisateur, technicien, admin, et visiteur.

### 7.1. Les utilisateurs

Ils ont accès aux portes non bloquées. Ils ont seulement accès aux zones qui leur sont affectées.
Ils ne peuvent pas accéder à une porte avec l'accès restreint pour les techniciens, ou aux administrateurs.
Ils ne peuvent pas accéder aux portes en maintenance.

### 7.2. Les techniciens

Quand on est en maintenance (23h à minuit), les techniciens ont accès à toutes les portes. Quand on est pas en
maintenance, ils n'ont pas accès à toutes les portes, seulement celles qui possèdent l'accès restreint pour les
techniciens. Ils ont accès à toutes les zones (même celles bloquées).

### 7.3. Les administrateurs

Ils ont accès à toutes les portes (même celles bloquées).
Ils peuvent accéder à toutes les zones (même celles bloquées).
Ils ne peuvent pas accéder à une porte avec l'accès restreint pour les techniciens.
Ils ne peuvent pas non plus accéder à une porte en maintenance.

### 7.4. Les visiteurs

Ils n'ont ni accès aux portes, ni aux zones. Ils peuvent seulement accéder à l'accueil.
Ils ne peuvent pas accéder aux portes durant la maintenance, ni en dehors des heures d'ouverture (plage horaire).

## 8. Les Zones

Les zones regroupent des portes. On peut bloquer ou débloquer une zone. Une zone bloquée ne peut pas être ouverte.