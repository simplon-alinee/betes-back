USE BETES;

DROP TABLE IF EXISTS `SCORE`;
CREATE TABLE `SCORE` (
`ID_SCORE` BIGINT (20) NOT NULL AUTO_INCREMENT
, `SCORE` INTEGER (10) NOT NULL DEFAULT 0
, `ID_UTILISATEUR` BIGINT (20) NOT NULL
, `DATE_UPDATE` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
, PRIMARY KEY (`ID_SCORE`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;