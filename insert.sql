-- Insertion de données dans la table CategorieIntervenant
INSERT INTO CategorieIntervenant (nomCat, service, maxHeure, ratioTPCatInterNum, ratioTPCatInterDen)
VALUES ('info_ec' , 192, 364, 1,1),
	   ('vaca_pro', 120, 187, 2,3),
	   ('vac_sd'  , 90 , 187, 1,1),
	   ('vaca_ret', 80 , 96 , 2,3),
	   ('info_sd' , 384, 576, 1,1);

-- Insertion de données dans la table TypeModule
INSERT INTO TypeModule (nomTypMod)
VALUES ('Ressources'),
	   ('SAE'       ),
	   ('Stage'     );

-- Insertion de données dans la table CategorieHeure
INSERT INTO CategorieHeure (nomCatHeure, coeffNum, coeffDen)
VALUES  ('CM'   , 3,2),
		('TD'   , 1,1),
		('TP'   , 1,1),
		('H tut', 1,1),
		('REH'  , 1,1),
		('H saé', 1,1),
		('HP'   , 1,1);



-- Insertion de données dans la table Semestre
INSERT INTO Semestre (codSem, nbGrpTD, nbGrpTP, nbEtd, nbSemaines)
VALUES ('S1', 3, 6, 85, 15),
	   ('S2', 3, 6, 65, 16),
	   ('S3', 2, 4, 48, 15),
	   ('S4', 2, 4, 48, 16),
	   ('S5', 2, 4, 42, 15),
	   ('S6', 2, 4, 42, 16);



-- Insertion de données dans la table Intervenants
INSERT INTO Intervenant (nom, prenom, codCatInter, hServ, maxHeure)
VALUES ('Boukachour', 'Hadhoum'  , 1, 192, 364),
	   ('Colignon'  , 'Thomas'   , 2, 120, 187),
	   ('Dubocage'  , 'Tiphaine' , 2, 120, 187),
	   ('Hervé'     , 'Nathalie' , 3,  90, 187),
	   ('Pecqueret' , 'Véronique', 4,  80,  96),
	   ('Laffeach'  , 'Quentin'  , 5, 384, 576),
	   ('Le Pivert' , 'Philippe' , 5, 384, 576),
	   ('Legrix'    , 'Bruno'    , 5, 384, 576),
	   ('Nivet'     , 'Laurence' , 5, 384, 576);



-- Insertion de données dans la table Module ressource
INSERT INTO Module (codMod, codSem, codTypMod, libLong, libCourt, valid, nbHParSemaineTD, nbHParSemaineTP, nbHParSemaineHTut,nbHParSemaineCM)
VALUES ('R1.01', 'S1' , 1, 'Initiation au développement'           , 'Init Dev' , false,4,2,1,6),
	   ('R1.02', 'S1' , 1, 'Développement interfaces Web'          , 'Dev Web'  , false,4,2,1,0),
	   ('R2.01', 'S2' , 1, 'Développement orienté objets'          , 'Dev Objet', false,4,2,1,0),
	   ('R2.02', 'S2' , 1, 'Développement d''applications avec IHM', 'Dev IHM'  , false,2,2,0,0);
	 
-- Insertion de données dans la table Module SAÉ
INSERT INTO Module (codSem, codTypMod, codMod, libLong, libCourt, valid, nbHPnSaeParSemestre, nbHPnTutParSemestre)
VALUES ('S1' , 2, 'S1.1', 'Implémentation d''un besoin client'    , 'SAE-01'   , false,6 ,0 ),
	   ('S2' , 2, 'S2.2', 'Développement d''une application'      , 'SAE-02'   , false,8 ,0 ),
	   ('S3' , 2, 'S3.1', 'Développement d''une application'      , 'Dev appli', false,40,38);


-- Insertion de données dans la table Module stage
INSERT INTO Module (codSem, codTypMod, codMod, libLong, libCourt, valid, nbHREH, nbHTut)
VALUES ('S4' , 3, 'S4.ST', 'Stage'                                 , 'Stage'    ,false ,10,2);



-- Insertion de données dans la table Affectation
INSERT INTO Affectation (codMod, codInter, codCatHeure, commentaire, nbSem, nbGrp)
VALUES('R1.01',7,1,'3 cm d''1h30.', 6, 1),
	  ('R1.01',7,2,           NULL, 14,2);

-- Insertion de données dans la table Affectation
INSERT INTO Affectation (codMod, codInter, codCatHeure, commentaire, nbH)
VALUES('S3.1',1,6,            NULL,   10);

/*
Catégorie Nom Prénom hServ hMax Coef TP S1 S3 S5 sTot S2 S4 S6 sTot Total
info_ec Boukachour Hadhoum 192 364 1 106.5 18.0 0.0 124.5 0.0 0.0 0.0 0.0 124.5
vaca_pro Colignon Thomas 120 187 2/3 40.3 0.0 0.0 40.3 0.0 0.0 0.0 0.0 40.3
vaca_pro Dubocage Tiphaine 120 187 2/3 40.3 0.0 0.0 40.3 0.0 0.0 0.0 0.0 40.3
vac_sd Hervé Nathalie 90 187 2/3 0.0 37.3 0.0 37.3 0.0 0.0 0.0 0.0 37.3
vaca_ret Pecqueret Véronique 80 96 2/3 0.0 37.3 0.0 37.3 0.0 0.0 0.0 0.037.3
info_sd Laffeach Quentin 384 576 1 155.0 12.0 0.0 167.0 0.0 3.0 0.0 3.0 170.0
info_sd LePivert Philippe 384 576 1 188.0 18.0 0.0 206.0 0.0 0.0 0.0 0.0 206.0
info_sd Legrix Bruno 384 576 1 118.0 18.0 0.0 136.0 0.0 0.0 0.0 0.0 136.0
info_sd Nivet Laurence 384 576 1 75.0 12.0 0.0 87.0 0.0 0.0 0.0 0.0 87.0
*/

/*Faire la somme en equivalent TD de toute les affectation des intervenants en fonction de chaque semestre*/
SELECT i.*
FROM Intervenant i JOIN Affectation a    ON i.codInter    = a.codInter
				   JOIN Module      m    ON a.codMod      = m.codMod
				   JOIN Semestre    s    ON m.codSem      = s.codSem
				   JOIN CategorieHeure c ON a.codCatHeure = c.codCatHeure
;

/*Tot Semestres*/
SELECT i.nom,i.prenom,c.nomCatHeure,m.codMod,
		CASE
			WHEN m.codTypMod = 1 THEN COALESCE(a.nbSem,1)*COALESCE(a.nbGrp,1)*(c.coeffNum/c.coeffDen)
			ELSE COALESCE(nbH,1)*(c.coeffNum/c.coeffDen)
		END AS "tot eqtd"
FROM Affectation a JOIN CategorieHeure c ON a.codCatHeure = c.codCatHeure
				   JOIN Module      m    ON a.codMod      = m.codMod
				   JOIN Intervenant i    ON i.codInter    = a.codInter;

/*sTot impaire*/

/*sTot paire*/

/*Total*/
