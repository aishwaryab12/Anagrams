/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    Integer wordlength=DEFAULT_WORD_LENGTH;
    ArrayList<String> wordList=new ArrayList<String>();
    HashMap<String,ArrayList<String>> lettersToWords =new HashMap<String, ArrayList<String>>();
    HashMap<Integer,ArrayList<String>> sizeToWords=new HashMap<Integer,ArrayList<String>>();
    HashSet<String> wordset=new HashSet<String>();
    int i=0;


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            putintoHashMap(word);
            putintoHashSet(word);
            putintosizeHashMap(word);


        }
    }

    public boolean isGoodWord(String word, String base) {

        if((!(word.contains(base))) && wordset.contains(word))
        {
           return true;
        }
        else
        {
                return false;
        }
       // return true;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

//        for (int i = 0; i < wordList.size(); i++) {
//            String fromList=wordList.get(i);
//
//            if(isAnagram(targetWord,fromList))
//            {
//                result.add(fromList);
//            }
//            else
//            {
//                continue;
//            }
    //}

        String sortedtargetword=sorted(targetWord);
        result=lettersToWords.get(sortedtargetword);
        return result;

    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> temp=new ArrayList<String>();
        String exword;

        for(char c='a';c<'z';c++)
        {
             exword=word+c;
            String sortedword=sorted(exword);
           if(lettersToWords.containsKey(sortedword)) {
               temp = lettersToWords.get(sortedword);

               for (int i = 0; i < temp.size(); i++) {
                   if (isGoodWord(temp.get(i),word)) {
                       result.add(temp.get(i));
                   }
               }
           }
        }
        return result;
    }

    public String pickGoodStarterWord() {

//        int r=random.nextInt(wordList.size());
//       ArrayList<String> checkSize=new ArrayList<String>();
//
//        while(true) {
//
//            String randomWord = wordList.get(r);
//            String sortedRandomWord = sorted(randomWord);
//
//            if (lettersToWords.containsKey(sortedRandomWord)) {
//                checkSize = lettersToWords.get(sortedRandomWord);
//
//                if (checkSize.size() > MIN_NUM_ANAGRAMS)
//                {
//                    return randomWord;
//                }
//                else
//                {
//                    r++;
//                    if(r>wordList.size())
//                    {
//                        r=random.nextInt(wordList.size());
//                    }
//
//                }
//            }
//        }

        ArrayList<String> temp=new ArrayList<String>();
        ArrayList<String> min=new ArrayList<String>();

        while(true)
        {
            temp = sizeToWords.get(wordlength);
            String word = temp.get(i);

            if (i > temp.size())
            {
                i = 0;
                wordlength++;
                if (wordlength > MAX_WORD_LENGTH) {
                    wordlength = DEFAULT_WORD_LENGTH;
                }
            }

            if (lettersToWords.containsKey(sorted(word)))
            {
                min = lettersToWords.get(sorted(word));

                if (min.size() >= MIN_NUM_ANAGRAMS)
                {
                    i++;
                    return word;
                }
                else
                {
                    i++;
                    if (i > temp.size())
                    {
                        i = 0;
                        wordlength++;
                        if (wordlength > MAX_WORD_LENGTH) {
                            wordlength = DEFAULT_WORD_LENGTH;
                        }
                    }

                    continue;
                }
            }
        }

    }

    public boolean isAnagram(String s1,String s2)
    {
        String sorted1=sorted(s1);
        String sorted2=sorted(s2);

            if(sorted1.equalsIgnoreCase(sorted2)) {
                return true;
            }
            else {
                return false;
            }
    }

    public String sorted(String toSort)
    {
        char ch[]=toSort.toCharArray();
         Arrays.sort(ch);
        return (new String(ch));
    }

    public void putintoHashMap(String word)
    {
        ArrayList<String> temp=new ArrayList<String>();
        String sortedWord=sorted(word);

        if(lettersToWords.containsKey(sortedWord))
        {
            temp=lettersToWords.get(sortedWord);
            temp.add(word);
            lettersToWords.put(sortedWord,temp);
        }
        else
        {
            temp.add(word);
            lettersToWords.put(sortedWord,temp);

        }
    }

    public void putintoHashSet(String word)
    {
        wordset.add(word);
    }

    public void putintosizeHashMap(String word)
    {
        ArrayList<String> temp=new ArrayList<String>();
        Integer sizeofword=word.length();

        if(sizeToWords.containsKey(sizeofword))
        {
            temp=sizeToWords.get(sizeofword);
            temp.add(word);
            sizeToWords.put(sizeofword,temp);
        }
        else
        {
            temp.add(word);
            sizeToWords.put(sizeofword,temp);
        }

    }


}
