/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ShariQ
 */
public class myImage {
    public String filename;
    public int width;
    public int height;
    public BufferedImage image;
    
    public myImage(){
        this.filename="picture.jpg";
        try {
            image = ImageIO.read(new File(this.filename));
            this.width= image.getWidth();
            this.height= image.getHeight(); 
        } catch (IOException ex) {
            System.out.println("Image Not Found.");
        }
        
        System.out.println("Width: "+this.width+"\tHeight: "+this.height);
    }
    
    
    public void convertToFilters(){
        int[] RGB=new int[3];
        Scanner scan=new Scanner(System.in);
        System.out.println("1: FOR DARK FILTER"
                + "\n2: FOR BRIGHT FILTER"
                + "\n3: FOR GRAY-SCALE FILTER"
                + "\n4: FOR REDDISH FILTER"
                + "\n5: FOR BLUEISH FILTER"
                + "\n6: FOR GREENISH FILTER");
        
        int yn=scan.nextInt();

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                Color c = new Color(image.getRGB(i, j));
                int red = (int)(c.getRed());
                int green =(int)(c.getGreen());
                int blue = (int)(c.getBlue());

                RGB=getRGBvalues(red,green,blue,yn);
                
                //System.out.println("RED: "+red+" GREEN: "+green+" BLUE: "+blue);
                Color newColor = new Color(RGB[0],RGB[1],RGB[2]);
                image.setRGB(i,j,newColor.getRGB());
                
            }
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now(); 
        String Name=dtf.format(now)+".jpg";
        String destName=Name.replace('/','_');
        destName=destName.replace(':', '_');
        System.out.println(destName);
        File ouptut = new File(destName);
        try {
            ImageIO.write(image, "jpg", ouptut);
            System.out.println("Filter Image Created.");

        } catch (IOException ex) {
            System.out.println("Filter Not Created.");
        }
        
    }

    private int[] getRGBvalues(int red,int green,int blue,int yn) {
        int[] temp=new int[3];
        
        switch(yn){
            case 1:
                temp[0]=(int) (red*0.5);
                temp[1]=(int) (green*0.5);
                temp[2]=(int) (blue*0.5);
                break;
                
            case 2:
                temp[0]=(int) (red*1.5);
                temp[1]=(int) (green*1.5);
                temp[2]=(int) (blue*1.5);
                break;
                
            case 3:
                red=(int) (red*0.299);
                green=(int) (green*0.587);
                blue=(int) (blue*0.114);
                
                temp[0]=red+green+blue;
                temp[1]=red+green+blue;
                temp[2]=red+green+blue;
                break;
            
            case 4:
                temp[0]=red+(red*100)/255;
                temp[1]=green;
                temp[2]=blue;
                break;
                
            case 5:
                temp[0]=red;
                temp[1]=green+(green*100)/255;
                temp[2]=blue;
                break;
                
            case 6:
                temp[0]=red;
                temp[1]=green;
                temp[2]=blue+(blue*100)/255;
                break;
            
            default:
                temp[0]=red;
                temp[1]=green;
                temp[2]=blue;
                break;
        }
        
        if(temp[0]>255){
            temp[0]=255;
        }
        if(temp[1]>255){
            temp[1]=255;
        }
        if(temp[2]>255){
            temp[2]=255;
        }
                
        return temp;
    }
    
    public void filterSquare(){
        int minWidth=(10*this.width)/100;
        int minHeight=(10*this.height)/100;
        int maxWidth=this.width-minWidth;
        int maxHeight=this.height-minHeight;
        
        System.out.println(minWidth+" "+minHeight);
                int num=0;int div=0;
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                Color c = new Color(image.getRGB(i, j));
                int red = (int)(c.getRed());
                int green =(int)(c.getGreen());
                int blue = (int)(c.getBlue());
                
                if((i<minWidth || j<minHeight) || (i>maxWidth || j>maxHeight)){
                    red=num;
                    green=div+num;
                    blue=num+div;
                    
                    if(red>255){
                        red=255;
                    }
                    if(green>255){
                        green=green%255;
                    }
                    if(blue>255){
                        blue=255;
                    }
                    
                }
                
                
                
                Color newColor = new Color(red,green,blue);
                image.setRGB(i,j,newColor.getRGB());
                
                if(num!=255 && j< this.height/2){
                    num++;
                }else{
                    num--;
                    if(num<0){
                        num=0;
                    }
                }
                
                div++;
            } num=0;
        }
        
        File ouptut = new File("filter.jpg");
        try {
            ImageIO.write(image, "jpg", ouptut);
            System.out.println("Filter Image Created.");

        } catch (IOException ex) {
            System.out.println("Filter Not Created.");
        }
    }
}
