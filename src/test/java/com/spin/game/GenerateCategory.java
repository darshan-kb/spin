//package com.spin.game;
//
//import com.spin.game.entities.BetCategory;
//import com.spin.game.entities.BetValuesMap;
//import com.spin.game.repository.BetCategoryRepository;
//import com.spin.game.repository.BetValuesMapRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//@SpringBootTest
//public class GenerateCategory {
//    @Autowired
//    BetCategoryRepository bcr;
//
//    @Autowired
//    BetValuesMapRepository bvmr;
//
//    @Test
//    public void generateTwoAdjacentCombinationCategories(){
//
//        List<String> TwoValueCombination = new ArrayList<>();
//
//        for(int i=1;i<=36;i++){
//            TwoValueCombination.add(i+"");
//        }
//
//        for(int i=1;i<=33;i++){
//            TwoValueCombination.add(i+"-"+(i+3));
//            if(i%3!=0){
//                TwoValueCombination.add(i+"-"+(i+1));
//            }
//        }
//        generateThreeColumnCombination(TwoValueCombination);
//    }
//
//    @Test
//    public void generateThreeColumnCombination(List<String> result){
//
//        for(int i=0;i<11;i++){
//            int t = (i*3)+1;
//            result.add((t)+"-"+(t+1)+"-"+(t+3)+"-"+(t+4));
//            t++;
//            result.add((t)+"-"+(t+1)+"-"+(t+3)+"-"+(t+4));
//        }
//        for(int i=1;i<=12;i++){
//            result.add(i+"lineOf3");
//        }
//
//        for(int i=1;i<=3;i++){
//            result.add(i+"lineOf12");
//        }
//
//        for(int i=1;i<=3;i++){
//            result.add(i+"dozen");
//        }
//
//        result.add("even");
//
//        result.add("odd");
//
//        result.add("red");
//
//        result.add("black");
//
//        result.stream().forEach(System.out::println);
//        System.out.println(result.size());
//    }
//
//    @Test
//    public void createBetCategory(){
//        List<String> betName = List.of("single","vSplit","row","hSplit","corner","zero","half","dozen","mis","column");
//        List<Double> betPayout = List.of(30.0,15.0,2.0,15.0,5.0,30.0,1.5,2.0,1.5,10.0);
//
//        for(int i=0;i<9;i++){
//            bcr.save(new BetCategory(betName.get(i),betPayout.get(i)));
//        }
//    }
//    @Test
//    public void betMap(){
//        singleIndexMapping();
//        vSplitMapping();
//        rowMapping();
//        hSplitMapping();
//        cornerMapping();
//        halfMapping();
//        dozenMapping();
//        misMapping();
//        columnMapping();
//        zeroMapping();
//
//    }
//    @Test
//    public void singleIndexMapping(){
//        for(int i=0;i<=36;i++){
//            bvmr.save(new BetValuesMap("single",i,""+i));
//            //TwoValueCombination.add(i+"");
//        }
//    }
//
//    @Test
//    public void vSplitMapping(){
//        int a=3;
//        int b=2;
//        for(int i=0;i<12;i++){
//            bvmr.save(new BetValuesMap("vSplit",i,a+","+b));
//            //System.out.println(a+","+b);
//            a+=3;
//            b+=3;
//        }
//
//        a=2;
//        b=1;
//        for(int i=12;i<24;i++){
//            bvmr.save(new BetValuesMap("vSplit",i,a+","+b));
//            //System.out.println(a+","+b);
//            a+=3;
//            b+=3;
//        }
//    }
//
//    @Test
//    public void rowMapping(){
//        int a=3;
//        int b=2;
//        int c=1;
//        String row1="";
//        String row2="";
//        String row3="";
//        for(int i=0;i<12;i++){
////            bvmr.save(new BetValuesMap("row",0,a+","+b));
//            row1+=(a)+",";
//            a+=3;
//            row2+=(b)+",";
//            b+=3;
//            row3+=(c)+",";
//            c+=3;
//        }
//        bvmr.save(new BetValuesMap("row",0,row1.substring(0,row1.length()-1)));
//        bvmr.save(new BetValuesMap("row",1,row2.substring(0,row1.length()-1)));
//        bvmr.save(new BetValuesMap("row",2,row3.substring(0,row1.length()-1)));
////        System.out.println(row1);
////        System.out.println(row2);
////        System.out.println(row3);
//    }
//
//    @Test
//    public void hSplitMapping(){
//        int a=3;
//        int b=6;
//        for(int i=0;i<11;i++){
//            bvmr.save(new BetValuesMap("hSplit",i,a+","+b));
//            //System.out.println(a+","+b);
//            a+=3;
//            b+=3;
//        }
//
//        a=2;
//        b=5;
//        for(int i=11;i<22;i++){
//            bvmr.save(new BetValuesMap("hSplit",i,a+","+b));
//            //System.out.println(a+","+b);
//            a+=3;
//            b+=3;
//        }
//        a=1;
//        b=4;
//        for(int i=22;i<33;i++){
//            bvmr.save(new BetValuesMap("hSplit",i,a+","+b));
//            //System.out.println(a+","+b);
//            a+=3;
//            b+=3;
//        }
//    }
//
//    @Test
//    public void cornerMapping(){
//        int a=2;
//        for(int i=0;i<11;i++){
//            bvmr.save(new BetValuesMap("corner",i,a+","+(a+1)+","+(a+3)+","+(a+4)));
//            a+=3;
//
//        }
//
//        a=1;
//
//        for(int i=11;i<22;i++){
//            bvmr.save(new BetValuesMap("corner",i,a+","+(a+1)+","+(a+3)+","+(a+4)));
//            a+=3;
//        }
//    }
//
//    @Test
//    public void halfMapping(){
//        String h1="";
//        String h2="";
//        for(int i=0;i<18;i++){
//            h1+=(i+1)+",";
//        }
//        for(int i=18;i<36;i++){
//            h2+=(i+1)+",";
//        }
//       bvmr.save(new BetValuesMap("half",0,h1.substring(0,h1.length()-1)));
//        bvmr.save(new BetValuesMap("half",1,h2.substring(0,h2.length()-1)));
////        System.out.println(h1);
////        System.out.println(h2);
//    }
//
//    @Test
//    public void dozenMapping(){
//        String h1="";
//        String h2="";
//        String h3="";
//        for(int i=0;i<12;i++){
//            h1+=(i+1)+",";
//        }
//        for(int i=12;i<24;i++){
//            h2+=(i+1)+",";
//        }
//        for(int i=24;i<36;i++){
//            h3+=(i+1)+",";
//        }
//        bvmr.save(new BetValuesMap("dozen",0,h1.substring(0,h1.length()-1)));
//        bvmr.save(new BetValuesMap("dozen",1,h2.substring(0,h2.length()-1)));
//        bvmr.save(new BetValuesMap("dozen",2,h3.substring(0,h3.length()-1)));
////        System.out.println(h1);
////        System.out.println(h2);
////        System.out.println(h3);
//    }
//
//    @Test
//    public void misMapping(){
//        String even = "";
//        String red ="1,3,5,7,9,12,14,16,18,19,23,25,27,30,32,34,36";
//        String black ="2,4,6,8,10,11,13,15,17,20,21,22,24,26,28,29,31,33,35";
//        String odd = "";
//
//        for(int i=1;i<=36;i++){
//            if(i%2==0){
//                even+=i+",";
//            }
//            else{
//                odd+=i+",";
//            }
//        }
//
//        System.out.println(even);
//        System.out.println(odd);
//
//        bvmr.save(new BetValuesMap("mis",0,even.substring(0,even.length()-1)));
//        bvmr.save(new BetValuesMap("mis",1,red));
//        bvmr.save(new BetValuesMap("mis",2,black));
//        bvmr.save(new BetValuesMap("mis",3,odd.substring(0,odd.length()-1)));
//    }
//
//    @Test
//    public void columnMapping(){
//        int a=1;
//        for(int i=0;i<12;i++){
//            bvmr.save(new BetValuesMap("column",i,a+","+(a+1)+","+(a+2)));
//            a+=3;
//        }
//    }
//
//    @Test
//    public void zeroMapping(){
//        bvmr.save(new BetValuesMap("zero",0,"0"));
//    }
//}
