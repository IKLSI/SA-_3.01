package metier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DB
{
	// Attribut de classe
	private static Connection connec;

	// Attribut requête Select
	private PreparedStatement psSelectIntervenants;
	private PreparedStatement psSelectIntervenant_final;
	private PreparedStatement psSelectIntervenant_complet;
	private PreparedStatement psSelect1Intervenant_final;
	private PreparedStatement psSelectCodInter;
	private PreparedStatement psSelectCategorieIntervenant;
	private PreparedStatement psSelectCategorieHeure;
	private PreparedStatement psSelectListeModule;
	private PreparedStatement psSelectListeSemestre;
	private PreparedStatement psSelectTypeModules;
	private PreparedStatement psSelectModuleParIntervenant;
	private PreparedStatement psSelectModulePar1Intervenant;
	private PreparedStatement psSelectPreviModuleRessource;
	private PreparedStatement psSelectAffectModuleRessource;
	private PreparedStatement psSelectModule;
	private PreparedStatement psSelectNomModule;
	private PreparedStatement psSelectNomSemestre;
	private PreparedStatement psSelectNomInter;
	private PreparedStatement psSelectAnnee;

	// Attribut requête Insert
	private PreparedStatement psInstertIntervenant;
	private PreparedStatement psInstertAffectation;
	private PreparedStatement psInsertModRessources;
	private PreparedStatement psInsertModSAE;
	private PreparedStatement psInsertModStage;
	private PreparedStatement psInsertModPPP;

	// Attribut requête Delete
	private PreparedStatement psDeleteInter;

	// Attribut requête Update
	private PreparedStatement psUpdateInter;
	private PreparedStatement psUpdateBool;
	private PreparedStatement psUpdateModRessources;
	private PreparedStatement psUpdateModSAE;
	private PreparedStatement psUpdateModStage;
	private PreparedStatement psUpdateModPPP;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	// Ici on gère la connexion et on prépare les requêtes
	public DB()
	{
		try
		{
			// Ouverture de la connexion à la data base
			DB.connec = DriverManager.getConnection("jdbc:postgresql://localhost/lk210125","lk210125","Kyliann.0Bado");

			// Préparation des Requêtes
			this.psSelectIntervenants          = DB.connec.prepareStatement("SELECT * FROM Intervenant");
			this.psSelectIntervenant_final     = DB.connec.prepareStatement("SELECT * FROM intervenant_final");
			this.psSelectIntervenant_complet   = DB.connec.prepareStatement("SELECT * FROM intervenant_complet");
			this.psSelect1Intervenant_final    = DB.connec.prepareStatement("SELECT * FROM intervenant_final WHERE codInter = ?");
			this.psSelectCategorieIntervenant  = DB.connec.prepareStatement("SELECT * FROM CategorieIntervenant");
			this.psSelectCodInter              = DB.connec.prepareStatement("SELECT codInter FROM Intervenant WHERE nom = ?");
			this.psSelectCategorieHeure        = DB.connec.prepareStatement("SELECT * FROM CategorieHeure");
			this.psSelectListeSemestre         = DB.connec.prepareStatement("SELECT nbGrpTD, nbGrpTP, nbEtd, nbSemaines FROM Semestre WHERE codSem = ?");
			this.psSelectListeModule           = DB.connec.prepareStatement("SELECT * FROM liste_module WHERE codSem = ?");
			this.psSelectTypeModules           = DB.connec.prepareStatement("SELECT * FROM TypeModule");
			this.psSelectModuleParIntervenant  = DB.connec.prepareStatement("SELECT * FROM affectation_final");
			this.psSelectModulePar1Intervenant = DB.connec.prepareStatement("SELECT * FROM affectation_final WHERE codInter = ?");
			this.psSelectModule                = DB.connec.prepareStatement("SELECT codmod,codsem,liblong,libcourt FROM module WHERE codMod = ?");
			this.psSelectPreviModuleRessource  = DB.connec.prepareStatement("SELECT * FROM module_final WHERE codMod = ?");
			this.psSelectAffectModuleRessource = DB.connec.prepareStatement("SELECT * FROM affectation_final WHERE codMod = ?;");
			this.psSelectNomModule             = DB.connec.prepareStatement("SELECT codMod, libCourt FROM Module");
			this.psSelectNomSemestre           = DB.connec.prepareStatement("SELECT codSem FROM SEMESTRE");
			this.psSelectNomInter              = DB.connec.prepareStatement("SELECT * FROM Intervenant WHERE nom = ? AND prenom = ?");
			this.psSelectAnnee                 = DB.connec.prepareStatement("SELECT annee FROM Annee");

			// Préparation des Insertions
			this.psInstertIntervenant  = DB.connec.prepareStatement("INSERT INTO Intervenant (nom, prenom, codCatInter, hServ, maxHeure)  VALUES(?,?,?,?,?)");
			this.psInstertAffectation  = DB.connec.prepareStatement("INSERT INTO Affectation (codMod, codInter, codCatHeure, commentaire) VALUES(?,?,?,?)");
			this.psInsertModRessources = DB.connec.prepareStatement("INSERT INTO Module (codMod, codSem, codTypMod, libLong, libCourt, valid, nbHPnCM, nbHPnTD, nbHPnTP, nbSemaineTD, nbSemaineTP, nbSemaineCM, nbHParSemaineTD, nbHParSemaineTP, nbHParSemaineCM, hPonctuelle) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			this.psInsertModSAE        = DB.connec.prepareStatement("INSERT INTO Module (codMod, codTypMod, codSem, libLong, libCourt, valid, nbHPnSaeParSemestre, nbHPnTutParSemestre, nbHSaeParSemestre, nbHTutParSemestre) VALUES(?,?,?,?,?,?,?,?,?,?)");
			this.psInsertModStage      = DB.connec.prepareStatement("INSERT INTO Module (codMod, codSem, codTypMod, libLong, libCourt, valid, nbHREH, nbHTut, nbHPnREH, nbHPnTut) VALUES(?,?,?,?,?,?,?,?,?,?)");
			this.psInsertModPPP        = DB.connec.prepareStatement("INSERT INTO Module (codMod, codSem, codTypMod, libLong, libCourt, valid, nbHPnCM, nbHPnTD, nbHPnTP, nbHParSemaineTD, nbHParSemaineTP, nbHParSemaineCM, hPonctuelle, nbHPnHTut) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			// Préparation des Updates
			this.psUpdateInter         = DB.connec.prepareStatement("UPDATE Intervenant SET nom = ?, prenom = ?, hServ = ?, maxHeure = ? WHERE codInter = ?;");
			this.psUpdateBool          = DB.connec.prepareStatement("UPDATE Module SET valid = ? WHERE codMod  = ?;");
			this.psUpdateModRessources = DB.connec.prepareStatement("UPDATE Module SET codMod = ?, libLong = ?, libCourt = ?, valid = ?, nbHPnCM = ?, nbHPnTD = ?, nbHPnTP = ?, nbSemaineTD = ?, nbSemaineTP = ?, nbSemaineCM = ?, nbHParSemaineTD = ?, nbHParSemaineTP = ?, nbHParSemaineCM = ?, hPonctuelle = ?  WHERE codMod = ?");
			this.psUpdateModSAE 	   = DB.connec.prepareStatement("UPDATE Module SET codMod = ?, libLong = ?, libCourt = ?, valid = ?, nbHPnSaeParSemestre = ?, nbHPnTutParSemestre = ?, nbHSaeParSemestre = ?, nbHTutParSemestre = ? WHERE codMod = ?");
			this.psUpdateModStage      = DB.connec.prepareStatement("UPDATE Module SET codMod = ?, libLong = ?, libCourt = ?, valid = ?, nbHPnREH = ?, nbHPnTut = ?, nbHREH = ?, nbHTut = ? WHERE codMod = ?");
			this.psUpdateModPPP        = DB.connec.prepareStatement("UPDATE Module SET codMod = ?, libLong = ?, libCourt = ?, valid = ?, nbHPnCM = ?, nbHPnTD = ?, nbHPnTP = ?, nbHParSemaineTD = ?, nbHParSemaineTP = ?, nbHParSemaineCM = ?, hPonctuelle = ?, nbHPnTut = ?, nbHTut = ?, nbHPnHTut = ? WHERE codMod = ?");
			
			// Preparation des Deletes
			this.psDeleteInter = DB.connec.prepareStatement("DELETE FROM Intervenant WHERE codInter = ?");
		}
		catch (SQLException e) { System.out.println("Problème de connexion à la base de donnée"); }
	}

	// Stoppe la connexion à la base de donnée
	public void couperConnection()
	{
		try
		{
			DB.connec.close();
		}
		catch (Exception e)	{ e.printStackTrace(); }
	}

	// Méthode requête à la base de donnée
	// Récupère tous les intervenants
	public ArrayList<Intervenant> getIntervenants()
	{
		ArrayList<Intervenant> lstInterv = new ArrayList<Intervenant>();

		try
		{
			ResultSet rs = this.psSelectIntervenants.executeQuery();
			while(rs.next())
			{
				int codInter    = rs.getInt("codInter");
				String nom      = rs.getString("nom");
				String prenom   = rs.getString("prenom");
				int codCatInter = rs.getInt("codCatInter");
				int hServ       = rs.getInt("hServ");
				int maxHeure    = rs.getInt("maxHeure");

				lstInterv.add(new Intervenant(nom, prenom, codCatInter, hServ, maxHeure));
			}
		}
		catch (Exception e) { e.printStackTrace(); }

		return lstInterv;
	}

	// Récupère le nom d'un module les categories d'heure
	public ArrayList<TypeModule> getNomCategorieModules()
	{
		ArrayList<TypeModule> lst = new ArrayList<TypeModule>();

		try
		{
			ResultSet rs = this.psSelectTypeModules .executeQuery();
			while(rs.next())
			{
				int codTypMod = rs.getInt("codTypMod");
				String nomTypMod = rs.getString("nomTypMod");
				lst.add(new TypeModule(codTypMod,nomTypMod));
			}
		}
		catch (Exception e) { e.printStackTrace(); }

		return lst;
	}

	// Récupère les intervenants finaux
	public ResultSet getIntervenant_final()
	{
		ResultSet resultSet = null;

		try { resultSet = this.psSelectIntervenant_final.executeQuery(); }
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	public ResultSet getIntervenant_final(int codInter)
	{
		ResultSet resultSet = null;

		try {
			this.psSelect1Intervenant_final.setInt(1,codInter);
			 resultSet = this.psSelect1Intervenant_final.executeQuery(); }
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	public ResultSet getModulParIntervenant(int codInter)
	{
		ResultSet resultSet = null;

		try { 
			this.psSelectModulePar1Intervenant.setInt(1, codInter);
			resultSet = this.psSelectModulePar1Intervenant.executeQuery(); }
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	// Récupère toutes les categories d'intervenants
	public ResultSet getCategorieInter()
	{
		ResultSet resultSet = null;

		try { resultSet = this.psSelectCategorieIntervenant.executeQuery(); }
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	public ResultSet getIntervenant_complet()
	{
		ResultSet resultSet = null;

		try { resultSet = this.psSelectCategorieIntervenant.executeQuery(); }
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	// Récupère toutes les categories d'heure
	public ArrayList<CategorieHeure> getCategorieHeure()
	{
		ArrayList<CategorieHeure> lst = new ArrayList<CategorieHeure>();

		try
		{
			ResultSet rs = this.psSelectCategorieHeure.executeQuery();
			
			while(rs.next())
			{
				int    codCatHeure = rs.getInt("codCatHeure");
				String nomCatHeure = rs.getString("nomCatHeure");
				int    coeffNum    = rs.getInt("coeffNum");
				int    coeffDen    = rs.getInt("coeffDen");

				lst.add(new CategorieHeure(codCatHeure,nomCatHeure,coeffNum,coeffDen));
			}
		}
		catch (Exception e) { e.printStackTrace(); }

		return lst;
	}

	public ArrayList<Semestre> getSemestre(String nomSem)
	{
		ArrayList<Semestre> lstSemestre = new ArrayList<Semestre>();

		try
		{
			this.psSelectListeSemestre.setString(1,nomSem);
			ResultSet rs = this.psSelectListeSemestre.executeQuery();

			while(rs.next())
			{
				int nbGrpTD = rs.getInt("nbGrpTD");
				int nbGrpTP = rs.getInt("nbGrpTP");
				int nbEtd = rs.getInt("nbEtd");
				int nbSemaine = rs.getInt("nbSemaines");

				lstSemestre.add(new Semestre(nomSem,nbGrpTD,nbGrpTP,nbEtd,nbSemaine));
			}
		}
		catch (Exception e) { e.printStackTrace(); }

		return lstSemestre;
	}

	
	public ArrayList<Modules> getListModule(String semestre)
	{
		ArrayList<Modules> lstModule = new ArrayList<Modules>();

		try
		{
			this.psSelectListeModule.setString(1,semestre);
			ResultSet rs = this.psSelectListeModule.executeQuery();

			while(rs.next())
			{
				String codSem = rs.getString("codSem");
				String codMod = rs.getString("codMod");
				String libLong = rs.getString("libLong");
				String hAP = rs.getString("hAP");
				boolean valid = rs.getBoolean("valid");
				
				lstModule.add(new Modules(codSem,codMod,libLong,hAP,valid));
			}
		}
		catch (Exception e){ e.printStackTrace(); }

		return lstModule;
	}

	public ResultSet getModule(String codMod)
	{
		ResultSet resultSet = null;

		try
		{
			this.psSelectModule.setString(1, codMod);
			resultSet = this.psSelectModule.executeQuery();
		}
		catch (Exception e) { e.printStackTrace(); }

		return resultSet;
	}

	public HashMap<String, String> getPreviModule(String codMod)
	{
		HashMap<String, String> lstVal = new HashMap<String, String>();
		try
		{
			this.psSelectPreviModuleRessource.setString(1, codMod);
			ResultSet rs = this.psSelectPreviModuleRessource.executeQuery();
			rs.next();
			
			for (int cpt = 1; cpt <= rs.getMetaData().getColumnCount(); cpt++) 
				lstVal.put(rs.getMetaData().getColumnName(cpt), null);
			
			do
			{
				for (String col : lstVal.keySet()) 
					lstVal.put(col,rs.getString(col));
			} while (rs.next());

			List<String> keysToRemove = new ArrayList<>();
			for (String col : lstVal.keySet())
				if (lstVal.get(col) == null) 
					keysToRemove.add(col);

			for (String key : keysToRemove) 
				lstVal.remove(key);

		} catch (Exception e) { e.printStackTrace(); }
		return lstVal;
	}

	public ArrayList<String> getNomModule()
	{
		ArrayList<String> lstModule = new ArrayList<String>();
		try
		{
			ResultSet rs = this.psSelectNomModule.executeQuery();
			while (rs.next()) 
				lstModule.add(rs.getString("codMod") + " " + rs.getString("libCourt"));
		} catch (SQLException e) {e.printStackTrace();}
		return lstModule;
	}

	public ArrayList<String> getNomSemestre()
	{
		ArrayList<String> lstSem = new ArrayList<String>();
		try
		{
			ResultSet rs = this.psSelectNomSemestre.executeQuery();
			while (rs.next()) 
				lstSem.add(rs.getString("codSem"));
		} catch (SQLException e) {e.printStackTrace();}
		return lstSem;
	}

	public boolean intervenantExist(String nomInter,String prenomInter)
	{
		try
		{
			this.psSelectNomInter.setString(1, nomInter);
			this.psSelectNomInter.setString(2, prenomInter);
			ResultSet rs = psSelectNomInter.executeQuery();
			if(rs.next()) return true;
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}

	// Récupère les affectations d'un module.
	public ResultSet getAffectation(String codMod)
	{
		ResultSet rs = null;
		try
		{
			this.psSelectAffectModuleRessource.setString(1, codMod);
			rs = this.psSelectAffectModuleRessource.executeQuery();
		}
		catch (Exception e) { e.printStackTrace(); }
		return rs;
	}
	

	// Récupère le code d'un intervenant
	public ArrayList<Integer> getCodInter(String nomInter)
	{
		ArrayList<Integer> lstCodInter = new ArrayList<Integer>();
		try
		{
			this.psSelectCodInter.setString(1, nomInter);
			ResultSet rs = this.psSelectCodInter.executeQuery();

			while (rs.next())
				lstCodInter.add(rs.getInt("codInter"));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return lstCodInter;
	}

	public ArrayList<String> getAnnee(){
		ArrayList<String> lstAnnee = new ArrayList<String>();

		try {
			ResultSet rs = psSelectAnnee.executeQuery();
			while (rs.next()) {
				lstAnnee.add(rs.getString("annee"));
			}
		} catch (SQLException e) {e.printStackTrace();}
		return lstAnnee;
	}

	// Méthode d'insertion
	public void insertIntervenant(Intervenant inter)
	{
		try
		{
			this.psInstertIntervenant.setString(1,inter.getNom());
			this.psInstertIntervenant.setString(2,inter.getPrenom());
			this.psInstertIntervenant.setInt(3,inter.getCodCatInter());
			this.psInstertIntervenant.setInt(4,inter.gethServ());
			this.psInstertIntervenant.setInt(5,inter.getMaxHeure());

			this.psInstertIntervenant.executeUpdate();
			this.psInstertIntervenant.close(); //jsp ?
		}
		catch (SQLException e) { e.printStackTrace(); }
	}

	public void insertAffectation(Affectation affec)
	{
		try
		{
			this.psInstertAffectation.setString(1,affec.getCodMod());
			this.psInstertAffectation.setInt(2,affec.getCodInter());
			this.psInstertAffectation.setInt(3,affec.getCodCatHeure());
			this.psInstertAffectation.setString(4,affec.getCommentaire());
			this.psInstertAffectation.executeUpdate();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}

	public void insertModRessources(String codMod, String codSem, Integer codTypMod, String libLong, String libCourt, boolean valid, Integer nbHPnCM, Integer nbHPnTD, Integer nbHPnTP, Integer nbSemaineTD, Integer nbSemaineTP, Integer nbSemaineCM, Integer nbHParSemaineTD, Integer nbHParSemaineTP, Integer nbHParSemaineCM, Integer hPonctuelle)
	{
		try
		{
			this.psInsertModRessources.setString(1,codMod);
			this.psInsertModRessources.setString(2,codSem);
			this.psInsertModRessources.setInt(3,codTypMod);
			this.psInsertModRessources.setString(4,libLong);
			this.psInsertModRessources.setString(5,libCourt);
			this.psInsertModRessources.setBoolean(6,valid);
			this.psInsertModRessources.setInt(7,nbHPnCM);
			this.psInsertModRessources.setInt(8,nbHPnTD);
			this.psInsertModRessources.setInt(9,nbHPnTP);
			this.psInsertModRessources.setInt(10,nbSemaineTD);
			this.psInsertModRessources.setInt(11,nbSemaineTP);
			this.psInsertModRessources.setInt(12,nbSemaineCM);
			this.psInsertModRessources.setInt(13,nbHParSemaineTD);
			this.psInsertModRessources.setInt(14,nbHParSemaineTP);
			this.psInsertModRessources.setInt(15,nbHParSemaineCM);
			this.psInsertModRessources.setInt(16,hPonctuelle);
			this.psInsertModRessources.executeUpdate();
		}catch (SQLException e) { e.printStackTrace(); }
	}

	public void insertModSAE(String codMod, String codTypMod, Integer codSem, String libLong, String libCourt, boolean valid, Integer nbHPnSaeParSemestre, Integer nbHPnTutParSemestre, Integer nbHSaeParSemestre, Integer nbHTutParSemestre)
	{
		try
		{
			this.psInsertModSAE.setString(1,codMod);
			this.psInsertModSAE.setString(2,codTypMod);
			this.psInsertModSAE.setInt(3,codSem);
			this.psInsertModSAE.setString(4,libLong);
			this.psInsertModSAE.setString(5,libCourt);
			this.psInsertModSAE.setBoolean(6,valid);
			this.psInsertModSAE.setInt(7,nbHPnSaeParSemestre);
			this.psInsertModSAE.setInt(8,nbHPnTutParSemestre);
			this.psInsertModSAE.setInt(9,nbHSaeParSemestre);
			this.psInsertModSAE.setInt(10,nbHTutParSemestre);
			this.psInsertModSAE.executeUpdate();
		}catch (SQLException e) { e.printStackTrace(); }
	}

	public void insertModStage(String codMod, String codSem, Integer codTypMod, String libLong, String libCourt, boolean valid, Integer nbHREH, Integer nbHTut, Integer nbHPnREH, Integer nbHPnTut)
	{
		try
		{
			this.psInsertModStage.setString(1,codMod);
			this.psInsertModStage.setString(2,codSem);
			this.psInsertModStage.setInt(3,codTypMod);
			this.psInsertModStage.setString(4,libLong);
			this.psInsertModStage.setString(5,libCourt);
			this.psInsertModStage.setBoolean(6,valid);
			this.psInsertModStage.setInt(7,nbHREH);
			this.psInsertModStage.setInt(8,nbHTut);
			this.psInsertModStage.setInt(9,nbHPnREH);
			this.psInsertModStage.setInt(10,nbHPnTut);
			this.psInsertModStage.executeUpdate();
		}catch (SQLException e) { e.printStackTrace(); }
	}

	public void insertModPPP(String codMod, String codSem, Integer codTypMod, String libLong, String libCourt, boolean valid, Integer nbHPnCM, Integer nbHPnTD, Integer nbHPnTP, Integer nbHParSemaineTD, Integer nbHParSemaineTP, Integer nbHParSemaineCM, Integer hPonctuelle, Integer nbHPnHTut)
	{
		try
		{
			this.psUpdateModPPP.setString(1,codMod);
			this.psUpdateModPPP.setString(2,codSem);
			this.psUpdateModPPP.setInt(3,codTypMod);
			this.psUpdateModPPP.setString(4,libLong);
			this.psUpdateModPPP.setString(5,libCourt);
			this.psUpdateModPPP.setBoolean(6,valid);
			this.psUpdateModPPP.setInt(7,nbHPnCM);
			this.psUpdateModPPP.setInt(8,nbHPnTD);
			this.psUpdateModPPP.setInt(9,nbHPnTP);
			this.psUpdateModPPP.setInt(10,nbHParSemaineTD);
			this.psUpdateModPPP.setInt(11,nbHParSemaineTP);
			this.psUpdateModPPP.setInt(12,nbHParSemaineCM);	
			this.psUpdateModPPP.setInt(13,hPonctuelle);
			this.psUpdateModPPP.setInt(14,nbHPnHTut);
			this.psUpdateModPPP.executeUpdate();
		}catch (SQLException e) { e.printStackTrace(); }
	}

	// Méthode de mise à jour
	public void updateInter(Intervenant nouveauInter)
	{
		try
		{
			this.psUpdateInter.setString(1,nouveauInter.getNom());
			this.psUpdateInter.setString(2,nouveauInter.getPrenom());
			this.psUpdateInter.setInt(3,nouveauInter.gethServ());
			this.psUpdateInter.setInt(4,nouveauInter.getMaxHeure());
			this.psUpdateInter.setInt(5, nouveauInter.getcodInter());
			this.psUpdateInter.executeUpdate();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void updateBool(boolean newVal, String codMod)
	{
		try  
		{ 
			this.psUpdateBool.setBoolean(1, newVal);
			this.psUpdateBool.setString(1, codMod);
			this.psUpdateBool.executeQuery();
		} catch (SQLException e) { e.printStackTrace(); }
	}

	public void updateSem(String textFieldId, String intitule, int newVal)
	{
		try 
		{
			String query = "UPDATE Semestre SET " + textFieldId + " = " + newVal + " WHERE codSem = '" + intitule + "'";
			Statement statement = DB.connec.createStatement();
			statement.executeUpdate(query);
			statement.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}

	public void updateMod(Modules nouveauModules, String nomTypMod, String codMod)
	{
		try
		{
			switch(nomTypMod)
			{
				case "Ressources":
					this.psUpdateModRessources.setString(1, nouveauModules.getCodMod());
					this.psUpdateModRessources.setString(2,nouveauModules.getLibLong());
					this.psUpdateModRessources.setString(3,nouveauModules.getLibCourt());
					this.psUpdateModRessources.setBoolean(4,nouveauModules.getValid());
					this.psUpdateModRessources.setInt(5,nouveauModules.getNbHPnCM());
					this.psUpdateModRessources.setInt(6,nouveauModules.getNbHPnTD());
					this.psUpdateModRessources.setInt(7,nouveauModules.getNbHPnTP());
					this.psUpdateModRessources.setInt(8,nouveauModules.getNbSemaineTD());
					this.psUpdateModRessources.setInt(9,nouveauModules.getNbSemaineTP());
					this.psUpdateModRessources.setInt(10,nouveauModules.getNbSemaineCM());
					this.psUpdateModRessources.setInt(11,nouveauModules.getNbHParSemaineTD());
					this.psUpdateModRessources.setInt(12,nouveauModules.getNbHParSemaineTP());
					this.psUpdateModRessources.setInt(13,nouveauModules.getNbHParSemaineCM());
					this.psUpdateModRessources.setInt(14,nouveauModules.getHPonctuelle());
					this.psUpdateModRessources.setString(15,codMod);
	             	this.psUpdateModRessources.executeUpdate();
					break;
	
	         case "SAE": 
					this.psUpdateModSAE.setString(1,nouveauModules.getCodMod());
					this.psUpdateModSAE.setString(2,nouveauModules.getLibLong());
					this.psUpdateModSAE.setString(3,nouveauModules.getLibCourt());
					this.psUpdateModSAE.setBoolean(4,nouveauModules.getValid());
					this.psUpdateModSAE.setInt(5,nouveauModules.getNbHPnSaeParSemestre());
					this.psUpdateModSAE.setInt(6,nouveauModules.getNbHPnTutParSemestre());
					this.psUpdateModSAE.setInt(7, nouveauModules.getNbHSaeParSemestre());
					this.psUpdateModSAE.setInt(8,nouveauModules.getNbHTutParSemestre());
					this.psUpdateModSAE.setString(9,codMod);
	             	this.psUpdateModSAE.executeUpdate();
					break;
	
	         case "Stage": 
					this.psUpdateModStage.setString(1,nouveauModules.getCodMod());
					this.psUpdateModStage.setString(2,nouveauModules.getLibLong());
					this.psUpdateModStage.setString(3,nouveauModules.getLibCourt());
					this.psUpdateModStage.setBoolean(4,nouveauModules.getValid());
					this.psUpdateModStage.setInt(5,nouveauModules.getNbHPnREH());
					this.psUpdateModStage.setInt(6,nouveauModules.getNbHPnTut());
					this.psUpdateModStage.setInt(7,nouveauModules.getNbHREH());
					this.psUpdateModStage.setInt(8,nouveauModules.getNbHTut());
					this.psUpdateModStage.setString(9,codMod);
	             	this.psUpdateModStage.executeUpdate();
					break;
	
	         case "PPP": 
					this.psUpdateModPPP.setString(1,nouveauModules.getCodMod());
					this.psUpdateModPPP.setString(2,nouveauModules.getLibLong());
					this.psUpdateModPPP.setString(3,nouveauModules.getLibCourt());
					this.psUpdateModPPP.setBoolean(4,nouveauModules.getValid());
					this.psUpdateModPPP.setInt(5,nouveauModules.getNbHPnCM());
					this.psUpdateModPPP.setInt(6,nouveauModules.getNbHPnTD());
					this.psUpdateModPPP.setInt(7,nouveauModules.getNbHPnTP());
					this.psUpdateModPPP.setInt(8,nouveauModules.getNbHParSemaineTD());
					this.psUpdateModPPP.setInt(9,nouveauModules.getNbHParSemaineTP());
					this.psUpdateModPPP.setInt(10,nouveauModules.getNbHParSemaineCM());
					this.psUpdateModPPP.setInt(11,nouveauModules.getHPonctuelle());
					this.psUpdateModPPP.setInt(12,nouveauModules.getNbHPnTut());
					this.psUpdateModPPP.setInt(13,nouveauModules.getNbHTut());	
					this.psUpdateModPPP.setInt(14,nouveauModules.getNbHPnHTut());
					this.psUpdateModPPP.setString(15,codMod);
	             	this.psUpdateModPPP.executeUpdate();
					break;
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
	}

	// Méthode de suppression
	public void supprInter(int codInter)
	{
		try
		{
			this.psDeleteInter.setInt(1,codInter);
			this.psDeleteInter.executeUpdate();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
}