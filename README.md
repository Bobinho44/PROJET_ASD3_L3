L3 Info, Math-Info, CMI Algorithmique et structures de donnees 3 2021-
F.S.T. de Nantes

## Projet Cameléon

```
Programmation en JAVA 1.7 et en binome (obligatoirement)
```
# 1 Objectifs

Le but du projet est d’implémenter le jeu décrit ci-dessous, entre un humain ́ J1 et l’ordinateur J2.
L’humain a la couleur rouge et l’ordinateur a la couleur bleu. Le gagnant est le joueur qui, lorsque le
plateau est rempli, a colorié ́e le plus de cases avec sa couleur.

# 2 Description du jeu

Le jeu se déroule sur un plateau carré qui fait 3 × 2<sup>n</sup> cases de coté (c’est-à-dire 3 × 2<sup>n</sup> lignes et 3 × 2<sup>n</sup>
colonnes), que l’on imagine découpé successivement en régions carrées de plus en plus petites, comme
pour construire un quadtree :

- Le plateau est lui-même une région carrée de 3 × 2<sup>n</sup> cases de coté.
- Il est découpé en quatre régions carrées, chacune de ́ 3 × 2<sup>n-1</sup> cases de coté.
- Et ainsi de suite, récursivement, chaque région carrée de 3 × 2<sup>k</sup> de coté, avec k≥ 1 , est découpée
en quatre régions carrées de 3 × 2<sup>k-1</sup> cases de coté.
- Le découpage s’arrête aux régions carrées de côté 3.
Le terme de région  utilisé par la suite fait référence exclusivement aux régions carrées a 3 × 2<sup>k</sup> cases
de coté obtenues par ce découpage, avec k= 0, 1 , 2 , ... , n. Une région est petite si elle a 3 cases de coté
et grande dans les autres cas.
Les colonnes du plateau sont numérotées de 1 à 3 × 2<sup>n</sup> de gauche à droite, et les lignes sont
numérotées de 1 à 3 × 2<sup>n</sup> de haut en bas. Une case est identifiée par ses coordonnées  (i, j), ou i est
le numéro de sa ligne et j le numéro de sa colonne. Elle appartient à exactement une petite région. Le
voisinage d’une case est l’ensemble d’au plus 8 cases qui ont soit un coté soit un coin en commun avec 
cette case.
Nous considérons que le plateau initial est soit vide, soit partiellement colorié de sorte qu’il y ait
autant de cases rouges que de cases bleues. Les autres cases sont supposées blanches. Si une région
entière, qu'elle que soit sa taille, est coloriée de la même couleur, elle est dite acquise(par le joueur qui
possède cette couleur). Toutes ses cases sont alors dites acquises. A noter qu’il s’agit (quand-même)
d’une acquisition temporaire.

Le joueur J1 commence le jeu. Ensuite, tour à tour, chaque joueur J choisit une case blanche (i, j),
ce qui déclenche le changement de couleur de plusieurs cases. Les règles de recoloriage définissent la
version du jeu, Caméléon Brave ou Caméléon Téméraire.

Règles pour le Caméléon Brave:

```
R 1 .La case (i, j) prend la couleur du joueur J.
R 2 .Toute case qui est voisine de la case (i, j) et qui est déjà coloriée prend la couleur du joueur J.
```

Règles pour le Caméléon Téméraire:

```
R 1 .La case (i, j) devient de la couleur du joueur J.
R′ 2 .(attention, changement) Toute case qui est voisine de la case (i, j) et qui est déjà coloriee mais 
n’est pas acquise devient de la couleur du joueur J.
R 3 .Si la case (i, j) éait la dernière case non-coloriée d’une petite région, alors la petite région est
acquise par le joueur J et toutes ses cases deviennent de la couleur du joueur J.
R 4 − 5 .Supposons qu’une région ́ P<sub>k</sub> de 3 × 2<sup>k</sup> cases de cotˆe, avec ́ k≥ 0 , du plateau vient d’etreˆ
acquise par un joueur X, quel qu’il soit (J ou son adversaire). SoitPk+1l’unique region de ́
3 × 2 k+1cases de cˆote qui contient ́ Pk. Alors :
R 4 .siPk+1est totalement colori ́ee, et le joueur X a acquis au moins deux des quatre sous-r ́egions
de taille 3 × 2 kdePk+1, alors toute la region ́ Pk+1est acquise par le joueur X et toutes ses
cases deviennent de la couleur du joueur X.
R 5 .SiPk+1est totalement coloriee, et le joueur X n’a acquis qu’une des quatre sous-r ́ ́egions de
taille 3 × 2 kdePk+1, alors toute la r ́egionPk+1est acquise par l’adversaire de X et toutes
ses cases deviennent de la couleur de l’adversaire de X.
```
Observation.Notez ici que si l’une des regles` R 4 ouR 5 a ́et ́e appliquee pour une r ́ egion ́ Pk, alorsPk+
est acquise par un joueur et donc il faut verifier ́ `a nouveau, mais cette fois-ci pourPk+1, si les r`egles
R 4 ouR 5 s’appliquent. Et cela recursivement tant qu’une nouvelle r ́ ́egion plus grande est acquise par un
joueur.

Exemple.Soit le plateau de gauche ci-dessous.
Versions Brave.Si le joueur J1 (rouge) choisit la case (2,5), les r`eglesR 1 etR 2 s’appliquent et le
plateau devient celui du milieu (le•indique la case choisie). Si,`a la place, il choisit la case (6,8), la case
(6,8) et les trois cases bleues situees en dessous, qui sont ses voisines, deviennent rouges (non montr ́ e ́
sur le dessin).
Version Tem ́ eraire. ́ Si, sur le plateau du milieu, le joueur J2 (bleu) joue la case (6,12) alors la case
(6,12) devient bleue mais aucun autre changement n’est effectue, puisque les deux cases rouges situ ́ ees ́
en dessous, qui sont ses voisines, sont acquises par J1 (non montre sur le dessin). Par contre si, sur le ́
plateau du milieu, le joueur J2 joue la case (11,8) alors le plateau devient celui de droite. La r`egleR 3
s’applique d’abord et le joueur J2 r ́ecupere la petite r` ́egiona l’intersection des lignes 10` a 12 avec les`
colonnes 7a 9. Ensuite, la r` egle` R 4 s’applique et le joueur J2 acquiert la region plus grande, celle de ́
3 × 2 cases de cotˆe, puisqu’il d ́ etient 3 sous-r ́ ́egions de cette region. Les images ne le montrent pas, mais ́
si les trois autres r ́egions de 3 × 2 cases de cotˆe du plateau ́ ́etaient rouges, alors tout le plateau deviendrait
rouge par la regle` R 5!

-
    -


# 3 Travail demande ́

```
En bref, le programme que vous allezecrire doit permettre de : ́
```
- r ́ecuperer le plateau initial selon les formalit ́ ́es definies plus bas. ́
- repr ́esenter le plateaua l’` ecran (une forme basique suffit amplement). ́
- reporter sur le plateau chaque coup jou ́e par l’humain ou l’ordinateur, et le coloriage qui s’en
    suit, selon les deux variantes BraveetTem ́ ́eraire.
- implementer plusieurs strat ́ ́egies de jeu pour l’ordinateur, en suivant les consignes ci-dessous.
Les d ́etails sont fournis ci-dessous.
Sur tous les aspects du jeu, on visera d’aborda`minimiser la complexite en temps, au pire ́ , mais
l’espace m ́emoire devraegalement ́ ˆetre utilise avec parcimonie. Il faudra suivre les consignes suivantes : ́

```
C1.Pour la complexite en temps, vous choisirez (sauf lorsque c’est impos ́ e) les structures de donn ́ ees ́
le plus efficaces, afin que pour chaque tache de l’algorithme la complexitˆ ́e minimum soit garantie.
Les structures de donn ́ees peuventetre de plusieurs types, adaptˆ es ́ `a vos besoins, pourvu que le
but d’efficacite soit atteint. ́
C2.Pour stocker le plateau et gerer une partie des informations le concernant, vous pouvez utiliser ́
un tableau. Par contre, le parcours du tableau sans raison precise et bien fond ́ ee est ́ `a proscrire. Si
la tache souhaitˆ ́ee peutetre accomplie en ne parcourant qu’un sous-tableau bien ciblˆ ́e et/ou une
structure de donnees plus efficace stockant les informations utiles ́ `a la realisation de cette t ́ ˆache,
vous devez le faire.
```
Dans chacune des variantes du jeu, il faut implementer la strat ́ ́egie gloutonne suivante, pour le joueur
J2 (l’ordinateur).

Strat ́egie gloutonne.Par d ́efinition, une strategie gloutonne est une strat ́ egie qui vise le gain imm ́ ediat ́
maximum, sans visiona long terme. Pour notre jeu, cette strat` egie se d ́ ́efinit comme suit : lorsque son
tour arrive, le joueur J2 choisit la case blanche qui lui permet, une fois que les regles du jeu ont` ́ete ́
appliquees et que son tour est fini, d’avoir un score maximum, o ́ `u le score est defini par : ́

```
score(J2) = nombre de cases bleues - nombre de cases rouges.
Vous devez :
```
- g ́erer les cases, et le score que J2 peut atteindre s’il choisit chaque case blanche,a l’aide de`
    structures de donnees de sorte que la complexit ́ e ́au pirede chaque coup jou ́e par J2 (incluant le
    choix de la case`a jouer, la misea jour du plateau et la mise` `a jour des structures de donnees) soit ́
    aussi petite que possible.
- pour la version Brave, choisir les structures de donnees adapt ́ ́ees.
- pour la version Tem ́ eraire, en plus des structures pour la variante Brave, utiliser un quadtree pour ́
    g ́erer les regions acquises ou non, et ́ ́evaluer le score atteignable par chaque case blanche. Dans
    ce quadtree, les feuilles de l’arbre seront les regions acquises les plus grandes (non-incluses dans ́
    d’autres r ́egions acquises) et les petites r ́egions qui ne sont pas acquises. Ces dernieres pourront`
    contenir des informations sur les cases blanches`a l’int ́erieur de la region concern ́ ́ee.

Deuxi`eme strategie. ́ Pour la version Tem ́ ́eraire, vous proposerez une deuxi`eme strat ́egie pour J2,
aussi intelligente que possible, et vous l’implementerez suivant les m ́ ˆemes consignes C1 et C2.


Vous devezegalement proposer un menu, demandant ́ `a l’utilisateur ce qu’il veut faire (le programme
appelera ensuite les methodes n ́ ecessaires pour r ́ ́epondrea sa demande) :`

- s’il veut demarrer par un plateau vide, ou d ́ ́ej`a partiellement rempli. Dans le deuxieme cas, le`
    plateau sera fourni dans un fichier texte, dont le nom ne doit pasetre imposˆ e, de la forme suivante : ́
    la premi`ere ligne contient le nombre de cases par cotˆe. Les lignes suivantes contiennent les lignes ́
    du plateau, ou chaque case est repr` ́esentee par R/B/A selon qu’elle est rouge, bleue ou blanche. ́
    Par exemple, pour le plateau fourni sur la figure prec ́ ́edente,`a gauche, le fichier contiendra :
    12
    ABRABBAABAAA
    ARRRABAAARAA
    ABAAABABBAAA
    BAARAAAARRBA
    ABBBAAAARRBA
    AARAAAAAABAA
    AAARRRBBBRRR
    AAARARBBBRRR
    AAARRRBBBRRR
    ARRRAABRRBBB
    AAABAARARBBB
    AAABABRRRBBB
- s’il veut afficher le plateau actuel (vous pouvez oublier cette option si vous affichez le plateau en
    permanence).
- s’il veut jouer, et avec quelle strat ́egie pour l’ordinateur.
- s’il veut afficher le score de chaque joueur ; l’utilisateur doit pouvoir connaˆıtre le score`a tout
    moment du jeu, pas seulementa la fin.`
- s’il veut connaˆıtre l’evaluation d’une case blanche, telle que calcul ́ ́ee par J2 pour son propre
    usage^2 ; l’utilisateur doit pouvoir faire cette demandea tout moment du jeu, pas seulement` a la`
    fin.
Enfin, votre programme doit contenir, parmi d’autres fonctions que vous impl ́ementerez, les fonctions
suivantes (dont les noms doivent se retrouver tel quels dans votre programme, de sorte qu’un lecteur de
votre code puisse aller directementa la fonction qui l’int` eresse) : ́

RemplirPlateau qui saisit le plateau fourni en entree. ́
JouerGloutonBrave, JouerGloutonTemeraire qui implementent la strat ́ egie gloutonne pour chacune ́
des variantes du jeu.
JouerIATemeraire qui impl ́emente votre strat ́egie intelligente.
EvalCaseBrave, EvalCaseTemeraire quievaluent le score que J2 peut atteindre, selon la version, ́
s’il choisit la case passee en param ́ etre.`
RemplirRegionqui modifie le quadtree selon les regles indiqu` ́ees (R 3 , R 4 , R 5 ) lorsque la derniere`
case d’une petite r ́egion est remplie.
CalculeScore qui calcule le score du plateau actuel.
Vous choisirez ́egalement des noms clairs pour vos structures de donnees : ́ Quadtreeet/ouABRet/ou
AVLetc.

Le programme sera lance en ligne de commande et pourra ́ etre utilisˆ e ind ́ ́ependemment d’Eclipse ou
autre IDE.

2. c’est promis, on ne trichera pas, on veut juste savoir!


# 4 Rendu de TP

Les programmes doiventetre implˆ ́ementes par vous-m ́ emes en Java 1.7 et doivent fonctionner par-ˆ
faitement sur les machines du CIE, sous Linux. L’appela des structures de donn` ees optimis ́ ees existantes ́
(du genre TreeSet) est interdit : vous devez impl ́ementer vous-mˆemes vos structures de donnees. Seuls les ́
tableaux et les listes chaˆın ́ees peuventˆetre utilises tels que fournis par Java. Aucun appel ́ `a une methode ́
mise`a disposition dans les bibliotheques Java (par exemple un tri d’une liste) n’est autoris` ́e si vous ne
connaissez pas le fonctionnement de la methode et sa complexit ́ ́e (ce que vous montrerez dans les calculs
de complexite demand ́ es ci-dessous). ́
Un rapport d’au maximum 12 pages sera fourni, incluant :

- les commandes de compilation et execution en mode console ́
- la description du jeu, les difficultes rencontre ́ ́es, les choix effectues (y compris ceux li ́ es aux ́
    structures de donnees) ́
- une vue globale de votre programme, sous la forme d’un algorithme (c’est-a-dire en pseudo-code)`
    dont les instructions sont des appelsa des proc` ́edures/fonctions ; la specification et les param ́ `etres
    de chaque procedure/fonction seront pr ́ ecis ́ ́ement d ́ecrits.
- pour chaque m ́ethode parmi celles de la liste demand ́ee :
    * le detail de la proc ́ edure/fonction, sous forme algorithmique (c’est- ́ `a-dire en pseudo-code) ;
    * l’explication de son fonctionnement ;
    * sa complexit ́e
- la complexite au pire d’un coup jou ́ e par J2, dans chacune des versions ́
- la complexit ́e globale de l’algorithme permettant de jouer jusqu’`a ce que le plateau soit rempli,
    dans chacune des versions, en supposant que chaque coup de J1 est fait enO(1).
- des jeux de donn ́ees commentes, mettant en lumi ́ ere des cas o` u J1 ou J2 gagne, en fonction des`
    strat ́egies utilis ́ees.

Important

- Les fichiers du programme, ainsi que les jeux de donnees, seront mis dans un r ́ ́epertoire appele ́
    Nom1Nom2 (ou Nom1 et Nom2 sont les noms des deux` etudiants du bin ́ ˆome). Ce repertoire sera ́
    ensuite archive sous le nom Nom1Nom2. ́ zip. L’archive sera depos ́ ee sur Madoc, au plus tard le ́
    samedi 4/12/2021a 23h55` (Madoc n’acceptera pas de retard).
- La derniere s` ́eance est consacr ́eea une d` emo de 10 minutes par projet, pour laquelle des plateaux ́
    vous seront fournis en entr ́ee. Vous devez donc imperativement suivre les consignes concernant ́
    le format du fichier en entree. Cette derni ́ ere s` eance ́ n’est donc pas une seance de travail sur le ́
    projet.
- Toutest important pour la notation. En particulier, il sera accord ́e beaucoup d’attention au respect
    des consignes eta la recherche d’une complexit` ́e minimum - garantie d’une efficacite maximum ́
    - pour votre m ́ethode, via la mise en place des structures de donnees les plus adapt ́ ees. ́


