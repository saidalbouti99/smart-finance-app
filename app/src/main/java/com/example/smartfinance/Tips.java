package com.example.smartfinance;

import java.util.List;

public class Tips {
    private  String tipsText;
    public Tips(){
    }

    public Tips( String tipsText) {
        this.tipsText = tipsText;
    }

    public String getTipsText() {
        return tipsText;
    }

    public void setTipsText(String tipsText) {
        this.tipsText = tipsText;
    }
}
