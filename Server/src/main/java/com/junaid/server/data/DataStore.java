package com.junaid.server.data;

// @author junaidxyz

import com.junaid.server.repository.DAO;
import com.junaid.shared_library.country.*;
import com.junaid.shared_library.election.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;


public class DataStore {
    public class Voters {
        public static ArrayList<Voter> voters = null;
        
        public static void LoadVoters(){
            try {
                voters = DAO.getTotalVoters();
            }
            catch (SQLException ex) {
                System.getLogger(DataStore.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        
        public static ArrayList<Voter> getAllVoters(){
            if (voters == null){
                LoadVoters();
            }
            
            return voters;
        }
        
        public static ArrayList<Voter> searchVoters (String keyword) {
            if (voters == null){
                LoadVoters();
            }
            
            ArrayList<Voter> results = new ArrayList<>();
            for (Voter v: voters){
                if (v.toString().toUpperCase().contains(keyword.toUpperCase())){
                    results.add(v);
                }
            }
            
            return results;
        }
    }
    
    
    public class Parties {
        private static final ConcurrentHashMap<String, Party> partyMap = new ConcurrentHashMap<>();

        public static void addParty(Party party) {
            partyMap.put(party.getCode(), party);
        }

        public static Party getParty(String code) {
            return partyMap.get(code);
        }

        public static Collection<Party> getAllParties() {
            return partyMap.values();
        }
    
    }
    
    
    public class Provinces {
        private static ArrayList<Province> provinces = null;

        public static void LoadProvinces(){
            ArrayList<Province> allProvinces = null;

            try {
                allProvinces = DAO.loadAllHierarchy();
            }
            catch (SQLException ex) {
                System.out.println("error loading provinces: " + ex.getMessage());
            }

            if (allProvinces != null){
                provinces = allProvinces;
            }
        }

        public static ArrayList<Province> getAllProvinces(){
            LoadProvinces();
            return provinces;
        }

        // returns province and city object
        public static Object[] getDivisionInfo(int divID){
            if (provinces == null){
                LoadProvinces();
            }

            for (Province p: provinces){
                for (City c: p.getCities()){
                    for (Division d: c.getDivisions()){
                        if (d.getId() == divID){
                            return new Object[] {
                                new Province(p.getCode(),p.getName()),
                                new City(c.getName())
                            };
                        }
                    }
                }
            }

            return null;
        }
    }
}
