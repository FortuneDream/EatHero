package com.example.q.eathero.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by Q on 2016/6/15.
 */
public class MyUser extends BmobUser {
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
