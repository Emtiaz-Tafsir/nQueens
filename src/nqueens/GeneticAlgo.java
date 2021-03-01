/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nqueens;

import java.util.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
public class GeneticAlgo
{
     int[][] chr;
     double[] val;
     double[] pop;
     int size;
     double optVal;
     double totVal;
     TextArea tx;
     Label l;
     GUIController gui;
    
     public GeneticAlgo(int chrSize, int size, TextArea tx, Label l, GUIController gui){
        this.tx = tx;
        this.l = l;
        this.gui = gui;
        chr = new int[chrSize][];
        val = new double[chrSize];
        pop = new double[chrSize];
        this.size = size;
        optVal = (size*(size-1))/2;
        start();
     }
    
    public void start() {
        Random r = new Random();
        tx.setText("Initializing....\n\n");
        for(int i=0; i<val.length; i++){
            int[] a = new int[size];
            for(int j=0; j<size; j++){
                a[j] = r.nextInt(size)+1;
            }
            chr[i] = a;
            val[i] = r.nextDouble();
            totVal += val[i];
        }
        sortChr(chr,val);
        countPOP();
        tx.appendText("Initiation Complete....\n\n");
        printChr();
        evaluate(chr,val);
        sortChr(chr,val);
        cTot();
        countPOP();
        tx.appendText("\nGeneration 0: \n");
        printChr();
        int i;
        for(i=1; i<501 && val[0]<optVal; i++){
            crossChr();
            cTot();
            countPOP();
            tx.appendText("\nGeneration "+i+": \n");
            l.setText(""+i);
            printChr();
        }
        
        if(i==501){
            tx.appendText("\n\n Simulation Failed");
        }
        else
            tx.appendText("\n\n Simulation Successful!!");
        gui.genarate(chr[0]);
        
    }
    
    public  void evaluate(int[][] chr, double[] val){
        double v;
        for(int i=0; i<chr.length; i++)
        {
            v = optVal;
            for(int j=0; j<size-1; j++){
                int ud=chr[i][j], dd=chr[i][j];
                for(int k=j+1; k<size; k++){
                    ud++;
                    dd--;
                    if(chr[i][j]==chr[i][k] || ud==chr[i][k] || dd==chr[i][k]){
                        v--;
                        //System.out.println(chr[i][j]+" "+chr[i][k]+" "+ud+" "+dd);
                    }
                }
            }
            val[i] = v;
        }
    }
    
    public  void cTot(){
        totVal = 0;
        for(double i : val) totVal+=i;
    }
    
    public  void countPOP(){
        for(int i=0; i<pop.length; i++)
            pop[i] = val[i]/totVal;
    }
    
    public  void crossChr(){
        int[][] offChr = new int[val.length][];
        Random r = new Random();
        for(int i=0; i<val.length; i++){
            int p1 = randPick();
            int p2;
            while((p2=randPick()) == p1);
            int[] c1 = new int[size], c2 = new int[size];
            for(int j=0; j<size; j++){
                if(j<size/2){
                    c1[j] = chr[p1][j];
                    c2[j] = chr[p2][j];
                    continue;
                }
                c1[j] = chr[p2][j];
                c2[j] = chr[p1][j];
            }
            c1[r.nextInt(size)] = r.nextInt(size)+1;
            c2[r.nextInt(size)] = r.nextInt(size)+1;
            offChr[i++] = c1;
            if(i==val.length) break;
            offChr[i] = c2;
        }
        double[] offVal = new double[offChr.length];
        evaluate(offChr, offVal);
        sortChr(offChr, offVal);
        int[][] ta = new int[chr.length][];
        double[] tv = new double[val.length];
        int i=0,j=0,k=0;
        for(; i<chr.length && j<chr.length && k<chr.length;k++){
            if(val[i]>offVal[j]){ 
                ta[k] = chr[i];
                tv[k] = val[i++];
                continue;
            }
            ta[k] = offChr[j];
            tv[k] = offVal[j++];
        }
        chr = ta;
        val = tv;
    }
    
    public  int randPick(){
        double r = Math.random();
        double pu = 1;
        for(int i=0; i<pop.length; i++){
            double pd = pu - pop[i];
            //System.out.println("num = "+r+": "+pu+">---->"+pd);
            if(pd<=r && r<=pu) return i;
            pu = pd;
        }
        return 0;
    }
    
    public  void sortChr(int[][] chr, double[] val){
        int n = val.length;
        for (int i=n-2; i>=0; --i){
            double key = val[i];
            int[] t = chr[i];
            int j = i + 1;
            while (j < n && val[j] > key){
                val[j - 1] = val[j];
                chr[j - 1] = chr[j];
                j = j + 1;
            }
            val[j - 1] = key;
            chr[j - 1] = t;
        }
    }
    
    public  void printChr(){
        for(int i=0; i<val.length; i++){
            for(int j : chr[i])
                tx.appendText(j+" ");
            tx.appendText(": value = "+val[i]+" : Population = "+pop[i]+"\n");
        }
    }
}
