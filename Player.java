import java.util.Scanner;

import java.awt.Color;

/**
 * Manages attributes of players and calculates new troops
 */
public class Player
{
    private String name;
    private int newTroops;
    private Color color;
    
    public Player(String pName, Color pColor)
    {
        name = pName;
        color = pColor;
    }
    
    /**
     * Calculates how many troops the player gets
     * 
     * Arguments:
     * Country[] countries -- an array of all country objects
     */
    public void setNewTroops(Country[] countries)
    {
        // Count My Countries
        int c = 0;
        for (int i = 0; i < countries.length; i++)
        {
            if (countries[i].getOwner().getName().equals(name))
            {
                c++;
            }
        }
        
        // Create myCountries
        Country[] myCountries = new Country[c];
        c = 0;
        for (int i = 0; i < countries.length; i++)
        {
            if (countries[i].getOwner().getName().equals(name))
            {
                myCountries[c] = countries[i];
                c++;
            }
        }
        
        // Get Troops Based On Owned Countries
        newTroops = 0;
        switch(myCountries.length)
        {
            case 1 :
            case 2 :
            case 3 :
            case 4 :
            case 5 :
            case 6 :
            case 7 :
            case 8 :
            case 9 :
            case 10 :
                newTroops += 3;
                break;
            case 11 :
            case 12 :
            case 13 :
                newTroops += 4;
                break;
            case 14 :
            case 15 :
            case 16 :
                newTroops += 5;
                break;
            case 17 :
            case 18 :
            case 19 :
                newTroops += 6;
                break;
            case 20 :
            case 21 :
            case 22 :
                newTroops += 7;
                break;
            case 23 :
            case 24 :
            case 25 :
                newTroops += 8;
                break;
            case 26 :
            case 27 :
            case 28 :
                newTroops += 9;
                break;
            default :
                newTroops += 10;
        }
        
        // Get Troops Based Of Whole Continents
        int[] continents = new int[6];
        for (int i = 0; i < myCountries.length; i++)
        {
            switch (myCountries[i].getContinent()){
                case "North America":
                    continents[0]++;
                    break;
                case "South America":
                    continents[1]++;
                    break;
                case "Europe":
                    continents[2]++;
                    break;
                case "Africa":
                    continents[3]++;
                    break;
                case "Asia":
                    continents[4]++;
                    break;
                case "Australia":
                    continents[5]++;
                    break;
                default:
                    System.out.println("Continent " + myCountries[i].getContinent() + " of " + myCountries[i].getName() + " not found!");
                }
        }
        
        if (continents[0] == 9) {newTroops += 5;}
        if (continents[1] == 4) {newTroops += 2;}
        if (continents[2] == 7) {newTroops += 5;}
        if (continents[3] == 6) {newTroops += 3;}
        if (continents[4] ==12) {newTroops += 7;}
        if (continents[5] == 4) {newTroops += 2;}
    }
    
    public void placeTroops(Country[] countries, Country country)
    {
        country.addAmountOfTroops(1);
        newTroops = newTroops -1;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getNewTroops()
    {
        return newTroops;
    }
    
    public Color getColor()
    {
        return color;
    }
}
