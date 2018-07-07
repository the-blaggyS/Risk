
/**
 * Manges attributes of countries
 */
public class Country
{
    private String name;
    private Country[] neighboringCountries;// = new Country[10];
    private Player owner;
    private int amountOfTroops;
    private String continent;
    
    private int x;
    private int y;
    
    public Country(String pName, Player pOwner, int pAmountOfTroops, int pX, int pY, String pContinent)
    {
        name = pName;
        owner = pOwner;
        amountOfTroops = pAmountOfTroops;
        continent = pContinent;
        
        x = pX;
        y = pY;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Country[] getNeighboringCountries()
    {
        return neighboringCountries;
    }
    
    public void setNeighboringCountries(Country[] pNeighboringCountries)
    {
        neighboringCountries = pNeighboringCountries;
    }
    
    public Player getOwner()
    {
        return owner;
    }
    
    public void setOwner(Player pOwner)
    {
        owner = pOwner;
    }
    
    public int getAmountOfTroops()
    {
        return amountOfTroops;
    }
    
    public void addAmountOfTroops(int pAmountOfTroops)
    {
        amountOfTroops = amountOfTroops + pAmountOfTroops;
    }
    
    public String getContinent()
    {
        return continent;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }

}
