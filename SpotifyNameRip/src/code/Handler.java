/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class Handler {
    public static String[] convertIntoNames(String sourceCode){
        String aux = sourceCode;
        aux=sourceCode.substring(sourceCode.indexOf("mainContainer"),sourceCode.indexOf("engageView"));
        String [] beta = aux.split("\n");
        
        
        //System.out.println(aux);
        beta[2]="";
        for (int i = 0; i < beta.length; i++) {
            if(!beta[i].contains("<li class") || beta[i].startsWith("</li><li") )
                beta[i]="";
            else{
            
            beta[i]=beta[i].replace("<li class=\"", "");
            beta[i]=beta[i].replace("\"","");
            beta[i]=beta[i].replace("</li>","");
            beta[i]=beta[i].replace("		","");
            
            
            if(beta[i].startsWith("artist"))
                beta[i]="Artist:\t"+beta[i].substring(beta[i].indexOf(">")+1);
            if(beta[i].startsWith("duration"))
                beta[i]="Duration:\t"+beta[i].substring(beta[i].indexOf(">")+1);
            if(beta[i].startsWith("track-title")){
                beta[i]=beta[i].substring(beta[i].indexOf(">")+1);
                beta[i]="Track title:\t"+beta[i].substring(beta[i].indexOf(".")+1);
            }
        }
        }
        
        List<String> result = new ArrayList<>();
        
        
        for (int i = 0; i < beta.length; i++) {
            if(!beta[i].equals(""))
                result.add(beta[i]);
            if(beta[i].startsWith("Artist"))
                result.add("");
        }
        
        beta = result.toArray(new String[result.size()]);
        
        //for (int i = 0; i < beta.length; i++) {
           // if(!beta[i].equals(""))
          //      System.out.println(beta[i]);
        //}
        return beta;
    }
}
