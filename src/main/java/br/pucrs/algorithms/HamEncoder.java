package br.pucrs.algorithms;

import java.util.ArrayList;

public class HamEncoder {
    public static void main(String[] args) {
        encode(args[0]);
    }

    public static String encode(String str) {
        StringBuilder res = new StringBuilder();
        String[] binArray = new String[str.length()+1];
        binArray[binArray.length-1] = "";

        for( int i = 0; i < str.length(); i++ ) {
            String aux = Integer.toBinaryString(str.charAt(i)-0);
            binArray[i] = "0" + aux;
            res.append(run(hamming(Util.reverse(binArray[i]))).toUpperCase());
        }
        System.out.println(res);
        return res.toString();
    }

    public static String hamming(String str) {
        return "xx"+str.charAt(0)+"x"+str.substring(1,4)+"x"+str.substring(4,str.length());
    }

    public static String run(String str) {
        int count = 1;
        ArrayList<String> binArray = new ArrayList<>();
        for( int i =0; i < str.length(); i++ ) {
            if( str.charAt(i) == '1' ) {
                binArray.add( String.format("%4s", Integer.toBinaryString(count)).replace(' ', '0') );
            }
            count++;
        }

        binArray.add("");

        for( int i = 0; i < 4; i++ ) {
            StringBuilder aux = new StringBuilder();
            for( int j = 0; j < binArray.size()-1; j++ ) {
                aux.append(binArray.get(j).charAt(i));
            }
            //calcula a paridade e adiciona no final do array
            binArray.set(binArray.size()-1, binArray.get(binArray.size()-1)+Util.countParity(aux.toString()));
        }

        String reversedParity = Util.reverse(binArray.get(binArray.size()-1));
        //substitui os xs pelos bits de paridade
        str = removeX(str, reversedParity);
        return Util.binaryToHex(Util.reverse(str));
    }

    public static String removeX(String str, String reversedParity) {
        int count = 0;
        for( int i=0; i < str.length(); i++ ) {
            if( str.charAt(i) == 'x' ) {
                str = str.replaceFirst("x", ""+reversedParity.charAt(count));
                count++;
            }
        }
        return str;
    }
}
