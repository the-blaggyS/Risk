import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

/**
 * Handles the whole Game:
 *  Draws the UI
 *  Calculates the attacks
 *  Manages mouse clicks
 *
 * @author Lucas
 * @version 02.06.2018
 */

public class Game extends JPanel
{    
    private Player[] players;
    private Country[] countries;
    private Dice dice;
    
    private boolean running = false;
    private boolean newPlayer = true;
    private boolean attacking = false;
    private boolean chooseYours = false;
    private boolean chooseEnemy = false;
    private boolean selectTroops = false;
    private int attackingTroops;
    private int p = 0;
    private Player player;
    
    private Image map;
    private Image start;
    
    private JLabel[] troopLabelCircles = new JLabel[42];
    private JLabel[] troopLabelAmounts = new JLabel[42];
    private JLabel instructions = new JLabel();
    
    private int[] attackingRoll;
    private int[] defendingRoll;
    private Country attackingCountry;
    private Country enemyCountry;
    
    public Game()
    {   
        Init init = new Init();
        players = init.players;
        countries = init.countries;
        dice = init.dice;
        
        initBoard();
    }
    /**
     * Initializes the board and implements a mouse handling method
     */
    private void initBoard()
    {
        ImageIcon iiMap = new ImageIcon("src/resources/map.png");
        map = iiMap.getImage();
        ImageIcon iiStart = new ImageIcon("src/resources/start.png");
        start = iiStart.getImage();
        
        int w = map.getWidth(this);
        int h = map.getHeight(this);
        setPreferredSize(new Dimension(w, h));
        setLayout(null);
        
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                Country country = clickedCountry(e.getX(), e.getY());
                //System.out.println(country);
                //System.out.println("X: " + Integer.toString(e.getX()) + ", " + "Y: " + Integer.toString(e.getY()));
                
                player = players[p%2];
                
                removeAll();
                
                // Init New Troops
                if (newPlayer)
                {
                    player.setNewTroops(countries);
                    running = true;
                    newPlayer = false;
                    createInstructionLabel("Click Countries To Place Your Troops");
                }
                
                // Place New Troops
                if (player.getNewTroops() > 0)
                {
                    if (country != null && country.getOwner() == player)
                    {
                        player.placeTroops(countries, country);
                    }
                    if (player.getNewTroops() == 0)
                    {
                        attacking = true;
                        chooseYours = true;
                        createInstructionLabel("Choose Attacking Country");
                        drawFinishButton();
                        if (getWidth()-200 < e.getX() && e.getX() < getWidth()-25 && getHeight()-75 < e.getY() && e.getY() < getHeight()-25)
                        {
                            newPlayer = true;
                            chooseYours = false;
                            chooseEnemy = false;
                            selectTroops = false;
                            p++;
                        }
                    }
                    else
                    {
                        createInstructionLabel("Click Countries To Place Your Troops");
                    }
                }
                
                // Attack
                else if (attacking)
                {
                    // Choose Your Country
                    if (chooseYours)
                    {
                        if (country != null && country.getOwner() == player && country.getAmountOfTroops() > 1)
                        {
                            attackingCountry = country;
                            chooseYours = false;
                            chooseEnemy = true;
                            createInstructionLabel("Choose Enemy Country");
                        }
                        else
                        {
                            createInstructionLabel("Choose Attacking Country");
                        }
                    }
                    // Choose Enemys Country
                    else if (chooseEnemy)
                    {
                        if (country != null && country.getOwner() != player && neighbor(attackingCountry, country))
                        {
                            enemyCountry = country;
                            chooseEnemy = false;
                            selectTroops = true;
                            createInstructionLabel("Choose Amount Of Troops To Attack With");
                            drawTroopSelection();
                        }
                        else
                        {
                            createInstructionLabel("Choose Enemy Country");
                        }
                    }
                    
                    // Slect Amount Of Troops To Attack With
                    else if (selectTroops)
                    {
                        if (175 < e.getY() && e.getY() < 275 && 375 < e.getX() && e.getX() < 665)
                        {
                            if (375 < e.getX() && e.getX() < 475)
                            {
                                attackingTroops = 1;
                            }
                            if (475 < e.getX() && e.getX() < 565)
                            {
                                attackingTroops = 2;
                            }
                            if (565 < e.getX() && e.getX() < 665)
                            {
                                attackingTroops = 3;
                            }
                            calcDices();
                            drawDices();
                            selectTroops = false;
                            chooseYours = true;
                        }
                        else
                        {
                            createInstructionLabel("Select Amount Of Troops To Attack With");
                            drawTroopSelection();
                        }
                    }
                    drawFinishButton();
                    if (getWidth()-200 < e.getX() && e.getX() < getWidth()-25 && getHeight()-75 < e.getY() && e.getY() < getHeight()-25)
                    {
                        newPlayer = true;
                        attacking = false;
                        chooseYours = false;
                        chooseEnemy = false;
                        selectTroops = false;
                        p++;
                    }
                }
                
                // Draw UI
                drawPlayerName();
                drawTroopLabels();
                repaint();
            }
        }
        );
    }
    
    public void drawFinishButton()
    {
        ImageIcon finishIcon = new ImageIcon("src/resources/finish.png");
        JLabel finishLabel = new JLabel(finishIcon);
        add(finishLabel);
        finishLabel.setBounds(getWidth()-200, getHeight()-75, 175, 50);
    }
    
    /**
     * Calculates which countries loses troops
     */
    public void calcDices()
    {
        int defendingTroops;
        if (enemyCountry.getAmountOfTroops() == 1) {defendingTroops = 1;}
        else {defendingTroops = 2;}
        defendingRoll = sortDices(dice.roll(defendingTroops));
        
        if (attackingTroops >= attackingCountry.getAmountOfTroops())
        {attackingTroops = attackingCountry.getAmountOfTroops()-1;}
        attackingRoll = sortDices(dice.roll(attackingTroops));
        
        int min;
        if (attackingRoll.length < defendingRoll.length) {min = attackingRoll.length;}
        else {min = defendingRoll.length;}
        
        for (int i = 0; i < min; i++)
        {
            if (attackingRoll[i] > defendingRoll[i]) {enemyCountry.addAmountOfTroops(-1);}
            else {attackingCountry.addAmountOfTroops(-1);}
        }
        
        if (enemyCountry.getAmountOfTroops() == 0)
        {
            enemyCountry.setOwner(player);
            enemyCountry.addAmountOfTroops(attackingRoll.length);
            attackingCountry.addAmountOfTroops(-attackingRoll.length);
        }
    }

    /**
     * Draws the dices
     */
    public void drawDices()
    {
        ImageIcon[] rollFaces = new ImageIcon[6];
        for (int i = 0; i < rollFaces.length; i++)
        {
            rollFaces[i] = new ImageIcon(new ImageIcon("src/resources/dice_" + Integer.toString(i+1)).getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));                                    
        }
        
        JLabel attackingLabel = new JLabel("Attacker");
        attackingLabel.setFont(new Font("Calibri",Font.BOLD , 32));
        add(attackingLabel);
        attackingLabel.setBounds(350, 50, 250, 50);
        
        JLabel defendingLabel = new JLabel("Defender");
        defendingLabel.setFont(new Font("Calibri",Font.BOLD , 32));
        add(defendingLabel);
        defendingLabel.setBounds(550, 50, 250, 50);
        
        JLabel[] attackingRollLabels = new JLabel[attackingRoll.length];
        for (int i = 0; i < attackingRoll.length; i++)
        {
            attackingRollLabels[i] = new JLabel(rollFaces[attackingRoll[i]-1]);
            add(attackingRollLabels[i]);
            attackingRollLabels[i].setBounds(400, 125 + 75*i, 50, 50);
        }
        
        JLabel[] defendingRollLabels = new JLabel[defendingRoll.length];
        for (int i = 0; i < defendingRoll.length; i++)
        {
            int dRi = defendingRoll[i]-1;
            ImageIcon rF = rollFaces[dRi];
            defendingRollLabels[i] = new JLabel(rF);
            add(defendingRollLabels[i]);
            defendingRollLabels[i].setBounds(600, 125 + 75*i, 50, 50);
        }
    }
    
    public int[] sortDices(int[] dices)
    {
        for (int i = 0; i < dices.length; i++)
        {
            for (int j = i; j > 0; j--)
            {
                if (dices[j] > dices[j-1])
                {
                    int tmp = dices[j];
                    dices[j] = dices[j-1];
                    dices[j-1] = tmp;
                }
            }
        }
        return dices;
    }
    
    public void drawTroopSelection()
    {
        JLabel[] amounts= new JLabel[3];
        for (int i = 0; i < 3; i++)
        {
            amounts[i] = new JLabel(Integer.toString(i+1));
            amounts[i].setFont(new Font("Calibri",Font.BOLD , 64));
            add(amounts[i]);
            amounts[i].setBounds(400 + 100*i, 200, 50, 50);
        }
        ImageIcon foregroundImage = new ImageIcon("src/resources/foreground.png");
        JLabel foreground = new JLabel(foregroundImage);
        add(foreground);
        foreground.setBounds(375, 175, 290, 100);
    }
    
    public void createInstructionLabel(String string)
    {
        instructions = new JLabel(string);
        instructions.setFont(new Font("Calibri",Font.BOLD , 16));
        instructions.setForeground(Color.white);
        add(instructions);
        instructions.setBounds(20, 10, 400, 50);
    }
    
    /**
     * Tests if the selected country is a neighbor of the attacking country
     */
    public boolean neighbor(Country yourCountry, Country country)
    {
        boolean neighbor = false;
        Country[] neighbors = yourCountry.getNeighboringCountries();
        /*
        for (int i = 0; i < neighbors.length; i++)
        {
            if (neighbors[i] == country)
            {
                neighbor = true;
                break;
            }
        }
        */
        int low = 0;
        int high = neighbors.length - 1;
        int middle;
        
        while (low <= high)
        {
            middle = (low + high) / 2;
            if (neighbors[middle].getName().compareTo(country.getName()) < 0)
            {
                low = ++middle;
            }
            if (neighbors[middle].getName().compareTo(country.getName()) > 0)
            {
                high = --middle;
            }
            if (neighbors[middle].getName().compareTo(country.getName()) == 0)
            {
                neighbor = true;
                break;
            }
        }
        return neighbor;
    }
    
    /**
     * Translates the coordinates of the mouse press to a Country object
     */
    public Country clickedCountry(int x, int y)
    {
        Country country = null;
        for (int i = 0; i < countries.length; i++)
        {
            if (countries[i].getX()-20 < x-15 && x-15 < countries[i].getX()+20)
            {
                if (countries[i].getY()-20 < y-15 && y-15 < countries[i].getY()+20)
                {
                    country = countries[i];
                    break;
                }
            }
        }
        return country;
    }
    
    public void drawPlayerName()
    {
        JLabel playerName = new JLabel(player.getName());
        playerName.setFont(new Font("Calibri",Font.BOLD , 32));
        playerName.setForeground(player.getColor());
        add(playerName);
        playerName.setBounds(25/*getWidth()/2 - player.getName().length()/2*20*/, getHeight() - 75, player.getName().length()*20, 50);
    }
    
    public void drawTroopLabels()
    {
        ImageIcon circle = new ImageIcon("src/resources/circle.png");
        for (int i = 0; i < countries.length; i++)
        {
            // Draw New Ones
            troopLabelCircles[i] = new JLabel(circle);
            troopLabelAmounts[i] = new JLabel(Integer.toString(countries[i].getAmountOfTroops()));
            troopLabelAmounts[i].setFont(new Font("Calibri",Font.BOLD , 32));
            troopLabelAmounts[i].setForeground(countries[i].getOwner().getColor());
            add(troopLabelAmounts[i]);
            add(troopLabelCircles[i]);
            troopLabelCircles[i].setBounds(countries[i].getX()-15, countries[i].getY()-15, circle.getIconWidth(), circle.getIconHeight());
            troopLabelAmounts[i].setBounds(countries[i].getX()-15+6, countries[i].getY()-15, circle.getIconWidth(), circle.getIconHeight());
        }
    }
    
    /**
     * Overwritten to draw the background pictures
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (running)
        {
            g.drawImage(map, 0, 0, null);
        }
        else
        {
            g.drawImage(start, 0, 0, null);
        }
        Toolkit.getDefaultToolkit().sync();
    }
}
