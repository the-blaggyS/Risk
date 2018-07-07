import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import java.awt.Color;

/**
 * Initializes the game, creates players, countries and dice
 *
 * @author Lucas
 * @version 01.06.2018
 */
public class Init
{
    Player[] players;
    Country[] countries;
    Dice dice;
    
    public Init()
    {
        String[] names = {"Player1", "Player2"};
        players = createPlayers(names);    // (getPlayerNames());
        countries = createCountries(players);
        dice = createDice();
    }
    
    private String[] getPlayerNames()
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Player Names (Comma Seperated): ");
        String[] playerNames = input.nextLine().split(", ");
        return playerNames;
    }
    
    private Player[] createPlayers(String[] playerNames)
    {
        Player[] players = new Player[playerNames.length];
        Color[] colors = {Color.red, Color.black};
        for (int i = 0; i < playerNames.length; i++)
        {
            players[i] = new Player(playerNames[i], colors[i]);
        }
        return players;
    }
    
    private Country[] createCountries(Player[] players)
    {
        String[][] countryList = 
        {
        //  Country Name                Troops  X       Y       Continent           Neighbors
            {"Afghanistan",             "2",    "657", "243",   "Asia",             "China", "India", "Middle East", "Ukraine", "Ural"},
            {"Alaska",                  "2",     "62", "136",   "North America",    "Alberta", "Kamchatka", "North West Territory"},
            {"Alberta",                 "1",    "166", "202",   "North America",    "Alaska", "North West Territory", "Ontario", "Western United States"},
            {"Argentinia",              "1",    "306", "453",   "South America",    "Brazil", "Peru"},
            {"Brazil",                  "2",    "358", "412",   "South America",    "Argentinia", "North Africa", "Peru", "Venezuela"},
            {"Congo",                   "2",    "550", "373",   "Africa",           "North Africa", "East Africa", "South Africa"},
            {"Central America",         "2",    "229", "307",   "North America",    "Eastern United States", "Western United States", "Venezuela"},
            {"China",                   "2",    "747", "263",   "Asia",             "Afghanistan", "India", "Mongolia", "Siam", "Siberia", "Ural"},
            {"East Africa",             "1",    "584", "332",   "Africa",           "Congo", "Egypt", "Madagascar", "Middle East", "North Africa", "South Africa"},
            {"Eastern Australia",       "1",    "900", "445",   "Australia",        "New Guinea", "Western Australia"},
            {"Eastern United States",   "1",    "256", "251",   "North America",    "Central America", "Ontario", "Quebec", "Western United States"},
            {"Egypt",                   "2",    "549", "282",   "Africa",           "East Africa", "Middle East", "North Africa", "Ukraine"},
            {"Great Britain",           "1",    "472", "195",   "Europe",           "Iceland", "Northern Europe", "Scandinavia", "Western Europe"},
            {"Greenland",               "1",    "372", "106",   "North America",    "Iceland", "North West Territory", "Ontario", "Quebec"},
            {"Iceland",                 "1",    "475", "149",   "Europe",           "Great Britain", "Greenland", "Scandinavia"},
            {"India",                   "1",    "695", "300",   "Asia",             "Afghanistan", "China", "Middle East", "Siam"},
            {"Indonesia",               "1",    "786", "393",   "Australia",        "New Guinea", "Siam", "Western Australia"},
            {"Irkutsk",                 "1",    "790", "198",   "Asia",             "Kamchatka", "Mongolia", "Siberia", "Yakutsk"},
            {"Japan",                   "2",    "880", "275",   "Asia",             "Kamchatka", "Mongolia"},
            {"Kamchatka",               "2",    "920", "164",   "Asia",             "Alaska", "Irkutsk", "Japan", "Mongolia", "Yakutsk"},
            {"Madagascar",              "1",    "623", "418",   "Africa",           "Congo", "East Africa", "South Africa"},
            {"Middle East",             "2",    "609", "280",   "Asia",             "Afghanistan", "East Africa", "Egypt", "India", "Southern Europe", "Ukraine"},
            {"Mongolia",                "1",    "795", "235",   "Asia",             "China", "Irkutsk", "Japan", "Kamchatka", "Siberia"},
            {"New Guinea",              "1",    "878", "377",   "Australia",        "Eastern Australia", "Indonesia", "Western Australia"},
            {"North Africa",            "2",    "490", "314",   "Africa",           "Brazil", "Congo", "East Africa", "Egypt", "Southern Europe", "Western Europe"},
            {"Northern Europe",         "1",    "533", "210",   "Europe",           "Great Britain", "Scandinavia", "Southern Europe", "Ukraine", "Western Europe"},
            {"North West Territory",    "1",    "163", "152",   "North America",    "Alaska", "Alberta", "Greenland", "Ontario"},
            {"Ontario",                 "1",    "232", "199",   "North America",    "Alberta", "Eastern United States", "Greenland", "Quebec", "Western United States"},
            {"Peru",                    "1",    "298", "408",   "South America",    "Argentinia", "Brazil", "Venezuela"},
            {"Quebec",                  "1",    "302", "204",   "North America",    "Eastern United States", "Greenland", "Ontario"},
            {"Scandinavia",             "1",    "533", "155",   "Europe",           "Great Britain", "Iceland", "Northern Europe", "Ukraine"},
            {"Siam",                    "2",    "755", "319",   "Asia",             "China", "India", "Indonesia"},
            {"Siberia",                 "1",    "746", "138",   "Asia",             "China", "Irkutsk", "Mongolia", "Ural", "Yakutsk"},
            {"South Africa",            "1",    "550", "431",   "Africa",           "Congo", "East Africa", "Madagascar"},
            {"Southern Europe",         "1",    "551", "242",   "Europe",           "Egypt", "Middle East", "Northern Europe", "North Africa", "Ukraine", "Western Europe"},
            {"Ukraine",                 "1",    "605", "181",   "Europe",           "Afghanistan", "Middle East", "Northern Europe", "Scandinavia", "Southern Europe", "Ural"},
            {"Ural",                    "2",    "693", "189",   "Asia",             "Afghanistan", "China", "Siberia", "Ukraine"},
            {"Venezuela",               "2",    "297", "360",   "South America",     "Brazil", "Central America", "Peru"},
            {"Western Australia",       "1",    "836", "461",   "Australia",        "Indonesia", "Eastern Australia", "New Guinea"},
            {"Western Europe",          "1",    "489", "239",   "Europe",           "Great Britain", "Northern Europe", "North Africa", "Southern Europe"},
            {"Western United States",   "1",    "191", "253",   "North America",    "Alberta", "Central America", "Eastern United States", "Ontario"},
            {"Yakutsk",                 "1",    "839", "139",   "Asia",             "Irkutsk", "Kamchatka", "Siberia"},
        };
        Country[] countries = new Country[countryList.length];
        
        // Shuffle countryList
        for (int i = 0; i < countryList.length; i++)
        {
            int rndm = ThreadLocalRandom.current().nextInt(0, countryList.length);
            String[] tmp = countryList[i];
            countryList[i] = countryList[rndm];
            countryList[rndm] = tmp;
        }
        
        // Create Countries
        for (int i = 0; i < countryList.length; i++)
        {
            countries[i] = new Country(countryList[i][0], players[i%players.length], Integer.parseInt(countryList[i][1]), Integer.parseInt(countryList[i][2]), Integer.parseInt(countryList[i][3]), countryList[i][4]);
        }
        
        // Set Neighboring Countries
        // For Each Country String
        for (int i = 0; i < countryList.length; i++)
        {
            if (countryList[i].length < 5) {break;}
            
            // Create Buffer
            Country[] bufNeighboringCountries = new Country[10];
            
            // For Each Neighboring String
            for (int j = 5; j < countryList[i].length; j++)
            {
                // For Each Country Object
                boolean fail = true;
                for (int k = 0; k < countries.length; k++)
                {
                    // If Country String Equals Country Object getName(), Then Adds Country Object To bufNeighborungCountries
                    if (countryList[i][j].equals(countries[k].getName()))
                    {
                        bufNeighboringCountries[j-5] = countries[k];
                        fail = false;
                        break;
                    }
                }
                // Fail Safe Agains missspelled Countries
                if (fail)
                {
                    System.out.println("Neighbor " + countryList[i][j] + " of " + countryList[i] + " not found!");
                }
            }
            
            // Count Countries In Buffer
            int c = 0;
            for (int j = 0; j < bufNeighboringCountries.length; j++)
            {
                if (bufNeighboringCountries[j] != null)
                {
                    c++;
                }
            }
            
            // Write Countries From Buffer To neighboringCountries
            Country[] neighboringCountries = new Country[c];
            c = 0;
            for (int j = 0; j < bufNeighboringCountries.length; j++)
            {
                if (bufNeighboringCountries[j] != null)
                {
                    neighboringCountries[c] = bufNeighboringCountries[j];
                    c++;
                }
            }
            
            // Call Set-Method
            countries[i].setNeighboringCountries(neighboringCountries);
        }
        
        // Sort countries (Insertion-Sort)
        for (int i = 0; i < countries.length; i++)
        {
            for (int j = i; j > 0; j--)
            {
                if (countries[j].getName().compareTo(countries[j-1].getName()) < 0)
                {
                    Country tmp = countries[j];
                    countries[j] = countries[j-1];
                    countries[j-1] = tmp;
                }
            }
        }
        
        return countries;
    }
    
    private Dice createDice()
    {
        Dice dice = new Dice();
        return dice;
    }
    
}
