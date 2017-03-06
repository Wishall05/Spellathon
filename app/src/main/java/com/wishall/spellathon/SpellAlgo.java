package com.wishall.spellathon;

/**
 * Created by Vishal on 03-07-2016.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import android.content.res.AssetManager;
import android.util.Log;

public class SpellAlgo {

    private Game game;
    private static HashMap<String, Integer> hm;
    private static HashMap<String, Integer> hmAns;
    private static ArrayList Length7List;
    private static Random randomno;
    private static int size7List;
    public final static int LEN = 7;

    public SpellAlgo() {
        super();
        // TODO Auto-generated constructor stub
        init();
    }

    public SpellAlgo(Game game) {
        super();
        // TODO Auto-generated constructor stub
        this.game = game;
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        AssetManager am = game.getAssets();
        hm = new HashMap<String, Integer>();
        Length7List = new ArrayList();
        randomno = new Random();
        try {
            InputStream is = am.open("dict.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            String s = br.readLine();

            while (s != null)

            {
                hm.put(s, 1);
                if (s.length() == 7) {
                    Length7List.add(s);
                }
                s = br.readLine();

            }
            Log.d("vishal", "hm has " + hm.get("love"));
            Log.d("vishal", "hm has " + hm.get("vishal"));
            Log.d("vishal", "hm has " + hm.get("animal"));
            Log.d("vishal", "hm has " + hm.get("abljc"));
            Log.d("vishal", "Length7list has words = " + Length7List.size());
            size7List = Length7List.size();
            //play();

            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d("vishal", "file related exception");
            e.printStackTrace();
        }
    }


    public void play() {
        hmAns = new HashMap<String, Integer>();

        int anyNum = randomno.nextInt(size7List);
        int anyChNum = randomno.nextInt(LEN);
        String anyWord = (String) Length7List.get(anyNum);
        char anyCh = anyWord.charAt(anyChNum);
        Log.d("vishal", "anyNum = " + anyNum);
        Log.d("vishal", "anyWord = " + anyWord);
        Log.d("vishal", "anyCh = " + anyCh);
        printCombination(anyWord, 7, 4, anyCh);
        printCombination(anyWord, 7, 5, anyCh);
        printCombination(anyWord, 7, 6, anyCh);
        printCombination(anyWord, 7, 7, anyCh);

		/*Set<String> keySet = hmAns.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
		   String key = keySetIterator.next();
		   Log.d("vishal", "key: " + key + " value:" + hmAns.get(key));
		}*/
        //remove anyCh from  anyWord
        Log.d("vishal", "play hmAns Size: " + hmAns.size());
        if(hmAns.size()>=10 && hmAns.size()<=40) {
            String restStr = anyWord.replaceFirst(""+anyCh, "");
            Log.d("vishal", "restStr: " + restStr);
            //Shuffle anyWord
            game.setRestStr(shuffle(restStr));
            game.setMainCh(anyCh);
            game.setHmAns(hmAns);
        }else{
            Log.d("vishal", "play calling play again");
            play();
            return;
        }

    }

    public String shuffle(String input){
        List<Character> characters = new ArrayList<Character>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        // Log.d("vishal", output.toString());
        return output.toString();
    }

    /*
     * arr[] ---> Input Array data[] ---> Temporary array to store current
     * combination start & end ---> Staring and Ending indexes in arr[] index
     * ---> Current index in data[] r ---> Size of a combination to be printed
     */
    static void combinationUtil(String arr, int n, int r, int index,
                                char data[], int i, char ch) {
        // Current combination is ready to be printed, print it
        if (index == r) {
            // for (int j=0; j<r; j++)
            // System.out.print(data[j]+" ");
            boolean flag = false;
            for (int j = 0; j < data.length; j++) {

                // check if string array contains the string
                if (data[j] == ch) {

                    // string found
                    flag = true;
                    break;

                }
            }
            if (flag) {
                permute(data, 0, r - 1);
            }
            // System.out.println("********************************");
            return;
        }

        // When no more elements are there to put in data[]
        if (i >= n)
            return;

        // current is included, put next at next location
        data[index] = arr.charAt(i);
        combinationUtil(arr, n, r, index + 1, data, i + 1, ch);

        // current is excluded, replace it with next (Note that
        // i+1 is passed, but index is not changed)
        combinationUtil(arr, n, r, index, data, i + 1, ch);
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    static void printCombination(String arr, int n, int r, char ch) {
        // A temporary array to store all combination one by one
        char data[] = new char[r];

        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, n, r, 0, data, 0, ch);
    }

    // *****************************************

    public static void permute(char[] array, int left, int right) {
        int i;
        if (left == right) {
            // System.out.println(array);
            // System.out.println("***"+hm.get(wish));
            String pWord = new String(array);
            if (hm.get(pWord) != null) {
                //Log.d("vishal", pWord);
                hmAns.put(pWord, 1);
            }
        } else {
            for (i = left; i <= right; i++) {
                swap(array, left, i);
                permute(array, left + 1, right);
                swap(array, left, i);
            }
        }
    }

    public static void swap(char[] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}