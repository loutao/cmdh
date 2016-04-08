package com.njucm.cmdh.app.misc;

import android.support.v4.app.Fragment;

import com.njucm.cmdh.app.fragments.ConsIdenty.ConsIdentyFragment;
import com.njucm.cmdh.app.fragments.FragmentAbout;
import com.njucm.cmdh.app.fragments.FragmentOne;
import com.njucm.cmdh.app.fragments.FragmentThree;
import com.njucm.cmdh.app.fragments.FragmentTwo;
import com.njucm.cmdh.app.fragments.healthdata.HealthDataFragement;
import com.njucm.cmdh.app.fragments.healthknowledges.HealthKnowledgeFragment;
import com.njucm.cmdh.app.fragments.mydevices.MyDevicesFragment;
import com.njucm.cmdh.app.fragments.myhealthy.MyConstitutionFragment;
import com.njucm.cmdh.app.fragments.myhealthy.MyHealthFragment;
import com.njucm.cmdh.app.fragments.myhealthy.MycontitutehistoryFragment;
import com.njucm.cmdh.app.fragments.myhealthy.habit.HabitFragment;
import com.njucm.cmdh.app.fragments.myhealthy.healthcollection.HeleathSuggestionCollectionFragment;
import com.njucm.cmdh.app.fragments.myhealthy.healthcollection.MyCollectionFragment;
import com.njucm.cmdh.app.fragments.myhealthy.healthsuggestion.HealthSuggestionFragment;
import com.njucm.cmdh.app.fragments.myhealthy.healthtending.HealthTendingFragment;
import com.njucm.cmdh.app.fragments.myhealthy.healthwarning.HealthWarningFragment;


/**
 * Created by Michal Bialas on 19/07/14.
 */
public enum Fragments {  //枚举类型在枚举类中直接实例化实例

    ONE(FragmentOne.class), TWO(FragmentTwo.class), THREE(FragmentThree.class), ABOUT(
            FragmentAbout.class), HealthKnowledge(HealthKnowledgeFragment.class), MyHealth(MyHealthFragment.class), ConsIdenty(ConsIdentyFragment.class), MyDevices(MyDevicesFragment.class),HealthData(HealthDataFragement.class),MyConstitutionFragment(MyConstitutionFragment.class),
            MyConstitute(MycontitutehistoryFragment.class), HealthSuggestion(HealthSuggestionFragment.class),HealthTending(HealthTendingFragment.class), HealthWarning(HealthWarningFragment.class)
            ,MyCollection(MyCollectionFragment.class), HealthSuggestionCollection(HeleathSuggestionCollectionFragment.class),
    HabitFragment1(HabitFragment.class);

    final Class<? extends Fragment> fragment;

    private Fragments(Class<? extends Fragment> fragment) {
        this.fragment = fragment;
    }   //构造方法

    public String getFragment() {
        return fragment.getName();  //每个类自带getName()方法
    }
}
