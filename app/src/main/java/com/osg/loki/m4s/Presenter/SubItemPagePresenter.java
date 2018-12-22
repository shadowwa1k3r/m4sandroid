package com.osg.loki.m4s.Presenter;

import com.osg.loki.m4s.Contracts.SubItemPageContract;
import com.osg.loki.m4s.Model.MainPageModel;
import com.osg.loki.m4s.Model.Wikimodel;
import com.osg.loki.m4s.PresenterBase;
import com.osg.loki.m4s.SubItemPageAdapter;
import com.osg.loki.m4s.View.SubItemPageHolderView;

import java.util.List;

import io.realm.Realm;

/**
 * Created by ergas on 1/11/2018.
 */

public class SubItemPagePresenter extends PresenterBase<SubItemPageContract.View> implements SubItemPageContract.Presenter {
    private SubItemPageAdapter mAdapter;
//    private ItemList mItemList;
//    private List<MainPageItemType> mList;
    private List<Wikimodel> mList;
    private int current;
    private String[] returnfullid = new String[2];
    private MainPageModel baza=new MainPageModel(Realm.getDefaultInstance());

    public SubItemPagePresenter(){

    }

    @Override
    public void viewIsReady(){
        getView().setAdapter(mAdapter);
        getView().setTitle("123");
    }
    @Override
    public String[]  onItemSelected(int position){
        /*switch (current){
            case 0:
                returnfullid[0]= String.valueOf(mItemList.menu1.get(position).getId());
                returnfullid[1] = mItemList.menu1.get(position).getText();
                return returnfullid;
            case 1:
                returnfullid[0]= String.valueOf(mItemList.menu2.get(position).getId());
                returnfullid[1] = mItemList.menu2.get(position).getText();
                return returnfullid;
            case 3:
                returnfullid[0]= String.valueOf(mItemList.menu4.get(position).getId());
                returnfullid[1] = mItemList.menu4.get(position).getText();
                return returnfullid;
            case 4:
                returnfullid[0]= String.valueOf(mItemList.menu5.get(position).getId());
                returnfullid[1] = mItemList.menu5.get(position).getText();
                return returnfullid;
            case 5:
                returnfullid[0]= String.valueOf(mItemList.menu5.get(position).getId());
                returnfullid[1] = mItemList.menu5.get(position).getText();
                return returnfullid;
            default:return returnfullid;
        }*/
        returnfullid[0] = String.valueOf(mList.get(position).getId());
        returnfullid[1] = mList.get(position).getTitle();
        return  returnfullid;
    }
    @Override
    public void loadItemList(int position,String lang,String cat){
        /*mItemList=baza.getItemList();
        mList=baza.getList();
        mAdapter=new SubItemPageAdapter(this);
        current=position;*/
        mList = baza.getHelpList(lang, cat);
        mAdapter = new SubItemPageAdapter(this);
        current = position;


    }
    public void onBindSubItemPageHolderViewAtPosition(int position, SubItemPageHolderView view){
       /* switch (current){
            case 0:
                MenuItem1List item = mItemList.menu1.get(position);
                view.setIcon(item.getImg());
                view.setText(item.getText());
                break;
            case 1:
                MenuItem2List item2 = mItemList.menu2.get(position);
                view.setIcon(item2.getImg());
                view.setText(item2.getText());
                break;
            case 3:
                MenuItem4List item3 = mItemList.menu4.get(position);
                view.setIcon(item3.getImg());
                view.setText(item3.getText());
                break;
            case 4:
                MenuItem5List item4 = mItemList.menu5.get(position);
                view.setIcon(item4.getImg());
                view.setText(item4.getText());
                break;
            case 5:
                MenuItem6List item5 = mItemList.menu6.get(position);
                view.setIcon(item5.getImg());
                view.setText(item5.getText());
                break;

        }*/

       view.setIcon(123);
       view.setText(mList.get(position).getTitle());
    }
    public int getListItemsCount(){
        /*switch (current){
            case 0:return mItemList.menu1.size();
            case 1:return mItemList.menu2.size();
            case 3:return mItemList.menu4.size();
            case 4:return mItemList.menu5.size();
            case 5:return mItemList.menu6.size();
            default:return 0;
        }*/
        return  mList.size();

    }

}
