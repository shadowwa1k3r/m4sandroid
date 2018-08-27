package com.osg.loki.m4s.Model;

import com.osg.loki.m4s.DataBase.ItemFullInfo;
import com.osg.loki.m4s.DataBase.ItemList;
import com.osg.loki.m4s.DataBase.MenuItem1List;
import com.osg.loki.m4s.DataBase.MenuItem2List;
import com.osg.loki.m4s.DataBase.MenuItem4List;
import com.osg.loki.m4s.DataBase.MenuItem5List;
import com.osg.loki.m4s.DataBase.MenuItem6List;
import com.osg.loki.m4s.DataBase.Test;
import com.osg.loki.m4s.Tools.State;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ergas on 1/9/2018.
 */

public class MainPageModel {




    private Realm mRealm;
    public MainPageModel(Realm realm){

            mRealm = realm;

    }
    public List<MainPageItemType> getList(){
        List<MainPageItemType> list=new ArrayList<>();
        RealmResults<MainPageItemType> itemList = mRealm.where(MainPageItemType.class).findAll();
        for (MainPageItemType item: itemList){

            list.add(new MainPageItemType(item.getTitle(),item.getIconpath()));
        }

        return list;
    }
    public ItemList getItemList(){
        ItemList menuItemList = new ItemList();
        RealmResults<MenuItem1List> menu1=mRealm.where(MenuItem1List.class).findAll();
        for(MenuItem1List item:menu1){
            menuItemList.menu1.add(new MenuItem1List(item.getId(),item.getText(),item.getImg()));
        }
        RealmResults<MenuItem2List> menu2=mRealm.where(MenuItem2List.class).findAll();
        for(MenuItem2List item:menu2){
            menuItemList.menu2.add(new MenuItem2List(item.getId(),item.getText(),item.getImg()));
        }
        RealmResults<MenuItem4List> menu4=mRealm.where(MenuItem4List.class).findAll();
        for(MenuItem4List item:menu4){
            menuItemList.menu4.add(new MenuItem4List(item.getId(),item.getText(),item.getImg()));
        }
        RealmResults<MenuItem5List> menu5=mRealm.where(MenuItem5List.class).findAll();
        for(MenuItem5List item:menu5){
            menuItemList.menu5.add(new MenuItem5List(item.getId(),item.getText(),item.getImg()));
        }
        RealmResults<MenuItem6List> menu6=mRealm.where(MenuItem6List.class).findAll();
        for(MenuItem6List item:menu6){
            menuItemList.menu6.add(new MenuItem6List(item.getId(),item.getText(),item.getImg()));
        }

        return menuItemList;

    }
    public ItemFullInfo getItemContent(int id){
        ItemFullInfo list;
        RealmResults<ItemFullInfo> itemList = mRealm.where(ItemFullInfo.class).equalTo("id",id).findAll();


            list=new ItemFullInfo(itemList.get(0).getId(),itemList.get(0).getContent(),itemList.get(0).getImg());

        return list;
    }
    public ArrayList<State> getStates(){
        ArrayList<State> states = new ArrayList<>();
        RealmResults<State> results = mRealm.where(State.class).findAll();
        for (State state:results) {
            states.add(new State(state.getSrc(),state.getName(),state.getFillColor(),state.getStrokeColor()));
        }
        return states;
    }
    public ArrayList<Test> getTest(int test_id){
        ArrayList<Test> tests = new ArrayList<>();
        RealmResults<Test> results = mRealm.where(Test.class).equalTo("test_id",test_id).findAll();
        for (Test test:results) {
            tests.add(new Test(test.getTest_id(),test.getTest_name(),test.getTest_description(),test.getQ_id(),test.getQ_content(),test.getA1(),test.getA2(),test.getA3(),test.getA4(),test.getA5(),test.getA()));
        }

        return tests;
    }

    public ArrayList<ResultDataModel> getResult(String text){
        ArrayList<ResultDataModel> result=new ArrayList<>();
        RealmResults<ItemFullInfo> itemList = mRealm.where(ItemFullInfo.class).contains("content",text).findAll();
        for (ItemFullInfo item:itemList){
            result.add(new ResultDataModel("",item.getContent(),item.getImg(),item.getId()));
        }
        return result;
    }



}
