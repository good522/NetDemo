package com.example.nextdemo;

import java.util.List;

public class JsonData {
    public List<DetailData> data;
    public int errorCode;
    public String errorMsg;
    public static class DetailData {
        public String desc;
        public int id;
        public String imagePath;
        public int isVisible;
        public int order;
        public String title;
        public int type;
        public String url;
    }
}
